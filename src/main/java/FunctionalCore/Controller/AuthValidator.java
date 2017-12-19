package FunctionalCore.Controller;

import java.util.Base64;

public class AuthValidator extends Validator {
    private final String authPrefix = "Authorization: Basic";
    private final String withNothing = "";

    public boolean valid(String fromConfig, String fromRequest) { return fromRequest.equals(fromConfig); }

    public String getAuthHeader(String[] headers) {
        return getHeader(headers, authPrefix);
    }

    public String getAuth(String authHeader) {
        String authValue = authHeader.replace(authPrefix, withNothing).trim();
        return new String(Base64.getDecoder().decode(authValue));
    }
}
