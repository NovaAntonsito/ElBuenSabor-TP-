package com.example.demo.Controllers.Auth0;

import com.example.demo.Controllers.Auth0.Auth0Classes.Auth0DTO;
import com.example.demo.Controllers.Auth0.Auth0Classes.UserAuth0;
import com.example.demo.Controllers.Auth0.Auth0Utils.JWTManager;

import com.example.demo.Entitys.Usuario;
import com.example.demo.Services.DireccionService;
import com.example.demo.Services.RolService;
import com.example.demo.Services.UserService;
import com.mashape.unirest.http.Unirest;

import com.nimbusds.jose.shaded.gson.Gson;

import com.nimbusds.jose.shaded.gson.JsonArray;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonPrimitive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mashape.unirest.http.HttpResponse;
import com.example.demo.Entitys.Rol;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class Auth0Controller {
    //http://localhost:9000/api/v1/users

    private final UserService userService;
    private final DireccionService direccionService;

    @Value("${auth0.m2m.ClientID}")
    private String clientID;

    @Value("${auth0.m2m.Secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String auth0Domain;

    private final RolService rolService;


    //SOLO UTILIZABLE EN AUTH0 IDS, No googleIDS o otras redes sociales
    @PutMapping("/changeUser/{idUser}")
    public ResponseEntity<?> changeUser(@RequestBody UserAuth0 userAuth0, @PathVariable("idUser") String idUser) throws Exception{
        try {
            JWTManager newJWT = new JWTManager();
            String newIdUser = idUser.replace('_' , '|');
            String JWTActual = newJWT.getJWTFromAuth0(clientID, clientSecret);
            String putUserURL = auth0Domain.concat("api/v2/users/"+newIdUser);
            Gson newJson = new Gson();
            String jsonBody = newJson.toJson(userAuth0);
            log.info(putUserURL);
            HttpResponse<String> response = Unirest.patch(putUserURL)
                    .header("content-type", "application/json")
                    .header("accept", "application/json")
                    .header("authorization", "Bearer " + JWTActual)
                    .body(jsonBody)
                    .asString();
            return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @PostMapping("/createUserAdmin")
    public ResponseEntity<?> createUser(@RequestBody UserAuth0 user) throws Exception{
        try {
            //Creacion de usuario
            user.setConnection("email");
            JWTManager newJTW = new JWTManager();
            String JWTActual = newJTW.getJWTFromAuth0(clientID, clientSecret);
            String postUserURL = auth0Domain.concat("api/v2/users");
            // Crear un nuevo objeto JSON con email y conexión
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("email", user.getEmail());
            jsonObject.addProperty("connection", user.getConnection());

            // Convertir el nuevo objeto JSON a cadena JSON
            Gson gson = new Gson();
            String nuevoJson = gson.toJson(jsonObject);
            HttpResponse<String> response = Unirest.post(postUserURL)
                    .header("content-type", "application/json")
                    .header("accept", "application/json")
                    .header("authorization", "Bearer " + JWTActual)
                    .body(nuevoJson)
                    .asString();
            String responseBody = response.getBody();
            JSONObject json = new JSONObject(responseBody);
            String userId = json.getString("user_id");


            //Asignacion de rol

            JsonObject rol = new JsonObject();
            JsonArray rolesArray = new JsonArray();
            rolesArray.add(user.getRolID());
            log.info(user.getRolID());
            rol.add("roles", rolesArray);
            // Obvio que hay que cambiar los nombres a las variables pero no me salia un mejor nombre
            String newJson = gson.toJson(rol);
            log.info(newJson.toString());
            String asignarRolURL = auth0Domain.concat("/api/v2/users/"+userId+"/roles");
            HttpResponse<String> asignar = Unirest.post(asignarRolURL)
                    .header("content-type", "application/json")
                    .header("accept", "application/json")
                    .header("authorization", "Bearer " + JWTActual)
                    .body(newJson)
                    .asString();

            if(userService.existsbyID(userId)){
                Usuario userFound = userService.userbyID(userId);
                Rol rolFound = rolService.findbyID(user.getRolID());
                userFound.setRol(rolFound);
                userService.saveUser(userFound);
                return ResponseEntity.status(HttpStatus.OK).body(Map.of("success", true, "message", "El usuario creado y asignado el rol"));
            }
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("success", true, "message", "Usuario creado pero no existe en nuestra base de datos"));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
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
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @GetMapping("/ForceBringUsersAuth0")
    public ResponseEntity<?> forceGetUsersAuth0(){
        try {
            JWTManager jwtManager = new JWTManager();
            String newJWT = jwtManager.getJWTFromAuth0(clientID,clientSecret);
            

            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("success", true, "message", "Los usuarios fueron traidos"));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @GetMapping("/getRoles")
    public ResponseEntity<?> getRoles(@PageableDefault(value = 10, page = 0) Pageable page) throws Exception{
        try {
            Page<Rol> allRoles = rolService.rolPage(page);
            return ResponseEntity.status(HttpStatus.OK).body(allRoles);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @PostMapping("/addRole")
    public ResponseEntity<?> createRole(@RequestBody Map<String, String> requestBody) throws Exception{
        try {
            JWTManager newJWT = new JWTManager();
            String JWT = newJWT.getJWTFromAuth0(clientID, clientSecret);
            String roleName = requestBody.get("nombre");
            String roleDescription = requestBody.get("desc");
            String getRolesURL = auth0Domain.concat("/api/v2/roles");
            HttpResponse<String> response = Unirest.post(getRolesURL)
                    .header("content-type", "application/json")
                    .header("authorization", "Bearer " + JWT)
                    .header("cache-control", "no-cache")
                    .body("{ \"name\": \"" + roleName + "\", \"description\": \"" +  roleDescription + "\" }")
                    .asString();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("success", true, "message", "Rol creado"));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }

    }



    @GetMapping("/search")
    public ResponseEntity<?> searchUsuarios(@PageableDefault(value = 10, page = 0) Pageable page,@RequestParam("username")String username) throws Exception{
        try {
            Page<Usuario> usuarioPage = userService.filterUsuarios(username,page);
            return ResponseEntity.status(HttpStatus.OK).body(usuarioPage);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }

    }
    @GetMapping("/{username}")
    public ResponseEntity<?> getOneUsuario(@PathVariable("username") String username)throws Exception{
        try {
            username = username.replace('_', '|');
            Usuario userFound = userService.userbyID(username);
            Auth0DTO userProccesed = new Auth0DTO();
            return ResponseEntity.status(HttpStatus.OK).body(userProccesed.toDTO(userFound));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }

    }

}
