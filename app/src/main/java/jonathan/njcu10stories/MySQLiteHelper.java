package jonathan.njcu10stories;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bruno on 8/24/2016.
 * Handles the creation and structure of the local database
 */
public class MySQLiteHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "ten_stories.db";
    private static final int DATABASE_VERSION = 1;

    /*
        Handles the table creation for line# tables
     */
    public final static String L_COL1 = "choice1";
    public final static String L_COL2 = "choice2";
    public final static String L_COL3 = "choice3";
    public final static String L_COL4 = "correct";
    public final static String L_COL5 = "interaction";

    public final static String TABLE_L1 = "line1";
    private static final String TABLE_L1_CREATE = "create table " +
            TABLE_L1 + "(" + L_COL1 + " text, " + L_COL2 + " text, " + L_COL3 + " text, " +
            L_COL4 + " text, " + L_COL5 + " text" + ");";
    //Finished line1 Table

    public final static String TABLE_L2 = "line2";
    private static final String TABLE_L2_CREATE = "create table " +
            TABLE_L2 + "(" + L_COL1 + " text, " + L_COL2 + " text, " + L_COL3 + " text, " +
            L_COL4 + " text, " + L_COL5 + " text" + ");";
    //Finished line2 Table

    public final static String TABLE_L3 = "line3";
    private static final String TABLE_L3_CREATE = "create table " +
            TABLE_L3 + "(" + L_COL1 + " text, " + L_COL2 + " text, " + L_COL3 + " text, " +
            L_COL4 + " text, " + L_COL5 + " text" + ");";
    //Finished line3 Table

    public final static String TABLE_L4 = "line4";
    private static final String TABLE_L4_CREATE = "create table " +
            TABLE_L4 + "(" + L_COL1 + " text, " + L_COL2 + " text, " + L_COL3 + " text, " +
            L_COL4 + " text, " + L_COL5 + " text" + ");";
    //Finished line4 Table

    public final static String TABLE_L5 = "line5";
    private static final String TABLE_L5_CREATE = "create table " +
            TABLE_L5 + "(" + L_COL1 + " text, " + L_COL2 + " text, " + L_COL3 + " text, " +
            L_COL4 + " text, " + L_COL5 + " text" + ");";
    //Finished line5 Table

    public final static String TABLE_L6 = "line6";
    private static final String TABLE_L6_CREATE = "create table " +
            TABLE_L6 + "(" + L_COL1 + " text, " + L_COL2 + " text, " + L_COL3 + " text, " +
            L_COL4 + " text, " + L_COL5 + " text" + ");";
    //Finished line6 Table

    public final static String TABLE_L7 = "line7";
    private static final String TABLE_L7_CREATE = "create table " +
            TABLE_L7 + "(" + L_COL1 + " text, " + L_COL2 + " text, " + L_COL3 + " text, " +
            L_COL4 + " text, " + L_COL5 + " text" + ");";
    //Finished line7 Table

    public final static String TABLE_L8 = "line8";
    private static final String TABLE_L8_CREATE = "create table " +
            TABLE_L8 + "(" + L_COL1 + " text, " + L_COL2 + " text, " + L_COL3 + " text, " +
            L_COL4 + " text, " + L_COL5 + " text" + ");";
    //Finished line8 Table

    public final static String TABLE_L9 = "line9";
    private static final String TABLE_L9_CREATE = "create table " +
            TABLE_L9 + "(" + L_COL1 + " text, " + L_COL2 + " text, " + L_COL3 + " text, " +
            L_COL4 + " text, " + L_COL5 + " text" + ");";
    //Finished line9 Table

    /*
        Creates the Story and the Complete tables below
     */

    public final static String S_COL1 = "story_title";
    public final static String S_COL2 = "line1";
    public final static String S_COL3 = "line2";
    public final static String S_COL4 = "line3";
    public final static String S_COL5 = "line4";
    public final static String S_COL6 = "line5";
    public final static String S_COL7 = "line6";
    public final static String S_COL8 = "line7";
    public final static String S_COL9 = "line8";
    public final static String S_COL10 = "line9";
    public final static String S_COL11 = "final_line";
    public final static String S_COL12 = "final_response";
    public final static String S_COL13 = "complete_status";

    public final static String TABLE_COMPLETE = "complete";
    private final static String TABLE_COMPLETE_CREATE = "create table " + TABLE_COMPLETE +
            "(" + S_COL1 + " text, " + S_COL2 + " text, " + S_COL3 + " text, " +
                  S_COL4 + " text, " + S_COL5 + " text, " + S_COL6 + " text, " +
                  S_COL7 + " text, " + S_COL8 + " text, " + S_COL9 + " text, " +
                  S_COL10 + " text, " + S_COL11 + " text, " + S_COL12 + " text, " + S_COL13 + " text" + ");";

    public final static String TABLE_STORY = "story";
    private final static String TABLE_STORY_CREATE = "create table " + TABLE_STORY +
            "(" + S_COL1 + " text, " + S_COL2 + " text, " + S_COL3 + " text, " +
                  S_COL4 + " text, " + S_COL5 + " text, " + S_COL6 + " text, " +
                  S_COL7 + " text, " + S_COL8 + " text, " + S_COL9 + " text, " +
                  S_COL10 + " text, " + S_COL11 + " text" + ");";

    //finished the creation of tables complete and story here.

    public MySQLiteHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase database)
    {
        database.execSQL(TABLE_L1_CREATE);
        database.execSQL(TABLE_L2_CREATE);
        database.execSQL(TABLE_L3_CREATE);
        database.execSQL(TABLE_L4_CREATE);
        database.execSQL(TABLE_L5_CREATE);
        database.execSQL(TABLE_L6_CREATE);
        database.execSQL(TABLE_L7_CREATE);
        database.execSQL(TABLE_L8_CREATE);
        database.execSQL(TABLE_L9_CREATE);

        database.execSQL(TABLE_COMPLETE_CREATE);
        database.execSQL(TABLE_STORY_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_L1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_L2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_L3);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_L4);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_L5);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_L6);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_L7);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_L8);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_L9);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPLETE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORY);

        onCreate(db);
    }
}
