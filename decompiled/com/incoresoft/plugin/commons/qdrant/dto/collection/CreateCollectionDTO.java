/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.qdrant.dto.collection;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.plugin.commons.qdrant.dto.collection.CollectionCreationVectorsDTO;
import com.incoresoft.plugin.commons.qdrant.dto.collection.HNSWConfigDTO;
import com.incoresoft.plugin.commons.qdrant.dto.collection.OptimizersConfigDTO;

public final class CreateCollectionDTO {
    @JSONField(name="vectors")
    private final CollectionCreationVectorsDTO vectors;
    @JSONField(name="optimizers_config")
    private final OptimizersConfigDTO optimizersConfig;
    @JSONField(name="hnsw_config")
    private final HNSWConfigDTO hnswConfig;

    public CreateCollectionDTO() {
        this(new CollectionCreationVectorsDTO("Dot", 512), new OptimizersConfigDTO(100000), new HNSWConfigDTO(true));
    }

    public CollectionCreationVectorsDTO getVectors() {
        return this.vectors;
    }

    public OptimizersConfigDTO getOptimizersConfig() {
        return this.optimizersConfig;
    }

    public HNSWConfigDTO getHnswConfig() {
        return this.hnswConfig;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CreateCollectionDTO)) {
            return false;
        }
        CreateCollectionDTO other = (CreateCollectionDTO)o;
        CollectionCreationVectorsDTO this$vectors = this.getVectors();
        CollectionCreationVectorsDTO other$vectors = other.getVectors();
        if (this$vectors == null ? other$vectors != null : !((Object)this$vectors).equals(other$vectors)) {
            return false;
        }
        OptimizersConfigDTO this$optimizersConfig = this.getOptimizersConfig();
        OptimizersConfigDTO other$optimizersConfig = other.getOptimizersConfig();
        if (this$optimizersConfig == null ? other$optimizersConfig != null : !((Object)this$optimizersConfig).equals(other$optimizersConfig)) {
            return false;
        }
        HNSWConfigDTO this$hnswConfig = this.getHnswConfig();
        HNSWConfigDTO other$hnswConfig = other.getHnswConfig();
        return !(this$hnswConfig == null ? other$hnswConfig != null : !((Object)this$hnswConfig).equals(other$hnswConfig));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        CollectionCreationVectorsDTO $vectors = this.getVectors();
        result = result * 59 + ($vectors == null ? 43 : ((Object)$vectors).hashCode());
        OptimizersConfigDTO $optimizersConfig = this.getOptimizersConfig();
        result = result * 59 + ($optimizersConfig == null ? 43 : ((Object)$optimizersConfig).hashCode());
        HNSWConfigDTO $hnswConfig = this.getHnswConfig();
        result = result * 59 + ($hnswConfig == null ? 43 : ((Object)$hnswConfig).hashCode());
        return result;
    }

    public String toString() {
        return "CreateCollectionDTO(vectors=" + String.valueOf(this.getVectors()) + ", optimizersConfig=" + String.valueOf(this.getOptimizersConfig()) + ", hnswConfig=" + String.valueOf(this.getHnswConfig()) + ")";
    }

    public CreateCollectionDTO(CollectionCreationVectorsDTO vectors, OptimizersConfigDTO optimizersConfig, HNSWConfigDTO hnswConfig) {
        this.vectors = vectors;
        this.optimizersConfig = optimizersConfig;
        this.hnswConfig = hnswConfig;
    }
}

