package be.niko.nikoton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;

import static be.niko.nikoton.Globals.g_driveServ;

public class StartActivity extends AppCompatActivity {

    Button signin,signout;
    private static final int RC_SIGN_IN = 9001;
    private static final int REQUEST_CODE_SIGN_IN = 1;
    private static final int REQ_ONE_TAP = 2;
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;
    private static final String TAG = "drive-quickstart";
    public GoogleSignInClient g_client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.start);
        signin= findViewById(R.id.signin);
        signout= findViewById(R.id.signout);
        signout.setVisibility(View.GONE);

        signin.setOnClickListener(v -> {
            signInClick();
        });

        signout.setOnClickListener(v -> {
            signOutClick();
        });

    }

    private void signInClick () {
        oneTapClient = Identity.getSignInClient(this);
        signInRequest = BeginSignInRequest.builder()
                .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
                        .setSupported(true)
                        .build())
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId("868733980552-a8muv4nvh8subcm8nol00s9adp55cvng.apps.googleusercontent.com")
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(true)
                        .build())
                // Automatically sign in when exactly one credential is retrieved.
                .setAutoSelectEnabled(true)
                .build();

        oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(this, new OnSuccessListener<BeginSignInResult>() {
                    @Override
                    public void onSuccess(BeginSignInResult result) {
                       loginResultHandler.launch(new IntentSenderRequest.Builder(result.getPendingIntent().getIntentSender()).build());
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // No saved credentials found. Launch the One Tap sign-up flow, or
                        // do nothing and continue presenting the signed-out UI.
                        Log.d(TAG, e.getLocalizedMessage());
                    }
                });
    }

    private void signOutClick () {
        GoogleSignInOptions SIopts = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(new Scope(DriveScopes.DRIVE_FILE))
                .build();
        g_client = GoogleSignIn.getClient(this, SIopts);
        g_client.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        setContentView(R.layout.start);
                    }
                });
    }

    private void retrievePlayList() throws GeneralSecurityException, IOException {
        if(g_driveServ == null) {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            g_driveServ = new Drive.Builder(HTTP_TRANSPORT, Globals.JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(Globals.APP_NAME)
                    .build();
        }

        goToMain();
    }

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = StartActivity.class.getResourceAsStream(Globals.CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + Globals.CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(Globals.JSON_FACTORY, new InputStreamReader(in));

        java.io.File DATA_STORE_DIR = new java.io.File("tokens");
        FileDataStoreFactory DATA_STORE_FACTORY;
        getRelPath("test");
        DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);

        // Build flow and trigger user authorization request.
        /*GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, Globals.JSON_FACTORY, clientSecrets, Collections.singleton(DriveScopes.DRIVE))
                .setDataStoreFactory(dstfact)
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");*/
        return null;
    }

    public static String getRelPath(String abs_path) {
        GDrive.class.getClassLoader().getResourceAsStream("floodlightdefault.properties");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String url = classLoader.getResource(".").getPath();


        return url.toString();
    }

    private ActivityResultLauncher<IntentSenderRequest> loginResultHandler = registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), result -> {
        // handle intent result here
        if (result.getResultCode() == RESULT_OK) {
            try {
                retrievePlayList();
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            //...
        }
    });

    public void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}