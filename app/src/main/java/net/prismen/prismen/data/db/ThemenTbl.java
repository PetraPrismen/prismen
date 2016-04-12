package net.prismen.prismen.data.db;

/**
 * Created by iri on 13.12.15.
 */
public final class ThemenTbl {
    public static final String TABLE_NAME = "themen";

    public static final String SQL_CREATE =
            "CREATE TABLE themen (" +
                    "_id TEXT NOT NULL," +
                    "name TEXT NOT NULL,"+
                    "nb_lektion INTEGER "+
                    ");";

    public static final String SQL_DROP =
            "DROP TABLE IF EXISTS " +
                    TABLE_NAME;

    public static final String THEMA_INSERT =
            "INSERT INTO "+
                    TABLE_NAME +
                    "(name) " +
                    "VALUES (?)";
    /**
     * SQL-STATEMENT to delete a Theme by ID
     */
    public static final String THEMA_DELETE_BY_ID =
            "DELETE "+  TABLE_NAME  +
                    "WHERE _id = ?";

}
