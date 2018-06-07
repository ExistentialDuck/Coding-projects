//Stephen Hanna 109097796
package pkgfinal.project;

public class RewardsCard extends CreditCard {

    protected int currentRewardPoints;

    public RewardsCard(String holder, long number, int expiration, double limit) {

        super(holder, number, expiration, limit);
        this.currentRewardPoints = 0;
    }

    public RewardsCard(String holder, long number, int expiration) {

        super(holder, number, expiration);
        this.creditLimit = 500;
        this.currentRewardPoints = 0;
    }

    public int rewardPoints() {
        return currentRewardPoints;
    }

    public boolean redeemPoints(int points) {
        if (points <= currentRewardPoints) {
            if (currentBalance >= (points / 100)) {
                currentBalance = currentBalance - (points / 100);
                currentRewardPoints = currentRewardPoints - points;
                Transaction t = new Transaction("redemption", "CSEBank", (points / 100));
                transactionList.add(t);
                System.out.println("success");
                return true;
            } else {
                double point = points;
                point = (point / 100 - currentBalance) * 100;
                double currentBalanceHolder = currentBalance;
                currentBalance = 0;
                currentRewardPoints = currentRewardPoints - (int) point;
                Transaction t = new Transaction("redemption", "CSEBank", currentBalanceHolder);
                transactionList.add(t);
                System.out.println("success");
                return true;
            }
        } else {
            return false;
        }
    }

    public String toString() {

        return super.toString() + "\nReward Points Available: " + currentRewardPoints;
    }

    @Override
    public boolean addTransaction(Transaction t) {
        if (freezeAccount == false) {
            if ((t.amount() <= 1.5 && t.amount()>=-1.5 && t.type().equals("debit")) && transactionList.size()>1) {
                Transaction last = transactionList.get(transactionList.size()-1);
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
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
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

}
