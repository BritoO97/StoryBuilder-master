package jonathan.storybuilder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import jonathan.storybuilder.StoryDbSchema.PointsTable;

/**
 * Created by Joe on 4/4/2016.
 */
public class PointBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "points.db";

    public PointBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + PointsTable.NAME + " ( " +
                            PointsTable.Cols.POINTS + " )"  );



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
