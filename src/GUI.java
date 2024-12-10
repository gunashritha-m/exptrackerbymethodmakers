import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.List;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


public class GUI {
    private JFrame frame;
    private ExpenseManager manager;
    private User user;


    public GUI() {
        frame = new JFrame("Expense Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);

        String name = JOptionPane.showInputDialog("Enter your name:");
        double monthlyIncome = Double.parseDouble(JOptionPane.showInputDialog("Enter your monthly income:"));
        String phoneNumber = JOptionPane.showInputDialog("Enter your phone number (10 digits):");

        if (!phoneNumber.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(frame, "Invalid phone number! Please enter exactly 10 digits.");
            return;
        }

        user = new User(name, monthlyIncome, phoneNumber);
        manager = new ExpenseManager(user);

        JOptionPane.showMessageDialog(frame, "Welcome " + name + "! Your monthly budget is: " + user.getTotalBudget());
    }

    public void showMainMenu() {
        frame.getContentPane().removeAll();

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(240, 248, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        JLabel title = new JLabel("Expense Tracker", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(0, 102, 204));
        title.setBorder(new EmptyBorder(10, 10, 20, 10));
        panel.add(title, gbc);

        JButton addExpenseButton = new JButton("Add Expense");
        customizeButton(addExpenseButton, new Color(50, 205, 50));
        addExpenseButton.addActionListener(e -> showAddExpenseWindow());
        panel.add(addExpenseButton, gbc);

        JButton viewReportButton = new JButton("View Report");
        customizeButton(viewReportButton, new Color(30, 144, 255));
        viewReportButton.addActionListener(e -> showReportWindow());
        panel.add(viewReportButton, gbc);

        JButton adjustIncomeButton = new JButton("Adjust Monthly Income");
        customizeButton(adjustIncomeButton, new Color(255, 165, 0));
        adjustIncomeButton.addActionListener(e -> adjustMonthlyIncome());
        panel.add(adjustIncomeButton, gbc);

        JButton adjustBudgetButton = new JButton("Adjust Budget");
        customizeButton(adjustBudgetButton, new Color(255, 69, 0));
        adjustBudgetButton.addActionListener(e -> adjustBudget());
        panel.add(adjustBudgetButton, gbc);

        JButton deleteExpenseButton = new JButton("Delete Expense");
        customizeButton(deleteExpenseButton, new Color(241, 92, 93));
        deleteExpenseButton.addActionListener(e -> showDeleteExpenseWindow());
        panel.add(deleteExpenseButton, gbc);

        JButton viewLogButton = new JButton("View Transaction Log");
        customizeButton(viewLogButton, new Color(0, 191, 255));
        viewLogButton.addActionListener(e -> showTransactionLogWindow());
        panel.add(viewLogButton, gbc);

        JButton exitButton = new JButton("Exit");
        customizeButton(exitButton, new Color(255, 0, 0));
        exitButton.addActionListener(e -> System.exit(0));
        panel.add(exitButton, gbc);

        frame.add(panel);
        frame.setVisible(true);
    }

    private JButton createColoredButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 50));
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setOpaque(true);
        return button;
    }


    private void showAddExpenseWindow() {
        JDialog addExpenseDialog = new JDialog(frame, "Add Expense", true);
        addExpenseDialog.setSize(350, 250);
        addExpenseDialog.setLocationRelativeTo(frame);
        addExpenseDialog.setLayout(new GridLayout(5, 2, 5, 5));  

        addExpenseDialog.add(new JLabel("Category:"));
        JComboBox<Category> categoryBox = new JComboBox<>(Category.values());
        addExpenseDialog.add(categoryBox);

        addExpenseDialog.add(new JLabel("Amount:"));
        JTextField amountField = new JTextField();
        addExpenseDialog.add(amountField);

        addExpenseDialog.add(new JLabel("Description:"));
        JTextField descriptionField = new JTextField();
        addExpenseDialog.add(descriptionField);

        addExpenseDialog.add(new JLabel("Mode of Payment:"));
        JComboBox<PaymentMode> paymentModeBox = new JComboBox<>(PaymentMode.values());
        addExpenseDialog.add(paymentModeBox);

        JButton addButton = new JButton("Add");
        addButton.setBackground(new Color(50, 205, 50));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setBorder(new LineBorder(Color.BLACK, 1));
        addButton.addActionListener(e -> {
            try {
                Category category = (Category) categoryBox.getSelectedItem();
                double amount = Double.parseDouble(amountField.getText());
                String description = descriptionField.getText();

           
                Expense expense;
                if (amount > user.getTotalBudget() / 2 ) {
                    expense = new LuxuryExpense(category, amount, description);
                } else {
                    expense = new RegularExpense(category, amount, description);
                }

                manager.addExpense(expense);

                String transactionDetails = String.format("Category: %s, Amount: %.2f, Description: %s, Type: %s",
                        category, amount, description, expense.getClass().getSimpleName());
                TransactionLogger.logTransaction(transactionDetails);

                JOptionPane.showMessageDialog(addExpenseDialog, "Expense added and logged successfully!");
                addExpenseDialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(addExpenseDialog, "Invalid input. Please try again.");
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(220, 20, 60));  
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setBorder(new LineBorder(Color.BLACK, 1));
        cancelButton.addActionListener(e -> addExpenseDialog.dispose());

        addExpenseDialog.add(addButton);
        addExpenseDialog.add(cancelButton);

        addExpenseDialog.setVisible(true);
    }


    private void showReportWindow() {
        StringBuilder reportText = new StringBuilder("<html><body style='font-family: Arial;'>");
        double totalExpenses = manager.getTotalExpenses();

        reportText.append("<h2>Expense Report</h2>");
        reportText.append("Monthly Budget: ").append(user.getTotalBudget()).append("<br>");
        reportText.append("Total Expenses: ").append(totalExpenses).append("<br>");

        if (totalExpenses > user.getTotalBudget()) {
            reportText.append("<p style='color:red'>⚠ Over budget by ").append(totalExpenses - user.getTotalBudget()).append("</p>");
        } else {
            reportText.append("<p style='color:green'>You are within budget!</p>");
        }

        reportText.append("<br>Expenses by Category:<br>");
        for (Category category : Category.values()) {
            double categoryTotal = manager.getExpensesByCategory(category).stream().mapToDouble(Expense::getAmount).sum();
            reportText.append(category).append(": ").append(categoryTotal).append("<br>");
        }

        reportText.append("</body></html>");

        JLabel reportLabel = new JLabel(reportText.toString());
        JFrame reportFrame = new JFrame("Expense Report");
        reportFrame.setSize(400, 300);
        reportFrame.add(reportLabel);
        reportFrame.setVisible(true);
    }

    private void adjustMonthlyIncome() {
        double newIncome = Double.parseDouble(JOptionPane.showInputDialog("Enter new monthly income:"));
        user = new User(user.getName(), newIncome, user.getPhoneNumber());
        manager = new ExpenseManager(user);
        JOptionPane.showMessageDialog(null, "Monthly Income adjusted successfully! New Budget: " + user.getTotalBudget());
    }

    private void adjustBudget() {
        double newBudget = Double.parseDouble(JOptionPane.showInputDialog("Enter new budget:"));
        user.setTotalBudget(newBudget);
        JOptionPane.showMessageDialog(null, "Budget adjusted successfully!");
    }

    public void showDeleteExpenseWindow() {
        JDialog deleteExpenseDialog = new JDialog(frame, "Delete Expense", true);
        deleteExpenseDialog.setSize(400, 300);
        deleteExpenseDialog.setLocationRelativeTo(frame);

        List<Expense> expenseList = manager.getExpenses();
        DefaultListModel<Expense> expenseModel = new DefaultListModel<>();
        expenseList.forEach(expenseModel::addElement);

        JList<Expense> expenseJList = new JList<>(expenseModel);
        expenseJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(expenseJList);
        deleteExpenseDialog.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); 

        JButton deleteButton = createColoredButton("Delete", new Color(241, 92, 93));
        deleteButton.addActionListener(e -> {
            Expense selectedExpense = expenseJList.getSelectedValue();
            if (selectedExpense != null) {
                manager.deleteExpense(selectedExpense);

                expenseModel.removeElement(selectedExpense);

                JOptionPane.showMessageDialog(deleteExpenseDialog, "Expense deleted successfully!");
            } else {

                JOptionPane.showMessageDialog(deleteExpenseDialog, "No expense selected.");
            }
        });

        JButton cancelButton = createColoredButton("Cancel", new Color(220, 20, 60));
        cancelButton.addActionListener(e -> deleteExpenseDialog.dispose());

        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);
        deleteExpenseDialog.add(buttonPanel, BorderLayout.SOUTH);

        deleteExpenseDialog.setVisible(true);
    }

    private void showTransactionLogWindow() {
        try {

            File file = new File("transactions.log");
            if (!file.exists()) {
                JOptionPane.showMessageDialog(frame, "No transactions logged yet.");
                return;
            }

            JTextArea textArea = new JTextArea(20, 40);
            textArea.setEditable(false);

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                textArea.append(line + "\n");
            }
            reader.close();

            JScrollPane scrollPane = new JScrollPane(textArea);
            JFrame logFrame = new JFrame("Transaction Log");
            logFrame.setSize(500, 400);
            logFrame.add(scrollPane);
            logFrame.setVisible(true);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error reading log file.");
        }
    }

    private void customizeButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(new LineBorder(Color.BLACK, 1));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI();
            gui.showMainMenu();
        });
    }
}
