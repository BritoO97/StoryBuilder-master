package jonathan.storybuilder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import jonathan.storybuilder.StoryDbSchema.StoryTable;

/**
 * Created by Joe on 2/29/2016.
 */
public class CompleteStories {

    private static CompleteStories sCompleteStories;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CompleteStories get(Context context) {
        if(sCompleteStories == null) {
            sCompleteStories = new CompleteStories(context);
        }

        return sCompleteStories;
    }

    private CompleteStories(Context context) {
        mContext = context;

        mDatabase = new StoryBaseHelper(mContext).getWritableDatabase();
    }

    public void saveCompleteStories(CompleteStory c) {
        ContentValues values = getContentValues(c);

        mDatabase.insert(StoryTable.NAME, null, values);
    }

    public void updateStory(CompleteStory story) {
        String title = story.getTitle();
        ContentValues values = getContentValues(story);

        mDatabase.update(StoryTable.NAME, values, StoryTable.Cols.TITLE + " =? ", new String[] { title });
    }

    public static ContentValues getContentValues(CompleteStory story){
        ContentValues contentValues = new ContentValues();
        contentValues.put(StoryTable.Cols.TITLE, story.getTitle().toString());
        contentValues.put(StoryTable.Cols.RESPONSE, story.getResponse().toString());
        contentValues.put(StoryTable.Cols.LINE1, story.getLine1().toString());
        contentValues.put(StoryTable.Cols.LINE2, story.getLine2().toString());
        contentValues.put(StoryTable.Cols.LINE3, story.getLine3().toString());
        contentValues.put(StoryTable.Cols.LINE4, story.getLine4().toString());
        contentValues.put(StoryTable.Cols.LINE5, story.getLine5().toString());
        contentValues.put(StoryTable.Cols.LINE6, story.getLine6().toString());
        contentValues.put(StoryTable.Cols.LINE7, story.getLine7().toString());
        contentValues.put(StoryTable.Cols.LINE8, story.getLine8().toString());
        contentValues.put(StoryTable.Cols.LINE9, story.getLine9().toString());
        contentValues.put(StoryTable.Cols.FINAL_LINE, story.getFinalLine().toString());
        contentValues.put(StoryTable.Cols.COMPLETE, story.getComplete().toString());
        return contentValues;
    }


    private StoryCursorWrapper queryStories(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                StoryTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new StoryCursorWrapper(cursor);
    }

    public List<CompleteStory> getCompleteStories() {
        List<CompleteStory> mCompleteStories = new ArrayList<>();

        StoryCursorWrapper cursorWrapper = queryStories(null, null);

        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                mCompleteStories.add(cursorWrapper.getResponse());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }
        return  mCompleteStories;
    }



    }
