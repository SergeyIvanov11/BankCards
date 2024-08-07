package org.example;

import java.util.concurrent.atomic.AtomicLong;

public class DebitCard extends BankCard {
    public DebitCard(String owner, long balance) {
        this.owner = owner;
        if (balance >= 0) {
            this.balance = new AtomicLong(balance);
        } else this.balance = new AtomicLong(0);
    }

    @Override
    public boolean pay(long amount) {
        if (balance.get() > 0 && balance.get() >= amount && amount > 0) {
            balance.addAndGet(-amount);
            return true;
        } else return false;
    }
}
