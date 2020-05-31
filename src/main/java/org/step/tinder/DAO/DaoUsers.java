package org.step.tinder.DAO;


import lombok.extern.log4j.Log4j2;
import org.step.tinder.entity.User;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;

@Log4j2
public class DaoUsers implements DAO<User>{

    private final Connection conn;
    private final String SQL_getAll = "SELECT * FROM users";
    private final String SQL_get    = "SELECT * FROM users WHERE uname=?";
    private final String SQL_put    = "INSERT INTO users(uname, pass, image, name, surname) VALUES (?,?,?,?,?)";
    private final String SQL_delete = "DELETE FROM users WHERE id=?";

    public DaoUsers(Connection conn) {
        this.conn = conn;
    }

    public boolean checkUser(String uname, String pass) {
        try {
            String query = "select uname,pass from users;";
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            HashMap<String, String> users = new HashMap<>();
            while (rs.next()){
                users.put(rs.getString("uname"),rs.getString("pass"));
            }
            return users.containsKey(uname) && users.get(uname).equals(pass);
        } catch (SQLException throwables) {
            log.error("User can't be checked");
            return false;
        }
    }

    @Override
    public LinkedList<User> getAll() {
        try {
            LinkedList<User> users = new LinkedList<>();
            PreparedStatement st = conn.prepareStatement(SQL_getAll);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Optional<User> profile = SQLData(rs);
                profile.ifPresent(users::add);
            }
            return users;
        } catch (SQLException throwables) {
            log.error("Users can't be found");
            return new LinkedList<>();
        }
    }

    @Override
    public <T> Optional<User> get(T data) {
        try{
            PreparedStatement st = conn.prepareStatement(SQL_get);
            st.setString(1, (String) data);
            ResultSet rs = st.executeQuery();
            rs.next();
            return SQLData(rs);
        } catch (SQLException throwables) {
            log.error("Profile can't be found to get");
            return Optional.empty();
        }
    }

    private Optional<User> SQLData(ResultSet rs) {
        try {
            String uname = rs.getString("uname");
            String image = rs.getString("image");
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            return Optional.of(User.Profile(uname,image,name,surname));
        } catch (SQLException throwables) {
            log.error("Columns can't be found");
            return Optional.empty();
        }
    }

    @Override
    public void put(User user) {
        try {
            PreparedStatement st = conn.prepareStatement(SQL_put);
            st.setString(1, user.getUname());
            st.setString(2, user.getPass());
            st.setString(3, user.getImage());
            st.setString(4, user.getName());
            st.setString(5, user.getSurname());
            st.executeUpdate();
        } catch (SQLException throwables) {
            log.error("User can't be added");
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement st = conn.prepareStatement(SQL_delete);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException throwables) {
            log.error("User can't be deleted");
        }
    }
}
