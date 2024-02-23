package org.example.task_3_1_5.dto;

import org.example.task_3_1_5.model.Role;

import java.util.Set;

public class UserDTO {
    private Long id;
    private String email;
    private int age;
    private String userName;
    private String lastName;
    private String password;
    private Set<Role> roles;

    public UserDTO() {
    }

    public UserDTO(Long id, String userName, String lastName, int age, String email, String password, Set<Role> roles) {
        this.id = id;
        this.email = email;
        this.age = age;
        this.userName = userName;
        this.lastName = lastName;
        this.password = password;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
