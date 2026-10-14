/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.ldaptive.AbstractMessage;
import org.ldaptive.AttributeModification;
import org.ldaptive.LdapAttribute;
import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.AbstractParseHandler;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.DERParser;
import org.ldaptive.asn1.DERPath;
import org.ldaptive.asn1.OctetStringType;

public class LdapEntry
extends AbstractMessage {
    public static final int PROTOCOL_OP = 4;
    private static final int HASH_CODE_SEED = 10303;
    private static final DERPath LDAP_DN_PATH = new DERPath("/SEQ/APP(4)/OCTSTR[0]");
    private static final DERPath ATTRIBUTES_PATH = new DERPath("/SEQ/APP(4)/SEQ/SEQ");
    private String ldapDn;
    private Map<String, LdapAttribute> attributes = new LinkedHashMap<String, LdapAttribute>();

    public LdapEntry() {
    }

    public LdapEntry(DERBuffer buffer) {
        DERParser parser = new DERParser();
        parser.registerHandler(AbstractMessage.MessageIDHandler.PATH, new AbstractMessage.MessageIDHandler(this));
        parser.registerHandler(LDAP_DN_PATH, new LdapDnHandler(this));
        parser.registerHandler(ATTRIBUTES_PATH, new AttributesHandler(this));
        parser.registerHandler(AbstractMessage.ControlsHandler.PATH, new AbstractMessage.ControlsHandler(this));
        parser.parse(buffer);
    }

    public String getDn() {
        return this.ldapDn;
    }

    public void setDn(String dn) {
        this.ldapDn = dn;
    }

    public Collection<LdapAttribute> getAttributes() {
        return this.attributes.values();
    }

    public LdapAttribute getAttribute() {
        if (this.attributes.isEmpty()) {
            return null;
        }
        return this.attributes.values().iterator().next();
    }

    public LdapAttribute getAttribute(String name) {
        if (name != null) {
            return this.attributes.get(name.toLowerCase());
        }
        return null;
    }

    public String[] getAttributeNames() {
        return (String[])this.attributes.values().stream().map(LdapAttribute::getName).toArray(String[]::new);
    }

    public void addAttributes(LdapAttribute ... attrs) {
        for (LdapAttribute a : attrs) {
            this.attributes.put(a.getName().toLowerCase(), a);
        }
    }

    public void addAttributes(Collection<LdapAttribute> attrs) {
        attrs.forEach(a -> this.attributes.put(a.getName().toLowerCase(), (LdapAttribute)a));
    }

    public void removeAttribute(String name) {
        this.attributes.remove(name.toLowerCase());
    }

    public void removeAttributes(LdapAttribute ... attrs) {
        for (LdapAttribute a : attrs) {
            this.attributes.remove(a.getName().toLowerCase());
        }
    }

    public void removeAttributes(Collection<LdapAttribute> attrs) {
        attrs.forEach(a -> this.attributes.remove(a.getName().toLowerCase()));
    }

    public int size() {
        return this.attributes.size();
    }

    public void clear() {
        this.attributes.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof LdapEntry && super.equals(o)) {
            LdapEntry v = (LdapEntry)o;
            return LdapUtils.areEqual(this.ldapDn != null ? this.ldapDn.toLowerCase() : null, v.ldapDn != null ? v.ldapDn.toLowerCase() : null) && LdapUtils.areEqual(this.attributes, v.attributes);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(10303, this.getMessageID(), this.getControls(), this.ldapDn != null ? this.ldapDn.toLowerCase() : null, this.attributes);
    }

    @Override
    public String toString() {
        return super.toString() + ", " + "dn=" + this.ldapDn + ", " + "attributes=" + (this.attributes != null ? this.attributes.values() : null);
    }

    public static LdapEntry sort(LdapEntry le) {
        LdapEntry sorted2 = new LdapEntry();
        sorted2.copyValues(le);
        sorted2.setDn(le.getDn());
        sorted2.addAttributes(le.getAttributes().stream().map(LdapAttribute::sort).sorted(Comparator.comparing(o -> o.getName(false))).collect(Collectors.toCollection(LinkedHashSet::new)));
        return sorted2;
    }

    public static AttributeModification[] computeModifications(LdapEntry source2, LdapEntry target) {
        AttributeModification mod;
        ArrayList<AttributeModification> mods = new ArrayList<AttributeModification>();
        for (LdapAttribute sourceAttr : source2.getAttributes()) {
            LdapAttribute targetAttr = target.getAttribute(sourceAttr.getName());
            if (targetAttr == null) {
                mod = new AttributeModification(AttributeModification.Type.ADD, sourceAttr);
                mods.add(mod);
                continue;
            }
            if (targetAttr.equals(sourceAttr)) continue;
            mod = new AttributeModification(AttributeModification.Type.REPLACE, sourceAttr);
            mods.add(mod);
        }
        for (LdapAttribute targetAttr : target.getAttributes()) {
            LdapAttribute sourceAttr = source2.getAttribute(targetAttr.getName());
            if (sourceAttr != null) continue;
            mod = new AttributeModification(AttributeModification.Type.DELETE, targetAttr);
            mods.add(mod);
        }
        return (AttributeModification[])mods.toArray(AttributeModification[]::new);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
    extends AbstractMessage.AbstractBuilder<Builder, LdapEntry> {
        protected Builder() {
            super(new LdapEntry());
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder dn(String dn) {
            ((LdapEntry)this.object).setDn(dn);
            return this;
        }

        public Builder attributes(LdapAttribute ... attrs) {
            ((LdapEntry)this.object).addAttributes(attrs);
            return this;
        }

        public Builder attributes(Collection<LdapAttribute> attrs) {
            ((LdapEntry)this.object).addAttributes(attrs);
            return this;
        }
    }

    protected static class AttributeParser {
        private static final DERPath NAME_PATH = new DERPath("/OCTSTR");
        private static final DERPath VALUES_PATH = new DERPath("/SET/OCTSTR");
        private final DERParser parser = new DERParser();
        private String name;
        private List<ByteBuffer> values = new ArrayList<ByteBuffer>();

        public AttributeParser() {
            this.parser.registerHandler(NAME_PATH, (p, e) -> {
                this.name = OctetStringType.decode(e);
            });
            this.parser.registerHandler(VALUES_PATH, (p, e) -> this.values.add(ByteBuffer.wrap(e.getRemainingBytes())));
        }

        public void parse(DERBuffer buffer) {
            this.parser.parse(buffer);
        }

        public Optional<String> getName() {
            return Optional.ofNullable(this.name);
        }

        public Optional<List<ByteBuffer>> getValues() {
            return this.values.isEmpty() ? Optional.empty() : Optional.of(this.values);
        }
    }

    protected static class AttributesHandler
    extends AbstractParseHandler<LdapEntry> {
        AttributesHandler(LdapEntry response) {
            super(response);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            AttributeParser p = new AttributeParser();
            p.parse(encoded);
            if (p.getName().isEmpty()) {
                throw new IllegalArgumentException("Could not parse attribute");
            }
            if (p.getValues().isEmpty()) {
                ((LdapEntry)this.getObject()).addAttributes(LdapAttribute.builder().name(p.getName().get()).build());
            } else {
                ((LdapEntry)this.getObject()).addAttributes(LdapAttribute.builder().name(p.getName().get()).bufferValues((Collection<ByteBuffer>)p.getValues().get()).build());
            }
        }
    }

    protected static class LdapDnHandler
    extends AbstractParseHandler<LdapEntry> {
        LdapDnHandler(LdapEntry response) {
            super(response);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((LdapEntry)this.getObject()).setDn(OctetStringType.decode(encoded));
        }
    }
}

