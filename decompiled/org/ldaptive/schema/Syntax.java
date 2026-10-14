/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.schema;

import java.nio.CharBuffer;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.ldaptive.LdapUtils;
import org.ldaptive.schema.AbstractDefaultDefinitionFunction;
import org.ldaptive.schema.AbstractRegexDefinitionFunction;
import org.ldaptive.schema.AbstractSchemaElement;
import org.ldaptive.schema.Extensions;
import org.ldaptive.schema.SchemaParseException;
import org.ldaptive.schema.SchemaParser;
import org.ldaptive.schema.SchemaUtils;

public class Syntax
extends AbstractSchemaElement {
    private static final int HASH_CODE_SEED = 1129;
    private final String oid;

    public Syntax(String s2) {
        this.oid = s2;
    }

    public Syntax(String oid, String description, Extensions extensions) {
        this(oid);
        this.setDescription(description);
        this.setExtensions(extensions);
    }

    public String getOID() {
        return this.oid;
    }

    public static Syntax parse(String definition) throws SchemaParseException {
        return SchemaParser.parse(Syntax.class, definition);
    }

    @Override
    public String format() {
        StringBuilder sb = new StringBuilder("( ");
        sb.append(this.oid).append(" ");
        if (this.getDescription() != null) {
            sb.append("DESC ");
            sb.append(SchemaUtils.formatDescriptors(this.getDescription()));
        }
        if (this.getExtensions() != null) {
            sb.append(this.getExtensions().format());
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof Syntax) {
            Syntax v = (Syntax)o;
            return LdapUtils.areEqual(this.oid, v.oid) && LdapUtils.areEqual(this.getDescription(), v.getDescription()) && LdapUtils.areEqual(this.getExtensions(), v.getExtensions());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(1129, this.oid, this.getDescription(), this.getExtensions());
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "oid=" + this.oid + ", " + "description=" + this.getDescription() + ", " + "extensions=" + this.getExtensions() + "]";
    }

    public static class RegexDefinitionFunction
    extends AbstractRegexDefinitionFunction<Syntax> {
        private static final Pattern DEFINITION_PATTERN = Pattern.compile("[ ]*\\([ ]*([^ ]+)[ ]*(?:DESC '([^']+)')?[ ]*(?:(X-[^ ]+.*))?[ ]*\\)[ ]*");

        @Override
        public Syntax parse(String definition) throws SchemaParseException {
            Matcher m4 = DEFINITION_PATTERN.matcher(definition);
            if (!m4.matches()) {
                throw new SchemaParseException("Invalid attribute syntax definition: " + definition);
            }
            Syntax asd = new Syntax(m4.group(1).trim());
            asd.setDescription(m4.group(2) != null ? m4.group(2).trim() : null);
            if (m4.group(3) != null) {
                asd.setExtensions(this.parseExtensions(m4.group(3).trim()));
            }
            return asd;
        }
    }

    public static class DefaultDefinitionFunction
    extends AbstractDefaultDefinitionFunction<Syntax> {
        @Override
        public Syntax parse(String definition) throws SchemaParseException {
            CharBuffer buffer = this.validate(definition);
            this.skipSpaces(buffer);
            Syntax s2 = new Syntax(this.readUntilSpace(buffer));
            Extensions exts = new Extensions();
            block8: while (buffer.hasRemaining()) {
                this.skipSpaces(buffer);
                String token = this.readUntilSpace(buffer);
                this.skipSpaces(buffer);
                switch (token) {
                    case "DESC": {
                        s2.setDescription(this.readQDString(buffer));
                        continue block8;
                    }
                    case "": {
                        continue block8;
                    }
                }
                if (!token.startsWith("X-")) {
                    throw new SchemaParseException("Definition '" + definition + "' contains invalid extension '" + token + "'");
                }
                this.skipSpaces(buffer);
                exts.addExtension(token, List.of(this.readQDStrings(buffer)));
            }
            if (!exts.isEmpty()) {
                s2.setExtensions(exts);
            }
            return s2;
        }
    }
}

