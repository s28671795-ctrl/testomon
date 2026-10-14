/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  edu.umd.cs.findbugs.annotations.SuppressFBWarnings
 */
package io.minio;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.minio.BucketExistsArgs;
import io.minio.CloseableIterator;
import io.minio.ComposeObjectArgs;
import io.minio.CopyObjectArgs;
import io.minio.DeleteBucketEncryptionArgs;
import io.minio.DeleteBucketLifecycleArgs;
import io.minio.DeleteBucketNotificationArgs;
import io.minio.DeleteBucketPolicyArgs;
import io.minio.DeleteBucketReplicationArgs;
import io.minio.DeleteBucketTagsArgs;
import io.minio.DeleteObjectLockConfigurationArgs;
import io.minio.DeleteObjectTagsArgs;
import io.minio.DisableObjectLegalHoldArgs;
import io.minio.DownloadObjectArgs;
import io.minio.EnableObjectLegalHoldArgs;
import io.minio.GetBucketEncryptionArgs;
import io.minio.GetBucketLifecycleArgs;
import io.minio.GetBucketNotificationArgs;
import io.minio.GetBucketPolicyArgs;
import io.minio.GetBucketReplicationArgs;
import io.minio.GetBucketTagsArgs;
import io.minio.GetBucketVersioningArgs;
import io.minio.GetObjectArgs;
import io.minio.GetObjectLockConfigurationArgs;
import io.minio.GetObjectResponse;
import io.minio.GetObjectRetentionArgs;
import io.minio.GetObjectTagsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.IsObjectLegalHoldEnabledArgs;
import io.minio.ListBucketsArgs;
import io.minio.ListObjectsArgs;
import io.minio.ListenBucketNotificationArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioAsyncClient;
import io.minio.ObjectWriteResponse;
import io.minio.PostPolicy;
import io.minio.PutObjectArgs;
import io.minio.RemoveBucketArgs;
import io.minio.RemoveObjectArgs;
import io.minio.RemoveObjectsArgs;
import io.minio.RestoreObjectArgs;
import io.minio.Result;
import io.minio.SelectObjectContentArgs;
import io.minio.SelectResponseStream;
import io.minio.SetBucketEncryptionArgs;
import io.minio.SetBucketLifecycleArgs;
import io.minio.SetBucketNotificationArgs;
import io.minio.SetBucketPolicyArgs;
import io.minio.SetBucketReplicationArgs;
import io.minio.SetBucketTagsArgs;
import io.minio.SetBucketVersioningArgs;
import io.minio.SetObjectLockConfigurationArgs;
import io.minio.SetObjectRetentionArgs;
import io.minio.SetObjectTagsArgs;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import io.minio.UploadObjectArgs;
import io.minio.UploadSnowballObjectsArgs;
import io.minio.credentials.Provider;
import io.minio.errors.BucketPolicyTooLargeException;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteError;
import io.minio.messages.Item;
import io.minio.messages.LifecycleConfiguration;
import io.minio.messages.NotificationConfiguration;
import io.minio.messages.NotificationRecords;
import io.minio.messages.ObjectLockConfiguration;
import io.minio.messages.ReplicationConfiguration;
import io.minio.messages.Retention;
import io.minio.messages.SseConfiguration;
import io.minio.messages.Tags;
import io.minio.messages.VersioningConfiguration;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public class MinioClient
implements AutoCloseable {
    private MinioAsyncClient asyncClient = null;

    private MinioClient(MinioAsyncClient asyncClient) {
        this.asyncClient = asyncClient;
    }

    protected MinioClient(MinioClient client) {
        this.asyncClient = client.asyncClient;
    }

    public StatObjectResponse statObject(StatObjectArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            return this.asyncClient.statObject(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
            return null;
        }
    }

    public GetObjectResponse getObject(GetObjectArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            return this.asyncClient.getObject(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
            return null;
        }
    }

    public void downloadObject(DownloadObjectArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            this.asyncClient.downloadObject(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
        }
    }

    public ObjectWriteResponse copyObject(CopyObjectArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            return this.asyncClient.copyObject(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
            return null;
        }
    }

    public ObjectWriteResponse composeObject(ComposeObjectArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            return this.asyncClient.composeObject(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
            return null;
        }
    }

    public String getPresignedObjectUrl(GetPresignedObjectUrlArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, XmlParserException, ServerException {
        return this.asyncClient.getPresignedObjectUrl(args2);
    }

    public Map<String, String> getPresignedPostFormData(PostPolicy policy) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        return this.asyncClient.getPresignedPostFormData(policy);
    }

    public void removeObject(RemoveObjectArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            this.asyncClient.removeObject(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
        }
    }

    public Iterable<Result<DeleteError>> removeObjects(RemoveObjectsArgs args2) {
        return this.asyncClient.removeObjects(args2);
    }

    public void restoreObject(RestoreObjectArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            this.asyncClient.restoreObject(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
        }
    }

    public Iterable<Result<Item>> listObjects(ListObjectsArgs args2) {
        return this.asyncClient.listObjects(args2);
    }

    public List<Bucket> listBuckets() throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            return this.asyncClient.listBuckets().get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
            return null;
        }
    }

    public List<Bucket> listBuckets(ListBucketsArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            return this.asyncClient.listBuckets(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
            return null;
        }
    }

    public boolean bucketExists(BucketExistsArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            return this.asyncClient.bucketExists(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
            return false;
        }
    }

    public void makeBucket(MakeBucketArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            this.asyncClient.makeBucket(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
        }
    }

    public void setBucketVersioning(SetBucketVersioningArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            this.asyncClient.setBucketVersioning(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
        }
    }

    public VersioningConfiguration getBucketVersioning(GetBucketVersioningArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            return this.asyncClient.getBucketVersioning(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
            return null;
        }
    }

    public void setObjectLockConfiguration(SetObjectLockConfigurationArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            this.asyncClient.setObjectLockConfiguration(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
        }
    }

    public void deleteObjectLockConfiguration(DeleteObjectLockConfigurationArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            this.asyncClient.deleteObjectLockConfiguration(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
        }
    }

    public ObjectLockConfiguration getObjectLockConfiguration(GetObjectLockConfigurationArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            return this.asyncClient.getObjectLockConfiguration(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
            return null;
        }
    }

    public void setObjectRetention(SetObjectRetentionArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            this.asyncClient.setObjectRetention(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
        }
    }

    public Retention getObjectRetention(GetObjectRetentionArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            return this.asyncClient.getObjectRetention(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
            return null;
        }
    }

    public void enableObjectLegalHold(EnableObjectLegalHoldArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            this.asyncClient.enableObjectLegalHold(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
        }
    }

    public void disableObjectLegalHold(DisableObjectLegalHoldArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            this.asyncClient.disableObjectLegalHold(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
        }
    }

    public boolean isObjectLegalHoldEnabled(IsObjectLegalHoldEnabledArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            return this.asyncClient.isObjectLegalHoldEnabled(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
            return false;
        }
    }

    public void removeBucket(RemoveBucketArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            this.asyncClient.removeBucket(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
        }
    }

    public ObjectWriteResponse putObject(PutObjectArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            return this.asyncClient.putObject(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
            return null;
        }
    }

    public ObjectWriteResponse uploadObject(UploadObjectArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            return this.asyncClient.uploadObject(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
            return null;
        }
    }

    public String getBucketPolicy(GetBucketPolicyArgs args2) throws BucketPolicyTooLargeException, ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            return this.asyncClient.getBucketPolicy(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
            return "";
        }
    }

    public void setBucketPolicy(SetBucketPolicyArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            this.asyncClient.setBucketPolicy(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
        }
    }

    public void deleteBucketPolicy(DeleteBucketPolicyArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            this.asyncClient.deleteBucketPolicy(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
        }
    }

    public void setBucketLifecycle(SetBucketLifecycleArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            this.asyncClient.setBucketLifecycle(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
        }
    }

    public void deleteBucketLifecycle(DeleteBucketLifecycleArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            this.asyncClient.deleteBucketLifecycle(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
        }
    }

    public LifecycleConfiguration getBucketLifecycle(GetBucketLifecycleArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            return this.asyncClient.getBucketLifecycle(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
            return null;
        }
    }

    public NotificationConfiguration getBucketNotification(GetBucketNotificationArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            return this.asyncClient.getBucketNotification(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
            return null;
        }
    }

    public void setBucketNotification(SetBucketNotificationArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            this.asyncClient.setBucketNotification(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
        }
    }

    public void deleteBucketNotification(DeleteBucketNotificationArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            this.asyncClient.deleteBucketNotification(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
        }
    }

    public ReplicationConfiguration getBucketReplication(GetBucketReplicationArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            return this.asyncClient.getBucketReplication(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
            return null;
        }
    }

    public void setBucketReplication(SetBucketReplicationArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            this.asyncClient.setBucketReplication(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
        }
    }

    public void deleteBucketReplication(DeleteBucketReplicationArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            this.asyncClient.deleteBucketReplication(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
        }
    }

    public CloseableIterator<Result<NotificationRecords>> listenBucketNotification(ListenBucketNotificationArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        return this.asyncClient.listenBucketNotification(args2);
    }

    public SelectResponseStream selectObjectContent(SelectObjectContentArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        return this.asyncClient.selectObjectContent(args2);
    }

    public void setBucketEncryption(SetBucketEncryptionArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            this.asyncClient.setBucketEncryption(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
        }
    }

    public SseConfiguration getBucketEncryption(GetBucketEncryptionArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            return this.asyncClient.getBucketEncryption(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
            return null;
        }
    }

    public void deleteBucketEncryption(DeleteBucketEncryptionArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            this.asyncClient.deleteBucketEncryption(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
        }
    }

    public Tags getBucketTags(GetBucketTagsArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            return this.asyncClient.getBucketTags(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
            return null;
        }
    }

    public void setBucketTags(SetBucketTagsArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            this.asyncClient.setBucketTags(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
        }
    }

    public void deleteBucketTags(DeleteBucketTagsArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            this.asyncClient.deleteBucketTags(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
        }
    }

    public Tags getObjectTags(GetObjectTagsArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            return this.asyncClient.getObjectTags(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
            return null;
        }
    }

    public void setObjectTags(SetObjectTagsArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            this.asyncClient.setObjectTags(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
        }
    }

    public void deleteObjectTags(DeleteObjectTagsArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            this.asyncClient.deleteObjectTags(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
        }
    }

    public ObjectWriteResponse uploadSnowballObjects(UploadSnowballObjectsArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            return this.asyncClient.uploadSnowballObjects(args2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.asyncClient.throwEncapsulatedException(e);
            return null;
        }
    }

    public void setTimeout(long connectTimeout, long writeTimeout, long readTimeout) {
        this.asyncClient.setTimeout(connectTimeout, writeTimeout, readTimeout);
    }

    @SuppressFBWarnings(value={"SIC"}, justification="Should not be used in production anyways.")
    public void ignoreCertCheck() throws KeyManagementException, NoSuchAlgorithmException {
        this.asyncClient.ignoreCertCheck();
    }

    public void setAppInfo(String name, String version) {
        this.asyncClient.setAppInfo(name, version);
    }

    public void traceOn(OutputStream traceStream) {
        this.asyncClient.traceOn(traceStream);
    }

    public void traceOff() throws IOException {
        this.asyncClient.traceOff();
    }

    @Deprecated
    public void enableAccelerateEndpoint() {
        this.asyncClient.enableAccelerateEndpoint();
    }

    @Deprecated
    public void disableAccelerateEndpoint() {
        this.asyncClient.disableAccelerateEndpoint();
    }

    public void enableDualStackEndpoint() {
        this.asyncClient.enableDualStackEndpoint();
    }

    public void disableDualStackEndpoint() {
        this.asyncClient.disableDualStackEndpoint();
    }

    public void enableVirtualStyleEndpoint() {
        this.asyncClient.enableVirtualStyleEndpoint();
    }

    public void disableVirtualStyleEndpoint() {
        this.asyncClient.disableVirtualStyleEndpoint();
    }

    public void setAwsS3Prefix(String awsS3Prefix) {
        this.asyncClient.setAwsS3Prefix(awsS3Prefix);
    }

    @Override
    public void close() throws Exception {
        if (this.asyncClient != null) {
            this.asyncClient.close();
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private MinioAsyncClient.Builder asyncClientBuilder = MinioAsyncClient.builder();

        public Builder endpoint(String endpoint) {
            this.asyncClientBuilder.endpoint(endpoint);
            return this;
        }

        public Builder endpoint(String endpoint, int port, boolean secure) {
            this.asyncClientBuilder.endpoint(endpoint, port, secure);
            return this;
        }

        public Builder endpoint(URL url2) {
            this.asyncClientBuilder.endpoint(url2);
            return this;
        }

        public Builder endpoint(HttpUrl url2) {
            this.asyncClientBuilder.endpoint(url2);
            return this;
        }

        public Builder region(String region) {
            this.asyncClientBuilder.region(region);
            return this;
        }

        public Builder credentials(String accessKey, String secretKey) {
            this.asyncClientBuilder.credentials(accessKey, secretKey);
            return this;
        }

        public Builder credentialsProvider(Provider provider) {
            this.asyncClientBuilder.credentialsProvider(provider);
            return this;
        }

        public Builder httpClient(OkHttpClient httpClient) {
            this.asyncClientBuilder.httpClient(httpClient);
            return this;
        }

        public MinioClient build() {
            MinioAsyncClient asyncClient = this.asyncClientBuilder.build();
            return new MinioClient(asyncClient);
        }
    }
}

