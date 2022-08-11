package org.example;

import static org.example.Main.list;

public class Avto implements Runnable {
    int valueAvto = 1;
    int normOfCars = 7;

    @Override
    public void run() {
        for (int i = 0; i < normOfCars; i++) {

            synchronized (list) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                list.add("Производитель тайота выпустил " + valueAvto + " машин");
                list.notify();
            }
        }
    }
}
