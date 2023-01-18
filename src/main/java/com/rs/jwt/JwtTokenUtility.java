package com.rs.jwt;


import com.rs.user.UserInfo;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtility {
    @Value("${app.jwt.issuer-name}")
    private String ISSUER_NAME;

    @Value("${app.jwt.period-of-validity}")
    private long PERIOD_OF_VALIDITY;

    @Value("${app.jwt.secret}")
    private String JWT_SECRET_KEY;

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtility.class);

    public String generateAccessToken(UserInfo user){
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuer(ISSUER_NAME)
                .claim("roles", user.getRoles().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+PERIOD_OF_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET_KEY)
                .compact();
    }

    public boolean validateAccessToken(String accessToken){
        return parseClaims(accessToken) != null;
    }

    public String getUsername(String accessToken){
        return getSubject(accessToken);
    }

    private String getSubject(String accessToken){
        return parseClaims(accessToken).getBody().getSubject();
    }

    protected Jws<Claims> parseClaims(String accessToken){
        try{
            return Jwts.parser()
                    .setSigningKey(JWT_SECRET_KEY)
                    .parseClaimsJws(accessToken);
        } catch (ExpiredJwtException ex){
            LOGGER.error("JWT Expired", ex.getMessage());
        } catch (IllegalArgumentException ex){
            LOGGER.error("Token is null, empty or whitespace", ex.getMessage());
        }catch (MalformedJwtException ex){
            LOGGER.error("JWT is Invalid", ex);
        }catch (UnsupportedJwtException ex){
            LOGGER.error("JWT is not supported", ex);
        }catch (SignatureException ex){
            LOGGER.error("Signature validation failed");
        }

        return null;
    }
}