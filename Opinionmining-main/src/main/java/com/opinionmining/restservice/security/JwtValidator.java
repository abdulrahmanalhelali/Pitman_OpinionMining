package com.opinionmining.restservice.security;

import com.opinionmining.restservice.exception.InvalidTokenException;
import com.opinionmining.restservice.model.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {
    private final String secret = "SecretKey";

    public JwtUser validate(String token) {

        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            return new JwtUser(body.getSubject(),
                    body.getId());


        }
        catch (Exception e) {
            throw new InvalidTokenException();
        }

    }
}

