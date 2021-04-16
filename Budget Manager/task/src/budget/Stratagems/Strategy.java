package budget.Stratagems;

import budget.Models.BudgetDetail;

import java.util.List;

public interface Strategy {

    void execute(List<BudgetDetail> budgetLog);
}
