/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.staxutils;

public class DocumentDepthProperties {
    public static final String TOTAL_ELEMENT_COUNT = "depthTotalElementCountThreshold";
    public static final String INNER_ELEMENT_COUNT = "depthInnerElementCountThreshold";
    public static final String INNER_ELEMENT_LEVEL = "depthInnerElementLevelThreshold";
    private int elementCountThreshold = -1;
    private int innerElementLevelThreshold = -1;
    private int innerElementCountThreshold = -1;

    public DocumentDepthProperties() {
    }

    public DocumentDepthProperties(int elementCountThreshold, int innerElementLevelThreshold, int innerElementCountThreshold) {
        this.elementCountThreshold = elementCountThreshold;
        this.innerElementLevelThreshold = innerElementLevelThreshold;
        this.innerElementCountThreshold = innerElementCountThreshold;
    }

    public boolean isEffective() {
        return this.elementCountThreshold != -1 || this.innerElementLevelThreshold != -1 || this.innerElementCountThreshold != -1;
    }

    public void setElementCountThreshold(int elementCountThreshold) {
        this.elementCountThreshold = elementCountThreshold;
    }

    public int getElementCountThreshold() {
        return this.elementCountThreshold;
    }

    public void setInnerElementLevelThreshold(int innerElementLevelThreshold) {
        this.innerElementLevelThreshold = innerElementLevelThreshold;
    }

    public int getInnerElementLevelThreshold() {
        return this.innerElementLevelThreshold;
    }

    public void setInnerElementCountThreshold(int innerElementCountThreshold) {
        this.innerElementCountThreshold = innerElementCountThreshold;
    }

    public int getInnerElementCountThreshold() {
        return this.innerElementCountThreshold;
    }
}

