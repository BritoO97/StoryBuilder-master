package jonathan.storybuilder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import jonathan.storybuilder.StoryDbSchema.StoryTable;

/**
 * Created by Joe on 2/29/2016.
 */
public class StoryBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "stories.db";

    public StoryBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + StoryTable.NAME + " (" +
                        StoryTable.Cols.TITLE + ", " +
                        StoryTable.Cols.RESPONSE + ", " +
                        StoryTable.Cols.LINE1 + ", " +
                        StoryTable.Cols.LINE2 + "," +
                        StoryTable.Cols.LINE3 + "," +
                        StoryTable.Cols.LINE4 + "," +
                        StoryTable.Cols.LINE5 + "," +
                        StoryTable.Cols.LINE6 + "," +
                        StoryTable.Cols.LINE7 + "," +
                        StoryTable.Cols.LINE8 + "," +
                        StoryTable.Cols.LINE9 + "," +
                        StoryTable.Cols.FINAL_LINE  + "," +
                        StoryTable.Cols.COMPLETE + ")" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
