#include <stdio.h>
#include <string.h>

int MAX_ACCOUNTS = 1000;

struct CheckingAccount{
    int number;
    float balance;
};

struct SavingAccount{
    int number;
    float balance;
};

void createAccount(struct CheckingAccount checking_accounts[], struct SavingAccount saving_accounts[], int *accountCount, int *checkingCount, int *savingCount){
    if (*accountCount >= MAX_ACCOUNTS) {
        printf("Cannot create more accounts due to limit number of accounts allowed to create.");
        return;
    }
    int account_type;
    float deposit;

    while (1){  
        printf("What account type you want to create?\n");
        printf("0: checking account\n");
        printf("1: saving account\n");
        scanf("%d", &account_type);
    
        if (account_type != 0 && account_type != 1) {
            printf("Invalid account type. Please try again.\n");
            return;
        }
        
        printf("How much do you want to deposit? ");
        scanf("%f", &deposit);

        while(deposit<= 0){
            printf("Deposit value must be positive. Please enter the deposit again.\n");
            printf("How much do you want to deposit? ");
            scanf("%f", &deposit);
        }

        if (account_type == 0){
            checking_accounts[*checkingCount].number = *accountCount;
            checking_accounts[*checkingCount].balance = deposit;
            (*checkingCount)++;
            break;
        }
        else if (account_type == 1)
        {
            saving_accounts[*savingCount].number = *accountCount;
            saving_accounts[*savingCount].balance = deposit;
            (*savingCount)++;
            break;
        }
        else {
            printf("Invalid choice of account type. Please try again.\n");
        }
    }
    printf("Account created successfully.\n");
    printf("Your account number is %d with balance of $%.2f.", *accountCount, deposit);
    (*accountCount)++;

}

void deposit(struct CheckingAccount checking_accounts[], struct SavingAccount saving_accounts[], int checkingCount, int savingCount){
    int accountNumber;
    float deposit;
    int account_type;
    printf("Please choose the type of your account. 0 for checking account and 1 for saving account: ");
    scanf("%d", &account_type);

    if (account_type != 0 && account_type != 1) {
        printf("Invalid account type. Please try again.\n");
        return;
    }

    printf("Please enter your account number: ");
    scanf("%d", &accountNumber);
     

    if (account_type == 0){
        for (int i = 0; i < checkingCount; i++) {
            if (checking_accounts[i].number == accountNumber) {
                while (1) {
                    printf("Please enter the amount you want to deposit: ");
                    scanf("%f", &deposit);
                    if (deposit <=0) {
                        printf("Deposit value must be positive. Please enter the deposit again");
                    } 
                    else {
                        break;
                    }
                }
                checking_accounts[i].balance += deposit;
                printf("Deposit successfully. Your checking account's current balance is $%.2f", checking_accounts[i].balance);
                return;
            }
        } 
    }
    else {
        for (int i = 0; i < savingCount; i++) {
            if (saving_accounts[i].number == accountNumber) {
                while (1) {
                    printf("Please enter the amount you want to deposit: ");
                    scanf("%f", &deposit);
                    if (deposit <=0) {
                        printf("Deposit value must be positive. Please enter the deposit again");
                    } 
                    else {
                        break;
                    }
                }
                saving_accounts[i].balance += deposit;
                printf("Deposit successfully. Your saving account's current balance is $%.2f", saving_accounts[i].balance);
                return;
            }
        }
    }
    printf("Cannot find your account number. Please try again.");
    return;
}
void withdraw(struct CheckingAccount checking_accounts[], struct SavingAccount saving_accounts[], int checkingCount, int savingCount) {
    int accountNumber;
    float withdraw;
    int account_type;
    printf("Please choose the type of your account. 0 for checking account and 1 for saving account: ");
    scanf("%d", &account_type);

    if (account_type != 0 && account_type != 1) {
        printf("Invalid account type. Please try again.\n");
        return;
    }

    printf("Please enter your account number: ");
    scanf("%d", &accountNumber);


    if (account_type == 1) {    // Saving account
        for (int i = 0; i < savingCount; i++) {
            if (saving_accounts[i].number == accountNumber) {
                
                // Check if the withdraw value is positive
                while (1) {
                    printf("Please enter the amount you want to withdraw: ");
                    scanf("%f", &withdraw);
                    if (withdraw <=0) {
                        printf("Withdraw value must be positive. Please enter the withdraw again\n");
                    } 
                    else {
                        break;
                    }
                }
                if (saving_accounts[i].balance >= withdraw) {
                    saving_accounts[i].balance -= withdraw;
                    printf("Withdraw successfully. Your current balance after withdrawal is $%.2f", saving_accounts[i].balance);
                    return;
                }
                else {
                    printf("Your account balance is not enough. The current balance is $%.2f. Please try again.", saving_accounts[i].balance);
                    return;
                }
            }
        }
    }
    else {   // Checking account
        for (int i = 0; i < checkingCount; i++) {
            if (checking_accounts[i].number == accountNumber) {  
                // Check if the withdraw value is positive
                while (1) {
                    printf("Please enter the amount you want to withdraw: ");
                    scanf("%f", &withdraw);
                    if (withdraw <=0) {
                        printf("Withdraw value must be positive. Please enter the withdraw again");
                    } 
                    else {
                        break;
                    }
                } 
                if (checking_accounts[i].balance >= withdraw) {
                    checking_accounts[i].balance -= withdraw;
                        printf("Withdraw successfully. Your current balance after withdrawal is $%.2f", checking_accounts[i].balance);
                        return;
                }
                else {
                    int withdraw_continue; 
                    printf("Your current balance if not enough. The current balance is $%.2f. If continue with withdrawal, an overdraft fee of 30 USD will be charged to the account.\nDo you want to continue: \n", checking_accounts[i].balance);
                    printf("0 for no and 1 for yes: ");
                    scanf("%d", &withdraw_continue);
                    switch (withdraw_continue) {
                        case 0:
                            printf("Stop withdrawal process. Please try again");
                            return;
                        case 1: 
                            checking_accounts[i].balance -= (withdraw + 30);
                            printf("Withdraw successfully but your balance becomes negative after withdrawal. An overdraft fee of 30 USD is charged to the account. Your current balance is $%.2f", checking_accounts[i].balance);
                            return;
                        default:
                            printf("Invalid choice. Please try again.");
                            return;
                    }
                }
            }    
        }
    }
    printf("Cannot find your account number. Please try again!");
}

void update_interest(struct SavingAccount saving_accounts[], int savingCount) {
    // Saving accounts
    for (int i = 0; i < savingCount; i++) {
        saving_accounts[i].balance *= 1.06; 
    } 
    printf("Finish updating all acount interest");
}
int main()
{
    struct CheckingAccount checking_accounts[MAX_ACCOUNTS];
    struct SavingAccount saving_accounts[MAX_ACCOUNTS];
    int accountCount = 0;
    int checkingCount = 0;
    int savingCount = 0;
    int choice;

    do {    
        printf("\n=======================\n");
        printf("Here are the bank management services: \n");
        printf("1. Create New Account\n");
        printf("2. Deposit Money\n");
        printf("3. Withdraw Money\n");
        printf("4. Update All Account Interest\n");
        printf("5. Exit\n");
        printf("Please select the service you want to proceed with: ");
        scanf("%d", &choice);
        
        switch (choice) {
            case 1:
                createAccount(checking_accounts, saving_accounts, &accountCount, &checkingCount, &savingCount);
                break;
            case 2:
                deposit(checking_accounts, saving_accounts, checkingCount, savingCount);
                break;
            case 3:
                withdraw(checking_accounts, saving_accounts, checkingCount, savingCount);
                break;
            case 4:
                update_interest(saving_accounts, savingCount);
            case 5:
        }
    } while (choice != 5);

    return 0;
}