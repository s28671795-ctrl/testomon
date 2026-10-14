/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.config.storage;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.incoresoft.dto.config.storage.StorageConfigDTO;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="S3StorageConfigDTO", description="S3 storage configuration DTO")
@JsonDeserialize(builder=S3StorageConfigDTOBuilder.class)
public final class S3StorageConfigDTO
implements StorageConfigDTO {
    @JsonProperty(value="url")
    @Schema(name="url", description="S3 storage host", example="http://localhost:9000")
    private final String url;
    @JsonProperty(value="access_key")
    @Schema(name="access_key", description="S3 storage access key", example="minioadmin")
    private final String accessKey;
    @JsonProperty(value="secret_key")
    @Schema(name="secret_key", description="S3 storage secret key", example="minioadmin")
    private final String secretKey;
    @JsonProperty(value="bucket_name")
    @Schema(name="bucket_name", description="S3 storage bucket name", example="videoanalytics")
    private final String bucketName;

    public static S3StorageConfigDTOBuilder builder() {
        return new S3StorageConfigDTOBuilder();
    }

    public String getUrl() {
        return this.url;
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
        if (!(o instanceof S3StorageConfigDTO)) {
            return false;
        }
        S3StorageConfigDTO other = (S3StorageConfigDTO)o;
        String this$url = this.getUrl();
        String other$url = other.getUrl();
        if (this$url == null ? other$url != null : !this$url.equals(other$url)) {
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
        String $url = this.getUrl();
        result = result * 59 + ($url == null ? 43 : $url.hashCode());
        String $accessKey = this.getAccessKey();
        result = result * 59 + ($accessKey == null ? 43 : $accessKey.hashCode());
        String $secretKey = this.getSecretKey();
        result = result * 59 + ($secretKey == null ? 43 : $secretKey.hashCode());
        String $bucketName = this.getBucketName();
        result = result * 59 + ($bucketName == null ? 43 : $bucketName.hashCode());
        return result;
    }

    public String toString() {
        return "S3StorageConfigDTO(url=" + this.getUrl() + ", accessKey=" + this.getAccessKey() + ", secretKey=" + this.getSecretKey() + ", bucketName=" + this.getBucketName() + ")";
    }

    public S3StorageConfigDTO(String url2, String accessKey, String secretKey, String bucketName) {
        this.url = url2;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucketName = bucketName;
    }

    @JsonPOJOBuilder(withPrefix="", buildMethodName="build")
    public static class S3StorageConfigDTOBuilder {
        private String url;
        private String accessKey;
        private String secretKey;
        private String bucketName;

        S3StorageConfigDTOBuilder() {
        }

        @JsonProperty(value="url")
        public S3StorageConfigDTOBuilder url(String url2) {
            this.url = url2;
            return this;
        }

        @JsonProperty(value="access_key")
        public S3StorageConfigDTOBuilder accessKey(String accessKey) {
            this.accessKey = accessKey;
            return this;
        }

        @JsonProperty(value="secret_key")
        public S3StorageConfigDTOBuilder secretKey(String secretKey) {
            this.secretKey = secretKey;
            return this;
        }

        @JsonProperty(value="bucket_name")
        public S3StorageConfigDTOBuilder bucketName(String bucketName) {
            this.bucketName = bucketName;
            return this;
        }

        public S3StorageConfigDTO build() {
            return new S3StorageConfigDTO(this.url, this.accessKey, this.secretKey, this.bucketName);
        }

        public String toString() {
            return "S3StorageConfigDTO.S3StorageConfigDTOBuilder(url=" + this.url + ", accessKey=" + this.accessKey + ", secretKey=" + this.secretKey + ", bucketName=" + this.bucketName + ")";
        }
    }
}

