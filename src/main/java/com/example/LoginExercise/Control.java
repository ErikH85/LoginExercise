package com.example.LoginExercise;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.*;

@Controller
public class Control {


    User user1 = new User("Erik", "123");

    //Cookie cookie = new Cookie("passKey", "Access");

    @GetMapping("/form")
    public String getForm(){

        return "form";
    }

    @PostMapping("/form")
    public String postForm(
           @RequestParam(name="user", required = false, defaultValue = "defaultTest") String username,
           @RequestParam(name="pw", required = false, defaultValue = "defaultTest") String password,
           HttpServletRequest request){

        HttpSession session = request.getSession(true);

        if(username.equals(user1.getUsername()) && password.equals(user1.getPassword())){
            session.setAttribute("loggedIn","true");
            return "redirect:secret";        }
        else{
            session.setAttribute("loggedIn", "false");
        }
        return "form";
    }

    @GetMapping("/secret")
    public String validate(HttpServletRequest request){

        HttpSession session = request.getSession(true);
        if(session.getAttribute("loggedIn") != null && ((String)session.getAttribute("loggedIn")).equals("true")){
            return "secret";
        }
        else {
            return "redirect:form";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        session.setAttribute("loggedIn","false");
        session.invalidate();
        return "redirect:form";
    }

}
