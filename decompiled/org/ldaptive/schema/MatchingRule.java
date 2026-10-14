/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.schema;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.ldaptive.LdapUtils;
import org.ldaptive.schema.AbstractDefaultDefinitionFunction;
import org.ldaptive.schema.AbstractNamedSchemaElement;
import org.ldaptive.schema.AbstractRegexDefinitionFunction;
import org.ldaptive.schema.Extensions;
import org.ldaptive.schema.SchemaParseException;
import org.ldaptive.schema.SchemaParser;
import org.ldaptive.schema.SchemaUtils;

public class MatchingRule
extends AbstractNamedSchemaElement {
    private static final int HASH_CODE_SEED = 1117;
    private final String oid;
    private String syntaxOID;

    public MatchingRule(String s2) {
        this.oid = s2;
    }

    public MatchingRule(String oid, String[] names, String description, boolean obsolete, String syntaxOID, Extensions extensions) {
        this(oid);
        this.setNames(names);
        this.setDescription(description);
        this.setObsolete(obsolete);
        this.setSyntaxOID(syntaxOID);
        this.setExtensions(extensions);
    }

    public String getOID() {
        return this.oid;
    }

    public String getSyntaxOID() {
        return this.syntaxOID;
    }

    public void setSyntaxOID(String s2) {
        this.syntaxOID = s2;
    }

    public static MatchingRule parse(String definition) throws SchemaParseException {
        return SchemaParser.parse(MatchingRule.class, definition);
    }

    @Override
    public String format() {
        StringBuilder sb = new StringBuilder("( ");
        sb.append(this.oid).append(" ");
        if (this.getNames() != null && this.getNames().length > 0) {
            sb.append("NAME ");
            sb.append(SchemaUtils.formatDescriptors(this.getNames()));
        }
        if (this.getDescription() != null) {
            sb.append("DESC ");
            sb.append(SchemaUtils.formatDescriptors(this.getDescription()));
        }
        if (this.isObsolete()) {
            sb.append("OBSOLETE ");
        }
        if (this.syntaxOID != null) {
            sb.append("SYNTAX ").append(this.syntaxOID).append(" ");
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
        if (o instanceof MatchingRule) {
            MatchingRule v = (MatchingRule)o;
            return LdapUtils.areEqual(this.oid, v.oid) && LdapUtils.areEqual(this.getNames(), v.getNames()) && LdapUtils.areEqual(this.getDescription(), v.getDescription()) && LdapUtils.areEqual(this.isObsolete(), v.isObsolete()) && LdapUtils.areEqual(this.syntaxOID, v.syntaxOID) && LdapUtils.areEqual(this.getExtensions(), v.getExtensions());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(1117, this.oid, this.getNames(), this.getDescription(), this.isObsolete(), this.syntaxOID, this.getExtensions());
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "oid=" + this.oid + ", " + "names=" + Arrays.toString(this.getNames()) + ", " + "description=" + this.getDescription() + ", " + "obsolete=" + this.isObsolete() + ", " + "syntaxOID=" + this.syntaxOID + ", " + "extensions=" + this.getExtensions() + "]";
    }

    public static class RegexDefinitionFunction
    extends AbstractRegexDefinitionFunction<MatchingRule> {
        private static final Pattern DEFINITION_PATTERN = Pattern.compile("[ ]*\\([ ]*([^ ]+)[ ]*(?:NAME[ ]+(?:'([^']+)'|\\(([^\\)]+)\\)))?[ ]*(?:DESC[ ]+'([^']*)')?[ ]*(OBSOLETE)?[ ]*(?:SYNTAX[ ]+([^ ]+))?[ ]*(?:(X-[^ ]+.*))?[ ]*\\)[ ]*");

        @Override
        public MatchingRule parse(String definition) throws SchemaParseException {
            Matcher m4 = DEFINITION_PATTERN.matcher(definition);
            if (!m4.matches()) {
                throw new SchemaParseException("Invalid matching rule definition: " + definition);
            }
            MatchingRule mrd = new MatchingRule(m4.group(1).trim());
            if (m4.group(2) != null) {
                mrd.setNames(SchemaUtils.parseDescriptors(m4.group(2).trim()));
            } else if (m4.group(3) != null) {
                mrd.setNames(SchemaUtils.parseDescriptors(m4.group(3).trim()));
            }
            mrd.setDescription(m4.group(4) != null ? m4.group(4).trim() : null);
            mrd.setObsolete(m4.group(5) != null);
            mrd.setSyntaxOID(m4.group(6) != null ? m4.group(6).trim() : null);
            if (m4.group(7) != null) {
                mrd.setExtensions(this.parseExtensions(m4.group(7).trim()));
            }
            return mrd;
        }
    }

    public static class DefaultDefinitionFunction
    extends AbstractDefaultDefinitionFunction<MatchingRule> {
        @Override
        public MatchingRule parse(String definition) throws SchemaParseException {
            CharBuffer buffer = this.validate(definition);
            this.skipSpaces(buffer);
            MatchingRule mr = new MatchingRule(this.readUntilSpace(buffer));
            Extensions exts = new Extensions();
            block14: while (buffer.hasRemaining()) {
                this.skipSpaces(buffer);
                String token = this.readUntilSpace(buffer);
                this.skipSpaces(buffer);
                switch (token) {
                    case "NAME": {
                        mr.setNames(this.readQDStrings(buffer));
                        continue block14;
                    }
                    case "DESC": {
                        mr.setDescription(this.readQDString(buffer));
                        continue block14;
                    }
                    case "OBSOLETE": {
                        mr.setObsolete(true);
                        continue block14;
                    }
                    case "SYNTAX": {
                        mr.setSyntaxOID(this.readUntilSpace(buffer));
                        continue block14;
                    }
                    case "": {
                        continue block14;
                    }
                }
                if (!token.startsWith("X-")) {
                    throw new SchemaParseException("Definition '" + definition + "' contains invalid extension '" + token + "'");
                }
                this.skipSpaces(buffer);
                exts.addExtension(token, List.of(this.readQDStrings(buffer)));
            }
            if (!exts.isEmpty()) {
                mr.setExtensions(exts);
            }
            return mr;
        }
    }
}

