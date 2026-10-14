/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import liquibase.exception.UnexpectedLiquibaseException;

public class Validate {
    public static void notNull(Object object, String failMessage) throws UnexpectedLiquibaseException {
        if (object == null) {
            Validate.fail(failMessage);
        }
    }

    public static void isTrue(boolean test, String failMessage) throws UnexpectedLiquibaseException {
        if (!test) {
            Validate.fail(failMessage);
        }
    }

    public static void fail(String failMessage) {
        throw new UnexpectedLiquibaseException(failMessage);
    }

    public static UnexpectedLiquibaseException failure(String failMessage) {
        throw new UnexpectedLiquibaseException(failMessage);
    }
}

