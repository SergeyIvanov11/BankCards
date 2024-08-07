package org.example;

import java.util.concurrent.atomic.AtomicLong;

public class CreditCard extends BankCard {
    protected long limit; //«Кредитный лимит»
    protected AtomicLong creditBalance; //Кредитные средства

    public CreditCard(String owner, long balance, long limit) {
        this.owner = owner;
        if (balance >= 0) {
            this.balance = new AtomicLong(balance);
        } else this.balance = new AtomicLong(0);
        if (limit >= 0) {
            this.limit = limit;
        } else {
            this.limit = 0;
        }
        this.creditBalance = new AtomicLong(limit);
    }

    @Override
    public boolean add(long amount) {
        if (amount > 0) {
            if (creditBalance.get() < limit) {
                long debt = limit - creditBalance.get();
                if (debt < amount) {
                    creditBalance.addAndGet(debt);
                    balance.addAndGet(amount - debt);
                } else creditBalance.addAndGet(amount);
            } else balance.addAndGet(amount);
            return true;
        } else return false;
    }

    @Override
    public boolean pay(long amount) {
        boolean b = false;
        if (amount > 0 && creditBalance.get() > 0) {
            if (balance.get() >= amount) {
                balance.addAndGet(-amount);
                b = true;
            } else {
                long delta = amount - balance.get();
                if (delta <= creditBalance.get()) {
                    balance.set(0);
                    creditBalance.addAndGet(-delta);
                    b = true;
                }
            }
        }
        return b;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + ",\n"
                + "кредитные средства в настоящий момент составляют " + creditBalance.get() + ",\n"
                + "при лимите карты в " + limit;
    }
}
