import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> inputList = new ArrayList<>();
        String nextLine;
        do {
            nextLine = scanner.nextLine();
            inputList.add(nextLine);
        } while (nextLine.isEmpty() || !nextLine.equals("0"));

        inputList.remove("0");

        for (String s : inputList) {
            tryToParseInt(s);
        }
    }

    private static void tryToParseInt(String input) {
        int result;
        try {
            result = Integer.parseInt(input);
            System.out.println(result * 10);
        } catch (Exception e) {
            System.out.printf("Invalid user input: %s%n", input);
        }
    }
}