package com.irede.java.repository;

import com.irede.java.config.ConnectionFactory;
import com.irede.java.models.DeveloperUser;
import com.irede.java.models.ProjectOwner;
import com.irede.java.models.User;
import com.irede.java.utils.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository implements Repository<User>{

    @Override
    public User getById(int id) {
        String sql = "SELECT * FROM user WHERE user.id=?";
        try (Connection conn = ConnectionFactory.conect(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            try (ResultSet response = stmt.executeQuery()){
                if ( !response.next() ) return null;

                Role role = Role.valueOf(response.getString("role"));
                int uid = response.getInt("id");
                String name = response.getString("name");
                String email = response.getString("email");
                String password = response.getString("password");

                return switch(role){
                    case DEVELOPER -> new DeveloperUser(uid, name, email, password);
                    case PROJECTOWNER -> new ProjectOwner(uid, name, email, password);
                };
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User delete(User t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public User update(User t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void add(User user){
        String sql = "INSERT INTO user (name, email, password, role) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.conect(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, String.valueOf(user.getRole()));

            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
