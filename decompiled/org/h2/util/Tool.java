/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.Properties;
import org.h2.message.DbException;
import org.h2.store.FileLister;
import org.h2.store.fs.FileUtils;
import org.h2.util.Utils;

public abstract class Tool {
    protected PrintStream out = System.out;
    private Properties resources;

    public void setOut(PrintStream printStream) {
        this.out = printStream;
    }

    public abstract void runTool(String ... var1) throws SQLException;

    protected SQLException showUsageAndThrowUnsupportedOption(String string) throws SQLException {
        this.showUsage();
        throw this.throwUnsupportedOption(string);
    }

    protected SQLException throwUnsupportedOption(String string) throws SQLException {
        throw DbException.getJdbcSQLException(50100, string);
    }

    protected void printNoDatabaseFilesFound(String string, String string2) {
        StringBuilder stringBuilder;
        if (!FileUtils.isDirectory(string = FileLister.getDir(string))) {
            stringBuilder = new StringBuilder("Directory not found: ");
            stringBuilder.append(string);
        } else {
            stringBuilder = new StringBuilder("No database files have been found");
            stringBuilder.append(" in directory ").append(string);
            if (string2 != null) {
                stringBuilder.append(" for the database ").append(string2);
            }
        }
        this.out.println(stringBuilder.toString());
    }

    protected void showUsage() {
        String string;
        if (this.resources == null) {
            this.resources = new Properties();
            string = "/org/h2/res/javadoc.properties";
            try {
                byte[] byArray = Utils.getResource(string);
                if (byArray != null) {
                    this.resources.load(new ByteArrayInputStream(byArray));
                }
            }
            catch (IOException iOException) {
                this.out.println("Cannot load " + string);
            }
        }
        string = this.getClass().getName();
        this.out.println(this.resources.get(string));
        this.out.println("Usage: java " + this.getClass().getName() + " <options>");
        this.out.println(this.resources.get(string + ".main"));
        this.out.println("See also https://h2database.com/javadoc/" + string.replace('.', '/') + ".html");
    }

    public static boolean isOption(String string, String string2) {
        if (string.equals(string2)) {
            return true;
        }
        if (string.startsWith(string2)) {
            throw DbException.getUnsupportedException("expected: " + string2 + " got: " + string);
        }
        return false;
    }
}

