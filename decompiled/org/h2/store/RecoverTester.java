/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashSet;
import org.h2.engine.ConnectionInfo;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.store.fs.FileUtils;
import org.h2.store.fs.Recorder;
import org.h2.store.fs.rec.FilePathRec;
import org.h2.tools.Recover;
import org.h2.util.IOUtils;
import org.h2.util.StringUtils;
import org.h2.util.Utils;

public class RecoverTester
implements Recorder {
    private static final RecoverTester instance = new RecoverTester();
    private String testDatabase = "memFS:reopen";
    private int writeCount = Utils.getProperty("h2.recoverTestOffset", 0);
    private int testEvery = Utils.getProperty("h2.recoverTest", 64);
    private final long maxFileSize = (long)Utils.getProperty("h2.recoverTestMaxFileSize", Integer.MAX_VALUE) * 1024L * 1024L;
    private int verifyCount;
    private final HashSet<String> knownErrors = new HashSet();
    private volatile boolean testing;

    public static synchronized void init(String string) {
        if (StringUtils.isNumber(string)) {
            instance.setTestEvery(Integer.parseInt(string));
        }
        FilePathRec.setRecorder(instance);
    }

    @Override
    public void log(int n, String string, byte[] byArray, long l) {
        if (n != 8 && n != 7) {
            return;
        }
        if (!string.endsWith(".mv.db")) {
            return;
        }
        ++this.writeCount;
        if (this.writeCount % this.testEvery != 0) {
            return;
        }
        if (FileUtils.size(string) > this.maxFileSize) {
            return;
        }
        if (this.testing) {
            return;
        }
        this.testing = true;
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new OutputStreamWriter(FileUtils.newOutputStream(string + ".log", true)));
            this.testDatabase(string, printWriter);
        }
        catch (IOException iOException) {
            try {
                throw DbException.convertIOException(iOException, null);
            }
            catch (Throwable throwable) {
                IOUtils.closeSilently(printWriter);
                this.testing = false;
                throw throwable;
            }
        }
        IOUtils.closeSilently(printWriter);
        this.testing = false;
    }

    private synchronized void testDatabase(String string, PrintWriter printWriter) {
        printWriter.println("+ write #" + this.writeCount + " verify #" + this.verifyCount);
        try {
            IOUtils.copyFiles(string, this.testDatabase + ".mv.db");
            ++this.verifyCount;
            ConnectionInfo connectionInfo = new ConnectionInfo("jdbc:h2:" + this.testDatabase + ";FILE_LOCK=NO;TRACE_LEVEL_FILE=0", null, "", "");
            Database database = new Database(connectionInfo, null);
            SessionLocal sessionLocal = database.getSystemSession();
            sessionLocal.prepare("script to '" + this.testDatabase + ".sql'").query(0L);
            sessionLocal.prepare("shutdown immediately").update();
            database.removeSession(null);
            return;
        }
        catch (DbException dbException) {
            SQLException sQLException = DbException.toSQLException(dbException);
            int n = sQLException.getErrorCode();
            if (n == 28000) {
                return;
            }
            if (n == 90049) {
                return;
            }
            dbException.printStackTrace(System.out);
        }
        catch (Exception exception) {
            int n = 0;
            if (exception instanceof SQLException) {
                n = ((SQLException)exception).getErrorCode();
            }
            if (n == 28000) {
                return;
            }
            if (n == 90049) {
                return;
            }
            exception.printStackTrace(System.out);
        }
        printWriter.println("begin ------------------------------ " + this.writeCount);
        try {
            Recover.execute(string.substring(0, string.lastIndexOf(47)), null);
        }
        catch (SQLException sQLException) {
            // empty catch block
        }
        this.testDatabase = this.testDatabase + "X";
        try {
            IOUtils.copyFiles(string, this.testDatabase + ".mv.db");
            ConnectionInfo connectionInfo = new ConnectionInfo("jdbc:h2:" + this.testDatabase + ";FILE_LOCK=NO", null, null, null);
            Database database = new Database(connectionInfo, null);
            database.removeSession(null);
        }
        catch (Exception exception) {
            SQLException sQLException;
            int n = 0;
            if (exception instanceof DbException) {
                sQLException = ((DbException)exception).getSQLException();
                n = sQLException.getErrorCode();
            }
            if (n == 28000) {
                return;
            }
            if (n == 90049) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            StackTraceElement[] stackTraceElementArray = sQLException.getStackTrace();
            for (int i = 0; i < 10 && i < stackTraceElementArray.length; ++i) {
                stringBuilder.append(stackTraceElementArray[i].toString()).append('\n');
            }
            String string2 = stringBuilder.toString();
            if (!this.knownErrors.contains(string2)) {
                printWriter.println(this.writeCount + " code: " + n + " " + sQLException.toString());
                sQLException.printStackTrace(System.out);
                this.knownErrors.add(string2);
            }
            printWriter.println(this.writeCount + " code: " + n);
        }
    }

    public void setTestEvery(int n) {
        this.testEvery = n;
    }
}

