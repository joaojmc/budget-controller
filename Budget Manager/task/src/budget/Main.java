package budget;

import budget.Controllers.BudgetController;

public class Main {

    private static final BudgetController BUDGET_CONTROLLER = BudgetController.getInstance();

    public static void main(String[] args) {
        BUDGET_CONTROLLER.init();
    }
}

