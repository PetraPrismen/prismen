package net.prismen.prismen.data.db;

/**
 * Created by iri on 16.12.15.
 */

public final class PrismenTbl {

    public static final String TABLE_NAME = "prismen";

    public static final String SQL_CREATE =
            "CREATE TABLE prismen (" +
                    "_id TEXT NOT NULL," +
                    "name TEXT NOT NULL,"+
                    "nb_seiten INTEGER,"+
                    "seiten TEXT,"+
                    "lektion_id INTEGER NOT NULL"+
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
