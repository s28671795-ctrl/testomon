/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.schema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.ldaptive.schema.DefinitionFunction;
import org.ldaptive.schema.Extensions;
import org.ldaptive.schema.SchemaElement;
import org.ldaptive.schema.SchemaUtils;

public abstract class AbstractRegexDefinitionFunction<T extends SchemaElement>
implements DefinitionFunction<T> {
    protected static final String WSP_REGEX = "[ ]*";
    protected static final String ONE_WSP_REGEX = "[ ]+";
    protected static final String NO_WSP_REGEX = "[^ ]+";
    private static final Pattern EXTENSIONS_PATTERN = Pattern.compile("(?:(X-[^ ]+)[ ]*(?:'([^']+)'|\\(([^\\)]+)\\))?)+");

    protected Extensions parseExtensions(String definition) {
        Matcher m4 = EXTENSIONS_PATTERN.matcher(definition);
        Extensions exts = new Extensions();
        while (m4.find()) {
            String name = m4.group(1).trim();
            ArrayList<String> values2 = new ArrayList<String>(1);
            if (m4.group(2) != null) {
                values2.add(m4.group(2).trim());
            } else if (m4.group(3) != null) {
                values2.addAll(Arrays.asList(SchemaUtils.parseDescriptors(m4.group(3).trim())));
            }
            exts.addExtension(name, values2);
        }
        return exts;
    }
}

