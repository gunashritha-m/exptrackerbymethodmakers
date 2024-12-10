public class User {
    private String name;
    private double monthlyIncome;
    private double totalBudget;
    private String phoneNumber;

    public User(String name, double monthlyIncome, String phoneNumber) {
        this.name = name;
        this.monthlyIncome = monthlyIncome;
        this.totalBudget = monthlyIncome * 0.5;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public double getTotalBudget() {
        return totalBudget;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setTotalBudget(double totalBudget) {
        this.totalBudget = totalBudget;
    }
}
