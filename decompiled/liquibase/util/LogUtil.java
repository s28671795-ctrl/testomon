/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import liquibase.Scope;

public class LogUtil {
    public static void setPersistedMdcKeysToEmptyString() {
        Scope.getCurrentScope().addMdcValue("changesetId", "", false);
        Scope.getCurrentScope().addMdcValue("changesetAuthor", "", false);
    }
}

