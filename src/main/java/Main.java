
import com.sun.scenario.effect.Merge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> ramArray1;
        List<String> ramArray2;
        String[] initial = {"glotov", "vladimir", "gonna", "solve", "external", "sorting", "problem", "car", "bed", "food", "sleep", "atnight"};
        int maxRam = 6;
        int halfMaxRam = maxRam/2;
        int maxCycle = initial.length/halfMaxRam;
        if (initial.length%halfMaxRam > 0) {
            maxCycle =+ 1;
        }

        System.out.println("Initial array at the beginning:");
        for (String str: initial) {
            System.out.print(str + ' ');
        }

        for (int p = 0; p < maxCycle-1; p++) {
            for (int j = 0; j < maxCycle - 1 -p; j++) {
                System.out.println("\n\nMacrocycle number " + (p + 1));
                System.out.println("Microcycle number " + (j + 1));
                String[] subArray1 = new String[halfMaxRam];
                String[] subArray2 = new String[halfMaxRam];

                System.arraycopy(initial, p * halfMaxRam, subArray1, 0, halfMaxRam);
                for (int i = 0; i < halfMaxRam; i++) {
                    subArray2[i] = initial[(i+p*halfMaxRam) +halfMaxRam + halfMaxRam * j];
                }
                ramArray1 = new ArrayList<String>(Arrays.asList(subArray1));
                ramArray2 = new ArrayList<String>(Arrays.asList(subArray2));

                System.out.println("ramArr1:");
                for (String number : ramArray1) {
                    System.out.print(number + " ");
                }

                System.out.println("\nramArr2:");
                for (String number : ramArray2) {
                    System.out.print(number + " ");
                }
                System.out.println();

                ramArray1.addAll(ramArray2);
                ramArray2.clear();
                Collections.sort(ramArray1);

                System.out.println("ramArr1 after sort:");
                for (String number : ramArray1) {
                    System.out.print(number + " ");
                }
                System.out.println();

                for (int i = 0; i < halfMaxRam; i++) {
                    ramArray2.add(i, ramArray1.get(i + halfMaxRam));
                }

                System.out.println("ramArr2 after refactoring:");
                for (String number : ramArray2) {
                    System.out.print(number + " ");
                }
                System.out.println();

                for (int i = 0; i < halfMaxRam; i++) {
                    initial[(i+p*halfMaxRam)] = ramArray1.get(i);
                }

                for (int i = 0; i < halfMaxRam; i++) {
                    initial[(i+p*halfMaxRam) + halfMaxRam + halfMaxRam * j] = ramArray1.get(i + halfMaxRam);
                }
                System.out.println("New sorted big array after cycle:");
                for (String str : initial) {
                    System.out.print(str + ' ');
                }
            }
        }
    }
}
