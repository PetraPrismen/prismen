package net.prismen.prismen.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.prismen.prismen.data.Lektion;
import net.prismen.prismen.data.LektionDataContract;

import java.util.ArrayList;

/**
 * Created by iri on 17.03.16.
 */
public class LektionDataDB extends PrismenDatabase implements LektionDataContract {


    private static final String[] TBL_COLUMNS = new String[]{
            "_id",
            "name",
            "nb_prismen",
            "thema_id"
    };

    public LektionDataDB(Context context) {
        super(context);
    }

    @Override
    public ArrayList<Lektion> getLektionenForThema(String thema_id) throws Exception {
        SQLiteDatabase dbConn = this.getReadableDatabase();
        Cursor lektionenCursor = dbConn.query(LektionenTbl.TABLE_NAME, TBL_COLUMNS, "thema_id = ?", new String[]{thema_id}, null, null, null);
        ArrayList<Lektion> lektionen = new ArrayList<Lektion>();
        if (lektionenCursor.getCount() >= 1) {
            lektionenCursor.moveToFirst();
            String id = lektionenCursor.getString(0);
            String name = lektionenCursor.getString(1);
            int nb_prismen = lektionenCursor.getInt(2);
            Lektion lk = new Lektion(id, name, nb_prismen, thema_id);
            lektionen.add(lk);
            while (lektionenCursor.moveToNext()) {
                id = lektionenCursor.getString(0);
                name = lektionenCursor.getString(1);
                nb_prismen = lektionenCursor.getInt(2);
                lk = new Lektion(id, name, nb_prismen, thema_id);
                lektionen.add(lk);
            }
        }
        dbConn.close();
        return lektionen;
    }

    @Override
    public void deleteLektion(String id) throws Exception {
        SQLiteDatabase dbConn = this.getWritableDatabase();
        dbConn.delete(LektionenTbl.TABLE_NAME,"_id=?",new String[]{id});
        dbConn.close();
        // TODO reduce one in number Lektionen for Thema
    }

    @Override
    public void renameLektion(String id, String newName) throws Exception {
        final ContentValues werte = new ContentValues();
        werte.put("name",newName);
        SQLiteDatabase dbConn = this.getWritableDatabase();
        dbConn.update(LektionenTbl.TABLE_NAME,werte,"_id=?",new String[]{id});
        dbConn.close();

    }

    @Override
    public void insertLektion(Lektion lk) throws Exception {
        String thema_id = lk.getThema_id();
        final ContentValues werte = new ContentValues();
        werte.put("_id",lk.getId());
        werte.put("name", lk.getName());
        werte.put("nb_prismen", lk.getNb_prismen());
        werte.put("thema_id", thema_id);
        SQLiteDatabase dbConn = this.getWritableDatabase();
        dbConn.insert(LektionenTbl.TABLE_NAME, null, werte);

        dbConn.close();
    }
}
