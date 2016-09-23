package jayk.jstream;

/**
 * Created by jak3779 on 8/16/16.
 */
/**
 * Copyright 2013 Google Inc. All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveApi.DriveContentsResult;
import com.google.android.gms.drive.DriveApi.DriveIdResult;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;

import static jayk.jstream.R.attr.title;


/**
 * An activity to illustrate how to edit contents of a Drive file.
 */
public class DataStream extends baseDrive{

    private static final String TAG = "EditContentsActivity";


    @Override
    public void onConnected(Bundle connectionHint) {
        super.onConnected(connectionHint);
        String EXISTING_FILE_ID = "0B2CjzfTp5tfjTTB0bU94QndIa1U";


        final ResultCallback<DriveApi.DriveIdResult> idCallback = new ResultCallback<DriveApi.DriveIdResult>() {
            @Override
            public void onResult(DriveApi.DriveIdResult result) {
                if (!result.getStatus().isSuccess()) {
                    showMessage("Cannot find DriveId. Are you authorized to view this file?");
                    return;
                }
                DriveId driveId = result.getDriveId();
                DriveFile file = driveId.asDriveFile();
                new EditContentsAsyncTask(DataStream.this).execute(file);
            }
        };
        Drive.DriveApi.fetchDriveId(getGoogleApiClient(), EXISTING_FILE_ID)
                .setResultCallback(idCallback);
    }

    public class EditContentsAsyncTask extends ApiClientAsyncTask<DriveFile, Void, Boolean> {

        public EditContentsAsyncTask(Context context) {
            super(context);
        }

        @Override
        protected Boolean doInBackgroundConnected(DriveFile... args) {
            DriveFile file = args[0];
            OutputStream outputStream = null;
            DataOutputStream dos= null;
            BufferedOutputStream bos = null;
            try {
                DriveApi.DriveContentsResult driveContentsResult = file.open(
                        getGoogleApiClient(), DriveFile.MODE_WRITE_ONLY, null).await();
                if (!driveContentsResult.getStatus().isSuccess()) {
                    return false;
                }
                DriveContents driveContents = driveContentsResult.getDriveContents();
                outputStream = driveContents.getOutputStream();
                bos = new BufferedOutputStream(outputStream);
                dos = new DataOutputStream(bos);
                String nl = System.getProperty("line.separator");
                for(int x = 0; x<5; x = x+1){
                    dos.writeBytes("Hello");
                    dos.writeBytes(nl);
                }
                dos.flush();
                com.google.android.gms.common.api.Status status =
                        driveContents.commit(getGoogleApiClient(), null).await();
                return status.getStatus().isSuccess();
            } catch (IOException e) {
                Log.e(TAG, "IOException while appending to the output stream", e);
            } finally {
                try {dos.close();
                    bos.close();
                    outputStream.close();
                }
                catch(Exception e) {
                }

            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (!result) {
                showMessage("Error while editing contents");
                return;
            }
            showMessage("Successfully edited contents");
        }

//        @Override
//        public void onSensorChanged(SensorEvent event) {
//
//
//            String sensorName = event.sensor.getName();
//            // int sensorType = event.sensor.getType();

//        String gravity = "gravity";
//        String accel = "linear";


//        if (sensorType == Sensor.TYPE_GYROSCOPE_UNCALIBRATED) {

//            String a = String.valueOf(event.values[0]);
//            String b = String.valueOf(event.values[1]);
//            String c = String.valueOf(event.values[2]);
//
//            title.setText(sensorName);
//            xText.setText(a);
//            yText.setText(b);
//            zText.setText(c);
//
//            Log.i(sensorName, a);

//            }
//        if (sensorType == Sensor.TYPE_GYROSCOPE) {
//
//                String a1 = String.valueOf(event.values[0]);
//                String b1 = String.valueOf(event.values[1]);
//                String c1 = String.valueOf(event.values[2]);
//
//                title1.setText(sensorName);
//                xText1.setText(a1);
//                yText1.setText(b1);
//                zText1.setText(c1);
//                String Tag2 = "222222: ";
//                Log.i(Tag2, a1);
//
//        }

        }

//        @Override
//        public void onAccuracyChanged(Sensor arg0, int arg1) {
//            //nothing happens here
        }


