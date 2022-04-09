package com.ardeveloper.plut;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ardeveloper.plut.data.db.DaoMaster;
import com.ardeveloper.plut.data.db.DaoSession;

public class MainApp extends Application {

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private SQLiteDatabase db;
    private String dbName;

    private static Context context;
    private static MainApp mInstance;
    @Override
    public void onCreate() {
        super.onCreate();

        initDAO();
        mInstance = this;
        MainApp.context = getApplicationContext();


    }

    public static synchronized MainApp getInstance() {
        return mInstance;
    }

    private void initDAO() {

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "plut_db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();

    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}


