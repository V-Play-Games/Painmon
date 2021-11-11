import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("ConstantConditions")
public class OSTFileMover {
    public static void main(String[] args) throws IOException {
        File logFile = new File("src/test/resources/OSTFileMovingLog.txt");
        System.out.println("---- Process started " + LocalTime.now().toString() + " ----");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the path of the parent directory containing the OST Collections: ");
        String outputPath = scanner.nextLine();
        for (File collectionFolder : new File(outputPath).listFiles()) {
            System.out.println("[" + LocalTime.now() + "] Detected Collection: " + collectionFolder);
            copyRenamePasteAll(collectionFolder.getPath());
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void copyRenamePasteAll(String path) throws IOException {
        String separator = File.separator;
        String outputPath = path + separator + "Full " + path.substring(path.lastIndexOf(separator) + 1);
        new File(outputPath).mkdirs();
        System.out.println("[" + LocalTime.now() + "] Outputting to " + outputPath);
        ArrayList<File> inputDirectories = new ArrayList<>();
        File discFolder;
        int i = 1;
        while ((discFolder = new File(path + separator + "Disc " + i++)).exists()) {
            System.out.println("[" + LocalTime.now() + "] Detected Disc: " + discFolder);
            inputDirectories.add(discFolder);
        }
        for (File folder : inputDirectories) {
            for (File file : folder.listFiles()) {
                String name = file.getName();
                if (!name.endsWith(".mp3")) {
                    continue;
                }
                while (!Character.isLetter(name.charAt(0))) {
                    name = name.substring(1);
                }
                Files.copy(file.toPath(), new File(outputPath + separator + name).toPath());
                System.out.println("[" + LocalTime.now() + "] Moved " + file.toString().substring(file.toString().lastIndexOf(separator) - 6, file.toString().lastIndexOf(separator) + 1) + name + " to the Output Directory");
            }
        }
    }
}
