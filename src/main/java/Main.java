import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static java.lang.Long.sum;

public class Main {

    public static void main(String[] args) throws IOException {

        String path = "src/main/resources/input.txt";
        int maxRam = 140;
        System.out.println("min stringCount is " + getMinStrCount(path, maxRam, Files.lines(Paths.get(path)).count()));
        sortFile(path, maxRam);

    }

    public static int getMinStrCount(String path, int maxRam, long lineCount) throws IOException {
        int biggestLineSize = 0;
        int minStringCount = 0;
        int tempSize;

        LineNumberReader lnr = new LineNumberReader(new FileReader(path));

        for (int i = 0; i < lineCount; i++) {
            try {
                tempSize = lnr.readLine().length() * 2;
                if (tempSize > biggestLineSize) biggestLineSize = tempSize;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (biggestLineSize > maxRam / 2) {
            System.out.println("No good because biggest line size is " + biggestLineSize);
        } else {
            minStringCount = (maxRam / 2) / biggestLineSize;
        }

        return minStringCount;
    }

    public static void sortFile(String path, int maxRam) throws IOException {

        RandomAccessFile file = new RandomAccessFile(new File(path), "rw");
        long totalLines = Files.lines(Paths.get(path)).count();
        int minStringCount = getMinStrCount(path, maxRam, totalLines);
        int segmentCount = (int) (totalLines / minStringCount);
        if (totalLines % minStringCount > 0) segmentCount += 1;

        for (int i = 0; i < segmentCount; i++) {
            ArrayList<String> arr1 = new ArrayList<>();
            goToLineNumber(i * minStringCount, file);

            for (int k = 0; k < minStringCount; k++) {
                arr1.add(file.readLine());
            }

            for (int j = i + 1; j < segmentCount; j++) {
                ArrayList<String> arr2 = new ArrayList<>();

                for (int k = 0; k < minStringCount; k++) {
                    String elemToAdd = file.readLine();
                    if (elemToAdd != null) arr2.add(file.readLine());
                }

                arr1.addAll(arr2);
                Collections.sort(arr1);

                //дальше записываем сразу из arr1
            }
        }
    }


    private static void goToLineNumber(int number, RandomAccessFile file) {
        try {
            file.seek(0);
            for (int i = 0; i < number; i++) {
                file.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeToFile(RandomAccessFile file, long startPos, List<String> srcArray) throws IOException {
        file.seek(startPos);

        for (String str : srcArray) {
            file.writeBytes(str + "\n");
        }
    }
}