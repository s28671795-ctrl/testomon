/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import org.h2.message.DbException;

public class ScriptReader
implements Closeable {
    private final Reader reader;
    private char[] buffer;
    private int bufferPos;
    private int bufferStart = -1;
    private int bufferEnd;
    private boolean endOfFile;
    private boolean insideRemark;
    private boolean blockRemark;
    private boolean skipRemarks;
    private int remarkStart;

    public ScriptReader(Reader reader) {
        this.reader = reader;
        this.buffer = new char[8192];
    }

    @Override
    public void close() {
        try {
            this.reader.close();
        }
        catch (IOException iOException) {
            throw DbException.convertIOException(iOException, null);
        }
    }

    public String readStatement() {
        if (this.endOfFile) {
            return null;
        }
        try {
            return this.readStatementLoop();
        }
        catch (IOException iOException) {
            throw DbException.convertIOException(iOException, null);
        }
    }

    /*
     * Unable to fully structure code
     */
    private String readStatementLoop() throws IOException {
        this.bufferStart = this.bufferPos;
        var1_1 = this.read();
        block7: while (true) {
            if (var1_1 < 0) {
                this.endOfFile = true;
                if (this.bufferPos - 1 != this.bufferStart) break;
                return null;
            }
            if (var1_1 == 59) break;
            switch (var1_1) {
                case 36: {
                    var1_1 = this.read();
                    if (var1_1 != 36 || this.bufferPos - this.bufferStart >= 3 && this.buffer[this.bufferPos - 3] > ' ') continue block7;
                    while ((var1_1 = this.read()) >= 0 && (var1_1 != 36 || (var1_1 = this.read()) >= 0 && var1_1 != 36)) {
                    }
                    var1_1 = this.read();
                    continue block7;
                }
                case 39: {
                    while ((var1_1 = this.read()) >= 0 && var1_1 != 39) {
                    }
                    var1_1 = this.read();
                    continue block7;
                }
                case 34: {
                    while ((var1_1 = this.read()) >= 0 && var1_1 != 34) {
                    }
                    var1_1 = this.read();
                    continue block7;
                }
                case 47: {
                    var1_1 = this.read();
                    if (var1_1 == 42) {
                        this.startRemark(true);
                        var2_2 = 1;
                        while ((var1_1 = this.read()) >= 0) {
                            if (var1_1 == 42) {
                                var1_1 = this.read();
                                if (var1_1 < 0) {
                                    this.clearRemark();
                                    break;
                                }
                                if (var1_1 != 47 || --var2_2 != 0) continue;
                                this.endRemark();
                                break;
                            }
                            if (var1_1 != 47) continue;
                            var1_1 = this.read();
                            if (var1_1 < 0) {
                                this.clearRemark();
                                break;
                            }
                            if (var1_1 != 42) continue;
                            ++var2_2;
                        }
                        var1_1 = this.read();
                        continue block7;
                    }
                    if (var1_1 != 47) continue block7;
                    this.startRemark(false);
                    do {
                        if ((var1_1 = this.read()) >= 0) continue;
                        this.clearRemark();
                        ** GOTO lbl59
                    } while (var1_1 != 13 && var1_1 != 10);
                    this.endRemark();
lbl59:
                    // 2 sources

                    var1_1 = this.read();
                    continue block7;
                }
                case 45: {
                    var1_1 = this.read();
                    if (var1_1 != 45) continue block7;
                    this.startRemark(false);
                    do {
                        if ((var1_1 = this.read()) >= 0) continue;
                        this.clearRemark();
                        ** GOTO lbl71
                    } while (var1_1 != 13 && var1_1 != 10);
                    this.endRemark();
lbl71:
                    // 2 sources

                    var1_1 = this.read();
                    continue block7;
                }
            }
            var1_1 = this.read();
        }
        return new String(this.buffer, this.bufferStart, this.bufferPos - 1 - this.bufferStart);
    }

    private void startRemark(boolean bl) {
        this.blockRemark = bl;
        this.remarkStart = this.bufferPos - 2;
        this.insideRemark = true;
    }

    private void endRemark() {
        this.clearRemark();
        this.insideRemark = false;
    }

    private void clearRemark() {
        if (this.skipRemarks) {
            Arrays.fill(this.buffer, this.remarkStart, this.bufferPos, ' ');
        }
    }

    private int read() throws IOException {
        if (this.bufferPos >= this.bufferEnd) {
            return this.readBuffer();
        }
        return this.buffer[this.bufferPos++];
    }

    private int readBuffer() throws IOException {
        if (this.endOfFile) {
            return -1;
        }
        int n = this.bufferPos - this.bufferStart;
        if (n > 0) {
            char[] cArray = this.buffer;
            if (n + 4096 > cArray.length) {
                if (cArray.length >= 0x3FFFFFFF) {
                    throw new IOException("Error in parsing script, statement size exceeds 1G, first 80 characters of statement looks like: " + new String(this.buffer, this.bufferStart, 80));
                }
                this.buffer = new char[cArray.length * 2];
            }
            System.arraycopy(cArray, this.bufferStart, this.buffer, 0, n);
        }
        this.remarkStart -= this.bufferStart;
        this.bufferStart = 0;
        this.bufferPos = n;
        int n2 = this.reader.read(this.buffer, n, 4096);
        if (n2 == -1) {
            this.bufferEnd = -1024;
            this.endOfFile = true;
            ++this.bufferPos;
            return -1;
        }
        this.bufferEnd = n + n2;
        return this.buffer[this.bufferPos++];
    }

    public boolean isInsideRemark() {
        return this.insideRemark;
    }

    public boolean isBlockRemark() {
        return this.blockRemark;
    }

    public void setSkipRemarks(boolean bl) {
        this.skipRemarks = bl;
    }
}

