package be.niko.nikoton.laatsteTest;

import android.app.Activity;
import android.content.Intent;

public class AuthrecActivity extends Activity {

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            String message=data.getStringExtra("MESSAGE");

        }
    }
}