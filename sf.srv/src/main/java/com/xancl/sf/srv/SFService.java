package com.xancl.sf.srv;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.xancl.xkutils.installer.Installer;
import com.xancl.xkutils.installer.InstallerFactory;
import com.xancl.xkutils.storage.StorageUtil;

import java.io.File;

public class SFService extends Service {
    private static final String TAG = SFService.class.getSimpleName();
    /*
    adb -s $device shell am startservice -a "xancl.intent.action.share" com.xancl.sf.srv/.SFService
    adb -s $device logcat -v threadtime -s SFService:V SFReceiver:V *:S
     */
    private static final String ACTION_SHARE = "xancl.intent.action.share";
    private Installer mInstaller = InstallerFactory.getInstance();
    private File mApkFile = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand(" + intent + ", " + flags + ", " + startId + ")");

        String action = intent.getAction();
        if (action != null && action.equals(ACTION_SHARE)) {
            doAction(SFService.this);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public SFService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    void doAction(Context context) {
        boolean res = genApkFile(context);
        if (res) {
            Intent i = new Intent(XancL.ACTION_SHARE_FILE);
            i.addCategory(Intent.CATEGORY_DEFAULT);
            i.addFlags(Intent.FLAG_EXCLUDE_STOPPED_PACKAGES);
//            mInstaller.modifyShareIntent(i, context, mApkFile);

            Log.i(TAG, "intent: " + i);
            sendBroadcast(i);
        }
    }

    private boolean genApkFile(Context context) {
        boolean res = false;
        if (mInstaller != null) {
            File apkDir = mInstaller.getStorageDir(context);
            mApkFile = new File(apkDir, "1.apk");
            res = StorageUtil.copyRawToFile(context, R.raw.abc, mApkFile);
            if (res) {
                Log.i(TAG, "release apk file");
            }
        }
        return res;
    }
}
