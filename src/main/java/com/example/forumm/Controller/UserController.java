package com.example.forumm.Controller;

import com.example.forumm.Entity.User;
import com.example.forumm.Service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    IUserService userService;

    @PostMapping("/addUser")
    @ResponseBody
    public User addUser(@RequestBody User user){

        return userService.addUser(user);
    }

    @DeleteMapping("/deleteUser/{userId}")
    @ResponseBody
    public void deleteUser(@PathVariable("userId") Long userId) {

        userService.deleteUser(userId);
    }

    @GetMapping("/getAllUsers")
    @ResponseBody
    public List<User> getAllUsers() {
        List<User> listUsers = userService.getAllUsers();
        return listUsers;
    }

    @GetMapping("/getUser/{userId}")
    @ResponseBody
    public User getUser(@PathVariable("userId") Long userId) {
        return userService.getUser(userId);
    }
}
