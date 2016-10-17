package jonathan.storybuilder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Joe on 2/26/2016.
 */
public class FinalDialog extends DialogFragment {

    protected static String mFinalQuestion;
    TextView mQuestionLabel;
    static CompleteStory mCompleteStory;
    RadioGroup mRadioGroup;
    RadioButton mSelected;
    static int points;
    int score;
    StoryPoints mStoryPoints;
    private static DataSourceManager source;
    private static ArrayList<CompleteStory> list;
    private static int storyIndex;

    public static void newInstance(String finalQuestion, String completeStory, int score, DataSourceManager s, ArrayList<CompleteStory> l) {
        source = s;
        mFinalQuestion = finalQuestion;
        //mCompleteStory = source.getCompleteStory(completeStory);
        points = score;
        list = l;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTitle().equals(completeStory))
            {
                storyIndex = i;
                mCompleteStory = list.get(i);
            }
        }

    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mStoryPoints = StoryPoints.get(getContext());
        score = mStoryPoints.getPoints();

        if(mFinalQuestion.equals("")) {

            return new AlertDialog.Builder(getActivity()).setTitle("Complete").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    source.setCompleteStatus(mCompleteStory.getTitle(),"yes", "no");
                    Log.i("Setting complete", mCompleteStory.getTitle() + " is now complete");
                    list.get(storyIndex).setComplete("yes");
                    mCompleteStory.setComplete("yes");
                    CompleteStories stories = CompleteStories.get(getContext());
                    stories.updateStory(mCompleteStory);
                    Intent intent = new Intent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("score", score);
                    intent.setClass(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            }).create();
        } else if(mFinalQuestion.equals("Who did it?")) {

            final View view = LayoutInflater.from(getActivity()).inflate(R.layout.last_dialog, null);
            mQuestionLabel = (TextView) view.findViewById(R.id.finalQuestionLabel);
            mQuestionLabel.setText(mFinalQuestion);
            mRadioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);

            return new AlertDialog.Builder(getActivity()).setView(view).setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int selectedId = mRadioGroup.getCheckedRadioButtonId();
                    if (selectedId == R.id.danny || selectedId == R.id.kristen ||
                            selectedId == R.id.coning ||selectedId == R.id.lizzy || selectedId == R.id.john
                            || selectedId == R.id.ravi || selectedId == R.id.ward || selectedId == R.id.maria  ) {
                        mStoryPoints.savePoints(score);
                        mSelected = (RadioButton) view.findViewById(selectedId);
                        String response = mSelected.getText().toString();
                        mCompleteStory.setResponse(response);
                        source.setCompleteStatus(mCompleteStory.getTitle(),"yes", response);
                        Log.i("Setting complete", mCompleteStory.getTitle() + " is now complete");
                        list.get(storyIndex).setComplete("yes");
                        list.get(storyIndex).setResponse(response);
                        mCompleteStory.setComplete("yes");
                        CompleteStories stories = CompleteStories.get(getContext());
                        stories.updateStory(mCompleteStory);
                        Intent intent = new Intent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("score", score);
                        intent.setClass(getActivity(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Incorrect. You must select a choice").setPositiveButton("Ok", null);
                        builder.show();
                    }

                }
            }).create();

        } else {


            final View view = LayoutInflater.from(getActivity()).inflate(R.layout.final_dialog, null);
            mQuestionLabel = (TextView) view.findViewById(R.id.finalQuestionLabel);
            mQuestionLabel.setText(mFinalQuestion);
            mRadioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);

            return new AlertDialog.Builder(getActivity()).setView(view).setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int selectedId = mRadioGroup.getCheckedRadioButtonId();
                    if (selectedId == R.id.danny || selectedId == R.id.radioNo) {
                        mStoryPoints.savePoints(score);
                        mSelected = (RadioButton) view.findViewById(selectedId);
                        String response = mSelected.getText().toString();
                        mCompleteStory.setResponse(response);
                        source.setCompleteStatus(mCompleteStory.getTitle(),"yes", response);
                        Log.i("Setting complete", mCompleteStory.getTitle() + " is now complete");
                        list.get(storyIndex).setComplete("yes");
                        list.get(storyIndex).setResponse(response);
                        mCompleteStory.setComplete("yes");
                        CompleteStories stories = CompleteStories.get(getContext());
                        stories.updateStory(mCompleteStory);
                        Intent intent = new Intent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("score", score);
                        intent.setClass(getActivity(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Incorrect. You must select a choice").setPositiveButton("Ok", null);
                        builder.show();
                    }

                }
            }).create();
        }
    }
}
