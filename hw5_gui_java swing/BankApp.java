public class BankApp {
    public static MainView mainv = new MainView();
    public static OpenView openv = new OpenView();
    public static DepositView depositv = new DepositView();
    public static WithdrawView withdrawv = new WithdrawView();
    public static DataAccessBank dab = new DataAccessBank();
    
    public static void main(String[] args) {
        mainv.setVisible(true);
    }
}
