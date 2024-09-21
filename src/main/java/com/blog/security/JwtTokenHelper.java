package com.blog.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenHelper {
     public static  final long JWT_TOKEN_VALIDITY=5*60*60;

    private final String secret = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";

    //private final SecretKey secret = Keys.secretKeyFor(SignatureAlgorithm.HS512);


    public String getUsernameFromToken(String token){
         return getClaimFromToken(token, Claims::getSubject);
     }


    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims,T> claimsResolver){
         final Claims claims=getAllClaimsFromToken(token);
         return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token){
         return Jwts.parser().setSigningKey(secret).build().parseSignedClaims(token).getPayload();
    }

    private Boolean isTokenExpired(String token){
         final Date expiration=getExpirationDateFromToken(token);
         return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails){

        Map<String, Object> claims=new HashMap<>();
        return doGenerateToken(claims,userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000*60*30))
                .signWith(getSignKey(),SignatureAlgorithm.HS256)
                .compact();
    }
    private SecretKey getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public  Boolean validateToken(String token , UserDetails userDetails){
         String username=getUsernameFromToken(token);
         return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
}


}
