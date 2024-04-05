package com.example.demo.test;
import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.*;
import java.sql.*;

@Controller
@CrossOrigin(origins="*")

public class test {
    String jdbcurl = "jdbc:mysql://127.0.0.1:3306/tracker_db" + "";

    @GetMapping("/start")
    public String start() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "signup";
    }

    //signin to db data transfer----->
    @PostMapping(value = "/signedup", produces = "text/html")
    public String signedup(@RequestParam("Email") String Email, @RequestParam("Username") String Username, @RequestParam("Password") String Password, @RequestParam("CPassword") String CPassword) {
        System.out.println("Inside signin method");

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(jdbcurl, "root", "root");
            String sql = "insert into users(name, email, password) VALUES (?,?,?)";
            Statement statement = connection.prepareStatement(sql);
            PreparedStatement state = connection.prepareStatement(sql);
            state.setString(1, Username);
            state.setString(2, Email);
            state.setString(3, Password);
            state.execute();
            connection.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return "login";
    }

    @GetMapping("/features")
    public String features()
    {
        return "feature";
    }
    @GetMapping("/checkout")
    public String checkoutt(){
        return "checkout";
    }
    @PostMapping(value = "/login", produces = "text/html")
    public String login(@RequestParam("Username") String Username, @RequestParam("Password") String Password) {
        System.out.println("Inside submit method");

        try (Connection connection = DriverManager.getConnection(jdbcurl, "root", "root")) {
            String sql = "SELECT password FROM users WHERE name=?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, Username);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        String storedPassword = rs.getString("password");
                        if (Password.equals(storedPassword)) {
                            return "dashboard";
                        } else {
                            System.out.println("Wrong password");
                            return "login";
                        }
                    } else {
                        System.out.println("User not found");
                        return "login";
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Exception: " + e.getMessage());
            return "login";
        }
    }
}