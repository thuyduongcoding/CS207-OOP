public class SavingAccount extends AccountModel {
    public SavingAccount(String accountNumber) {
        super(accountNumber);
    }
    
    @Override
    public int withdraw(double amount) {
        double currentBalance = getcurrentBalance();
        double newBalance = 0;
        int withdrawStatus = 0;
        if (currentBalance < amount) {
            return withdrawStatus; 
        } else {
            newBalance = currentBalance - amount;
        }
        DataAccessBank dab = new DataAccessBank();
        withdrawStatus = dab.updateBalance(getAccountNumber(), newBalance);
        return withdrawStatus;
    }
}
