/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.dml;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import org.h2.command.Prepared;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.engine.SysProperties;
import org.h2.expression.Expression;
import org.h2.message.DbException;
import org.h2.security.SHA256;
import org.h2.store.FileStore;
import org.h2.store.FileStoreInputStream;
import org.h2.store.FileStoreOutputStream;
import org.h2.store.fs.FileUtils;
import org.h2.tools.CompressTool;
import org.h2.util.IOUtils;
import org.h2.util.StringUtils;

abstract class ScriptBase
extends Prepared {
    private static final String SCRIPT_SQL = "script.sql";
    protected OutputStream out;
    protected BufferedReader reader;
    private Expression fileNameExpr;
    private Expression password;
    private String fileName;
    private String cipher;
    private FileStore store;
    private String compressionAlgorithm;

    ScriptBase(SessionLocal sessionLocal) {
        super(sessionLocal);
    }

    public void setCipher(String string) {
        this.cipher = string;
    }

    private boolean isEncrypted() {
        return this.cipher != null;
    }

    public void setPassword(Expression expression) {
        this.password = expression;
    }

    public void setFileNameExpr(Expression expression) {
        this.fileNameExpr = expression;
    }

    protected String getFileName() {
        if (this.fileNameExpr != null && this.fileName == null) {
            this.fileName = this.fileNameExpr.optimize(this.session).getValue(this.session).getString();
            if (this.fileName == null || StringUtils.isWhitespaceOrEmpty(this.fileName)) {
                this.fileName = SCRIPT_SQL;
            }
            this.fileName = SysProperties.getScriptDirectory() + this.fileName;
        }
        return this.fileName;
    }

    @Override
    public boolean isTransactional() {
        return false;
    }

    void deleteStore() {
        String string = this.getFileName();
        if (string != null && FileUtils.isRegularFile(string)) {
            FileUtils.delete(string);
        }
    }

    private void initStore() {
        Object object;
        Database database = this.getDatabase();
        byte[] byArray = null;
        if (this.cipher != null && this.password != null) {
            object = this.password.optimize(this.session).getValue(this.session).getString().toCharArray();
            byArray = SHA256.getKeyPasswordHash("script", (char[])object);
        }
        object = this.getFileName();
        this.store = FileStore.open(database, (String)object, "rw", this.cipher, byArray);
        this.store.setCheckedWriting(false);
        this.store.init();
    }

    void openOutput() {
        String string = this.getFileName();
        if (string == null) {
            return;
        }
        if (this.isEncrypted()) {
            this.initStore();
            this.out = new FileStoreOutputStream(this.store, this.compressionAlgorithm);
            this.out = new BufferedOutputStream(this.out, 131072);
        } else {
            OutputStream outputStream2;
            try {
                outputStream2 = FileUtils.newOutputStream(string, false);
            }
            catch (IOException iOException) {
                throw DbException.convertIOException(iOException, null);
            }
            this.out = new BufferedOutputStream(outputStream2, 4096);
            this.out = CompressTool.wrapOutputStream(this.out, this.compressionAlgorithm, SCRIPT_SQL);
        }
    }

    void openInput(Charset charset) {
        InputStream inputStream2;
        String string = this.getFileName();
        if (string == null) {
            return;
        }
        if (this.isEncrypted()) {
            this.initStore();
            inputStream2 = new FileStoreInputStream(this.store, this.compressionAlgorithm != null, false);
        } else {
            try {
                inputStream2 = FileUtils.newInputStream(string);
            }
            catch (IOException iOException) {
                throw DbException.convertIOException(iOException, string);
            }
            inputStream2 = CompressTool.wrapInputStream(inputStream2, this.compressionAlgorithm, SCRIPT_SQL);
            if (inputStream2 == null) {
                throw DbException.get(90124, "script.sql in " + string);
            }
        }
        this.reader = new BufferedReader(new InputStreamReader(inputStream2, charset), 4096);
    }

    void closeIO() {
        IOUtils.closeSilently(this.out);
        this.out = null;
        IOUtils.closeSilently(this.reader);
        this.reader = null;
        if (this.store != null) {
            this.store.closeSilently();
            this.store = null;
        }
    }

    @Override
    public boolean needRecompile() {
        return false;
    }

    public void setCompressionAlgorithm(String string) {
        this.compressionAlgorithm = string;
    }
}

