package com.irede.java.utils;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.irede.java.models.DeveloperUser;
import com.irede.java.models.User;

class SessionTest {

    // Session é estado estático global: limpa para não vazar entre testes.
    @AfterEach
    void limpaSessao() {
        Session.clear();
    }

    @Test
    void getCurrentUser_semLogin_retornaNull() {
        assertNull(Session.getCurrentUser());
    }

    @Test
    void setCurrentUser_guardaOUsuarioLogado() {
        User user = new DeveloperUser(1, "Ana", "ana@x.com", "hash");

        Session.setCurrentUser(user);

        assertSame(user, Session.getCurrentUser());
    }

    @Test
    void clear_removeOUsuarioLogado() {
        Session.setCurrentUser(new DeveloperUser(1, "Ana", "ana@x.com", "hash"));

        Session.clear();

        assertNull(Session.getCurrentUser());
    }
}
