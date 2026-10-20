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

    @ParameterizedTest // são testes que recebem parâmetro na função, diferente do @Test que não recebe parâmetro
    @EnumSource(TaskStatus.class) // vai servir como parâmetro para a função, roda uma vez para NAO_INICIADO, EM_ANDAMENTO, CONCLUIDO. Se adicionar um novo valor ao enum eu não preciso alterar o teste
    void fromLabel_labelDeCadaStatus_fazOIdaEVolta(TaskStatus status) {
        assertEquals(status, TaskStatus.fromLabel(status.getLabel()));
    }

    @ParameterizedTest
    @NullSource // o @ValueSource vai servir como valores para o parâmetro do teste, mas ele não passa null como valor, ele tem um annotation próprio para isso
    @ValueSource(strings = {"", "desconhecido", "em andamento", "CONCLUIDA"}) // lista de valores que serão passados como parâmetro para o teste. Ou seja, o teste rodará 5 vezes (null + 4 ‘strings’)
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
