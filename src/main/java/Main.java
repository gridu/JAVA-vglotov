import org.testng.annotations.Test;


import java.util.*;

public class Main {

    public static void main(String[] args) {

        String[] initial = {"glotov", "vladimir", "gonna", "tolve", "external", "sorting", "problem", "car", "baed", "afood", "12number", "uleep", "funk"};
        printArray(initial);
        sort(initial, 5);
        printArray(initial);
    }

    public static void sort(String[] initial, int maxRam) {
        List<String> ramArray1;
        List<String> ramArray2;
        int halfMaxRam = maxRam / 2;
        int maxCycle = initial.length / halfMaxRam;
        if (initial.length % halfMaxRam > 0) {
            maxCycle += 1;
        }

        for (int p = 0; p < maxCycle - 1; p++) {
            for (int j = 0; j < maxCycle - 1 - p; j++) {
                String[] subArray1 = new String[halfMaxRam];
                String[] subArray2 = new String[halfMaxRam];
                boolean isCut = false;
                int cutLength = 0;

                System.arraycopy(initial, p * halfMaxRam, subArray1, 0, halfMaxRam);
                for (int i = 0; i < halfMaxRam; i++) {
                    if ((i + p * halfMaxRam) + halfMaxRam + halfMaxRam * j > initial.length - 1) {
                        isCut = true;
                        String[] newArr = new String[i];
                        System.arraycopy(subArray2, 0, newArr, 0, i);
                        subArray2 = new String[i];
                        subArray2 = newArr;
                        cutLength = subArray2.length;
                        break;
                    }
                    subArray2[i] = initial[(i + p * halfMaxRam) + halfMaxRam + halfMaxRam * j];
                }

                ramArray1 = new ArrayList<>(Arrays.asList(subArray1));
                ramArray2 = new ArrayList<>(Arrays.asList(subArray2));

                ramArray1.addAll(ramArray2);
                ramArray2.clear();
                Collections.sort(ramArray1);

                if (isCut) {
                    for (int i = 0; i < cutLength; i++) {
                        ramArray2.add(i, ramArray1.get(i + halfMaxRam));
                    }
                } else {
                    for (int i = 0; i < halfMaxRam; i++) {
                        ramArray2.add(i, ramArray1.get(i + halfMaxRam));
                    }
                }

                for (int i = 0; i < halfMaxRam; i++) {
                    initial[(i + p * halfMaxRam)] = ramArray1.get(i);
                }

                if (!isCut) {
                    for (int i = 0; i < halfMaxRam; i++) {
                        initial[(i + p * halfMaxRam) + halfMaxRam + halfMaxRam * j] = ramArray1.get(i + halfMaxRam);
                    }
                } else {
                    for (int i = 0; i < cutLength; i++) {
                        initial[(i + p * halfMaxRam) + halfMaxRam + halfMaxRam * j] = ramArray1.get(i + halfMaxRam);
                    }
                }
            }
        }
    }

    private static void printArray(String[] array) {
        System.out.println("\nArray values:");
        for (String str : array) {
            System.out.print(str + ' ');
        }
    }

    @Test
    private static void sortTest(){
        String[] actual = {"a", "c", "e", "3", "f", "d", "b"};
        String[] expected = {"3", "a", "b", "c", "d", "e", "f"};
        sort(actual, 3);
        printArray(actual);
        assert(Arrays.equals(actual, expected));
    }
}
