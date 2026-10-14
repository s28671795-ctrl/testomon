/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.SystemPropertyAction;

public final class FileUtils {
    private static final long RETRY_SLEEP_MILLIS = 10L;
    private static File defaultTempDir;
    private static Thread shutdownHook;
    private static final char[] ILLEGAL_CHARACTERS;

    private FileUtils() {
    }

    public static boolean isValidFileName(String name) {
        for (int i = name.length(); i > 0; --i) {
            char c = name.charAt(i - 1);
            for (char c2 : ILLEGAL_CHARACTERS) {
                if (c != c2) continue;
                return false;
            }
        }
        File file = new File(FileUtils.getDefaultTempDir(), name);
        boolean isValid = true;
        try {
            if (FileUtils.exists(file)) {
                return true;
            }
            if (file.createNewFile()) {
                file.delete();
            }
        }
        catch (IOException e) {
            isValid = false;
        }
        return isValid;
    }

    public static synchronized File getDefaultTempDir() {
        File f;
        if (defaultTempDir != null && FileUtils.exists(defaultTempDir)) {
            return defaultTempDir;
        }
        String s2 = SystemPropertyAction.getPropertyOrNull(FileUtils.class.getName() + ".TempDirectory");
        if (s2 != null && (f = new File(s2)).mkdirs()) {
            defaultTempDir = f;
        }
        if (defaultTempDir == null) {
            defaultTempDir = FileUtils.createTmpDir(false);
            if (shutdownHook != null) {
                Runtime.getRuntime().removeShutdownHook(shutdownHook);
            }
            shutdownHook = new Thread(){

                @Override
                public void run() {
                    FileUtils.removeDir(defaultTempDir, true);
                }
            };
            Runtime.getRuntime().addShutdownHook(shutdownHook);
        }
        return defaultTempDir;
    }

    public static synchronized void maybeDeleteDefaultTempDir() {
        if (defaultTempDir != null) {
            Runtime.getRuntime().gc();
            String[] files = defaultTempDir.list();
            if (files != null && files.length > 0) {
                Runtime.getRuntime().gc();
                Runtime.getRuntime().runFinalization();
                Runtime.getRuntime().gc();
                files = defaultTempDir.list();
            }
            if (files == null || files.length == 0) {
                try {
                    Runtime.getRuntime().removeShutdownHook(shutdownHook);
                    shutdownHook.run();
                }
                catch (IllegalStateException illegalStateException) {
                    // empty catch block
                }
                shutdownHook = null;
                defaultTempDir = null;
            }
        }
    }

    public static File createTmpDir() {
        return FileUtils.createTmpDir(true);
    }

    public static File createTmpDir(boolean addHook) {
        File newTmpDir;
        String s2 = SystemPropertyAction.getProperty("java.io.tmpdir");
        File checkExists = new File(s2);
        if (!FileUtils.exists(checkExists) || !checkExists.isDirectory()) {
            throw new RuntimeException("The directory " + checkExists.getAbsolutePath() + " does not exist, please set java.io.tempdir to an existing directory");
        }
        if (!checkExists.canWrite()) {
            throw new RuntimeException("The directory " + checkExists.getAbsolutePath() + " is not writable, please set java.io.tempdir to a writable directory");
        }
        if (checkExists.getUsableSpace() < 0x100000L) {
            LogUtils.getL7dLogger(FileUtils.class).warning("The directory " + s2 + " has very little usable temporary space.  Operations requiring temporary files may fail.");
        }
        try {
            Path path = Files.createTempDirectory(checkExists.toPath(), "cxf-tmp-", new FileAttribute[0]);
            File f = path.toFile();
            f.deleteOnExit();
            newTmpDir = f;
        }
        catch (IOException ex) {
            Random r = new Random();
            File f = new File(checkExists, "cxf-tmp-" + r.nextInt());
            int count = 0;
            while (!f.mkdir()) {
                if (count > 10000) {
                    throw new RuntimeException("Could not create a temporary directory in " + s2 + ",  please set java.io.tempdir to a writable directory");
                }
                f = new File(checkExists, "cxf-tmp-" + r.nextInt());
                ++count;
            }
            newTmpDir = f;
        }
        if (addHook) {
            final File f2 = newTmpDir;
            Thread hook = new Thread(){

                @Override
                public void run() {
                    FileUtils.removeDir(f2, true);
                }
            };
            Runtime.getRuntime().addShutdownHook(hook);
        }
        return newTmpDir;
    }

    public static void mkDir(File dir) {
        boolean result;
        if (dir == null) {
            throw new RuntimeException("dir attribute is required");
        }
        if (dir.isFile()) {
            throw new RuntimeException("Unable to create directory as a file already exists with that name: " + dir.getAbsolutePath());
        }
        if (!FileUtils.exists(dir) && !(result = FileUtils.doMkDirs(dir))) {
            String msg = "Directory " + dir.getAbsolutePath() + " creation was not successful for an unknown reason";
            throw new RuntimeException(msg);
        }
    }

    private static boolean doMkDirs(File f) {
        if (!f.mkdirs()) {
            try {
                Thread.sleep(10L);
                return f.mkdirs();
            }
            catch (InterruptedException ex) {
                return f.mkdirs();
            }
        }
        return true;
    }

    public static void removeDir(File d) {
        FileUtils.removeDir(d, false);
    }

    private static void removeDir(File d, boolean inShutdown) {
        String[] list = d.list();
        if (list == null) {
            list = new String[]{};
        }
        for (int i = 0; i < list.length; ++i) {
            String s2 = list[i];
            File f = new File(d, s2);
            if (f.isDirectory()) {
                FileUtils.removeDir(f, inShutdown);
                continue;
            }
            FileUtils.delete(f, inShutdown);
        }
        FileUtils.delete(d, inShutdown);
    }

    public static void delete(File f) {
        FileUtils.delete(f, false);
    }

    public static void delete(File f, boolean inShutdown) {
        if (!f.delete()) {
            try {
                Thread.sleep(10L);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
            if (!f.delete() && !inShutdown) {
                f.deleteOnExit();
            }
        }
    }

    public static File createTempFile(String prefix, String suffix) throws IOException {
        return FileUtils.createTempFile(prefix, suffix, null, false);
    }

    public static File createTempFile(String prefix, String suffix, File parentDir, boolean deleteOnExit) throws IOException {
        File parent;
        File file = parent = parentDir == null ? FileUtils.getDefaultTempDir() : parentDir;
        if (prefix == null) {
            prefix = "cxf";
        } else if (prefix.length() < 3) {
            prefix = prefix + "cxf";
        }
        File result = Files.createTempFile(parent.toPath(), prefix, suffix, new FileAttribute[0]).toFile();
        if (deleteOnExit && parentDir != null) {
            result.deleteOnExit();
        }
        return result;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String getStringFromFile(File location) {
        try (InputStream is = Files.newInputStream(location.toPath(), new OpenOption[0]);){
            String string = FileUtils.normalizeCRLF(is);
            return string;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String normalizeCRLF(InputStream instream) {
        BufferedReader in = new BufferedReader(new InputStreamReader(instream));
        StringBuilder result = new StringBuilder();
        String line = null;
        try {
            line = in.readLine();
            while (line != null) {
                String[] tok = line.split("\\s");
                for (int x = 0; x < tok.length; ++x) {
                    String token = tok[x];
                    result.append("  ").append(token);
                }
                line = in.readLine();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        String rtn = result.toString();
        rtn = FileUtils.ignoreTokens(rtn, "<!--", "-->");
        rtn = FileUtils.ignoreTokens(rtn, "/*", "*/");
        return rtn;
    }

    private static String ignoreTokens(String contents, String startToken, String endToken) {
        String rtn = contents;
        int headerIndexStart = rtn.indexOf(startToken);
        int headerIndexEnd = rtn.indexOf(endToken);
        if (headerIndexStart != -1 && headerIndexEnd != -1 && headerIndexStart < headerIndexEnd) {
            rtn = rtn.substring(0, headerIndexStart - 1) + rtn.substring(headerIndexEnd + endToken.length() + 1);
        }
        return rtn;
    }

    public static List<File> getFilesUsingSuffix(File dir, String suffix) {
        return FileUtils.getFilesRecurseUsingSuffix(dir, suffix, false, new ArrayList<File>());
    }

    public static List<File> getFilesRecurseUsingSuffix(File dir, String suffix) {
        return FileUtils.getFilesRecurseUsingSuffix(dir, suffix, true, new ArrayList<File>());
    }

    private static List<File> getFilesRecurseUsingSuffix(File dir, String suffix, boolean rec, List<File> fileList) {
        File[] files = dir.listFiles();
        if (files != null) {
            int suffixLength = suffix.length();
            for (File file : files) {
                if (file.isDirectory() && rec) {
                    FileUtils.getFilesRecurseUsingSuffix(file, suffix, rec, fileList);
                    continue;
                }
                if (!file.getName().endsWith(suffix) || file.getName().length() <= suffixLength) continue;
                fileList.add(file);
            }
        }
        return fileList;
    }

    public static List<File> getFiles(File dir, String pattern) {
        ArrayList<File> fileList = new ArrayList<File>();
        File[] files = dir.listFiles();
        if (files != null) {
            Pattern p = Pattern.compile(pattern);
            for (File file : files) {
                Matcher m4 = p.matcher(file.getName());
                if (!m4.matches()) continue;
                fileList.add(file);
            }
        }
        return fileList;
    }

    public static List<String> readLines(File file) throws Exception {
        if (!FileUtils.exists(file)) {
            return Collections.emptyList();
        }
        return Files.readAllLines(file.toPath());
    }

    public static boolean exists(File file) {
        if (System.getSecurityManager() == null) {
            return file.exists();
        }
        return AccessController.doPrivileged(() -> file.exists());
    }

    public static String stripPath(String name) {
        int posWin;
        if (name == null) {
            return null;
        }
        int posUnix = name.lastIndexOf(47);
        int pos = Math.max(posUnix, posWin = name.lastIndexOf(92));
        if (pos != -1) {
            return name.substring(pos + 1);
        }
        return name;
    }

    static {
        ILLEGAL_CHARACTERS = new char[]{'/', '\n', '\r', '\t', '\u0000', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':'};
    }
}

