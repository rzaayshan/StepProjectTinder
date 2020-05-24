package org.step.tinder.Helpers;

public class Profile {
    String uname;
    String image;
    String name;
    String surname;

    public Profile(String uname, String image, String name, String surname) {
        this.uname = uname;
        this.image = image;
        this.name = name;
        this.surname = surname;
    }

    @Override
    public String toString() {
        return String.format("Profile{name='%s', image='%s'}", name, image);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
