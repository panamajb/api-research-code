package rest;
import auth.JWTRequired;
import io.jsonwebtoken.Jwts;

import javax.annotation.Priority;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Key;

@Provider
@JWTRequired
@Priority(Priorities.AUTHENTICATION)
public class JWTFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext)throws IOException {
        System.out.println("FILTER IS RUNNING *************");
        String header = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            throw new NotAuthorizedException(
                    "Authorization header must be provided");
        }
        // Get token from the HTTP Authorization header
        String token = header.substring("Bearer".length()).trim();

        String user = getUserIfValid(token);
        System.out.println("USER:  "+user);
        //set user in context if required
    }

    private String getUserIfValid(String token) {
        Key key = new SecretKeySpec("secret".getBytes(), "DES");
        try {
            return Jwts.parser().setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            //don't trust the JWT!
            throw new NotAuthorizedException("Invalid JWT");
        }
    }

}