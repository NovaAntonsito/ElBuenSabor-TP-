package com.example.demo.Controllers.Auth0;

import com.example.demo.Services.UserService;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mashape.unirest.http.HttpResponse;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class Auth0Controller {
    //http://localhost:9000/api/v1
    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String auth0Domain;

    @Value("${auth0.m2m.ClientID}")
    private String clientID;

    @Value("${auth0.m2m.Secret}")
    private String secret;

    @Value("${auth0.Admin.Audience}")
    private String audience;


    private final UserService userService;

    //TODO Esto es importante para sacarle el sub y para asignarselo a la clase Usuario
    @GetMapping(value = "/public")
    public ResponseEntity<?> publicEndpoint(@RequestHeader(value = "Authorization") String token) throws Exception {
        String jwtToken = token.substring(7);
        try {
            JWTClaimsSet decodedJWT = JWTParser.parse(jwtToken).getJWTClaimsSet();
            String sub = decodedJWT.getSubject();
            log.info("Este es el sub que viene ----->"+ sub);
            //userService.saveUsuario(sub);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.OK).body("{ \"El Usuario fue guardado\"}");
    }

    @GetMapping("/getUsers")
    public ResponseEntity<String> getUsersFromAuth0(@RequestHeader("Authorization") String rawJWT) throws UnirestException{
        String jwtToken = rawJWT.substring(7);
        String getUsersURL = auth0Domain.concat("/api/v2/users");
        HttpResponse<String> response = Unirest.get(getUsersURL)
                .header("content-type", "application/json")
                .header("authorization", "Bearer " + jwtToken)
                .header("cache-control", "no-cache")
                .asString();
        String allUsersFromAuth0 = response.getBody();
        return ResponseEntity.ok().body(allUsersFromAuth0);
    }
}
