package org.step.tinder.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Profile {
    String uname;
    String image;
    String name;
    String surname;
}
