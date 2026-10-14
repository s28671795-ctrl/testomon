/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.schema;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.ldaptive.LdapUtils;
import org.ldaptive.schema.AttributeType;
import org.ldaptive.schema.DITContentRule;
import org.ldaptive.schema.DITStructureRule;
import org.ldaptive.schema.MatchingRule;
import org.ldaptive.schema.MatchingRuleUse;
import org.ldaptive.schema.NameForm;
import org.ldaptive.schema.ObjectClass;
import org.ldaptive.schema.Syntax;

public class Schema {
    private static final int HASH_CODE_SEED = 1181;
    private Collection<AttributeType> attributeTypes = Collections.emptySet();
    private Collection<DITContentRule> ditContentRules = Collections.emptySet();
    private Collection<DITStructureRule> ditStructureRules = Collections.emptySet();
    private Collection<Syntax> syntaxes = Collections.emptySet();
    private Collection<MatchingRule> matchingRules = Collections.emptySet();
    private Collection<MatchingRuleUse> matchingRuleUses = Collections.emptySet();
    private Collection<NameForm> nameForms = Collections.emptySet();
    private Collection<ObjectClass> objectClasses = Collections.emptySet();

    public Schema() {
    }

    public Schema(Collection<AttributeType> attributeTypes, Collection<DITContentRule> ditContentRules, Collection<DITStructureRule> ditStructureRules, Collection<Syntax> syntaxes, Collection<MatchingRule> matchingRules, Collection<MatchingRuleUse> matchingRuleUses, Collection<NameForm> nameForms, Collection<ObjectClass> objectClasses) {
        this.setAttributeTypes(attributeTypes);
        this.setDitContentRules(ditContentRules);
        this.setDitStructureRules(ditStructureRules);
        this.setSyntaxes(syntaxes);
        this.setMatchingRules(matchingRules);
        this.setMatchingRuleUses(matchingRuleUses);
        this.setNameForms(nameForms);
        this.setObjectClasses(objectClasses);
    }

    public Collection<AttributeType> getAttributeTypes() {
        return this.attributeTypes;
    }

    public AttributeType getAttributeType(String name) {
        for (AttributeType at : this.attributeTypes) {
            if (!at.getOID().equals(name) && !at.hasName(name)) continue;
            return at;
        }
        return null;
    }

    public void setAttributeTypes(Collection<AttributeType> c) {
        this.attributeTypes = c;
    }

    public String[] getBinaryAttributeNames() {
        ArrayList binaryAttrs = new ArrayList();
        for (AttributeType type : this.attributeTypes) {
            boolean isBinary = false;
            String syntaxOid = type.getSyntaxOID(false);
            if ("1.3.6.1.4.1.1466.115.121.1.5".equals(syntaxOid)) {
                isBinary = true;
            } else {
                Syntax syntax = this.getSyntax(syntaxOid);
                if (Syntax.containsBooleanExtension(syntax, "X-NOT-HUMAN-READABLE")) {
                    isBinary = true;
                }
            }
            if (!isBinary) continue;
            Collections.addAll(binaryAttrs, type.getNames());
        }
        return binaryAttrs.toArray(new String[0]);
    }

    public Collection<DITContentRule> getDitContentRules() {
        return this.ditContentRules;
    }

    public DITContentRule getDITContentRule(String name) {
        for (DITContentRule rule : this.ditContentRules) {
            if (!rule.getOID().equals(name) && !rule.hasName(name)) continue;
            return rule;
        }
        return null;
    }

    public void setDitContentRules(Collection<DITContentRule> c) {
        this.ditContentRules = c;
    }

    public Collection<DITStructureRule> getDitStructureRules() {
        return this.ditStructureRules;
    }

    public DITStructureRule getDITStructureRule(int id) {
        for (DITStructureRule rule : this.ditStructureRules) {
            if (rule.getID() != id) continue;
            return rule;
        }
        return null;
    }

    public DITStructureRule getDITStructureRule(String name) {
        for (DITStructureRule rule : this.ditStructureRules) {
            if (!rule.hasName(name)) continue;
            return rule;
        }
        return null;
    }

    public void setDitStructureRules(Collection<DITStructureRule> c) {
        this.ditStructureRules = c;
    }

    public Collection<Syntax> getSyntaxes() {
        return this.syntaxes;
    }

    public Syntax getSyntax(String oid) {
        for (Syntax syntax : this.syntaxes) {
            if (!syntax.getOID().equals(oid)) continue;
            return syntax;
        }
        return null;
    }

    public void setSyntaxes(Collection<Syntax> c) {
        this.syntaxes = c;
    }

    public Collection<MatchingRule> getMatchingRules() {
        return this.matchingRules;
    }

    public MatchingRule getMatchingRule(String name) {
        for (MatchingRule rule : this.matchingRules) {
            if (!rule.getOID().equals(name) && !rule.hasName(name)) continue;
            return rule;
        }
        return null;
    }

    public void setMatchingRules(Collection<MatchingRule> c) {
        this.matchingRules = c;
    }

    public Collection<MatchingRuleUse> getMatchingRuleUses() {
        return this.matchingRuleUses;
    }

    public MatchingRuleUse getMatchingRuleUse(String name) {
        for (MatchingRuleUse rule : this.matchingRuleUses) {
            if (!rule.getOID().equals(name) && !rule.hasName(name)) continue;
            return rule;
        }
        return null;
    }

    public void setMatchingRuleUses(Collection<MatchingRuleUse> c) {
        this.matchingRuleUses = c;
    }

    public Collection<NameForm> getNameForms() {
        return this.nameForms;
    }

    public NameForm getNameForm(String name) {
        for (NameForm form : this.nameForms) {
            if (!form.getOID().equals(name) && !form.hasName(name)) continue;
            return form;
        }
        return null;
    }

    public void setNameForms(Collection<NameForm> c) {
        this.nameForms = c;
    }

    public Collection<ObjectClass> getObjectClasses() {
        return this.objectClasses;
    }

    public ObjectClass getObjectClass(String name) {
        for (ObjectClass oc : this.objectClasses) {
            if (!oc.getOID().equals(name) && !oc.hasName(name)) continue;
            return oc;
        }
        return null;
    }

    public void setObjectClasses(Collection<ObjectClass> c) {
        this.objectClasses = c;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof Schema) {
            Schema v = (Schema)o;
            return LdapUtils.areEqual(this.attributeTypes, v.attributeTypes) && LdapUtils.areEqual(this.ditContentRules, v.ditContentRules) && LdapUtils.areEqual(this.ditStructureRules, v.ditStructureRules) && LdapUtils.areEqual(this.syntaxes, v.syntaxes) && LdapUtils.areEqual(this.matchingRules, v.matchingRules) && LdapUtils.areEqual(this.matchingRuleUses, v.matchingRuleUses) && LdapUtils.areEqual(this.nameForms, v.nameForms) && LdapUtils.areEqual(this.objectClasses, v.objectClasses);
        }
        return false;
    }

    public int hashCode() {
        return LdapUtils.computeHashCode(1181, this.attributeTypes, this.ditContentRules, this.ditStructureRules, this.syntaxes, this.matchingRules, this.matchingRuleUses, this.nameForms, this.objectClasses);
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "attributeTypes=" + this.attributeTypes + ", " + "ditContentRules=" + this.ditContentRules + ", " + "ditStructureRules=" + this.ditStructureRules + ", " + "syntaxes=" + this.syntaxes + ", " + "matchingRules=" + this.matchingRules + ", " + "matchingRuleUses=" + this.matchingRuleUses + ", " + "nameForms=" + this.nameForms + ", " + "objectClasses=" + this.objectClasses + "]";
    }
}

