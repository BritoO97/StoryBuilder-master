package jonathan.storybuilder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created by Joe on 2/26/2016.
 */
public class MultiChoiceDialog extends DialogFragment {

    private static final String CHOICE_1 = "c1";
    private static final String CHOICE_2 = "c2";
    private static final String CHOICE_3 = "c3";
    private static final String CHOICE_CORRECT = "c4";
    private static final String ROW = "row";
    private static final String ANSWERED = "answered";
    RadioButton choice1;
    RadioButton choice2;
    RadioButton choice3;
    RadioGroup choices;
    View view;
    private int score;
    static boolean notAnswered = true;
    StoryPoints mStoryPoints;
    static int chances = 0;



    public static MultiChoiceDialog newInstance(String c1, String c2, String c3, String correct, String row){
        Bundle args = new Bundle();
        args.putSerializable(CHOICE_1, c1);
        args.putSerializable(CHOICE_2, c2);
        args.putSerializable(CHOICE_3, c3);
        args.putSerializable(CHOICE_CORRECT, correct);
        args.putSerializable(ROW, row);

        MultiChoiceDialog fragment = new MultiChoiceDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String c1 = (String) getArguments().getSerializable(CHOICE_1);
        String c2 = (String) getArguments().getSerializable(CHOICE_2);
        String c3 = (String) getArguments().getSerializable(CHOICE_3);
        String row = (String) getArguments().getSerializable(ROW);
        final String correct = (String) getArguments().getSerializable(CHOICE_CORRECT);

        view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_choice, null);

        choices = (RadioGroup) view.findViewById(R.id.radioChoices);

        choice1 = (RadioButton) view.findViewById(R.id.radioChoice1);
        choice2 = (RadioButton) view.findViewById(R.id.radioChoice2);
        choice3 = (RadioButton) view.findViewById(R.id.radioChoice3);
        choice1.setText(c1);
        choice2.setText(c2);
        choice3.setText(c3);
       // mTextView = (TextView) view.findViewById(R.id.storyText);
       // mTextView.setText(row);
        mStoryPoints = StoryPoints.get(getContext());
        score = mStoryPoints.getPoints();


        return new AlertDialog.Builder(getActivity()).setView(view).setMessage(row).setPositiveButton("Submit",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int selectedId = choices.getCheckedRadioButtonId();

                        switch (selectedId) {
                            case R.id.radioChoice1:
                                if (choice1.getText().toString().equals(correct)) {

                                    correctDialog();
                                    StoryFragment.get();
                                    updateScore();
                                    notAnswered = false;

                                    chances = 0;
                                } else {
                                    incorrectDialog();
                                    chances++;
                                }
                                break;
                            case R.id.radioChoice2:
                                if (choice2.getText().toString().equals(correct)) {
                                    correctDialog();
                                    StoryFragment.get();
                                    updateScore();
                                    notAnswered = false;

                                    chances = 0;
                                } else {
                                    incorrectDialog();
                                    chances++;
                                }
                                break;
                            case R.id.radioChoice3:
                                if (choice3.getText().toString().equals(correct)) {

                                    correctDialog();
                                    StoryFragment.get();
                                    updateScore();
                                    notAnswered = false;

                                    chances = 0;
                                } else {
                                    incorrectDialog();
                                    chances++;
                                }
                                break;

                        }

                    }
                }).setNegativeButton("Cancel", null).create();
    }

    public void correctDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Correct").setPositiveButton("Ok", null);
        builder.show();
    }
    public void incorrectDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Incorrect. Please try again").setPositiveButton("Ok", null);
        builder.show();
    }

    public void updateScore() {
        Log.d("BOOL", notAnswered + "");
        if (notAnswered) {
            if (chances == 0) {
                score += 2;
                mStoryPoints.setPoints(score);
            } else if (chances == 1) {
                score += 1;
                mStoryPoints.setPoints(score);
            } else {

            }
        }

    }

    public static void setNotAnswered() {
        notAnswered = true;
    }
}
