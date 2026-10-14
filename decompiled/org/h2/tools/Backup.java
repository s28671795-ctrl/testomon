/*
 * Decompiled with CFR 0.152.
 */
package org.h2.tools;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.h2.command.dml.BackupCommand;
import org.h2.message.DbException;
import org.h2.store.FileLister;
import org.h2.store.fs.FileUtils;
import org.h2.util.IOUtils;
import org.h2.util.Tool;

public class Backup
extends Tool {
    public static void main(String ... stringArray) throws SQLException {
        new Backup().runTool(stringArray);
    }

    @Override
    public void runTool(String ... stringArray) throws SQLException {
        String string = "backup.zip";
        String string2 = ".";
        String string3 = null;
        boolean bl = false;
        for (int i = 0; stringArray != null && i < stringArray.length; ++i) {
            String string4 = stringArray[i];
            if (string4.equals("-dir")) {
                string2 = stringArray[++i];
                continue;
            }
            if (string4.equals("-db")) {
                string3 = stringArray[++i];
                continue;
            }
            if (string4.equals("-quiet")) {
                bl = true;
                continue;
            }
            if (string4.equals("-file")) {
                string = stringArray[++i];
                continue;
            }
            if (string4.equals("-help") || string4.equals("-?")) {
                this.showUsage();
                return;
            }
            this.showUsageAndThrowUnsupportedOption(string4);
        }
        try {
            this.process(string, string2, string3, bl);
        }
        catch (Exception exception) {
            throw DbException.toSQLException(exception);
        }
    }

    public static void execute(String string, String string2, String string3, boolean bl) throws SQLException {
        try {
            new Backup().process(string, string2, string3, bl);
        }
        catch (Exception exception) {
            throw DbException.toSQLException(exception);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void process(String string, String string2, String string3, boolean bl) throws SQLException {
        boolean bl2 = string3 != null && string3.isEmpty();
        List<String> list = bl2 ? FileUtils.newDirectoryStream(string2) : FileLister.getDatabaseFiles(string2, string3, true);
        if (list.isEmpty()) {
            if (!bl) {
                this.printNoDatabaseFilesFound(string2, string3);
            }
            return;
        }
        if (!bl) {
            FileLister.tryUnlockDatabase(list, "backup");
        }
        string = FileUtils.toRealPath(string);
        FileUtils.delete(string);
        OutputStream outputStream2 = null;
        try {
            outputStream2 = FileUtils.newOutputStream(string, false);
            try (ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream2);){
                String string4 = "";
                for (String string5 : list) {
                    if (!bl2 && !string5.endsWith(".mv.db")) continue;
                    string4 = FileUtils.getParent(string5);
                    break;
                }
                for (String string5 : list) {
                    String string6 = FileUtils.toRealPath(string5);
                    if (!string6.startsWith(string4)) {
                        throw DbException.getInternalError(string6 + " does not start with " + string4);
                    }
                    if (string6.endsWith(string) || FileUtils.isDirectory(string5)) continue;
                    string6 = string6.substring(string4.length());
                    string6 = BackupCommand.correctFileName(string6);
                    ZipEntry zipEntry = new ZipEntry(string6);
                    zipOutputStream.putNextEntry(zipEntry);
                    InputStream inputStream2 = null;
                    try {
                        inputStream2 = FileUtils.newInputStream(string5);
                        IOUtils.copyAndCloseInput(inputStream2, zipOutputStream);
                    }
                    catch (FileNotFoundException fileNotFoundException) {
                    }
                    finally {
                        IOUtils.closeSilently(inputStream2);
                    }
                    zipOutputStream.closeEntry();
                    if (bl) continue;
                    this.out.println("Processed: " + string5);
                }
            }
        }
        catch (IOException iOException) {
            throw DbException.convertIOException(iOException, string);
        }
        finally {
            IOUtils.closeSilently(outputStream2);
        }
    }
}

