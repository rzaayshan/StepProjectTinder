package org.step.tinder.DAO;

import lombok.SneakyThrows;
import org.step.tinder.entity.Profile;



import java.sql.*;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class DaoLikes {
    private final Connection conn;

    public DaoLikes(Connection conn) {
        this.conn = conn;
    }


    @SneakyThrows
    public LinkedList<Profile> getLikes(String who, boolean isLike){
        DaoUsers daoUsers = new DaoUsers(conn);
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

    @SneakyThrows
    public void addLikes(String who, String whom){
        String query = "INSERT INTO likes(who,whom) VALUES (?,?)";
        PreparedStatement st = conn.prepareStatement(query);
        st.setString(1,who);
        st.setString(2,whom);
        st.executeUpdate();
    }


}
