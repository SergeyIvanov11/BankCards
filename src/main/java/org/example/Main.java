package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        DebitCardPlus debitCardPeti = new DebitCardPlus("Петр", 10000);
        CreditCardPlus creditCardNatashi = new CreditCardPlus("Наталья", 0, 30000);

        debitCardPeti.payWithBonuses(8000);
        creditCardNatashi.payWithBonuses(2000);

        System.out.println(debitCardPeti.getInfo());
        System.out.println(creditCardNatashi.getInfo());

        creditCardNatashi.transfer(debitCardPeti, 10000);

        System.out.println(debitCardPeti.getInfo());
        System.out.println(creditCardNatashi.getInfo());
    }
}