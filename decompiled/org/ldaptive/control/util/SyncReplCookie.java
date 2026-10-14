/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.control.util;

import java.util.HashMap;
import java.util.Map;

public class SyncReplCookie {
    private final String rid;
    private final CSN csn;

    public SyncReplCookie(String cookie) {
        Map<String, String> nameValues = SyncReplCookie.parseCookie(cookie);
        if (!nameValues.containsKey("rid")) {
            throw new IllegalArgumentException("Could not parse 'rid' from " + cookie);
        }
        if (!nameValues.containsKey("csn")) {
            throw new IllegalArgumentException("Could not parse 'csn' from " + cookie);
        }
        this.rid = nameValues.get("rid");
        this.csn = new CSN(nameValues.get("csn"));
    }

    public String getRid() {
        return this.rid;
    }

    public CSN getCsn() {
        return this.csn;
    }

    private static Map<String, String> parseCookie(String cookie) {
        String[] nameValuePairs;
        HashMap<String, String> parsedCookie = new HashMap<String, String>(2);
        for (String nameValuePair : nameValuePairs = cookie.split(",")) {
            String[] nameValue = nameValuePair.split("=");
            parsedCookie.put(nameValue[0], nameValue[1]);
        }
        return parsedCookie;
    }

    public static class CSN {
        private final String value;
        private final String time;
        private final String count;
        private final String sid;
        private final String mod;

        public CSN(String csn) {
            this.value = csn;
            String[] csnParts = csn.split("#");
            if (csnParts.length != 4) {
                throw new IllegalArgumentException("CSN does not contain 4 parts: " + csn);
            }
            this.time = csnParts[0];
            this.count = csnParts[1];
            this.sid = csnParts[2];
            this.mod = csnParts[3];
        }

        public String getValue() {
            return this.value;
        }

        public String getTime() {
            return this.time;
        }

        public String getCount() {
            return this.count;
        }

        public String getSid() {
            return this.sid;
        }

        public String getMod() {
            return this.mod;
        }
    }
}

