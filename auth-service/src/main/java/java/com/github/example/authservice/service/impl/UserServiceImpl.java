package java.com.github.example.authservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import java.com.github.example.authservice.entity.User;
import java.com.github.example.authservice.exception.NotFoundException;
import java.com.github.example.authservice.repository.UserRepository;
import java.com.github.example.authservice.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository repository;

    @Override
    public User create(User user) {

        User existing = repository.findByUsername(user.getUsername());
        Assert.isNull(existing, "user already exists: " + user.getUsername());

        String hash = encoder.encode(user.getPassword());
        user.setPassword(hash);

        User newuser = repository.save(user);
        log.info("new user has been created: {}", newuser.getUsername());
        return newuser;
    }

    @Override
    public String findbyName(String username){
      User user =  repository.findByUsername(username);
      return user.getLocation();
    }

    @Override
    @Transactional(readOnly = true)
    public List findAll() {
        List list = new ArrayList<>();
        repository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public User update(Integer id, User user) {
        User olduser = repository.findOne(id);
        if (user != null) user.setId(olduser.getId());
        return repository.save(user);
    }

    @Override
    public User findUserById(Integer id){
        return repository.findById(id).orElseThrow(
              () -> new NotFoundException("User","id",id));
    }
}
