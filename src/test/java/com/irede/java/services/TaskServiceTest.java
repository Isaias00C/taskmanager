package com.irede.java.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.irede.java.exceptions.InvalidTaskException;
import com.irede.java.exceptions.NullStatusException;
import com.irede.java.exceptions.TaskNotFoundException;
import com.irede.java.models.DeveloperUser;
import com.irede.java.models.Task;
import com.irede.java.models.TaskStatus;

class TaskServiceTest {

    private FakeTaskRepository taskRepo;
    private FakeUserRepository userRepo;
    private TaskService service;

    @BeforeEach
    void setUp() {
        taskRepo = new FakeTaskRepository();
        userRepo = new FakeUserRepository();
        service = new TaskService(taskRepo, new UserService(userRepo));
    }

    // ---------- createTask ----------

    @Test
    void createTask_dadosValidos_persisteERetornaTarefaNaoIniciada() {
        Task task = service.createTask(1, "Login", "Tela de login");

        assertEquals("Login", task.getTitle());
        assertEquals("Tela de login", task.getDescription());
        assertEquals(1, task.getAssignTo());
        assertEquals(TaskStatus.NAO_INICIADA, task.getStatus());
        assertEquals(1, taskRepo.tasks.size());
        assertTrue(task.getId() > 0, "o repositório deve atribuir o id");
    }

    @Test
    void createTask_semResponsavel_eAceita() {
        Task task = service.createTask(null, "Login", "Tela de login");

        assertNull(task.getAssignTo());
        assertEquals(1, taskRepo.tasks.size());
    }

    @Test
    void createTask_tituloInvalido_lancaInvalidTaskExceptionENaoPersiste() {
        assertThrows(InvalidTaskException.class, () -> service.createTask(1, null, "desc"));
        assertThrows(InvalidTaskException.class, () -> service.createTask(1, "   ", "desc"));
        assertTrue(taskRepo.tasks.isEmpty());
    }

    @Test
    void createTask_descricaoNula_lancaInvalidTaskException() {
        assertThrows(InvalidTaskException.class, () -> service.createTask(1, "Login", null));
    }

    // ---------- updateDescription ----------

    @Test
    void updateDescription_tarefaExistente_alteraEPersiste() {
        service.createTask(1, "Login", "antiga");

        service.updateDescription("Login", "nova");

        assertEquals("nova", service.getTaskByTitle("Login").getDescription());
        assertEquals(1, taskRepo.updateCalls);
    }

    @Test
    void updateDescription_tarefaInexistente_lancaTaskNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> service.updateDescription("Nada", "x"));
        assertEquals(0, taskRepo.updateCalls);
    }

    // ---------- updateStatus ----------

    @Test
    void updateStatus_opcao1_marcaEmAndamento() {
        service.createTask(1, "Login", "desc");

        service.updateStatus("Login", 1);

        assertEquals(TaskStatus.EM_ANDAMENTO, service.getTaskByTitle("Login").getStatus());
        assertEquals(1, taskRepo.updateCalls);
    }

    @Test
    void updateStatus_opcao2_marcaConcluida() {
        service.createTask(1, "Login", "desc");

        service.updateStatus("Login", 2);

        assertEquals(TaskStatus.CONCLUIDA, service.getTaskByTitle("Login").getStatus());
    }

    @Test
    void updateStatus_opcaoDesconhecida_lancaNullStatusExceptionESemAlterar() {
        service.createTask(1, "Login", "desc");

        assertThrows(NullStatusException.class, () -> service.updateStatus("Login", 99));

        assertEquals(TaskStatus.NAO_INICIADA, service.getTaskByTitle("Login").getStatus());
        assertEquals(0, taskRepo.updateCalls);
    }

    @Test
    void updateStatus_tarefaInexistente_lancaTaskNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> service.updateStatus("Nada", 1));
    }

    // ---------- updateTask ----------

    @Test
    void updateTask_dadosValidos_aplicaTodosOsCamposEPersiste() {
        Task task = service.createTask(1, "Login", "antiga");

        service.updateTask(task, 2, "nova", TaskStatus.CONCLUIDA);

        assertEquals(2, task.getAssignTo());
        assertEquals("nova", task.getDescription());
        assertEquals(TaskStatus.CONCLUIDA, task.getStatus());
        assertEquals(1, taskRepo.updateCalls);
    }

    @Test
    void updateTask_descricaoNula_lancaInvalidTaskExceptionESemAlterar() {
        Task task = service.createTask(1, "Login", "antiga");

        assertThrows(InvalidTaskException.class,
                () -> service.updateTask(task, 2, null, TaskStatus.CONCLUIDA));

        assertEquals(1, task.getAssignTo());
        assertEquals("antiga", task.getDescription());
        assertEquals(0, taskRepo.updateCalls);
    }

    // ---------- consultas ----------

    @Test
    void getAllTasks_retornaTodasAsTarefas() {
        service.createTask(1, "A", "d");
        service.createTask(2, "B", "d");

        assertEquals(2, service.getAllTasks().size());
    }

    @Test
    void getTaskByTitle_existente_retornaTarefa() {
        Task criada = service.createTask(1, "Login", "desc");

        assertSame(criada, service.getTaskByTitle("Login"));
    }

    @Test
    void getTaskByTitle_inexistente_lancaTaskNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> service.getTaskByTitle("Nada"));
    }

    @Test
    void getTasksByUser_retornaTarefasDoUsuarioEAsSemResponsavel() {
        service.createTask(1, "Do usuário 1", "d");
        service.createTask(2, "Do usuário 2", "d");
        service.createTask(null, "De todos", "d");

        List<Task> tarefas = service.getTasksByUser(1);

        assertEquals(List.of("Do usuário 1", "De todos"),
                tarefas.stream().map(Task::getTitle).toList());
    }

    // ---------- getters de apresentação ----------

    @Test
    void getters_retornamOsDadosDaTarefa() {
        Task task = service.createTask(1, "Login", "desc");
        task.setStatus(TaskStatus.EM_ANDAMENTO);

        assertEquals("Login", service.getTitle(task));
        assertEquals("desc", service.getDescription(task));
        assertEquals("Em Andamento", service.getStatus(task));
    }

    @Test
    void getAssignTo_usuarioExistente_retornaNome() {
        userRepo.add(new DeveloperUser(0, "Ana", "ana@x.com", "hash"));
        Task task = service.createTask(1, "Login", "desc");

        assertEquals("Ana", service.getAssignTo(task));
    }

    @Test
    void getAssignTo_semResponsavel_retornaTodos() {
        Task task = service.createTask(null, "Login", "desc");

        assertEquals(UserService.ALL, service.getAssignTo(task));
    }
}
