package org.step.tinder.Helpers;

public class User {
    private int id;
    private String uname;
    private String pass;
    private String name;
    private String surname;
    private String image;

    public User(){

    }

    public User(int id, String uname, String pass, String name, String surname, String image) {
        this.id = id;
        this.uname = uname;
        this.pass = pass;
        this.name = name;
        this.surname = surname;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
