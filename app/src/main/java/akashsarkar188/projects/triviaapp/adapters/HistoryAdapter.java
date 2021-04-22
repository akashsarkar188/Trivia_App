package akashsarkar188.projects.triviaapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import akashsarkar188.projects.triviaapp.R;
import akashsarkar188.projects.triviaapp.model.Datum;
import akashsarkar188.projects.triviaapp.model.HistoryListModel;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    List<HistoryListModel> list;
    Context context;

    public HistoryAdapter(List<HistoryListModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_card, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        HistoryListModel data = list.get(position);

        holder.gameDateTextView.setText(String.format("Game %s : %s", String.valueOf(position + 1), data.getDate()));
        holder.nameTextView.setText(String.format("Name : %s", data.getUsername()));

        for (int i = 0; i < data.getData().size(); i++) {
            try {
                Datum innerData = data.getData().get(i);
                TextView questionTextView = new TextView(context);
                questionTextView.setText(String.format("Question %s : %s", String.valueOf(i + 1), innerData.getQuestion()));
                questionTextView.setTextSize(16f);
                questionTextView.setTextColor(context.getResources().getColor(R.color.dark_grey));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 25, 0, 0);
                holder.dataLayout.addView(questionTextView, layoutParams);

                TextView answerTextView = new TextView(context);
                answerTextView.setText(String.format("Answer : %s", innerData.getAnswer()));
                answerTextView.setTextColor(context.getResources().getColor(R.color.dark_grey));
                answerTextView.setTextSize(16f);
                holder.dataLayout.addView(answerTextView);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        holder.headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.dataLayout.setVisibility(holder.dataLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView gameDateTextView, nameTextView;
        RelativeLayout headerLayout;
        LinearLayout questionsDataLayout, dataLayout;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            gameDateTextView = itemView.findViewById(R.id.gameDateTextView);
            headerLayout = itemView.findViewById(R.id.headerLayout);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            dataLayout = itemView.findViewById(R.id.dataLayout);
            questionsDataLayout = itemView.findViewById(R.id.questionsDataLayout);
        }
    }
}
