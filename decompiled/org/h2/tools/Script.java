/*
 * Decompiled with CFR 0.152.
 */
package org.h2.tools;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.h2.util.JdbcUtils;
import org.h2.util.StringUtils;
import org.h2.util.Tool;

public class Script
extends Tool {
    public static void main(String ... stringArray) throws SQLException {
        new Script().runTool(stringArray);
    }

    @Override
    public void runTool(String ... stringArray) throws SQLException {
        String string = null;
        String string2 = "";
        String string3 = "";
        String string4 = "backup.sql";
        String string5 = "";
        String string6 = "";
        for (int i = 0; stringArray != null && i < stringArray.length; ++i) {
            String string7 = stringArray[i];
            if (string7.equals("-url")) {
                string = stringArray[++i];
                continue;
            }
            if (string7.equals("-user")) {
                string2 = stringArray[++i];
                continue;
            }
            if (string7.equals("-password")) {
                string3 = stringArray[++i];
                continue;
            }
            if (string7.equals("-script")) {
                string4 = stringArray[++i];
                continue;
            }
            if (string7.equals("-options")) {
                StringBuilder stringBuilder = new StringBuilder();
                StringBuilder stringBuilder2 = new StringBuilder();
                ++i;
                while (i < stringArray.length) {
                    String string8 = stringArray[i];
                    String string9 = StringUtils.toUpperEnglish(string8);
                    if ("SIMPLE".equals(string9) || string9.startsWith("NO") || "DROP".equals(string9)) {
                        stringBuilder.append(' ');
                        stringBuilder.append(stringArray[i]);
                    } else if ("BLOCKSIZE".equals(string9)) {
                        stringBuilder.append(' ');
                        stringBuilder.append(stringArray[i]);
                        stringBuilder.append(' ');
                        stringBuilder.append(stringArray[++i]);
                    } else {
                        stringBuilder2.append(' ');
                        stringBuilder2.append(stringArray[i]);
                    }
                    ++i;
                }
                string5 = stringBuilder.toString();
                string6 = stringBuilder2.toString();
                continue;
            }
            if (string7.equals("-help") || string7.equals("-?")) {
                this.showUsage();
                return;
            }
            this.showUsageAndThrowUnsupportedOption(string7);
        }
        if (string == null) {
            this.showUsage();
            throw new SQLException("URL not set");
        }
        Script.process(string, string2, string3, string4, string5, string6);
    }

    public static void process(String string, String string2, String string3, String string4, String string5, String string6) throws SQLException {
        try (Connection connection = JdbcUtils.getConnection(null, string, string2, string3);){
            Script.process(connection, string4, string5, string6);
        }
    }

    public static void process(Connection connection, String string, String string2, String string3) throws SQLException {
        try (Statement statement = connection.createStatement();){
            String string4 = "SCRIPT " + string2 + " TO '" + string + "' " + string3;
            statement.execute(string4);
        }
    }
}

