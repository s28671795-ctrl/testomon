/*
 * Decompiled with CFR 0.152.
 */
package org.h2.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.h2.message.DbException;
import org.h2.mvstore.MVStore;
import org.h2.store.FileLister;
import org.h2.store.fs.FilePath;
import org.h2.store.fs.FileUtils;
import org.h2.store.fs.encrypt.FileEncrypt;
import org.h2.store.fs.encrypt.FilePathEncrypt;
import org.h2.util.Tool;

public class ChangeFileEncryption
extends Tool {
    private String directory;
    private String cipherType;
    private byte[] decryptKey;
    private byte[] encryptKey;

    public static void main(String ... stringArray) {
        try {
            new ChangeFileEncryption().runTool(stringArray);
        }
        catch (SQLException sQLException) {
            sQLException.printStackTrace(System.err);
            System.exit(1);
        }
    }

    @Override
    public void runTool(String ... stringArray) throws SQLException {
        String string = ".";
        String string2 = null;
        char[] cArray = null;
        char[] cArray2 = null;
        String string3 = null;
        boolean bl = false;
        for (int i = 0; stringArray != null && i < stringArray.length; ++i) {
            String string4 = stringArray[i];
            if (string4.equals("-dir")) {
                string = stringArray[++i];
                continue;
            }
            if (string4.equals("-cipher")) {
                string2 = stringArray[++i];
                continue;
            }
            if (string4.equals("-db")) {
                string3 = stringArray[++i];
                continue;
            }
            if (string4.equals("-decrypt")) {
                cArray = stringArray[++i].toCharArray();
                continue;
            }
            if (string4.equals("-encrypt")) {
                cArray2 = stringArray[++i].toCharArray();
                continue;
            }
            if (string4.equals("-quiet")) {
                bl = true;
                continue;
            }
            if (string4.equals("-help") || string4.equals("-?")) {
                this.showUsage();
                return;
            }
            this.showUsageAndThrowUnsupportedOption(string4);
        }
        if (cArray2 == null && cArray == null || string2 == null) {
            this.showUsage();
            throw new SQLException("Encryption or decryption password not set, or cipher not set");
        }
        try {
            this.process(string, string3, string2, cArray, cArray2, bl);
        }
        catch (Exception exception) {
            throw DbException.toSQLException(exception);
        }
    }

    public static void execute(String string, String string2, String string3, char[] cArray, char[] cArray2, boolean bl) throws SQLException {
        try {
            new ChangeFileEncryption().process(string, string2, string3, cArray, cArray2, bl);
        }
        catch (Exception exception) {
            throw DbException.toSQLException(exception);
        }
    }

    private void process(String string, String string2, String string3, char[] cArray, char[] cArray2, boolean bl) throws SQLException {
        string = FileLister.getDir(string);
        ChangeFileEncryption changeFileEncryption = new ChangeFileEncryption();
        if (cArray2 != null) {
            for (char c : cArray2) {
                if (c != ' ') continue;
                throw new SQLException("The file password may not contain spaces");
            }
            changeFileEncryption.encryptKey = FilePathEncrypt.getPasswordBytes(cArray2);
        }
        if (cArray != null) {
            changeFileEncryption.decryptKey = FilePathEncrypt.getPasswordBytes(cArray);
        }
        changeFileEncryption.out = this.out;
        changeFileEncryption.directory = string;
        changeFileEncryption.cipherType = string3;
        Object object = FileLister.getDatabaseFiles(string, string2, true);
        FileLister.tryUnlockDatabase((List<String>)object, "encryption");
        object = FileLister.getDatabaseFiles(string, string2, false);
        if (((ArrayList)object).isEmpty() && !bl) {
            this.printNoDatabaseFilesFound(string, string2);
        }
        Iterator iterator2 = ((ArrayList)object).iterator();
        while (iterator2.hasNext()) {
            String string4 = (String)iterator2.next();
            String string5 = string + "/temp.db";
            FileUtils.delete(string5);
            FileUtils.move(string4, string5);
            FileUtils.move(string5, string4);
        }
        iterator2 = ((ArrayList)object).iterator();
        while (iterator2.hasNext()) {
            String string6 = (String)iterator2.next();
            if (FileUtils.isDirectory(string6)) continue;
            changeFileEncryption.process(string6, bl, cArray);
        }
    }

    private void process(String string, boolean bl, char[] cArray) throws SQLException {
        if (string.endsWith(".mv.db")) {
            try {
                this.copyMvStore(string, bl, cArray);
            }
            catch (IOException iOException) {
                throw DbException.convertIOException(iOException, "Error encrypting / decrypting file " + string);
            }
            return;
        }
    }

    private void copyMvStore(String string, boolean bl, char[] cArray) throws IOException, SQLException {
        Object object;
        if (FileUtils.isDirectory(string)) {
            return;
        }
        try {
            object = new MVStore.Builder().fileName(string).readOnly().encryptionKey(cArray).open();
            ((MVStore)object).close();
        }
        catch (IllegalStateException illegalStateException) {
            throw new SQLException("error decrypting file " + string, illegalStateException);
        }
        object = this.directory + "/temp.db";
        try (FileChannel fileChannel = ChangeFileEncryption.getFileChannel(string, "r", this.decryptKey);
             InputStream inputStream2 = Channels.newInputStream(fileChannel);){
            FileUtils.delete((String)object);
            try (OutputStream outputStream2 = Channels.newOutputStream(ChangeFileEncryption.getFileChannel((String)object, "rw", this.encryptKey));){
                long l;
                byte[] byArray = new byte[4096];
                long l2 = l = fileChannel.size();
                long l3 = System.nanoTime();
                while (l > 0L) {
                    if (!bl && System.nanoTime() - l3 > TimeUnit.SECONDS.toNanos(1L)) {
                        this.out.println(string + ": " + (100L - 100L * l / l2) + "%");
                        l3 = System.nanoTime();
                    }
                    int n = (int)Math.min((long)byArray.length, l);
                    n = inputStream2.read(byArray, 0, n);
                    outputStream2.write(byArray, 0, n);
                    l -= (long)n;
                }
            }
        }
        FileUtils.delete(string);
        FileUtils.move((String)object, string);
    }

    private static FileChannel getFileChannel(String string, String string2, byte[] byArray) throws IOException {
        FileChannel fileChannel = FilePath.get(string).open(string2);
        if (byArray != null) {
            fileChannel = new FileEncrypt(string, byArray, fileChannel);
        }
        return fileChannel;
    }
}

