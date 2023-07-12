package com.example.demo.Controllers.Auth0.Auth0Classes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserAuth0 {
    private String userID;
    private String name;
    private String username;
    private String rolID;
    private String connection;
    private String email;
    private String phone_number;
    private String given_name;
    private String family_name;
    private String password;
}
