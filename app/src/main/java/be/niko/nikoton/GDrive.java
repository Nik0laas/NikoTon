package be.niko.nikoton;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
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

public class GDrive extends AppCompatActivity {
    private Context context;
    private static final String APPLICATION_NAME = "NIKOTON";
    private static final String TOKENS_DIRECTORY_PATH = "TOKENS";
    private NetHttpTransport httptrans;
    private Drive service;

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    public GDrive() {
        httptrans = null;

        try {
            httptrans = GoogleNetHttpTransport.newTrustedTransport();

            service = new Drive.Builder(httptrans, Globals.JSON_FACTORY, getCredentials(httptrans))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException, FileNotFoundException {
        // Load client secrets.
        ClassLoader classLoader = LaatsteTest.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("cred/cred_nikoton.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(Globals.JSON_FACTORY, new InputStreamReader(inputStream));

        // Build flow and trigger user authorization request.
        final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_FILE);
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, Globals.JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new MemoryDataStoreFactory())
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

    }

    public String[] importPlays() {
        Task<List<GoogleDriveFileHolder>> googleDriveFilePlays;

        try {
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
        } catch (IOException ioE) {

        }

       return new String[]{"E", "R"};
    }
}