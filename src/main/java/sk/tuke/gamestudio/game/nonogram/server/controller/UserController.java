package sk.tuke.gamestudio.game.nonogram.server.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.game.nonogram.entity.User;
import sk.tuke.gamestudio.game.nonogram.service.UserService;

@Controller
@RequestMapping("/user")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController {

    @Autowired
    private UserService user;



    private User loggedUser;
    private boolean registerState = false;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login(String login, String password) {
            if (user.getUser(login, password) != null) {
                loggedUser = user.getUser(login, password);
                return "redirect:/nonogram";
            }else {
                return "redirect:/";
            }

    }

    @RequestMapping("/signup")
    public String signUp(String login, String password, String vpassword) {
        if(login != null && password != null && user.getUser(login, password) == null
                && password.equals(vpassword)){
            user.addUser(new User(login, password));
            registerState = false;
        }

        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout() {
        loggedUser = null;
        return "redirect:/";
    }

    @RequestMapping("/register")
    public String register() {
        registerState = !registerState;
        return "redirect:/";
    }

    public boolean isRegister() {
        return registerState;
    }



    public User getLoggedUser() {
        return loggedUser;
    }

    public boolean isLogged() {
        return loggedUser != null;
    }
}
