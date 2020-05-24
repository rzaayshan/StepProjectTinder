package org.step.tinder.DAO;

import org.step.tinder.Helpers.Profile;
import org.step.tinder.db.ConnDetails;


import java.sql.*;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class DaoLikes {
    private static final String URL = ConnDetails.url;
    private static final String UNAME = ConnDetails.username;
    private static final String PWD = ConnDetails.password;

    private Connection conn;

    public void connect(){
        try {
            this.conn = DriverManager.getConnection(URL, UNAME, PWD);
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public LinkedList<Profile> getLikes(String who, boolean isLike){
        try{
            DaoUsers daoUsers = new DaoUsers();
            daoUsers.connect();
            LinkedList<Profile> profiles = daoUsers.getProfiles();
            String query = "SELECT * FROM likes WHERE who=?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1,who);
            ResultSet rs = st.executeQuery();
            LinkedList<String> likes = new LinkedList<>();
            while (rs.next()){
                likes.add(rs.getString("whom"));

            }
            if(!isLike)
                return profiles.stream().filter(p->!likes.contains(p.getUname()) && !p.getUname().equals(who))
                        .collect(Collectors.toCollection(LinkedList::new));
            return profiles.stream().filter(p->likes.contains(p.getUname()))
                    .collect(Collectors.toCollection(LinkedList::new));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void addLikes(String who, String whom){
        try{
            String query = "INSERT INTO likes(who,whom) VALUES (?,?)";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1,who);
            st.setString(2,whom);
            st.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }


}
