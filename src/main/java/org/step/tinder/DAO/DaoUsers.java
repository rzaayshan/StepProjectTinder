package org.step.tinder.DAO;

import org.step.tinder.Helpers.Profile;
import org.step.tinder.Helpers.User;
import org.step.tinder.db.ConnDetails;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;

public class DaoUsers {
    private static final String URL = ConnDetails.url;
    private static final String UNAME = ConnDetails.username;
    private static final String PWD = ConnDetails.password;

    private Connection conn;

    public void connect() {
        try {
            this.conn = DriverManager.getConnection(URL, UNAME, PWD);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
            if(users.containsKey(uname) && users.get(uname).equals(pass))
                return true;
            return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
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

    public void deleteUser(int id) {
        try {
            String query = "DELETE FROM users WHERE id=?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    LinkedList<Profile> getProfiles() {
        try {
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    public Profile getProfile(String who) {
        try {

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

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }


}
