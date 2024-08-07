package org.example;

import java.util.concurrent.atomic.AtomicLong;

// кредитная карта с бонусами в виде кэшбека и бонусных баллов
public class CreditCardPlus extends CreditCard {
    private AtomicLong bonuses; //количество бонусов
    private final int CASHBACK_INDEX = 5; //процент кэшбека
    private final int CASHBACK_LIMIT = 5000; //лимит покупки для начисления кэшбека
    private final int BONUS_LIMIT = 500; //лимит покупки для начисления бонусов
    private final int BONUS_INDEX = 1; // процент для начисления бонусов

    public CreditCardPlus(String owner, long balance, long limit) {
        super(owner, balance, limit);
        bonuses = new AtomicLong(0);
    }

    //новый метод для начисления кэшбека и бонусов, просто метод pay в этом случае используется для transfer
    public boolean payWithBonuses(long amount) {
        boolean b = false;
        if (amount > 0 && creditBalance.get() > 0) {
            if (balance.get() >= amount) {
                if (amount >= BONUS_LIMIT) {
                    bonuses.addAndGet(amount * BONUS_INDEX / 100);
                }
                if (amount >= CASHBACK_LIMIT) {
                    amount = amount * (100 - CASHBACK_INDEX) / 100;
                }
                balance.addAndGet(-amount);
                b = true;
            } else {
                long delta = amount - balance.get();
                if (delta <= creditBalance.get()) {
                    if (amount >= BONUS_LIMIT) {
                        bonuses.addAndGet(amount * BONUS_INDEX / 100);
                    }
                    balance.set(0);
                    creditBalance.addAndGet(-delta);
                    b = true;
                }
            }
        }
        return b;
    }

    //permission - это разрешение от пользователя использовать бонусы для оплаты
    public boolean pay(long amount, boolean permission) {
        boolean b = false;
        if (permission && bonuses.get() > 0 && amount > 0) {
            if (bonuses.get() >= amount) {
                bonuses.addAndGet(-amount);
                b = true;
            } else {
                long delta = amount - bonuses.get();
                if (this.payWithBonuses(delta)) {
                    bonuses.set(0);
                    b = true;
                }
            }
        } else b = this.payWithBonuses(amount);
        return b;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + ",\n"
                + "количество бонусов на карте: " + bonuses.get() + ",\n"
                + "процент кэшбека составляет: " + CASHBACK_INDEX + "% при покупках от " + CASHBACK_LIMIT + "\n";
    }
}
