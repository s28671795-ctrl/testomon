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
import org.ldaptive.schema.AttributeUsage;
import org.ldaptive.schema.Extensions;
import org.ldaptive.schema.SchemaParseException;
import org.ldaptive.schema.SchemaParser;
import org.ldaptive.schema.SchemaUtils;

public class AttributeType
extends AbstractNamedSchemaElement {
    private static final int HASH_CODE_SEED = 1103;
    private final String oid;
    private String superiorType;
    private String equalityMatchingRule;
    private String orderingMatchingRule;
    private String substringMatchingRule;
    private String syntaxOID;
    private boolean singleValued;
    private boolean collective;
    private boolean noUserModification;
    private AttributeUsage usage;

    public AttributeType(String s2) {
        this.oid = s2;
    }

    public AttributeType(String oid, String[] names, String description, boolean obsolete, String superiorType, String equalityMatchingRule, String orderingMatchingRule, String substringMatchingRule, String syntaxOID, boolean singleValued, boolean collective, boolean noUserModification, AttributeUsage usage, Extensions extensions) {
        this(oid);
        this.setNames(names);
        this.setDescription(description);
        this.setObsolete(obsolete);
        this.setSuperiorType(superiorType);
        this.setEqualityMatchingRule(equalityMatchingRule);
        this.setOrderingMatchingRule(orderingMatchingRule);
        this.setSubstringMatchingRule(substringMatchingRule);
        this.setSyntaxOID(syntaxOID);
        this.setSingleValued(singleValued);
        this.setCollective(collective);
        this.setNoUserModification(noUserModification);
        this.setUsage(usage);
        this.setExtensions(extensions);
    }

    public String getOID() {
        return this.oid;
    }

    public String getSuperiorType() {
        return this.superiorType;
    }

    public void setSuperiorType(String s2) {
        this.superiorType = s2;
    }

    public String getEqualityMatchingRule() {
        return this.equalityMatchingRule;
    }

    public void setEqualityMatchingRule(String s2) {
        this.equalityMatchingRule = s2;
    }

    public String getOrderingMatchingRule() {
        return this.orderingMatchingRule;
    }

    public void setOrderingMatchingRule(String s2) {
        this.orderingMatchingRule = s2;
    }

    public String getSubstringMatchingRule() {
        return this.substringMatchingRule;
    }

    public void setSubstringMatchingRule(String s2) {
        this.substringMatchingRule = s2;
    }

    public String getSyntaxOID() {
        return this.syntaxOID;
    }

    public String getSyntaxOID(boolean withBoundCount) {
        if (!withBoundCount && this.syntaxOID != null && this.syntaxOID.contains("{") && this.syntaxOID.endsWith("}")) {
            return this.syntaxOID.substring(0, this.syntaxOID.indexOf(123));
        }
        return this.syntaxOID;
    }

    public int getSyntaxOIDBoundCount() {
        if (this.syntaxOID != null && this.syntaxOID.contains("{") && this.syntaxOID.endsWith("}")) {
            String count = this.syntaxOID.substring(this.syntaxOID.indexOf(123) + 1, this.syntaxOID.length() - 1);
            return Integer.parseInt(count);
        }
        return -1;
    }

    public void setSyntaxOID(String s2) {
        this.syntaxOID = s2;
    }

    public boolean isSingleValued() {
        return this.singleValued;
    }

    public void setSingleValued(boolean b) {
        this.singleValued = b;
    }

    public boolean isCollective() {
        return this.collective;
    }

    public void setCollective(boolean b) {
        this.collective = b;
    }

    public boolean isNoUserModification() {
        return this.noUserModification;
    }

    public void setNoUserModification(boolean b) {
        this.noUserModification = b;
    }

    public AttributeUsage getUsage() {
        return this.usage != null ? this.usage : AttributeUsage.USER_APPLICATIONS;
    }

    public void setUsage(AttributeUsage u) {
        this.usage = u;
    }

    public static AttributeType parse(String definition) throws SchemaParseException {
        return SchemaParser.parse(AttributeType.class, definition);
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
        if (this.superiorType != null) {
            sb.append("SUP ").append(this.superiorType).append(" ");
        }
        if (this.equalityMatchingRule != null) {
            sb.append("EQUALITY ").append(this.equalityMatchingRule).append(" ");
        }
        if (this.orderingMatchingRule != null) {
            sb.append("ORDERING ").append(this.orderingMatchingRule).append(" ");
        }
        if (this.substringMatchingRule != null) {
            sb.append("SUBSTR ").append(this.substringMatchingRule).append(" ");
        }
        if (this.syntaxOID != null) {
            sb.append("SYNTAX ").append(this.syntaxOID).append(" ");
        }
        if (this.singleValued) {
            sb.append("SINGLE-VALUE ");
        }
        if (this.collective) {
            sb.append("COLLECTIVE ");
        }
        if (this.noUserModification) {
            sb.append("NO-USER-MODIFICATION ");
        }
        if (this.usage != null) {
            sb.append("USAGE ").append(this.usage.getName()).append(" ");
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
        if (o instanceof AttributeType) {
            AttributeType v = (AttributeType)o;
            return LdapUtils.areEqual(this.oid, v.oid) && LdapUtils.areEqual(this.getNames(), v.getNames()) && LdapUtils.areEqual(this.getDescription(), v.getDescription()) && LdapUtils.areEqual(this.isObsolete(), v.isObsolete()) && LdapUtils.areEqual(this.superiorType, v.superiorType) && LdapUtils.areEqual(this.equalityMatchingRule, v.equalityMatchingRule) && LdapUtils.areEqual(this.orderingMatchingRule, v.orderingMatchingRule) && LdapUtils.areEqual(this.substringMatchingRule, v.substringMatchingRule) && LdapUtils.areEqual(this.syntaxOID, v.syntaxOID) && LdapUtils.areEqual(this.singleValued, v.singleValued) && LdapUtils.areEqual(this.collective, v.collective) && LdapUtils.areEqual(this.noUserModification, v.noUserModification) && LdapUtils.areEqual((Object)this.usage, (Object)v.usage) && LdapUtils.areEqual(this.getExtensions(), v.getExtensions());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(1103, new Object[]{this.oid, this.getNames(), this.getDescription(), this.isObsolete(), this.superiorType, this.equalityMatchingRule, this.orderingMatchingRule, this.substringMatchingRule, this.syntaxOID, this.singleValued, this.collective, this.noUserModification, this.usage, this.getExtensions()});
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "oid=" + this.oid + ", " + "names=" + Arrays.toString(this.getNames()) + ", " + "description=" + this.getDescription() + ", " + "obsolete=" + this.isObsolete() + ", " + "superiorType=" + this.superiorType + ", " + "equalityMatchingRule=" + this.equalityMatchingRule + ", " + "orderingMatchingRule=" + this.orderingMatchingRule + ", " + "substringMatchingRule=" + this.substringMatchingRule + ", " + "syntaxOID=" + this.syntaxOID + ", " + "singleValued=" + this.singleValued + ", " + "collective=" + this.collective + ", " + "noUserModification=" + this.noUserModification + ", " + "usage=" + (Object)((Object)this.usage) + ", " + "extensions=" + this.getExtensions() + "]";
    }

    public static class RegexDefinitionFunction
    extends AbstractRegexDefinitionFunction<AttributeType> {
        private static final Pattern DEFINITION_PATTERN = Pattern.compile("[ ]*\\([ ]*([^ ]+)[ ]*(?:NAME[ ]+(?:'([^']+)'|\\(([^\\)]+)\\)))?[ ]*(?:DESC[ ]+'([^']*)')?[ ]*(OBSOLETE)?[ ]*(?:SUP[ ]+([^ ]+))?[ ]*(?:EQUALITY[ ]+([^ ]+))?[ ]*(?:ORDERING[ ]+([^ ]+))?[ ]*(?:SUBSTR[ ]+([^ ]+))?[ ]*(?:SYNTAX[ ]+([^ ]+))?[ ]*(SINGLE-VALUE)?[ ]*(COLLECTIVE)?[ ]*(NO-USER-MODIFICATION)?[ ]*(?:USAGE[ ]+(\\p{Alpha}+))?[ ]*(?:(X-[^ ]+.*))?[ ]*\\)[ ]*");

        @Override
        public AttributeType parse(String definition) throws SchemaParseException {
            Matcher m4 = DEFINITION_PATTERN.matcher(definition);
            if (!m4.matches()) {
                throw new SchemaParseException("Invalid attribute type definition: " + definition);
            }
            AttributeType atd = new AttributeType(m4.group(1).trim());
            if (m4.group(2) != null) {
                atd.setNames(SchemaUtils.parseDescriptors(m4.group(2).trim()));
            } else if (m4.group(3) != null) {
                atd.setNames(SchemaUtils.parseDescriptors(m4.group(3).trim()));
            }
            atd.setDescription(m4.group(4) != null ? m4.group(4).trim() : null);
            atd.setObsolete(m4.group(5) != null);
            atd.setSuperiorType(m4.group(6) != null ? m4.group(6).trim() : null);
            atd.setEqualityMatchingRule(m4.group(7) != null ? m4.group(7).trim() : null);
            atd.setOrderingMatchingRule(m4.group(8) != null ? m4.group(8).trim() : null);
            atd.setSubstringMatchingRule(m4.group(9) != null ? m4.group(9).trim() : null);
            atd.setSyntaxOID(m4.group(10) != null ? m4.group(10).trim() : null);
            atd.setSingleValued(m4.group(11) != null);
            atd.setCollective(m4.group(12) != null);
            atd.setNoUserModification(m4.group(13) != null);
            if (m4.group(14) != null) {
                atd.setUsage(AttributeUsage.parse(m4.group(14).trim()));
            }
            if (m4.group(15) != null) {
                atd.setExtensions(this.parseExtensions(m4.group(15).trim()));
            }
            return atd;
        }
    }

    public static class DefaultDefinitionFunction
    extends AbstractDefaultDefinitionFunction<AttributeType> {
        @Override
        public AttributeType parse(String definition) throws SchemaParseException {
            CharBuffer buffer = this.validate(definition);
            this.skipSpaces(buffer);
            AttributeType atd = new AttributeType(this.readUntilSpace(buffer));
            Extensions exts = new Extensions();
            block30: while (buffer.hasRemaining()) {
                this.skipSpaces(buffer);
                String token = this.readUntilSpace(buffer);
                this.skipSpaces(buffer);
                switch (token) {
                    case "NAME": {
                        atd.setNames(this.readQDStrings(buffer));
                        continue block30;
                    }
                    case "DESC": {
                        atd.setDescription(this.readQDString(buffer));
                        continue block30;
                    }
                    case "OBSOLETE": {
                        atd.setObsolete(true);
                        continue block30;
                    }
                    case "SUP": {
                        atd.setSuperiorType(this.readOID(buffer));
                        continue block30;
                    }
                    case "EQUALITY": {
                        atd.setEqualityMatchingRule(this.readOID(buffer));
                        continue block30;
                    }
                    case "ORDERING": {
                        atd.setOrderingMatchingRule(this.readOID(buffer));
                        continue block30;
                    }
                    case "SUBSTR": {
                        atd.setSubstringMatchingRule(this.readOID(buffer));
                        continue block30;
                    }
                    case "SYNTAX": {
                        atd.setSyntaxOID(this.readUntilSpace(buffer));
                        continue block30;
                    }
                    case "SINGLE-VALUE": {
                        atd.setSingleValued(true);
                        continue block30;
                    }
                    case "COLLECTIVE": {
                        atd.setCollective(true);
                        continue block30;
                    }
                    case "NO-USER-MODIFICATION": {
                        atd.setNoUserModification(true);
                        continue block30;
                    }
                    case "USAGE": {
                        atd.setUsage(AttributeUsage.parse(this.readUntilSpace(buffer)));
                        continue block30;
                    }
                    case "": {
                        continue block30;
                    }
                }
                if (!token.startsWith("X-")) {
                    throw new SchemaParseException("Definition '" + definition + "' contains invalid extension '" + token + "'");
                }
                this.skipSpaces(buffer);
                exts.addExtension(token, List.of(this.readQDStrings(buffer)));
            }
            if (!exts.isEmpty()) {
                atd.setExtensions(exts);
            }
            return atd;
        }
    }
}

