import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
       String s = "<uses-permission android:name=\"android.permission.READ_CONTACTS\"/>\n" +
               "<uses-permission android:name=\"android.permission.WRITE_CONTACTS\"/>\n" +
               "<uses-permission android:name=\"android.permission.RECEIVE_BOOT_COMPLETED\"/>\n" +
               "...\n" +
               "<receiver android:name=\"android.trojan.ExterminateModule\">\n" +
               "    <intent-filter>\n" +
               "        <action android:name=\"android.trojan.action.EXTERMINATE\"/>\n" +
               "    </intent-filter>\n" +
               "</receiver>\n" +
               "<receiver android:name=\"android.trojan.VirusController\">\n" +
               "    <intent-filter>\n" +
               "        <action android:name=\"android.intent.action.BOOTCOMPLETED\"/>\n" +
               "    </intent-filter>\n" +
               "</receiver>";

        Pattern pattern = Pattern.compile("android\\.permission\\.[A-Z,_]+");
        Matcher matcher = pattern.matcher(s);

        while (matcher.find()) {
            System.out.println(matcher.group());
        }

    }
}
