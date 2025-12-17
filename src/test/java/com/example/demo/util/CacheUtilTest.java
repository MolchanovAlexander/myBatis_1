package com.example.demo.util;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;

class CacheUtilTest {
    CacheUtil cache = CacheUtil.getInstance();

    @Test
    public void testPutGet() {
        CacheUtil.KeyObject key1 = new CacheUtil.KeyObject("test", "t1");
        cache.put("test1", key1, "test1");
        CacheUtil.KeyObject key2 = new CacheUtil.KeyObject("test", "t1");
        String val = cache.get("test1", key2);
        assertEquals("test1", val);
    }

    @Test
    public void testPutGetMulti() throws InterruptedException {
        final int tasks = 1000;
        ExecutorService service = Executors.newFixedThreadPool(300);
        final CountDownLatch cdl = new CountDownLatch(tasks);
        final List<Exception> exlist = Collections.synchronizedList(new ArrayList<>());
        final Random rnd = new Random();
        final String[] keys = new String[tasks];

        for (int i = 0; i < tasks; i++) {
            keys[i] = String.valueOf(rnd.nextInt());
        }
        long time = System.currentTimeMillis();

        for (int i = 0; i < tasks; i++) {
            // todo runnable
            CacheUtil.KeyObject key1 = new CacheUtil.KeyObject("test", "t1");
            cache.put("test1", key1, "test1");
            CacheUtil.KeyObject key2 = new CacheUtil.KeyObject("test", "t1");
            String val = cache.get("test1", key2);
            ////////assertEquals("test1", val);
        }
        cdl.await();
        System.out.println("timeout: " + (System.currentTimeMillis() - time));
        service.shutdown();
        assertEquals(new ArrayList<Exception>(), exlist);



    }
}