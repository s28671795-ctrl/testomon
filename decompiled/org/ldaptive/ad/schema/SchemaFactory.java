/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ad.schema;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashSet;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.LdapAttribute;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapException;
import org.ldaptive.ReturnAttributes;
import org.ldaptive.SearchRequest;
import org.ldaptive.SearchResponse;
import org.ldaptive.control.util.PagedResultsClient;
import org.ldaptive.io.LdifReader;
import org.ldaptive.schema.AttributeType;
import org.ldaptive.schema.ObjectClass;
import org.ldaptive.schema.ObjectClassType;
import org.ldaptive.schema.Schema;

public final class SchemaFactory {
    private SchemaFactory() {
    }

    public static Schema createSchema(InputStream is) throws IOException {
        LdifReader reader = new LdifReader(new InputStreamReader(is));
        return SchemaFactory.createSchema(reader.read());
    }

    public static Schema createSchema(ConnectionFactory factory2, String entryDn) throws LdapException {
        return SchemaFactory.createSchema(SchemaFactory.getSearchResult(factory2, entryDn, "(objectClass=*)", ReturnAttributes.ALL.value()));
    }

    public static Schema createSchema(SearchResponse schemaResult) {
        HashSet<AttributeType> attributeTypes = new HashSet<AttributeType>();
        HashSet<ObjectClass> objectClasses = new HashSet<ObjectClass>();
        for (LdapEntry entry : schemaResult.getEntries()) {
            LdapAttribute la = entry.getAttribute("objectClass");
            if (la != null && la.getStringValues().contains("attributeSchema")) {
                attributeTypes.add(SchemaFactory.createAttributeType(entry));
            }
            if (la == null || !la.getStringValues().contains("classSchema")) continue;
            objectClasses.add(SchemaFactory.createObjectClass(entry));
        }
        Schema schema = new Schema();
        schema.setAttributeTypes(attributeTypes);
        schema.setObjectClasses(objectClasses);
        return schema;
    }

    protected static SearchResponse getSearchResult(ConnectionFactory factory2, String dn, String filter2, String[] retAttrs) throws LdapException {
        PagedResultsClient client = new PagedResultsClient(factory2, 100);
        return client.executeToCompletion((SearchRequest)SearchRequest.builder().dn(dn).filter(filter2).returnAttributes(retAttrs).build());
    }

    protected static AttributeType createAttributeType(LdapEntry entry) {
        LdapAttribute la = entry.getAttribute("objectClass");
        if (la == null || !la.getStringValues().contains("attributeSchema")) {
            throw new IllegalArgumentException("Entry is not an attribute schema");
        }
        return new AttributeType(SchemaFactory.getAttributeValue(entry, "attributeID"), SchemaFactory.getAttributeValues(entry, "lDAPDisplayName", "adminDisplayName", "name"), SchemaFactory.getAttributeValue(entry, "adminDescription"), false, null, null, null, null, SchemaFactory.getAttributeValue(entry, "attributeSyntax"), Boolean.valueOf(SchemaFactory.getAttributeValue(entry, "isSingleValued")), false, false, null, null);
    }

    protected static ObjectClass createObjectClass(LdapEntry entry) {
        LdapAttribute la = entry.getAttribute("objectClass");
        if (la == null || !la.getStringValues().contains("classSchema")) {
            throw new IllegalArgumentException("Entry is not an object class");
        }
        ObjectClassType ocType = null;
        String ocCategory = SchemaFactory.getAttributeValue(entry, "objectClassCategory");
        if (ocCategory != null) {
            for (ObjectClassType type : ObjectClassType.values()) {
                if (type.ordinal() != Integer.parseInt(ocCategory)) continue;
                ocType = type;
                break;
            }
        }
        return new ObjectClass(SchemaFactory.getAttributeValue(entry, "governsID"), SchemaFactory.getAttributeValues(entry, "lDAPDisplayName", "adminDisplayName", "name"), SchemaFactory.getAttributeValue(entry, "adminDescription"), false, SchemaFactory.getAttributeValues(entry, "possSuperiors", "systemPossSuperiors"), ocType, SchemaFactory.getAttributeValues(entry, "mustContain", "systemMustContain"), SchemaFactory.getAttributeValues(entry, "mayContain", "systemMayContain"), null);
    }

    private static String getAttributeValue(LdapEntry entry, String ... names) {
        String value = null;
        for (String name : names) {
            LdapAttribute la = entry.getAttribute(name);
            if (la == null) continue;
            value = la.getStringValue();
            break;
        }
        return value;
    }

    private static String[] getAttributeValues(LdapEntry entry, String ... names) {
        Collection<String> values2 = null;
        for (String name : names) {
            LdapAttribute la = entry.getAttribute(name);
            if (la == null) continue;
            values2 = la.getStringValues();
            break;
        }
        return values2 != null ? values2.toArray(new String[0]) : null;
    }
}

