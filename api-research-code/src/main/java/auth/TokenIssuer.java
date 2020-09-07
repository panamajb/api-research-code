package auth;
import io.jsonwebtoken.*;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

public class TokenIssuer {
    //Expiration time of token would be 60 mins
    public static final long EXPIRY_MINS = 1L;

    public String issueToken(String username) {
        LocalDateTime expiryPeriod = LocalDateTime.now().plusSeconds(15);
                                        //.plusMinutes(EXPIRY_MINS);
        Date expirationDateTime = Date.from(
                expiryPeriod.atZone(ZoneId.systemDefault())
                        .toInstant());

        Key key = new SecretKeySpec("secret".getBytes(), "DES");
        String compactJws = Jwts.builder()
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS256, key)
                .setIssuedAt(new Date())
                .setExpiration(expirationDateTime)
                .compact();
        return compactJws;
    }
}