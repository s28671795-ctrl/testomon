/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.control.util;

import org.ldaptive.SearchResponse;
import org.ldaptive.control.VirtualListViewRequestControl;
import org.ldaptive.control.VirtualListViewResponseControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VirtualListViewParams {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final int beforeCount;
    private final int afterCount;
    private final int targetOffset;
    private final String assertionValue;

    public VirtualListViewParams(int offset, int before, int after) {
        this.targetOffset = offset;
        this.beforeCount = before;
        this.afterCount = after;
        this.assertionValue = null;
    }

    public VirtualListViewParams(String assertion, int before, int after) {
        this.assertionValue = assertion;
        this.beforeCount = before;
        this.afterCount = after;
        this.targetOffset = 0;
    }

    public int getBeforeCount() {
        return this.beforeCount;
    }

    public int getAfterCount() {
        return this.afterCount;
    }

    public int getTargetOffset() {
        return this.targetOffset;
    }

    public String getAssertionValue() {
        return this.assertionValue;
    }

    public VirtualListViewRequestControl createRequestControl(boolean critical) {
        if (this.assertionValue != null) {
            return new VirtualListViewRequestControl(this.assertionValue, this.beforeCount, this.afterCount, critical);
        }
        return new VirtualListViewRequestControl(this.targetOffset, this.beforeCount, this.afterCount, critical);
    }

    public VirtualListViewRequestControl createRequestControl(SearchResponse result, boolean critical) {
        VirtualListViewRequestControl control = this.createRequestControl(critical);
        VirtualListViewResponseControl responseControl = (VirtualListViewResponseControl)result.getControl("2.16.840.1.113730.3.4.10");
        if (responseControl != null) {
            if (this.assertionValue == null) {
                control.setContentCount(responseControl.getContentCount());
            }
            control.setContextID(responseControl.getContextID());
        }
        return control;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[").append(this.getClass().getName()).append("@").append(this.hashCode()).append("::");
        if (this.assertionValue != null) {
            sb.append("assertionValue=").append(this.assertionValue).append(", ");
        } else {
            sb.append("targetOffset=").append(this.targetOffset).append(", ");
        }
        sb.append("beforeCount=").append(this.beforeCount).append(", ").append("afterCount=").append(this.afterCount).append("]");
        return sb.toString();
    }
}

