package com.example.demo.util;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class CacheUtil {
    private static CacheUtil cache = new CacheUtil();
    private Map<Key, SoftReference<Value>> map = Collections.synchronizedMap(new HashMap<>());
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread th = new Thread("My.SomeThread.CacheUtil");
            th.setDaemon(true);
            return th;
        }
    });

    private CacheUtil() {
        this.scheduler.scheduleAtFixedRate(new Runnable() {
            public void run() {
                List<Key> badskey = new ArrayList<>();
                synchronized (CacheUtil.this.map) {
                    for (Map.Entry<Key, SoftReference<Value>> en : CacheUtil.this.map.entrySet()) {
                        Key k = (Key) en.getKey();
                        if (en.getValue() == null || ((SoftReference) en.getValue()).get() == null || !((Value) ((SoftReference) en.getValue()).get()).isLive()) {
                            badskey.add(k);
                        }
                    }
                }

                for (Key k : badskey) {
                    CacheUtil.this.map.remove(k);
                }
            }
        }, 1L, 1L, TimeUnit.MINUTES);


    }

    public static CacheUtil getInstance() {
        return cache;
    }
    public <K, V> void put(String type, K key, V data) {
        this.put(type, key, data, TimeUnit.HOURS.toMillis(1L));
    }
     public <K, V> void put(String type, K key, V data, long timeout) {
        // todo dfsgdfg
        this.put(type, key, data, TimeUnit.HOURS.toMillis(1L));
    }
    private static class Key {
        private final String type;
        private final Object key;

        public Key(String type, Object key) {
            this.type = type;
            this.key = key;
        }

        public String getType() {
            return type;
        }

        public Object getKey() {
            return key;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            } else if (this.getClass() != obj.getClass()) {
                return false;
            } else {
                Key other = (Key) obj;
                if (this.type == null) {
                    if (other.type != null) {
                        return false;
                    }
                } else if (!this.type.equals(other.type)) {
                    return false;
                }

                if (this.key == other.key || this.key != null && this.key.equals(other.key)) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 31 * hash + (this.type != null ? this.type.hashCode() : 0);
            hash = 31 * hash + (this.key != null ? this.key.hashCode() : 0);
            return hash;
        }
    }

    private static class Value {
        private final Object data;
        private final long timelife;

        public Value(Object data) {
            this.data = data;
            this.timelife = System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1L);
        }

        public Value(Object data, long timelife) {
            this.data = data;
            this.timelife = System.currentTimeMillis() + timelife;
        }

        public Object getData() {
            return data;
        }

        public boolean isLive() {
            return System.currentTimeMillis() < this.timelife;
        }



    }
}
