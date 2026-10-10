package com.irede.java.exceptions.validators;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import com.irede.java.exceptions.InvalidTaskException;
import com.irede.java.exceptions.NullStatusException;
import com.irede.java.exceptions.TaskNotFoundException;
import com.irede.java.models.Task;
import com.irede.java.models.TaskStatus;

class ValidatorsTest {

    // ---------- InvalidTaskValidator ----------

    @Test
    void invalidTask_dadosValidos_naoLanca() {
        assertDoesNotThrow(() -> InvalidTaskValidator.validate(1, "Login", "desc"));
    }

    @Test
    void invalidTask_semResponsavel_naoLanca() {
        assertDoesNotThrow(() -> InvalidTaskValidator.validate(null, "Login", "desc"));
    }

    @Test
    void invalidTask_descricaoVazia_naoLanca() {
        assertDoesNotThrow(() -> InvalidTaskValidator.validate(1, "Login", ""));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t"})
    void invalidTask_tituloNuloOuEmBranco_lanca(String titulo) {
        assertThrows(InvalidTaskException.class, () -> InvalidTaskValidator.validate(1, titulo, "desc"));
    }

    @Test
    void invalidTask_descricaoNula_lanca() {
        assertThrows(InvalidTaskException.class, () -> InvalidTaskValidator.validate(1, "Login", null));
    }

    // ---------- NullStatusValidator ----------

    @ParameterizedTest
    @EnumSource(TaskStatus.class)
    void nullStatus_statusPreenchido_naoLanca(TaskStatus status) {
        assertDoesNotThrow(() -> NullStatusValidator.validate(status));
    }

    @Test
    void nullStatus_statusNulo_lanca() {
        assertThrows(NullStatusException.class, () -> NullStatusValidator.validate(null));
    }

    // ---------- TaskNotFoundValidator ----------

    @Test
    void taskNotFound_tarefaExistente_naoLanca() {
        assertDoesNotThrow(() -> TaskNotFoundValidator.validate(new Task(1, "Login", "desc")));
    }

    @Test
    void taskNotFound_tarefaNula_lanca() {
        assertThrows(TaskNotFoundException.class, () -> TaskNotFoundValidator.validate(null));
    }
}
