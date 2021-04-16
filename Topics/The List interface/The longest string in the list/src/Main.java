import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    static void changeList(List<String> list) {
        int largestEntryIndex = 0;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).length() > largestEntryIndex) {
                largestEntryIndex = i;
            }
        }

        int finalLargestEntryIndex = largestEntryIndex;
        list.replaceAll(e -> list.get(finalLargestEntryIndex));
    }

    /* Do not change code below */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        List<String> lst = Arrays.asList(s.split(" "));
        changeList(lst);
        lst.forEach(e -> System.out.print(e + " "));
    }
}