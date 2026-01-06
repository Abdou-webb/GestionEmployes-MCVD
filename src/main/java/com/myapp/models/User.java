package com.myapp.models;

import org.mindrot.jbcrypt.BCrypt;

public class User {
    private String username;
    private String password;
    private String role; // ADMIN ou EMPLOYEE
    private int employeeId;

    public User(String username, String password, String role, int employeeId, boolean isHashed) {
        this.username = username;
        this.role = role;
        this.employeeId = employeeId;
        if (isHashed) {
            this.password = password;
        } else {
            this.password = org.mindrot.jbcrypt.BCrypt.hashpw(password, org.mindrot.jbcrypt.BCrypt.gensalt());
        }
    }


    public boolean checkPassword(String candidate) {

        // this.password est le hash stocké dans le CSV
        return BCrypt.checkpw(candidate, this.password);
    }

    // Getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public int getEmployeeId() { return employeeId; }
}