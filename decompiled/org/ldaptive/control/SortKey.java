/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.control;

import org.ldaptive.LdapUtils;

public class SortKey {
    private static final int HASH_CODE_SEED = 739;
    private String attributeDescription;
    private String matchingRuleId;
    private boolean reverseOrder;

    public SortKey() {
    }

    public SortKey(String attrDescription) {
        this.setAttributeDescription(attrDescription);
    }

    public SortKey(String attrDescription, String ruleId) {
        this.setAttributeDescription(attrDescription);
        this.setMatchingRuleId(ruleId);
    }

    public SortKey(String attrDescription, String ruleId, boolean reverse) {
        this.setAttributeDescription(attrDescription);
        this.setMatchingRuleId(ruleId);
        this.setReverseOrder(reverse);
    }

    public String getAttributeDescription() {
        return this.attributeDescription;
    }

    public void setAttributeDescription(String s2) {
        this.attributeDescription = s2;
    }

    public String getMatchingRuleId() {
        return this.matchingRuleId;
    }

    public void setMatchingRuleId(String s2) {
        this.matchingRuleId = s2;
    }

    public boolean getReverseOrder() {
        return this.reverseOrder;
    }

    public void setReverseOrder(boolean b) {
        this.reverseOrder = b;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof SortKey) {
            SortKey v = (SortKey)o;
            return LdapUtils.areEqual(this.attributeDescription, v.attributeDescription) && LdapUtils.areEqual(this.matchingRuleId, v.matchingRuleId) && LdapUtils.areEqual(this.reverseOrder, v.reverseOrder);
        }
        return false;
    }

    public int hashCode() {
        return LdapUtils.computeHashCode(739, this.attributeDescription, this.matchingRuleId, this.reverseOrder);
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "attributeDescription=" + this.attributeDescription + ", " + "matchingRuleId=" + this.matchingRuleId + ", " + "reverseOrder=" + this.reverseOrder + "]";
    }
}

