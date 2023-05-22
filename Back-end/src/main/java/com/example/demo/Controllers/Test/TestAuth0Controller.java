package com.example.demo.Controllers.Test;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import com.mashape.unirest.http.HttpResponse;

import java.text.ParseException;

@RestController
@Slf4j
@RequestMapping(path = "api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestAuth0Controller {
    //http://localhost:9000/api/v1
    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String auth0Domain;


    @GetMapping("/login")
    public RedirectView login() {
        // Replace with your Auth0 domain
        String authorizeUrl = auth0Domain + "authorize";
        return new RedirectView(authorizeUrl);
    }

    @GetMapping(value = "/public")
    public ResponseEntity<?> publicEndpoint(@RequestHeader(value = "Authorization") String token) {
        String jwtToken = token.substring(7);

        try {
            JWTClaimsSet decodedJWT = JWTParser.parse(jwtToken).getJWTClaimsSet();
            String sub = decodedJWT.getSubject();
            log.warn(sub, decodedJWT.getIssuer(), decodedJWT.getExpirationTime(), decodedJWT.getIssueTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.OK).body("{ \"message\": \"All good. You DO NOT need to be authenticated to call this endpoint.\"}");
    }

    @GetMapping(value = "/private")
    public ResponseEntity<?> privateEndpoint() {
        return ResponseEntity.status(HttpStatus.OK).body("{ \"message\": \"All good. You can see this because you are Authenticated.\"}");
    }

    @GetMapping(value = "/admin-only")
    @PreAuthorize("hasAuthority('read:messages')")
    public ResponseEntity<?> adminOnlyEndpoint() {
        return ResponseEntity.status(HttpStatus.OK).body("{ \"message\": \"All good. You can see this because you are an Admin.\"}");
    }

    @PostMapping(value = "/addRole")
    public ResponseEntity<?> createRol(@RequestBody String nombre, @RequestBody String desc, @RequestBody String JWT) throws UnirestException {
        String mgmtApiAccessToken = JWT;
        String roleName = nombre;
        String roleDescription = desc;
        HttpResponse<String> response = Unirest.post("https://dev-jyps2gw84pxnplkp.us.auth0.com/api/v2/roles")
                .header("content-type", "application/json")
                .header("authorization", "Bearer " + mgmtApiAccessToken)
                .header("cache-control", "no-cache")
                .body("{ \"name\": \"" + roleName + "\", \"description\": \"" + roleDescription + "\" }")
                .asString();
        return ResponseEntity.status(HttpStatus.OK).body("Creado :)");
    }

}