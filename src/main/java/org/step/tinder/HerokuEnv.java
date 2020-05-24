package org.step.tinder;

public class HerokuEnv {

    public static int port(){
        try{
            String port = System.getenv("PORT");
            return Integer.parseInt(port);
        }
        catch (NumberFormatException ex){
            return 5000;
        }

    }
}
