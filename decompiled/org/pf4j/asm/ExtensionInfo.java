/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j.asm;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.objectweb.asm.ClassReader;
import org.pf4j.asm.ExtensionVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ExtensionInfo {
    private static final Logger log = LoggerFactory.getLogger(ExtensionInfo.class);
    private final String className;
    int ordinal = 0;
    List<String> plugins = new ArrayList<String>();
    List<String> points = new ArrayList<String>();

    private ExtensionInfo(String className) {
        this.className = className;
    }

    public String getClassName() {
        return this.className;
    }

    public int getOrdinal() {
        return this.ordinal;
    }

    public List<String> getPlugins() {
        return Collections.unmodifiableList(this.plugins);
    }

    public List<String> getPoints() {
        return Collections.unmodifiableList(this.points);
    }

    public static ExtensionInfo load(String className, ClassLoader classLoader) {
        ExtensionInfo extensionInfo;
        block8: {
            InputStream input = classLoader.getResourceAsStream(className.replace('.', '/') + ".class");
            try {
                ExtensionInfo info = new ExtensionInfo(className);
                new ClassReader(input).accept(new ExtensionVisitor(info), 2);
                extensionInfo = info;
                if (input == null) break block8;
            }
            catch (Throwable throwable) {
                try {
                    if (input != null) {
                        try {
                            input.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (IOException e) {
                    log.error(e.getMessage(), e);
                    return null;
                }
            }
            input.close();
        }
        return extensionInfo;
    }
}

