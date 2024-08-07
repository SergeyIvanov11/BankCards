package org.example;

import java.util.concurrent.atomic.AtomicLong;

public abstract class BankCard {
    protected String owner;
    protected AtomicLong balance;

    //«Оплатить»
    public boolean pay(long amount) {
        if (amount > 0) {
            balance.addAndGet(-amount);
            return true;
        } else return false;
    }

    //«Пополнить»
    public boolean add(long amount) {
        if (amount > 0) {
            balance.addAndGet(amount);
            return true;
        } else return false;
    }

    //"Перевести сумму"
    public boolean transfer(BankCard bankCard, long amount) {
        if (amount > 0) {
            if (this.pay(amount)) {
                if (!bankCard.add(amount)) {
                    this.add(amount);
                }
                return true;
            }
        }
        return false;
    }

    //«Получить информацию о балансе»
    public long getBalance() {
        return balance.get();
    }

    //«Получить информацию о доступных средствах»
    public String getInfo() {
        return  "Владелец карты: " + this.owner + ", собственные средства на счете: " + this.getBalance();
    }
}
