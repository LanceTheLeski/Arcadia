package com.springtester.springdemo.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@EnableJpaRepositories
@RestController
class UserProfileController {

    @Autowired
    public UserProfileRepository userProfilesRepository;

    @PostMapping("/users/new")
    public String saveUser(@RequestBody UserProfile user) {
        userProfilesRepository.save(user);
        return "New user "+ user.getFirstName() + " Saved";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/users")
    public List<UserProfile> getAllUsers() {
        List<UserProfile> results = userProfilesRepository.findAll();
        return results;
    }

}
