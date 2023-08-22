package com.example.demo.Services;

import com.example.demo.Controllers.Auth0.Auth0Utils.JWTManager;
import com.example.demo.Entitys.Direccion;
import com.example.demo.Entitys.Insumo;
import com.example.demo.Entitys.Rol;
import com.example.demo.Entitys.Usuario;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Services.Interfaces.UserServiceInterface;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.JsonArray;
import com.nimbusds.jose.shaded.gson.JsonObject;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;
    //http://localhost:9000/api/v1/users

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

    private final JWTManager jwtManager = new JWTManager();
    @Override
    public Page<Usuario> viewAllUsuarios(Pageable page) throws Exception {
        return userRepository.findAll(page);
    }

    @Override
    public Page<Usuario> filterUsuarios(String query, String rol ,Pageable pageable) throws Exception {
        return userRepository.filterUsers(query, rol, pageable);
    }

    @Override
    public Usuario userbyID(String username) throws Exception {
        return userRepository.userFound(username);
    }

    @Override
    public Usuario userbyUsername(String username) throws Exception {
        return userRepository.getUserbyUsername(username);
    }

    @Override
    public Usuario addAddressToUser(String id, Direccion direccion) throws Exception {
        Usuario userFound = userRepository.userFound(id);
        userFound.getDireccionList().add(direccion);
        return userFound;
    }

    @Override
    public Boolean existsbyID(String userID) throws Exception {
        return userRepository.existsById(userID);
    }

    @Override
    public Usuario saveUser(Usuario newUser) throws Exception {
        return userRepository.save(newUser);
    }

    @Override
    public Usuario upsertUser(Usuario user) throws Exception {
        Usuario userFound = userRepository.findById(user.getId()).orElseThrow(() -> new Exception("No existe el usuario"));
        userFound.setName(user.getName());
        userFound.setPhone_number(user.getPhone_number());
        userFound.setEmail(user.getEmail());
        userFound.setDireccionList(user.getDireccionList());
        userFound.setBlocked(user.getBlocked());
        userFound.setRol(user.getRol());
        userRepository.save(userFound);
        return userFound;
    }

    public ResponseEntity<?> createUser(Usuario user) {
        try {
            log.info("Creating user: {}", user.toString());

            String JWTActual = jwtManager.getJWTFromAuth0(clientID, clientSecret);

            String postUserURL = auth0Domain.concat("api/v2/users");

            JsonObject userJson = prepareUserJsonObject(user);
            HttpResponse<String> response = sendPostRequest(postUserURL, JWTActual, userJson.toString());

            if (response.getStatus() == HttpStatus.CONFLICT.value()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("success", false, "message", "El usuario ya existe"));
            } else if (response.getStatus() != HttpStatus.CREATED.value()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("success", false, "message", "Error al crear el usuario"));
            }


            var jsonResponse = new JSONObject(response.getBody());
            String userId = jsonResponse.getString("user_id");
            assignRoleToUser(userId, JWTActual, user.getRol().getId());

            Rol rolFound = rolService.findbyID(user.getRol().getId());

            user.setRol(rolFound);

            if(userId.contains("auth0")) userId = userId.substring(6);

            user.setId(userId);
            upsertUser(user);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("success", true, "message", "User created and assigned role"));
        } catch (Exception e) {
            log.error("Error while creating user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    private JsonObject prepareUserJsonObject(Usuario user) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", user.getEmail());
        jsonObject.addProperty("connection", "ElBuenSaborDB");
        jsonObject.addProperty("password", LePassword);
        jsonObject.addProperty("name", user.getName());
        jsonObject.addProperty("username", user.getUsername());
        //jsonObject.addProperty("phone_number", user.getPhone_number());
        jsonObject.addProperty("blocked", user.getBlocked());
        return jsonObject;
    }

    private HttpResponse<String> sendPostRequest(String url, String authorizationHeader, String body) throws UnirestException {
        return Unirest.post(url)
                .header("content-type", "application/json")
                .header("accept", "application/json")
                .header("authorization", "Bearer " + authorizationHeader)
                .body(body)
                .asString();
    }

    private void assignRoleToUser(String userId, String JWTActual, String roleId) throws UnirestException {
        JsonObject rol = new JsonObject();
        JsonArray rolesArray = new JsonArray();
        rolesArray.add(roleId);
        rol.add("roles", rolesArray);

        Gson gson = new Gson();
        String newJson = gson.toJson(rol);
        String asignarRolURL = auth0Domain.concat("/api/v2/users/" + userId + "/roles");

        HttpResponse<String> asignar = sendPostRequest(asignarRolURL, JWTActual, newJson);
        log.info("Assigning role response: {}", asignar.getBody());
    }
    @Override
    public void updateLocalDBUser (String id,Usuario user){
        try {
            Optional<Usuario> userFound = userRepository.findById(id);
            if (userFound.isPresent()) {
                userFound.get().setName(user.getName());
                userFound.get().setUsername(user.getUsername());
                userFound.get().setEmail(user.getEmail());
                userRepository.save(userFound.get());
            } else {
                throw new RuntimeException("No existe ese usuario");
            }
        } catch (RuntimeException e) {
            log.error(Map.of(
                    "Error", e.getMessage(),
                    "Causa", e.getCause()
            ).toString());
        }

    }
}
