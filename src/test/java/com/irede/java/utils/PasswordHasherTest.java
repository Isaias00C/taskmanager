package com.irede.java.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class PasswordHasherTest {

    @Test
    void hash_naoGuardaASenhaEmTextoPuro() {
        String hash = PasswordHasher.hash("segredo");

        assertFalse(hash.contains("segredo"));
    }

    @Test
    void hash_usaFormatoIteracoesSaltHash() {
        String[] partes = PasswordHasher.hash("segredo").split(":");

        assertEquals(3, partes.length);
        assertTrue(Integer.parseInt(partes[0]) > 0);
    }

    @Test
    void hash_mesmaSenhaGeraHashesDiferentesPorCausaDoSalt() {
        assertNotEquals(PasswordHasher.hash("segredo"), PasswordHasher.hash("segredo"));
    }

    @Test
    void matches_senhaCorreta_retornaTrue() {
        String hash = PasswordHasher.hash("segredo");

        assertTrue(PasswordHasher.matches("segredo", hash));
    }

    @Test
    void matches_senhaErrada_retornaFalse() {
        String hash = PasswordHasher.hash("segredo");

        assertFalse(PasswordHasher.matches("Segredo", hash));
        assertFalse(PasswordHasher.matches("", hash));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"texto-puro", "a:b", "x:y:z", "120000:!!!:!!!", "a:b:c:d"})
    void matches_hashArmazenadoMalformado_retornaFalseSemLancar(String armazenado) {
        assertFalse(PasswordHasher.matches("segredo", armazenado));
    }
}
