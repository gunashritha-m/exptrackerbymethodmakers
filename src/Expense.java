abstract class Expense {
    private Category category;
    private double amount;
    private String description;

    public Expense(Category category, double amount, String description) {
        this.category = category;
        this.amount = amount;
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public abstract String getType();

    @Override
    public String toString() {
        return "Expense{" +
                "category=" + category +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}

class RegularExpense extends Expense {
    public RegularExpense(Category category, double amount, String description) {
        super(category, amount, description);
    }

    @Override
    public String getType() {
        return "Regular Expense";
    }
}

class LuxuryExpense extends Expense {
    public LuxuryExpense(Category category, double amount, String description) {
        super(category, amount, description);
    }

    @Override
    public String getType() {
        return "Luxury Expense";
    }
}
