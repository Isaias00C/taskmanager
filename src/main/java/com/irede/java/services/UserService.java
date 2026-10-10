package com.irede.java.services;

import java.util.List;

import com.irede.java.exceptions.AuthException;
import com.irede.java.models.DeveloperUser;
import com.irede.java.models.ProjectOwner;
import com.irede.java.models.User;
import com.irede.java.utils.PasswordHasher;
import com.irede.java.utils.Role;
import com.irede.java.repository.UserRepository;

public class UserService {
    public static final String ALL = "Todos";

    private final UserRepository repo;

    public UserService() {
        this(new UserRepository());
    }

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

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

    public User register(String name, String email, String password, String confirmPassword, String roleLabel) {
        if (name == null || name.isBlank()) throw new AuthException("Informe o nome.");
        if (email == null || !email.trim().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new AuthException("Informe um e-mail válido.");
        }
        if (password == null || password.length() < 6) throw new AuthException("A senha deve ter ao menos 6 caracteres.");
        if (!password.equals(confirmPassword)) throw new AuthException("As senhas não conferem.");
        Role role = roleFromLabel(roleLabel);

        String normalizedEmail = email.trim().toLowerCase();
        if (repo.findByEmail(normalizedEmail) != null) throw new AuthException("Já existe uma conta com esse e-mail.");

        String hash = PasswordHasher.hash(password);
        User user = role == Role.PROJECTOWNER
                ? new ProjectOwner(0, name.trim(), normalizedEmail, hash)
                : new DeveloperUser(0, name.trim(), normalizedEmail, hash);
        repo.add(user);
        return user;
    }

    public User login(String email, String password) {
        if (email == null || email.isBlank() || password == null || password.isEmpty()) {
            throw new AuthException("Informe e-mail e senha.");
        }
        User user = repo.findByEmail(email.trim().toLowerCase());
        if (user == null || !PasswordHasher.matches(password, user.getPassword())) {
            throw new AuthException("E-mail ou senha inválidos.");
        }
        return user;
    }

    private Role roleFromLabel(String label) {
        if ("Product Owner".equals(label)) return Role.PROJECTOWNER;
        if ("Desenvolvedor".equals(label)) return Role.DEVELOPER;
        throw new AuthException("Selecione o cargo.");
    }
}
