import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    
    public MainView() {
        // Create the JFrame (window)
        this.setTitle("Bank Management System");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 250);
        this.setLayout(new FlowLayout()); // Set the layout manager to FlowLayout
        
        JPanel panel = new JPanel(new GridLayout(2, 1, 50,30));

        // Create the panel and buttons for each service
        JPanel btnpanel = new JPanel(new GridLayout(4, 1, 10, 10));
        JLabel question = new JLabel("What services do you want to use?");
        JButton openbtn = new JButton("Open new account");
        JButton depositbtn = new JButton("Deposit");
        JButton withdrawbtn = new JButton("Withdraw");
        
        // Add the buttons to the panel
        btnpanel.add(question);
        btnpanel.add(openbtn);
        btnpanel.add(depositbtn);
        btnpanel.add(withdrawbtn);

        openbtn.addActionListener(e -> {
            this.setVisible(false);
            BankApp.openv.setVisible(true);
        });

        depositbtn.addActionListener(e -> {
            this.setVisible(false);
            BankApp.depositv.setVisible(true);
        });

        withdrawbtn.addActionListener(e -> {
            this.setVisible(false);
            BankApp.withdrawv.setVisible(true);
        });

        // Create panel and button for exit
        JPanel ePanel = new JPanel();
        JButton exitbtn = new JButton("Exit");
        ePanel.add(exitbtn);

        // Add all the panels to the frame
        panel.add(btnpanel);
        panel.add(ePanel);
        this.add(panel);

        exitbtn.addActionListener(e -> System.exit(0));

        this.setLocationRelativeTo(null);
        // Make the this visible
        this.setVisible(true);
    }
}