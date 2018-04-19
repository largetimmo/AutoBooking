package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pojo.User;
import service.UserManagement;
import serviceImpl.UserManagementImpl;

@Controller
public class MainController {
    @Autowired
    UserManagementImpl userManagement;

    @RequestMapping("/addUser")
    public String addUser(User user){
        userManagement.addUser(user);
        return "index";
    }

}
