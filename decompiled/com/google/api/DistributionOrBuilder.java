/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.api.Distribution;
import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface DistributionOrBuilder
extends MessageOrBuilder {
    public long getCount();

    public double getMean();

    public double getSumOfSquaredDeviation();

    public boolean hasRange();

    public Distribution.Range getRange();

    public Distribution.RangeOrBuilder getRangeOrBuilder();

    public boolean hasBucketOptions();

    public Distribution.BucketOptions getBucketOptions();

    public Distribution.BucketOptionsOrBuilder getBucketOptionsOrBuilder();

    public List<Long> getBucketCountsList();

    public int getBucketCountsCount();

    public long getBucketCounts(int var1);

    public List<Distribution.Exemplar> getExemplarsList();

    public Distribution.Exemplar getExemplars(int var1);

    public int getExemplarsCount();

    public List<? extends Distribution.ExemplarOrBuilder> getExemplarsOrBuilderList();

    public Distribution.ExemplarOrBuilder getExemplarsOrBuilder(int var1);
}

