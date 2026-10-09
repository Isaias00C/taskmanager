package com.irede.java.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.irede.java.config.ConnectionFactory;
import com.irede.java.models.Task;
import com.irede.java.models.TaskStatus;

public class TaskRepository implements Repository<Task>{

    public Task findTaskByTitle(String title) {
        String sql = "SELECT * FROM task WHERE task.title=?";

        try (Connection conn = ConnectionFactory.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1,  title);
            ResultSet rs = stmt.executeQuery();

            return rs.next() ? map(rs) : null;

        } catch (SQLException e) {
            throw  new RuntimeException(e);
        }
    }

    @Override
    public void add(Task t) {
        String sql = "INSERT INTO task (assign_to, title, description, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.connect(); PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);){
            setAssignTo(stmt, 1, t.getAssignTo());
            stmt.setString(2, t.getTitle());
            stmt.setString(3, t.getDescription());
            stmt.setString(4, t.getStatus().name());

            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()){
                if (keys.next()) { t.setId(keys.getInt(1)); }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Task getById(int id) {
        String sql = "SELECT * FROM task WHERE task.id=?";

        try(Connection conn = ConnectionFactory.connect(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            return rs.next() ? map(rs) : null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Task delete(Task t) {
        String sql = "DELETE FROM task WHERE task.id=?";

        try( Connection conn = ConnectionFactory.connect(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, t.getId());

            int affectedRows = stmt.executeUpdate();

            return affectedRows>0 ? t : null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Task update(Task t) {
        String sql = "UPDATE task SET assign_to=?, description=?, status=? WHERE id=?";

        try (Connection conn = ConnectionFactory.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            setAssignTo(stmt, 1, t.getAssignTo());
            stmt.setString(2, t.getDescription());
            stmt.setString(3, t.getStatus().name());
            stmt.setInt(4, t.getId());

            return stmt.executeUpdate() > 0 ? t : null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Task> findAll() {
        String sql = "SELECT * FROM task ORDER BY id";
        List<Task> tasks = new ArrayList<>();

        try (Connection conn = ConnectionFactory.connect(); PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) tasks.add(map(rs));
            return tasks;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setAssignTo(PreparedStatement stmt, int index, Integer assignTo) throws SQLException {
        if (assignTo == null) stmt.setNull(index, Types.INTEGER);
        else stmt.setInt(index, assignTo);
    }

    private Task map(ResultSet rs) throws SQLException{
        int assignTo = rs.getInt("assign_to");
        Task task = new Task(rs.wasNull() ? null : assignTo, rs.getString("title"), rs.getString("description"));
        task.setId(rs.getInt("id"));
        task.setStatus(TaskStatus.valueOf(rs.getString("status")));
        return task;
    }
}