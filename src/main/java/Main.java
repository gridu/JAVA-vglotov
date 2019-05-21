import org.testng.annotations.Test;

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
        int tempSize = 0;
        int minStringCount = 0;

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

//        LineNumberReader lnr = new LineNumberReader(new FileReader(path));
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

        //потом использовать writeChars не в этом методе
        //может придется удалять строки
    }


    private static long fillArrayList(RandomAccessFile file, long startPos, List<String> destArray, long halfMaxRam) throws IOException {
        long tempMemory = 0;
        file.seek(startPos);
        String element;

        while (tempMemory < halfMaxRam) {
            long returnToPos = file.getFilePointer();
            element = file.readLine();
            if (element == null || sum(tempMemory, element.length() * 2) > halfMaxRam) {
                file.seek(returnToPos);
                break;
            } else {
                destArray.add(element);
                tempMemory = sum(tempMemory, element.length() * 2);
            }
        }
        return file.getFilePointer();
    }

    private static void writeToFile(RandomAccessFile file, long startPos, List<String> srcArray) throws IOException {
        file.seek(startPos);

        for (String str : srcArray) {
            file.writeBytes(str + "\n");
        }

    }

//    public static void sort(String[] initial, int maxRam) {
//        List<String> ramArray1;
//        List<String> ramArray2;
//        int halfMaxRam = maxRam / 2;
//        int maxCycle = initial.length / halfMaxRam;
//        if (initial.length % halfMaxRam > 0) {
//            maxCycle += 1;
//        }
//
//        for (int p = 0; p < maxCycle - 1; p++) {
//            for (int j = 0; j < maxCycle - 1 - p; j++) {
//                String[] subArray1 = new String[halfMaxRam];
//                String[] subArray2 = new String[halfMaxRam];
//                boolean isCut = false;
//                int cutLength = 0;
//
//                System.arraycopy(initial, p * halfMaxRam, subArray1, 0, halfMaxRam);
//                for (int i = 0; i < halfMaxRam; i++) {
//                    if ((i + p * halfMaxRam) + halfMaxRam + halfMaxRam * j > initial.length - 1) {
//                        isCut = true;
//                        String[] newArr = new String[i];
//                        System.arraycopy(subArray2, 0, newArr, 0, i);
//                        subArray2 = new String[i];
//                        subArray2 = newArr;
//                        cutLength = subArray2.length;
//                        break;
//                    }
//                    subArray2[i] = initial[(i + p * halfMaxRam) + halfMaxRam + halfMaxRam * j];
//                }
//
//                ramArray1 = new ArrayList<>(Arrays.asList(subArray1));
//                ramArray2 = new ArrayList<>(Arrays.asList(subArray2));
//
//                ramArray1.addAll(ramArray2);
//                ramArray2.clear();
//                Collections.sort(ramArray1);
//
//                if (isCut) {
//                    for (int i = 0; i < cutLength; i++) {
//                        ramArray2.add(i, ramArray1.get(i + halfMaxRam));
//                    }
//                } else {
//                    for (int i = 0; i < halfMaxRam; i++) {
//                        ramArray2.add(i, ramArray1.get(i + halfMaxRam));
//                    }
//                }
//
//                for (int i = 0; i < halfMaxRam; i++) {
//                    initial[(i + p * halfMaxRam)] = ramArray1.get(i);
//                }
//
//                if (!isCut) {
//                    for (int i = 0; i < halfMaxRam; i++) {
//                        initial[(i + p * halfMaxRam) + halfMaxRam + halfMaxRam * j] = ramArray1.get(i + halfMaxRam);
//                    }
//                } else {
//                    for (int i = 0; i < cutLength; i++) {
//                        initial[(i + p * halfMaxRam) + halfMaxRam + halfMaxRam * j] = ramArray1.get(i + halfMaxRam);
//                    }
//                }
//            }
//        }
//    }

//    private static void printArray(String[] array) {
//        System.out.println("\nArray values:");
//        for (String str : array) {
//            System.out.print(str + ' ');
//        }
//    }
//
//    @Test
//    private static void sortTest() {
//        String[] actual = {"a", "c", "e", "3", "f", "d", "b"};
//        String[] expected = {"3", "a", "b", "c", "d", "e", "f"};
//        sort(actual, 3);
//        printArray(actual);
//        assert (Arrays.equals(actual, expected));
//    }
}