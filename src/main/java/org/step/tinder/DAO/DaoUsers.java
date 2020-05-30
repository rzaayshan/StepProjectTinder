package org.step.tinder.DAO;

import lombok.SneakyThrows;
import org.step.tinder.entity.Profile;
import org.step.tinder.entity.User;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;

public class DaoUsers {

    private final Connection conn;

    public DaoUsers(Connection conn) {
        this.conn = conn;
    }

    @SneakyThrows
    public boolean checkUser(String uname, String pass) {
        String query = "select uname,pass from users;";
        PreparedStatement st = conn.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        HashMap<String, String> users = new HashMap<>();
        while (rs.next()){
            users.put(rs.getString("uname"),rs.getString("pass"));
        }
        return users.containsKey(uname) && users.get(uname).equals(pass);

    }

    public void addUser(User user) {
        try {
            String query = "INSERT INTO users VALUES (?,?,?,?,?)";
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, user.getId());
            st.setString(2, user.getName());
            st.setString(3, user.getPass());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @SneakyThrows
    public void deleteUser(int id) {
        String query = "DELETE FROM users WHERE id=?";
        PreparedStatement st = conn.prepareStatement(query);
        st.setInt(1, id);
        st.executeUpdate();
    }

    @SneakyThrows
    LinkedList<Profile> getProfiles() {
        LinkedList<Profile> users = new LinkedList<>();
        String query = "SELECT * FROM users";
        PreparedStatement st = conn.prepareStatement(query);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            String uname = rs.getString("uname");
            String image = rs.getString("image");
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            users.add(new Profile(uname, image, name, surname));
        }
        return users;
    }

    @SneakyThrows
    public Profile getProfile(String who) {
        String query = "SELECT * FROM users WHERE uname=?";
        PreparedStatement st = conn.prepareStatement(query);
        st.setString(1,who);
        ResultSet rs = st.executeQuery();

        rs.next();

        String uname = rs.getString("uname");
        String image = rs.getString("image");
        String name = rs.getString("name");
        String surname = rs.getString("surname");

        return new Profile(uname,image,name,surname);


    }


}
