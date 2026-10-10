package com.irede.java.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

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

    @ParameterizedTest
    @EnumSource(TaskStatus.class)
    void fromLabel_labelDeCadaStatus_fazOIdaEVolta(TaskStatus status) {
        assertEquals(status, TaskStatus.fromLabel(status.getLabel()));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "desconhecido", "em andamento", "CONCLUIDA"})
    void fromLabel_labelInvalido_retornaNaoIniciada(String label) {
        assertEquals(TaskStatus.NAO_INICIADA, TaskStatus.fromLabel(label));
    }

    @Test
    void getLabel_retornaOTextoExibidoNaTela() {
        assertEquals("Não Iniciada", TaskStatus.NAO_INICIADA.getLabel());
        assertEquals("Em Andamento", TaskStatus.EM_ANDAMENTO.getLabel());
        assertEquals("Concluída", TaskStatus.CONCLUIDA.getLabel());
    }
}
