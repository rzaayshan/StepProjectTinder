package org.step.tinder.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private int id;
    private String uname;
    private String pass;
    private String name;
    private String surname;
    private String image;

    public User(String uname, String image, String name, String surname) {
        this.uname=uname;
        this.image=image;
        this.name=name;
        this.surname=surname;
    }

    public static User Profile(String uname,String image,String name,String surname){
        return new User(uname,image,name,surname);
    }
}
