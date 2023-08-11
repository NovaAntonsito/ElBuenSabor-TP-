package com.example.demo.Controllers.Auth0.Auth0Utils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class JWTManager {
    private String jwt;
    private long duration;

    public String getJWTFromAuth0 (String clientID, String clientSecret) {

        HttpResponse<String> response = null;
        try {
            response = Unirest.post("https://dev-jyps2gw84pxnplkp.us.auth0.com/oauth/token")
                    .header("content-type", "application/json")
                    .body(String.format(
                            "{\"client_id\":\"%s\",\"client_secret\":\"%s\",\"audience\":\"https://dev-jyps2gw84pxnplkp.us.auth0.com/api/v2/\",\"grant_type\":\"client_credentials\"}",
                            clientID,
                            clientSecret
                    ))
                    .asString();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        JSONObject responseBody = new JSONObject(response.getBody());
        String accessToken = responseBody.getString("access_token");
        return accessToken;
    }
}
