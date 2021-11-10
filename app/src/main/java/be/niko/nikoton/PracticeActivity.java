package be.niko.nikoton;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class PracticeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String playText = getIntent().getExtras().getString("Play");
        String[] roles = read_roles(playText);
        setContentView(R.layout.roles);
    }

    String[] read_roles(String playTitle) {

        return new String[0];
    }
}

