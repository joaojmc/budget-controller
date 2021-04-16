package budget.Stratagems;

import budget.Models.BudgetDetail;

import java.util.Comparator;
import java.util.List;

public class SortAllStrategy implements Strategy {

    @Override
    public void execute(List<BudgetDetail> budgetLog) {
        budgetLog.sort(Comparator.comparing(BudgetDetail::getValue).reversed());
    }
}
