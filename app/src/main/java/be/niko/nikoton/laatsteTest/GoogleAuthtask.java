package be.niko.nikoton.laatsteTest;

import static be.niko.nikoton.laatsteTest.Global.clientSecrets;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.android.util.store.FileDataStoreFactory;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;

class GoogleAuthtask extends AsyncTask<String, String, Credential> {
    Exception error;
    private Context context;

    public GoogleAuthtask(Context context) { this.context = context; }

    @Override
    protected Credential doInBackground(String... strings) {
        error = null;
        Credential credential = null;
        try {
            GoogleAuthorizationCodeFlow authorisationFlow = new GoogleAuthorizationCodeFlow.Builder(
                    Global.HTTP_TRANSPORT, Global.JSON_FACTORY, clientSecrets, Global.SCOPES)
                    .setDataStoreFactory(new FileDataStoreFactory(Global.tokenFolder))
                    .setAccessType("offline")
                    .build();

            LocalServerReceiver receiver =
                    new LocalServerReceiver.Builder().setPort(8888).build();

            credential = new AuthorizationCodeInstalledApp(authorisationFlow, new LocalServerReceiver()) {

                protected void onAuthorization(AuthorizationCodeRequestUrl authorizationUrl) {
                    //                String url = authorizationUrl.build();
                    //                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    //                startActivity(browserIntent);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(authorizationUrl.build()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setPackage("com.android.chrome");
                    try {
                        context.startActivity(intent);
                    } catch (ActivityNotFoundException ex) {
                        // Chrome browser presumably not installed so allow user to choose instead
                        intent.setPackage(null);
                        context.startActivity(intent);
                    }
                }
            }.authorize("user");
        }catch(java.io.IOException ioe) {
            error = ioe;
        }
        Global.cred = credential;
        Global.authed = true;
        return credential;
    }

    @Override
    protected void onPostExecute(Credential cred) {
        super.onPostExecute(cred);

        Global.cred = cred;
        Global.authed = true;
    }
}