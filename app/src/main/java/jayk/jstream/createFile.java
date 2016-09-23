package jayk.jstream;

import android.content.Intent;
import  android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveFolder.DriveFileResult;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.ExecutionOptions;
import com.google.android.gms.drive.MetadataChangeSet;
import jayk.jstream.baseDrive;

/**
 * Created by jak3779 on 8/16/16.
 */
public class createFile extends baseDrive {
    private static final String TAG = "_X_";

    @Override
    public void onConnected(Bundle connectionHint) {
        super.onConnected(connectionHint);
        Toast.makeText(this, "activity2" , Toast.LENGTH_SHORT).show();

        MetadataChangeSet meta = new MetadataChangeSet.Builder()
                .setTitle("EmptyFile.txt").setMimeType("text/plain")
                .build();

        Drive.DriveApi.getRootFolder(getGoogleApiClient())
                .createFile(getGoogleApiClient(), meta, null,
                        new ExecutionOptions.Builder()
                                .setNotifyOnCompletion(true)
                                .build()
                )
                .setResultCallback(new ResultCallback<DriveFolder.DriveFileResult>() {
                    @Override
                    public void onResult(DriveFolder.DriveFileResult result) {
                        if (result.getStatus().isSuccess()) {
                            DriveId driveId = result.getDriveFile().getDriveId();
                            Log.d(TAG, "Created a empty file: " + driveId);
                            DriveFile file = Drive.DriveApi.getFile(getGoogleApiClient(), driveId);
                            file.addChangeSubscription(getGoogleApiClient());
//                            Intent intent = new Intent(createFile.this,MainActivity.class); //this is the second activity , put it here);
//                            startActivity(intent);
                        }
                    }
                });
    }
}