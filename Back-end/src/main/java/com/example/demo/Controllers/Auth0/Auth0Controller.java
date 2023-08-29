package com.example.demo.Controllers.Auth0;

import com.example.demo.Controllers.Auth0.Auth0Classes.Auth0DTO;
import com.example.demo.Controllers.Auth0.Auth0Utils.JWTManager;
import com.example.demo.Entitys.Usuario;
import com.example.demo.Services.DireccionService;
import com.example.demo.Services.RolService;
import com.example.demo.Services.UserService;
import com.mashape.unirest.http.Unirest;
import com.nimbusds.jose.shaded.gson.Gson;

import com.nimbusds.jose.shaded.gson.JsonArray;
import com.nimbusds.jose.shaded.gson.JsonObject;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mashape.unirest.http.HttpResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "v1/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class Auth0Controller {
    private final UserService userService;
    private final DireccionService direccionService;

    @Value("${auth0.m2m.ClientID}")
    private String clientID;

    @Value("${auth0.m2m.Secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String auth0Domain;

    @Value("${The.Ultimate.Password}")
    private String LePassword;

    private final RolService rolService;

    private final JWTManager jwtManager;

    /*
    SOLO UTILIZABLE EN AUTH0 IDS, No googleIDS o otras redes sociales
    Solo cambiara contraseña, no es posible cambiar el username, email
    atte : Auth0 el peor sistema de seguridad del planeta tierra
    Documentacion: me la meti en el orto porque no existo
    */
    @PutMapping("/changeUser/{idUser}")
    public ResponseEntity<?> changeUser(@RequestBody Usuario userAuth0, @PathVariable("idUser") String idUser) throws Exception {
        try {
            String newIdUser = idUser.replace('_', '|');
            userService.updateLocalDBUser(newIdUser, userAuth0);
            return ResponseEntity.status(HttpStatus.OK).body("El usuario fue cambiado en la base de datos local");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }


    @PostMapping("/createUserAdmin")
    public ResponseEntity<?> createUser(@RequestBody Usuario user) {
        return userService.createUser(user);
    }

    @GetMapping("/getUsers")
    public ResponseEntity<?> getUsers(@PageableDefault(value = 10, page = 0) Pageable page) throws Exception {
        try {
            Page<Usuario> allUsuarios = userService.viewAllUsuarios(page);
            Page<Auth0DTO> allUsuariosDTO = allUsuarios.map(usuario -> {
                Auth0DTO usuarioDTO = new Auth0DTO();
                return usuarioDTO.toDTO(usuario);
            });
            return ResponseEntity.status(HttpStatus.OK).body(allUsuariosDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @GetMapping("/ForceBringUsersAuth0")
    public ResponseEntity<?> forceGetUsersAuth0() {
        try {
            String newJWT = jwtManager.getJWTFromAuth0(clientID, clientSecret);
            String getUsersUrl = auth0Domain.concat("api/v2/users");

            log.info(getUsersUrl);
            log.info(newJWT);
            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = Unirest.get(getUsersUrl)
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + newJWT)
                    .asString();

            String responseBody = response.getBody();
            //Create a user list to save the users
            List<Usuario> userList = new ArrayList<>();
            // For each user in the response body create a new user and save it in the list
            JSONArray jsonArray = new JSONArray(responseBody);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Usuario newUser = new Usuario();
                newUser.setId(jsonObject.getString("user_id"));
                newUser.setEmail(jsonObject.getString("email"));
                newUser.setUsername(jsonObject.getString("nickname"));
                newUser.setPicture(jsonObject.getString("picture"));
                // newUser.setRol(rolService.findbyID("rol_0"));
                if (jsonObject.has("blocked")) {
                    log.info("bloqueado" + jsonObject.getBoolean("blocked"));
                    newUser.setBlocked((jsonObject.getBoolean("blocked")));
                }
                if (jsonObject.has("email_verified")) {
                    newUser.setEmail_verified((jsonObject.getBoolean("email_verified")));
                }
                userList.add(newUser);
            }
            // Save the list of users in the database
            for (Usuario user : userList) {
                if (!userService.existsbyID(user.getId())) {
                    userService.saveUser(user);
                }
            }

            log.info(responseBody);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(jsonArray);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @GetMapping("/getRoles")
    public ResponseEntity<?> getRoles() throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(rolService.getAllWOPage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @PostMapping("/addRole")
    public ResponseEntity<?> createRole(@RequestBody Map<String, String> requestBody) {
        try {
            String JWT = jwtManager.getJWTFromAuth0(clientID, clientSecret);
            String roleName = requestBody.get("nombre");
            String roleDescription = requestBody.get("desc");
            String getRolesURL = auth0Domain.concat("/api/v2/roles");
            HttpResponse<String> response = Unirest.post(getRolesURL)
                    .header("content-type", "application/json")
                    .header("authorization", "Bearer " + JWT)
                    .header("cache-control", "no-cache")
                    .body("{ \"name\": \"" + roleName + "\", \"description\": \"" + roleDescription + "\" }")
                    .asString();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("success", true, "message", "Rol creado"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }

    }


    @GetMapping("/search")
    public ResponseEntity<?> searchUsuarios(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable page,
                                            @RequestParam(value = "query", required = false) String query,
                                            @RequestParam(value = "rol", required = false) String rol
    ) {
        try {
            Page<Usuario> usuarioPage = userService.filterUsuarios(query, rol, page);
            return ResponseEntity.status(HttpStatus.OK).body(usuarioPage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }

    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getOneUsuario(@PathVariable("username") String username) throws Exception {
        try {
            username = username.replace('_', '|');
            Usuario userFound = userService.userbyID(username);
            Auth0DTO userProccesed = new Auth0DTO();
            return ResponseEntity.status(HttpStatus.OK).body(userProccesed.toDTO(userFound));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }

    }

    @GetMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestParam("userID") String userID) throws Exception{
        try{
            String userIDMod = "auth0|".concat(userID);
            String newJWT = jwtManager.getJWTFromAuth0(clientID, clientSecret);
            String urlChange = auth0Domain + "/api/v2/tickets/password-change";
            JSONObject newJson = new JSONObject();
            newJson.put("user_id", userIDMod);
            HttpResponse<String> response = Unirest.post(urlChange)
                    .header("content-type", "application/json")
                    .header("authorization", "Bearer " + newJWT)
                    .body(newJson)
                    .asString();
            return ResponseEntity.status(HttpStatus.OK).body(response.getBody());

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
}
