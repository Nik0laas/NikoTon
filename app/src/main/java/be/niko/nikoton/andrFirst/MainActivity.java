package be.niko.nikoton.andrFirst;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.util.List;

import be.niko.nikoton.R;
import be.niko.nikoton.laatsteTest.GDrive;
import be.niko.nikoton.laatsteTest.Global;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button[] m_buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        Global.g_driveServ = new GDrive();
        findViewById(R.id.buttonPractice).setOnClickListener(this);
        findViewById(R.id.buttonImport).setOnClickListener(this);
        System.out.println("UD: ActionLIst");
    }

    @Override
    public void onClick(View v) {
        String btnTxt = (String) ((MaterialButton) v).getText();

        if (btnTxt.equals("Oefenen")) {
            setContentView(R.layout.list_btn);
            TextView tv1 = (TextView)findViewById(R.id.subTitle);
            tv1.setText("Welke stuk wil je oefenen?");


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
            } else {
                List<String[]> plays = Global.g_driveServ.readPlaysToImport();
                for (int i = 0; i < m_buttons.length; i++) {
                    if (m_buttons[i].getId() == v.getId()) {

                        Intent intent = new Intent(MainActivity.this,
                                PracticeActivity.class);
                        intent.putExtra("PlayID", plays.get(i)[1]);
                        startActivity(intent);
                    }
                }
            }
        }
    }
}
