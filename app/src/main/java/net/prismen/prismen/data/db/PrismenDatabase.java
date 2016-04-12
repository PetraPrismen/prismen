package net.prismen.prismen.data.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

/**
 * Created by iri on 12.12.15.
 */
public class PrismenDatabase extends SQLiteOpenHelper {

    private static PrismenDatabase sInstance;

    private static final String DATABASE_NAME = "prismen.db";

    private static final int DATABASE_VERSION = 13;

    public static synchronized PrismenDatabase getInstance(Context context){
        if (sInstance == null){
            sInstance = new PrismenDatabase(context.getApplicationContext());
        }
        return sInstance;
    }

    protected PrismenDatabase (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(ThemenTbl.SQL_CREATE);
        db.execSQL(LektionenTbl.SQL_CREATE);
        db.execSQL(PrismenTbl.SQL_CREATE);
        db.execSQL(SeitenTbl.SQL_CREATE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ThemenTbl.SQL_DROP);
        db.execSQL(LektionenTbl.SQL_DROP);
        db.execSQL(PrismenTbl.SQL_DROP);
        db.execSQL(SeitenTbl.SQL_DROP);
        onCreate(db);
    }


}
