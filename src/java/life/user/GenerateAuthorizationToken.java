package life.user;

import life.Token;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static life.user.JSONResponses.INVALID_SECRET_PHRASE;

public final class GenerateAuthorizationToken extends UserServlet.UserRequestHandler {

    static final GenerateAuthorizationToken instance = new GenerateAuthorizationToken();

    private GenerateAuthorizationToken() {}

    @Override
    JSONStreamAware processRequest(HttpServletRequest req, User user) throws IOException {
        String secretPhrase = req.getParameter("secretPhrase");
        if (! user.getSecretPhrase().equals(secretPhrase)) {
            return INVALID_SECRET_PHRASE;
        }

        String tokenString = Token.generateToken(secretPhrase, req.getParameter("website").trim());

        JSONObject response = new JSONObject();
        response.put("response", "showAuthorizationToken");
        response.put("token", tokenString);

        return response;
    }

    @Override
    boolean requirePost() {
        return true;
    }

}
