package com.example.demo.Components;

import com.example.demo.Controllers.Auth0.Auth0Utils.JWTManager;
import com.example.demo.Entitys.Rol;
import com.example.demo.Services.RolService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
@RequiredArgsConstructor
@Slf4j
public class RolesScheduling {

    @Value("${auth0.m2m.ClientID}")
    private String clientID;

    @Value("${auth0.m2m.Secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String auth0Domain;

    private final RolService rolService;


    //Funciona, tira palida de las cookies pero funciona
    @Scheduled(cron = "0 0 */1 * * *")
    public void getRolesSchedule() {
        try {
            JWTManager newJWT = new JWTManager();
            String JWT = newJWT.getJWTFromAuth0(clientID, clientSecret);
            String getRolesURL = auth0Domain.concat("/api/v2/roles");
            HttpResponse<String> response = Unirest.get(getRolesURL)
                    .header("content-type", "application/json")
                    .header("authorization", "Bearer " + JWT)
                    .header("cache-control", "no-cache")
                    .asString();
            String allRoles = response.getBody();
            JSONArray jsonArrayFromAuth0 = new JSONArray(allRoles);
            log.info(jsonArrayFromAuth0.toString(), "<----------- JsonArray de java");
            for (Object obj: jsonArrayFromAuth0) {
                JSONObject jsonObject = (JSONObject) obj;
                if(!rolService.checkID(jsonObject.getString("id"))){
                    Rol newRol = new Rol();
                    newRol.setId(jsonObject.getString("id"));
                    newRol.setName(jsonObject.getString("name"));
                    newRol.setDescripcion(jsonObject.getString("description"));
                    rolService.saveRol(newRol);
                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
