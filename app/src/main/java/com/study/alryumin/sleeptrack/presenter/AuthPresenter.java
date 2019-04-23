package com.study.alryumin.sleeptrack.presenter;

import com.study.alryumin.sleeptrack.contract.AuthContract;

import java.io.IOException;

public class AuthPresenter implements AuthContract.authPresenter {
    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }
}
