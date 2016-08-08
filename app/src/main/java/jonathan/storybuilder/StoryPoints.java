package jonathan.storybuilder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import jonathan.storybuilder.StoryDbSchema.PointsTable;

/**
 * Created by Joe on 4/4/2016.
 */
public class StoryPoints {

    private static StoryPoints sStoryPoints;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private int mPoints;

    public static StoryPoints get(Context context) {
        if(sStoryPoints == null) {
            sStoryPoints = new StoryPoints(context);
           // sStoryPoints.getPointScore();
        }
        return sStoryPoints;
    }

    public StoryPoints(Context context) {
        mContext = context;
        Log.e("TAG", "in storipoints ");
        mDatabase = new PointBaseHelper(mContext).getWritableDatabase();
    }

    public void savePoints(int points) {
        ContentValues values = getContentValues(points);

        mDatabase.insert(PointsTable.NAME, null, values);
    }

    public void updatePoints(int points) {
        ContentValues values = getContentValues(points);

        mDatabase.update(PointsTable.NAME, values, null, null);
    }

    public static ContentValues getContentValues(int points){
        ContentValues contentValues = new ContentValues();
        contentValues.put(PointsTable.Cols.POINTS, points);
        return contentValues;
    }


    private PointsCursorWrapper queryPoints(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                PointsTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new PointsCursorWrapper(cursor);
    }

    public int getPointScore() {


        PointsCursorWrapper cursorWrapper = queryPoints(null, null);

        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
              setPoints(cursorWrapper.getResponse());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }
        return  mPoints;
    }

    public void setPoints(int points) {
        mPoints = points;
    }

    public int getPoints() {
        return mPoints;
    }
}
