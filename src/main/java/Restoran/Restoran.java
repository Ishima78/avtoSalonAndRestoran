package Restoran;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Restoran {
    public static void main(String[] args) {
        System.out.println("Повар пришел на работу ");
        restore restore = new restore();
        Waiter waiter = new Waiter(restore);
        Consumer consumer = new Consumer(restore);
        new Thread(waiter).start();
        new Thread(consumer).start();


    }
}


class restore {
    private int dish;// Блюдо
    ReentrantLock reentrantLock;// Блокировка
    Condition condition;// Ожидание блюда  сигнал

    restore() {
        reentrantLock = new ReentrantLock();
        condition = reentrantLock.newCondition();
    }

    public void get() {
        reentrantLock.lock();

        try {
            while (dish < 1)
                condition.await();
            Thread.sleep(2000);
            System.out.println("Повар приготовил блюдо ");
            Thread.sleep(1000);
            System.out.println("Официант понес блюдо ");
            dish--;
            Thread.sleep(2000);
            System.out.println("Посетитель все сьел и ушел");
            // сигнализируем
            condition.signalAll();

        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            reentrantLock.unlock();
        }
    }


    public void put() {

        reentrantLock.lock();
        try {
            while (dish >= 3)
                condition.await();

            //
            System.out.println("Официант взял заказ ");
            dish++;
            System.out.println("ЗАКАЗОВ В ОЖИДАНИИ  : " + dish);

            condition.signalAll();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            reentrantLock.unlock();
        }
    }

}

class Waiter implements Runnable {

    restore store;

    Waiter(restore store) {
        this.store = store;
    }

    public void run() {
        for (int i = 1; i < 5; i++) {
            System.out.println("Посетитель " + i + " пришел в ресторан , изучает меню ");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Позвал официанта и сделал заказ ");
            store.put();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Consumer implements Runnable {

    restore store;

    Consumer(restore store) {
        this.store = store;
    }

    public void run() {
        for (int i = 1; i < 5; i++) {
            store.get();
        }
    }
}