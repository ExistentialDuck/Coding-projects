//Stephen Hanna 109097796
package pkgfinal.project;

public class CreditCard extends BankCard {

    private int expirationDate;
    protected double creditLimit;

    public CreditCard(String cardHolder, long cardNumber, int expiration, double limit) {
        super(cardHolder, cardNumber);
        this.expirationDate = expiration;
        this.creditLimit = limit;
    }

    public CreditCard(String cardHolder, long cardNumber, int expiration) {
        super(cardHolder, cardNumber);
        this.expirationDate = expiration;
        this.creditLimit = 500;
    }

    public double limit() {
        return creditLimit;
    }

    public double availableCredit() {
        return creditLimit - currentBalance;
    }

    public int expiration() {
        return expirationDate;
    }

    @Override
    public String toString() {
        return super.toString() + "\nExpiration Date: " + expirationDate;
    }

    @Override
    public boolean addTransaction(Transaction t) {
        if (freezeAccount == false) {
            if ((t.amount() <= 1.5 && t.amount()>=-1.5 && t.type().equals("debit")) && transactionList.size()>1) {
                Transaction last = transactionList.get((transactionList.size()-1));
                if (last.amount()>=-1.5 && last.amount() <= 1.5 && last.type().equals("debit")) {
                    if (t.merchant().equals(last.merchant())) {
                        freezeAccount = true;
                        return false;
                    } else {
                        if (t.type().equals("debit") && t.amount() <= availableCredit()) {
                            t.addNotes("APPROVED");
                            currentBalance = currentBalance + t.amount();
                            transactionList.add(t);
                            return true;
                        } else if (t.type().equals("debit") && t.amount() > availableCredit()) {
                            t.addNotes("DENIED");
                            return false;
                        } else if (t.type().equals("credit")) {
                            t.addNotes("APPROVED");
                            currentBalance = currentBalance + t.amount();
                            transactionList.add(t);
                            return true;
                        } else if (t.type().equals("fee")) {
                            t.addNotes("APPROVED");
                            currentBalance = currentBalance + t.amount();
                            transactionList.add(t);
                            return true;
                        } else if (t.type().equals("advance")) {
                            t.addNotes("APPROVED");
                            currentBalance = currentBalance + t.amount();
                            transactionList.add(t);
                            return true;
                        } else {
                            return false;
                        }
                    }
                } else {
                    if (t.type().equals("debit") && t.amount() <= availableCredit()) {
                        t.addNotes("APPROVED");
                        currentBalance = currentBalance + t.amount();
                        transactionList.add(t);
                        return true;
                    } else if (t.type().equals("debit") && t.amount() > availableCredit()) {
                        t.addNotes("DENIED");
                        return false;
                    } else if (t.type().equals("credit")) {
                        t.addNotes("APPROVED");
                        currentBalance = currentBalance + t.amount();
                        transactionList.add(t);
                        return true;
                    } else if (t.type().equals("fee")) {
                        t.addNotes("APPROVED");
                        currentBalance = currentBalance + t.amount();
                        transactionList.add(t);
                        return true;
                    } else if (t.type().equals("advance")) {
                        t.addNotes("APPROVED");
                        currentBalance = currentBalance + t.amount();
                        transactionList.add(t);
                        return true;
                    } else {
                        return false;
                    }
                }
            } else {
                if (t.type().equals("debit") && t.amount() <= availableCredit()) {
                    t.addNotes("APPROVED");
                    currentBalance = currentBalance + t.amount();
                    transactionList.add(t);
                    return true;
                } else if (t.type().equals("debit") && t.amount() > availableCredit()) {
                    t.addNotes("DENIED");
                    return false;
                } else if (t.type().equals("credit")) {
                    t.addNotes("APPROVED");
                    currentBalance = currentBalance + t.amount();
                    transactionList.add(t);
                    return true;
                } else if (t.type().equals("fee")) {
                    t.addNotes("APPROVED");
                    currentBalance = currentBalance + t.amount();
                    transactionList.add(t);
                    return true;
                } else if (t.type().equals("advance")) {
                    t.addNotes("APPROVED");
                    currentBalance = currentBalance + t.amount();
                    transactionList.add(t);
                    return true;
                } else {
                    return false;
                }
            }
        }
        else
        return false;
    }

    @Override
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
    public void fixFraud() {
        Transaction last = transactionList.get(transactionList.size()-1);
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

    public boolean getCashAdvance(double cashRequested) {
        double serviceFee = .05 * cashRequested;
        double totalCost = serviceFee + cashRequested;
        if (totalCost <= availableCredit()) {
            Transaction t = new Transaction("advance", "CSEBank", cashRequested);
            addTransaction(t);
            Transaction a = new Transaction("fee", "Cash advance fee", serviceFee);
            addTransaction(a);
            return true;
        } else {
            return false;
        }
    }

}
