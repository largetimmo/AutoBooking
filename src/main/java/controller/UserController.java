package controller;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import pojo.User;
import service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);
    private UserService userManagement;
    public UserController(UserService userManagement) {
        this.userManagement = userManagement;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public @ResponseBody String addUser(@ModelAttribute User user){
        //todo 1
        logger.debug("Request: user reg");
        userManagement.addUser(user);
        return "Added";
    }
    @RequestMapping(method = RequestMethod.DELETE ,value = "/{id}")
    public @ResponseBody String removeUser(@PathVariable("id") int id){
        logger.debug("Receive delete user request, ID:"+id);
        userManagement.removeUser(id);
        return "Removed";
    }
    @RequestMapping(method = RequestMethod.GET,value = "/count")
    public @ResponseBody String count(){
        logger.debug("Request: count");
        return userManagement.countUser().toString();
    }
    @RequestMapping(method = RequestMethod.GET,value = "")
    public @ResponseBody String getallAccountsDetail(){
        logger.debug("Request: all accounts detail");
        return "Not implemented yet";
    }
}
