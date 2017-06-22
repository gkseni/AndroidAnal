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
        final String regexsFileName = "regexes.txt";
        Map<String, String> patterns = readPatterns(patternsFileName);
        Map<String, String> regexes = readPatterns(regexsFileName);

        final File folder = new File("main");
        analyzeFilesFromFolder(folder, patterns, regexes);

    }

    public static void analyzeFilesFromFolder(File folder, Map<String, String> patterns, Map<String, String> regexes) {
        File[] folderEntries = folder.listFiles();
        for (File entry : folderEntries) {
            if (entry.isDirectory()) {
                analyzeFilesFromFolder(entry, patterns, regexes);
                continue;
            }

            String filePath = entry.getAbsolutePath();
            if(filePath.contains(".java") || filePath.contains(".xml") ||
                    filePath.contains(".class") || filePath.contains(".js")){
                findPatternsAndRegexesInFile(filePath, patterns, regexes);
            }
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
                String textToFind = line.substring(0, line.indexOf('~'));
                String message = line.substring(line.indexOf('~') + 1);
                patterns.put(textToFind, message);
            }

        } catch (IOException e) {
            System.out.println("Something went wrong");
        }

        return patterns;
    }

    public static void findPatternsAndRegexesInFile(String fileName, Map<String, String> patterns,  Map<String, String> regexes) {
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
                    Matcher mp = p.matcher(line);

                    if(mp.find()) {
                        System.out.println("Find in string #" + cnt + ":");
                        System.out.println(pattern);
                        System.out.println(patterns.get(pattern));
                        System.out.println();
                    }
                }

                for (String regex : regexes.keySet()) {
                    Pattern r = Pattern.compile(regex);
                    Matcher mr = r.matcher(line);

                    if(mr.find()) {
                        System.out.println("Find in string #" + cnt + ":");
                        System.out.println(mr.group());
                        System.out.println(regexes.get(regex));
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
