/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jooq.tools.JooqLogger;

final class CLIUtil {
    CLIUtil() {
    }

    static void main(String url2, Runnable runnable2) {
        JooqLogger.initSimpleFormatter();
        try {
            runnable2.run();
        }
        catch (NoClassDefFoundError e) {
            throw new RuntimeException("A class definition could not be found when running the CLI utility.\n\nThis is mostly due to a missing dependency. Make sure you have added the right dependencies\nas according to the manual for {url}\n".replace("{url}", url2.replace("/latest/", "/3.18/")), e);
        }
    }
}

