import java.util.*;

public class Main {

    public static void main(String[] args) {
        List<String> ramArray1;
        List<String> ramArray2;
        String[] initial = {"glotov", "vladimir", "gonna", "tolve", "external", "sorting", "problem", "car", "baed", "afood", "12number", "uleep", "funk"};
        int maxRam = 5;
        int halfMaxRam = maxRam / 2;
        int maxCycle = initial.length / halfMaxRam;
        if (initial.length % halfMaxRam > 0) {
            maxCycle += 1;
        }

        System.out.println("Initial array:");
        for (String str: initial) {
            System.out.print(str + ' ');
        }

        for (int p = 0; p < maxCycle-1; p++) {
            for (int j = 0; j < maxCycle - 1 -p; j++) {
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
        System.out.println("\nSorted array:");
        for (String str: initial) {
            System.out.print(str + ' ');
        }
    }
}
