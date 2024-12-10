abstract class Budget {
    protected double amount;

    public Budget(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public abstract String getBudgetType();
}

class DefaultBudget extends Budget {
    public DefaultBudget(double monthlyIncome) {
        super(monthlyIncome * 0.5); // Default budget is 50% of income
    }

    @Override
    public String getBudgetType() {
        return "Default Budget (50% of income)";
    }
}

class CustomBudget extends Budget {
    public CustomBudget(double amount) {
        super(amount);
    }

    @Override
    public String getBudgetType() {
        return "Custom Budget";
    }
}
