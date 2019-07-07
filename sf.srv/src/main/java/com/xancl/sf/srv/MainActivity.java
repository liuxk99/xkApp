package com.xancl.sf.srv;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.xancl.xkutils.XancL;
import com.xancl.xkutils.installer.Installer;
import com.xancl.xkutils.installer.InstallerFactory;
import com.xancl.xkutils.storage.StorageUtil;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        final Installer mInstaller = InstallerFactory.getInstance();
        if (mInstaller != null) {
            File apkDir = mInstaller.getStorageDir(MainActivity.this);
            final File apkFile = new File(apkDir, "1.apk");
            boolean res = StorageUtil.copyRawToFile(MainActivity.this, R.raw.abc, apkFile);
            if (res) {
                Log.i(TAG, "release apk file");

                Button shareFilesButton = (Button) findViewById(R.id.btn_share_files);
                if (shareFilesButton != null) {
                    shareFilesButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(XancL.ACTION_SHARE);
                            i.addCategory(Intent.CATEGORY_DEFAULT);
                            mInstaller.modifyShareIntent(i, MainActivity.this, apkFile);

                            i.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                i.setPackage("com.xancl.sf.cli");
                            }
                            sendBroadcast(i);
                            Log.i(TAG, "sendBroadcast(" + i + ")");
                        }
                    });
                }
            }
        }
    }

}
