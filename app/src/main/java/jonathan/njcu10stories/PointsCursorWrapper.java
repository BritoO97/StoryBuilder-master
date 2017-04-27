package jonathan.njcu10stories;

import android.database.Cursor;
import android.database.CursorWrapper;

import static jonathan.njcu10stories.StoryDbSchema.PointsTable.*;

/**
 * Created by Joe on 4/4/2016.
 */

/*
    Gets the data from the db and returns it back to the StoryPoints class
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
