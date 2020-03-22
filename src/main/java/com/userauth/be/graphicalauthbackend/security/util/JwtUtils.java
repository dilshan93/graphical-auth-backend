package com.userauth.be.graphicalauthbackend.security.util;

import com.userauth.be.graphicalauthbackend.security.configuration.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${user.app.jwtSecret}")
    private String jwtSecret;

    @Value("${user.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String jwtTokengeneration(Authentication authentication){

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime()+jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token){

        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (SignatureException e){
            LOGGER.error("Invalid Token Signature : " + e.getMessage());
        }catch (MalformedJwtException e){
            LOGGER.error("Invalid Token : " + e.getMessage());
        }catch (ExpiredJwtException e){
            LOGGER.error("Expired Token : " + e.getMessage());
        }catch (UnsupportedJwtException e){
            LOGGER.error("Unsupported Token : " + e.getMessage());
        }catch (IllegalArgumentException e){
            LOGGER.error("Illegal Token : " + e.getMessage());
        }catch (Exception e){
            LOGGER.error("Token Error : " + e.getMessage());
        }

        return false;
    }

    public String getUserNameFromToken(String token){

        String username = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
        return username;
    }
}
