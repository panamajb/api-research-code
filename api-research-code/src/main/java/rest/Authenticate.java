package rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import auth.TokenIssuer;
@Path("authenticate")
public class Authenticate {
	
	@Inject private TokenIssuer issuer;
	
	//@POST
	@GET
    @Produces(MediaType.TEXT_PLAIN)
	public Response authenticate() {
	       // boolean valid = service.isValid(creds.getUsername(),creds.getPassword());
		boolean valid=true;
	        if (valid) {
	            String token = issuer.issueToken("panamajb");
	            return Response.ok(token).build();
	        }
	        return Response.status(Response.Status.UNAUTHORIZED)
	               .build();
	}


//	public Response authenticate(Credential creds) {
//        boolean valid = service.isValid(creds.getUsername(),
//                                        creds.getPassword());
//        if (valid) {
//            String token = issuer.issueToken(creds.getUsername());
//            return Response.ok(token).build();
//        }
//        return Response.status(Response.Status.UNAUTHORIZED)
//               .build();
//	}

}
