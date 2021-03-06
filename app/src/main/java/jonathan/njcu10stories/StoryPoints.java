package jonathan.njcu10stories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import jonathan.njcu10stories.StoryDbSchema.PointsTable;

/**
 * Created by Joe on 4/4/2016.
 */

/*
    Stores the points that the user has won in certain stories
    Stores in a database and uses a PointCursorWrapper to do so.
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
        mDatabase = new PointBaseHelper(mContext).getWritableDatabase();
    }

    /*
    saves the data to the db using a ContentValues
     */

    public void savePoints(int points) {
        ContentValues values = getContentValues(points);

        mDatabase.insert(PointsTable.NAME, null, values);
    }

    public void updatePoints(int points) {
        ContentValues values = getContentValues(points);

        mDatabase.update(PointsTable.NAME, values, null, null);
    }

    /*
    Gets the point data through a ContentValues class
     */

    public static ContentValues getContentValues(int points){
        ContentValues contentValues = new ContentValues();
        contentValues.put(PointsTable.Cols.POINTS, points);
        return contentValues;
    }

    /*
    Tells the PointCursorWrapper where to look for the point score in the db
     */

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

    /*
    Gets the point score from the db using a cursor wrapped in the PointCursorWrapper
     */

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

    /*
    Sets and gets the points in the class (not the db)
     */

    public void setPoints(int points) {
        mPoints = points;
    }

    public int getPoints() {
        return mPoints;
    }
}
