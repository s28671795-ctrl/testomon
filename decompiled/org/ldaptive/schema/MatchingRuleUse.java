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

public class MatchingRuleUse
extends AbstractNamedSchemaElement {
    private static final int HASH_CODE_SEED = 1123;
    private final String oid;
    private String[] appliesAttributeTypes;

    public MatchingRuleUse(String s2) {
        this.oid = s2;
    }

    public MatchingRuleUse(String oid, String[] names, String description, boolean obsolete, String[] appliesAttributeTypes, Extensions extensions) {
        this(oid);
        this.setNames(names);
        this.setDescription(description);
        this.setObsolete(obsolete);
        this.setAppliesAttributeTypes(appliesAttributeTypes);
        this.setExtensions(extensions);
    }

    public String getOID() {
        return this.oid;
    }

    public String[] getAppliesAttributeTypes() {
        return this.appliesAttributeTypes;
    }

    public void setAppliesAttributeTypes(String[] s2) {
        this.appliesAttributeTypes = s2;
    }

    public static MatchingRuleUse parse(String definition) throws SchemaParseException {
        return SchemaParser.parse(MatchingRuleUse.class, definition);
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
        if (this.appliesAttributeTypes != null && this.appliesAttributeTypes.length > 0) {
            sb.append("APPLIES ");
            sb.append(SchemaUtils.formatOids(this.appliesAttributeTypes));
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
        if (o instanceof MatchingRuleUse) {
            MatchingRuleUse v = (MatchingRuleUse)o;
            return LdapUtils.areEqual(this.oid, v.oid) && LdapUtils.areEqual(this.getNames(), v.getNames()) && LdapUtils.areEqual(this.getDescription(), v.getDescription()) && LdapUtils.areEqual(this.isObsolete(), v.isObsolete()) && LdapUtils.areEqual(this.appliesAttributeTypes, v.appliesAttributeTypes) && LdapUtils.areEqual(this.getExtensions(), v.getExtensions());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(1123, this.oid, this.getNames(), this.getDescription(), this.isObsolete(), this.appliesAttributeTypes, this.getExtensions());
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "oid=" + this.oid + ", " + "names=" + Arrays.toString(this.getNames()) + ", " + "description=" + this.getDescription() + ", " + "obsolete=" + this.isObsolete() + ", " + "appliesAttributeTypes=" + Arrays.toString(this.appliesAttributeTypes) + ", " + "extensions=" + this.getExtensions() + "]";
    }

    public static class RegexDefinitionFunction
    extends AbstractRegexDefinitionFunction<MatchingRuleUse> {
        private static final Pattern DEFINITION_PATTERN = Pattern.compile("[ ]*\\([ ]*([^ ]+)[ ]*(?:NAME[ ]+(?:'([^']+)'|\\(([^\\)]+)\\)))?[ ]*(?:DESC[ ]+'([^']*)')?[ ]*(OBSOLETE)?[ ]*(?:APPLIES[ ]+(?:([^ ]+)|\\(([^\\)]+)\\)))?[ ]*(?:(X-[^ ]+.*))?[ ]*\\)[ ]*");

        @Override
        public MatchingRuleUse parse(String definition) throws SchemaParseException {
            Matcher m4 = DEFINITION_PATTERN.matcher(definition);
            if (!m4.matches()) {
                throw new SchemaParseException("Invalid matching rule use definition: " + definition);
            }
            MatchingRuleUse mrud = new MatchingRuleUse(m4.group(1).trim());
            if (m4.group(2) != null) {
                mrud.setNames(SchemaUtils.parseDescriptors(m4.group(2).trim()));
            } else if (m4.group(3) != null) {
                mrud.setNames(SchemaUtils.parseDescriptors(m4.group(3).trim()));
            }
            mrud.setDescription(m4.group(4) != null ? m4.group(4).trim() : null);
            mrud.setObsolete(m4.group(5) != null);
            if (m4.group(6) != null) {
                mrud.setAppliesAttributeTypes(SchemaUtils.parseOIDs(m4.group(6).trim()));
            } else if (m4.group(7) != null) {
                mrud.setAppliesAttributeTypes(SchemaUtils.parseOIDs(m4.group(7).trim()));
            }
            if (m4.group(8) != null) {
                mrud.setExtensions(this.parseExtensions(m4.group(8).trim()));
            }
            return mrud;
        }
    }

    public static class DefaultDefinitionFunction
    extends AbstractDefaultDefinitionFunction<MatchingRuleUse> {
        @Override
        public MatchingRuleUse parse(String definition) throws SchemaParseException {
            CharBuffer buffer = this.validate(definition);
            this.skipSpaces(buffer);
            MatchingRuleUse mru = new MatchingRuleUse(this.readUntilSpace(buffer));
            Extensions exts = new Extensions();
            block14: while (buffer.hasRemaining()) {
                this.skipSpaces(buffer);
                String token = this.readUntilSpace(buffer);
                this.skipSpaces(buffer);
                switch (token) {
                    case "NAME": {
                        mru.setNames(this.readQDStrings(buffer));
                        continue block14;
                    }
                    case "DESC": {
                        mru.setDescription(this.readQDString(buffer));
                        continue block14;
                    }
                    case "OBSOLETE": {
                        mru.setObsolete(true);
                        continue block14;
                    }
                    case "APPLIES": {
                        mru.setAppliesAttributeTypes(this.readOIDs(buffer));
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
                mru.setExtensions(exts);
            }
            return mru;
        }
    }
}

