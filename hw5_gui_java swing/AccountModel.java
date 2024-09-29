public abstract class AccountModel {    
    private String accountNumber;
    private double balance;

    public AccountModel(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getcurrentBalance() {
        DataAccessBank dab = new DataAccessBank();
        balance = dab.getBalance(getAccountNumber());
        return balance;
    }

    public int deposit(double amount) {
        if (amount < 0) {
            return 0;
        }
        DataAccessBank dab = new DataAccessBank();
        double current_balance = getcurrentBalance();
        System.out.println(current_balance);
        double new_balance = current_balance+amount;

        int depositStatus = dab.updateBalance(getAccountNumber(), new_balance);
        return depositStatus;
    } ;
    
    public abstract int withdraw(double amount);
}