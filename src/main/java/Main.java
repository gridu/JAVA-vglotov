import java.util.*;

public class Main {

    public static void main(String[] args) {
        List<String> ramArray1;
        List<String> ramArray2;
        String[] initial = {"glotov", "vladimir", "gonna", "solve", "external", "sorting", "problem", "car", "baed", "afood", "sleep"};
        int maxRam = 5;
        int halfMaxRam = maxRam/2;
        int maxCycle = initial.length/halfMaxRam;
        if (initial.length%halfMaxRam > 0) {
            maxCycle += 1;
        }

        System.out.println("Initial array at the beginning:");
        for (String str: initial) {
            System.out.print(str + ' ');
        }

        for (int p = 0; p < maxCycle-1; p++) {
            for (int j = 0; j < maxCycle - 1 -p; j++) {
                System.out.println("\n\n--Macrocycle number " + (p + 1) + "--");
                System.out.println("--Microcycle number " + (j + 1) + "--");
                String[] subArray1 = new String[halfMaxRam];
                String[] subArray2 = new String[halfMaxRam];
                boolean isCut = false;
                int cutLength = 0;

                System.arraycopy(initial, p * halfMaxRam, subArray1, 0, halfMaxRam);
                for (int i = 0; i < halfMaxRam; i++) {
                    if ((i+p*halfMaxRam) + halfMaxRam + halfMaxRam * j > initial.length-1){
                        System.out.println("\nSecond array is cut!");
                        isCut = true;
                        String[] newArr = new String[i];
                        System.arraycopy(subArray2, 0, newArr, 0, i);
                        subArray2 = new String[i];
                        subArray2 = newArr;
                        cutLength = subArray2.length;
                        break;
                    }
                    subArray2[i] = initial[(i+p*halfMaxRam) + halfMaxRam + halfMaxRam * j];
                }

                ramArray1 = new ArrayList<>(Arrays.asList(subArray1));
                ramArray2 = new ArrayList<>(Arrays.asList(subArray2));

                System.out.println("\nramArr1:");
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

                if (isCut) {
                    for (int i = 0; i < cutLength; i++) {
                        ramArray2.add(i, ramArray1.get(i + halfMaxRam));
                    }
                } else {
                    for (int i = 0; i < halfMaxRam; i++) {
                        ramArray2.add(i, ramArray1.get(i + halfMaxRam));
                    }
                }

                System.out.println("ramArr2 after refactoring:");
                for (String number : ramArray2) {
                    System.out.print(number + " ");
                }
                System.out.println();

                for (int i = 0; i < halfMaxRam; i++) {
                    initial[(i+p*halfMaxRam)] = ramArray1.get(i);
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

                System.out.println("New sorted big array after cycle:");
                for (String str : initial) {
                    System.out.print(str + ' ');
                }
//                isCut = false;
//                cutLength = 0;
            }
        }
    }
}
