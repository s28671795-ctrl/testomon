/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.io.ByteStreams;
import io.minio.BucketExistsArgs;
import io.minio.CloseableIterator;
import io.minio.ComposeObjectArgs;
import io.minio.ComposeSource;
import io.minio.CopyObjectArgs;
import io.minio.DeleteBucketEncryptionArgs;
import io.minio.DeleteBucketLifecycleArgs;
import io.minio.DeleteBucketNotificationArgs;
import io.minio.DeleteBucketPolicyArgs;
import io.minio.DeleteBucketReplicationArgs;
import io.minio.DeleteBucketTagsArgs;
import io.minio.DeleteObjectLockConfigurationArgs;
import io.minio.DeleteObjectTagsArgs;
import io.minio.DeleteObjectsResponse;
import io.minio.Directive;
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
import io.minio.ObjectWriteResponse;
import io.minio.PostPolicy;
import io.minio.PutObjectArgs;
import io.minio.RemoveBucketArgs;
import io.minio.RemoveObjectArgs;
import io.minio.RemoveObjectsArgs;
import io.minio.RestoreObjectArgs;
import io.minio.Result;
import io.minio.S3Base;
import io.minio.S3Escaper;
import io.minio.SelectObjectContentArgs;
import io.minio.SelectResponseStream;
import io.minio.ServerSideEncryptionCustomerKey;
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
import io.minio.Signer;
import io.minio.SnowballObject;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import io.minio.UploadObjectArgs;
import io.minio.UploadSnowballObjectsArgs;
import io.minio.Xml;
import io.minio.credentials.Credentials;
import io.minio.credentials.Provider;
import io.minio.credentials.StaticProvider;
import io.minio.errors.BucketPolicyTooLargeException;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.http.HttpUtils;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.CopyObjectResult;
import io.minio.messages.CreateBucketConfiguration;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import io.minio.messages.LegalHold;
import io.minio.messages.LifecycleConfiguration;
import io.minio.messages.ListAllMyBucketsResult;
import io.minio.messages.NotificationConfiguration;
import io.minio.messages.NotificationRecords;
import io.minio.messages.ObjectLockConfiguration;
import io.minio.messages.Part;
import io.minio.messages.ReplicationConfiguration;
import io.minio.messages.Retention;
import io.minio.messages.SelectObjectContentRequest;
import io.minio.messages.SseConfiguration;
import io.minio.messages.Tags;
import io.minio.messages.VersioningConfiguration;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.xerial.snappy.SnappyFramedOutputStream;

public class MinioAsyncClient
extends S3Base {
    private MinioAsyncClient(HttpUrl baseUrl, String awsS3Prefix, String awsDomainSuffix, boolean awsDualstack, boolean useVirtualStyle, String region, Provider provider, OkHttpClient httpClient, boolean closeHttpClient) {
        super(baseUrl, awsS3Prefix, awsDomainSuffix, awsDualstack, useVirtualStyle, region, provider, httpClient, closeHttpClient);
    }

    protected MinioAsyncClient(MinioAsyncClient client) {
        super(client);
    }

    public CompletableFuture<StatObjectResponse> statObject(StatObjectArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        return super.statObjectAsync(args2);
    }

    public CompletableFuture<GetObjectResponse> getObject(GetObjectArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        args2.validateSsec(this.baseUrl);
        return this.executeGetAsync(args2, args2.getHeaders(), args2.versionId() != null ? this.newMultimap("versionId", args2.versionId()) : null).thenApply(response -> new GetObjectResponse(response.headers(), args2.bucket(), args2.region(), args2.object(), response.body().byteStream()));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void downloadObject(String filename, boolean overwrite, StatObjectResponse statObjectResponse, GetObjectResponse getObjectResponse) throws IOException {
        OutputStream os = null;
        try {
            long bytesWritten;
            Path filePath = Paths.get(filename, new String[0]);
            String tempFilename = filename + "." + S3Escaper.encode(statObjectResponse.etag()) + ".part.minio";
            Path tempFilePath = Paths.get(tempFilename, new String[0]);
            if (Files.exists(tempFilePath, new LinkOption[0])) {
                Files.delete(tempFilePath);
            }
            if ((bytesWritten = ByteStreams.copy(getObjectResponse, os = Files.newOutputStream(tempFilePath, StandardOpenOption.CREATE, StandardOpenOption.WRITE))) != statObjectResponse.size()) {
                throw new IOException(tempFilename + ": unexpected data written.  expected = " + statObjectResponse.size() + ", written = " + bytesWritten);
            }
            if (overwrite) {
                Files.move(tempFilePath, filePath, StandardCopyOption.REPLACE_EXISTING);
            } else {
                Files.move(tempFilePath, filePath, new CopyOption[0]);
            }
        }
        finally {
            getObjectResponse.close();
            if (os != null) {
                os.close();
            }
        }
    }

    public CompletableFuture<Void> downloadObject(DownloadObjectArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        String filename = args2.filename();
        Path filePath = Paths.get(filename, new String[0]);
        if (!args2.overwrite() && Files.exists(filePath, new LinkOption[0])) {
            throw new IllegalArgumentException("Destination file " + filename + " already exists");
        }
        return ((CompletableFuture)this.statObjectAsync(new StatObjectArgs(args2)).thenCombine(this.getObject(new GetObjectArgs(args2)), (statObjectResponse, getObjectResponse) -> {
            try {
                this.downloadObject(filename, args2.overwrite(), (StatObjectResponse)statObjectResponse, (GetObjectResponse)getObjectResponse);
                return null;
            }
            catch (IOException e) {
                throw new CompletionException(e);
            }
        })).thenAccept(nullValue -> {});
    }

    public CompletableFuture<ObjectWriteResponse> copyObject(CopyObjectArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        args2.validateSse(this.baseUrl);
        return ((CompletableFuture)((CompletableFuture)((CompletableFuture)CompletableFuture.supplyAsync(() -> args2.source().offset() != null && args2.source().length() != null).thenCompose(condition -> {
            if (condition.booleanValue()) {
                try {
                    return this.statObjectAsync(new StatObjectArgs(args2.source()));
                }
                catch (InsufficientDataException | InternalException | XmlParserException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                    throw new CompletionException(e);
                }
            }
            return CompletableFuture.completedFuture(null);
        })).thenApply(stat -> stat == null ? -1L : stat.size())).thenCompose(size -> {
            if (args2.source().offset() != null || args2.source().length() != null || size > 0x140000000L) {
                if (args2.metadataDirective() != null && args2.metadataDirective() == Directive.COPY) {
                    throw new IllegalArgumentException("COPY metadata directive is not applicable to source object size greater than 5 GiB");
                }
                if (args2.taggingDirective() != null && args2.taggingDirective() == Directive.COPY) {
                    throw new IllegalArgumentException("COPY tagging directive is not applicable to source object size greater than 5 GiB");
                }
                try {
                    return this.composeObject(new ComposeObjectArgs(args2));
                }
                catch (InsufficientDataException | InternalException | XmlParserException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                    throw new CompletionException(e);
                }
            }
            return CompletableFuture.completedFuture(null);
        })).thenCompose(objectWriteResponse -> {
            if (objectWriteResponse != null) {
                return CompletableFuture.completedFuture(objectWriteResponse);
            }
            Multimap<String, String> headers = args2.genHeaders();
            if (args2.metadataDirective() != null) {
                headers.put("x-amz-metadata-directive", args2.metadataDirective().name());
            }
            if (args2.taggingDirective() != null) {
                headers.put("x-amz-tagging-directive", args2.taggingDirective().name());
            }
            headers.putAll(args2.source().genCopyHeaders());
            try {
                return this.executePutAsync(args2, headers, null, null, 0).thenApply(response -> {
                    try {
                        CopyObjectResult result = Xml.unmarshal(CopyObjectResult.class, response.body().charStream());
                        ObjectWriteResponse objectWriteResponse = new ObjectWriteResponse(response.headers(), args2.bucket(), args2.region(), args2.object(), result.etag(), response.header("x-amz-version-id"));
                        return objectWriteResponse;
                    }
                    catch (XmlParserException e) {
                        throw new CompletionException(e);
                    }
                    finally {
                        response.close();
                    }
                });
            }
            catch (InsufficientDataException | InternalException | XmlParserException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                throw new CompletionException(e);
            }
        });
    }

    private CompletableFuture<Part[]> uploadPartCopy(String bucketName, String region, String objectName, String uploadId, int partNumber, Multimap<String, String> headers, Part[] parts) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        return this.uploadPartCopyAsync(bucketName, region, objectName, uploadId, partNumber, headers, null).thenApply(uploadPartCopyResponse -> {
            parts[partNumber - 1] = new Part(partNumber, uploadPartCopyResponse.result().etag());
            return parts;
        });
    }

    public CompletableFuture<ObjectWriteResponse> composeObject(ComposeObjectArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        args2.validateSse(this.baseUrl);
        List<ComposeSource> sources = args2.sources();
        int[] partCount = new int[]{0};
        String[] uploadIdCopy = new String[]{null};
        return ((CompletableFuture)((CompletableFuture)this.calculatePartCountAsync(sources).thenApply(count -> {
            partCount[0] = count;
            return count == 1 && args2.sources().get(0).offset() == null && args2.sources().get(0).length() == null;
        })).thenCompose(copyObjectFlag -> {
            if (copyObjectFlag.booleanValue()) {
                try {
                    return this.copyObject(new CopyObjectArgs(args2));
                }
                catch (InsufficientDataException | InternalException | XmlParserException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                    throw new CompletionException(e);
                }
            }
            return CompletableFuture.completedFuture(null);
        })).thenCompose(objectWriteResponse -> {
            if (objectWriteResponse != null) {
                return CompletableFuture.completedFuture(objectWriteResponse);
            }
            CompletionStage completableFuture = ((CompletableFuture)((CompletableFuture)((CompletableFuture)CompletableFuture.supplyAsync(() -> {
                Multimap<String, String> headers = this.newMultimap(args2.extraHeaders());
                headers.putAll(args2.genHeaders());
                return headers;
            }).thenCompose(headers -> {
                try {
                    return this.createMultipartUploadAsync(args2.bucket(), args2.region(), args2.object(), (Multimap<String, String>)headers, args2.extraQueryParams());
                }
                catch (InsufficientDataException | InternalException | XmlParserException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                    throw new CompletionException(e);
                }
            })).thenApply(createMultipartUploadResponse -> {
                String uploadId;
                uploadIdCopy[0] = uploadId = createMultipartUploadResponse.result().uploadId();
                return uploadId;
            })).thenCompose(uploadId -> {
                HashMultimap<String, String> ssecHeaders = HashMultimap.create();
                if (args2.sse() != null && args2.sse() instanceof ServerSideEncryptionCustomerKey) {
                    ssecHeaders.putAll(this.newMultimap(args2.sse().headers()));
                }
                int partNumber = 0;
                CompletionStage<Object> future = CompletableFuture.supplyAsync(() -> new Part[partCount[0]]);
                for (ComposeSource src : sources) {
                    Multimap<String, String> headers;
                    long size = 0L;
                    try {
                        size = src.objectSize();
                    }
                    catch (InternalException e) {
                        throw new CompletionException(e);
                    }
                    if (src.length() != null) {
                        size = src.length();
                    } else if (src.offset() != null) {
                        size -= src.offset().longValue();
                    }
                    long offset = 0L;
                    if (src.offset() != null) {
                        offset = src.offset();
                    }
                    try {
                        headers = this.newMultimap(src.headers());
                    }
                    catch (InternalException e) {
                        throw new CompletionException(e);
                    }
                    headers.putAll(ssecHeaders);
                    if (size <= 0x140000000L) {
                        ++partNumber;
                        if (src.length() != null) {
                            headers.put("x-amz-copy-source-range", "bytes=" + offset + "-" + (offset + src.length() - 1L));
                        } else if (src.offset() != null) {
                            headers.put("x-amz-copy-source-range", "bytes=" + offset + "-" + (offset + size - 1L));
                        }
                        int partNum = partNumber;
                        future = ((CompletableFuture)future).thenCompose(parts -> {
                            try {
                                return this.uploadPartCopy(args2.bucket(), args2.region(), args2.object(), (String)uploadId, partNum, headers, (Part[])parts);
                            }
                            catch (InsufficientDataException | InternalException | XmlParserException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                                throw new CompletionException(e);
                            }
                        });
                        continue;
                    }
                    while (size > 0L) {
                        ++partNumber;
                        long length = size;
                        if (length > 0x140000000L) {
                            length = 0x140000000L;
                        }
                        long endBytes = offset + length - 1L;
                        Multimap<String, String> headersCopy = this.newMultimap(headers);
                        headersCopy.put("x-amz-copy-source-range", "bytes=" + offset + "-" + endBytes);
                        int partNum = partNumber;
                        future = ((CompletableFuture)future).thenCompose(parts -> {
                            try {
                                return this.uploadPartCopy(args2.bucket(), args2.region(), args2.object(), (String)uploadId, partNum, headersCopy, (Part[])parts);
                            }
                            catch (InsufficientDataException | InternalException | XmlParserException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                                throw new CompletionException(e);
                            }
                        });
                        offset += length;
                        size -= length;
                    }
                }
                return future;
            })).thenCompose(parts -> {
                try {
                    return this.completeMultipartUploadAsync(args2.bucket(), args2.region(), args2.object(), uploadIdCopy[0], (Part[])parts, null, null);
                }
                catch (InsufficientDataException | InternalException | XmlParserException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                    throw new CompletionException(e);
                }
            });
            ((CompletableFuture)completableFuture).exceptionally(e -> {
                if (uploadIdCopy[0] != null) {
                    try {
                        this.abortMultipartUploadAsync(args2.bucket(), args2.region(), args2.object(), uploadIdCopy[0], null, null).get();
                    }
                    catch (InsufficientDataException | InternalException | XmlParserException | IOException | InterruptedException | InvalidKeyException | NoSuchAlgorithmException | ExecutionException ex) {
                        throw new CompletionException(ex);
                    }
                }
                throw new CompletionException((Throwable)e);
            });
            return completableFuture;
        });
    }

    public String getPresignedObjectUrl(GetPresignedObjectUrlArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, XmlParserException, ServerException {
        this.checkArgs(args2);
        byte[] body2 = (byte[])(args2.method() == Method.PUT || args2.method() == Method.POST ? HttpUtils.EMPTY_BODY : null);
        Multimap<String, String> queryParams2 = this.newMultimap(args2.extraQueryParams());
        if (args2.versionId() != null) {
            queryParams2.put("versionId", args2.versionId());
        }
        String region = null;
        try {
            region = this.getRegionAsync(args2.bucket(), args2.region()).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.throwEncapsulatedException(e);
        }
        if (this.provider == null) {
            HttpUrl url2 = this.buildUrl(args2.method(), args2.bucket(), args2.object(), region, queryParams2);
            return url2.toString();
        }
        Credentials creds = this.provider.fetch();
        if (creds.sessionToken() != null) {
            queryParams2.put("X-Amz-Security-Token", creds.sessionToken());
        }
        HttpUrl url3 = this.buildUrl(args2.method(), args2.bucket(), args2.object(), region, queryParams2);
        Request request = this.createRequest(url3, args2.method(), args2.extraHeaders() == null ? null : this.httpHeaders(args2.extraHeaders()), body2, 0, creds);
        url3 = Signer.presignV4(request, region, creds.accessKey(), creds.secretKey(), args2.expiry());
        return url3.toString();
    }

    public Map<String, String> getPresignedPostFormData(PostPolicy policy) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        if (this.provider == null) {
            throw new IllegalArgumentException("Anonymous access does not require presigned post form-data");
        }
        String region = null;
        try {
            region = this.getRegionAsync(policy.bucket(), null).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.throwEncapsulatedException(e);
        }
        return policy.formData(this.provider.fetch(), region);
    }

    public CompletableFuture<Void> removeObject(RemoveObjectArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        return this.executeDeleteAsync(args2, args2.bypassGovernanceMode() ? this.newMultimap("x-amz-bypass-governance-retention", "true") : null, args2.versionId() != null ? this.newMultimap("versionId", args2.versionId()) : null).thenAccept(response -> response.close());
    }

    public Iterable<Result<DeleteError>> removeObjects(final RemoveObjectsArgs args2) {
        this.checkArgs(args2);
        return new Iterable<Result<DeleteError>>(){

            @Override
            public Iterator<Result<DeleteError>> iterator() {
                return new Iterator<Result<DeleteError>>(){
                    private Result<DeleteError> error = null;
                    private Iterator<DeleteError> errorIterator = null;
                    private boolean completed = false;
                    private Iterator<DeleteObject> objectIter;
                    {
                        this.objectIter = args2.objects().iterator();
                    }

                    private void setError() {
                        this.error = null;
                        while (this.errorIterator.hasNext()) {
                            DeleteError deleteError = this.errorIterator.next();
                            if ("NoSuchVersion".equals(deleteError.code())) continue;
                            this.error = new Result<DeleteError>(deleteError);
                            break;
                        }
                    }

                    private synchronized void populate() {
                        if (this.completed) {
                            return;
                        }
                        try {
                            LinkedList<DeleteObject> objectList = new LinkedList<DeleteObject>();
                            while (this.objectIter.hasNext() && objectList.size() < 1000) {
                                objectList.add(this.objectIter.next());
                            }
                            this.completed = objectList.isEmpty();
                            if (this.completed) {
                                return;
                            }
                            DeleteObjectsResponse response = null;
                            try {
                                response = MinioAsyncClient.this.deleteObjectsAsync(args2.bucket(), args2.region(), objectList, true, args2.bypassGovernanceMode(), args2.extraHeaders(), args2.extraQueryParams()).get();
                            }
                            catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            catch (ExecutionException e) {
                                MinioAsyncClient.this.throwEncapsulatedException(e);
                            }
                            if (!response.result().errorList().isEmpty()) {
                                this.errorIterator = response.result().errorList().iterator();
                                this.setError();
                                this.completed = true;
                            }
                        }
                        catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidResponseException | ServerException | XmlParserException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                            this.error = new Result(e);
                            this.completed = true;
                        }
                    }

                    @Override
                    public boolean hasNext() {
                        while (this.error == null && this.errorIterator == null && !this.completed) {
                            this.populate();
                        }
                        if (this.error == null && this.errorIterator != null) {
                            this.setError();
                        }
                        if (this.error != null) {
                            return true;
                        }
                        if (this.completed) {
                            return false;
                        }
                        this.errorIterator = null;
                        return this.hasNext();
                    }

                    @Override
                    public Result<DeleteError> next() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        if (this.error != null) {
                            Result<DeleteError> error = this.error;
                            this.error = null;
                            return error;
                        }
                        throw new NoSuchElementException();
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }

    public CompletableFuture<Void> restoreObject(RestoreObjectArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        return this.executePostAsync(args2, null, this.newMultimap("restore", ""), args2.request()).thenAccept(response -> response.close());
    }

    public Iterable<Result<Item>> listObjects(ListObjectsArgs args2) {
        if (args2.includeVersions() || args2.versionIdMarker() != null) {
            return this.listObjectVersions(args2);
        }
        if (args2.useApiVersion1()) {
            return this.listObjectsV1(args2);
        }
        return this.listObjectsV2(args2);
    }

    public CompletableFuture<List<Bucket>> listBuckets() throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        return this.listBuckets((ListBucketsArgs)ListBucketsArgs.builder().build());
    }

    public CompletableFuture<List<Bucket>> listBuckets(ListBucketsArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        return this.executeGetAsync(args2, null, null).thenApply(response -> {
            try {
                ListAllMyBucketsResult result = Xml.unmarshal(ListAllMyBucketsResult.class, response.body().charStream());
                List<Bucket> list = result.buckets();
                return list;
            }
            catch (XmlParserException e) {
                throw new CompletionException(e);
            }
            finally {
                response.close();
            }
        });
    }

    public CompletableFuture<Boolean> bucketExists(BucketExistsArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        return ((CompletableFuture)this.executeHeadAsync(args2, null, null).exceptionally(e -> {
            Throwable ex = e.getCause();
            if (ex instanceof CompletionException) {
                ex = ((CompletionException)ex).getCause();
            }
            if (ex instanceof ExecutionException) {
                ex = ((ExecutionException)ex).getCause();
            }
            if (ex instanceof ErrorResponseException && ((ErrorResponseException)ex).errorResponse().code().equals("NoSuchBucket")) {
                return null;
            }
            throw new CompletionException(ex);
        })).thenApply(response -> {
            try {
                Boolean bl = response != null;
                return bl;
            }
            finally {
                if (response != null) {
                    response.close();
                }
            }
        });
    }

    public CompletableFuture<Void> makeBucket(MakeBucketArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        String region = args2.region();
        if (this.region != null && !this.region.isEmpty()) {
            if (region != null && !region.equals(this.region)) {
                throw new IllegalArgumentException("region must be " + this.region + ", but passed " + region);
            }
            region = this.region;
        }
        if (region == null) {
            region = "us-east-1";
        }
        Multimap<String, String> headers = args2.objectLock() ? this.newMultimap("x-amz-bucket-object-lock-enabled", "true") : null;
        String location = region;
        return this.executeAsync(Method.PUT, args2.bucket(), null, location, this.httpHeaders(this.merge(args2.extraHeaders(), headers)), args2.extraQueryParams(), location.equals("us-east-1") ? null : new CreateBucketConfiguration(location), 0).thenAccept(response -> {
            this.regionCache.put(args2.bucket(), location);
            response.close();
        });
    }

    public CompletableFuture<Void> setBucketVersioning(SetBucketVersioningArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        return this.executePutAsync(args2, null, this.newMultimap("versioning", ""), args2.config(), 0).thenAccept(response -> response.close());
    }

    public CompletableFuture<VersioningConfiguration> getBucketVersioning(GetBucketVersioningArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        return this.executeGetAsync(args2, null, this.newMultimap("versioning", "")).thenApply(response -> {
            try {
                VersioningConfiguration versioningConfiguration = Xml.unmarshal(VersioningConfiguration.class, response.body().charStream());
                return versioningConfiguration;
            }
            catch (XmlParserException e) {
                throw new CompletionException(e);
            }
            finally {
                response.close();
            }
        });
    }

    public CompletableFuture<Void> setObjectLockConfiguration(SetObjectLockConfigurationArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        return this.executePutAsync(args2, null, this.newMultimap("object-lock", ""), args2.config(), 0).thenAccept(response -> response.close());
    }

    public CompletableFuture<Void> deleteObjectLockConfiguration(DeleteObjectLockConfigurationArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        return this.executePutAsync(args2, null, this.newMultimap("object-lock", ""), new ObjectLockConfiguration(), 0).thenAccept(response -> response.close());
    }

    public CompletableFuture<ObjectLockConfiguration> getObjectLockConfiguration(GetObjectLockConfigurationArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        return this.executeGetAsync(args2, null, this.newMultimap("object-lock", "")).thenApply(response -> {
            try {
                ObjectLockConfiguration objectLockConfiguration = Xml.unmarshal(ObjectLockConfiguration.class, response.body().charStream());
                return objectLockConfiguration;
            }
            catch (XmlParserException e) {
                throw new CompletionException(e);
            }
            finally {
                response.close();
            }
        });
    }

    public CompletableFuture<Void> setObjectRetention(SetObjectRetentionArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        Multimap<String, String> queryParams2 = this.newMultimap("retention", "");
        if (args2.versionId() != null) {
            queryParams2.put("versionId", args2.versionId());
        }
        return this.executePutAsync(args2, args2.bypassGovernanceMode() ? this.newMultimap("x-amz-bypass-governance-retention", "True") : null, queryParams2, args2.config(), 0).thenAccept(response -> response.close());
    }

    public CompletableFuture<Retention> getObjectRetention(GetObjectRetentionArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        Multimap<String, String> queryParams2 = this.newMultimap("retention", "");
        if (args2.versionId() != null) {
            queryParams2.put("versionId", args2.versionId());
        }
        return ((CompletableFuture)this.executeGetAsync(args2, null, queryParams2).exceptionally(e -> {
            Throwable ex = e.getCause();
            if (ex instanceof CompletionException) {
                ex = ((CompletionException)ex).getCause();
            }
            if (ex instanceof ExecutionException) {
                ex = ((ExecutionException)ex).getCause();
            }
            if (ex instanceof ErrorResponseException && ((ErrorResponseException)ex).errorResponse().code().equals("NoSuchObjectLockConfiguration")) {
                return null;
            }
            throw new CompletionException(ex);
        })).thenApply(response -> {
            if (response == null) {
                return null;
            }
            try {
                Retention retention = Xml.unmarshal(Retention.class, response.body().charStream());
                return retention;
            }
            catch (XmlParserException e) {
                throw new CompletionException(e);
            }
            finally {
                response.close();
            }
        });
    }

    public CompletableFuture<Void> enableObjectLegalHold(EnableObjectLegalHoldArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        Multimap<String, String> queryParams2 = this.newMultimap("legal-hold", "");
        if (args2.versionId() != null) {
            queryParams2.put("versionId", args2.versionId());
        }
        return this.executePutAsync(args2, null, queryParams2, new LegalHold(true), 0).thenAccept(response -> response.close());
    }

    public CompletableFuture<Void> disableObjectLegalHold(DisableObjectLegalHoldArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        Multimap<String, String> queryParams2 = this.newMultimap("legal-hold", "");
        if (args2.versionId() != null) {
            queryParams2.put("versionId", args2.versionId());
        }
        return this.executePutAsync(args2, null, queryParams2, new LegalHold(false), 0).thenAccept(response -> response.close());
    }

    public CompletableFuture<Boolean> isObjectLegalHoldEnabled(IsObjectLegalHoldEnabledArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        Multimap<String, String> queryParams2 = this.newMultimap("legal-hold", "");
        if (args2.versionId() != null) {
            queryParams2.put("versionId", args2.versionId());
        }
        return ((CompletableFuture)this.executeGetAsync(args2, null, queryParams2).exceptionally(e -> {
            Throwable ex = e.getCause();
            if (ex instanceof CompletionException) {
                ex = ((CompletionException)ex).getCause();
            }
            if (ex instanceof ExecutionException) {
                ex = ((ExecutionException)ex).getCause();
            }
            if (ex instanceof ErrorResponseException && ((ErrorResponseException)ex).errorResponse().code().equals("NoSuchObjectLockConfiguration")) {
                return null;
            }
            throw new CompletionException(ex);
        })).thenApply(response -> {
            if (response == null) {
                return false;
            }
            try {
                LegalHold result = Xml.unmarshal(LegalHold.class, response.body().charStream());
                Boolean bl = result.status();
                return bl;
            }
            catch (XmlParserException e) {
                throw new CompletionException(e);
            }
            finally {
                response.close();
            }
        });
    }

    public CompletableFuture<Void> removeBucket(RemoveBucketArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        return this.executeDeleteAsync(args2, null, null).thenAccept(response -> {
            String cfr_ignored_0 = (String)this.regionCache.remove(args2.bucket());
        });
    }

    public CompletableFuture<ObjectWriteResponse> putObject(PutObjectArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        args2.validateSse(this.baseUrl);
        return this.putObjectAsync(args2, args2.stream(), args2.objectSize(), args2.partSize(), args2.partCount(), args2.contentType());
    }

    public CompletableFuture<ObjectWriteResponse> uploadObject(UploadObjectArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        args2.validateSse(this.baseUrl);
        RandomAccessFile file = new RandomAccessFile(args2.filename(), "r");
        return ((CompletableFuture)this.putObjectAsync(args2, file, args2.objectSize(), args2.partSize(), args2.partCount(), args2.contentType()).exceptionally(e -> {
            try {
                file.close();
            }
            catch (IOException ex) {
                throw new CompletionException(ex);
            }
            Throwable ex = e.getCause();
            if (ex instanceof CompletionException) {
                ex = ((CompletionException)ex).getCause();
            }
            if (ex instanceof ExecutionException) {
                ex = ((ExecutionException)ex).getCause();
            }
            throw new CompletionException(ex);
        })).thenApply(objectWriteResponse -> {
            try {
                file.close();
            }
            catch (IOException e) {
                throw new CompletionException(e);
            }
            return objectWriteResponse;
        });
    }

    public CompletableFuture<String> getBucketPolicy(GetBucketPolicyArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        return ((CompletableFuture)this.executeGetAsync(args2, null, this.newMultimap("policy", "")).exceptionally(e -> {
            Throwable ex = e.getCause();
            if (ex instanceof CompletionException) {
                ex = ((CompletionException)ex).getCause();
            }
            if (ex instanceof ExecutionException) {
                ex = ((ExecutionException)ex).getCause();
            }
            if (ex instanceof ErrorResponseException && ((ErrorResponseException)ex).errorResponse().code().equals("NoSuchBucketPolicy")) {
                return null;
            }
            throw new CompletionException(ex);
        })).thenApply(response -> {
            if (response == null) {
                return "";
            }
            try {
                byte[] buf = new byte[20480];
                int bytesRead = 0;
                bytesRead = response.body().byteStream().read(buf, 0, 20480);
                if (bytesRead < 0) {
                    throw new CompletionException(new IOException("unexpected EOF when reading bucket policy"));
                }
                if (bytesRead == 20480) {
                    int byteRead = 0;
                    while (byteRead == 0 && (byteRead = response.body().byteStream().read()) >= 0) {
                        if (byteRead <= 0) continue;
                        throw new CompletionException(new BucketPolicyTooLargeException(args2.bucket()));
                    }
                }
                String string = new String(buf, 0, bytesRead, StandardCharsets.UTF_8);
                return string;
            }
            catch (IOException e) {
                throw new CompletionException(e);
            }
            finally {
                response.close();
            }
        });
    }

    public CompletableFuture<Void> setBucketPolicy(SetBucketPolicyArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        return this.executePutAsync(args2, this.newMultimap("Content-Type", "application/json"), this.newMultimap("policy", ""), args2.config(), 0).thenAccept(response -> response.close());
    }

    public CompletableFuture<Void> deleteBucketPolicy(DeleteBucketPolicyArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        return ((CompletableFuture)this.executeDeleteAsync(args2, null, this.newMultimap("policy", "")).exceptionally(e -> {
            Throwable ex = e.getCause();
            if (ex instanceof CompletionException) {
                ex = ((CompletionException)ex).getCause();
            }
            if (ex instanceof ExecutionException) {
                ex = ((ExecutionException)ex).getCause();
            }
            if (ex instanceof ErrorResponseException && ((ErrorResponseException)ex).errorResponse().code().equals("NoSuchBucketPolicy")) {
                return null;
            }
            throw new CompletionException(ex);
        })).thenAccept(response -> {
            if (response != null) {
                response.close();
            }
        });
    }

    public CompletableFuture<Void> setBucketLifecycle(SetBucketLifecycleArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        return this.executePutAsync(args2, null, this.newMultimap("lifecycle", ""), args2.config(), 0).thenAccept(response -> response.close());
    }

    public CompletableFuture<Void> deleteBucketLifecycle(DeleteBucketLifecycleArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        return this.executeDeleteAsync(args2, null, this.newMultimap("lifecycle", "")).thenAccept(response -> response.close());
    }

    public CompletableFuture<LifecycleConfiguration> getBucketLifecycle(GetBucketLifecycleArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        return ((CompletableFuture)this.executeGetAsync(args2, null, this.newMultimap("lifecycle", "")).exceptionally(e -> {
            Throwable ex = e.getCause();
            if (ex instanceof CompletionException) {
                ex = ((CompletionException)ex).getCause();
            }
            if (ex instanceof ExecutionException) {
                ex = ((ExecutionException)ex).getCause();
            }
            if (ex instanceof ErrorResponseException && ((ErrorResponseException)ex).errorResponse().code().equals("NoSuchLifecycleConfiguration")) {
                return null;
            }
            throw new CompletionException(ex);
        })).thenApply(response -> {
            if (response == null) {
                return null;
            }
            try {
                LifecycleConfiguration lifecycleConfiguration = Xml.unmarshal(LifecycleConfiguration.class, response.body().charStream());
                return lifecycleConfiguration;
            }
            catch (XmlParserException e) {
                throw new CompletionException(e);
            }
            finally {
                response.close();
            }
        });
    }

    public CompletableFuture<NotificationConfiguration> getBucketNotification(GetBucketNotificationArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        return this.executeGetAsync(args2, null, this.newMultimap("notification", "")).thenApply(response -> {
            try {
                NotificationConfiguration notificationConfiguration = Xml.unmarshal(NotificationConfiguration.class, response.body().charStream());
                return notificationConfiguration;
            }
            catch (XmlParserException e) {
                throw new CompletionException(e);
            }
            finally {
                response.close();
            }
        });
    }

    public CompletableFuture<Void> setBucketNotification(SetBucketNotificationArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        return this.executePutAsync(args2, null, this.newMultimap("notification", ""), args2.config(), 0).thenAccept(response -> response.close());
    }

    public CompletableFuture<Void> deleteBucketNotification(DeleteBucketNotificationArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        return this.executePutAsync(args2, null, this.newMultimap("notification", ""), new NotificationConfiguration(), 0).thenAccept(response -> response.close());
    }

    public CompletableFuture<ReplicationConfiguration> getBucketReplication(GetBucketReplicationArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        return ((CompletableFuture)this.executeGetAsync(args2, null, this.newMultimap("replication", "")).exceptionally(e -> {
            Throwable ex = e.getCause();
            if (ex instanceof CompletionException) {
                ex = ((CompletionException)ex).getCause();
            }
            if (ex instanceof ExecutionException) {
                ex = ((ExecutionException)ex).getCause();
            }
            if (ex instanceof ErrorResponseException && ((ErrorResponseException)ex).errorResponse().code().equals("ReplicationConfigurationNotFoundError")) {
                return null;
            }
            throw new CompletionException(ex);
        })).thenApply(response -> {
            if (response == null) {
                return null;
            }
            try {
                ReplicationConfiguration replicationConfiguration = Xml.unmarshal(ReplicationConfiguration.class, response.body().charStream());
                return replicationConfiguration;
            }
            catch (XmlParserException e) {
                throw new CompletionException(e);
            }
            finally {
                response.close();
            }
        });
    }

    public CompletableFuture<Void> setBucketReplication(SetBucketReplicationArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        return this.executePutAsync(args2, args2.objectLockToken() != null ? this.newMultimap("x-amz-bucket-object-lock-token", args2.objectLockToken()) : null, this.newMultimap("replication", ""), args2.config(), 0).thenAccept(response -> response.close());
    }

    public CompletableFuture<Void> deleteBucketReplication(DeleteBucketReplicationArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        return this.executeDeleteAsync(args2, null, this.newMultimap("replication", "")).thenAccept(response -> response.close());
    }

    public CloseableIterator<Result<NotificationRecords>> listenBucketNotification(ListenBucketNotificationArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        this.checkArgs(args2);
        Multimap<String, String> queryParams2 = this.newMultimap("prefix", args2.prefix(), "suffix", args2.suffix());
        for (String event : args2.events()) {
            queryParams2.put("events", event);
        }
        Response response = null;
        try {
            response = this.executeGetAsync(args2, null, queryParams2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.throwEncapsulatedException(e);
        }
        S3Base.NotificationResultRecords result = new S3Base.NotificationResultRecords(response);
        return result.closeableIterator();
    }

    public SelectResponseStream selectObjectContent(SelectObjectContentArgs args2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        this.checkArgs(args2);
        args2.validateSsec(this.baseUrl);
        Response response = null;
        try {
            response = this.executePostAsync(args2, args2.ssec() != null ? this.newMultimap(args2.ssec().headers()) : null, this.newMultimap("select", "", "select-type", "2"), new SelectObjectContentRequest(args2.sqlExpression(), args2.requestProgress(), args2.inputSerialization(), args2.outputSerialization(), args2.scanStartRange(), args2.scanEndRange())).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.throwEncapsulatedException(e);
        }
        return new SelectResponseStream(response.body().byteStream());
    }

    public CompletableFuture<Void> setBucketEncryption(SetBucketEncryptionArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        return this.executePutAsync(args2, null, this.newMultimap("encryption", ""), args2.config(), 0).thenAccept(response -> response.close());
    }

    public CompletableFuture<SseConfiguration> getBucketEncryption(GetBucketEncryptionArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        return ((CompletableFuture)this.executeGetAsync(args2, null, this.newMultimap("encryption", "")).exceptionally(e -> {
            Throwable ex = e.getCause();
            if (ex instanceof CompletionException) {
                ex = ((CompletionException)ex).getCause();
            }
            if (ex instanceof ExecutionException) {
                ex = ((ExecutionException)ex).getCause();
            }
            if (ex instanceof ErrorResponseException && ((ErrorResponseException)ex).errorResponse().code().equals("ServerSideEncryptionConfigurationNotFoundError")) {
                return null;
            }
            throw new CompletionException(ex);
        })).thenApply(response -> {
            if (response == null) {
                return new SseConfiguration(null);
            }
            try {
                SseConfiguration sseConfiguration = Xml.unmarshal(SseConfiguration.class, response.body().charStream());
                return sseConfiguration;
            }
            catch (XmlParserException e) {
                throw new CompletionException(e);
            }
            finally {
                response.close();
            }
        });
    }

    public CompletableFuture<Void> deleteBucketEncryption(DeleteBucketEncryptionArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        return ((CompletableFuture)this.executeDeleteAsync(args2, null, this.newMultimap("encryption", "")).exceptionally(e -> {
            Throwable ex = e.getCause();
            if (ex instanceof CompletionException) {
                ex = ((CompletionException)ex).getCause();
            }
            if (ex instanceof ExecutionException) {
                ex = ((ExecutionException)ex).getCause();
            }
            if (ex instanceof ErrorResponseException && ((ErrorResponseException)ex).errorResponse().code().equals("ServerSideEncryptionConfigurationNotFoundError")) {
                return null;
            }
            throw new CompletionException(ex);
        })).thenAccept(response -> {
            if (response != null) {
                response.close();
            }
        });
    }

    public CompletableFuture<Tags> getBucketTags(GetBucketTagsArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        return ((CompletableFuture)this.executeGetAsync(args2, null, this.newMultimap("tagging", "")).exceptionally(e -> {
            Throwable ex = e.getCause();
            if (ex instanceof CompletionException) {
                ex = ((CompletionException)ex).getCause();
            }
            if (ex instanceof ExecutionException) {
                ex = ((ExecutionException)ex).getCause();
            }
            if (ex instanceof ErrorResponseException && ((ErrorResponseException)ex).errorResponse().code().equals("NoSuchTagSet")) {
                return null;
            }
            throw new CompletionException(ex);
        })).thenApply(response -> {
            if (response == null) {
                return new Tags();
            }
            try {
                Tags tags = Xml.unmarshal(Tags.class, response.body().charStream());
                return tags;
            }
            catch (XmlParserException e) {
                throw new CompletionException(e);
            }
            finally {
                response.close();
            }
        });
    }

    public CompletableFuture<Void> setBucketTags(SetBucketTagsArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        return this.executePutAsync(args2, null, this.newMultimap("tagging", ""), args2.tags(), 0).thenAccept(response -> response.close());
    }

    public CompletableFuture<Void> deleteBucketTags(DeleteBucketTagsArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        return this.executeDeleteAsync(args2, null, this.newMultimap("tagging", "")).thenAccept(response -> response.close());
    }

    public CompletableFuture<Tags> getObjectTags(GetObjectTagsArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        Multimap<String, String> queryParams2 = this.newMultimap("tagging", "");
        if (args2.versionId() != null) {
            queryParams2.put("versionId", args2.versionId());
        }
        return this.executeGetAsync(args2, null, queryParams2).thenApply(response -> {
            try {
                Tags tags = Xml.unmarshal(Tags.class, response.body().charStream());
                return tags;
            }
            catch (XmlParserException e) {
                throw new CompletionException(e);
            }
            finally {
                response.close();
            }
        });
    }

    public CompletableFuture<Void> setObjectTags(SetObjectTagsArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        Multimap<String, String> queryParams2 = this.newMultimap("tagging", "");
        if (args2.versionId() != null) {
            queryParams2.put("versionId", args2.versionId());
        }
        return this.executePutAsync(args2, null, queryParams2, args2.tags(), 0).thenAccept(response -> response.close());
    }

    public CompletableFuture<Void> deleteObjectTags(DeleteObjectTagsArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        Multimap<String, String> queryParams2 = this.newMultimap("tagging", "");
        if (args2.versionId() != null) {
            queryParams2.put("versionId", args2.versionId());
        }
        return this.executeDeleteAsync(args2, null, queryParams2).thenAccept(response -> response.close());
    }

    public CompletableFuture<ObjectWriteResponse> uploadSnowballObjects(UploadSnowballObjectsArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        return CompletableFuture.supplyAsync(() -> {
            FileOutputStream fos = null;
            BufferedOutputStream bos = null;
            SnappyFramedOutputStream sos = null;
            ByteArrayOutputStream baos = null;
            TarArchiveOutputStream tarOutputStream = null;
            try {
                OutputStream os = null;
                if (args2.stagingFilename() != null) {
                    fos = new FileOutputStream(args2.stagingFilename());
                    bos = new BufferedOutputStream(fos);
                    os = bos;
                } else {
                    os = baos = new ByteArrayOutputStream();
                }
                if (args2.compression()) {
                    sos = new SnappyFramedOutputStream(os);
                    os = sos;
                }
                tarOutputStream = new TarArchiveOutputStream(os);
                tarOutputStream.setLongFileMode(3);
                for (SnowballObject object : args2.objects()) {
                    if (object.filename() != null) {
                        Path filePath = Paths.get(object.filename(), new String[0]);
                        TarArchiveEntry entry = new TarArchiveEntry(filePath.toFile(), object.name());
                        tarOutputStream.putArchiveEntry(entry);
                        Files.copy(filePath, tarOutputStream);
                    } else {
                        TarArchiveEntry entry = new TarArchiveEntry(object.name());
                        if (object.modificationTime() != null) {
                            entry.setModTime(Date.from(object.modificationTime().toInstant()));
                        }
                        entry.setSize(object.size());
                        tarOutputStream.putArchiveEntry(entry);
                        ByteStreams.copy(object.stream(), tarOutputStream);
                    }
                    tarOutputStream.closeArchiveEntry();
                }
                tarOutputStream.finish();
            }
            catch (IOException e) {
                throw new CompletionException(e);
            }
            finally {
                try {
                    if (tarOutputStream != null) {
                        tarOutputStream.flush();
                    }
                    if (sos != null) {
                        sos.flush();
                    }
                    if (bos != null) {
                        bos.flush();
                    }
                    if (fos != null) {
                        fos.flush();
                    }
                    if (tarOutputStream != null) {
                        tarOutputStream.close();
                    }
                    if (sos != null) {
                        sos.close();
                    }
                    if (bos != null) {
                        bos.close();
                    }
                    if (fos != null) {
                        fos.close();
                    }
                }
                catch (IOException e) {
                    throw new CompletionException(e);
                }
            }
            return baos;
        }).thenCompose(baos -> {
            Multimap<String, String> headers = this.newMultimap(args2.extraHeaders());
            headers.putAll(args2.genHeaders());
            headers.put("X-Amz-Meta-Snowball-Auto-Extract", "true");
            if (args2.stagingFilename() == null) {
                byte[] data = baos.toByteArray();
                try {
                    return this.putObjectAsync(args2.bucket(), args2.region(), args2.object(), data, data.length, headers, args2.extraQueryParams());
                }
                catch (InsufficientDataException | InternalException | XmlParserException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                    throw new CompletionException(e);
                }
            }
            long length = Paths.get(args2.stagingFilename(), new String[0]).toFile().length();
            if (length > 0x50000000000L) {
                throw new IllegalArgumentException("tarball size " + length + " is more than maximum allowed 5TiB");
            }
            try (RandomAccessFile file = new RandomAccessFile(args2.stagingFilename(), "r");){
                CompletableFuture<ObjectWriteResponse> completableFuture = this.putObjectAsync(args2.bucket(), args2.region(), args2.object(), file, length, headers, args2.extraQueryParams());
                return completableFuture;
            }
            catch (InsufficientDataException | InternalException | XmlParserException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                throw new CompletionException(e);
            }
        });
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private HttpUrl baseUrl;
        private String awsS3Prefix;
        private String awsDomainSuffix;
        private boolean awsDualstack;
        private boolean useVirtualStyle;
        private String region;
        private Provider provider;
        private OkHttpClient httpClient;

        private void setAwsInfo(String host2, boolean https) {
            this.awsS3Prefix = null;
            this.awsDomainSuffix = null;
            this.awsDualstack = false;
            if (!HttpUtils.HOSTNAME_REGEX.matcher(host2).find()) {
                return;
            }
            if (HttpUtils.AWS_ELB_ENDPOINT_REGEX.matcher(host2).find()) {
                String[] tokens = host2.split("\\.elb\\.amazonaws\\.com", 1)[0].split("\\.");
                this.region = tokens[tokens.length - 1];
                return;
            }
            if (!HttpUtils.AWS_ENDPOINT_REGEX.matcher(host2).find()) {
                return;
            }
            if (!HttpUtils.AWS_S3_ENDPOINT_REGEX.matcher(host2).find()) {
                throw new IllegalArgumentException("invalid Amazon AWS host " + host2);
            }
            Matcher matcher = HttpUtils.AWS_S3_PREFIX_REGEX.matcher(host2);
            matcher.lookingAt();
            int end = matcher.end();
            this.awsS3Prefix = host2.substring(0, end);
            if (this.awsS3Prefix.contains("s3-accesspoint") && !https) {
                throw new IllegalArgumentException("use HTTPS scheme for host " + host2);
            }
            CharSequence[] tokens = host2.substring(end).split("\\.");
            this.awsDualstack = "dualstack".equals(tokens[0]);
            if (this.awsDualstack) {
                tokens = Arrays.copyOfRange(tokens, 1, tokens.length);
            }
            CharSequence regionInHost = null;
            if (!tokens[0].equals("vpce") && !tokens[0].equals("amazonaws")) {
                regionInHost = tokens[0];
                tokens = (String[])Arrays.copyOfRange(tokens, 1, tokens.length);
            }
            this.awsDomainSuffix = String.join((CharSequence)".", tokens);
            if (host2.equals("s3-external-1.amazonaws.com")) {
                regionInHost = "us-east-1";
            }
            if (host2.equals("s3-us-gov-west-1.amazonaws.com") || host2.equals("s3-fips-us-gov-west-1.amazonaws.com")) {
                regionInHost = "us-gov-west-1";
            }
            if (regionInHost != null) {
                this.region = regionInHost;
            }
        }

        private void setBaseUrl(HttpUrl url2) {
            this.baseUrl = url2;
            this.setAwsInfo(url2.host(), url2.isHttps());
            this.useVirtualStyle = this.awsDomainSuffix != null || url2.host().endsWith("aliyuncs.com");
        }

        public Builder endpoint(String endpoint) {
            this.setBaseUrl(HttpUtils.getBaseUrl(endpoint));
            return this;
        }

        public Builder endpoint(String endpoint, int port, boolean secure) {
            HttpUrl url2 = HttpUtils.getBaseUrl(endpoint);
            if (port < 1 || port > 65535) {
                throw new IllegalArgumentException("port must be in range of 1 to 65535");
            }
            url2 = url2.newBuilder().port(port).scheme(secure ? "https" : "http").build();
            this.setBaseUrl(url2);
            return this;
        }

        public Builder endpoint(URL url2) {
            HttpUtils.validateNotNull(url2, "url");
            return this.endpoint(HttpUrl.get(url2));
        }

        public Builder endpoint(HttpUrl url2) {
            HttpUtils.validateNotNull(url2, "url");
            HttpUtils.validateUrl(url2);
            this.setBaseUrl(url2);
            return this;
        }

        public Builder region(String region) {
            if (region != null && !HttpUtils.REGION_REGEX.matcher(region).find()) {
                throw new IllegalArgumentException("invalid region " + region);
            }
            this.region = region;
            return this;
        }

        public Builder credentials(String accessKey, String secretKey) {
            this.provider = new StaticProvider(accessKey, secretKey, null);
            return this;
        }

        public Builder credentialsProvider(Provider provider) {
            this.provider = provider;
            return this;
        }

        public Builder httpClient(OkHttpClient httpClient) {
            HttpUtils.validateNotNull(httpClient, "http client");
            this.httpClient = httpClient;
            return this;
        }

        public MinioAsyncClient build() {
            HttpUtils.validateNotNull(this.baseUrl, "endpoint");
            if (this.awsDomainSuffix != null && this.awsDomainSuffix.endsWith(".cn") && !this.awsS3Prefix.endsWith("s3-accelerate.") && this.region == null) {
                throw new IllegalArgumentException("Region missing in Amazon S3 China endpoint " + this.baseUrl);
            }
            boolean closeHttpClient = false;
            if (this.httpClient == null) {
                this.httpClient = HttpUtils.newDefaultHttpClient(S3Base.DEFAULT_CONNECTION_TIMEOUT, S3Base.DEFAULT_CONNECTION_TIMEOUT, S3Base.DEFAULT_CONNECTION_TIMEOUT);
                closeHttpClient = true;
            }
            return new MinioAsyncClient(this.baseUrl, this.awsS3Prefix, this.awsDomainSuffix, this.awsDualstack, this.useVirtualStyle, this.region, this.provider, this.httpClient, closeHttpClient);
        }
    }
}

