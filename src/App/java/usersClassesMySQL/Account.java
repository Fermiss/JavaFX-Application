package usersClassesMySQL;

public class Account {
    private int id;
    private String login;
    private String password;
    private String role;
    public Account(String login, String password, String role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public Account(int id, String login, String password, String role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public Account(){
        login = null;
        password = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role_id) {
        this.role = role_id;
    }

}
