/*
 * Decompiled with CFR 0.152.
 */
package org.h2.tools;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.h2.message.DbException;
import org.h2.store.fs.FileUtils;
import org.h2.util.IOUtils;
import org.h2.util.Tool;

public class Restore
extends Tool {
    public static void main(String ... stringArray) throws SQLException {
        new Restore().runTool(stringArray);
    }

    @Override
    public void runTool(String ... stringArray) throws SQLException {
        String string = "backup.zip";
        String string2 = ".";
        String string3 = null;
        for (int i = 0; stringArray != null && i < stringArray.length; ++i) {
            String string4 = stringArray[i];
            if (string4.equals("-dir")) {
                string2 = stringArray[++i];
                continue;
            }
            if (string4.equals("-file")) {
                string = stringArray[++i];
                continue;
            }
            if (string4.equals("-db")) {
                string3 = stringArray[++i];
                continue;
            }
            if (string4.equals("-quiet")) continue;
            if (string4.equals("-help") || string4.equals("-?")) {
                this.showUsage();
                return;
            }
            this.showUsageAndThrowUnsupportedOption(string4);
        }
        Restore.execute(string, string2, string3);
    }

    private static String getOriginalDbName(String string, String string2) throws IOException {
        try (InputStream inputStream2 = FileUtils.newInputStream(string);){
            ZipEntry zipEntry;
            ZipInputStream zipInputStream = new ZipInputStream(inputStream2);
            String string3 = null;
            boolean bl = false;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                String string4 = zipEntry.getName();
                zipInputStream.closeEntry();
                String string5 = Restore.getDatabaseNameFromFileName(string4);
                if (string5 == null) continue;
                if (string2.equals(string5)) {
                    string3 = string5;
                    break;
                }
                if (string3 == null) {
                    string3 = string5;
                    continue;
                }
                bl = true;
            }
            zipInputStream.close();
            if (bl && !string2.equals(string3)) {
                throw new IOException("Multiple databases found, but not " + string2);
            }
            zipEntry = string3;
            return zipEntry;
        }
    }

    private static String getDatabaseNameFromFileName(String string) {
        if (string.endsWith(".mv.db")) {
            return string.substring(0, string.length() - ".mv.db".length());
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void execute(String string, String string2, String string3) {
        InputStream inputStream2 = null;
        try {
            if (!FileUtils.exists(string)) {
                throw new IOException("File not found: " + string);
            }
            String string4 = null;
            int n = 0;
            if (string3 != null) {
                string4 = Restore.getOriginalDbName(string, string3);
                if (string4 == null) {
                    throw new IOException("No database named " + string3 + " found");
                }
                if (string4.startsWith(File.separator)) {
                    string4 = string4.substring(1);
                }
                n = string4.length();
            }
            inputStream2 = FileUtils.newInputStream(string);
            try (ZipInputStream zipInputStream = new ZipInputStream(inputStream2);){
                ZipEntry zipEntry;
                while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                    String string5 = zipEntry.getName();
                    if ((string5 = IOUtils.nameSeparatorsToNative(string5)).startsWith(File.separator)) {
                        string5 = string5.substring(1);
                    }
                    boolean bl = false;
                    if (string3 == null) {
                        bl = true;
                    } else if (string5.startsWith(string4 + ".")) {
                        string5 = string3 + string5.substring(n);
                        bl = true;
                    }
                    if (bl) {
                        OutputStream outputStream2 = null;
                        try {
                            outputStream2 = FileUtils.newOutputStream(string2 + File.separatorChar + string5, false);
                            IOUtils.copy(zipInputStream, outputStream2);
                            outputStream2.close();
                        }
                        catch (Throwable throwable) {
                            IOUtils.closeSilently(outputStream2);
                            throw throwable;
                        }
                        IOUtils.closeSilently(outputStream2);
                    }
                    zipInputStream.closeEntry();
                }
                zipInputStream.closeEntry();
            }
        }
        catch (IOException iOException) {
            try {
                throw DbException.convertIOException(iOException, string);
            }
            catch (Throwable throwable) {
                IOUtils.closeSilently(inputStream2);
                throw throwable;
            }
        }
        IOUtils.closeSilently(inputStream2);
    }
}

