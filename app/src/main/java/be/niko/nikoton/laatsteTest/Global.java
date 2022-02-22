package be.niko.nikoton.laatsteTest;

import android.content.Context;
import android.content.Intent;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

import java.util.Collections;
import java.util.List;

public class Global {
    public static GDrive g_driveServ;
    public static boolean g_signed_in;

    public static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    public static NetHttpTransport HTTP_TRANSPORT = null;
    public static GoogleClientSecrets clientSecrets;

    public static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_METADATA_READONLY);
    //private static final String CREDENTIALS_FILE_PATH = "C:/Users/NH/AndroidStudioProjects/NikoTon/app/src/practice/Resources/conf/cred_nikoton.json";

    public static final String CREDENTIALS_FILE_PATH = "/cred/cred_NikoTon.json";
    public static final String APP_NAME = "NikoTon";

    public static java.io.File tokenFolder;
    public static final String APPLICATION_NAME = "NikoTon";
    public static final String TOKENS_DIRECTORY_PATH = "tokens";
    public static Credential cred = null;
    public static List<File> files;
    public static boolean authed;
    public static boolean driveAuth;
    public static Context appl_ctext;
    public static Intent browserIntent;
}
