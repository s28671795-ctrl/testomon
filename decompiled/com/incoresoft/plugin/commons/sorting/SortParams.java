/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.sorting;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.plugin.commons.sorting.SortFields;
import com.incoresoft.plugin.commons.sorting.SortOrders;

public class SortParams {
    @JsonProperty(value="sort_field")
    @JSONField(name="sort_field")
    private SortFields sortField;
    @JsonProperty(value="sort_order")
    @JSONField(name="sort_order")
    private SortOrders sortOrder;

    public SortFields getSortField() {
        return this.sortField;
    }

    public SortOrders getSortOrder() {
        return this.sortOrder;
    }

    @JsonProperty(value="sort_field")
    public void setSortField(SortFields sortField) {
        this.sortField = sortField;
    }

    @JsonProperty(value="sort_order")
    public void setSortOrder(SortOrders sortOrder) {
        this.sortOrder = sortOrder;
    }
}

