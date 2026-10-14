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
import org.ldaptive.schema.ObjectClassType;
import org.ldaptive.schema.SchemaParseException;
import org.ldaptive.schema.SchemaParser;
import org.ldaptive.schema.SchemaUtils;

public class ObjectClass
extends AbstractNamedSchemaElement {
    private static final int HASH_CODE_SEED = 1109;
    private final String oid;
    private String[] superiorClasses;
    private ObjectClassType objectClassType;
    private String[] requiredAttributes;
    private String[] optionalAttributes;

    public ObjectClass(String s2) {
        this.oid = s2;
    }

    public ObjectClass(String oid, String[] names, String description, boolean obsolete, String[] superiorClasses, ObjectClassType objectClassType, String[] requiredAttributes, String[] optionalAttributes, Extensions extensions) {
        this(oid);
        this.setNames(names);
        this.setDescription(description);
        this.setObsolete(obsolete);
        this.setSuperiorClasses(superiorClasses);
        this.setObjectClassType(objectClassType);
        this.setRequiredAttributes(requiredAttributes);
        this.setOptionalAttributes(optionalAttributes);
        this.setExtensions(extensions);
    }

    public String getOID() {
        return this.oid;
    }

    public String[] getSuperiorClasses() {
        return this.superiorClasses;
    }

    public void setSuperiorClasses(String[] s2) {
        this.superiorClasses = s2;
    }

    public ObjectClassType getObjectClassType() {
        return this.objectClassType;
    }

    public void setObjectClassType(ObjectClassType type) {
        this.objectClassType = type;
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

    public static ObjectClass parse(String definition) throws SchemaParseException {
        return SchemaParser.parse(ObjectClass.class, definition);
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
        if (this.superiorClasses != null && this.superiorClasses.length > 0) {
            sb.append("SUP ");
            sb.append(SchemaUtils.formatOids(this.superiorClasses));
        }
        if (this.objectClassType != null) {
            sb.append(this.objectClassType.name()).append(" ");
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
        if (o instanceof ObjectClass) {
            ObjectClass v = (ObjectClass)o;
            return LdapUtils.areEqual(this.oid, v.oid) && LdapUtils.areEqual(this.getNames(), v.getNames()) && LdapUtils.areEqual(this.getDescription(), v.getDescription()) && LdapUtils.areEqual(this.isObsolete(), v.isObsolete()) && LdapUtils.areEqual(this.superiorClasses, v.superiorClasses) && LdapUtils.areEqual((Object)this.objectClassType, (Object)v.objectClassType) && LdapUtils.areEqual(this.requiredAttributes, v.requiredAttributes) && LdapUtils.areEqual(this.optionalAttributes, v.optionalAttributes) && LdapUtils.areEqual(this.getExtensions(), v.getExtensions());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(1109, new Object[]{this.oid, this.getNames(), this.getDescription(), this.isObsolete(), this.superiorClasses, this.objectClassType, this.requiredAttributes, this.optionalAttributes, this.getExtensions()});
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "oid=" + this.oid + ", " + "names=" + Arrays.toString(this.getNames()) + ", " + "description=" + this.getDescription() + ", " + "obsolete=" + this.isObsolete() + ", " + "superiorClasses=" + Arrays.toString(this.superiorClasses) + ", " + "objectClassType=" + (Object)((Object)this.objectClassType) + ", " + "requiredAttributes=" + Arrays.toString(this.requiredAttributes) + ", " + "optionalAttributes=" + Arrays.toString(this.optionalAttributes) + ", " + "extensions=" + this.getExtensions() + "]";
    }

    public static class RegexDefinitionFunction
    extends AbstractRegexDefinitionFunction<ObjectClass> {
        private static final Pattern DEFINITION_PATTERN = Pattern.compile("[ ]*\\([ ]*([^ ]+)[ ]*(?:NAME[ ]+(?:'([^']+)'|\\(([^\\)]+)\\)))?[ ]*(?:DESC[ ]+'([^']*)')?[ ]*(OBSOLETE)?[ ]*(?:SUP[ ]+(?:([^ ]+)|\\(([^\\)]+)\\)))?[ ]*(\\p{Alpha}+)?[ ]*(?:MUST[ ]+(?:([^ ]+)|\\(([^\\)]+)\\)))?[ ]*(?:MAY[ ]+(?:([^ ]+)|\\(([^\\)]+)\\)))?[ ]*(?:(X-[^ ]+.*))?[ ]*\\)[ ]*");

        @Override
        public ObjectClass parse(String definition) throws SchemaParseException {
            Matcher m4 = DEFINITION_PATTERN.matcher(definition);
            if (!m4.matches()) {
                throw new SchemaParseException("Invalid object class definition: " + definition);
            }
            ObjectClass ocd = new ObjectClass(m4.group(1).trim());
            if (m4.group(2) != null) {
                ocd.setNames(SchemaUtils.parseDescriptors(m4.group(2).trim()));
            } else if (m4.group(3) != null) {
                ocd.setNames(SchemaUtils.parseDescriptors(m4.group(3).trim()));
            }
            ocd.setDescription(m4.group(4) != null ? m4.group(4).trim() : null);
            ocd.setObsolete(m4.group(5) != null);
            if (m4.group(6) != null) {
                ocd.setSuperiorClasses(SchemaUtils.parseOIDs(m4.group(6).trim()));
            } else if (m4.group(7) != null) {
                ocd.setSuperiorClasses(SchemaUtils.parseOIDs(m4.group(7).trim()));
            }
            if (m4.group(8) != null) {
                ocd.setObjectClassType(ObjectClassType.valueOf(m4.group(8).trim()));
            }
            if (m4.group(9) != null) {
                ocd.setRequiredAttributes(SchemaUtils.parseOIDs(m4.group(9).trim()));
            } else if (m4.group(10) != null) {
                ocd.setRequiredAttributes(SchemaUtils.parseOIDs(m4.group(10).trim()));
            }
            if (m4.group(11) != null) {
                ocd.setOptionalAttributes(SchemaUtils.parseOIDs(m4.group(11).trim()));
            } else if (m4.group(12) != null) {
                ocd.setOptionalAttributes(SchemaUtils.parseOIDs(m4.group(12).trim()));
            }
            if (m4.group(13) != null) {
                ocd.setExtensions(this.parseExtensions(m4.group(13).trim()));
            }
            return ocd;
        }
    }

    public static class DefaultDefinitionFunction
    extends AbstractDefaultDefinitionFunction<ObjectClass> {
        @Override
        public ObjectClass parse(String definition) throws SchemaParseException {
            CharBuffer buffer = this.validate(definition);
            this.skipSpaces(buffer);
            ObjectClass oc = new ObjectClass(this.readUntilSpace(buffer));
            Extensions exts = new Extensions();
            block24: while (buffer.hasRemaining()) {
                this.skipSpaces(buffer);
                String token = this.readUntilSpace(buffer);
                this.skipSpaces(buffer);
                switch (token) {
                    case "NAME": {
                        oc.setNames(this.readQDStrings(buffer));
                        continue block24;
                    }
                    case "DESC": {
                        oc.setDescription(this.readQDString(buffer));
                        continue block24;
                    }
                    case "OBSOLETE": {
                        oc.setObsolete(true);
                        continue block24;
                    }
                    case "SUP": {
                        oc.setSuperiorClasses(this.readOIDs(buffer));
                        continue block24;
                    }
                    case "ABSTRACT": {
                        oc.setObjectClassType(ObjectClassType.ABSTRACT);
                        continue block24;
                    }
                    case "STRUCTURAL": {
                        oc.setObjectClassType(ObjectClassType.STRUCTURAL);
                        continue block24;
                    }
                    case "AUXILIARY": {
                        oc.setObjectClassType(ObjectClassType.AUXILIARY);
                        continue block24;
                    }
                    case "MUST": {
                        oc.setRequiredAttributes(this.readOIDs(buffer));
                        continue block24;
                    }
                    case "MAY": {
                        oc.setOptionalAttributes(this.readOIDs(buffer));
                        continue block24;
                    }
                    case "": {
                        continue block24;
                    }
                }
                if (!token.startsWith("X-")) {
                    throw new SchemaParseException("Definition '" + definition + "' contains invalid extension '" + token + "'");
                }
                this.skipSpaces(buffer);
                exts.addExtension(token, List.of(this.readQDStrings(buffer)));
            }
            if (!exts.isEmpty()) {
                oc.setExtensions(exts);
            }
            return oc;
        }
    }
}

