package be.niko.nikoton.laatsteTest;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import be.niko.nikoton.R;

public class PracticeActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_btn);
        TextView tv1 = (TextView)findViewById(R.id.subTitle);
        tv1.setText("Welke stuk wil je oefenen?");

        List<String[]> plays = Global.g_driveServ.readPlays();
        String[] it_play;
        for (int i = 0;i <= 5 && i < plays.size();i++) {

        }
    }
}