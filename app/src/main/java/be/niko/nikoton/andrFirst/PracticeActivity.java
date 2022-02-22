package be.niko.nikoton.andrFirst;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import be.niko.nikoton.R;
import be.niko.nikoton.laatsteTest.Global;

public class PracticeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String playID = getIntent().getExtras().getString("PlayID");
        String[] roles = read_roles(playID);
        setContentView(R.layout.roles);
    }

    String[] read_roles(String playID) {
        String playCont = Global.g_driveServ.readPlay(playID);
        return new String[0];
    }
}

