package by.lyofchik.errorservice.Utils;

public class Utils {
    public static boolean tryParse(String number){
        try {
            Integer.parseInt(number);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
