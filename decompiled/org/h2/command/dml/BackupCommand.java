/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.dml;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.zip.ZipOutputStream;
import org.h2.command.Prepared;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.message.DbException;
import org.h2.mvstore.db.Store;
import org.h2.result.ResultInterface;
import org.h2.store.FileLister;
import org.h2.store.fs.FileUtils;

public class BackupCommand
extends Prepared {
    private Expression fileNameExpr;

    public BackupCommand(SessionLocal sessionLocal) {
        super(sessionLocal);
    }

    public void setFileName(Expression expression) {
        this.fileNameExpr = expression;
    }

    @Override
    public long update() {
        String string = this.fileNameExpr.getValue(this.session).getString();
        this.session.getUser().checkAdmin();
        this.backupTo(string);
        return 0L;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void backupTo(String string) {
        Database database = this.getDatabase();
        if (!database.isPersistent()) {
            throw DbException.get(90126);
        }
        try {
            Store store = database.getStore();
            store.flush();
            String string2 = database.getName();
            string2 = FileUtils.getName(string2);
            try (OutputStream outputStream2 = FileUtils.newOutputStream(string, false);){
                ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream2);
                database.flush();
                Object object = database.getLobSyncObject();
                synchronized (object) {
                    String string3 = database.getDatabasePath();
                    String string4 = FileUtils.getParent(string3);
                    string4 = FileLister.getDir(string4);
                    ArrayList<String> arrayList = FileLister.getDatabaseFiles(string4, string2, true);
                    for (String string5 : arrayList) {
                        if (!string5.endsWith(".mv.db")) continue;
                        store.getMvStore().getFileStore().backup(zipOutputStream);
                    }
                }
                zipOutputStream.close();
            }
        }
        catch (IOException iOException) {
            throw DbException.convertIOException(iOException, string);
        }
    }

    @Override
    public boolean isTransactional() {
        return true;
    }

    public static String correctFileName(String string) {
        if ((string = string.replace('\\', '/')).startsWith("/")) {
            string = string.substring(1);
        }
        return string;
    }

    @Override
    public boolean needRecompile() {
        return false;
    }

    @Override
    public ResultInterface queryMeta() {
        return null;
    }

    @Override
    public int getType() {
        return 56;
    }
}

