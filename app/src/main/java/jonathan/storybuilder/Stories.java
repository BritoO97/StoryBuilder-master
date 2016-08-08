package jonathan.storybuilder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import jonathan.storybuilder.StoryDbSchema.StoryTable;

/**
 * Created by Joe on 2/25/2016.
 */
public class Stories implements Parcelable {

    private static ArrayList<Story> mStories;


    public Stories() {
        mStories = new ArrayList<>();

    }


    public void addStory(Story story) {
        mStories.add(story);
    }

    public int getStorySize() {
        return mStories.size();
    }

    public Story getStory(int index) {

        Story story = mStories.get(index);

        return story;
    }

    public ArrayList<Story> getAllStories() {
        return mStories;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mStories);
    }

    private Stories(Parcel in) {
        in.readTypedList(mStories, Story.CREATOR );
    }

    public static final Parcelable.Creator<Stories> CREATOR =
            new Parcelable.Creator<Stories>() {
                public Stories createFromParcel(Parcel in) {
                    return new Stories(in);
                }

                public Stories[] newArray(int size) {
                    return new Stories[0];
                }
            };
}
