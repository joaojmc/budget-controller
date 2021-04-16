package budget.Models;

public class BudgetDetail {

    private final String category;
    private final String name;
    private final Double value;

    public BudgetDetail(String category, String name, Double value) {
        this.category = category;
        this.name = name;
        this.value = value;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "BudgetDetail{" +
                "category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
