/*
 * Decompiled with CFR 0.152.
 */
package io.qdrant.client.grpc;

import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import io.qdrant.client.grpc.Points;

public final class PointsService {
    private static Descriptors.FileDescriptor descriptor;

    private PointsService() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        PointsService.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u0014points_service.proto\u0012\u0006qdrant\u001a\fpoints.proto2\u0096\u000e\n\u0006Points\u0012A\n\u0006Upsert\u0012\u0014.qdrant.UpsertPoints\u001a\u001f.qdrant.PointsOperationResponse\"\u0000\u0012A\n\u0006Delete\u0012\u0014.qdrant.DeletePoints\u001a\u001f.qdrant.PointsOperationResponse\"\u0000\u0012/\n\u0003Get\u0012\u0011.qdrant.GetPoints\u001a\u0013.qdrant.GetResponse\"\u0000\u0012N\n\rUpdateVectors\u0012\u001a.qdrant.UpdatePointVectors\u001a\u001f.qdrant.PointsOperationResponse\"\u0000\u0012N\n\rDeleteVectors\u0012\u001a.qdrant.DeletePointVectors\u001a\u001f.qdrant.PointsOperationResponse\"\u0000\u0012I\n\nSetPayload\u0012\u0018.qdrant.SetPayloadPoints\u001a\u001f.qdrant.PointsOperationResponse\"\u0000\u0012O\n\u0010OverwritePayload\u0012\u0018.qdrant.SetPayloadPoints\u001a\u001f.qdrant.PointsOperationResponse\"\u0000\u0012O\n\rDeletePayload\u0012\u001b.qdrant.DeletePayloadPoints\u001a\u001f.qdrant.PointsOperationResponse\"\u0000\u0012M\n\fClearPayload\u0012\u001a.qdrant.ClearPayloadPoints\u001a\u001f.qdrant.PointsOperationResponse\"\u0000\u0012Y\n\u0010CreateFieldIndex\u0012\".qdrant.CreateFieldIndexCollection\u001a\u001f.qdrant.PointsOperationResponse\"\u0000\u0012Y\n\u0010DeleteFieldIndex\u0012\".qdrant.DeleteFieldIndexCollection\u001a\u001f.qdrant.PointsOperationResponse\"\u0000\u00128\n\u0006Search\u0012\u0014.qdrant.SearchPoints\u001a\u0016.qdrant.SearchResponse\"\u0000\u0012G\n\u000bSearchBatch\u0012\u0019.qdrant.SearchBatchPoints\u001a\u001b.qdrant.SearchBatchResponse\"\u0000\u0012I\n\fSearchGroups\u0012\u0019.qdrant.SearchPointGroups\u001a\u001c.qdrant.SearchGroupsResponse\"\u0000\u00128\n\u0006Scroll\u0012\u0014.qdrant.ScrollPoints\u001a\u0016.qdrant.ScrollResponse\"\u0000\u0012A\n\tRecommend\u0012\u0017.qdrant.RecommendPoints\u001a\u0019.qdrant.RecommendResponse\"\u0000\u0012P\n\u000eRecommendBatch\u0012\u001c.qdrant.RecommendBatchPoints\u001a\u001e.qdrant.RecommendBatchResponse\"\u0000\u0012R\n\u000fRecommendGroups\u0012\u001c.qdrant.RecommendPointGroups\u001a\u001f.qdrant.RecommendGroupsResponse\"\u0000\u0012>\n\bDiscover\u0012\u0016.qdrant.DiscoverPoints\u001a\u0018.qdrant.DiscoverResponse\"\u0000\u0012M\n\rDiscoverBatch\u0012\u001b.qdrant.DiscoverBatchPoints\u001a\u001d.qdrant.DiscoverBatchResponse\"\u0000\u00125\n\u0005Count\u0012\u0013.qdrant.CountPoints\u001a\u0015.qdrant.CountResponse\"\u0000\u0012G\n\u000bUpdateBatch\u0012\u0019.qdrant.UpdateBatchPoints\u001a\u001b.qdrant.UpdateBatchResponse\"\u0000\u00125\n\u0005Query\u0012\u0013.qdrant.QueryPoints\u001a\u0015.qdrant.QueryResponse\"\u0000\u0012D\n\nQueryBatch\u0012\u0018.qdrant.QueryBatchPoints\u001a\u001a.qdrant.QueryBatchResponse\"\u0000\u0012F\n\u000bQueryGroups\u0012\u0018.qdrant.QueryPointGroups\u001a\u001b.qdrant.QueryGroupsResponse\"\u0000B,\n\u0015io.qdrant.client.grpc\u00aa\u0002\u0012Qdrant.Client.Grpcb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[]{Points.getDescriptor()});
        Points.getDescriptor();
    }
}

