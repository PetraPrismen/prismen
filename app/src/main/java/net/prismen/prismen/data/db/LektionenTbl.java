package net.prismen.prismen.data.db;

/**
 * Created by iri on 13.12.15.
 */
public final class LektionenTbl {
    public static final String TABLE_NAME = "lektionen";

    public static final String SQL_CREATE =
            "CREATE TABLE lektionen (" +
                    "_id TEXT NOT NULL," +
                    "name TEXT NOT NULL,"+
                    "nb_prismen INTEGER,"+
                    "thema_id TEXT"+
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
