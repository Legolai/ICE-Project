package entities;

public class User {
    private String name;
    private String username;
    private String email;
    private String password;

    public User() {
        name = "";
        username = "";
        email = "";
        password = "";
    }
    
    public void fillUser(String info) {
        String[] splitted = info.split(";");

        name = splitted[0];
        username = splitted[1];
        email = splitted[2];
        password = splitted[3];
    }

    public String userToString() {
        String res = "";

        res += name+";";
        res += username+";";
        res += email+";";
        res += password;

        return res;
    }


}
