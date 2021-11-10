package be.niko.nikoton;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class PracticeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String playID = getIntent().getExtras().getString("PlayID");
        String[] roles = read_roles(playID);
        setContentView(R.layout.roles);
    }

    String[] read_roles(String playID) {
        String playCont = Globals.g_driveServ.readPlay(playID);
        return new String[0];
    }
}

