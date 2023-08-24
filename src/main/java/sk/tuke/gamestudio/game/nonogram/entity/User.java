package sk.tuke.gamestudio.game.nonogram.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "player")
public class User {
    private String login;
    private String password;
    @Id
    @GeneratedValue
    private int ident;

    public int getIdent() {
        return ident;
    }


    public void setIdent(int ident) {
        this.ident = ident;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User() {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
