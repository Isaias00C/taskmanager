package com.irede.java.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.irede.java.utils.Role;

class UserTest {

    @Test
    void projectOwner_temRoleProjectOwnerEDadosDoConstrutor() {
        User po = new ProjectOwner(1, "Ana", "ana@x.com", "hash");

        assertEquals(1, po.getId());
        assertEquals("Ana", po.getName());
        assertEquals("ana@x.com", po.getEmail());
        assertEquals("hash", po.getPassword());
        assertEquals(Role.PROJECTOWNER, po.getRole());
    }

    @Test
    void developerUser_temRoleDeveloper() {
        User dev = new DeveloperUser(2, "Bia", "bia@x.com", "hash");

        assertEquals(Role.DEVELOPER, dev.getRole());
    }

    @Test
    void projectOwner_possuiTodasAsPermissoes() {
        User po = new ProjectOwner(1, "Ana", "ana@x.com", "hash");

        assertTrue(po.canCreateTask());
        assertTrue(po.canManageAnyTask());
        assertTrue(po.canSchudelerMeeting());
    }

    @Test
    void developerUser_naoPossuiPermissoesDeGestao() {
        User dev = new DeveloperUser(2, "Bia", "bia@x.com", "hash");

        assertFalse(dev.canCreateTask());
        assertFalse(dev.canManageAnyTask());
        assertFalse(dev.canSchudelerMeeting());
    }

    @Test
    void setters_atualizamOsValores() {
        User user = new DeveloperUser(2, "Bia", "bia@x.com", "hash");

        user.setId(10);
        user.setName("Beatriz");
        user.setEmail("beatriz@x.com");
        user.setPassword("novo-hash");

        assertEquals(10, user.getId());
        assertEquals("Beatriz", user.getName());
        assertEquals("beatriz@x.com", user.getEmail());
        assertEquals("novo-hash", user.getPassword());
    }
}
