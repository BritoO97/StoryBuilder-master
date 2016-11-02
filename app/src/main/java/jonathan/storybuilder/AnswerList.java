package jonathan.storybuilder;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/*
 * Created by Joe on 2/25/2016.
 */
public class AnswerList implements Parcelable {

    static ArrayList<Answer> mAnswers;

    public AnswerList() {
        mAnswers = new ArrayList<>();
    }

    public void addAnswers(Answer answers) {
        mAnswers.add(answers);
    }
    // adds an answer to the arrayList

    public int getListSize() {
        return mAnswers.size();
    }
    // Gets the size of the array, the number of answers. Never Used

    public Answer getAnswers(int index) {

        Answer answers = mAnswers.get(index);

        return answers;
    }
    // gets a specific answer from the array. Never Used

    public ArrayList<Answer> getAllAnswers() {
        return  mAnswers;
    }
    // gets all the answers in the array

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mAnswers);
    }

    private AnswerList(Parcel in) {
        in.readTypedList(mAnswers, Answer.CREATOR);
    }
    public static final Parcelable.Creator<AnswerList> CREATOR =
            new Parcelable.Creator<AnswerList>() {
                public AnswerList createFromParcel(Parcel in) {
                    return new AnswerList(in);
                }

                public AnswerList[] newArray(int size) {
                    return new AnswerList[0];
                }
            };
}
