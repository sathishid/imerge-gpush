package com.arasoftwares.imerge;

import java.util.ArrayList;
import java.util.Base64;

import com.arasoftwares.imerge.domain.MyWatchRequest;
import com.arasoftwares.imerge.domain.ResponseWrapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import com.google.api.services.gmail.model.WatchRequest;
import com.google.api.services.gmail.model.WatchResponse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/")
public class GMailClientController {

    private static final String APPLICATION_NAME = "Gmail API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart. If modifying these
     * scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_LABELS);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     * 
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        final InputStream in = GMailClientController.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        final GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        final GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                clientSecrets, SCOPES)
                        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                        .setAccessType("offline").build();
        final LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    @GetMapping
    public String sayHi() {
        System.out.println("say hi method called");
        return "<html><head><meta name='google-site-verification' content='IH0iwOYz3p9Xr3GfZi3UeMIQrYtzbxeNOU6kUDJB9JQ' />"
                + "<title>Gmail-Push Notification </title>" + "</head><body><h1>Hello World!</h1>"
                + "<p>Welcome to the Web App</body></html>";
    }

    @GetMapping("api-watch")
    public ResponseEntity<String> gmailApiWatch() {
        try {
            final String baseUrl = "https://www.googleapis.com/gmail/v1/users/mazher@imerge.in/watch";
            URI uri = new URI(baseUrl);
            RestTemplate restTemplate = new RestTemplate();
            MyWatchRequest wreRequest = MyWatchRequest.createRequest();
            ResponseEntity<String> result = restTemplate.postForEntity(uri, wreRequest, String.class);

            System.out.println(result);
            return new ResponseEntity<String>(result.getBody(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
        }
    }

    @GetMapping("watch")
    public ResponseEntity<String> gmailWatch() {
        try {
            WatchRequest request = new WatchRequest();
            List<String> labels = new ArrayList<String>();
            labels.add("INBOX");
            request.setLabelIds(labels);
            request.setLabelFilterAction("include");
            request.setTopicName("projects/gpushproject/topics/gpushtopic");

            // Gmail.Builder
            // WatchResponse wres = w.watch("dsfdas", request).execute();
        } catch (Exception e) {
            return new ResponseEntity<String>("Watched", HttpStatus.OK);
        }
        return new ResponseEntity<String>("Watched", HttpStatus.OK);
    }

    @GetMapping("lables")
    public ResponseEntity<String> getLables() {
        try {
            // Build a new authorized API client service.
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            final Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME).build();

            // Print the labels in the user's account.
            final String user = "me";
            final ListLabelsResponse listResponse = service.users().labels().list(user).execute();
            final List<Label> labels = listResponse.getLabels();
            if (labels.isEmpty()) {
                System.out.println("No labels found.");
            } else {
                System.out.println("Labels:");
                for (final Label label : labels) {
                    System.out.printf("- %s\n", label.getName());
                }
            }
        } catch (final Exception exception) {
            return new ResponseEntity<String>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("DONE", HttpStatus.OK);
    }

    @PostMapping("webhook")
    public ResponseEntity<?> recieveMail(@RequestBody final ResponseWrapper responseWrapper) {
        System.out.println(responseWrapper);
        try {
            final String decodeRequest = new String(
                    Base64.getMimeDecoder().decode(responseWrapper.getMessage().getData().getBytes()));
            System.out.println(decodeRequest);
        } catch (final Exception e) {
            System.out.println(e.getMessage());
        }
        return ResponseEntity.noContent().build();

    }
}
