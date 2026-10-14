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

public class DITContentRule
extends AbstractNamedSchemaElement {
    private static final int HASH_CODE_SEED = 1151;
    private final String oid;
    private String[] auxiliaryClasses;
    private String[] requiredAttributes;
    private String[] optionalAttributes;
    private String[] restrictedAttributes;

    public DITContentRule(String s2) {
        this.oid = s2;
    }

    public DITContentRule(String oid, String[] names, String description, boolean obsolete, String[] auxiliaryClasses, String[] requiredAttributes, String[] optionalAttributes, String[] restrictedAttributes, Extensions extensions) {
        this(oid);
        this.setNames(names);
        this.setDescription(description);
        this.setObsolete(obsolete);
        this.setAuxiliaryClasses(auxiliaryClasses);
        this.setRequiredAttributes(requiredAttributes);
        this.setOptionalAttributes(optionalAttributes);
        this.setRestrictedAttributes(restrictedAttributes);
        this.setExtensions(extensions);
    }

    public String getOID() {
        return this.oid;
    }

    public String[] getAuxiliaryClasses() {
        return this.auxiliaryClasses;
    }

    public void setAuxiliaryClasses(String[] s2) {
        this.auxiliaryClasses = s2;
    }

    public String[] getRequiredAttributes() {
        return this.requiredAttributes;
    }

    public void setRequiredAttributes(String[] s2) {
        this.requiredAttributes = s2;
    }

    public String[] getOptionalAttributes() {
        return this.optionalAttributes;
    }

    public void setOptionalAttributes(String[] s2) {
        this.optionalAttributes = s2;
    }

    public String[] getRestrictedAttributes() {
        return this.restrictedAttributes;
    }

    public void setRestrictedAttributes(String[] s2) {
        this.restrictedAttributes = s2;
    }

    public static DITContentRule parse(String definition) throws SchemaParseException {
        return SchemaParser.parse(DITContentRule.class, definition);
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
        if (this.auxiliaryClasses != null && this.auxiliaryClasses.length > 0) {
            sb.append("AUX ");
            sb.append(SchemaUtils.formatOids(this.auxiliaryClasses));
        }
        if (this.requiredAttributes != null && this.requiredAttributes.length > 0) {
            sb.append("MUST ");
            sb.append(SchemaUtils.formatOids(this.requiredAttributes));
        }
        if (this.optionalAttributes != null && this.optionalAttributes.length > 0) {
            sb.append("MAY ");
            sb.append(SchemaUtils.formatOids(this.optionalAttributes));
        }
        if (this.restrictedAttributes != null && this.restrictedAttributes.length > 0) {
            sb.append("NOT ");
            sb.append(SchemaUtils.formatOids(this.restrictedAttributes));
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
        if (o instanceof DITContentRule) {
            DITContentRule v = (DITContentRule)o;
            return LdapUtils.areEqual(this.oid, v.oid) && LdapUtils.areEqual(this.getNames(), v.getNames()) && LdapUtils.areEqual(this.getDescription(), v.getDescription()) && LdapUtils.areEqual(this.isObsolete(), v.isObsolete()) && LdapUtils.areEqual(this.auxiliaryClasses, v.auxiliaryClasses) && LdapUtils.areEqual(this.requiredAttributes, v.requiredAttributes) && LdapUtils.areEqual(this.optionalAttributes, v.optionalAttributes) && LdapUtils.areEqual(this.restrictedAttributes, v.restrictedAttributes) && LdapUtils.areEqual(this.getExtensions(), v.getExtensions());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(1151, this.oid, this.getNames(), this.getDescription(), this.isObsolete(), this.auxiliaryClasses, this.requiredAttributes, this.optionalAttributes, this.restrictedAttributes, this.getExtensions());
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "oid=" + this.oid + ", " + "names=" + Arrays.toString(this.getNames()) + ", " + "description=" + this.getDescription() + ", " + "obsolete=" + this.isObsolete() + ", " + "auxiliaryClasses=" + Arrays.toString(this.auxiliaryClasses) + ", " + "requiredAttributes=" + Arrays.toString(this.requiredAttributes) + ", " + "optionalAttributes=" + Arrays.toString(this.optionalAttributes) + ", " + "restrictedAttributes=" + Arrays.toString(this.restrictedAttributes) + ", " + "extensions=" + this.getExtensions() + "]";
    }

    public static class RegexDefinitionFunction
    extends AbstractRegexDefinitionFunction<DITContentRule> {
        private static final Pattern DEFINITION_PATTERN = Pattern.compile("[ ]*\\([ ]*([^ ]+)[ ]*(?:NAME[ ]+(?:'([^']+)'|\\(([^\\)]+)\\)))?[ ]*(?:DESC[ ]+'([^']*)')?[ ]*(OBSOLETE)?[ ]*(?:AUX[ ]+(?:([^ ]+)|\\(([^\\)]+)\\)))?[ ]*(?:MUST[ ]+(?:([^ ]+)|\\(([^\\)]+)\\)))?[ ]*(?:MAY[ ]+(?:([^ ]+)|\\(([^\\)]+)\\)))?[ ]*(?:NOT[ ]+(?:([^ ]+)|\\(([^\\)]+)\\)))?[ ]*(?:(X-[^ ]+.*))?[ ]*\\)[ ]*");

        @Override
        public DITContentRule parse(String definition) throws SchemaParseException {
            Matcher m4 = DEFINITION_PATTERN.matcher(definition);
            if (!m4.matches()) {
                throw new SchemaParseException("Invalid DIT content rule definition: " + definition);
            }
            DITContentRule dcrd = new DITContentRule(m4.group(1).trim());
            if (m4.group(2) != null) {
                dcrd.setNames(SchemaUtils.parseDescriptors(m4.group(2).trim()));
            } else if (m4.group(3) != null) {
                dcrd.setNames(SchemaUtils.parseDescriptors(m4.group(3).trim()));
            }
            dcrd.setDescription(m4.group(4) != null ? m4.group(4).trim() : null);
            dcrd.setObsolete(m4.group(5) != null);
            if (m4.group(6) != null) {
                dcrd.setAuxiliaryClasses(SchemaUtils.parseOIDs(m4.group(6).trim()));
            } else if (m4.group(7) != null) {
                dcrd.setAuxiliaryClasses(SchemaUtils.parseOIDs(m4.group(7).trim()));
            }
            if (m4.group(9) != null) {
                dcrd.setRequiredAttributes(SchemaUtils.parseOIDs(m4.group(9).trim()));
            } else if (m4.group(10) != null) {
                dcrd.setRequiredAttributes(SchemaUtils.parseOIDs(m4.group(10).trim()));
            }
            if (m4.group(11) != null) {
                dcrd.setOptionalAttributes(SchemaUtils.parseOIDs(m4.group(11).trim()));
            } else if (m4.group(12) != null) {
                dcrd.setOptionalAttributes(SchemaUtils.parseOIDs(m4.group(12).trim()));
            }
            if (m4.group(11) != null) {
                dcrd.setRestrictedAttributes(SchemaUtils.parseOIDs(m4.group(11).trim()));
            } else if (m4.group(12) != null) {
                dcrd.setRestrictedAttributes(SchemaUtils.parseOIDs(m4.group(12).trim()));
            }
            if (m4.group(13) != null) {
                dcrd.setExtensions(this.parseExtensions(m4.group(13).trim()));
            }
            return dcrd;
        }
    }

    public static class DefaultDefinitionFunction
    extends AbstractDefaultDefinitionFunction<DITContentRule> {
        @Override
        public DITContentRule parse(String definition) throws SchemaParseException {
            CharBuffer buffer = this.validate(definition);
            this.skipSpaces(buffer);
            DITContentRule dcr = new DITContentRule(this.readUntilSpace(buffer));
            Extensions exts = new Extensions();
            block20: while (buffer.hasRemaining()) {
                this.skipSpaces(buffer);
                String token = this.readUntilSpace(buffer);
                this.skipSpaces(buffer);
                switch (token) {
                    case "NAME": {
                        dcr.setNames(this.readQDStrings(buffer));
                        continue block20;
                    }
                    case "DESC": {
                        dcr.setDescription(this.readQDString(buffer));
                        continue block20;
                    }
                    case "OBSOLETE": {
                        dcr.setObsolete(true);
                        continue block20;
                    }
                    case "AUX": {
                        dcr.setAuxiliaryClasses(this.readOIDs(buffer));
                        continue block20;
                    }
                    case "MUST": {
                        dcr.setRequiredAttributes(this.readOIDs(buffer));
                        continue block20;
                    }
                    case "MAY": {
                        dcr.setOptionalAttributes(this.readOIDs(buffer));
                        continue block20;
                    }
                    case "NOT": {
                        dcr.setRestrictedAttributes(this.readOIDs(buffer));
                        continue block20;
                    }
                    case "": {
                        continue block20;
                    }
                }
                if (!token.startsWith("X-")) {
                    throw new SchemaParseException("Definition '" + definition + "' contains invalid extension '" + token + "'");
                }
                this.skipSpaces(buffer);
                exts.addExtension(token, List.of(this.readQDStrings(buffer)));
            }
            if (!exts.isEmpty()) {
                dcr.setExtensions(exts);
            }
            return dcr;
        }
    }
}

