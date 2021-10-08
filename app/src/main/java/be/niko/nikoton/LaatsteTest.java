package be.niko.nikoton;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class LaatsteTest {
    private static final String APPLICATION_NAME = "NIKOTON";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    // * Global instance of the scopes required by this quickstart.
    // * If modifying these scopes, delete your previously saved tokens/ folder.

    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_METADATA_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "C:/Users/NH/AndroidStudioProjects/NikoTon/app/src/main/Resources/conf/cred_nikoton.json";

    // * Creates an authorized Credential object.
// * @param HTTP_TRANSPORT The network HTTP Transport.
// * @return An authorized Credential object.
// * @throws IOException If the client_secret.json file cannot be found.
// */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException, FileNotFoundException {
        // Load client secrets.
        ClassLoader classLoader = LaatsteTest.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("cred/cred_nikoton.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(inputStream));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new MemoryDataStoreFactory())
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

    }

    public static void main(String... args) throws IOException, GeneralSecurityException {
        try {
            // Build a new authorized API client service.
            System.out.println("This is a test line");

            //declare final variable NetHttpTransport and GoogleNetHttpTransport from import statements
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            System.out.println("This is the second test line");
            Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            System.out.println("This is the third test line");

            // Print the names and IDs for files.
            FileList result = service.files().list()
                    .setQ("mimeType = 'image/jpeg' and '1JvnzFrmDBNBQBs572DJp8pj605lHrxBb' in parents")
                    .setPageSize(10)
                    .setFields("nextPageToken, files(id, name)")
                    .execute();
            List<File> files = result.getFiles();
            if (files == null || files.isEmpty()) {
                System.out.println("No files found.");
            } else {
                System.out.println("Files:");
                for (File file : files) {
                    System.out.printf("%s (%s)\n", file.getName(), file.getId());
                }
            }
        } catch (Exception e) {
            System.out.println("error");
        }

        System.out.println("This is the fourth test line");

    }

}