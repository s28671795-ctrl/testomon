/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.storage;

import com.google.inject.Inject;
import com.incoresoft.config.models.StorageConfig;
import com.incoresoft.database.CleaningSettingsRepository;
import com.incoresoft.plugin.commons.cleaner.VezhaCleaningCategory;
import com.incoresoft.vezha.config.VezhaConfigManager;
import com.incoresoft.vezha.config.models.VezhaConfig;
import io.minio.MinioClient;
import io.minio.SetBucketLifecycleArgs;
import io.minio.messages.Expiration;
import io.minio.messages.LifecycleConfiguration;
import io.minio.messages.LifecycleRule;
import io.minio.messages.RuleFilter;
import io.minio.messages.Status;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class S3StorageCleaner {
    private static final Logger log = LoggerFactory.getLogger(S3StorageCleaner.class);
    public static final String ANALYTICS_STORAGE_PREFIX = "analytics/";
    private final VezhaConfigManager configManager;
    private final CleaningSettingsRepository cleaningSettingsRepository;

    @Inject
    public void updateLifecycleRule() {
        StorageConfig config = ((VezhaConfig)this.configManager.getConfig()).getStorage();
        if (Objects.equals(config.getProperties().get("type"), "s3")) {
            String url2 = config.getProperties().get("url");
            String login = config.getProperties().get("access_key");
            String password = config.getProperties().get("secret_key");
            String bucketName = config.getProperties().get("bucket_name");
            try {
                MinioClient minioClient = MinioClient.builder().credentials(login, password).endpoint(url2).build();
                int storageLifetimeDays = Math.toIntExact(this.cleaningSettingsRepository.getRetentionTime(VezhaCleaningCategory.STORAGE_DATA).toDays());
                LifecycleConfiguration lifecycleConfiguration = new LifecycleConfiguration(List.of(new LifecycleRule(Status.ENABLED, null, new Expiration((ZonedDateTime)null, (Integer)storageLifetimeDays, null), new RuleFilter(ANALYTICS_STORAGE_PREFIX), "StorageCleanerRule", null, null, null)));
                SetBucketLifecycleArgs args2 = (SetBucketLifecycleArgs)((SetBucketLifecycleArgs.Builder)SetBucketLifecycleArgs.builder().bucket(bucketName)).config(lifecycleConfiguration).build();
                minioClient.setBucketLifecycle(args2);
            }
            catch (Exception e) {
                log.error("Error setting lifecycle rule for S3 bucket", e);
            }
        }
    }

    @Inject
    public S3StorageCleaner(VezhaConfigManager configManager, CleaningSettingsRepository cleaningSettingsRepository) {
        this.configManager = configManager;
        this.cleaningSettingsRepository = cleaningSettingsRepository;
    }
}

