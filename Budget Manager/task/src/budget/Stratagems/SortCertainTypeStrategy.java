package budget.Stratagems;

import budget.Models.BudgetDetail;

import java.util.ArrayList;
import java.util.List;

public class SortCertainTypeStrategy implements Strategy {

    private final String category;

    public SortCertainTypeStrategy(String category) {
        this.category = category;
    }

    @Override
    public void execute(List<BudgetDetail> budgetLog) {

    }
}
