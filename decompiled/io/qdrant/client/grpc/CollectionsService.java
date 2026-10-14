/*
 * Decompiled with CFR 0.152.
 */
package io.qdrant.client.grpc;

import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import io.qdrant.client.grpc.Collections;

public final class CollectionsService {
    private static Descriptors.FileDescriptor descriptor;

    private CollectionsService() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        CollectionsService.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u0019collections_service.proto\u0012\u0006qdrant\u001a\u0011collections.proto2\u00e2\b\n\u000bCollections\u0012L\n\u0003Get\u0012 .qdrant.GetCollectionInfoRequest\u001a!.qdrant.GetCollectionInfoResponse\"\u0000\u0012I\n\u0004List\u0012\u001e.qdrant.ListCollectionsRequest\u001a\u001f.qdrant.ListCollectionsResponse\"\u0000\u0012I\n\u0006Create\u0012\u0018.qdrant.CreateCollection\u001a#.qdrant.CollectionOperationResponse\"\u0000\u0012I\n\u0006Update\u0012\u0018.qdrant.UpdateCollection\u001a#.qdrant.CollectionOperationResponse\"\u0000\u0012I\n\u0006Delete\u0012\u0018.qdrant.DeleteCollection\u001a#.qdrant.CollectionOperationResponse\"\u0000\u0012M\n\rUpdateAliases\u0012\u0015.qdrant.ChangeAliases\u001a#.qdrant.CollectionOperationResponse\"\u0000\u0012\\\n\u0015ListCollectionAliases\u0012$.qdrant.ListCollectionAliasesRequest\u001a\u001b.qdrant.ListAliasesResponse\"\u0000\u0012H\n\u000bListAliases\u0012\u001a.qdrant.ListAliasesRequest\u001a\u001b.qdrant.ListAliasesResponse\"\u0000\u0012f\n\u0015CollectionClusterInfo\u0012$.qdrant.CollectionClusterInfoRequest\u001a%.qdrant.CollectionClusterInfoResponse\"\u0000\u0012W\n\u0010CollectionExists\u0012\u001f.qdrant.CollectionExistsRequest\u001a .qdrant.CollectionExistsResponse\"\u0000\u0012{\n\u001cUpdateCollectionClusterSetup\u0012+.qdrant.UpdateCollectionClusterSetupRequest\u001a,.qdrant.UpdateCollectionClusterSetupResponse\"\u0000\u0012Q\n\u000eCreateShardKey\u0012\u001d.qdrant.CreateShardKeyRequest\u001a\u001e.qdrant.CreateShardKeyResponse\"\u0000\u0012Q\n\u000eDeleteShardKey\u0012\u001d.qdrant.DeleteShardKeyRequest\u001a\u001e.qdrant.DeleteShardKeyResponse\"\u0000B,\n\u0015io.qdrant.client.grpc\u00aa\u0002\u0012Qdrant.Client.Grpcb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[]{Collections.getDescriptor()});
        Collections.getDescriptor();
    }
}

