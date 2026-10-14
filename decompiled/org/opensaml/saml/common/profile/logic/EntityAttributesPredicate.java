/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.common.profile.logic;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.joda.time.DateTime;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.schema.XSAny;
import org.opensaml.core.xml.schema.XSBase64Binary;
import org.opensaml.core.xml.schema.XSBoolean;
import org.opensaml.core.xml.schema.XSDateTime;
import org.opensaml.core.xml.schema.XSInteger;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.core.xml.schema.XSURI;
import org.opensaml.saml.ext.saml2mdattr.EntityAttributes;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityAttributesPredicate
implements Predicate<EntityDescriptor> {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(EntityAttributesPredicate.class);
    private final boolean trimTags;
    private final boolean matchAll;
    @Nonnull
    @NonnullElements
    private final Collection<Candidate> candidateSet;

    public EntityAttributesPredicate(@Nonnull @NonnullElements Collection<Candidate> candidates) {
        Constraint.isNotNull(candidates, "Attribute collection cannot be null");
        this.candidateSet = new ArrayList<Candidate>(Collections2.filter(candidates, Predicates.notNull()));
        this.trimTags = true;
        this.matchAll = false;
    }

    public EntityAttributesPredicate(@Nonnull @NonnullElements Collection<Candidate> candidates, boolean trim) {
        Constraint.isNotNull(candidates, "Attribute collection cannot be null");
        this.candidateSet = new ArrayList<Candidate>(Collections2.filter(candidates, Predicates.notNull()));
        this.trimTags = trim;
        this.matchAll = false;
    }

    public EntityAttributesPredicate(@Nonnull @NonnullElements Collection<Candidate> candidates, boolean trim, boolean all) {
        Constraint.isNotNull(candidates, "Attribute collection cannot be null");
        this.candidateSet = new ArrayList<Candidate>(Collections2.filter(candidates, Predicates.notNull()));
        this.trimTags = trim;
        this.matchAll = all;
    }

    public boolean getTrimTags() {
        return this.trimTags;
    }

    @Nonnull
    @NonnullElements
    @Unmodifiable
    @NotLive
    public Collection<Candidate> getCandidates() {
        return ImmutableList.copyOf(this.candidateSet);
    }

    @Override
    public boolean apply(@Nullable EntityDescriptor input) {
        List<XMLObject> children;
        if (input == null) {
            return false;
        }
        ArrayList<Attribute> entityAttributes = null;
        Extensions exts = input.getExtensions();
        if (exts != null && !(children = exts.getUnknownXMLObjects(EntityAttributes.DEFAULT_ELEMENT_NAME)).isEmpty() && children.get(0) instanceof EntityAttributes) {
            if (entityAttributes == null) {
                entityAttributes = new ArrayList<Attribute>();
            }
            entityAttributes.addAll(((EntityAttributes)children.get(0)).getAttributes());
        }
        for (EntitiesDescriptor group = (EntitiesDescriptor)input.getParent(); group != null; group = (EntitiesDescriptor)group.getParent()) {
            List<XMLObject> children2;
            exts = group.getExtensions();
            if (exts == null || (children2 = exts.getUnknownXMLObjects(EntityAttributes.DEFAULT_ELEMENT_NAME)).isEmpty() || !(children2.get(0) instanceof EntityAttributes)) continue;
            if (entityAttributes == null) {
                entityAttributes = new ArrayList();
            }
            entityAttributes.addAll(((EntityAttributes)children2.get(0)).getAttributes());
        }
        if (entityAttributes == null || entityAttributes.isEmpty()) {
            this.log.debug("no EntityAttributes extension found for {}", (Object)input.getEntityID());
            return false;
        }
        EntityAttributesMatcher matcher = new EntityAttributesMatcher((Collection<Attribute>)entityAttributes);
        if (this.matchAll) {
            return Iterables.all(this.candidateSet, matcher);
        }
        return Iterables.tryFind(this.candidateSet, matcher).isPresent();
    }

    private class EntityAttributesMatcher
    implements Predicate<Candidate> {
        @Nonnull
        private final Logger log = LoggerFactory.getLogger(EntityAttributesPredicate.class);
        private final Collection<Attribute> attributes;

        public EntityAttributesMatcher(Collection<Attribute> attrs) {
            this.attributes = Constraint.isNotNull(attrs, "Extension attributes cannot be null");
        }

        @Override
        public boolean apply(@Nonnull Candidate input) {
            List tagvals = input.values;
            List tagexps = input.regexps;
            boolean[] valflags = new boolean[tagvals.size()];
            boolean[] expflags = new boolean[tagexps.size()];
            for (Attribute a : this.attributes) {
                int tagindex;
                if (a.getName() == null || !a.getName().equals(input.getName()) || input.getNameFormat() != null && !input.getNameFormat().equals(a.getNameFormat())) continue;
                block1: for (tagindex = 0; tagindex < tagvals.size(); ++tagindex) {
                    String tagvalstr = (String)tagvals.get(tagindex);
                    List<XMLObject> cvals = a.getAttributeValues();
                    for (XMLObject cval : cvals) {
                        String cvalstr = this.xmlObjectToString(cval);
                        if (tagvalstr == null || cvalstr == null) continue;
                        if (tagvalstr.equals(cvalstr)) {
                            valflags[tagindex] = true;
                            continue block1;
                        }
                        if (!EntityAttributesPredicate.this.trimTags || !tagvalstr.equals(cvalstr.trim())) continue;
                        valflags[tagindex] = true;
                        continue block1;
                    }
                }
                block3: for (tagindex = 0; tagindex < tagexps.size(); ++tagindex) {
                    List<XMLObject> cvals = a.getAttributeValues();
                    for (XMLObject cval : cvals) {
                        String cvalstr = this.xmlObjectToString(cval);
                        if (tagexps.get(tagindex) == null || cvalstr == null || !((Pattern)tagexps.get(tagindex)).matcher(cvalstr).matches()) continue;
                        expflags[tagindex] = true;
                        continue block3;
                    }
                }
            }
            for (Object flag : (Object)valflags) {
                if (flag != false) continue;
                return false;
            }
            for (Object flag : (Object)expflags) {
                if (flag != false) continue;
                return false;
            }
            return true;
        }

        @Nullable
        private String xmlObjectToString(@Nonnull XMLObject object) {
            XSAny wc;
            String toMatch = null;
            if (object instanceof XSString) {
                toMatch = ((XSString)object).getValue();
            } else if (object instanceof XSURI) {
                toMatch = ((XSURI)object).getValue();
            } else if (object instanceof XSBoolean) {
                toMatch = ((XSBoolean)object).getValue().getValue() != false ? "1" : "0";
            } else if (object instanceof XSInteger) {
                toMatch = ((XSInteger)object).getValue().toString();
            } else if (object instanceof XSDateTime) {
                DateTime dt = ((XSDateTime)object).getValue();
                if (dt != null) {
                    toMatch = ((XSDateTime)object).getDateTimeFormatter().print(dt);
                }
            } else if (object instanceof XSBase64Binary) {
                toMatch = ((XSBase64Binary)object).getValue();
            } else if (object instanceof XSAny && (wc = (XSAny)object).getUnknownAttributes().isEmpty() && wc.getUnknownXMLObjects().isEmpty()) {
                toMatch = wc.getTextContent();
            }
            if (toMatch != null) {
                return toMatch;
            }
            this.log.warn("Unrecognized XMLObject type ({}), unable to convert to a string for comparison", (Object)object.getClass().getName());
            return null;
        }
    }

    public static class Candidate {
        @Nonnull
        @NotEmpty
        private final String nam;
        @Nullable
        private final String nameFormat;
        @Nonnull
        @NonnullElements
        private List<String> values;
        @Nonnull
        @NonnullElements
        private List<Pattern> regexps;

        public Candidate(@Nonnull @NotEmpty String name) {
            this.nam = Constraint.isNotNull(StringSupport.trimOrNull(name), "Attribute Name cannot be null or empty");
            this.nameFormat = null;
            this.values = Collections.emptyList();
            this.regexps = Collections.emptyList();
        }

        public Candidate(@Nonnull @NotEmpty String name, @Nullable String format) {
            this.nam = Constraint.isNotNull(StringSupport.trimOrNull(name), "Attribute Name cannot be null or empty");
            this.nameFormat = "urn:oasis:names:tc:SAML:2.0:attrname-format:unspecified".equals(format) ? null : StringSupport.trimOrNull(format);
            this.values = Collections.emptyList();
            this.regexps = Collections.emptyList();
        }

        @Nonnull
        @NotEmpty
        public String getName() {
            return this.nam;
        }

        @Nullable
        public String getNameFormat() {
            return this.nameFormat;
        }

        @Nonnull
        @NonnullElements
        @Unmodifiable
        @NotLive
        public List<String> getValues() {
            return ImmutableList.copyOf(this.values);
        }

        public void setValues(@Nonnull @NonnullElements Collection<String> vals) {
            Constraint.isNotNull(vals, "Values collection cannot be null");
            this.values = new ArrayList<String>(vals.size());
            for (String value : vals) {
                if (value == null) continue;
                this.values.add(value);
            }
        }

        @Nonnull
        @NonnullElements
        @Unmodifiable
        @NotLive
        public List<Pattern> getRegexps() {
            return ImmutableList.copyOf(this.regexps);
        }

        public void setRegexps(@Nonnull @NonnullElements Collection<Pattern> exps) {
            Constraint.isNotNull(exps, "Regular expressions collection cannot be null");
            this.regexps = new ArrayList<Pattern>(Collections2.filter(exps, Predicates.notNull()));
        }
    }
}

