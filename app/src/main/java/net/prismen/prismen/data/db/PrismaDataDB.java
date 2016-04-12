package net.prismen.prismen.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.util.Log;

import net.prismen.prismen.data.Prisma;
import net.prismen.prismen.data.PrismaDataContract;
import net.prismen.prismen.data.Seite;

import java.util.ArrayList;

/**
 * Created by iri on 18.03.16.
 */
public class PrismaDataDB extends PrismenDatabase implements PrismaDataContract {

    private static final String[] PRISMEN_TBL_COLUMNS = new String[]{
            "_id",
            "name",
            "nb_seiten"
    };


    private static final String[] SEITEN_TBL_COLUMNS = new String[]{
            "_id",
            "frage",
            "antwort",
            "known",
            "typ"
    };



    public PrismaDataDB(Context context){
        super(context);
    }

    @Override
    public ArrayList<Prisma> getPrismenForLektion(String lektion_id) throws Exception {
        SQLiteDatabase dbConn = this.getReadableDatabase();
        Cursor prismenCursor = dbConn.query(PrismenTbl.TABLE_NAME, PRISMEN_TBL_COLUMNS,
                "lektion_id = ?", new String[]{lektion_id}, null, null, null, null);
        ArrayList<Prisma> prismen = new ArrayList<Prisma>();
        if (prismenCursor.getCount() >= 1) {
            prismenCursor.moveToFirst();
            String id = prismenCursor.getString(0);
            String name = prismenCursor.getString(1);
            int nb_seiten = prismenCursor.getInt(2);
            Prisma pr = new Prisma(id, name, nb_seiten, lektion_id);
            prismen.add(pr);
            while (prismenCursor.moveToNext()) {
                id = prismenCursor.getString(0);
                name = prismenCursor.getString(1);
                nb_seiten = prismenCursor.getInt(2);
                pr = new Prisma(id, name, nb_seiten, lektion_id);
                prismen.add(pr);
            }
        }
        dbConn.close();
        return prismen;
    }

    @Override
    public void deletePrisma(String id) throws Exception {
        SQLiteDatabase dbConn = this.getWritableDatabase();
        dbConn.delete(PrismenTbl.TABLE_NAME,"_id=?",new String[]{id});
        dbConn.delete(SeitenTbl.TABLE_NAME,"prisma_id=?",new String[]{id});
        dbConn.close();
        //TODO decrement nb Prismen for Lektion
    }

    @Override
    public void renamePrisma(String id, String newName) throws Exception {
        final ContentValues werte = new ContentValues();
        werte.put("name",newName);
        SQLiteDatabase dbConn = this.getWritableDatabase();
        dbConn.update(PrismenTbl.TABLE_NAME,werte,"_id=?",new String[]{id});
        dbConn.close();
    }

    @Override
    public void insertPrisma(Prisma pr) throws Exception {
        String lektion_id = pr.getLektion_id();
        final ContentValues werte = new ContentValues();
        werte.put("_id",pr.getId());
        werte.put("name",pr.getName());
        werte.put("nb_seiten",pr.getNb_seiten());
        werte.put("lektion_id",lektion_id);
        SQLiteDatabase dbConn = this.getWritableDatabase();
        dbConn.insert(PrismenTbl.TABLE_NAME,null,werte);
        dbConn.close();
        // TODO increment nb Prismen for Lektion
    }

    @Override
    public ArrayList<Seite> getSeitenForPrisma(String prisma_id) throws Exception {
        SQLiteDatabase dbConn = this.getReadableDatabase();
        Cursor seitenCursor = dbConn.query(SeitenTbl.TABLE_NAME, SEITEN_TBL_COLUMNS,
                "prisma_id = ?", new String[]{prisma_id}, null, null, null, null);
        Log.i("***getSeitenForPrisma","nb seiten: "+ seitenCursor.getCount());
        ArrayList<Seite> seiten = new ArrayList<Seite>();
        if (seitenCursor.getCount() >= 1) {
            seitenCursor.moveToFirst();
            String id = seitenCursor.getString(0);
            String frage = seitenCursor.getString(1);
            String antwort = seitenCursor.getString(2);
            int known = seitenCursor.getInt(3);
            int typ = seitenCursor.getInt(4);
            Seite st = new Seite(id, frage, antwort, prisma_id, known, typ);
            seiten.add(st);
            while (seitenCursor.moveToNext()) {
                id = seitenCursor.getString(0);
                frage = seitenCursor.getString(1);
                antwort = seitenCursor.getString(2);
                known = seitenCursor.getInt(3);
                typ = seitenCursor.getInt(4);
                st = new Seite(id, frage, antwort, prisma_id, known, typ);
                seiten.add(st);
            }
        }
        dbConn.close();
        return seiten;
    }

    @Override
    public void deleteSeite(String seite_id) throws Exception {
        SQLiteDatabase dbConn = this.getWritableDatabase();
        dbConn.delete(SeitenTbl.TABLE_NAME,"_id=?",new String[]{seite_id});
        dbConn.close();
        //TODO decrement nb Seiten for Prisma
    }

    @Override
    public void updateSeite(String id, String frage, String antwort, int typ) throws Exception {
        final ContentValues werte = new ContentValues();
        werte.put("frage",frage);
        werte.put("antwort",antwort);
        werte.put("typ",typ);
        SQLiteDatabase dbConn = this.getWritableDatabase();
        dbConn.update(SeitenTbl.TABLE_NAME,werte,"_id=?",new String[]{id});
        dbConn.close();
    }

    @Override
    public void insertSeite(String id_prisma, Seite st) {
        final ContentValues werte = new ContentValues();
        werte.put("frage",st.getFrage());
            werte.put("antwort",st.getAntwort());
        werte.put("typ",st.getTyp());
        werte.put("_id",st.getId());
            werte.put("known",st.getKnown());
        werte.put("prisma_id",id_prisma);
        SQLiteDatabase dbConn = this.getWritableDatabase();
        dbConn.insert(SeitenTbl.TABLE_NAME, null, werte);
        dbConn.close();
        // TODO increment nb Seiten for prisma
    }


}
