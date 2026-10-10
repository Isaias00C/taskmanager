package com.irede.java.services;

import java.util.ArrayList;
import java.util.List;

import com.irede.java.models.User;
import com.irede.java.repository.UserRepository;

/** UserRepository em memória: permite testar UserService sem MySQL. */
class FakeUserRepository extends UserRepository {
    final List<User> users = new ArrayList<>();
    private int nextId = 1;

    @Override
    public void add(User user) {
        user.setId(nextId++);
        users.add(user);
    }

    @Override
    public User getById(int id) {
        return users.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }

    @Override
    public User getByName(String name) {
        return users.stream().filter(u -> u.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return users.stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users);
    }
}
