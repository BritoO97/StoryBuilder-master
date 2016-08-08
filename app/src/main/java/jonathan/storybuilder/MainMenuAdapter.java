package jonathan.storybuilder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Joe on 2/25/2016.
 */
public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.StoryHolder> {


    public static final String ANSWERS = "answers";
    public static final String STORIES = "stories";

    Stories mStories;
    AnswerList mAnswerList;
    Context mContext;

    public MainMenuAdapter(Context context, Stories stories, AnswerList answerList) {
        mStories = stories;
        mContext = context;
        mAnswerList = answerList;

    }

    @Override
    public StoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_list_layout, parent, false);
        StoryHolder holder = new StoryHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(StoryHolder holder, int position) {
        holder.bindStory(mStories.getStory(position), mAnswerList, mStories);
    }

    @Override
    public int getItemCount() {
        return mStories.getStorySize();
    }

    public class StoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView storyTitleLabel;
        Story mStory;
        AnswerList mAnswers;
        Stories mStoryList;


        public StoryHolder(View itemView) {
            super(itemView);

            storyTitleLabel = (TextView) itemView.findViewById(R.id.storyLabel);

            itemView.setOnClickListener(this);
        }

        public void bindStory(Story story, AnswerList answers, Stories stories) {

            mStory = story;
            storyTitleLabel.setText(story.getTitle());
            mAnswers = answers;
            mStoryList = stories;



        }

        @Override
        public void onClick(View v) {

            Intent intent = StoryActivity.newIntent(mContext, mStory.getTitle(), mAnswers, mStoryList);

            mContext.startActivity(intent);
        }
    }
}
