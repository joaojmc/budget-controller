import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputLine = scanner.nextLine();
        String[] inputTokens = inputLine.split(" ");
        int[] numbers = new int[inputTokens.length];
        for (int i = 0; i < inputTokens.length; i++) {
            numbers[i] = Integer.parseInt(inputTokens[i]);
        }
        System.out.println(bubbleSortSwapCount(numbers));
    }

    private static int bubbleSortSwapCount(int[] numbers) {
        int count = 0;
        for (int i = 0; i < numbers.length - 1; i++) {
            for (int j = 0; j < numbers.length - i - 1; j++) {
                if (numbers[j] < numbers[j + 1]) {
                    int temp = numbers[j];
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = temp;
                    count++;
                }
            }
        }
        return count;
    }
}