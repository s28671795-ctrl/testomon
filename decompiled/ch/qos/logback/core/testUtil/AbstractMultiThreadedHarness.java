/*
 * Decompiled with CFR 0.152.
 */
package ch.qos.logback.core.testUtil;

import ch.qos.logback.core.testUtil.RunnableWithCounterAndDone;

public abstract class AbstractMultiThreadedHarness {
    public abstract void waitUntilEndCondition() throws InterruptedException;

    public void execute(RunnableWithCounterAndDone[] runnableArray) throws InterruptedException {
        Thread[] threadArray = new Thread[runnableArray.length];
        for (int i = 0; i < runnableArray.length; ++i) {
            System.out.println("Starting " + String.valueOf(runnableArray[i]));
            threadArray[i] = new Thread(runnableArray[i], "Harness[" + i + "]");
        }
        for (Thread thread2 : threadArray) {
            thread2.start();
        }
        this.waitUntilEndCondition();
        for (Runnable runnable2 : runnableArray) {
            ((RunnableWithCounterAndDone)runnable2).setDone(true);
        }
        for (Runnable runnable3 : threadArray) {
            ((Thread)runnable3).join();
        }
    }
}

