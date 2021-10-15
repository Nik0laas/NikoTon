package be.niko.nikoton;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    List<GoogleDriveFileHolder> c_googleDrivePlayHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practice);

        findViewById(R.id.buttonLogout).setOnClickListener(this);

        Drive.About ab = Globals.g_driveServ.about();
        DriveServiceHelper driveHelp = new DriveServiceHelper(Globals.g_driveServ);
        driveHelp.searchFolder("Toneel")
                .addOnSuccessListener(this::onSuccess)
                .addOnFailureListener(this::onFail);
        /*driveHelp.queryFiles("IMP")
                .addOnSuccessListener(this::onSuccess)
                .addOnFailureListener(e -> c_googleDrivePlayHolder = null);*/
    }

    private void signOut() {
        GoogleSignInOptions SIopts = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(new Scope(DriveScopes.DRIVE_FILE))
                .build();
        GoogleSignInClient client = GoogleSignIn.getClient(this, SIopts);
        client.signOut()
                .addOnCompleteListener(this, task -> setContentView(R.layout.start));
    }

    private void onSuccess(List<GoogleDriveFileHolder> googleDrivePlayHolder) {
        int i = 1;
        c_googleDrivePlayHolder = googleDrivePlayHolder;

        for (GoogleDriveFileHolder googleDriveFileHolder : googleDrivePlayHolder) {
            String btnId = "play" + i;

            int resID = getResources().getIdentifier(btnId, "id", getPackageName());
            Button btn = findViewById(resID);
            btn.setText(googleDriveFileHolder.getName());
            btn.setOnClickListener(v -> goToPlay(googleDriveFileHolder));
        }
    }

    private void onFail(Exception e) {

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonLogout) {
            signOut();
        }
    }

    public void goToPlay(GoogleDriveFileHolder googleDrivePlayHolder) {
        setContentView(R.layout.play);

        TextView practSub = findViewById(R.id.practice_sub);
        practSub.setText(googleDrivePlayHolder.getName());
    }
}
