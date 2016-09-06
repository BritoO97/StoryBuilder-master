package jonathan.storybuilder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
    private String[] allTitles = {"Story 1:  New Job", "Story 2-- Tommy is Missing ", "Story 3: Wedding Date ",
            "Story 4: Medical Affair  ", "Story 5 Dangerous Work", "Story 6: Warning to Kristen",
            "Story 7: Stolen", "Story 8: Parents’ Worries ", "Story 9: Confused about Gay Marriage ",
            "Story 10: Fear Loves Company ", "Conclusion: Murder", "Who Did It? "};

    public static DataSourceManager get(Context context)
    {
        if(source == null) {
            source = new DataSourceManager(context);
        }

        return source;
    }

    public DataSourceManager(Context context)
    {
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

    /*
        I think i need to open the db before adding data to it!!
     */

    public boolean needUpdate()
    {
        boolean result = true;
        open();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_STORY,
                null, null, null, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() != 0)
        {
            String test = cursor.getString(1);
            //if (test.equalsIgnoreCase("Beth:  I’m not sure I’ll succeed at this job.  I’ve never been a waitress before."))
                //result = false;

            if (test.indexOf("Beth:") >= 0)
                result = false;
        }
        cursor.close();

        close();

        Log.i("UPDATE", "The result of update needed is " + result);
        return result;
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
        Log.i("IMPORTANT", "The title passed as a param is \'" + title + "\'");
        open();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_STORY,
                null, (MySQLiteHelper.S_COL1 + " = \'" + title + "\'"), null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Story s = cursorToStory(cursor);
        cursor.close();
        close();

        return s;
    }

    public Answer getAnswers(String title)
    {
        open();

        int row;
        switch(title)
        {
            case "Story 1:  New Job" :
                row = 1;
                break;
            case "Story 2-- Tommy is Missing " :
                row = 2;
                break;
            case "Story 3: Wedding Date " :
                row = 3;
                break;
            case "Story 4: Medical Affair  " :
                row = 4;
                break;
            case "Story 5 Dangerous Work" :
                row = 5;
                break;
            case "Story 6: Warning to Kristen" :
                row = 6;
                break;
            case "Story 7: Stolen" :
                row = 7;
                break;
            case "Story 8: Parents’ Worries " :
                row = 8;
                break;
            case "Story 9: Confused about Gay Marriage " :
                row = 9;
                break;
            case "Story 10: Fear Loves Company " :
                row = 10;
                break;
            case "Conclusion: Murder" :
                row = 11;
                break;
            case "Who Did It? " :
                row = 12;
                break;
            default:
                row = 0;
                break;
        }

        Answer s = new Answer();

        for (int j = 1; j<=9; j++)
        {
            Cursor cursor = database.query(("line" + j),
                    null, null, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();

            for (int i = 1; i < row; i++) {
                cursor.moveToNext();
            }

            cursorToAnswer(s, cursor, j);
            cursor.close();

        }

        close();

        return s;
    }

    public CompleteStory getCompleteStory(String title)
    {
        open();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMPLETE,
                null, MySQLiteHelper.S_COL1 + " = \'" + title + "\'", null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        CompleteStory s = cursorToComplete(cursor);
        cursor.close();
        close();

        return s;
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

        for (int i = 0; i < allTitles.length; i++)
        {
            Story temp = getStory(allTitles[i]);
            s.addStory(temp);
        }

        return s;
    }

    public AnswerList getAllAnswers()
    {
        AnswerList s = new AnswerList();

        for (int i = 0; i < allTitles.length; i++)
        {
            Answer temp = getAnswers(allTitles[i]);
            s.addAnswers(temp);
        }

        return s;
    }
}
