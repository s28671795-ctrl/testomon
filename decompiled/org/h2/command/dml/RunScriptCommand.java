/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.dml;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.h2.command.CommandContainer;
import org.h2.command.Prepared;
import org.h2.command.dml.ScriptBase;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.result.ResultInterface;
import org.h2.util.ScriptReader;
import org.h2.util.StringUtils;

public class RunScriptCommand
extends ScriptBase {
    private static final char UTF8_BOM = '\ufeff';
    private Charset charset = StandardCharsets.UTF_8;
    private boolean quirksMode;
    private boolean variableBinary;
    private boolean from1X;

    public RunScriptCommand(SessionLocal sessionLocal) {
        super(sessionLocal);
    }

    @Override
    public long update() {
        this.session.getUser().checkAdmin();
        int n = 0;
        boolean bl = this.session.isQuirksMode();
        boolean bl2 = this.session.isVariableBinary();
        try {
            String string;
            this.openInput(this.charset);
            this.reader.mark(1);
            if (this.reader.read() != 65279) {
                this.reader.reset();
            }
            if (this.quirksMode) {
                this.session.setQuirksMode(true);
            }
            if (this.variableBinary) {
                this.session.setVariableBinary(true);
            }
            ScriptReader scriptReader = new ScriptReader(this.reader);
            while ((string = scriptReader.readStatement()) != null) {
                this.execute(string);
                if ((++n & 0x7F) != 0) continue;
                this.checkCanceled();
            }
            scriptReader.close();
        }
        catch (IOException iOException) {
            throw DbException.convertIOException(iOException, null);
        }
        finally {
            if (this.quirksMode) {
                this.session.setQuirksMode(bl);
            }
            if (this.variableBinary) {
                this.session.setVariableBinary(bl2);
            }
            this.closeIO();
        }
        return n;
    }

    private void execute(String string) {
        if (this.from1X) {
            int n;
            if ((string = string.trim()).startsWith("--")) {
                char c;
                n = 2;
                int n2 = string.length();
                do {
                    if (n < n2) continue;
                    return;
                } while ((c = string.charAt(n++)) != '\n' && c != '\r');
                string = StringUtils.trimSubstring(string, n);
            }
            if (string.startsWith("INSERT INTO SYSTEM_LOB_STREAM VALUES(") && (n = string.indexOf(", NULL, '")) >= 0) {
                string = new StringBuilder(string.length() + 1).append(string, 0, n + 8).append("X'").append(string, n + 9, string.length()).toString();
            }
        }
        try {
            Prepared prepared = this.session.prepare(string);
            CommandContainer commandContainer = new CommandContainer(this.session, string, prepared);
            if (commandContainer.isQuery()) {
                commandContainer.executeQuery(0L, false);
            } else {
                commandContainer.executeUpdate(null);
            }
        }
        catch (DbException dbException) {
            throw dbException.addSQL(string);
        }
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public void setQuirksMode(boolean bl) {
        this.quirksMode = bl;
    }

    public void setVariableBinary(boolean bl) {
        this.variableBinary = bl;
    }

    public void setFrom1X() {
        this.from1X = true;
        this.quirksMode = true;
        this.variableBinary = true;
    }

    @Override
    public ResultInterface queryMeta() {
        return null;
    }

    @Override
    public int getType() {
        return 64;
    }
}

