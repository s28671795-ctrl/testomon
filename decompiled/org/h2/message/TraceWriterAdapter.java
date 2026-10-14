/*
 * Decompiled with CFR 0.152.
 */
package org.h2.message;

import org.h2.message.Trace;
import org.h2.message.TraceWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TraceWriterAdapter
implements TraceWriter {
    private String name;
    private final Logger logger = LoggerFactory.getLogger("h2database");

    @Override
    public void setName(String string) {
        this.name = string;
    }

    @Override
    public boolean isEnabled(int n) {
        switch (n) {
            case 3: {
                return this.logger.isDebugEnabled();
            }
            case 2: {
                return this.logger.isInfoEnabled();
            }
            case 1: {
                return this.logger.isErrorEnabled();
            }
        }
        return false;
    }

    @Override
    public void write(int n, int n2, String string, Throwable throwable) {
        this.write(n, Trace.MODULE_NAMES[n2], string, throwable);
    }

    @Override
    public void write(int n, String string, String string2, Throwable throwable) {
        if (this.isEnabled(n)) {
            string2 = this.name != null ? this.name + ":" + string + " " + string2 : string + " " + string2;
            switch (n) {
                case 3: {
                    this.logger.debug(string2, throwable);
                    break;
                }
                case 2: {
                    this.logger.info(string2, throwable);
                    break;
                }
                case 1: {
                    this.logger.error(string2, throwable);
                    break;
                }
            }
        }
    }
}

