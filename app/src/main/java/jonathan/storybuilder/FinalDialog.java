package jonathan.storybuilder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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

    public static void newInstance(String finalQuestion, CompleteStory completeStory, int score) {
        mFinalQuestion = finalQuestion;
        mCompleteStory = completeStory;
        points = score;
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
                        String reponse = mSelected.getText().toString();
                        mCompleteStory.setResponse(reponse);
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
                        String reponse = mSelected.getText().toString();
                        mCompleteStory.setResponse(reponse);
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
