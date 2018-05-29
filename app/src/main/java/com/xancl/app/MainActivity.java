package com.xancl.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.xancl.xkutils.installer.Installer;
import com.xancl.xkutils.installer.InstallerFactory;
import com.xancl.xkutils.storage.StorageUtil;

import java.io.File;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String APK_FILE_NAME = "1.apk";
    private Installer mInstaller;
    private File mApkFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        mInstaller = InstallerFactory.getInstance();
        if (mInstaller != null) {
            File apkDir = mInstaller.getStorageDir(this);
            mApkFile = new File(apkDir, APK_FILE_NAME);

            Log.i(TAG, "(1)mApkFile: " + mApkFile);

            boolean res = StorageUtil.copyRawToFile(MainActivity.this, R.raw.abc, mApkFile);
            if (res) {
                Button helloButton = findViewById(R.id.btn_hello);
                if (helloButton != null) {
                    helloButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d(TAG, "(2)mApkFile: " + mApkFile);
                            mInstaller.install(MainActivity.this, mApkFile);
                        }
                    });
                }
            }

            Button startButton = findViewById(R.id.btn_start);
            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(MainActivity.this, SubActivity.class);
                    startActivity(i);
                }
            });
        }

    }
}
