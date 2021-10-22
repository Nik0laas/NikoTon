package be.niko.nikoton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.api.services.drive.DriveScopes;

import javax.annotation.Nullable;

import static be.niko.nikoton.Globals.g_driveServ;

public class StartActivity extends AppCompatActivity {

    Button signin,signout;
    private static final int RC_SIGN_IN = 9001;
    private static final int REQUEST_CODE_SIGN_IN = 1;
    private static final int REQ_ONE_TAP = 2;
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;
    private static final String TAG = "drive-quickstart";

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
                        .setServerClientId(getString(R.string.default_web_client_id))
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(true)
                        .build())
                // Automatically sign in when exactly one credential is retrieved.
                .setAutoSelectEnabled(true)
                .build();
    }

    private void signOutClick () {
        GoogleSignInOptions SIopts = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(new Scope(DriveScopes.DRIVE_FILE))
                .build();
        GoogleSignInClient client = GoogleSignIn.getClient(this, SIopts);
        client.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        setContentView(R.layout.start);
                    }
                });
    }

    private void retrievePlayList() {
        if(g_driveServ == null) {
            Log.i(TAG, "Start sign in");
            GoogleSignInOptions signInOptions =
                    new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestScopes(new Scope(DriveScopes.DRIVE_FILE))
                            .requestEmail()
                            .build();
            mGoogleSignInClient = GoogleSignIn.getClient(this, signInOptions);
            startActivityForResult(mGoogleSignInClient.getSignInIntent(), REQUEST_CODE_SIGN_IN);

        }

        goToMain();
    }

    public void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}