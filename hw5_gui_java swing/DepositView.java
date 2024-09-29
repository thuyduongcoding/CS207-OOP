import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DepositView extends JFrame implements ActionListener{
    JLabel accountLabel = new JLabel("Please enter your account number: ");
    private JTextField accountField = new JTextField(15);
    

    JLabel depositLabel = new JLabel("Please enter deposit amount: ");
    private JTextField depositField = new JTextField(10);

    public DepositView() {
        this.setTitle("Deposit Money");
        this.setSize(400, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel textpanel = new JPanel(new GridLayout(2,1,10,10));


        JPanel accountPanel = new JPanel();
        accountField.setDocument(new JTextFieldLimit(100));
        accountPanel.add(accountLabel);
        accountPanel.add(accountField);

        JPanel depositPanel = new JPanel();
        depositField.setDocument(new JTextFieldLimit(10));
        depositPanel.add(depositLabel);
        depositPanel.add(depositField);

        depositField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                // Check if the character is a digit or a decimal point
                if (!(Character.isDigit(c) || c == '.')) {
                    e.consume(); // Ignore non-digit and non-decimal characters
                }

                // Allow only one decimal point
                if (c == '.' && depositField.getText().contains(".")) {
                    e.consume(); // Ignore additional decimal points
                }
            }
        });

        textpanel.add(accountPanel);
        textpanel.add(depositPanel);

        JPanel dePanel = new JPanel();
        JButton depositButton = new JButton("Deposit");
        JButton cancelButton = new JButton("Cancel");

        dePanel.add(depositButton);
        dePanel.add(cancelButton);

        depositButton.addActionListener(this);

        cancelButton.addActionListener(e -> 
        {
            this.setVisible(false);
            BankApp.mainv.setVisible(true);
        });

        JPanel panel = new JPanel(new GridLayout(2,1,5,5));
        panel.add(textpanel);
        panel.add(dePanel);

        this.add(panel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int depositStatus = 0;
            String accountNumber = accountField.getText();
            AccountModel account;
            Double depositValue = Double.parseDouble(depositField.getText());
            
            String type = BankApp.dab.getType(accountNumber);

            // Check if the account type is null or empty
            if (type == null || type.isEmpty()) {
                depositStatus = 0; // Set deposit status to 0 for invalid account type
            } else if (type.equals("Checking")) {
                account = new CheckingAccount(accountNumber);
                depositStatus = account.deposit(depositValue);
            } else if (type.equals("Saving")) { 
                account = new SavingAccount(accountNumber);
                depositStatus = account.deposit(depositValue);
            } else {
                depositStatus = 0; // Set deposit status to 0 for invalid account type
            }

            if (depositStatus == 0) {
                JOptionPane.showMessageDialog(null, "Deposit failed. Please check your account number and amount.");
            } else {
                JOptionPane.showMessageDialog(null, "Successfully deposit the money!");
                this.setVisible(false);
                BankApp.mainv.setVisible(true);
            }
        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, "Unexpected error");
            error.printStackTrace();
        }
    }
}
