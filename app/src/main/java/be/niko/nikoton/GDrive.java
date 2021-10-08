package be.niko.nikoton;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.Task;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.services.drive.DriveScopes;
import com.google.cloud.firestore.Firestore;

import java.util.Collections;
import java.util.List;

public class GDrive extends AppCompatActivity {
    private Context context;
    private static final String APPLICATION_NAME = "NIKOTON";
    private static final String TOKENS_DIRECTORY_PATH = "TOKENS";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static GoogleClientSecrets clientSecrets;
    private static Firestore fs_db;
    private GoogleSignInClient g_client;
    private DriveServiceHelper mDriveServiceHelper;

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_METADATA_READONLY);
    private static DataStoreFactory gdata_store;

    public GDrive(Context current) {
        this.context = current;
    }

    public String[] readPlays(Context p_cont) {
        Task<List<GoogleDriveFileHolder>> googleDriveFilePlays;

        googleDriveFilePlays = mDriveServiceHelper.queryFiles("1s8jU5kZx4987gv0l3kmgSGmonrSCNzQE");
        String[] plays = new String[0];

        return plays;
    }
}