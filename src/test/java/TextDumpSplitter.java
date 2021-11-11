import java.io.*;
import java.util.HashMap;
import java.util.StringJoiner;

public class TextDumpSplitter {
    public static void main(String[] args) {
        String data, prefix = "C:\\Users\\hp\\IdeaProjects\\VPlayGames\\src\\test\\resources\\lsd_extracted_temp\\";
        StringJoiner current = new StringJoiner("\n");
        HashMap<String, StringJoiner> fileMap = new HashMap<>();
        boolean addData = true;
        int breaksToBeIgnored = 0, subFolder = 1, cursor = 0;
        try (BufferedReader in = new BufferedReader(new FileReader("D:\\Downloads\\lsd_dl (3).txt"))) {
            while ((data = in.readLine()) != null) {
                if (data.isEmpty() || data.startsWith("=")) {
                    if (breaksToBeIgnored == 1) addData = false;
                    if (breaksToBeIgnored != 0) breaksToBeIgnored--;
                } else if (data.endsWith("_en.lsd")) {
                    fileMap.put(data, current = new StringJoiner("\n"));
                    breaksToBeIgnored = 2;
                    addData = true;
                    System.out.println(data);
                } else if (addData) current.add(data);
            }
            for (String k : fileMap.keySet()) {
                if (cursor == 100) {
                    cursor = 0;
                    subFolder++;
                }
                new File(prefix + subFolder + "\\").mkdirs();
                new FileWriter(prefix + subFolder + "\\" + k).append(fileMap.get(k).toString()).close();
                cursor++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}