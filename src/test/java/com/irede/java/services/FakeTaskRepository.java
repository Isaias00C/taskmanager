package com.irede.java.services;

import java.util.ArrayList;
import java.util.List;

import com.irede.java.models.Task;
import com.irede.java.repository.TaskRepository;

/** TaskRepository em memória: permite testar TaskService sem MySQL. */
class FakeTaskRepository extends TaskRepository {
    final List<Task> tasks = new ArrayList<>();
    int updateCalls = 0;
    private int nextId = 1;

    @Override
    public void add(Task t) {
        t.setId(nextId++);
        tasks.add(t);
    }

    @Override
    public Task update(Task t) {
        updateCalls++;
        return t;
    }

    @Override
    public Task findTaskByTitle(String title) {
        return tasks.stream().filter(t -> t.getTitle().equals(title)).findFirst().orElse(null);
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(tasks);
    }

    /** Mesma regra do SQL real: tarefas do usuário + tarefas sem responsável. */
    @Override
    public List<Task> findByAssignTo(int userId) {
        return tasks.stream()
                .filter(t -> t.getAssignTo() == null || t.getAssignTo() == userId)
                .toList();
    }
}
