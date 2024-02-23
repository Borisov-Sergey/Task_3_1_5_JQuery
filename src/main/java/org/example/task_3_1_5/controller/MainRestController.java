package org.example.task_3_1_5.controller;


import org.example.task_3_1_5.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.example.task_3_1_5.model.Role;
import org.example.task_3_1_5.model.User;
import org.example.task_3_1_5.security.SecurityUserDetails;
import org.example.task_3_1_5.service.RegistrationServiceImpl;
import org.example.task_3_1_5.service.UserService;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/")
public class MainRestController {

    private final UserService userService;
    private final RegistrationServiceImpl registrationService;

    @Autowired
    public MainRestController(UserService userService, RegistrationServiceImpl registrationService) {
        this.userService = userService;
        this.registrationService = registrationService;
    }


    @GetMapping(value = "admin")
    public ModelAndView adminAccount(@AuthenticationPrincipal SecurityUserDetails userDetails) {
        ModelAndView model = new ModelAndView("index");
        model.addObject("userActive", userDetails.getUser());
        if (userDetails.getUser().getRoles().contains(Role.ADMIN)) {
            model.addObject("roleBoolean", true);
        }
        return model;
    }

    @GetMapping(value = "user")
    public ModelAndView userAccount(@AuthenticationPrincipal SecurityUserDetails userDetails) {
        ModelAndView model = new ModelAndView("userPage");
        model.addObject("user", userDetails.getUser());
        if (userDetails.getUser().getRoles().contains(Role.ADMIN)) {
            model.addObject("roleBoolean", true);
        }
        return model;
    }

    @GetMapping(value = "account")
    public ResponseEntity<User> getAuthUser(@AuthenticationPrincipal SecurityUserDetails userDetails) {
        return new ResponseEntity<>(userDetails.getUser(), HttpStatus.OK);
    }

    @GetMapping(value = "admin/list")
    public ResponseEntity<List<User>> allUser() {
        List<User> users = userService.getAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "admin/get")
    public ResponseEntity<User> getUserById(@RequestParam(value = "id", required = false) Long id) {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @GetMapping("admin/roles")
    public List<Role> getRoles () {
        return new ArrayList<>(List.of(Role.values()));
    }

    @PutMapping(value = "admin")
    public ResponseEntity<User> updateUser(@RequestBody UserDTO userDTO) {
        User user = new User(userDTO);
        user.setId(userDTO.getId());
        userService.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "admin")
    public ResponseEntity<User> deleteUser(@RequestParam(value = "id", required = false) Long id) {
        userService.removeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "admin")
    public ResponseEntity<User> newUser(@RequestBody UserDTO userDTO) {
        User user = new User(userDTO);
        registrationService.register(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}