package budget.Controllers;

import budget.Models.BudgetDetail;
import budget.Stratagems.SortAllStrategy;
import budget.Stratagems.Strategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BudgetController {

    // Singleton
    private static BudgetController instance = null;
    // Utility
    private final Scanner SCANNER = new Scanner(System.in);
    private final StringBuilder STRING_BUILDER = new StringBuilder();
    private final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.00", DecimalFormatSymbols.getInstance(Locale.US));
    // Labels
    private final List<String> MAIN_MENU_OPTIONS = List.of("Add income", "Add purchase", "Show list of purchases", "Balance", "Save", "Load", "Analyze (Sort)", "Exit");
    private final List<String> SORT_OPTIONS = List.of("Sort all purchases", "Sort by type", "Sort certain type", "Back");
    private final List<String> CATEGORIES = List.of("Food", "Clothes", "Entertainment", "Other", "All", "Back");
    private final List<String> CATEGORIES_WITHOUT_ALL = List.of("Food", "Clothes", "Entertainment", "Other", "Back");

    // Data structure
    private double totalMoney;
    private final List<BudgetDetail> BUDGET_ENTRIES = new ArrayList<>();
    private final File FILE = new File("./purchases.txt");

    private BudgetController() {
    }

    public static BudgetController getInstance() {
        if (instance == null) {
            instance = new BudgetController();
        }
        return instance;
    }

    public void init() {
        mainMenu();
    }

    private void consumeEmptyLine() {
        SCANNER.nextLine();
    }

    private void printLineBreak() {
        System.out.println();
    }

    private void mainMenu() {
        boolean isRunning = true;
        int userInput;
        String category;

        do {
            // Double loop since menus may be reprinted during normal execution without interruption
            do {
                printMenuPrefix(MAIN_MENU_OPTIONS);
                printMenu(MAIN_MENU_OPTIONS);

                userInput = SCANNER.nextInt();
                consumeEmptyLine();
                printLineBreak();

                switch (userInput) {
                    case 1:
                        addIncome();
                        break;
                    case 2:
                        do {
                            printMenuPrefix(CATEGORIES_WITHOUT_ALL);
                            printMenu(CATEGORIES_WITHOUT_ALL);
                            category = getOptionAsString(CATEGORIES_WITHOUT_ALL, selectOption(CATEGORIES_WITHOUT_ALL));
                            if (category.equals("EXIT")) {
                                break;
                            } else {
                                addPurchase(category);
                            }
                        } while (true);
                        break;
                    case 3:
                        do {
                            printMenuPrefix(CATEGORIES);
                            printMenu(CATEGORIES);
                            category = getOptionAsString(CATEGORIES, selectOption(CATEGORIES));
                            if (category.equals("EXIT")) {
                                break;
                            } else if (category.equals("All")) {
                                printAllCategories();
                            } else {
                                printCategory(category);
                            }
                        } while (true);
                        break;
                    case 4:
                        showBalance();
                        break;
                    case 5:
                        saveBudget();
                        break;
                    case 6:
                        loadBudget();
                        break;
                    case 7:
                        sortBudget();
                        break;
                    case 0:
                        System.out.print("Bye!\n");
                        isRunning = false;
                        break;
                    default:
                        System.out.print("Invalid option.\n");
                        break;
                }
            } while (userInput < 0 || userInput > MAIN_MENU_OPTIONS.size());
        } while (isRunning);
    }

    private void printMenuPrefix(List<String> menuOptions) {
        STRING_BUILDER.setLength(0);

        if (MAIN_MENU_OPTIONS.equals(menuOptions)) {
            STRING_BUILDER.append("Choose your action:\n");
        } else if (CATEGORIES.equals(menuOptions)) {
            STRING_BUILDER.append("Choose the type of purchases\n");
        } else if (CATEGORIES_WITHOUT_ALL.equals(menuOptions)) {
            STRING_BUILDER.append("Choose the type of purchase\n");
        }

        System.out.print(STRING_BUILDER);
    }

    private void printMenu(List<String> menuOptions) {
        STRING_BUILDER.setLength(0);

        for (int i = 0; i < menuOptions.size(); i++) {
            String currentOption = menuOptions.get(i);
            if (currentOption.equals("Exit")) {
                STRING_BUILDER.append(String.format("0) %s%n", currentOption));
            } else {
                STRING_BUILDER.append(String.format("%d) %s%n", i + 1, currentOption));
            }
        }

        System.out.print(STRING_BUILDER);
    }

    private int selectOption(List<String> optionList) {
        int option;
        do {
            option = SCANNER.nextInt();
            consumeEmptyLine();
            printLineBreak();

            // Exit/Back option was selected
            if (option == optionList.size()) {
                return -1;
            }
        } while (option < 1 || option > optionList.size());
        return option;
    }

    private String getOptionAsString(List<String> optionList, int index) {
        if (index == -1) return "EXIT";
        return optionList.get(index - 1);
    }

    private void addIncome() {
        System.out.println("Enter income:");
        double income = SCANNER.nextDouble();
        consumeEmptyLine();
        totalMoney += ((income > 0) ? income : 0);

        BUDGET_ENTRIES.add(new BudgetDetail("Income", "Income added", income));
        System.out.println("Income was added!\n");
    }

    private void addPurchase(String category) {
        System.out.println("Enter purchase name:");
        String name = SCANNER.nextLine();
        //consumeEmptyLine();

        System.out.println("Enter its price:");
        double value = SCANNER.nextDouble();
        consumeEmptyLine();

        totalMoney -= value;
        totalMoney = (totalMoney > 0) ? totalMoney : 0;

        BUDGET_ENTRIES.add(new BudgetDetail(category, name, value));
        System.out.println("Purchase was added!\n");
    }

    private void printAllCategories() {
        if (BUDGET_ENTRIES.isEmpty()) {
            System.out.println("The purchase list is empty!\n");
            return;
        }

        double totalExpenses = 0;
        // Clear any previous content
        STRING_BUILDER.setLength(0);
        STRING_BUILDER.append("All:\n");

        for (BudgetDetail budgetDetail : BUDGET_ENTRIES) {
            if (!budgetDetail.getCategory().equals("Income")) {
                STRING_BUILDER.append(String.format("%s $%.2f%n", budgetDetail.getName(), budgetDetail.getValue()));
                totalExpenses += budgetDetail.getValue();
            }
        }

        // Final printing changes
        STRING_BUILDER.append(String.format("Total sum: $%.2f%n", totalExpenses));
        System.out.println(STRING_BUILDER);
    }

    private void printAllCategoriesSummary() {
        double totalExpensesCategory;
        // Clear any previous content
        STRING_BUILDER.setLength(0);

        List<String> categoriesForOutput = new ArrayList<>(CATEGORIES_WITHOUT_ALL);
        categoriesForOutput.remove("Back");

        Map<String, Double> expensesByCategory = new HashMap<>();

        for (String cat : categoriesForOutput) {
            totalExpensesCategory = 0;
            for (BudgetDetail budgetDetail : BUDGET_ENTRIES) {
                if (budgetDetail.getCategory().equals(cat)) {
                    totalExpensesCategory += budgetDetail.getValue();
                }
            }
            expensesByCategory.put(cat, totalExpensesCategory);
        }

        expensesByCategory = expensesByCategory.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));

        for (var expense : expensesByCategory.entrySet()) {
            STRING_BUILDER.append(String.format("%s - $%.2f%n", expense.getKey(), expense.getValue()));
        }

        System.out.println(STRING_BUILDER);
    }

    private void printCategory(String category) {
        if (BUDGET_ENTRIES.isEmpty()) {
            System.out.println("The purchase list is empty!\n");
            return;
        }

        double totalExpenses = 0;
        // Clear any previous content
        STRING_BUILDER.setLength(0);
        STRING_BUILDER.append(category).append(":\n");

        for (BudgetDetail budgetDetail : BUDGET_ENTRIES) {
            if (budgetDetail.getCategory().equals(category)) {
                STRING_BUILDER.append(String.format("%s $%.2f%n", budgetDetail.getName(), budgetDetail.getValue()));
                totalExpenses += budgetDetail.getValue();
            }
        }

        // Final printing changes
        STRING_BUILDER.append("Total sum: ").append(String.format("$%.2f%n", totalExpenses));
        System.out.println(STRING_BUILDER);
    }

    private void showBalance() {
        System.out.printf("Balance: $%.2f%n%n", totalMoney);
    }

    private void saveBudget() {
        try (PrintWriter printWriter = new PrintWriter(FILE)) {
            STRING_BUILDER.setLength(0);
            for (BudgetDetail budgetDetail : BUDGET_ENTRIES) {
                if (!budgetDetail.getCategory().equals("Income")) {
                    STRING_BUILDER.append(String.format("%s|%s|%s%n",
                            budgetDetail.getCategory(),
                            budgetDetail.getName(),
                            DECIMAL_FORMAT.format(budgetDetail.getValue())));
                }
            }
            STRING_BUILDER.append(String.format("%s|%s%n", "Balance", DECIMAL_FORMAT.format(totalMoney)));
            printWriter.print(STRING_BUILDER);
            System.out.println("Purchases were saved!\n");
        } catch (FileNotFoundException e) {
            System.err.printf(
                    "Could not find the desired file: %s%n.", FILE.getName());
            e.printStackTrace();
        }
    }

    private void loadBudget() {
        try (Scanner fileScanner = new Scanner(FILE)) {
            String[] nextLineParts;
            BUDGET_ENTRIES.clear();
            while (fileScanner.hasNext()) {
                nextLineParts = fileScanner.nextLine().split("\\|");
                if (nextLineParts[0].equals("Balance")) {
                    totalMoney = DECIMAL_FORMAT.parse(nextLineParts[1]).doubleValue();
                } else {
                    BUDGET_ENTRIES.add(new BudgetDetail(nextLineParts[0], nextLineParts[1], Double.parseDouble(nextLineParts[2])));
                }
            }
            System.out.println("Purchases were loaded!\n");
        } catch (FileNotFoundException e) {
            System.err.printf(
                    "Could not find the desired file: %s%n.", FILE.getName());
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.print("Could not parse the file.\n");
            e.printStackTrace();
        }
    }

    private void sortBudget() {
        int option;
        Strategy strategy;
        do {
            printMenuPrefix(SORT_OPTIONS);
            printMenu(SORT_OPTIONS);
            option = selectOption(SORT_OPTIONS);
            switch (option) {
                case 1:
                    strategy = new SortAllStrategy();
                    strategy.execute(BUDGET_ENTRIES);
                    printAllCategories();
                    break;
                case 2:
                    printAllCategoriesSummary();
                    break;
                case 3:
                    printMenuPrefix(CATEGORIES_WITHOUT_ALL);
                    printMenu(CATEGORIES_WITHOUT_ALL);
                    String category = getOptionAsString(CATEGORIES_WITHOUT_ALL, selectOption(CATEGORIES_WITHOUT_ALL));
                    printCategory(category);
                    break;
                case -1:
                    break;
                default:
                    System.err.println("Invalid strategy, exiting.");
                    break;
            }
        } while (option != -1);
    }
}
