package akashsarkar188.projects.triviaapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import akashsarkar188.projects.triviaapp.R;
import akashsarkar188.projects.triviaapp.adapters.HistoryAdapter;
import akashsarkar188.projects.triviaapp.helper.SharedPrefHelper;
import akashsarkar188.projects.triviaapp.model.HistoryListModel;

public class Dashboard extends AppCompatActivity {

    private TextInputEditText nameEditText;
    private MaterialButton startQuizButton;
    private RecyclerView historyRecyclerView;
    private HistoryAdapter adapter;
    private List<HistoryListModel> list = new ArrayList<>();
    private SharedPrefHelper sharedPrefHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.darkStatusIcons);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        onClickListeners();
    }

    private void initView() {

        nameEditText = findViewById(R.id.nameEditText);
        startQuizButton = findViewById(R.id.startQuizButton);
        historyRecyclerView = findViewById(R.id.historyRecyclerView);

        sharedPrefHelper = new SharedPrefHelper(Dashboard.this);

        try {
            String data = sharedPrefHelper.getHistory();
            JSONArray parent;

            if (!data.isEmpty())
                parent = new JSONArray(data);
            else
                parent = new JSONArray();

            if (parent.length() > 0) {
                for (int i = 0; i < parent.length(); i++) {
                    list.add(new Gson().fromJson(parent.getJSONObject(i).toString(), HistoryListModel.class));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        historyRecyclerView.setLayoutManager(new LinearLayoutManager(Dashboard.this, RecyclerView.VERTICAL, false));
        historyRecyclerView.setHasFixedSize(true);
        adapter = new HistoryAdapter(list, Dashboard.this);
        historyRecyclerView.setAdapter(adapter);
    }

    private void onClickListeners() {

        startQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameEditText.getText() != null && !nameEditText.getText().toString().isEmpty()) {
                    startActivity(new Intent(Dashboard.this, QuizView.class)
                            .putExtra("username", nameEditText.getText().toString()));
                    nameEditText.setText("");
                } else {
                    Toast.makeText(Dashboard.this, "Please enter your name first.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        refreshHistory();
        super.onResume();
    }

    private void refreshHistory() {
        try {
            list.clear();
            String data = sharedPrefHelper.getHistory();
            JSONArray parent;

            if (!data.isEmpty())
                parent = new JSONArray(data);
            else
                parent = new JSONArray();

            if (parent.length() > 0) {
                for (int i = 0; i < parent.length(); i++) {
                    list.add(new Gson().fromJson(parent.getJSONObject(i).toString(), HistoryListModel.class));
                }
            }
            adapter = new HistoryAdapter(list, Dashboard.this);
            historyRecyclerView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}