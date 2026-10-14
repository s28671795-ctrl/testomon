/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command;

import java.util.ArrayList;
import java.util.Set;
import org.h2.command.CommandInterface;
import org.h2.engine.Database;
import org.h2.engine.DbObject;
import org.h2.engine.SessionLocal;
import org.h2.expression.ParameterInterface;
import org.h2.message.DbException;
import org.h2.message.Trace;
import org.h2.result.ResultInterface;
import org.h2.result.ResultWithGeneratedKeys;
import org.h2.util.Utils;

public abstract class Command
implements CommandInterface {
    protected final SessionLocal session;
    protected long startTimeNanos;
    private final Trace trace;
    private volatile boolean cancel;
    private final String sql;
    private boolean canReuse;

    Command(SessionLocal sessionLocal, String string) {
        this.session = sessionLocal;
        this.sql = string;
        this.trace = this.getDatabase().getTrace(0);
    }

    public abstract boolean isTransactional();

    @Override
    public abstract boolean isQuery();

    @Override
    public abstract ArrayList<? extends ParameterInterface> getParameters();

    public abstract boolean isReadOnly();

    public abstract ResultInterface queryMeta();

    public abstract ResultWithGeneratedKeys update(Object var1);

    public abstract ResultInterface query(long var1);

    @Override
    public final ResultInterface getMetaData() {
        return this.queryMeta();
    }

    void start() {
        if (this.trace.isInfoEnabled() || this.getDatabase().getQueryStatistics()) {
            this.startTimeNanos = Utils.currentNanoTime();
        }
    }

    void setProgress(Database database, int n) {
        database.setProgress(n, this.sql, 0L, 0L);
    }

    protected void checkCanceled() {
        if (this.cancel) {
            this.cancel = false;
            throw DbException.get(57014);
        }
    }

    @Override
    public void stop() {
        long l;
        if (this.session.isOpen()) {
            this.commitIfNonTransactional();
            if (this.isTransactional() && this.session.getAutoCommit()) {
                this.session.commit(false);
            }
        }
        if (this.trace.isInfoEnabled() && this.startTimeNanos != 0L && (l = (System.nanoTime() - this.startTimeNanos) / 1000000L) > 100L) {
            this.trace.info("slow query: {0} ms", l);
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    public ResultInterface executeQuery(long var1_1, boolean var3_2) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
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

    /*
     * Exception decompiling
     */
    @Override
    public ResultWithGeneratedKeys executeUpdate(Object var1_1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [10[CATCHBLOCK]], but top level block is 6[TRYBLOCK]
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

    private void commitIfNonTransactional() {
        if (!this.isTransactional()) {
            boolean bl = this.session.getAutoCommit();
            this.session.commit(true);
            if (!bl && this.session.getAutoCommit()) {
                this.session.begin();
            }
        }
    }

    private long filterConcurrentUpdate(DbException dbException, long l) {
        int n = dbException.getErrorCode();
        if (n != 90131 && n != 90143 && n != 90112) {
            throw dbException;
        }
        long l2 = Utils.currentNanoTime();
        if (l != 0L && l2 - l > (long)this.session.getLockTimeout() * 1000000L) {
            throw DbException.get(50200, dbException, new String[0]);
        }
        return l == 0L ? l2 : l;
    }

    @Override
    public void close() {
        this.canReuse = true;
    }

    @Override
    public void cancel() {
        this.cancel = true;
    }

    public String toString() {
        return this.sql + Trace.formatParams(this.getParameters());
    }

    public boolean isCacheable() {
        return false;
    }

    public boolean canReuse() {
        return this.canReuse;
    }

    public void reuse() {
        this.canReuse = false;
        ArrayList<? extends ParameterInterface> arrayList = this.getParameters();
        for (ParameterInterface parameterInterface : arrayList) {
            parameterInterface.setValue(null, true);
        }
    }

    public void setCanReuse(boolean bl) {
        this.canReuse = bl;
    }

    public abstract Set<DbObject> getDependencies();

    protected abstract boolean isRetryable();

    protected final Database getDatabase() {
        return this.session.getDatabase();
    }
}

