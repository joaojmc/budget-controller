import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfElements = scanner.nextInt();
        Set<String> set = new TreeSet<>();

        for (int i = 0; i <= numberOfElements; i++) {
            set.add(scanner.nextLine());
        }

        for (String s : set) {
            System.out.println(s);
        }
    }
}