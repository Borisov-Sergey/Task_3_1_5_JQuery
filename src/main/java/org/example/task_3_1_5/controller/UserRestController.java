package org.example.task_3_1_5.controller;


import org.example.task_3_1_5.dto.UserDTO;
import org.example.task_3_1_5.service.RoleService;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/")
public class UserRestController {

    private final UserService userService;
    private final RegistrationServiceImpl registrationService;
    private final RoleService roleService;

    @Autowired
    public UserRestController(UserService userService, RegistrationServiceImpl registrationService, RoleService roleService) {
        this.userService = userService;
        this.registrationService = registrationService;
        this.roleService = roleService;
    }


    @GetMapping(value = "admin")
    public ModelAndView getAdminPage(@AuthenticationPrincipal SecurityUserDetails userDetails) {
        ModelAndView model = new ModelAndView("index");
        model.addObject("userActive", userDetails.getUser());
        if (userDetails.getUser().getRoles().toString().contains("ADMIN")) {
            model.addObject("roleBoolean", true);
        }
        return model;
    }

    @GetMapping(value = "user")
    public ModelAndView getUserPage(@AuthenticationPrincipal SecurityUserDetails userDetails) {
        ModelAndView model = new ModelAndView("userPage");
        model.addObject("user", userDetails.getUser());
        if (userDetails.getUser().getRoles().toString().contains("ADMIN")) {
            model.addObject("roleBoolean", true);
        }
        return model;
    }

    @GetMapping(value = "account")
    public ResponseEntity<User> getAuthUser(@AuthenticationPrincipal SecurityUserDetails userDetails) {
        return new ResponseEntity<>(userDetails.getUser(), HttpStatus.OK);
    }

    @GetMapping(value = "admin/list")
    public ResponseEntity<List<User>> getListUser() {
        List<User> users = userService.getAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "admin/get")
    public ResponseEntity<User> getUserById(@RequestParam(value = "id", required = false) Long id) {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @GetMapping("admin/roles")
    public List<Role> getRoles () {
        return roleService.getAllRoles();
    }

    @PutMapping(value = "admin")
    public ResponseEntity<User> putUpdateUser(@RequestBody UserDTO userDTO) {
        User user = new User(userDTO);
        Set<Role> roles = new HashSet<>();
        for (String role : userDTO.getRoles()) {
            roles.add(roleService.findByName(role));
        }
        user.setRoles(roles);
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
    public ResponseEntity<User> postNewUser(@RequestBody UserDTO userDTO) {
        User user = new User(userDTO);
        Set<Role> roles = new HashSet<>();
        for (String role : userDTO.getRoles()) {
            roles.add(roleService.findByName(role));
        }
        user.setRoles(roles);
        registrationService.register(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}