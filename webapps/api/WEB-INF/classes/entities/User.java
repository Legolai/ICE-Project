package entities;

public class User {
    private int user_id;
    private String name;
    private String username;
    private String email;

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
