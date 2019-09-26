package java.com.github.example.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.com.github.example.authservice.dto.UserDto;
import java.com.github.example.authservice.entity.Role;
import java.com.github.example.authservice.entity.User;
import java.com.github.example.authservice.repository.RoleRepository;
import java.com.github.example.authservice.service.UserService;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roles;

    @GetMapping(value="/users")
    public List listUser(){
        return userService.findAll();
    }

    @PostMapping(value = "/user")
    public User create(@RequestBody UserDto user){
        Role role = roles.findOne(user.getRole());
      return userService.create(User.builder().username(user.getUsername())
        .firstName(user.getFirstname()).lastName(user.getLastname()).middleName(user.getMiddlename())
        .birthday(LocalDate.parse(user.getBirthday(), DateTimeFormatter.ofPattern("dd-MM-yyyy"))).appointment_date(LocalDate.parse(user.getAppointment_date(),DateTimeFormatter.ofPattern("dd-MM-yyyy")))
               .mobile(user.getMobile()).location(user.getLocation()).password(user.getPassword())
        .role(role).build());
    }

    @PutMapping(value = "/user/{id}")
    public User update(@PathVariable("id") Integer id,
                       @RequestBody User user){
        return userService.update(id,user);
    }

    @GetMapping(value = "/user/{id}")
    public User find(@PathVariable("id") Integer id){
        return userService.findUserById(id);
    }

    @GetMapping(value = "/location")
    public String getUser( @RequestParam(value = "username")  String username) {
        return userService.findbyName(username);
    }

    @GetMapping("/user")
    public Principal principal(Principal principal) {
        return principal;
    }

}
