package org.step.tinder.DAO;


import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;

import org.step.tinder.entity.User;
import org.step.tinder.entity.Like;


@Log4j2
public class DaoLikes implements DAO<Like> {
    private final Connection conn;
    private final String SQL_getAll = "SELECT * FROM likes";
    private final String SQL_get    = "SELECT * FROM likes WHERE who=?";
    private final String SQL_put    = "INSERT INTO likes(who, whom) VALUES (?,?)";
    private final String SQL_delete = "DELETE FROM likes WHERE id=?";



    public DaoLikes(Connection conn) {
        this.conn = conn;
    }

    public LinkedList<User> getLikes(String who, boolean isLike){
        try{
            DaoUsers daoUsers = new DaoUsers(conn);
            LinkedList<User> profiles = daoUsers.getAll();
            PreparedStatement st = conn.prepareStatement(SQL_get);
            st.setString(1,who);
            ResultSet rs = st.executeQuery();
            LinkedList<String> likes = new LinkedList<>();
            while (rs.next()){
                likes.add(rs.getString("whom"));
            }
            Predicate<User> pred;

            if(!isLike){
                pred = p -> !likes.contains(p.getUname()) && !p.getUname().equals(who);
            }
            else{
                pred = p -> likes.contains(p.getUname());
            }

            return profiles.stream().filter(pred).collect(Collectors.toCollection(LinkedList::new));
        }catch (SQLException throwables) {
            log.error("Likes can't be found");
            return new LinkedList<>();
        }
    }


    @Override
    public List<Like> getAll() {
        try {
            LinkedList<Like> messages = new LinkedList<>();
            PreparedStatement st = conn.prepareStatement(SQL_getAll);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Optional<Like> message = SQLData(rs);
                message.ifPresent(messages::add);
            }
            return messages;
        } catch (SQLException throwables) {
            log.error("Messages can't be found");
            return new LinkedList<>();
        }
    }

    @Override
    public <T> Optional<Like> get(T data) {
        return Optional.empty();
    }


    private Optional<Like> SQLData(ResultSet rs) {
        try {
            String who = rs.getString("who");
            String whom = rs.getString("whom");
            return Optional.of(new Like(who,whom));
        } catch (SQLException throwables) {
            log.error("Columns can't be found");
            return Optional.empty();
        }
    }

    @Override
    public void put(Like like) {
        try{
            PreparedStatement st = conn.prepareStatement(SQL_put);
            st.setString(1,like.getWho());
            st.setString(2,like.getWhom());
            st.executeUpdate();
        }catch (SQLException throwables) {
            log.error("Like can't be added");
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement st = conn.prepareStatement(SQL_delete);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException throwables) {
            log.error("Like can't be deleted");
        }

    }
}
