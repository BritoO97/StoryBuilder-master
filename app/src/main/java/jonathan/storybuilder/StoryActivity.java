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

/*

        IMPORTANT NOTE:
            Some of the original code is left in the class, and in other classes as well.
            Because of changes made this code (mostly a few variables) is never actually used.
            However to make it easier and quicker to implement the wanted changes cleanup will be done at a later date.

 */


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
    Context context;


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
        context = this;

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
        /*\
            I think this is where the changes will be made for the data to be read from the db and saved under the
            established variables.
         */

        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {

                // Changes start here
                DataSourceManager source = DataSourceManager.get(context);

                Story story1 = source.getStory(mTitle);
                Answer answer1 = source.getAnswers(mTitle);
                CompleteStory completeStory1 = source.getCompleteStory(mTitle);
                ArrayList<CompleteStory> list = source.getAllComplete();

                // Changes end here


                Story story = mStoryArray.get(position);
                Answer answer = mAnswerArray.get(position);
                CompleteStory completeStory = mCompleteStoryList.get(position);

                if (position == 0) {
                    return StoryFragment.newInstance(story1, answer1, completeStory1, source, list);
                } else if(position > 0) {
                    if (list.get(position - 1).getComplete().equals("no")) {
                        InCompleteFragment fragement = new InCompleteFragment();
                        return fragement;
                    } else {
                        return StoryFragment.newInstance(story1, answer1, completeStory1, source, list);
                    }
                }
                return StoryFragment.newInstance(story1, answer1, completeStory1, source, list);
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
