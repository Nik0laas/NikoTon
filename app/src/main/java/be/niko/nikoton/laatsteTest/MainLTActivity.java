package be.niko.nikoton.laatsteTest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.util.List;

import be.niko.nikoton.R;

public class MainLTActivity extends AppCompatActivity implements View.OnClickListener {

    Button[] m_buttons;
    String btnTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Global.tokenFolder = this.getExternalFilesDir(Global.TOKENS_DIRECTORY_PATH);
        if (!Global.tokenFolder.exists()) {
            Global.tokenFolder.mkdirs();
        }

        Global.appl_ctext = this.getApplicationContext();
        Global.g_driveServ = new GDrive();
        while(!Global.driveAuth) {

        }

        setContentView(R.layout.main);
        findViewById(R.id.buttonPractice).setOnClickListener(this);
        findViewById(R.id.buttonImport).setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        if(btnTxt == "") {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        btnTxt = (String) ((MaterialButton) v).getText();

        if (btnTxt.equals("Oefenen")) {

        } else {
            if (btnTxt.equals("Importeren")) {
                m_buttons = new Button[7];
                setContentView(R.layout.list_btn);
                TextView tv1 = (TextView)findViewById(R.id.subTitle);
                tv1.setText("Welke bestand wil je importeren?");

                List<String[]> plays = Global.g_driveServ.readPlaysToImport();
                for(int i = 0;i < plays.size() - 1 && i < 7;i++) {
                    String rID = "play" + i + 1;
                    int playID = getResources().getIdentifier(rID, "id", getPackageName());
                    m_buttons[i] = (Button) findViewById(playID);
                    m_buttons[i].setText(plays.get(i)[1]);
                    m_buttons[i].setOnClickListener(this);
                }
            }
        }
    }
}
