package be.niko.nikoton.laatsteTest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import be.niko.nikoton.R;
import be.niko.nikoton.andrFirst.PracticeActivity;

public class ImportActivity extends AppCompatActivity implements View.OnClickListener {

    Button[] m_buttons;
    List<String[]> plays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);

        m_buttons = new Button[7];
        setContentView(R.layout.list_btn);
        TextView tv1 = (TextView)findViewById(R.id.subTitle);
        tv1.setText("Welke bestand wil je importeren?");

        plays = Global.g_driveServ.readPlaysToImport();
        for(int i = 0;i < plays.size() - 1 && i < 7;i++) {
            String rID = "play" + i + 1;
            int playID = getResources().getIdentifier(rID, "id", getPackageName());
            m_buttons[i] = (Button) findViewById(playID);
            m_buttons[i].setText(plays.get(i)[1]);
            m_buttons[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < m_buttons.length; i++) {
            if (m_buttons[i].getId() == v.getId()) {

                Intent intent = new Intent(ImportActivity.this,
                        PracticeActivity.class);
                intent.putExtra("PlayID", plays.get(i)[1]);
                startActivity(intent);
            }
        }
    }
}