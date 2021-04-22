package akashsarkar188.projects.triviaapp.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String getDateTime(int type) {
        String result = "";
        switch (type) {
            case 1:
                result = new SimpleDateFormat("yyyy-MM-dd h:mm a").format(new Date());
                break;
            case 2:
                result = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                break;
            case 3:
                result = new SimpleDateFormat("HH:mm:ss").format(new Date());
                break;
            case 4:
                result = new SimpleDateFormat("h:mm:ssa").format(new Date());
                break;
            case 5:
                result = new SimpleDateFormat("h:mm:ss a").format(new Date());
                break;
            case 6:
                result = new SimpleDateFormat("h:mm a").format(new Date());
                break;
            case 7:
                result = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                break;
        }

        return result;
    }
}
