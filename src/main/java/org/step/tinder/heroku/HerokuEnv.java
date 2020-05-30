package org.step.tinder.heroku;

public class HerokuEnv implements HerokuEnvVars{

  private static final String EMPTY = " is empty!!!";

  private final static IllegalArgumentException empty_url =
          new IllegalArgumentException(JDBC_DATABASE_URL+EMPTY);
  private final static IllegalArgumentException empty_username =
          new IllegalArgumentException(JDBC_DATABASE_USERNAME+EMPTY);
  private final static IllegalArgumentException empty_password =
          new IllegalArgumentException(JDBC_DATABASE_PASSWORD+EMPTY);

  public static int port() {
    try {
      return Integer.parseInt(System.getenv(PORT));
    } catch (NumberFormatException ex) {
      return 5000;
    }
  }

  public static String jdbc_url(){
    String val = System.getenv(JDBC_DATABASE_URL);
    if(val==null) throw empty_url;
    return val;
  }

  public static String jdbc_username(){
    String val = System.getenv(JDBC_DATABASE_USERNAME);
    if(val==null) throw empty_username;
    return val;
  }

  public static String jdbc_password(){
    String val = System.getenv(JDBC_DATABASE_PASSWORD);
    if(val==null) throw empty_password;
    return val;
  }

}
