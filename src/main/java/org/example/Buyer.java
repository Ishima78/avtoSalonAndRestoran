package org.example;

import static org.example.Main.list;

public class Buyer implements Runnable {
    int people = 10;

    @Override
    public void run() {
        for (int i = 1; i < people; i++) {
            synchronized (list) {
                System.out.println("\n" + "Покупатель " + i + " пришел в магазин");
                if (list.isEmpty()) {
                    System.out.println("Mашин нет ");
                    try {
                        list.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    System.out.println(list.get(0));
                    System.out.println("В НАЛИЧИИ ЕСТЬ МАШИНА ");
                    System.out.println("Покупатель " + i + " купил машину ");

                }
            }
        }
    }
}

