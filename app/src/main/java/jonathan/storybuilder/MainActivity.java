package jonathan.storybuilder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    Stories mStories;
    AnswerList mAnswerList;
    RecyclerView mRecyclerView;
    CompleteStories mCompleteStories;
    List<CompleteStory> mCompleteStoryList;
    StoryPoints storyPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCompleteStories = CompleteStories.get(this);
        mCompleteStoryList = mCompleteStories.getCompleteStories();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        getConnection();

        storyPoints = StoryPoints.get(this);



    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);


        MenuItem scoreBar = menu.findItem(R.id.scoreMenu);
        if(storyPoints.getPointScore() == 0) {
            storyPoints.savePoints(0);
        }
        scoreBar.setTitle("Score:" + " " + storyPoints.getPointScore());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.reset_button:
                for(int i = 0; i < mStories.getStorySize(); i++) {
                    CompleteStory mCompleteStory = mCompleteStoryList.get(i);
                    mCompleteStory.setComplete("no");
                    mCompleteStories.updateStory(mCompleteStory);
                    //Toast.makeText(this, mStories.getStorySize() + "", Toast.LENGTH_LONG).show();
                }
                storyPoints.savePoints(0);
                this.invalidateOptionsMenu();
                return true;
            case R.id.storyCredit:
                Intent intent = new Intent(this, CreditsActivity.class);
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }

    private void getConnection() {

        String siteUrl = "http://10stories.esy.es/story.php";

        if (isNetworkAvailable()) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(siteUrl).build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String jsonData = response.body().string();
                        if(response.isSuccessful()) {
                            mStories = getStoryInfo(jsonData);
                            mAnswerList = getAnswers(jsonData);
                            getCompleteStories(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    updateDisplay();

                                }
                            });

                        } else {
                            alertUserAboutError();
                        } } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {

                    }
                }
            });

        }
    }

    private void updateDisplay() {
        MainMenuAdapter adapter = new MainMenuAdapter(this, mStories, mAnswerList);
        mRecyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean networkOn = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            networkOn = true;
        }

        return networkOn;
    }

    private void alertUserAboutError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error").setMessage("There was an error while trying to connect to the internet").
                setPositiveButton("Ok", null);
        builder.show();
    }

    private Stories getStoryInfo(String jsonData) throws JSONException {
        JSONObject info = new JSONObject(jsonData);
        JSONArray questions = info.getJSONArray("story");
        Stories stories = new Stories();

        for(int i = 0; i < questions.length(); i++) {
            JSONObject jsonObject = questions.getJSONObject(i);

            Story story = new Story();

            story.setTitle(jsonObject.getString("title"));
            story.setLine1(jsonObject.getString("line1"));
            story.setLine2(jsonObject.getString("line2"));
            story.setLine3(jsonObject.getString("line3"));
            story.setLine4(jsonObject.getString("line4"));
            story.setLine5(jsonObject.getString("line5"));
            story.setLine6(jsonObject.getString("line6"));
            story.setLine7(jsonObject.getString("line7"));
            story.setLine8(jsonObject.getString("line8"));
            story.setLine9(jsonObject.getString("line9"));
            story.setFinalLine(jsonObject.getString("finalLine"));
            stories.addStory(story);

        }

        return stories;

    }

    private CompleteStories getCompleteStories(String jsonData) throws  JSONException {

        JSONObject info = new JSONObject(jsonData);
        JSONArray story = info.getJSONArray("complete");
        CompleteStories completeStories = CompleteStories.get(this);

        for(int i = 0; i < story.length(); i++) {
            JSONObject jsonObject = story.getJSONObject(i);

            CompleteStory completeStory = new CompleteStory();

            completeStory.setTitle(jsonObject.getString("title"));
            completeStory.setLine1(jsonObject.getString("line1"));
            completeStory.setLine2(jsonObject.getString("line2"));
            completeStory.setLine3(jsonObject.getString("line3"));
            completeStory.setLine4(jsonObject.getString("line4"));
            completeStory.setLine5(jsonObject.getString("line5"));
            completeStory.setLine6(jsonObject.getString("line6"));
            completeStory.setLine7(jsonObject.getString("line7"));
            completeStory.setLine8(jsonObject.getString("line8"));
            completeStory.setLine9(jsonObject.getString("line9"));
            completeStory.setFinalLine(jsonObject.getString("finalLine"));
            completeStory.setResponse("no");
            completeStory.setComplete("no");
            completeStories.saveCompleteStories(completeStory);

        }

        return completeStories;
    }

    private AnswerList getAnswers(String jsonData) throws JSONException {


        JSONObject info = new JSONObject(jsonData);
        JSONArray line1 = info.getJSONArray("line1");
        JSONArray line2 = info.getJSONArray("line2");
        JSONArray line3 = info.getJSONArray("line3");
        JSONArray line4 = info.getJSONArray("line4");
        JSONArray line5 = info.getJSONArray("line5");
        JSONArray line6 = info.getJSONArray("line6");
        JSONArray line7 = info.getJSONArray("line7");
        JSONArray line8 = info.getJSONArray("line8");
        JSONArray line9 = info.getJSONArray("line9");
        AnswerList answerList = new AnswerList();

        for (int i = 0; i < line1.length(); i++) {
            JSONObject jsonObject = line1.getJSONObject(i);
            JSONObject jsonObject2 = line2.getJSONObject(i);
            JSONObject jsonObject3 = line3.getJSONObject(i);
            JSONObject jsonObject4 = line4.getJSONObject(i);
            JSONObject jsonObject5 = line5.getJSONObject(i);
            JSONObject jsonObject6 = line6.getJSONObject(i);
            JSONObject jsonObject7 = line7.getJSONObject(i);
            JSONObject jsonObject8 = line8.getJSONObject(i);
            JSONObject jsonObject9 = line9.getJSONObject(i);


            Answer answer = new Answer();

            answer.setAnswer1Choice1(jsonObject.getString("choice1"));
            answer.setAnswer1Choice2(jsonObject.getString("choice2"));
            answer.setAnswer1Choice3(jsonObject.getString("choice3"));
            answer.setAnswer1Correct(jsonObject.getString("correct"));
            answer.setAnswer1Interaction(jsonObject.getString("interaction"));

            answer.setAnswer2Choice1(jsonObject2.getString("choice1"));
            answer.setAnswer2Choice2(jsonObject2.getString("choice2"));
            answer.setAnswer2Choice3(jsonObject2.getString("choice3"));
            answer.setAnswer2Correct(jsonObject2.getString("correct"));
            answer.setAnswer2Interaction(jsonObject2.getString("interaction"));

            answer.setAnswer3Choice1(jsonObject3.getString("choice1"));
            answer.setAnswer3Choice2(jsonObject3.getString("choice2"));
            answer.setAnswer3Choice3(jsonObject3.getString("choice3"));
            answer.setAnswer3Correct(jsonObject3.getString("correct"));
            answer.setAnswer3Interaction(jsonObject3.getString("interaction"));

            answer.setAnswer4Choice1(jsonObject4.getString("choice1"));
            answer.setAnswer4Choice2(jsonObject4.getString("choice2"));
            answer.setAnswer4Choice3(jsonObject4.getString("choice3"));
            answer.setAnswer4Correct(jsonObject4.getString("correct"));
            answer.setAnswer4Interaction(jsonObject4.getString("interaction"));

            answer.setAnswer5Choice1(jsonObject5.getString("choice1"));
            answer.setAnswer5Choice2(jsonObject5.getString("choice2"));
            answer.setAnswer5Choice3(jsonObject5.getString("choice3"));
            answer.setAnswer5Correct(jsonObject5.getString("correct"));
            answer.setAnswer5Interaction(jsonObject5.getString("interaction"));

            answer.setAnswer6Choice1(jsonObject6.getString("choice1"));
            answer.setAnswer6Choice2(jsonObject6.getString("choice2"));
            answer.setAnswer6Choice3(jsonObject6.getString("choice3"));
            answer.setAnswer6Correct(jsonObject6.getString("correct"));
            answer.setAnswer6Interaction(jsonObject6.getString("interaction"));

            answer.setAnswer7Choice1(jsonObject7.getString("choice1"));
            answer.setAnswer7Choice2(jsonObject7.getString("choice2"));
            answer.setAnswer7Choice3(jsonObject7.getString("choice3"));
            answer.setAnswer7Correct(jsonObject7.getString("correct"));
            answer.setAnswer7Interaction(jsonObject7.getString("interaction"));

            answer.setAnswer8Choice1(jsonObject8.getString("choice1"));
            answer.setAnswer8Choice2(jsonObject8.getString("choice2"));
            answer.setAnswer8Choice3(jsonObject8.getString("choice3"));
            answer.setAnswer8Correct(jsonObject8.getString("correct"));
            answer.setAnswer8Interaction(jsonObject8.getString("interaction"));

            answer.setAnswer9Choice1(jsonObject9.getString("choice1"));
            answer.setAnswer9Choice2(jsonObject9.getString("choice2"));
            answer.setAnswer9Choice3(jsonObject9.getString("choice3"));
            answer.setAnswer9Correct(jsonObject9.getString("correct"));
            answer.setAnswer9Interaction(jsonObject9.getString("interaction"));

            answerList.addAnswers(answer);

        }

        return answerList;
    }




}
