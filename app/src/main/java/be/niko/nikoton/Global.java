package be.niko.nikoton;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;

class Globals {
    public static Drive g_driveServ;
    public static DriveServiceHelper g_driveServiceHelper;
    public static boolean g_signed_in;

    public static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public static final String CREDENTIALS_FILE_PATH = "/cred/cred_NikoTon.json";
    public static final String TOKENS_DIRECTORY_PATH = "tokens";
    public static final String APP_NAME = "NikoTon";
}
