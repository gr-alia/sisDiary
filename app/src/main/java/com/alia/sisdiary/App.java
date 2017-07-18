package com.alia.sisdiary;

import android.app.Application;


import com.alia.sisdiary.model.DaoMaster;
import com.alia.sisdiary.model.DaoSession;

import org.greenrobot.greendao.database.Database;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class App extends Application {
    private DaoSession mDaoSession;
    @Override
    public void onCreate() {
        super.onCreate();

        setFonts();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "sisdiary-db");
        Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();

    }
    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    private void setFonts(){
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Lato-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
