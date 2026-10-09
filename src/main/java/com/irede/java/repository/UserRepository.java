package com.irede.java.repository;

import com.irede.java.config.ConnectionFactory;
import com.irede.java.models.DeveloperUser;
import com.irede.java.models.ProjectOwner;
import com.irede.java.models.User;
import com.irede.java.utils.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements Repository<User>{

    public User getByName(String name){
        String sql = "SELECT * FROM user WHERE user.name=?";
        try (Connection conn = ConnectionFactory.connect(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()){
                return rs.next() ? map(rs) : null;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> findAll() {
        String sql = "SELECT * FROM user ORDER BY name";
        List<User> users = new ArrayList<>();
        try (Connection conn = ConnectionFactory.connect(); PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) users.add(map(rs));
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getById(int id) {
        String sql = "SELECT * FROM user WHERE user.id=?";
        try (Connection conn = ConnectionFactory.connect(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()){

                return rs.next() ? map(rs) : null;

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User delete(User t) {
        int id = t.getId();
        String sql = "DELETE FROM user WHERE user.id=?";

        try (Connection conn = ConnectionFactory.connect(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();

            return affectedRows>0 ? t : null;

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public User update(User t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void add(User user){
        String sql = "INSERT INTO user (name, email, password, role) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.connect(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, String.valueOf(user.getRole()));

            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    user.setId(keys.getInt(1));
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private User map(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        String password = rs.getString("password");
        Role role = Role.valueOf(rs.getString("role"));

        return switch (role){
            case PROJECTOWNER -> new ProjectOwner(id, name, email, password);
            case DEVELOPER -> new DeveloperUser(id, name, email, password);
        };
    }
}
