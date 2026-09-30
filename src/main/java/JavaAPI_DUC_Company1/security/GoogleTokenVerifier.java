package JavaAPI_DUC_Company1.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleTokenVerifier {

    private final GoogleIdTokenVerifier verifier;

    public GoogleTokenVerifier(
            @Value("${google.client-id}") String googleClientId)
            throws GeneralSecurityException, IOException {

        this.verifier =
                new GoogleIdTokenVerifier.Builder(
                        GoogleNetHttpTransport.newTrustedTransport(),
                        GsonFactory.getDefaultInstance())
                        .setAudience(
                                Collections.singletonList(googleClientId))
                        .build();
    }

    public Payload verify(String token) {
        try {
            GoogleIdToken idToken = verifier.verify(token);
            if (idToken == null) {
                throw new RuntimeException("Invalid Google ID token");
            }
            return idToken.getPayload();

        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(
                    "Could not verify Google ID token", e);
        }
    }
}
