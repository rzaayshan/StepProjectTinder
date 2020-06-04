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
}
