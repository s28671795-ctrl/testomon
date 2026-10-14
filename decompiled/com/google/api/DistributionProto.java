/*
 * Decompiled with CFR 0.152.
 */
package com.google.api;

import com.google.protobuf.AnyProto;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.TimestampProto;

public final class DistributionProto {
    static final Descriptors.Descriptor internal_static_google_api_Distribution_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_Distribution_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_api_Distribution_Range_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_Distribution_Range_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_api_Distribution_BucketOptions_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_Distribution_BucketOptions_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_api_Distribution_BucketOptions_Linear_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_Distribution_BucketOptions_Linear_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_api_Distribution_BucketOptions_Exponential_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_Distribution_BucketOptions_Exponential_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_api_Distribution_BucketOptions_Explicit_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_Distribution_BucketOptions_Explicit_fieldAccessorTable;
    static final Descriptors.Descriptor internal_static_google_api_Distribution_Exemplar_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_google_api_Distribution_Exemplar_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private DistributionProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        DistributionProto.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u001dgoogle/api/distribution.proto\u0012\ngoogle.api\u001a\u0019google/protobuf/any.proto\u001a\u001fgoogle/protobuf/timestamp.proto\"\u00d9\u0006\n\fDistribution\u0012\r\n\u0005count\u0018\u0001 \u0001(\u0003\u0012\f\n\u0004mean\u0018\u0002 \u0001(\u0001\u0012 \n\u0018sum_of_squared_deviation\u0018\u0003 \u0001(\u0001\u0012-\n\u0005range\u0018\u0004 \u0001(\u000b2\u001e.google.api.Distribution.Range\u0012>\n\u000ebucket_options\u0018\u0006 \u0001(\u000b2&.google.api.Distribution.BucketOptions\u0012\u0015\n\rbucket_counts\u0018\u0007 \u0003(\u0003\u00124\n\texemplars\u0018\n \u0003(\u000b2!.google.api.Distribution.Exemplar\u001a!\n\u0005Range\u0012\u000b\n\u0003min\u0018\u0001 \u0001(\u0001\u0012\u000b\n\u0003max\u0018\u0002 \u0001(\u0001\u001a\u00b5\u0003\n\rBucketOptions\u0012G\n\u000elinear_buckets\u0018\u0001 \u0001(\u000b2-.google.api.Distribution.BucketOptions.LinearH\u0000\u0012Q\n\u0013exponential_buckets\u0018\u0002 \u0001(\u000b22.google.api.Distribution.BucketOptions.ExponentialH\u0000\u0012K\n\u0010explicit_buckets\u0018\u0003 \u0001(\u000b2/.google.api.Distribution.BucketOptions.ExplicitH\u0000\u001aC\n\u0006Linear\u0012\u001a\n\u0012num_finite_buckets\u0018\u0001 \u0001(\u0005\u0012\r\n\u0005width\u0018\u0002 \u0001(\u0001\u0012\u000e\n\u0006offset\u0018\u0003 \u0001(\u0001\u001aO\n\u000bExponential\u0012\u001a\n\u0012num_finite_buckets\u0018\u0001 \u0001(\u0005\u0012\u0015\n\rgrowth_factor\u0018\u0002 \u0001(\u0001\u0012\r\n\u0005scale\u0018\u0003 \u0001(\u0001\u001a\u001a\n\bExplicit\u0012\u000e\n\u0006bounds\u0018\u0001 \u0003(\u0001B\t\n\u0007options\u001as\n\bExemplar\u0012\r\n\u0005value\u0018\u0001 \u0001(\u0001\u0012-\n\ttimestamp\u0018\u0002 \u0001(\u000b2\u001a.google.protobuf.Timestamp\u0012)\n\u000battachments\u0018\u0003 \u0003(\u000b2\u0014.google.protobuf.AnyBq\n\u000ecom.google.apiB\u0011DistributionProtoP\u0001ZCgoogle.golang.org/genproto/googleapis/api/distribution;distribution\u00a2\u0002\u0004GAPIb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[]{AnyProto.getDescriptor(), TimestampProto.getDescriptor()});
        internal_static_google_api_Distribution_descriptor = DistributionProto.getDescriptor().getMessageTypes().get(0);
        internal_static_google_api_Distribution_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_Distribution_descriptor, new String[]{"Count", "Mean", "SumOfSquaredDeviation", "Range", "BucketOptions", "BucketCounts", "Exemplars"});
        internal_static_google_api_Distribution_Range_descriptor = internal_static_google_api_Distribution_descriptor.getNestedTypes().get(0);
        internal_static_google_api_Distribution_Range_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_Distribution_Range_descriptor, new String[]{"Min", "Max"});
        internal_static_google_api_Distribution_BucketOptions_descriptor = internal_static_google_api_Distribution_descriptor.getNestedTypes().get(1);
        internal_static_google_api_Distribution_BucketOptions_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_Distribution_BucketOptions_descriptor, new String[]{"LinearBuckets", "ExponentialBuckets", "ExplicitBuckets", "Options"});
        internal_static_google_api_Distribution_BucketOptions_Linear_descriptor = internal_static_google_api_Distribution_BucketOptions_descriptor.getNestedTypes().get(0);
        internal_static_google_api_Distribution_BucketOptions_Linear_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_Distribution_BucketOptions_Linear_descriptor, new String[]{"NumFiniteBuckets", "Width", "Offset"});
        internal_static_google_api_Distribution_BucketOptions_Exponential_descriptor = internal_static_google_api_Distribution_BucketOptions_descriptor.getNestedTypes().get(1);
        internal_static_google_api_Distribution_BucketOptions_Exponential_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_Distribution_BucketOptions_Exponential_descriptor, new String[]{"NumFiniteBuckets", "GrowthFactor", "Scale"});
        internal_static_google_api_Distribution_BucketOptions_Explicit_descriptor = internal_static_google_api_Distribution_BucketOptions_descriptor.getNestedTypes().get(2);
        internal_static_google_api_Distribution_BucketOptions_Explicit_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_Distribution_BucketOptions_Explicit_descriptor, new String[]{"Bounds"});
        internal_static_google_api_Distribution_Exemplar_descriptor = internal_static_google_api_Distribution_descriptor.getNestedTypes().get(2);
        internal_static_google_api_Distribution_Exemplar_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_google_api_Distribution_Exemplar_descriptor, new String[]{"Value", "Timestamp", "Attachments"});
        AnyProto.getDescriptor();
        TimestampProto.getDescriptor();
    }
}

