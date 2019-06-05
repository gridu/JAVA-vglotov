import org.testng.annotations.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.testng.internal.junit.ArrayAsserts.assertArrayEquals;

public class Main {

    public static void main(String[] args) {

        String pathInput = "src/main/resources/input.txt";
        String pathOutput = "src/main/resources/output.txt";
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(pathInput), Charset.forName("UTF-8"));
        String[] strArray = lines.toArray(new String[lines.size()]);

        mergeSort(strArray, 0, strArray.length - 1);
        System.out.println(Arrays.toString(strArray));
        write(pathOutput, strArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void mergeSort(String[] a, int from, int to) {
        if (from == to) {
            return;
        }
        int mid = (from + to) / 2;
        mergeSort(a, from, mid);
        mergeSort(a, mid + 1, to);
        merge(a, from, mid, to);
    }

    public static void merge(String[] a, int from, int mid, int to) {
        int n = to - from + 1;
        String[] b = new String[n];
        int i1 = from;
        int i2 = mid + 1;
        int j = 0;

        while (i1 <= mid && i2 <= to) {
            if (a[i1].compareTo(a[i2]) < 0) {
                b[j] = a[i1];
                i1++;
            } else {
                b[j] = a[i2];
                i2++;
            }
            j++;
        }

        while (i1 <= mid) {
            b[j] = a[i1];
            i1++;
            j++;
        }

        while (i2 <= to) {
            b[j] = a[i2];
            i2++;
            j++;
        }

        for (j = 0; j < n; j++) {
            a[from + j] = b[j];
        }
    }

    public static void write (String filename, String[] x) throws IOException{
        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(new FileWriter(filename));
        for (int i = 0; i < x.length; i++) {
            outputWriter.write(x[i]);
            outputWriter.newLine();
        }
        outputWriter.flush();
        outputWriter.close();
    }

    @Test
    public void positiveTest() {
        String[] actual = { "one", "two", "3" };
        String[] expected = { "3", "one", "two" };
        Main.mergeSort(actual,0, actual.length - 1);
        assertArrayEquals(expected, actual);
    }
}