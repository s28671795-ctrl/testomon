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

public class NameForm
extends AbstractNamedSchemaElement {
    private static final int HASH_CODE_SEED = 1163;
    private final String oid;
    private String structuralClass;
    private String[] requiredAttributes;
    private String[] optionalAttributes;

    public NameForm(String s2) {
        this.oid = s2;
    }

    public NameForm(String oid, String[] names, String description, boolean obsolete, String structuralClass, String[] requiredAttributes, String[] optionalAttributes, Extensions extensions) {
        this(oid);
        this.setNames(names);
        this.setDescription(description);
        this.setObsolete(obsolete);
        this.setStructuralClass(structuralClass);
        this.setRequiredAttributes(requiredAttributes);
        this.setOptionalAttributes(optionalAttributes);
        this.setExtensions(extensions);
    }

    public String getOID() {
        return this.oid;
    }

    public String getStructuralClass() {
        return this.structuralClass;
    }

    public void setStructuralClass(String s2) {
        this.structuralClass = s2;
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

    public static NameForm parse(String definition) throws SchemaParseException {
        return SchemaParser.parse(NameForm.class, definition);
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
        if (this.structuralClass != null) {
            sb.append("OC ").append(this.structuralClass).append(" ");
        }
        if (this.requiredAttributes != null && this.requiredAttributes.length > 0) {
            sb.append("MUST ");
            sb.append(SchemaUtils.formatOids(this.requiredAttributes));
        }
        if (this.optionalAttributes != null && this.optionalAttributes.length > 0) {
            sb.append("MAY ");
            sb.append(SchemaUtils.formatOids(this.optionalAttributes));
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
        if (o instanceof NameForm) {
            NameForm v = (NameForm)o;
            return LdapUtils.areEqual(this.oid, v.oid) && LdapUtils.areEqual(this.getNames(), v.getNames()) && LdapUtils.areEqual(this.getDescription(), v.getDescription()) && LdapUtils.areEqual(this.isObsolete(), v.isObsolete()) && LdapUtils.areEqual(this.structuralClass, v.structuralClass) && LdapUtils.areEqual(this.requiredAttributes, v.requiredAttributes) && LdapUtils.areEqual(this.optionalAttributes, v.optionalAttributes) && LdapUtils.areEqual(this.getExtensions(), v.getExtensions());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(1163, this.oid, this.getNames(), this.getDescription(), this.isObsolete(), this.structuralClass, this.requiredAttributes, this.optionalAttributes, this.getExtensions());
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "oid=" + this.oid + ", " + "names=" + Arrays.toString(this.getNames()) + ", " + "description=" + this.getDescription() + ", " + "obsolete=" + this.isObsolete() + ", " + "structuralClass=" + this.structuralClass + ", " + "requiredAttributes=" + Arrays.toString(this.requiredAttributes) + ", " + "optionalAttributes=" + Arrays.toString(this.optionalAttributes) + ", " + "extensions=" + this.getExtensions() + "]";
    }

    public static class RegexDefinitionFunction
    extends AbstractRegexDefinitionFunction<NameForm> {
        private static final Pattern DEFINITION_PATTERN = Pattern.compile("[ ]*\\([ ]*([^ ]+)[ ]*(?:NAME[ ]+(?:'([^']+)'|\\(([^\\)]+)\\)))?[ ]*(?:DESC[ ]+'([^']*)')?[ ]*(OBSOLETE)?[ ]*(?:OC[ ]+([^ ]+))?[ ]*(?:MUST[ ]+(?:([^ ]+)|\\(([^\\)]+)\\)))?[ ]*(?:MAY[ ]+(?:([^ ]+)|\\(([^\\)]+)\\)))?[ ]*(?:(X-[^ ]+.*))?[ ]*\\)[ ]*");

        @Override
        public NameForm parse(String definition) throws SchemaParseException {
            Matcher m4 = DEFINITION_PATTERN.matcher(definition);
            if (!m4.matches()) {
                throw new SchemaParseException("Invalid name form definition: " + definition);
            }
            NameForm nfd = new NameForm(m4.group(1).trim());
            if (m4.group(2) != null) {
                nfd.setNames(SchemaUtils.parseDescriptors(m4.group(2).trim()));
            } else if (m4.group(3) != null) {
                nfd.setNames(SchemaUtils.parseDescriptors(m4.group(3).trim()));
            }
            nfd.setDescription(m4.group(4) != null ? m4.group(4).trim() : null);
            nfd.setObsolete(m4.group(5) != null);
            nfd.setStructuralClass(m4.group(6) != null ? m4.group(6).trim() : null);
            if (m4.group(7) != null) {
                nfd.setRequiredAttributes(SchemaUtils.parseOIDs(m4.group(7).trim()));
            } else if (m4.group(8) != null) {
                nfd.setRequiredAttributes(SchemaUtils.parseOIDs(m4.group(8).trim()));
            }
            if (m4.group(9) != null) {
                nfd.setOptionalAttributes(SchemaUtils.parseOIDs(m4.group(9).trim()));
            } else if (m4.group(10) != null) {
                nfd.setOptionalAttributes(SchemaUtils.parseOIDs(m4.group(10).trim()));
            }
            if (m4.group(11) != null) {
                nfd.setExtensions(this.parseExtensions(m4.group(11).trim()));
            }
            return nfd;
        }
    }

    public static class DefaultDefinitionFunction
    extends AbstractDefaultDefinitionFunction<NameForm> {
        @Override
        public NameForm parse(String definition) throws SchemaParseException {
            CharBuffer buffer = this.validate(definition);
            this.skipSpaces(buffer);
            NameForm nf = new NameForm(this.readUntilSpace(buffer));
            Extensions exts = new Extensions();
            block18: while (buffer.hasRemaining()) {
                this.skipSpaces(buffer);
                String token = this.readUntilSpace(buffer);
                this.skipSpaces(buffer);
                switch (token) {
                    case "NAME": {
                        nf.setNames(this.readQDStrings(buffer));
                        continue block18;
                    }
                    case "DESC": {
                        nf.setDescription(this.readQDString(buffer));
                        continue block18;
                    }
                    case "OBSOLETE": {
                        nf.setObsolete(true);
                        continue block18;
                    }
                    case "OC": {
                        nf.setStructuralClass(this.readOID(buffer));
                        continue block18;
                    }
                    case "MUST": {
                        nf.setRequiredAttributes(this.readOIDs(buffer));
                        continue block18;
                    }
                    case "MAY": {
                        nf.setOptionalAttributes(this.readOIDs(buffer));
                        continue block18;
                    }
                    case "": {
                        continue block18;
                    }
                }
                if (!token.startsWith("X-")) {
                    throw new SchemaParseException("Definition '" + definition + "' contains invalid extension '" + token + "'");
                }
                this.skipSpaces(buffer);
                exts.addExtension(token, List.of(this.readQDStrings(buffer)));
            }
            if (!exts.isEmpty()) {
                nf.setExtensions(exts);
            }
            return nf;
        }
    }
}

