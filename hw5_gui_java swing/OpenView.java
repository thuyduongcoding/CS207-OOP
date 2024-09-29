import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

// Reference: How can we limit the number of characters inside a JTextField in Java? - Tutorialspoint
class JTextFieldLimit extends PlainDocument {
    private int limit;
    JTextFieldLimit(int limit) {
       super();
       this.limit = limit;
    }
    JTextFieldLimit(int limit, boolean upper) {
       super();
       this.limit = limit;
    }
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
       if (str == null)
          return;
       if ((getLength() + str.length()) <= limit) {
          super.insertString(offset, str, attr);
       }
    }
 }

public class OpenView extends JFrame implements ActionListener{
    // Account balance
    JLabel balanceLabel = new JLabel("How much do you want to deposit?");
    private JTextField balanceField = new JTextField(10);

    // Account type
    JLabel typeLabel = new JLabel("Please choose the account type: ");
    String[] typeOption = {"Saving", "Checking"}; // Options for the dropdown
    private JComboBox<String> typeBox = new JComboBox<>(typeOption);
    

    public OpenView(){
        this.setTitle("Open New Account");
        this.setSize(400, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel(new GridLayout(3,1,10,5));
        
        JPanel balancePanel = new JPanel();
        balanceField.setDocument(new JTextFieldLimit(10));
        
        balancePanel.add(balanceLabel);
        balancePanel.add(balanceField);

        balanceField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                // Check if the character is a digit or a decimal point
                if (!(Character.isDigit(c) || c == '.')) {
                    e.consume(); // Ignore non-digit and non-decimal characters
                }

                // Allow only one decimal point
                if (c == '.' && balanceField.getText().contains(".")) {
                    e.consume(); // Ignore additional decimal points
                }
            }
        });

        JPanel typePanel = new JPanel();
        typePanel.add(typeLabel);
        typePanel.add(typeBox);

        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");
        JPanel bPanel = new JPanel();
        bPanel.add(submitButton);
        bPanel.add(cancelButton);

        submitButton.addActionListener(this);

        cancelButton.addActionListener(e -> 
        {
            this.setVisible(false);
            BankApp.mainv.setVisible(true);
        });

        panel.add(balancePanel);
        panel.add(typePanel);
        panel.add(bPanel);

        this.add(panel);
    } 

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String accountNumber = BankApp.dab.generateNewAccountNumber();
            double initialBalance = Double.parseDouble(balanceField.getText());
            String type = (String) typeBox.getSelectedItem();
            
            // Add new account to the database
            int status = BankApp.dab.addNewAccount(accountNumber, initialBalance, type);
            if (status != 0) {
                JOptionPane.showMessageDialog(null, "Successfully create a new account.\nYour account number is " + accountNumber);
                this.setVisible(false);
                BankApp.mainv.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Error! Please try again!");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "ERROR!!!");
        }
    }
}