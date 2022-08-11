package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static List<String> list = new ArrayList<>();

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(5);
        Avto avto = new Avto();
        Buyer buyer = new Buyer();

        es.execute(avto);
        es.execute(buyer);

        es.shutdown();

    }

}