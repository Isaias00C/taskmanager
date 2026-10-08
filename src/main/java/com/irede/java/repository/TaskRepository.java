package com.irede.java.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.irede.java.config.ConnectionFactory;
import com.irede.java.models.Task;

public class TaskRepository implements Repository<Task>{

    public Task findTaskByTitle(String title) {
        String sql = "SELECT * FROM task WHERE task.title=?";

        try (Connection conn = ConnectionFactory.conect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1,  title);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) return null;

            return new Task(
                    rs.getInt("assignTo"),
                    rs.getString("title"),
                    rs.getString("description")
            );

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void add(Task t) {
        String sql = "INSERT INTO task (title, description, status) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.conect()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, t.getTitle());
            stmt.setString(2, t.getDescription());
            stmt.setString(3, t.getStatus().name());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Task getById(int id) {
        String sql = "SELECT * FROM task WHERE task.id=?";

        try(Connection conn = ConnectionFactory.conect(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if(!rs.next()) return null;

            return new Task(
                    rs.getInt("assignTo"),
                    rs.getString("title"),
                    rs.getString("description")
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Task delete(Task t) {
        String sql = "DELETE FROM task WHERE task.id=?";

        try( Connection conn = ConnectionFactory.conect(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, t.getId());

            int affectedRows = stmt.executeUpdate();

            return affectedRows>0 ? t : null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Task update(Task t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}