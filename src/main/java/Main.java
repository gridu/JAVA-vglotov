import org.testng.annotations.Test;

import java.io.*;
import java.util.*;

import static java.lang.Long.sum;

public class Main {

    public static void main(String[] args) throws IOException {

        File file = new File("src/main/resources/input.txt");
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");

        sortFile(randomAccessFile, 20);
    }

    public static void sortFile(RandomAccessFile file, int maxRam) throws IOException {

        int halfMaxRam = maxRam / 2;
        long pointer1Writing;
        long pointer1Reading = 0;
        long pointer2Writing;
        long pointer2Reading = 0;

        while (pointer1Reading < file.length()) {
            List<String> ramArray1 = new ArrayList<>();
            pointer1Writing = pointer1Reading;
            pointer1Reading = fillArrayList(file, pointer1Reading, ramArray1, halfMaxRam);
            pointer2Reading = pointer1Reading;

            while (pointer2Reading < file.length()) {

                List<String> ramArray2 = new ArrayList<>();

//                pointer2Writing = pointer2Reading;
                pointer2Reading = fillArrayList(file, pointer2Reading, ramArray2, halfMaxRam);

//                ramArray1.addAll(ramArray2);

                for (String str: ramArray1) {
                    System.out.print(str + " ");
                }
                System.out.println();
//                Collections.sort(ramArray1);

                for (String str: ramArray2) {
                    System.out.print(str + " ");
                }

//                TODO: проблема с оставшимися строками если они разной длины

//                for (int i = 0; i < arr1size; i++) {
//                    System.out.println("1st arr elem before writing: " + ramArray1.get(i));
//                    file.seek(pointer1Writing);
//                    System.out.println("pointer1Writing= " + pointer1Writing);
//                    file.writeBytes(ramArray1.get(i) + "\n");
//                    pointer1Writing += ramArray1.get(i).length() + 1;
//                }
//
//                for (int i = arr1size; i < ramArray1.size(); i++) {
//                    System.out.println("2nd arr elem before writing: " + ramArray1.get(i));
//                    file.seek(pointer2Writing);
//                    System.out.println("pointer2Writing= " + pointer2Writing);
//                    file.writeBytes(ramArray1.get(i));
//                    pointer2Writing += ramArray1.get(i).length();
//                    if (i < ramArray1.size() - 1) {
//                        file.writeBytes("\n");
//                        pointer2Writing++;
//                    }
//                }
            }
        }
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

        for (String str: srcArray) {
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