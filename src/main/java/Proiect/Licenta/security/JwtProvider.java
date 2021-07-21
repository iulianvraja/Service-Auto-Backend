package Proiect.Licenta.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.Map;

/**
 * Utility Class for common Java Web Token operations
 *
 * Created by Mary Ellen Bowman
 */
@Component
public class JwtProvider{

    private final String ROLES_KEY = "roles";

    private JwtParser parser;

    private String secretKey;
    private long validityInMilliseconds;

    @Autowired
    public JwtProvider(@Value("${security.jwt.token.secret-key}") String secretKey,
                       @Value("${security.jwt.token.expiration}")long validityInMilliseconds) {

        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.validityInMilliseconds = validityInMilliseconds;
    }

    /**
     * Create JWT string given id and roles.
     *
     * @param id
     * @param role
     * @return jwt string
     */
    public String createToken(int id, String role) {
        System.out.println("SUntem in create token");
        //Add the username to the payload
        Claims claims = Jwts.claims().setSubject(String.valueOf(id));
        //Convert roles to Spring Security SimpleGrantedAuthority objects,
        //Add to Simple Granted Authority objects to claims
        claims.put(ROLES_KEY,new SimpleGrantedAuthority(role));
        //Build the Token
        Date now = new Date();
        String tok=Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validityInMilliseconds))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        System.out.println("tok="+tok);
        return tok;
    }

    /**
     * Validate the JWT String
     *
     * @param token JWT string
     * @return true if valid, false otherwise
     */
    public boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            System.out.println("valid");
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("invalid");
            System.out.println(e);
            return false;
        }
    }

    /**
     * Get the username from the token string
     *
     * @param token jwt
     * @return username
     */
    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Get the roles from the token string
     *
     * @param token jwt
     * @return username
     */
    public GrantedAuthority getRoles(String token) {
        Map<String,String> roleClaims = Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody().get(ROLES_KEY,Map.class);
        System.out.println("autority===" +roleClaims.get("authority"));
        return new SimpleGrantedAuthority(roleClaims.get("authority"));

    }
}