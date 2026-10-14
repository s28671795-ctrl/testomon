/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.schema;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.LdapAttribute;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapException;
import org.ldaptive.ReturnAttributes;
import org.ldaptive.SearchOperation;
import org.ldaptive.SearchRequest;
import org.ldaptive.SearchResponse;
import org.ldaptive.SearchScope;
import org.ldaptive.io.LdifReader;
import org.ldaptive.schema.Schema;
import org.ldaptive.schema.transcode.AttributeTypeValueTranscoder;
import org.ldaptive.schema.transcode.DITContentRuleValueTranscoder;
import org.ldaptive.schema.transcode.DITStructureRuleValueTranscoder;
import org.ldaptive.schema.transcode.MatchingRuleUseValueTranscoder;
import org.ldaptive.schema.transcode.MatchingRuleValueTranscoder;
import org.ldaptive.schema.transcode.NameFormValueTranscoder;
import org.ldaptive.schema.transcode.ObjectClassValueTranscoder;
import org.ldaptive.schema.transcode.SyntaxValueTranscoder;

public final class SchemaFactory {
    private static final String SUBSCHEMA_SUBENTRY_ATTR_NAME = "subschemaSubentry";
    private static final String ATTRIBUTE_TYPES_ATTR_NAME = "attributeTypes";
    private static final String DIT_CONTENT_RULES_ATTR_NAME = "dITContentRules";
    private static final String DIT_STRUCTURE_RULES_ATTR_NAME = "dITStructureRules";
    private static final String LDAP_SYNTAXES_ATTR_NAME = "ldapSyntaxes";
    private static final String MATCHING_RULES_ATTR_NAME = "matchingRules";
    private static final String MATCHING_RULE_USE_ATTR_NAME = "matchingRuleUse";
    private static final String NAME_FORMS_ATTR_NAME = "nameForms";
    private static final String OBJECT_CLASS_ATTR_NAME = "objectClasses";

    private SchemaFactory() {
    }

    public static Schema createSchema(InputStream is) throws IOException {
        LdifReader reader = new LdifReader(new InputStreamReader(is));
        return SchemaFactory.createSchema(reader.read().getEntry());
    }

    public static Schema createSchema(ConnectionFactory factory2) throws LdapException {
        LdapEntry rootDSE = SchemaFactory.getLdapEntry(factory2, "", "(objectClass=*)", SUBSCHEMA_SUBENTRY_ATTR_NAME);
        String entryDn = rootDSE.getAttribute(SUBSCHEMA_SUBENTRY_ATTR_NAME).getStringValue();
        return SchemaFactory.createSchema(SchemaFactory.getLdapEntry(factory2, entryDn, "(objectClass=subSchema)", ReturnAttributes.ALL.value()));
    }

    public static Schema createSchema(ConnectionFactory factory2, String entryDn) throws LdapException {
        return SchemaFactory.createSchema(SchemaFactory.getLdapEntry(factory2, entryDn, "(objectClass=subSchema)", ReturnAttributes.ALL.value()));
    }

    public static Schema createSchema(LdapEntry schemaEntry) {
        LdapAttribute ocAttr;
        LdapAttribute nfAttr;
        LdapAttribute mruAttr;
        LdapAttribute mrAttr;
        LdapAttribute sAttr;
        LdapAttribute dsrAttr;
        LdapAttribute dcrAttr;
        if (schemaEntry == null) {
            throw new IllegalArgumentException("Schema entry cannot be null");
        }
        Schema schema = new Schema();
        LdapAttribute atAttr = schemaEntry.getAttribute(ATTRIBUTE_TYPES_ATTR_NAME);
        if (atAttr != null) {
            schema.setAttributeTypes(atAttr.getValues(new AttributeTypeValueTranscoder().decoder()));
        }
        if ((dcrAttr = schemaEntry.getAttribute(DIT_CONTENT_RULES_ATTR_NAME)) != null) {
            schema.setDitContentRules(dcrAttr.getValues(new DITContentRuleValueTranscoder().decoder()));
        }
        if ((dsrAttr = schemaEntry.getAttribute(DIT_STRUCTURE_RULES_ATTR_NAME)) != null) {
            schema.setDitStructureRules(dsrAttr.getValues(new DITStructureRuleValueTranscoder().decoder()));
        }
        if ((sAttr = schemaEntry.getAttribute(LDAP_SYNTAXES_ATTR_NAME)) != null) {
            schema.setSyntaxes(sAttr.getValues(new SyntaxValueTranscoder().decoder()));
        }
        if ((mrAttr = schemaEntry.getAttribute(MATCHING_RULES_ATTR_NAME)) != null) {
            schema.setMatchingRules(mrAttr.getValues(new MatchingRuleValueTranscoder().decoder()));
        }
        if ((mruAttr = schemaEntry.getAttribute(MATCHING_RULE_USE_ATTR_NAME)) != null) {
            schema.setMatchingRuleUses(mruAttr.getValues(new MatchingRuleUseValueTranscoder().decoder()));
        }
        if ((nfAttr = schemaEntry.getAttribute(NAME_FORMS_ATTR_NAME)) != null) {
            schema.setNameForms(nfAttr.getValues(new NameFormValueTranscoder().decoder()));
        }
        if ((ocAttr = schemaEntry.getAttribute(OBJECT_CLASS_ATTR_NAME)) != null) {
            schema.setObjectClasses(ocAttr.getValues(new ObjectClassValueTranscoder().decoder()));
        }
        return schema;
    }

    protected static LdapEntry getLdapEntry(ConnectionFactory factory2, String dn, String filter2, String ... retAttrs) throws LdapException {
        SearchOperation search = new SearchOperation(factory2);
        SearchResponse result = search.execute((SearchRequest)SearchRequest.builder().dn(dn).scope(SearchScope.OBJECT).filter(filter2).returnAttributes(retAttrs).build());
        if (!result.isSuccess()) {
            throw new LdapException("Unsuccessful search for schema: " + result);
        }
        return result.getEntry();
    }
}

