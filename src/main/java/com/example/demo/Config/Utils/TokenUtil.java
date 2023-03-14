package com.example.demo.Config.Utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtil {
    private final static String access_token_secret = "Lets-take-the-dark-road-to-the-far-side-of-the-station";
    private final static Long duration_token = 3600L;

    public static String createToken (String username){
        Long expiration_token = duration_token * 1000;
        Date expiration_date_token = new Date(System.currentTimeMillis() * expiration_token);
        Map<String,Object> backpack = new HashMap<>();
        backpack.put("name",username);

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(expiration_date_token)
                .addClaims(backpack)
                .signWith(Keys.hmacShaKeyFor(access_token_secret.getBytes()))
                .compact();
    }
    public static UsernamePasswordAuthenticationToken authenticationToken(String token){
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(access_token_secret.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String backpackClaims = claims.getSubject();

            return new UsernamePasswordAuthenticationToken(backpackClaims, null, Collections.emptyList());
        }catch (JwtException JWTe){
                return null;
        }
    }

}
