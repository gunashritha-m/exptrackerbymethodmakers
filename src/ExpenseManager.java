import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
public class ExpenseManager {
    private User user;
    private List<Expense> expenses;

    public ExpenseManager(User user) {
        this.user = user;
        this.expenses = new ArrayList<>();
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public double getTotalExpenses() {
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }

    public List<Expense> getExpensesByCategory(Category category) {
        return expenses.stream()
                .filter(expense -> expense.getCategory() == category)
                .collect(Collectors.toList());
    }

    public User getUser() {
        return user;
    }

    public void deleteExpense(Expense expense) {
        expenses.remove(expense);
    }

    public List<Expense> getExpenses() {
        return expenses;
    }
}
