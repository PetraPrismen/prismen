package net.prismen.prismen.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.prismen.prismen.data.Thema;
import net.prismen.prismen.data.ThemaDataContract;

import java.util.ArrayList;

/**
 * Created by iri on 07.03.16.
 */
public class ThemaDataDB extends PrismenDatabase implements ThemaDataContract {

    private static final String[] TBL_COLUMNS = new String[]{
            "_id",
            "name",
            "nb_lektion"
    };

    public ThemaDataDB(Context ctx){
        super(ctx);
    }


    @Override
    public ArrayList<Thema> getAllThemen() throws Exception {
        SQLiteDatabase dbConn = this.getReadableDatabase();
        Cursor themenCursor = dbConn.query(ThemenTbl.TABLE_NAME, TBL_COLUMNS, null, null, null, null, null);
        ArrayList<Thema> themen= new ArrayList<Thema>();
        if (themenCursor.getCount() >= 1) {
            themenCursor.moveToFirst();
            String name = themenCursor.getString(1);
            String id = themenCursor.getString(0);
            int nb_lektionen = themenCursor.getInt(2);
            Thema th = new Thema(id, name, nb_lektionen);
            themen.add(th);
            while (themenCursor.moveToNext()) {
                name = themenCursor.getString(1);
                id = themenCursor.getString(0);
                nb_lektionen = themenCursor.getInt(2);
                th = new Thema(id, name, nb_lektionen);
                themen.add(th);
            }
        }
        dbConn.close();
        return themen;
    }

    @Override
    public void deleteThema(String id) throws Exception {
        SQLiteDatabase dbConn = this.getWritableDatabase();
        dbConn.delete(ThemenTbl.TABLE_NAME, "_id=?", new String[]{id});
        dbConn.close();
    }

    @Override
    public void renameThema(String id, String newName) throws Exception {
        SQLiteDatabase dbConn = this.getWritableDatabase();
        final ContentValues werte = new ContentValues();
        werte.put("name", newName);
        dbConn.update(ThemenTbl.TABLE_NAME, werte, "_id=?", new String[]{id});
        dbConn.close();
    }

    @Override
    public void insertThema(Thema th) throws Exception {
        SQLiteDatabase dbConn = this.getWritableDatabase();
        final ContentValues werte = new ContentValues();
        werte.put("nb_lektion", th.getNb_lektionen());
        werte.put("name", th.getName());
        werte.put("_id",th.getId());
        long id = dbConn.insert(ThemenTbl.TABLE_NAME, null, werte);
        dbConn.close();
    }
}
