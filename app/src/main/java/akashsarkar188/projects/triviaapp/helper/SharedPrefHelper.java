package akashsarkar188.projects.triviaapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefHelper {

    private SharedPreferences sharedPreferences;
    private static final String initSharedPref = "akashsarkar188.projects.triviaapp.session_init";
    private static final String sharedPref_history = "akashsarkar188.projects.triviaapp.history";

    public SharedPrefHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(initSharedPref, Context.MODE_PRIVATE);
    }

    public void addHistory(String data) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(sharedPref_history, data);
        editor.apply();
    }

    public String getHistory() {
        return sharedPreferences.getString(sharedPref_history, "");
    }
}
