package org.step.tinder.DAO;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.step.tinder.entity.Message;
import org.step.tinder.entity.User;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
public class DaoMessage implements DAO<Message>{
    private final Connection conn;
    private final String SQL_getAll = "SELECT * FROM messages";
    private final String SQL_get    = "SELECT * FROM messages WHERE ((sender=? AND receiver=?) OR (sender=? AND receiver=?))";
    private final String SQL_put    = "INSERT INTO messages(sender, receiver, message, time) VALUES (?,?,?,?)";
    private final String SQL_delete = "DELETE FROM messages WHERE id=?";
    private final String SQL_getID    = "SELECT * FROM messages WHERE id=?";

    public DaoMessage(Connection conn) {
        this.conn = conn;
    }

    public LinkedList<Message> getMessages(String sender, String receiver){
        try{
            PreparedStatement st = conn.prepareStatement(SQL_get);
            st.setString(1,sender);
            st.setString(2,receiver);
            st.setString(3,receiver);
            st.setString(4,sender);
            ResultSet rs = st.executeQuery();
            LinkedList<Message> messages = new LinkedList<>();
            while (rs.next()){
                Optional<Message> message = SQLData(rs);
                message.ifPresent(messages::add);
            }
            return messages.stream().sorted(Comparator.comparing(Message::getTime))
                    .collect(Collectors.toCollection(LinkedList::new));
        }catch (SQLException throwables) {
            log.error("Messages can't be found");
            return new LinkedList<>();
        }
    }

    @Override
    public List<Message> getAll() {
        try {
            LinkedList<Message> messages = new LinkedList<>();
            PreparedStatement st = conn.prepareStatement(SQL_getAll);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Optional<Message> message = SQLData(rs);
                message.ifPresent(messages::add);
            }
            return messages;
        } catch (SQLException throwables) {
            log.error("Messages can't be found");
            return new LinkedList<>();
        }
    }

    @Override
    public <T> Optional<Message> get(T data) {
        try{
            PreparedStatement st = conn.prepareStatement(SQL_getID);
            st.setInt(1, (Integer) data);
            ResultSet rs = st.executeQuery();
            rs.next();
            return SQLData(rs);
        } catch (SQLException throwables) {
            log.error("Profile can't be found to get");
            return Optional.empty();
        }
    }

    private Optional<Message> SQLData(ResultSet rs) {
        try {
            String from = rs.getString("sender");
            String to = rs.getString("receiver");
            LocalDateTime time = LocalDateTime.parse(rs.getString("time"));
            String message = rs.getString("message");
            return Optional.of(new Message(from,to,time,message));
        } catch (SQLException throwables) {
            log.error("Columns can't be found");
            return Optional.empty();
        }
    }

    @Override
    public void put(Message message) {
        try{
            PreparedStatement st = conn.prepareStatement(SQL_put);
            st.setString(1,message.getFrom());
            st.setString(2,message.getTo());
            st.setString(3,message.getMes());
            st.setString(4,message.getTime().toString());
            st.executeUpdate();
        }catch (SQLException throwables) {
            log.error("Messages can't be added");
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement st = conn.prepareStatement(SQL_delete);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException throwables) {
            log.error("Message can't be deleted");
        }
    }
}
