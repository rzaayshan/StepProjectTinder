package org.step.tinder.entity;

import java.util.Base64;

public class Crip {
    public static String encode(String val){
        return Base64.getEncoder().encodeToString(val.getBytes());
    }

    public static String decode(String val){
        return new String(Base64.getDecoder().decode(val));
    }
}
