/*
 * Decompiled with CFR 0.152.
 */
package org.h2.tools;

import java.sql.SQLException;
import java.util.ArrayList;
import org.h2.store.FileLister;
import org.h2.store.fs.FileUtils;
import org.h2.util.Tool;

public class DeleteDbFiles
extends Tool {
    public static void main(String ... stringArray) throws SQLException {
        new DeleteDbFiles().runTool(stringArray);
    }

    @Override
    public void runTool(String ... stringArray) throws SQLException {
        String string = ".";
        String string2 = null;
        boolean bl = false;
        for (int i = 0; stringArray != null && i < stringArray.length; ++i) {
            String string3 = stringArray[i];
            if (string3.equals("-dir")) {
                string = stringArray[++i];
                continue;
            }
            if (string3.equals("-db")) {
                string2 = stringArray[++i];
                continue;
            }
            if (string3.equals("-quiet")) {
                bl = true;
                continue;
            }
            if (string3.equals("-help") || string3.equals("-?")) {
                this.showUsage();
                return;
            }
            this.showUsageAndThrowUnsupportedOption(string3);
        }
        this.process(string, string2, bl);
    }

    public static void execute(String string, String string2, boolean bl) {
        new DeleteDbFiles().process(string, string2, bl);
    }

    private void process(String string, String string2, boolean bl) {
        ArrayList<String> arrayList = FileLister.getDatabaseFiles(string, string2, true);
        if (arrayList.isEmpty() && !bl) {
            this.printNoDatabaseFilesFound(string, string2);
        }
        for (String string3 : arrayList) {
            DeleteDbFiles.process(string3, bl);
            if (bl) continue;
            this.out.println("Processed: " + string3);
        }
    }

    private static void process(String string, boolean bl) {
        if (FileUtils.isDirectory(string)) {
            FileUtils.tryDelete(string);
        } else if (bl || string.endsWith(".temp.db") || string.endsWith(".trace.db")) {
            FileUtils.tryDelete(string);
        } else {
            FileUtils.delete(string);
        }
    }
}

