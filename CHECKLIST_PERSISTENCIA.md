# Checklist de persistência (MySQL/JDBC)

## Estado atual
| Peça | Situação |
|---|---|
| `init.sql` (tabelas `user`, `task`) + docker-compose | ✅ pronto |
| `ConnectionFactory` | ✅ funciona (credenciais fixas no código) |
| `UserRepository.add` / `getById` | ✅ persiste |
| `UserRepository.update` / `delete` | ❌ `UnsupportedOperationException` |
| `TaskRepository.save` | ⚠️ insere, mas **não grava `assign_to`** e nada o chama |
| `TaskRepository.add/getById/delete/findTaskByTitle` | ❌ ainda em `ArrayList` (memória) |
| `TaskRepository.update` | ❌ `UnsupportedOperationException` |
| `TaskService` | ❌ só usa memória; nenhuma tarefa chega ao banco |
| `LoginController.onLogin` / `SignInController.onSignIn` | ❌ não usam `UserRepository` |
| Senha | ❌ gravada em texto puro |

## A fazer (ordem sugerida)
- [ ] **1. Modelo `Task`**: adicionar `id` (int) com getter/setter e preencher ao ler do banco.
- [ ] **2. Decidir `assignTo`**: hoje é `String` (nome), mas a coluna é `INT` FK → `user.id`. Opções: `Task.assignTo` vira `User`/`int`, ou a coluna vira `VARCHAR`.
- [ ] **3. `UserRepository`**: `findByEmail` (login), `findAll` (combo de responsáveis), `update`, `delete`. Parar de engolir a `SQLException` no `add` (propagar para a UI tratar e-mail duplicado).
- [ ] **4. `TaskRepository` em JDBC**: `add` (gravando `assign_to` e lendo o id gerado com `RETURN_GENERATED_KEYS`), `getById`, `findAll`, `findByTitle`, `update`, `delete`.
- [ ] **5. `TaskService`** usar o repositório JDBC (remover a lista em memória e o import não usado `com.mysql.cj.conf.StringProperty`).
- [ ] **6. Cadastro/Login**: `SignInController` → `UserRepository.add`; `LoginController` → `findByEmail` + conferência de senha; guardar o usuário logado (classe de sessão).
- [ ] **7. Hash de senha** (BCrypt ou SHA-256 + salt) antes de gravar.
- [ ] **8. Telas** (`POController`, `DevViewController`): carregar tarefas do banco ao abrir e usar o service nas ações.
- [ ] **9. Config**: tirar usuário/senha do código (variáveis de ambiente); avaliar remover `Scratch.java`.

## Como testar com JUnit
JUnit 4.11 já está no `pom.xml`; testes em `src/test/java`. Rode com `mvn test`.

### Sem banco (começar por aqui)
- [ ] `Task`: valores iniciais (status `NAO_INICIADA`), setters e properties.
- [ ] `TaskStatus.fromLabel`: label válido, desconhecido e `null` → `NAO_INICIADA`.
- [ ] Validators (`InvalidTaskValidator`, `NullStatusValidator`, `TaskNotFoundValidator`): válido não lança; `null`/título em branco lançam a exceção certa (`@Test(expected = ...)`).
- [ ] `TaskService`: criar, buscar por título, `updateDescription`, `updateStatus` (opções 1, 2 e inválida), `updateTask`.
  - Para isolar do banco, o service deve receber o repositório pelo construtor (idealmente uma interface de repositório de tarefas) e o teste passa uma versão em memória.
- [ ] Papéis (`ProjectOwner` / `DeveloperUser`): `getRole()` correto e `canCreateTask()` etc. PO → `true`, Developer → `false`.
  - ⚠️ Atenção: as duas classes declaram um campo `role` próprio que esconde o de `User` e fica sempre `null`; por isso `canCreateTask()` do PO retorna `false`. Remover os campos duplicados e usar `getRole()`.

### Com banco (repositórios)
- [ ] Subir o MySQL: `docker compose up -d db`.
- [ ] Criar um banco separado `taskmanager_test` (para não apagar seus dados de dev) e permitir o usuário `admin` nele.
- [ ] Fazer o `ConnectionFactory` aceitar a URL por propriedade de sistema (ex.: `System.getProperty("db.url", <padrão>)`) e configurar o surefire no `pom.xml` para usar o banco de teste.
- [ ] `@Before`: recriar as tabelas executando o `init.sql` (schema igual ao real) ou dar `DELETE` nas tabelas.
- [ ] `UserRepository`: `add` + `getById` devolve `DeveloperUser`/`ProjectOwner` com os mesmos dados; id inexistente → `null`; e-mail duplicado não cria 2º usuário; `findByEmail` (existe/não existe); `update`; `delete`.
- [ ] `TaskRepository`: `add` grava título, descrição, status (como `status.name()`) e `assign_to`; `getById`; `findAll` (vazio/vários); `update`; `delete`; título nulo falha.
- [ ] FK: apagar o usuário deixa `task.assign_to = NULL` (`ON DELETE SET NULL`).
- [ ] `TaskService` com repositório real: criar → recriar o service → a tarefa continua lá.
- [ ] Login: senha correta, senha errada, e-mail inexistente.
- [ ] Hash: senha no banco ≠ senha digitada e a verificação confere.

**Dica:** controllers JavaFX são difíceis de testar; mantenha as regras em `services/` e teste lá.
