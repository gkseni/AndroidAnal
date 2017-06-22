import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        final String patternsFileName = "patterns.txt";
        Map<String, String> patterns = readPatterns(patternsFileName);

        final File folder = new File("main");
        analyzeFilesFromFolder(folder, patterns);

    }

    public static void analyzeFilesFromFolder(File folder, Map<String, String> patterns) {
        File[] folderEntries = folder.listFiles();
        for (File entry : folderEntries) { //if folder - next step
            if (entry.isDirectory()) {
                analyzeFilesFromFolder(entry, patterns);
                continue;
            }
            // if file
            findPatternsInFile(entry.getAbsolutePath(), patterns);
        }
    }

    public static Map<String, String> readPatterns(String fileName) {
        BufferedReader br = null;
        FileReader fr = null;

        Map<String, String> patterns = new HashMap<>();

        try {
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);

            String line;

            while ((line = br.readLine()) != null) {
                String textToFind = line.substring(0, line.indexOf(':'));
                String message = line.substring(line.indexOf(':') + 1);
                patterns.put(textToFind, message);
//                System.out.println("Text to find: " + textToFind);
//                System.out.println("Message: " + message);
//                System.out.println();
            }

        } catch (IOException e) {
            System.out.println("Something went wrong");
        }

        return patterns;
    }

    public static void findPatternsInFile(String fileName, Map<String, String> patterns) {
        BufferedReader br = null;
        FileReader fr = null;

        try {

            fr = new FileReader(fileName);
            br = new BufferedReader(fr);

            String line;

            System.out.println("Searching in file '" + fileName + "'" );
            System.out.println();

            Integer cnt = 1;
            while ((line = br.readLine()) != null) {
                for (String pattern : patterns.keySet()) {

                    Pattern p = Pattern.compile(pattern);
                    Matcher m = p.matcher(line);

                    if(m.find()) {
                        System.out.println("Find in string #" + cnt + ":");
                        System.out.println(pattern);
                        System.out.println(patterns.get(pattern));
                        System.out.println();
                    }

                }
                cnt++;
            }

        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
    }
}
