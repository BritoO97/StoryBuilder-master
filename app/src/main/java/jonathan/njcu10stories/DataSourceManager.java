package jonathan.njcu10stories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bruno on 8/25/2016.
 * Manages the reading and writing of data to the db
 */
public class DataSourceManager
{
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private static DataSourceManager source;
    private Context context;
    private String[] allTitles = {"Story 1:  New Job", "Story 2-- Tommy is Missing", "Story 3: Wedding Date",
            "Story 4: Medical Affair", "Story 5 Dangerous Work", "Story 6: Warning to Kristen",
            "Story 7: Stolen", "Story 8: Parents’ Worries", "Story 9: Confused about Gay Marriage",
            "Story 10: Fear Loves Company", "Conclusion: Murder", "Who Did It?"};

    public static DataSourceManager get(Context context)
    {
        if(source == null) {
            source = new DataSourceManager(context);
        }

        return source;
    }

    public DataSourceManager(Context context)
    {
        this.context = context;
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException
    {
        database = dbHelper.getWritableDatabase();
    }

    public void close()
    {
        dbHelper.close();
    }

    public boolean needUpdate()
    {
        File dbFile = context.getDatabasePath("ten_stories.db");
        boolean exists = dbFile.exists();

        return !exists;

    }

    public void addStoryData (Stories stories)
    {
        /*
            Get the Stories array, loop through it and do one set of lines at a time.
         */

        // Gets the array
        ArrayList<Story> list = stories.getAllStories();

        // Loops through the array
        for (Story s : list)
        {
            String title = s.getTitle();
            int len = title.length();
            while (title.charAt(len-1) == ' ') {
                title = title.substring(0, len - 1);
                len = title.length();
            }

            s.setTitle(title);

            open();
            // for each story in the array it creates a ContentValues, puts the correct data into it and inserts into the db.
            ContentValues content = new ContentValues();
            content.put(MySQLiteHelper.S_COL1, s.getTitle());
            content.put(MySQLiteHelper.S_COL2, s.getLine1());
            content.put(MySQLiteHelper.S_COL3, s.getLine2());
            content.put(MySQLiteHelper.S_COL4, s.getLine3());
            content.put(MySQLiteHelper.S_COL5, s.getLine4());
            content.put(MySQLiteHelper.S_COL6, s.getLine5());
            content.put(MySQLiteHelper.S_COL7, s.getLine6());
            content.put(MySQLiteHelper.S_COL8, s.getLine7());
            content.put(MySQLiteHelper.S_COL9, s.getLine8());
            content.put(MySQLiteHelper.S_COL10, s.getLine9());
            content.put(MySQLiteHelper.S_COL11, s.getFinalLine());

            database.insert(MySQLiteHelper.TABLE_STORY, null, content);
            close();
        }
    }

    public void addCompleteData (CompleteStories complete) // see previous method
    {
        /*
            Get the CompletedStories array, loop through it and do one set of stories at a time.
         */

        // Gets the array
        List<CompleteStory> list = complete.getCompleteStories();

        // Loops through the array
        for (CompleteStory s : list)
        {
            String title = s.getTitle();
            int len = title.length();
            while (title.charAt(len-1) == ' ') {
                title = title.substring(0, len - 1);
                len = title.length();
            }

            s.setTitle(title);

            open();
            // for each completestory in the array it creates a ContentValues, puts the correct data into it and inserts into the db.
            ContentValues content = new ContentValues();
            content.put(MySQLiteHelper.S_COL1, s.getTitle());
            content.put(MySQLiteHelper.S_COL2, s.getLine1());
            content.put(MySQLiteHelper.S_COL3, s.getLine2());
            content.put(MySQLiteHelper.S_COL4, s.getLine3());
            content.put(MySQLiteHelper.S_COL5, s.getLine4());
            content.put(MySQLiteHelper.S_COL6, s.getLine5());
            content.put(MySQLiteHelper.S_COL7, s.getLine6());
            content.put(MySQLiteHelper.S_COL8, s.getLine7());
            content.put(MySQLiteHelper.S_COL9, s.getLine8());
            content.put(MySQLiteHelper.S_COL10, s.getLine9());
            content.put(MySQLiteHelper.S_COL11, s.getFinalLine());
            content.put(MySQLiteHelper.S_COL12, s.getResponse());
            content.put(MySQLiteHelper.S_COL13, s.getComplete());

            database.insert(MySQLiteHelper.TABLE_COMPLETE, null, content);
            close();
        }
    }

    public void addLineData (AnswerList answers)
    {
        /*
            Get the AnswerList array, loop through it and do one set of answers at a time.
         */

        // Gets the array
        ArrayList<Answer> list = answers.getAllAnswers();

        /*  looping through the array

            It goes one set of answers at a time and retrieves the data for
            answer one and inserts in the proper table, then all the data for answer 2 into the proper table etc.
         */
        open();
        for (Answer a : list)
        {
            ContentValues c1 = new ContentValues();
            c1.put(MySQLiteHelper.L_COL1, a.getAnswer1Choice1());
            c1.put(MySQLiteHelper.L_COL2, a.getAnswer1Choice2());
            c1.put(MySQLiteHelper.L_COL3, a.getAnswer1Choice3());
            c1.put(MySQLiteHelper.L_COL4, a.getAnswer1Correct());
            c1.put(MySQLiteHelper.L_COL5, a.getAnswer1Interaction());
            database.insert(MySQLiteHelper.TABLE_L1, null, c1);

            ContentValues c2 = new ContentValues();
            c2.put(MySQLiteHelper.L_COL1, a.getAnswer2Choice1());
            c2.put(MySQLiteHelper.L_COL2, a.getAnswer2Choice2());
            c2.put(MySQLiteHelper.L_COL3, a.getAnswer2Choice3());
            c2.put(MySQLiteHelper.L_COL4, a.getAnswer2Correct());
            c2.put(MySQLiteHelper.L_COL5, a.getAnswer2Interaction());
            database.insert(MySQLiteHelper.TABLE_L2, null, c2);

            ContentValues c3 = new ContentValues();
            c3.put(MySQLiteHelper.L_COL1, a.getAnswer3Choice1());
            c3.put(MySQLiteHelper.L_COL2, a.getAnswer3Choice2());
            c3.put(MySQLiteHelper.L_COL3, a.getAnswer3Choice3());
            c3.put(MySQLiteHelper.L_COL4, a.getAnswer3Correct());
            c3.put(MySQLiteHelper.L_COL5, a.getAnswer3Interaction());
            database.insert(MySQLiteHelper.TABLE_L3, null, c3);

            ContentValues c4 = new ContentValues();
            c4.put(MySQLiteHelper.L_COL1, a.getAnswer4Choice1());
            c4.put(MySQLiteHelper.L_COL2, a.getAnswer4Choice2());
            c4.put(MySQLiteHelper.L_COL3, a.getAnswer4Choice3());
            c4.put(MySQLiteHelper.L_COL4, a.getAnswer4Correct());
            c4.put(MySQLiteHelper.L_COL5, a.getAnswer4Interaction());
            database.insert(MySQLiteHelper.TABLE_L4, null, c4);

            ContentValues c5 = new ContentValues();
            c5.put(MySQLiteHelper.L_COL1, a.getAnswer5Choice1());
            c5.put(MySQLiteHelper.L_COL2, a.getAnswer5Choice2());
            c5.put(MySQLiteHelper.L_COL3, a.getAnswer5Choice3());
            c5.put(MySQLiteHelper.L_COL4, a.getAnswer5Correct());
            c5.put(MySQLiteHelper.L_COL5, a.getAnswer5Interaction());
            database.insert(MySQLiteHelper.TABLE_L5, null, c5);

            ContentValues c6 = new ContentValues();
            c6.put(MySQLiteHelper.L_COL1, a.getAnswer6Choice1());
            c6.put(MySQLiteHelper.L_COL2, a.getAnswer6Choice2());
            c6.put(MySQLiteHelper.L_COL3, a.getAnswer6Choice3());
            c6.put(MySQLiteHelper.L_COL4, a.getAnswer6Correct());
            c6.put(MySQLiteHelper.L_COL5, a.getAnswer6Interaction());
            database.insert(MySQLiteHelper.TABLE_L6, null, c6);

            ContentValues c7 = new ContentValues();
            c7.put(MySQLiteHelper.L_COL1, a.getAnswer7Choice1());
            c7.put(MySQLiteHelper.L_COL2, a.getAnswer7Choice2());
            c7.put(MySQLiteHelper.L_COL3, a.getAnswer7Choice3());
            c7.put(MySQLiteHelper.L_COL4, a.getAnswer7Correct());
            c7.put(MySQLiteHelper.L_COL5, a.getAnswer7Interaction());
            database.insert(MySQLiteHelper.TABLE_L7, null, c7);

            ContentValues c8 = new ContentValues();
            c8.put(MySQLiteHelper.L_COL1, a.getAnswer8Choice1());
            c8.put(MySQLiteHelper.L_COL2, a.getAnswer8Choice2());
            c8.put(MySQLiteHelper.L_COL3, a.getAnswer8Choice3());
            c8.put(MySQLiteHelper.L_COL4, a.getAnswer8Correct());
            c8.put(MySQLiteHelper.L_COL5, a.getAnswer8Interaction());
            database.insert(MySQLiteHelper.TABLE_L8, null, c8);

            ContentValues c9 = new ContentValues();
            c9.put(MySQLiteHelper.L_COL1, a.getAnswer9Choice1());
            c9.put(MySQLiteHelper.L_COL2, a.getAnswer9Choice2());
            c9.put(MySQLiteHelper.L_COL3, a.getAnswer9Choice3());
            c9.put(MySQLiteHelper.L_COL4, a.getAnswer9Correct());
            c9.put(MySQLiteHelper.L_COL5, a.getAnswer9Interaction());
            database.insert(MySQLiteHelper.TABLE_L9, null, c9);
        }
        close();
    }

    public Story getStory(String title)
    {
        open();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_STORY,
                null, (MySQLiteHelper.S_COL1 + " = \'" + title + "\'"), null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Story s = cursorToStory(cursor);
        if (cursor != null)
            cursor.close();
        close();

        return s;
    }

    public Answer getAnswers(String title)
    {
        open();

        int row = 0;
        for (int i = 0; i<allTitles.length; i++)
            if (title.equals(allTitles[i]))
                row = (i + 1)%13;

        Answer s = new Answer();

        for (int j = 1; j<=9; j++)
        {
            Cursor cursor = database.query(("line" + j),
                    null, null, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();

            for (int i = 1; i < row; i++) {
                if (cursor != null)
                    cursor.moveToNext();
            }

            cursorToAnswer(s, cursor, j);
            if (cursor != null)
                cursor.close();

        }

        close();

        return s;
    }

    public CompleteStory getCompleteStory(String title)
    {
        open();
        String args = "\'" + title + "\'";
        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMPLETE,
                null, MySQLiteHelper.S_COL1 + "=" + args, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        CompleteStory s = cursorToComplete(cursor);
        if (cursor != null)
            cursor.close();
        close();

        return s;
    }

    public ArrayList<CompleteStory> getAllComplete()
    {
        ArrayList<CompleteStory> list = new ArrayList<>();

        for(String s : allTitles)
        {
            list.add(getCompleteStory(s));
        }

        return list;
    }

    public Answer cursorToAnswer(Answer s, Cursor cursor, int table)
    {
        switch (table)
        {
            case 1 :
                s.setAnswer1Choice1(cursor.getString(0));
                s.setAnswer1Choice2(cursor.getString(1));
                s.setAnswer1Choice3(cursor.getString(2));
                s.setAnswer1Correct(cursor.getString(3));
                s.setAnswer1Interaction(cursor.getString(4));
                break;
            case 2 :
                s.setAnswer2Choice1(cursor.getString(0));
                s.setAnswer2Choice2(cursor.getString(1));
                s.setAnswer2Choice3(cursor.getString(2));
                s.setAnswer2Correct(cursor.getString(3));
                s.setAnswer2Interaction(cursor.getString(4));
                break;
            case 3 :
                s.setAnswer3Choice1(cursor.getString(0));
                s.setAnswer3Choice2(cursor.getString(1));
                s.setAnswer3Choice3(cursor.getString(2));
                s.setAnswer3Correct(cursor.getString(3));
                s.setAnswer3Interaction(cursor.getString(4));
                break;
            case 4 :
                s.setAnswer4Choice1(cursor.getString(0));
                s.setAnswer4Choice2(cursor.getString(1));
                s.setAnswer4Choice3(cursor.getString(2));
                s.setAnswer4Correct(cursor.getString(3));
                s.setAnswer4Interaction(cursor.getString(4));
                break;
            case 5 :
                s.setAnswer5Choice1(cursor.getString(0));
                s.setAnswer5Choice2(cursor.getString(1));
                s.setAnswer5Choice3(cursor.getString(2));
                s.setAnswer5Correct(cursor.getString(3));
                s.setAnswer5Interaction(cursor.getString(4));
                break;
            case 6 :
                s.setAnswer6Choice1(cursor.getString(0));
                s.setAnswer6Choice2(cursor.getString(1));
                s.setAnswer6Choice3(cursor.getString(2));
                s.setAnswer6Correct(cursor.getString(3));
                s.setAnswer6Interaction(cursor.getString(4));
                break;
            case 7 :
                s.setAnswer7Choice1(cursor.getString(0));
                s.setAnswer7Choice2(cursor.getString(1));
                s.setAnswer7Choice3(cursor.getString(2));
                s.setAnswer7Correct(cursor.getString(3));
                s.setAnswer7Interaction(cursor.getString(4));
                break;
            case 8 :
                s.setAnswer8Choice1(cursor.getString(0));
                s.setAnswer8Choice2(cursor.getString(1));
                s.setAnswer8Choice3(cursor.getString(2));
                s.setAnswer8Correct(cursor.getString(3));
                s.setAnswer8Interaction(cursor.getString(4));
                break;
            case 9 :
                s.setAnswer9Choice1(cursor.getString(0));
                s.setAnswer9Choice2(cursor.getString(1));
                s.setAnswer9Choice3(cursor.getString(2));
                s.setAnswer9Correct(cursor.getString(3));
                s.setAnswer9Interaction(cursor.getString(4));
                break;
        }

        return s;
    }

    public CompleteStory cursorToComplete(Cursor cursor)
    {
        CompleteStory s = new CompleteStory();
        s.setTitle(cursor.getString(0));
        s.setLine1(cursor.getString(1));
        s.setLine2(cursor.getString(2));
        s.setLine3(cursor.getString(3));
        s.setLine4(cursor.getString(4));
        s.setLine5(cursor.getString(5));
        s.setLine6(cursor.getString(6));
        s.setLine7(cursor.getString(7));
        s.setLine8(cursor.getString(8));
        s.setLine9(cursor.getString(9));
        s.setFinalLine(cursor.getString(10));
        s.setResponse(cursor.getString(11));
        s.setComplete(cursor.getString(12));

        return s;
    }

    public Story cursorToStory(Cursor cursor)
    {
        Story s = new Story();
        s.setTitle(cursor.getString(0));
        s.setLine1(cursor.getString(1));
        s.setLine2(cursor.getString(2));
        s.setLine3(cursor.getString(3));
        s.setLine4(cursor.getString(4));
        s.setLine5(cursor.getString(5));
        s.setLine6(cursor.getString(6));
        s.setLine7(cursor.getString(7));
        s.setLine8(cursor.getString(8));
        s.setLine9(cursor.getString(9));
        s.setFinalLine(cursor.getString(10));

        return s;
    }

    public Stories getAllStories()
    {
        Stories s = new Stories();

        for (String allTitle : allTitles) {
            Story temp = getStory(allTitle);
            s.addStory(temp);
        }

        return s;
    }

    public AnswerList getAllAnswers()
    {
        AnswerList s = new AnswerList();

        for (String allTitle : allTitles) {
            Answer temp = getAnswers(allTitle);
            s.addAnswers(temp);
        }

        return s;
    }

    public void setCompleteStatus(String name, String completeStatus, String finalResponse)
    {
        CompleteStory temp = getCompleteStory(name);

        ContentValues cv = new ContentValues();
        cv.put(MySQLiteHelper.S_COL1, temp.getTitle());
        cv.put(MySQLiteHelper.S_COL2, temp.getLine1());
        cv.put(MySQLiteHelper.S_COL3, temp.getLine2());
        cv.put(MySQLiteHelper.S_COL4, temp.getLine3());
        cv.put(MySQLiteHelper.S_COL5, temp.getLine4());
        cv.put(MySQLiteHelper.S_COL6, temp.getLine5());
        cv.put(MySQLiteHelper.S_COL7, temp.getLine6());
        cv.put(MySQLiteHelper.S_COL8, temp.getLine7());
        cv.put(MySQLiteHelper.S_COL9, temp.getLine8());
        cv.put(MySQLiteHelper.S_COL10, temp.getLine9());
        cv.put(MySQLiteHelper.S_COL11, temp.getFinalLine());
        cv.put(MySQLiteHelper.S_COL12, finalResponse);
        cv.put(MySQLiteHelper.S_COL13, completeStatus);

        name = "\'"+ name + "\'";
        open();
        database.update(MySQLiteHelper.TABLE_COMPLETE, cv, "story_title="+name, null);
        close();
    }

    public void setCompleteStatusAll(String cStatus)
    {
        for(String s : allTitles)
        {
            setCompleteStatus(s, cStatus, "no");
        }
    }
}
