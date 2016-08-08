package jonathan.storybuilder;

import android.database.Cursor;
import android.database.CursorWrapper;

import jonathan.storybuilder.StoryDbSchema.PointsTable;

import static jonathan.storybuilder.StoryDbSchema.PointsTable.*;

/**
 * Created by Joe on 4/4/2016.
 */
public class PointsCursorWrapper extends CursorWrapper {


    public PointsCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public int getResponse() {
        int points = getInt(getColumnIndex(Cols.POINTS));

        return points;
    }
}
