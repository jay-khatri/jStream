package jayk.jstream;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mFileButton;
    private Button mImuButton;
    private Button mCamButton;
    private Button mBothButton;
    private TextView mStatusView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ChngeSvc change = new ChngeSvc();

        mFileButton = (Button) findViewById(R.id.file_button);
        //setting a listener for the button
        mFileButton.setOnClickListener(new View.OnClickListener() {//sets a listener and it is from "on click listener"
        @Override
        public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,createFile.class); //this is the second activity , put it here);
                startActivity(intent);
            }
        });

        mImuButton = (Button) findViewById(R.id.imu_button);
        //setting a listener for the button
        mImuButton.setOnClickListener(new View.OnClickListener() {//sets a listener and it is from "on click listener"
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DataStream.class); //this is the second activity , put it here);
                startActivity(intent);
            }
        });

        Toast.makeText(this, "File Created with Res Id:" + change.resId, Toast.LENGTH_SHORT).show();

        }
    }


