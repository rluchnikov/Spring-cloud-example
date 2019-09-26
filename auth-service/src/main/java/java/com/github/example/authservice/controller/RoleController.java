package java.com.github.example.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.com.github.example.authservice.entity.Role;
import java.com.github.example.authservice.repository.RoleRepository;

import java.util.List;

@RestController
public class RoleController {

    @Autowired
    private RoleRepository roles;

    @RequestMapping(value="/roles", method = RequestMethod.GET)
    public List listRoles(){
        return roles.findAll();
    }

    @RequestMapping(value = "/role", method = RequestMethod.POST)
    public Role create(@RequestBody Role role){
        return roles.save(role);
    }



}
