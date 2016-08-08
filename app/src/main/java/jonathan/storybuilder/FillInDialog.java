package jonathan.storybuilder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Joe on 2/26/2016.
 */
public class FillInDialog extends DialogFragment {

    private static final String ANSWERED = "answered";
    EditText mAnswer;
    TextView mQuestionLabel;
    public static final String CORRECT = "correct";
    public static final String QUESTION = "question";
    private int score;
    StoryPoints mStoryPoints;
    static int chances = 0;
    static boolean notAnswered = true;

    public static FillInDialog newInstance(String correctAnswer, String question) {
        Bundle args = new Bundle();
        args.putSerializable(CORRECT, correctAnswer);
        args.putSerializable(QUESTION, question);

        FillInDialog fragement = new FillInDialog();
        fragement.setArguments(args);
        return fragement;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final String correct = (String) getArguments().getSerializable(CORRECT);
        String question = (String) getArguments().getSerializable(QUESTION);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fill, null);

        mAnswer = (EditText) view.findViewById(R.id.fillInText);
        mQuestionLabel = (TextView) view.findViewById(R.id.questionLabel);

        mQuestionLabel.setText(question);

        mStoryPoints = StoryPoints.get(getContext());
        score = mStoryPoints.getPoints();

        return new AlertDialog.Builder(getActivity()).setView(view).setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (correct.trim().toLowerCase().equals(mAnswer.getText().toString().toLowerCase().trim().toString())) {


                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Correct").setPositiveButton("Ok", null);
                    builder.show();
                    StoryFragment.get();
                    updateScore();
                    notAnswered = false;
                    chances = 0;
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Wrong, Try Again!").setPositiveButton("Ok", null);
                    builder.show();
                    chances++;
                }
            }
        }).setNegativeButton("Cancel", null).create();
    }

    public void updateScore() {

        if(notAnswered) {
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
