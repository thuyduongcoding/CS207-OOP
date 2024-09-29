public class CheckingAccount extends AccountModel{
    public CheckingAccount(String accountNumber) {
        super(accountNumber);
    }
    
    @Override
    public int withdraw(double amount) {
        double currentBalance = getcurrentBalance();
        double newBalance = 0;
        if (currentBalance < amount) {
            newBalance = currentBalance - amount - 30;  
        } else {
            newBalance = currentBalance - amount;
        }
        DataAccessBank dab = new DataAccessBank();
        int withdrawStatus = dab.updateBalance(getAccountNumber(), newBalance);
        return withdrawStatus;
    } 
}
