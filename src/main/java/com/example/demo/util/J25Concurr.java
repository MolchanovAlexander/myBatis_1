package com.example.demo.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class J25Concurr {

    Callable<String> taskA = () -> {
        Thread.sleep(1000);
        return "Result of Task1";
    };

    Callable<String> taskB = () -> {
        Thread.sleep(2000);
        return "Result of Task2";
    };

    ExecutorService threads = Executors.newVirtualThreadPerTaskExecutor();
//    var subtaskA = threads.submit(this::taskA);
//    var subtaskA = threads.submit(this::taskB);
//    var result = subtaskA.get() +

}
