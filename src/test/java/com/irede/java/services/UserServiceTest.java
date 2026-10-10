package com.irede.java.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import com.irede.java.exceptions.AuthException;
import com.irede.java.models.DeveloperUser;
import com.irede.java.models.ProjectOwner;
import com.irede.java.models.User;
import com.irede.java.utils.PasswordHasher;
import com.irede.java.utils.Role;

class UserServiceTest {

    private static final String PO = "Product Owner";
    private static final String DEV = "Desenvolvedor";

    private FakeUserRepository repo;
    private UserService service;

    @BeforeEach
    void setUp() {
        repo = new FakeUserRepository();
        service = new UserService(repo);
    }

    // ---------- register ----------

    @Test
    void register_productOwner_criaProjectOwnerComSenhaHasheada() {
        User user = service.register("Ana", "ana@x.com", "segredo", "segredo", PO);

        assertInstanceOf(ProjectOwner.class, user);
        assertEquals(Role.PROJECTOWNER, user.getRole());
        assertNotEquals("segredo", user.getPassword(), "a senha não pode ser salva em texto puro");
        assertTrue(PasswordHasher.matches("segredo", user.getPassword()));
        assertEquals(1, repo.users.size());
    }

    @Test
    void register_desenvolvedor_criaDeveloperUser() {
        User user = service.register("Bia", "bia@x.com", "segredo", "segredo", DEV);

        assertInstanceOf(DeveloperUser.class, user);
        assertEquals(Role.DEVELOPER, user.getRole());
    }

    @Test
    void register_normalizaNomeEEmail() {
        User user = service.register("  Ana  ", "  ANA@X.COM ", "segredo", "segredo", DEV);

        assertEquals("Ana", user.getName());
        assertEquals("ana@x.com", user.getEmail());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void register_nomeVazio_lancaAuthException(String nome) {
        assertThrows(AuthException.class,
                () -> service.register(nome, "ana@x.com", "segredo", "segredo", DEV));
        assertTrue(repo.users.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"semarroba", "a@b", "a b@c.com", "@x.com", "ana@.com"})
    void register_emailInvalido_lancaAuthException(String email) {
        assertThrows(AuthException.class,
                () -> service.register("Ana", email, "segredo", "segredo", DEV));
        assertTrue(repo.users.isEmpty());
    }

    @Test
    void register_senhaCurta_lancaAuthException() {
        AuthException e = assertThrows(AuthException.class,
                () -> service.register("Ana", "ana@x.com", "12345", "12345", DEV));

        assertEquals("A senha deve ter ao menos 6 caracteres.", e.getMessage());
    }

    @Test
    void register_senhaComExatamenteSeisCaracteres_eAceita() {
        User user = service.register("Ana", "ana@x.com", "123456", "123456", DEV);

        assertEquals(1, repo.users.size());
        assertSame(user, repo.users.get(0));
    }

    @Test
    void register_confirmacaoDiferente_lancaAuthException() {
        AuthException e = assertThrows(AuthException.class,
                () -> service.register("Ana", "ana@x.com", "segredo", "outra123", DEV));

        assertEquals("As senhas não conferem.", e.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"Admin", "desenvolvedor"})
    void register_cargoInvalido_lancaAuthException(String cargo) {
        AuthException e = assertThrows(AuthException.class,
                () -> service.register("Ana", "ana@x.com", "segredo", "segredo", cargo));

        assertEquals("Selecione o cargo.", e.getMessage());
    }

    @Test
    void register_emailJaCadastrado_lancaAuthException() {
        service.register("Ana", "ana@x.com", "segredo", "segredo", DEV);

        AuthException e = assertThrows(AuthException.class,
                () -> service.register("Outra", "ANA@x.com", "segredo", "segredo", PO));

        assertEquals("Já existe uma conta com esse e-mail.", e.getMessage());
        assertEquals(1, repo.users.size());
    }

    // ---------- login ----------

    @Test
    void login_credenciaisCorretas_retornaUsuario() {
        User registrado = service.register("Ana", "ana@x.com", "segredo", "segredo", DEV);

        User logado = service.login("ana@x.com", "segredo");

        assertSame(registrado, logado);
    }

    @Test
    void login_emailEmOutraCaixaEComEspacos_eNormalizado() {
        service.register("Ana", "ana@x.com", "segredo", "segredo", DEV);

        assertEquals("Ana", service.login("  ANA@X.com ", "segredo").getName());
    }

    @Test
    void login_senhaErrada_lancaAuthException() {
        service.register("Ana", "ana@x.com", "segredo", "segredo", DEV);

        AuthException e = assertThrows(AuthException.class, () -> service.login("ana@x.com", "errada"));

        assertEquals("E-mail ou senha inválidos.", e.getMessage());
    }

    @Test
    void login_usuarioInexistente_lancaAuthExceptionComMesmaMensagem() {
        AuthException e = assertThrows(AuthException.class,
                () -> service.login("ninguem@x.com", "segredo"));

        assertEquals("E-mail ou senha inválidos.", e.getMessage());
    }

    @Test
    void login_camposVazios_lancaAuthException() {
        assertThrows(AuthException.class, () -> service.login(null, "segredo"));
        assertThrows(AuthException.class, () -> service.login("  ", "segredo"));
        assertThrows(AuthException.class, () -> service.login("ana@x.com", null));
        assertThrows(AuthException.class, () -> service.login("ana@x.com", ""));
    }

    // ---------- getName / getIdByName / getAllNames ----------

    @Test
    void getName_idExistente_retornaNome() {
        User ana = service.register("Ana", "ana@x.com", "segredo", "segredo", DEV);

        assertEquals("Ana", service.getName(ana.getId()));
    }

    @Test
    void getName_idNuloOuInexistente_retornaTodos() {
        assertEquals(UserService.ALL, service.getName(null));
        assertEquals(UserService.ALL, service.getName(999));
    }

    @Test
    void getIdByName_nomeExistente_retornaId() {
        User ana = service.register("Ana", "ana@x.com", "segredo", "segredo", DEV);

        assertEquals(ana.getId(), service.getIdByName("Ana"));
    }

    @Test
    void getIdByName_todosVazioNuloOuDesconhecido_retornaNull() {
        service.register("Ana", "ana@x.com", "segredo", "segredo", DEV);

        assertNull(service.getIdByName(UserService.ALL));
        assertNull(service.getIdByName(""));
        assertNull(service.getIdByName("   "));
        assertNull(service.getIdByName(null));
        assertNull(service.getIdByName("Desconhecido"));
    }

    @Test
    void getAllNames_comecaPorTodosSeguidoDosUsuarios() {
        service.register("Ana", "ana@x.com", "segredo", "segredo", DEV);
        service.register("Bia", "bia@x.com", "segredo", "segredo", PO);

        assertEquals(List.of("Todos", "Ana", "Bia"), service.getAllNames());
    }

    @Test
    void getAllNames_semUsuarios_retornaSoTodos() {
        assertEquals(List.of("Todos"), service.getAllNames());
    }
}
