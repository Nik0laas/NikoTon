package be.niko.nikoton.laatsteTest;

import android.content.Context;
import android.os.AsyncTask;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.IOException;
import java.util.List;

public class GoogleListFolderTask extends AsyncTask<String, String, List<File>> {
    Exception error;
    private Context context;
    private Drive drive;

    public GoogleListFolderTask(Context context, Drive drive) { this.context = context; this.drive = drive; }

    @Override
    protected List<File> doInBackground(String... strings) {
        FileList result = null;
        try {
            result = drive.files().list()
                    .setQ("'1JvnzFrmDBNBQBs572DJp8pj605lHrxBb' in parents")
                    .setPageSize(10)
                    .setFields("nextPageToken, files(id, name)")
                    .execute();
        } catch (IOException e) {
            error = e;
        }
        return result.getFiles();
    }

    @Override
    protected void onPostExecute(List<File> flist) {
        super.onPostExecute(flist);

        Global.files = flist;
    }

}
