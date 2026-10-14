/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.primitive;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NullableElements;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.logic.TrimOrNullStringFunction;

public final class StringSupport {
    private StringSupport() {
    }

    @Nonnull
    public static String inputStreamToString(@Nonnull InputStream input, @Nullable CharsetDecoder decoder) throws IOException {
        CharsetDecoder charsetDecoder = decoder;
        if (decoder == null) {
            charsetDecoder = Charset.defaultCharset().newDecoder();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(input, charsetDecoder));
        StringBuilder stringBuffer = new StringBuilder();
        String line = reader.readLine();
        while (line != null) {
            stringBuffer.append(line).append("\n");
            line = reader.readLine();
        }
        reader.close();
        return stringBuffer.toString();
    }

    @Nonnull
    public static String listToStringValue(@Nonnull List<String> values2, @Nonnull String delimiter) {
        Constraint.isNotNull(values2, "List of values can not be null");
        Constraint.isNotNull(delimiter, "String delimiter may not be null");
        StringBuilder stringValue = new StringBuilder();
        Iterator<String> valueItr = values2.iterator();
        while (valueItr.hasNext()) {
            stringValue.append(valueItr.next());
            if (!valueItr.hasNext()) continue;
            stringValue.append(delimiter);
        }
        return stringValue.toString();
    }

    @Nonnull
    public static List<String> stringToList(@Nonnull String string, @Nonnull String delimiter) {
        Constraint.isNotNull(string, "String data can not be null");
        Constraint.isNotNull(delimiter, "String delimiter may not be null");
        ArrayList<String> values2 = new ArrayList<String>();
        String trimmedString = StringSupport.trimOrNull(string);
        if (trimmedString != null) {
            StringTokenizer tokens = new StringTokenizer(trimmedString, delimiter);
            while (tokens.hasMoreTokens()) {
                values2.add(tokens.nextToken());
            }
            if (string.endsWith(delimiter)) {
                values2.add("");
            }
        }
        return values2;
    }

    @Nullable
    public static String trim(@Nullable String s2) {
        if (s2 == null) {
            return null;
        }
        return s2.trim();
    }

    @Nullable
    public static String trimOrNull(@Nullable String s2) {
        String temp = StringSupport.trim(s2);
        if (temp == null || temp.length() == 0) {
            return null;
        }
        return temp;
    }

    @Nonnull
    @NonnullElements
    public static Collection<String> normalizeStringCollection(@Nullable @NullableElements Collection<String> values2) {
        if (values2 == null) {
            return Collections.emptySet();
        }
        return Collections2.filter(Collections2.transform(values2, TrimOrNullStringFunction.INSTANCE), Predicates.notNull());
    }
}

