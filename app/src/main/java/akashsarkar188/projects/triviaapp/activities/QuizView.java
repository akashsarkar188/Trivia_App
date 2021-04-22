package akashsarkar188.projects.triviaapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import akashsarkar188.projects.triviaapp.R;
import akashsarkar188.projects.triviaapp.helper.SharedPrefHelper;
import akashsarkar188.projects.triviaapp.helper.Utils;
import akashsarkar188.projects.triviaapp.model.QuizQuestionModel;

public class QuizView extends AppCompatActivity {

    private RelativeLayout headerLayout;
    private TextView questionTextView, summaryGreetingsTextView;
    private LinearLayout optionsLayout, quizLayout, summaryDataLayout;
    private NestedScrollView summaryScrollView;
    private MaterialButton nextButton, finishSummaryButton;
    private List<QuizQuestionModel> questionList;
    private int currentQuestion = 0;
    private String questionID_c = "", question_c = "", response_c = "", username_c = "";
    private HashMap<String, String> responses = new HashMap<>();
    private SharedPrefHelper sharedPrefHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.darkStatusIcons);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_view);

        initView();
        onClickListeners();
        populateQuestions();
        showQuestions();
    }

    private void initView() {

        headerLayout = findViewById(R.id.headerLayout);
        questionTextView = findViewById(R.id.questionTextView);
        optionsLayout = findViewById(R.id.optionsLayout);
        quizLayout = findViewById(R.id.quizLayout);
        nextButton = findViewById(R.id.nextButton);
        summaryScrollView = findViewById(R.id.summaryScrollView);
        finishSummaryButton = findViewById(R.id.finishSummaryButton);
        summaryDataLayout = findViewById(R.id.summaryDataLayout);
        summaryGreetingsTextView = findViewById(R.id.summaryGreetingsTextView);

        questionList = new ArrayList<>();

        username_c = getIntent().getStringExtra("username");

        sharedPrefHelper = new SharedPrefHelper(QuizView.this);
    }

    private void onClickListeners() {

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionList.size() == currentQuestion + 1) {
                    if (responses.containsKey(questionID_c)) {
                        if (responses.get(questionID_c) != null && !responses.get(questionID_c).isEmpty()) {
                            showResult();
                     } else {
                            Toast.makeText(QuizView.this, "Please make atleast one choice.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(QuizView.this, "Please make atleast one choice.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (responses.containsKey(questionID_c)) {
                        if (responses.get(questionID_c)!= null && !responses.get(questionID_c).isEmpty()) {
                            currentQuestion++;
                            showQuestions();

                            if (questionList.size() == currentQuestion + 1)
                                nextButton.setText("Finish");
                        } else {
                            Toast.makeText(QuizView.this, "Please make atleast one choice.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(QuizView.this, "Please make atleast one choice.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        finishSummaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void populateQuestions() {

        // populating hardcoded data, which can be easily replaced by API
        // Also the quiz is dynamic so you can input any number of questions
        questionList.add(new QuizQuestionModel(1, "Who is the best cricketer in the world?", "radio", new ArrayList<String>() {{
            add("Sachin Tendulkar");
            add("Virat Kohli");
            add("Adam Gilchirst");
            add("Jacques Kallis");
        }}));
        questionList.add(new QuizQuestionModel(2, "Which are the colors in the Indian national flag?", "check", new ArrayList<String>() {{
            add("White");
            add("Yellow");
            add("Orange");
            add("Green");
        }}));
    }

    private void showQuestions() {
        // this method will handle the visibility of question and the type of options it should have
        // either radio or checkbox
        // I am using dynamic layout to populate the options
        quizLayout.setVisibility(View.VISIBLE);
        summaryScrollView.setVisibility(View.GONE);

        if (questionList.size() >= currentQuestion + 1) {
            questionID_c = String.valueOf(questionList.get(currentQuestion).getId());
            question_c = questionList.get(currentQuestion).getQuestion();
            questionTextView.setText(questionList.get(currentQuestion).getQuestion());

            switch (questionList.get(currentQuestion).getType()) {
                case "radio":
                    populateOptions_radio(questionList.get(currentQuestion).getOptions());
                    break;
                case "check":
                    populateOptions_check(questionList.get(currentQuestion).getOptions());
                    break;
            }
        }
    }

    private void populateOptions_radio(List<String> options) {
        optionsLayout.removeAllViews();

        RadioGroup radioGroup = new RadioGroup(QuizView.this);
        radioGroup.setOrientation(LinearLayout.VERTICAL);

        for (String data : options) {
            // dynamic layout
            RadioButton radioButton = new RadioButton(QuizView.this);
            radioButton.setText(data);
            radioButton.setTextSize(16f);
            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        responses.put(questionID_c, buttonView.getText().toString());
                    }
                }
            });
            radioGroup.addView(radioButton);
        }

        optionsLayout.addView(radioGroup);
    }

    private void populateOptions_check(List<String> options) {
        optionsLayout.removeAllViews();

        for (String data : options) {
            // dynamic layout
            CheckBox checkBox = new CheckBox(QuizView.this);
            checkBox.setText(data);
            checkBox.setTextSize(16f);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    String[] currentlySelectedItems = response_c.split(",");
                    List<String> checkedItems;

                    checkedItems = new ArrayList<>();

                    if (!response_c.isEmpty())
                        for (String items : currentlySelectedItems) {
                            checkedItems.add(items.trim());
                        }

                    if (isChecked) {
                        if (!checkedItems.contains(buttonView.getText().toString()))
                            checkedItems.add(buttonView.getText().toString());
                    } else {
                        checkedItems.remove(buttonView.getText().toString());
                    }
                    response_c = checkedItems.toString().replace("[", "").replace("]", "");
                    responses.put(questionID_c, response_c);
                }
            });
            optionsLayout.addView(checkBox);
        }
    }

    private void showResult() {
        try {
            summaryDataLayout.removeAllViews();
            summaryGreetingsTextView.setText(String.format("Hello %s, Here are the answers selected :", username_c));

            // all the json operations are responsible for creating a json structure which will
            // be used to show the history data in dashboard
            // sample -  [{username:"Akash", date:"2021-04-22 5:56 am", data: [{question:"", answer:""}, {question:"", answer:""}]}]
            String previousResponse = sharedPrefHelper.getHistory();
            JSONArray previousObject;

            if (!previousResponse.isEmpty())
                previousObject = new JSONArray(previousResponse);
            else
                previousObject = new JSONArray();

            JSONObject currentSession = new JSONObject();
            JSONArray sessionData = new JSONArray();
            JSONObject questionData;

            for (int i = 0; i < questionList.size(); i++) {
                QuizQuestionModel data = questionList.get(i);
                questionData = new JSONObject();

                TextView questionTextView = new TextView(QuizView.this);
                questionTextView.setText(String.format("Question %s : %s", String.valueOf(i + 1), data.getQuestion()));
                questionTextView.setTextSize(16f);
                questionTextView.setTextColor(getResources().getColor(R.color.dark_grey));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 25, 0, 0);
                summaryDataLayout.addView(questionTextView, layoutParams);

                TextView answerTextView = new TextView(QuizView.this);
                String response = responses.get(String.valueOf(data.getId()));
                answerTextView.setText(String.format("Answer : %s", response));
                answerTextView.setTextColor(getResources().getColor(R.color.dark_grey));
                answerTextView.setTextSize(16f);
                summaryDataLayout.addView(answerTextView);

                questionData.put("question", data.getQuestion());
                questionData.put("answer", response);
                sessionData.put(questionData);
            }
            currentSession.put("data", sessionData);
            currentSession.put("username", username_c);
            currentSession.put("date", Utils.getDateTime(1));
            previousObject.put(currentSession);

            Log.e("XXX", "showResult: " + previousObject );
            sharedPrefHelper.addHistory(previousObject.toString());

            quizLayout.setVisibility(View.GONE);
            summaryScrollView.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}