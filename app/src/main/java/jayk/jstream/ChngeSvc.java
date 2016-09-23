package jayk.jstream;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.drive.events.DriveEventService;

/**
 * Created by jak3779 on 8/8/16.
 */

public class ChngeSvc extends DriveEventService {
    private static final String TAG = "_X_";

    public String resId;


    @Override
    public void onCompletion(CompletionEvent event) {
        super.onCompletion(event);
        DriveId driveId = event.getDriveId();
        resId = driveId.getResourceId();

        Log.d(TAG, "onComplete: " + resId);
        switch (event.getStatus()) {
            case CompletionEvent.STATUS_CONFLICT:  Log.d(TAG, "STATUS_CONFLICT"); event.dismiss(); break;
            case CompletionEvent.STATUS_FAILURE:   Log.d(TAG, "STATUS_FAILURE");  event.dismiss(); break;
            case CompletionEvent.STATUS_SUCCESS:   Log.d(TAG, "STATUS_SUCCESS "); event.dismiss(); break;


        }
    }
}

