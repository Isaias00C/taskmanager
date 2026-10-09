package com.irede.java.services;

import java.util.List;

import com.irede.java.models.User;
import com.irede.java.repository.UserRepository;

public class UserService {
    public static final String ALL = "Todos";

    private final UserRepository repo = new UserRepository();

    /** Nome do usuário, ou "Todos" quando o id é nulo ou não existe mais. */
    public String getName(Integer id) {
        if (id == null) return ALL;
        User user = repo.getById(id);
        return user == null ? ALL : user.getName();
    }

    /** Id do usuário com esse nome; null para "Todos", vazio ou nome desconhecido. */
    public Integer getIdByName(String name) {
        if (name == null || name.isBlank() || ALL.equals(name)) return null;
        User user = repo.getByName(name);
        return user == null ? null : user.getId();
    }

    /** Nomes para o combo de responsáveis, começando por "Todos". */
    public List<String> getAllNames() {
        List<String> names = new java.util.ArrayList<>();
        names.add(ALL);
        for (User u : repo.findAll()) names.add(u.getName());
        return names;
    }
}
