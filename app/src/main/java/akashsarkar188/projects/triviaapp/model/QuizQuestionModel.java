package akashsarkar188.projects.triviaapp.model;

import java.util.List;

public class QuizQuestionModel {

    int id;
    String question, type;
    List<String> options;

    public QuizQuestionModel(int id, String question, String type, List<String> options) {
        this.id = id;
        this.question = question;
        this.type = type;
        this.options = options;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getType() {
        return type;
    }

    public List<String> getOptions() {
        return options;
    }
}
