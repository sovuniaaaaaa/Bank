package com.example.bank;

public class Main{
    public static void main(String[] args){
        Account account = new Account();
        Client client = new Client(account);
        Output  output  = new Output (account);
        new Thread(client).start();
        new Thread(output).start();
    }
}
class Account extends Thread{
    private int balance = 0;
    private final int output = 500;

    public synchronized void put(){
        while(balance >= output){
            try{
                wait();// Ожидает когда клиент пополнит баланс
            }
            catch (Exception ex){
            }
        }
        balance += 100;
        System.out.println("Баланс был пополнен на 100.");
        System.out.println("Баланс сейчас " + balance);
        notify(); // Сообщает о том, что мы смогли накопить до 500
    }
    public synchronized void get(){
        while(balance < output){
            try {
                wait(); // Ожидает когда клиент пополнит баланс
            }
            catch (Exception ex){
            }
        }
        balance -= 500;
        System.out.println("С карты было списано 500");
        System.out.println("Баланс сейчас " + balance);
        notify();
    }
}
class Client extends Thread{
    Account account;
    Client(Account account){
        this.account=account;
    }
    public void run(){
        for (int i = 1; i < 10; i++) {
            account.get();
        }
    }
}
class Output  extends Thread {
    Account account;
    Output (Account account) {
        this.account = account;
    }
    public void run() {
        for (int i = 1; i < 10; i++) {
            account.put();
        }
    }
}