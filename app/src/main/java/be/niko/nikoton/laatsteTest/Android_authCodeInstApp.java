package be.niko.nikoton.laatsteTest;

import static be.niko.nikoton.laatsteTest.Global.browserIntent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import androidx.core.app.ActivityCompat;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.java6.auth.oauth2.VerificationCodeReceiver;

import java.io.IOException;

public class Android_authCodeInstApp extends AuthorizationCodeInstalledApp {
    int result;
    Activity rAct;
    static AuthrecActivity authrec;

    public Android_authCodeInstApp(Activity rAct,AuthorizationCodeFlow flow, VerificationCodeReceiver receiver) {
        super(flow, receiver);
        this.rAct = rAct;
    }

    protected void onAuthorization(AuthorizationCodeRequestUrl authorizationUrl) throws IOException {
        /*String url = authorizationUrl.build();
        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ActivityCompat.startActivityForResult(this.rAct,browserIntent, 1,null);
        //Global.appl_ctext.startActivity(browserIntent);
        int i = 0;*/
        authCall(authorizationUrl);
    }

    public static void authCall(AuthorizationCodeRequestUrl authorizationUrl) {
        authrec = new AuthrecActivity();
        String url = authorizationUrl.build();
        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ActivityCompat.startActivityForResult(authrec, browserIntent, 1, null);
        //Global.appl_ctext.startActivity(browserIntent);
        int i = 0;
    }
}

