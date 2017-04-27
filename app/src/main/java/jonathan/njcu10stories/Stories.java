package jonathan.njcu10stories;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

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
    // Adds a story to the array of stories

    public int getStorySize() {
        return mStories.size();
    }
    // retrieves the size of the array, meaning how many stories are present

    public Story getStory(int index) {

        Story story = mStories.get(index);

        return story;
    }
    // retrieves a single story from the array based on an index num

    public ArrayList<Story> getAllStories() {
        return mStories;
    }
    // returns all stories in the array


    /*
        Need work to understand parcelable
     */
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
