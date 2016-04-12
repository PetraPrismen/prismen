package net.prismen.prismen.data.db;

/**
 * Created by iri on 18.12.15.
 */

public final class SeitenTbl {
    public static final String TABLE_NAME = "seiten";

    // typ 0= einfache Antwort, 1=Karte
    public static final String SQL_CREATE =
            "CREATE TABLE seiten (" +
                    "_id TEXT NOT NULL," +
                    "frage TEXT,"+
                    "antwort TEXT NOT NULL,"+
                    "prisma_id INTEGER NOT NULL,"+
                    "known INTEGER DEFAULT 1,"+
                    "typ INTEGER"+
                    ");";

    public static final String SQL_DROP =
            "DROP TABLE IF EXISTS " +
                    TABLE_NAME;

    public static final String LEKTION_INSERT =
            "INSERT INTO "+
                    TABLE_NAME +
                    "(name) " +
                    "VALUES (?)";

}