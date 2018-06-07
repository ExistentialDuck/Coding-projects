//Stephen Hanna 109097796
package pkgfinal.project;

public class PrepaidCard extends BankCard {

    public PrepaidCard(String cardHolder, long cardNumber, double balance) {

        super(cardHolder, cardNumber);

        this.currentBalance = balance;
    }

    public PrepaidCard(String cardHolder, long cardNumber) {
        super(cardHolder, cardNumber);
        this.currentBalance = 0;
    }

    public boolean addTransaction(Transaction t) {
        if (freezeAccount == false) {
            if ((t.amount() <= 1.5 && t.amount()>=-1.5 && t.type().equals("debit")) && transactionList.size()>1) {
                Transaction last = transactionList.get(transactionList.size()-1);
                if (last.amount()>=-1.5 && last.amount() <= 1.5 && last.type().equals("debit")) {
                    if (t.merchant().equals(last.merchant())) {
                        freezeAccount = true;
                        return false;
                    } else {
                        if (t.type().equals("debit") && t.amount() <= currentBalance) {
                            t.addNotes("APPROVED");
                            currentBalance = currentBalance + t.amount();
                            transactionList.add(t);
                            return true;
                        } else if (t.type().equals("debit") && t.amount() > currentBalance) {
                            t.addNotes("DENIED");
                            return false;
                        } else if (t.type().equals("credit")) {
                            t.addNotes("APPROVED");
                            currentBalance = currentBalance + t.amount();
                            transactionList.add(t);
                            return true;
                        } else {
                            return false;
                        }
                    }
                } else {
                    if (t.type().equals("debit") && t.amount() <= currentBalance) {
                        t.addNotes("APPROVED");
                        currentBalance = currentBalance + t.amount();
                        transactionList.add(t);
                        return true;
                    } else if (t.type().equals("debit") && t.amount() > currentBalance) {
                        t.addNotes("DENIED");
                        return false;
                    } else if (t.type().equals("credit")) {
                        t.addNotes("APPROVED");
                        currentBalance = currentBalance + t.amount();
                        transactionList.add(t);
                        return true;
                    } else {
                        return false;
                    }
                }
            } else {

                if (t.type().equals("debit") && t.amount() <= currentBalance) {
                    t.addNotes("APPROVED");
                    currentBalance = currentBalance + t.amount();
                    transactionList.add(t);
                    return true;
                } else if (t.type().equals("debit") && t.amount() > currentBalance) {
                    t.addNotes("DENIED");
                    return false;
                } else if (t.type().equals("credit")) {
                    t.addNotes("APPROVED");
                    currentBalance = currentBalance + t.amount();
                    transactionList.add(t);
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    public boolean addFunds(double amount) {

        if (amount >= 0) {

            currentBalance = currentBalance + amount;
            Transaction deposit = new Transaction("top-up", "User payment", -1 * amount);
            transactionList.add(deposit);
            return true;

        } else {
            return false;
        }

    }

    public String toString() {
        return super.toString();
    }

    public void fixFraud() {
        Transaction last = transactionList.get(transactionList.size() - 1);
        Transaction secondToLast = transactionList.get(transactionList.size() - 2);
        Transaction first = new Transaction("credit", last.merchant(), last.amount());
        Transaction second = new Transaction("credit", secondToLast.merchant(), secondToLast.amount());
        first.addNotes("APPROVED");
        currentBalance = currentBalance + first.amount();
        transactionList.add(first);
        second.addNotes("APPROVED");
        currentBalance = currentBalance + second.amount();
        transactionList.add(second);
    }

    public void printStatement() {
        if(freezeAccount == true)
            fixFraud();
        System.out.println(toString());
        System.out.println();
        System.out.println("Transactions for this billing period:");
        for (int i = 0; i < transactionList.size(); i++) {
            System.out.println(transactionList.get(i));
        }
        if (freezeAccount == true){
            System.out.println("** Account frozen due to suspected fraud **");
            freezeAccount = false;
        }
    }

}
