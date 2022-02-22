package be.niko.nikoton.laatsteTest;

import static be.niko.nikoton.laatsteTest.Global.authed;
import static be.niko.nikoton.laatsteTest.Global.browserIntent;

import androidx.appcompat.app.AppCompatActivity;

import com.google.api.client.auth.oauth2.Credential;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GDrive extends AppCompatActivity {
    //private Context context;
    private static final String APPLICATION_NAME = "NIKOTON";
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private NetHttpTransport httptrans;
    private Drive service;
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_METADATA_READONLY);

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    public GDrive() {
        int authWaitCounter = 0;

        try {
            // Build a new authorized API client service.
            System.out.println("UD: This is a test line");

            //declare final variable NetHttpTransport and GoogleNetHttpTransport from import statements
            Global.HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            System.out.println("UD: This is the second test line");

            authed = false;
            Credential cred = getCredentials(Global.HTTP_TRANSPORT);
            while (!authed && authWaitCounter < 5000) {
                authWaitCounter++;
                browserIntent.getData();
            }
            if(authed) {
                afterAuth();
            }
        } catch (Exception e) {
            System.out.println("UD: error");
        }

        System.out.println("UD: This is the fourth test line");
    }

    public void afterAuth() {
        try{
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            System.out.println("UD: This is afterUath the second test line");
            service = new Drive.Builder(HTTP_TRANSPORT, Global.JSON_FACTORY, Global.cred)
                                .setApplicationName(APPLICATION_NAME)
                                .build();
            Global.driveAuth = true;
             /* // Print the names and IDs for files.
            GoogleListFolderTask asyncListFolderTask = new GoogleListFolderTask(getApplicationContext(), drive);
            asyncListFolderTask.execute();*/
        } catch (Exception e) {
            System.out.println("UD: error");
        }
    }

    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException, FileNotFoundException, Except_exist {
        // Load client secrets.
        ClassLoader classLoader = LT_android.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("cred/cred_NikoTon.json");
        if (inputStream.equals(null)) {
            throw new Except_exist("correcte cred/cred_NikoTon.json resouce");
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(inputStream));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new MemoryDataStoreFactory())
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();

        return new Android_authCodeInstApp(this,flow, receiver).authorize("user").setAccessToken("user");
    }


    public List<String[]> readPlays() {
        List<String[]> plays = null;

        /*try {
           *//* mDriveService.files().list().setSpaces("drive").execute();
            List<File> files = result.getFiles();
            if (files == null || files.isEmpty()) {
                System.out.println("UD: No files found.");
            } else {
                plays = new ArrayList<String[]>();

                for (File file : files) {
                    plays.add(new String[] { file.getId(), file.getName() });
                }
            }*//*
        } catch (IOException ioE) {
        }*/

       return plays;
    }

    public String readPlay(String fileID) {
        try {
            File result = service.files().get(fileID)
                    .execute();
            if (result== null) {
                System.out.println("UD: No file found.");
            } else {

            }
        } catch (IOException ioE) {
        }

        return "";
    }

    public List<String[]> readPlaysToImport() {
        List<String[]> plays = null;

        try {
            FileList result = service.files().list()
                    .setQ("'1JvnzFrmDBNBQBs572DJp8pj605lHrxBb' in parents")
                    .setPageSize(6)
                    .setFields("nextPageToken, files(id, name)")
                    .execute();
            List<File> files = result.getFiles();
            if (files == null || files.isEmpty()) {
                System.out.println("UD: No files found.");
            } else {
                plays = new ArrayList<String[]>();

                for (File file : files) {
                    plays.add(new String[] { file.getId(), file.getName() });
                }
            }
        } catch (IOException ioE) {
        }

        return plays;
    }
}