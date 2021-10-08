package be.niko.nikoton;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.buttonLogout).setOnClickListener(this);

        DriveServiceHelper driveHelp = new DriveServiceHelper(Globals.g_driveServ);
        driveHelp.queryFiles("IMP");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonLogout:
                signOut();
                break;
        }
    }
    private void signOut() {
        // Firebase sign out

    }
}
