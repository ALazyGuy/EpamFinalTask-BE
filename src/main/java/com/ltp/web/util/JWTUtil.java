package com.ltp.web.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

public class JWTUtil {
    private static final String SECRET_KEY = "sqTgjho2qPA9K1lk1Rsewfjoweoiwojf82938fh2893";
    private static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;
    private static final int EXPIRATION_TIME = 1000 * 60 * 60 * 10;

    public static final String TOKEN_PREFIX = "JWT";

    private static boolean isExpired(String token){
        return getClaim(token, Claims::getExpiration).before(new Date());
    }

    private static <T> T getClaim(String token, Function<Claims, T> func){
        return func.apply(Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody());
    }

    public static String generate(String email){
        return TOKEN_PREFIX + Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(ALGORITHM, SECRET_KEY)
                .compact();
    }

    public static boolean validate(String token, String email){
        return (!isExpired(token) && getClaim(token, Claims::getSubject).equals(email));
    }

    public static String getEmail(String token){
        return getClaim(token, Claims::getSubject);
    }
}