/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.util;

import java.lang.invoke.MethodHandles;
import java.util.Collection;
import org.hibernate.validator.internal.util.StringHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.logging.Messages;

public final class Contracts {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());

    private Contracts() {
    }

    public static void assertNotNull(Object o) {
        Contracts.assertNotNull(o, Messages.MESSAGES.mustNotBeNull());
    }

    public static void assertNotNull(Object o, String message) {
        if (o == null) {
            throw LOG.getIllegalArgumentException(message);
        }
    }

    public static void assertValueNotNull(Object o, String name) {
        if (o == null) {
            throw LOG.getIllegalArgumentException(Messages.MESSAGES.mustNotBeNull(name));
        }
    }

    public static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw LOG.getIllegalArgumentException(message);
        }
    }

    public static void assertTrue(boolean condition, String message, Object ... messageParameters) {
        if (!condition) {
            throw LOG.getIllegalArgumentException(StringHelper.format(message, messageParameters));
        }
    }

    public static void assertNotEmpty(String s2, String message) {
        if (StringHelper.isNullOrEmptyString(s2)) {
            throw LOG.getIllegalArgumentException(message);
        }
    }

    public static void assertNotEmpty(Collection<?> collection, String message) {
        if (collection.size() == 0) {
            throw LOG.getIllegalArgumentException(message);
        }
    }

    public static void assertNotEmpty(Collection<?> collection, String message, Object ... messageParameters) {
        if (collection.size() == 0) {
            throw LOG.getIllegalArgumentException(StringHelper.format(message, messageParameters));
        }
    }
}

