/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UrlUtil {
    public static String handleSqlServerDbUrlParameters(List<String> keys2, String url2) {
        if (url2 != null && url2.contains(";")) {
            ArrayList<String> moveForward = new ArrayList<String>();
            String[] parts = url2.split(";");
            String mainPart = parts[0];
            for (String part : parts) {
                if (!keys2.stream().anyMatch(key -> part.toLowerCase().startsWith(key.toLowerCase() + "="))) continue;
                moveForward.add(part);
            }
            Collections.sort(moveForward);
            String forward = String.join((CharSequence)";", moveForward);
            return String.format("%s;%s", mainPart, forward);
        }
        return url2;
    }
}

