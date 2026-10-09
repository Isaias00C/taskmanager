package com.irede.java.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TaskStatusTest {

    @Test
    void fromLabel_labelValido_retornaOStatusCorrespondente() {
        // Arrange: o dado de entrada
        String label = "Em Andamento";

        // Act: executa o que está sendo testado
        TaskStatus resultado = TaskStatus.fromLabel(label);

        // Assert: confere o resultado esperado
        assertEquals(TaskStatus.EM_ANDAMENTO, resultado);
    }
}
