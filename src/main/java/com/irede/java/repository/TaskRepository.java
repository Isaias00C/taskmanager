package com.irede.java.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import com.irede.java.config.ConnectionFactory;
import com.irede.java.models.Task;

public class TaskRepository implements Repository<Task>{
    private ArrayList<Task> repo;

    public TaskRepository() {
        this.repo = new ArrayList<Task>();       
    }

    public Task findTaskByTitle(String title) {
        for (Task t : repo){
            if(t.getTitle().equals(title)){
                return t;
            }
        }

        return null;
    }

    public ArrayList<Task> getRepo() {
        return repo;
    }

    @Override
    public void add(Task t) {
        repo.add(t);
    }

    @Override
    public Task getById(int id) {
        return repo.get(id);
    }

    @Override
    public Task delete(Task t) {
        repo.remove(t);
        return t;
    }

    @Override
    public Task update(Task t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    public void save(Task task) throws SQLException{
        String sql = "INSERT INTO task (title, description, status) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.conect()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getStatus().name());
            stmt.executeUpdate();
        }
    }
}