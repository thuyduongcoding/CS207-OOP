import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WithdrawView extends JFrame implements ActionListener{
    JLabel accountLabel = new JLabel("Please enter your account number: ");
    private JTextField accountField = new JTextField(15);

    JLabel withdrawLabel = new JLabel("Please enter withdraw amount: ");
    private JTextField withdrawField = new JTextField(10);

    public WithdrawView() {
        this.setTitle("Withdraw Money");
        this.setSize(400, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel textpanel = new JPanel(new GridLayout(2,1,10,10));


        JPanel accountPanel = new JPanel();
        accountField.setDocument(new JTextFieldLimit(100));
        accountPanel.add(accountLabel);
        accountPanel.add(accountField);

        JPanel withdrawPanel = new JPanel();
        withdrawField.setDocument(new JTextFieldLimit(10));
        withdrawPanel.add(withdrawLabel);
        withdrawPanel.add(withdrawField);

        withdrawField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                // Check if the character is a digit or a decimal point
                if (!(Character.isDigit(c) || c == '.')) {
                    e.consume(); // Ignore non-digit and non-decimal characters
                }

                // Allow only one decimal point
                if (c == '.' && withdrawField.getText().contains(".")) {
                    e.consume(); // Ignore additional decimal points
                }
            }
        });

        textpanel.add(accountPanel);
        textpanel.add(withdrawPanel);

        JPanel wiPanel = new JPanel();
        JButton withdrawButton = new JButton("Withdraw");
        JButton cancelButton = new JButton("Cancel");

        wiPanel.add(withdrawButton);
        wiPanel.add(cancelButton);

        withdrawButton.addActionListener(this);

        cancelButton.addActionListener(e -> 
        {
            this.setVisible(false);
            BankApp.mainv.setVisible(true);
        });

        JPanel panel = new JPanel(new GridLayout(2,1,5,5));
        panel.add(textpanel);
        panel.add(wiPanel);

        this.add(panel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int withdrawStatus = 0;
            String accountNumber = accountField.getText();
            AccountModel account;
            Double withdrawValue = Double.parseDouble(withdrawField.getText());
            // create account
            String type = BankApp.dab.getType(accountNumber);

            // Check if the account type is null or empty
            if (type == null || type.isEmpty()) {
                withdrawStatus = 0; // Set withdraw status to 0 for invalid account type
            } else if (type.equals("Checking")) {
                account = new CheckingAccount(accountNumber);
                withdrawStatus = account.withdraw(withdrawValue);
            } else if (type.equals("Saving")) { // Ensure you handle "Saving" as well
                account = new SavingAccount(accountNumber);
                withdrawStatus = account.withdraw(withdrawValue);
            } else {
                withdrawStatus = 0; // Set withdraw status to 0 for invalid account type
            }

            if (withdrawStatus == 0) {
                JOptionPane.showMessageDialog(null, "Withdraw failed. Please check your account number and amount.");
            } else {
                JOptionPane.showMessageDialog(null, "Successfully withdraw the money!");
                this.setVisible(false);
                BankApp.mainv.setVisible(true);
            }
        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, "Unexpected error");
            error.printStackTrace();
        }
    }
}
