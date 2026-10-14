/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.config.storage;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.incoresoft.dto.config.storage.SetupStorageConfigDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(name="SetupS3StorageConfigDTO", description="S3 storage configuration")
@JsonDeserialize(builder=SetupS3StorageConfigDTOBuilder.class)
public final class SetupS3StorageConfigDTO
implements SetupStorageConfigDTO {
    @JsonProperty(value="host")
    @Schema(name="host", description="Storage host", example="localhost", implementation=String.class)
    @NotNull
    private final String host;
    @JsonProperty(value="port")
    @Schema(name="port", description="Storage port", example="3000", implementation=Integer.class)
    @NotNull
    private final Integer port;
    @JsonProperty(value="access_key")
    @Schema(name="access_key", description="Storage access key", example="minioadmin", implementation=String.class)
    @NotNull
    private final String accessKey;
    @JsonProperty(value="secret_key")
    @Schema(name="secret_key", description="Storage secret key", example="minioadmin", implementation=String.class)
    @NotNull
    private final String secretKey;
    @JsonProperty(value="bucket_name")
    @Schema(name="bucket_name", description="Storage bucket name", example="videoanalytics", implementation=String.class)
    @NotEmpty
    private final String bucketName;

    public static SetupS3StorageConfigDTOBuilder builder() {
        return new SetupS3StorageConfigDTOBuilder();
    }

    public String getHost() {
        return this.host;
    }

    public Integer getPort() {
        return this.port;
    }

    public String getAccessKey() {
        return this.accessKey;
    }

    public String getSecretKey() {
        return this.secretKey;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SetupS3StorageConfigDTO)) {
            return false;
        }
        SetupS3StorageConfigDTO other = (SetupS3StorageConfigDTO)o;
        Integer this$port = this.getPort();
        Integer other$port = other.getPort();
        if (this$port == null ? other$port != null : !((Object)this$port).equals(other$port)) {
            return false;
        }
        String this$host = this.getHost();
        String other$host = other.getHost();
        if (this$host == null ? other$host != null : !this$host.equals(other$host)) {
            return false;
        }
        String this$accessKey = this.getAccessKey();
        String other$accessKey = other.getAccessKey();
        if (this$accessKey == null ? other$accessKey != null : !this$accessKey.equals(other$accessKey)) {
            return false;
        }
        String this$secretKey = this.getSecretKey();
        String other$secretKey = other.getSecretKey();
        if (this$secretKey == null ? other$secretKey != null : !this$secretKey.equals(other$secretKey)) {
            return false;
        }
        String this$bucketName = this.getBucketName();
        String other$bucketName = other.getBucketName();
        return !(this$bucketName == null ? other$bucketName != null : !this$bucketName.equals(other$bucketName));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $port = this.getPort();
        result = result * 59 + ($port == null ? 43 : ((Object)$port).hashCode());
        String $host = this.getHost();
        result = result * 59 + ($host == null ? 43 : $host.hashCode());
        String $accessKey = this.getAccessKey();
        result = result * 59 + ($accessKey == null ? 43 : $accessKey.hashCode());
        String $secretKey = this.getSecretKey();
        result = result * 59 + ($secretKey == null ? 43 : $secretKey.hashCode());
        String $bucketName = this.getBucketName();
        result = result * 59 + ($bucketName == null ? 43 : $bucketName.hashCode());
        return result;
    }

    public String toString() {
        return "SetupS3StorageConfigDTO(host=" + this.getHost() + ", port=" + this.getPort() + ", accessKey=" + this.getAccessKey() + ", secretKey=" + this.getSecretKey() + ", bucketName=" + this.getBucketName() + ")";
    }

    public SetupS3StorageConfigDTO(String host2, Integer port, String accessKey, String secretKey, String bucketName) {
        this.host = host2;
        this.port = port;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucketName = bucketName;
    }

    @JsonPOJOBuilder(withPrefix="", buildMethodName="build")
    public static class SetupS3StorageConfigDTOBuilder {
        private String host;
        private Integer port;
        private String accessKey;
        private String secretKey;
        private String bucketName;

        SetupS3StorageConfigDTOBuilder() {
        }

        @JsonProperty(value="host")
        public SetupS3StorageConfigDTOBuilder host(String host2) {
            this.host = host2;
            return this;
        }

        @JsonProperty(value="port")
        public SetupS3StorageConfigDTOBuilder port(Integer port) {
            this.port = port;
            return this;
        }

        @JsonProperty(value="access_key")
        public SetupS3StorageConfigDTOBuilder accessKey(String accessKey) {
            this.accessKey = accessKey;
            return this;
        }

        @JsonProperty(value="secret_key")
        public SetupS3StorageConfigDTOBuilder secretKey(String secretKey) {
            this.secretKey = secretKey;
            return this;
        }

        @JsonProperty(value="bucket_name")
        public SetupS3StorageConfigDTOBuilder bucketName(String bucketName) {
            this.bucketName = bucketName;
            return this;
        }

        public SetupS3StorageConfigDTO build() {
            return new SetupS3StorageConfigDTO(this.host, this.port, this.accessKey, this.secretKey, this.bucketName);
        }

        public String toString() {
            return "SetupS3StorageConfigDTO.SetupS3StorageConfigDTOBuilder(host=" + this.host + ", port=" + this.port + ", accessKey=" + this.accessKey + ", secretKey=" + this.secretKey + ", bucketName=" + this.bucketName + ")";
        }
    }
}

