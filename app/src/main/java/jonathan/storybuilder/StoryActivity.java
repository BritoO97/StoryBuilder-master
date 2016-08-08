package jonathan.storybuilder;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class StoryActivity extends AppCompatActivity {

    public static final String TITLE = "title";
    Stories mStories;
    CompleteStories mCompleteStories;
    AnswerList mAnswerList;
    ArrayList<Story> mStoryArray;
    ArrayList<Answer> mAnswerArray;
    ArrayList<CompleteStory> mList;
    String mTitle;
    List<CompleteStory> mCompleteStoryList;


    private ViewPager mViewPager;

    public static Intent newIntent(Context packageContext, String title, AnswerList mAnswers, Stories mStoryList) {
        Intent intent = new Intent(packageContext,
                StoryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(MainMenuAdapter.ANSWERS, mAnswers);
        bundle.putParcelable(MainMenuAdapter.STORIES, mStoryList);
        intent.putExtras(bundle);
        intent.putExtra(TITLE, title);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        Bundle b = this.getIntent().getExtras();
        mTitle = this.getIntent().getStringExtra(TITLE);
        if(b != null) {
            mStories = b.getParcelable(MainMenuAdapter.STORIES);
            mAnswerList = b.getParcelable(MainMenuAdapter.ANSWERS);

        }

        mCompleteStories = CompleteStories.get(this);
        mCompleteStoryList = mCompleteStories.getCompleteStories();
        mStoryArray = mStories.getAllStories();
        mAnswerArray = mAnswerList.getAllAnswers();


        mViewPager = (ViewPager) findViewById(R.id.story_view_pager);

        FragmentManager fragmentManager = getSupportFragmentManager();

        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Story story = mStoryArray.get(position);
                Answer answer = mAnswerArray.get(position);
                CompleteStory completeStory = mCompleteStoryList.get(position);
                if (position == 0) {
                    return StoryFragment.newInstance(story, answer, completeStory);
                } else if(position > 0) {
                    if (mCompleteStoryList.get(position - 1).getComplete().equals("no")) {
                        InCompleteFragment fragement = new InCompleteFragment();
                        return fragement;
                    } else {
                        return StoryFragment.newInstance(story, answer, completeStory);
                    }
                }
                return StoryFragment.newInstance(story, answer, completeStory);
            }

            @Override
            public int getCount() {
                return mStoryArray.size();
            }
        });

        for(int i = 0; i < mStoryArray.size(); i++) {
            if(mStoryArray.get(i).getTitle().equals(mTitle)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }




}
