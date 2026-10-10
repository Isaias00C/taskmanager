package com.irede.java.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TaskTest {

    @Test
    void construtor_definePropriedadesEIniciaComoNaoIniciada() {
        Task task = new Task(7, "Login", "Tela de login");

        assertEquals(7, task.getAssignTo());
        assertEquals("Login", task.getTitle());
        assertEquals("Tela de login", task.getDescription());
        assertEquals(TaskStatus.NAO_INICIADA, task.getStatus());
        assertEquals(0, task.getId());
    }

    @Test
    void construtor_aceitaResponsavelNulo() {
        Task task = new Task(null, "Login", "desc");

        assertNull(task.getAssignTo());
    }

    @Test
    void setters_atualizamOsValores() {
        Task task = new Task(1, "Login", "desc");

        task.setAssignTo(2);
        task.setDescription("nova");
        task.setStatus(TaskStatus.CONCLUIDA);
        task.setId(42);

        assertEquals(2, task.getAssignTo());
        assertEquals("nova", task.getDescription());
        assertEquals(TaskStatus.CONCLUIDA, task.getStatus());
        assertEquals(42, task.getId());
    }

    @Test
    void propriedades_refletemMudancasNosSetters() {
        Task task = new Task(1, "Login", "desc");

        task.setStatus(TaskStatus.EM_ANDAMENTO);
        task.setDescription("nova");

        assertEquals(TaskStatus.EM_ANDAMENTO, task.statusProperty().get());
        assertEquals("nova", task.descriptionProperty().get());
        assertEquals("Login", task.titleProperty().get());
    }

    @Test
    void propriedades_notificamListenersQuandoMudam() {
        Task task = new Task(1, "Login", "desc");
        TaskStatus[] ultimo = new TaskStatus[1];
        task.statusProperty().addListener((obs, antigo, novo) -> ultimo[0] = novo);

        task.setStatus(TaskStatus.CONCLUIDA);

        assertEquals(TaskStatus.CONCLUIDA, ultimo[0]);
    }

    @Test
    void toString_contemTituloDescricaoEStatus() {
        Task task = new Task(1, "Login", "desc");

        String texto = task.toString();

        assertTrue(texto.contains("Login"));
        assertTrue(texto.contains("desc"));
        assertTrue(texto.contains("NAO_INICIADA"));
    }
}
