/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.properties;

import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.StringHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.spi.properties.ConstrainableExecutable;
import org.hibernate.validator.spi.properties.GetterPropertySelectionStrategy;

public class DefaultGetterPropertySelectionStrategy
implements GetterPropertySelectionStrategy {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    private static final String GETTER_PREFIX_GET = "get";
    private static final String GETTER_PREFIX_IS = "is";
    private static final String GETTER_PREFIX_HAS = "has";
    private static final String[] GETTER_PREFIXES = new String[]{"get", "is", "has"};

    @Override
    public Optional<String> getProperty(ConstrainableExecutable executable) {
        Contracts.assertNotNull(executable, "executable cannot be null");
        if (!DefaultGetterPropertySelectionStrategy.isGetter(executable)) {
            return Optional.empty();
        }
        String methodName = executable.getName();
        for (String prefix : GETTER_PREFIXES) {
            if (!methodName.startsWith(prefix)) continue;
            return Optional.of(StringHelper.decapitalize(methodName.substring(prefix.length())));
        }
        throw new AssertionError((Object)("Method " + executable.getName() + " was considered a getter but we couldn't extract a property name."));
    }

    @Override
    public Set<String> getGetterMethodNameCandidates(String propertyName) {
        Contracts.assertNotEmpty(propertyName, "Name of a property must not be empty");
        HashSet<String> nameCandidates = CollectionHelper.newHashSet(GETTER_PREFIXES.length);
        for (String prefix : GETTER_PREFIXES) {
            nameCandidates.add(prefix + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1));
        }
        return nameCandidates;
    }

    private static boolean isGetter(ConstrainableExecutable executable) {
        if (executable.getParameterTypes().length != 0) {
            return false;
        }
        String methodName = executable.getName();
        if (methodName.startsWith(GETTER_PREFIX_GET) && executable.getReturnType() != Void.TYPE) {
            return true;
        }
        if (methodName.startsWith(GETTER_PREFIX_IS) && executable.getReturnType() == Boolean.TYPE) {
            return true;
        }
        return methodName.startsWith(GETTER_PREFIX_HAS) && executable.getReturnType() == Boolean.TYPE;
    }
}

