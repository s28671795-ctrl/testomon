/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.thread;

import org.apache.activemq.thread.Task;
import org.apache.activemq.thread.TaskRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DedicatedTaskRunner
implements TaskRunner {
    private static final Logger LOG = LoggerFactory.getLogger(DedicatedTaskRunner.class);
    private final Task task;
    private final Thread thread;
    private final Object mutex = new Object();
    private boolean threadTerminated;
    private boolean pending;
    private boolean shutdown;

    public DedicatedTaskRunner(final Task task, String name, int priority, boolean daemon) {
        this.task = task;
        this.thread = new Thread(name){

            @Override
            public void run() {
                try {
                    DedicatedTaskRunner.this.runTask();
                }
                finally {
                    LOG.trace("Run task done: {}", (Object)task);
                }
            }
        };
        this.thread.setDaemon(daemon);
        this.thread.setName(name);
        this.thread.setPriority(priority);
        this.thread.start();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void wakeup() throws InterruptedException {
        Object object = this.mutex;
        synchronized (object) {
            if (this.shutdown) {
                return;
            }
            this.pending = true;
            this.mutex.notifyAll();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void shutdown(long timeout2) throws InterruptedException {
        LOG.trace("Shutdown timeout: {} task: {}", (Object)timeout2, (Object)this.task);
        Object object = this.mutex;
        synchronized (object) {
            this.shutdown = true;
            this.pending = true;
            this.mutex.notifyAll();
            if (Thread.currentThread() != this.thread && !this.threadTerminated) {
                this.mutex.wait(timeout2);
            }
        }
    }

    @Override
    public void shutdown() throws InterruptedException {
        this.shutdown(0L);
    }

    /*
     * Exception decompiling
     */
    final void runTask() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [27[UNCONDITIONALDOLOOP]], but top level block is 8[TRYBLOCK]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }
}

