class ReportGenerator {
    public static void generateSummaryReport(ExpenseManager manager) {
        double totalExpenses = manager.getTotalExpenses();

        double budgetAmount = manager.getUser().getTotalBudget();

        System.out.println("Expense Summary Report:");
        System.out.println("Total Expenses: " + totalExpenses);
        System.out.println("Budget: " + budgetAmount);

        if (totalExpenses > budgetAmount) {
            System.out.println("Warning: You've exceeded your budget!");
        } else {
            System.out.println("You are within your budget.");
        }
    }
}