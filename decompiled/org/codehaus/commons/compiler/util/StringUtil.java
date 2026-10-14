/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util;

import java.io.File;
import java.util.ArrayList;
import org.codehaus.commons.nullanalysis.Nullable;

public final class StringUtil {
    private StringUtil() {
    }

    public static File[] parsePath(String s2) {
        int from = 0;
        ArrayList<File> l = new ArrayList<File>();
        while (true) {
            int to;
            if ((to = s2.indexOf(File.pathSeparatorChar, from)) == -1) {
                if (from == s2.length()) break;
                l.add(new File(s2.substring(from)));
                break;
            }
            if (to != from) {
                l.add(new File(s2.substring(from, to)));
            }
            from = to + 1;
        }
        return l.toArray(new File[l.size()]);
    }

    @Nullable
    public static File[] parseOptionalPath(@Nullable String s2) {
        return s2 == null ? null : StringUtil.parsePath(s2);
    }
}

