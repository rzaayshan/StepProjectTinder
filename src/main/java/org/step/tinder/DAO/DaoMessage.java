package org.step.tinder.DAO;

import org.step.tinder.Helpers.Message;
import org.step.tinder.db.ConnDetails;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class DaoMessage {
    private static final String URL = ConnDetails.url;
    private static final String UNAME = ConnDetails.username;
    private static final String PWD = ConnDetails.password;

    public DaoMessage(){

    }
    public void addMessage(String from, String to, String mes){
        String time = LocalDateTime.now().toString();

        try(Connection conn = DriverManager.getConnection(URL,UNAME,PWD)){
            String query = "INSERT INTO messages(sender, receiver, message, time) VALUES (?,?,?,?)";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1,from);
            st.setString(2,to);
            st.setString(3,mes);
            st.setString(4,time);
            st.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public LinkedList<Message> getMessages(String sender, String receiver){
        try(Connection conn = DriverManager.getConnection(URL,UNAME,PWD)){
            String query = "SELECT * FROM messages WHERE ((sender=? AND receiver=?) OR (sender=? AND receiver=?))";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1,sender);
            st.setString(2,receiver);
            st.setString(3,receiver);
            st.setString(4,sender);
            ResultSet rs = st.executeQuery();
            LinkedList<Message> messages = new LinkedList<>();
            while (rs.next()){
                String from = rs.getString("sender");
                String to = rs.getString("receiver");
                LocalDateTime time = LocalDateTime.parse(rs.getString("time"));
                String message = rs.getString("message");
                messages.add(new Message(from,to,time,message));
            }
            return messages.stream().sorted(Comparator.comparing(Message::getTime)).collect(Collectors.toCollection(LinkedList::new));
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }


}
