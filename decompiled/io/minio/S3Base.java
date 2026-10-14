/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  edu.umd.cs.findbugs.annotations.SuppressFBWarnings
 */
package io.minio;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.minio.AbortMultipartUploadResponse;
import io.minio.BaseArgs;
import io.minio.BucketArgs;
import io.minio.CloseableIterator;
import io.minio.ComposeSource;
import io.minio.CreateMultipartUploadResponse;
import io.minio.DeleteObjectsResponse;
import io.minio.Digest;
import io.minio.HttpRequestBody;
import io.minio.ListMultipartUploadsResponse;
import io.minio.ListObjectVersionsResponse;
import io.minio.ListObjectsArgs;
import io.minio.ListObjectsV1Response;
import io.minio.ListObjectsV2Response;
import io.minio.ListPartsResponse;
import io.minio.MinioProperties;
import io.minio.ObjectArgs;
import io.minio.ObjectWriteResponse;
import io.minio.PartReader;
import io.minio.PartSource;
import io.minio.PutObjectBaseArgs;
import io.minio.Result;
import io.minio.S3Escaper;
import io.minio.ServerSideEncryptionCustomerKey;
import io.minio.Signer;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import io.minio.Time;
import io.minio.UploadPartCopyResponse;
import io.minio.UploadPartResponse;
import io.minio.Xml;
import io.minio.credentials.Credentials;
import io.minio.credentials.Provider;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.http.HttpUtils;
import io.minio.http.Method;
import io.minio.messages.CompleteMultipartUpload;
import io.minio.messages.CopyPartResult;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteMarker;
import io.minio.messages.DeleteObject;
import io.minio.messages.DeleteRequest;
import io.minio.messages.DeleteResult;
import io.minio.messages.ErrorResponse;
import io.minio.messages.InitiateMultipartUploadResult;
import io.minio.messages.Item;
import io.minio.messages.ListBucketResultV1;
import io.minio.messages.ListBucketResultV2;
import io.minio.messages.ListMultipartUploadsResult;
import io.minio.messages.ListObjectsResult;
import io.minio.messages.ListPartsResult;
import io.minio.messages.ListVersionsResult;
import io.minio.messages.LocationConstraint;
import io.minio.messages.NotificationRecords;
import io.minio.messages.Part;
import io.minio.messages.Prefix;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public abstract class S3Base
implements AutoCloseable {
    protected static final String NO_SUCH_BUCKET_MESSAGE = "Bucket does not exist";
    protected static final String NO_SUCH_BUCKET = "NoSuchBucket";
    protected static final String NO_SUCH_BUCKET_POLICY = "NoSuchBucketPolicy";
    protected static final String NO_SUCH_OBJECT_LOCK_CONFIGURATION = "NoSuchObjectLockConfiguration";
    protected static final String SERVER_SIDE_ENCRYPTION_CONFIGURATION_NOT_FOUND_ERROR = "ServerSideEncryptionConfigurationNotFoundError";
    protected static final long DEFAULT_CONNECTION_TIMEOUT;
    protected static final int MAX_BUCKET_POLICY_SIZE = 20480;
    protected static final String US_EAST_1 = "us-east-1";
    protected final Map<String, String> regionCache = new ConcurrentHashMap<String, String>();
    private static final String RETRY_HEAD = "RetryHead";
    private static final String END_HTTP = "----------END-HTTP----------";
    private static final String UPLOAD_ID = "uploadId";
    private static final Set<String> TRACE_QUERY_PARAMS;
    private PrintWriter traceStream;
    private String userAgent = MinioProperties.INSTANCE.getDefaultUserAgent();
    protected HttpUrl baseUrl;
    protected String awsS3Prefix;
    protected String awsDomainSuffix;
    protected boolean awsDualstack;
    protected boolean useVirtualStyle;
    protected String region;
    protected Provider provider;
    protected OkHttpClient httpClient;
    protected boolean closeHttpClient;

    @Deprecated
    protected S3Base(HttpUrl baseUrl, String awsS3Prefix, String awsDomainSuffix, boolean awsDualstack, boolean useVirtualStyle, String region, Provider provider, OkHttpClient httpClient) {
        this(baseUrl, awsS3Prefix, awsDomainSuffix, awsDualstack, useVirtualStyle, region, provider, httpClient, false);
    }

    protected S3Base(HttpUrl baseUrl, String awsS3Prefix, String awsDomainSuffix, boolean awsDualstack, boolean useVirtualStyle, String region, Provider provider, OkHttpClient httpClient, boolean closeHttpClient) {
        this.baseUrl = baseUrl;
        this.awsS3Prefix = awsS3Prefix;
        this.awsDomainSuffix = awsDomainSuffix;
        this.awsDualstack = awsDualstack;
        this.useVirtualStyle = useVirtualStyle;
        this.region = region;
        this.provider = provider;
        this.httpClient = httpClient;
        this.closeHttpClient = closeHttpClient;
    }

    @Deprecated
    protected S3Base(HttpUrl baseUrl, String region, boolean isAwsHost, boolean isFipsHost, boolean isAccelerateHost, boolean isDualStackHost, boolean useVirtualStyle, Provider provider, OkHttpClient httpClient) {
        this.baseUrl = baseUrl;
        if (isAwsHost) {
            this.awsS3Prefix = "s3.";
        }
        if (isFipsHost) {
            this.awsS3Prefix = "s3-fips.";
        }
        if (isAccelerateHost) {
            this.awsS3Prefix = "s3-accelerate.";
        }
        if (isAwsHost || isFipsHost || isAccelerateHost) {
            String host2 = baseUrl.host();
            if (host2.endsWith(".amazonaws.com")) {
                this.awsDomainSuffix = "amazonaws.com";
            }
            if (host2.endsWith(".amazonaws.com.cn")) {
                this.awsDomainSuffix = "amazonaws.com.cn";
            }
        }
        this.awsDualstack = isDualStackHost;
        this.useVirtualStyle = useVirtualStyle;
        this.region = region;
        this.provider = provider;
        this.httpClient = httpClient;
        this.closeHttpClient = false;
    }

    protected S3Base(S3Base client) {
        this.baseUrl = client.baseUrl;
        this.awsS3Prefix = client.awsS3Prefix;
        this.awsDomainSuffix = client.awsDomainSuffix;
        this.awsDualstack = client.awsDualstack;
        this.useVirtualStyle = client.useVirtualStyle;
        this.region = client.region;
        this.provider = client.provider;
        this.httpClient = client.httpClient;
        this.closeHttpClient = client.closeHttpClient;
    }

    protected void checkArgs(BaseArgs args2) {
        String bucketName;
        if (args2 == null) {
            throw new IllegalArgumentException("null arguments");
        }
        if (this.awsDomainSuffix != null && args2 instanceof BucketArgs && ((bucketName = ((BucketArgs)args2).bucket()).startsWith("xn--") || bucketName.endsWith("--s3alias") || bucketName.endsWith("--ol-s3"))) {
            throw new IllegalArgumentException("bucket name '" + bucketName + "' must not start with 'xn--' and must not end with '--s3alias' or '--ol-s3'");
        }
    }

    protected Multimap<String, String> merge(Multimap<String, String> m1, Multimap<String, String> m22) {
        HashMultimap<String, String> map = HashMultimap.create();
        if (m1 != null) {
            map.putAll(m1);
        }
        if (m22 != null) {
            map.putAll(m22);
        }
        return map;
    }

    protected Multimap<String, String> newMultimap(String ... keysAndValues) {
        if (keysAndValues.length % 2 != 0) {
            throw new IllegalArgumentException("Expected alternating keys and values");
        }
        HashMultimap<String, String> map = HashMultimap.create();
        for (int i = 0; i < keysAndValues.length; i += 2) {
            map.put(keysAndValues[i], keysAndValues[i + 1]);
        }
        return map;
    }

    protected Multimap<String, String> newMultimap(Map<String, String> map) {
        return map != null ? Multimaps.forMap(map) : HashMultimap.create();
    }

    protected Multimap<String, String> newMultimap(Multimap<String, String> map) {
        return map != null ? HashMultimap.create(map) : HashMultimap.create();
    }

    public void throwEncapsulatedException(ExecutionException e) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        if (e == null) {
            return;
        }
        Throwable ex = e.getCause();
        if (ex instanceof CompletionException) {
            ex = ((CompletionException)ex).getCause();
        }
        if (ex instanceof ExecutionException) {
            ex = ((ExecutionException)ex).getCause();
        }
        try {
            throw ex;
        }
        catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidResponseException | ServerException | XmlParserException | IOException | IllegalArgumentException | InvalidKeyException | NoSuchAlgorithmException exc) {
            throw exc;
        }
        catch (Throwable exc) {
            throw new RuntimeException(exc.getCause() == null ? exc : exc.getCause());
        }
    }

    private String[] handleRedirectResponse(Method method2, String bucketName, Response response, boolean retry2) {
        String code = null;
        String message = null;
        if (response.code() == 301) {
            code = "PermanentRedirect";
            message = "Moved Permanently";
        } else if (response.code() == 307) {
            code = "Redirect";
            message = "Temporary redirect";
        } else if (response.code() == 400) {
            code = "BadRequest";
            message = "Bad request";
        }
        String region = response.headers().get("x-amz-bucket-region");
        if (message != null && region != null) {
            message = message + ". Use region " + region;
        }
        if (retry2 && region != null && method2.equals((Object)Method.HEAD) && bucketName != null && this.regionCache.get(bucketName) != null) {
            code = RETRY_HEAD;
            message = null;
        }
        return new String[]{code, message};
    }

    private String buildAwsUrl(HttpUrl.Builder builder, String bucketName, boolean enforcePathStyle, String region) {
        String host2 = this.awsS3Prefix + this.awsDomainSuffix;
        if (host2.equals("s3-external-1.amazonaws.com") || host2.equals("s3-us-gov-west-1.amazonaws.com") || host2.equals("s3-fips-us-gov-west-1.amazonaws.com")) {
            builder.host(host2);
            return host2;
        }
        host2 = this.awsS3Prefix;
        if (this.awsS3Prefix.contains("s3-accelerate")) {
            if (bucketName.contains(".")) {
                throw new IllegalArgumentException("bucket name '" + bucketName + "' with '.' is not allowed for accelerate endpoint");
            }
            if (enforcePathStyle) {
                host2 = host2.replaceFirst("-accelerate", "");
            }
        }
        if (this.awsDualstack) {
            host2 = host2 + "dualstack.";
        }
        if (!this.awsS3Prefix.contains("s3-accelerate")) {
            host2 = host2 + region + ".";
        }
        host2 = host2 + this.awsDomainSuffix;
        builder.host(host2);
        return host2;
    }

    private String buildListBucketsUrl(HttpUrl.Builder builder, String region) {
        if (this.awsDomainSuffix == null) {
            return null;
        }
        String host2 = this.awsS3Prefix + this.awsDomainSuffix;
        if (host2.equals("s3-external-1.amazonaws.com") || host2.equals("s3-us-gov-west-1.amazonaws.com") || host2.equals("s3-fips-us-gov-west-1.amazonaws.com")) {
            builder.host(host2);
            return host2;
        }
        String s3Prefix = this.awsS3Prefix;
        String domainSuffix = this.awsDomainSuffix;
        if (this.awsS3Prefix.startsWith("s3.") || this.awsS3Prefix.startsWith("s3-")) {
            s3Prefix = "s3.";
            domainSuffix = "amazonaws.com" + (domainSuffix.endsWith(".cn") ? ".cn" : "");
        }
        host2 = s3Prefix + region + "." + domainSuffix;
        builder.host(host2);
        return host2;
    }

    protected HttpUrl buildUrl(Method method2, String bucketName, String objectName, String region, Multimap<String, String> queryParamMap) throws NoSuchAlgorithmException {
        if (bucketName == null && objectName != null) {
            throw new IllegalArgumentException("null bucket name for object '" + objectName + "'");
        }
        HttpUrl.Builder urlBuilder = this.baseUrl.newBuilder();
        if (queryParamMap != null) {
            for (Map.Entry<String, String> entry : queryParamMap.entries()) {
                urlBuilder.addEncodedQueryParameter(S3Escaper.encode(entry.getKey()), S3Escaper.encode(entry.getValue()));
            }
        }
        if (bucketName == null) {
            this.buildListBucketsUrl(urlBuilder, region);
            return urlBuilder.build();
        }
        boolean enforcePathStyle = method2 == Method.PUT && objectName == null && queryParamMap == null || queryParamMap != null && queryParamMap.containsKey("location") || bucketName.contains(".") && this.baseUrl.isHttps();
        String host2 = this.baseUrl.host();
        if (this.awsDomainSuffix != null) {
            host2 = this.buildAwsUrl(urlBuilder, bucketName, enforcePathStyle, region);
        }
        if (enforcePathStyle || !this.useVirtualStyle) {
            urlBuilder.addEncodedPathSegment(S3Escaper.encode(bucketName));
        } else {
            urlBuilder.host(bucketName + "." + host2);
        }
        if (objectName != null) {
            urlBuilder.addEncodedPathSegments(S3Escaper.encodePath(objectName));
        }
        return urlBuilder.build();
    }

    protected Headers httpHeaders(Multimap<String, String> headerMap) {
        Headers.Builder builder = new Headers.Builder();
        if (headerMap == null) {
            return builder.build();
        }
        if (headerMap.containsKey("Content-Encoding")) {
            builder.add("Content-Encoding", headerMap.get("Content-Encoding").stream().distinct().filter(encoding -> !encoding.isEmpty()).collect(Collectors.joining(",")));
        }
        for (Map.Entry<String, String> entry : headerMap.entries()) {
            if (entry.getKey().equals("Content-Encoding")) continue;
            builder.addUnsafeNonAscii(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }

    protected Request createRequest(HttpUrl url2, Method method2, Headers headers, Object body2, int length, Credentials creds) throws InsufficientDataException, InternalException, IOException, NoSuchAlgorithmException {
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(url2);
        if (headers != null) {
            requestBuilder.headers(headers);
        }
        requestBuilder.header("Host", HttpUtils.getHostHeader(url2));
        requestBuilder.header("Accept-Encoding", "identity");
        requestBuilder.header("User-Agent", this.userAgent);
        String md5Hash = "1B2M2Y8AsgTpgAmY7PhCfg==";
        if (body2 != null) {
            md5Hash = body2 instanceof byte[] ? Digest.md5Hash((byte[])body2, length) : null;
        }
        String sha256Hash = null;
        if (creds != null) {
            sha256Hash = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
            if (!url2.isHttps()) {
                if (body2 != null) {
                    if (body2 instanceof PartSource) {
                        sha256Hash = ((PartSource)body2).sha256Hash();
                    } else if (body2 instanceof byte[]) {
                        sha256Hash = Digest.sha256Hash((byte[])body2, length);
                    }
                }
            } else {
                sha256Hash = "UNSIGNED-PAYLOAD";
                if (body2 != null && body2 instanceof PartSource) {
                    sha256Hash = ((PartSource)body2).sha256Hash();
                }
            }
        }
        if (md5Hash != null) {
            requestBuilder.header("Content-MD5", md5Hash);
        }
        if (sha256Hash != null) {
            requestBuilder.header("x-amz-content-sha256", sha256Hash);
        }
        if (creds != null && creds.sessionToken() != null) {
            requestBuilder.header("X-Amz-Security-Token", creds.sessionToken());
        }
        ZonedDateTime date = ZonedDateTime.now();
        requestBuilder.header("x-amz-date", date.format(Time.AMZ_DATE_FORMAT));
        HttpRequestBody requestBody = null;
        if (body2 != null) {
            String contentType = headers != null ? headers.get("Content-Type") : null;
            requestBody = body2 instanceof PartSource ? new HttpRequestBody((PartSource)body2, contentType) : new HttpRequestBody((byte[])body2, length, contentType);
        }
        requestBuilder.method(method2.toString(), requestBody);
        return requestBuilder.build();
    }

    private StringBuilder newTraceBuilder(Request request, String body2) {
        StringBuilder traceBuilder = new StringBuilder();
        traceBuilder.append("---------START-HTTP---------\n");
        String encodedPath = request.url().encodedPath();
        String encodedQuery = request.url().encodedQuery();
        if (encodedQuery != null) {
            encodedPath = encodedPath + "?" + encodedQuery;
        }
        traceBuilder.append(request.method()).append(" ").append(encodedPath).append(" HTTP/1.1\n");
        traceBuilder.append(request.headers().toString().replaceAll("Signature=([0-9a-f]+)", "Signature=*REDACTED*").replaceAll("Credential=([^/]+)", "Credential=*REDACTED*"));
        if (body2 != null) {
            traceBuilder.append("\n").append(body2);
        }
        return traceBuilder;
    }

    protected CompletableFuture<Response> executeAsync(final Method method2, final String bucketName, final String objectName, String region, Headers headers, final Multimap<String, String> queryParamMap, Object body2, int length) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        boolean traceRequestBody = false;
        if (body2 != null && !(body2 instanceof PartSource) && !(body2 instanceof byte[])) {
            byte[] bytes = body2 instanceof CharSequence ? body2.toString().getBytes(StandardCharsets.UTF_8) : Xml.marshal(body2).getBytes(StandardCharsets.UTF_8);
            body2 = bytes;
            length = bytes.length;
            traceRequestBody = true;
        }
        if (body2 == null && (method2 == Method.PUT || method2 == Method.POST)) {
            body2 = HttpUtils.EMPTY_BODY;
        }
        HttpUrl url2 = this.buildUrl(method2, bucketName, objectName, region, queryParamMap);
        Credentials creds = this.provider == null ? null : this.provider.fetch();
        Request req = this.createRequest(url2, method2, headers, body2, length, creds);
        if (creds != null) {
            req = Signer.signV4S3(req, region, creds.accessKey(), creds.secretKey(), req.header("x-amz-content-sha256"));
        }
        final Request request = req;
        final StringBuilder traceBuilder = this.newTraceBuilder(request, traceRequestBody ? new String((byte[])body2, StandardCharsets.UTF_8) : null);
        final PrintWriter traceStream = this.traceStream;
        if (traceStream != null) {
            traceStream.println(traceBuilder.toString());
        }
        traceBuilder.append("\n");
        OkHttpClient httpClient = this.httpClient;
        if (!(body2 instanceof byte[] || method2 != Method.PUT && method2 != Method.POST)) {
            httpClient = this.httpClient.newBuilder().retryOnConnectionFailure(false).build();
        }
        final CompletableFuture<Response> completableFuture = new CompletableFuture<Response>();
        httpClient.newCall(request).enqueue(new Callback(){

            @Override
            public void onFailure(Call call, IOException e) {
                completableFuture.completeExceptionally(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String trace = response.protocol().toString().toUpperCase(Locale.US) + " " + response.code() + "\n" + response.headers();
                traceBuilder.append(trace).append("\n");
                if (traceStream != null) {
                    traceStream.println(trace);
                }
                if (response.isSuccessful()) {
                    if (traceStream != null) {
                        Set keys2 = queryParamMap.keySet();
                        if (!(method2 == Method.GET && objectName != null && Collections.disjoint(keys2, TRACE_QUERY_PARAMS) || keys2.contains("events") && (keys2.contains("prefix") || keys2.contains("suffix")))) {
                            ResponseBody responseBody = response.peekBody(0x100000L);
                            traceStream.println(responseBody.string());
                        }
                        traceStream.println(S3Base.END_HTTP);
                    }
                    completableFuture.complete(response);
                    return;
                }
                String errorXml = null;
                try (ResponseBody responseBody = response.body();){
                    errorXml = responseBody.string();
                }
                if (!"".equals(errorXml) || !method2.equals((Object)Method.HEAD)) {
                    traceBuilder.append(errorXml).append("\n");
                    if (traceStream != null) {
                        traceStream.println(errorXml);
                    }
                }
                traceBuilder.append(S3Base.END_HTTP).append("\n");
                if (traceStream != null) {
                    traceStream.println(S3Base.END_HTTP);
                }
                String contentType = response.headers().get("content-type");
                if (!(method2.equals((Object)Method.HEAD) || contentType != null && Arrays.asList(contentType.split(";")).contains("application/xml"))) {
                    if (response.code() == 304 && response.body().contentLength() == 0L) {
                        completableFuture.completeExceptionally(new ServerException("server failed with HTTP status code " + response.code(), response.code(), traceBuilder.toString()));
                    }
                    completableFuture.completeExceptionally(new InvalidResponseException(response.code(), contentType, errorXml.substring(0, errorXml.length() > 1024 ? 1024 : errorXml.length()), traceBuilder.toString()));
                    return;
                }
                ErrorResponse errorResponse = null;
                if (!"".equals(errorXml)) {
                    try {
                        errorResponse = Xml.unmarshal(ErrorResponse.class, errorXml);
                    }
                    catch (XmlParserException e) {
                        completableFuture.completeExceptionally(e);
                        return;
                    }
                } else if (!method2.equals((Object)Method.HEAD)) {
                    completableFuture.completeExceptionally(new InvalidResponseException(response.code(), contentType, errorXml, traceBuilder.toString()));
                    return;
                }
                if (errorResponse == null) {
                    String code = null;
                    String message = null;
                    switch (response.code()) {
                        case 301: 
                        case 307: 
                        case 400: {
                            String[] result = S3Base.this.handleRedirectResponse(method2, bucketName, response, true);
                            code = result[0];
                            message = result[1];
                            break;
                        }
                        case 404: {
                            if (objectName != null) {
                                code = "NoSuchKey";
                                message = "Object does not exist";
                                break;
                            }
                            if (bucketName != null) {
                                code = S3Base.NO_SUCH_BUCKET;
                                message = S3Base.NO_SUCH_BUCKET_MESSAGE;
                                break;
                            }
                            code = "ResourceNotFound";
                            message = "Request resource not found";
                            break;
                        }
                        case 405: 
                        case 501: {
                            code = "MethodNotAllowed";
                            message = "The specified method is not allowed against this resource";
                            break;
                        }
                        case 409: {
                            if (bucketName != null) {
                                code = S3Base.NO_SUCH_BUCKET;
                                message = S3Base.NO_SUCH_BUCKET_MESSAGE;
                                break;
                            }
                            code = "ResourceConflict";
                            message = "Request resource conflicts";
                            break;
                        }
                        case 403: {
                            code = "AccessDenied";
                            message = "Access denied";
                            break;
                        }
                        case 412: {
                            code = "PreconditionFailed";
                            message = "At least one of the preconditions you specified did not hold";
                            break;
                        }
                        case 416: {
                            code = "InvalidRange";
                            message = "The requested range cannot be satisfied";
                            break;
                        }
                        default: {
                            completableFuture.completeExceptionally(new ServerException("server failed with HTTP status code " + response.code(), response.code(), traceBuilder.toString()));
                            return;
                        }
                    }
                    errorResponse = new ErrorResponse(code, message, bucketName, objectName, request.url().encodedPath(), response.header("x-amz-request-id"), response.header("x-amz-id-2"));
                }
                if (errorResponse.code().equals(S3Base.NO_SUCH_BUCKET) || errorResponse.code().equals(S3Base.RETRY_HEAD)) {
                    S3Base.this.regionCache.remove(bucketName);
                }
                ErrorResponseException e = new ErrorResponseException(errorResponse, response, traceBuilder.toString());
                completableFuture.completeExceptionally(e);
            }
        });
        return completableFuture;
    }

    protected CompletableFuture<Response> executeAsync(Method method2, BaseArgs args2, Multimap<String, String> headers, Multimap<String, String> queryParams2, Object body2, int length) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        String region;
        String bucketName;
        if (args2 instanceof BucketArgs) {
            bucketName = ((BucketArgs)args2).bucket();
            region = ((BucketArgs)args2).region();
        } else {
            bucketName = null;
            region = null;
        }
        String objectName = args2 instanceof ObjectArgs ? ((ObjectArgs)args2).object() : null;
        return this.getRegionAsync(bucketName, region).thenCompose(location -> {
            try {
                return this.executeAsync(method2, bucketName, objectName, (String)location, this.httpHeaders(this.merge(args2.extraHeaders(), headers)), this.merge(args2.extraQueryParams(), queryParams2), body2, length);
            }
            catch (InsufficientDataException | InternalException | XmlParserException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                throw new CompletionException(e);
            }
        });
    }

    @Deprecated
    protected Response execute(Method method2, String bucketName, String objectName, String region, Headers headers, Multimap<String, String> queryParamMap, Object body2, int length) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        CompletableFuture<Response> completableFuture = this.executeAsync(method2, bucketName, objectName, region, headers, queryParamMap, body2, length);
        try {
            return completableFuture.get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.throwEncapsulatedException(e);
            return null;
        }
    }

    @Deprecated
    protected Response execute(Method method2, BaseArgs args2, Multimap<String, String> headers, Multimap<String, String> queryParams2, Object body2, int length) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        String bucketName = null;
        String region = null;
        String objectName = null;
        if (args2 instanceof BucketArgs) {
            bucketName = ((BucketArgs)args2).bucket();
            region = ((BucketArgs)args2).region();
        }
        if (args2 instanceof ObjectArgs) {
            objectName = ((ObjectArgs)args2).object();
        }
        return this.execute(method2, bucketName, objectName, this.getRegion(bucketName, region), this.httpHeaders(this.merge(args2.extraHeaders(), headers)), this.merge(args2.extraQueryParams(), queryParams2), body2, length);
    }

    protected CompletableFuture<String> getRegionAsync(String bucketName, String region) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        if (region != null) {
            if (this.region != null && !this.region.equals(region)) {
                throw new IllegalArgumentException("region must be " + this.region + ", but passed " + region);
            }
            return CompletableFuture.completedFuture(region);
        }
        if (this.region != null && !this.region.equals("")) {
            return CompletableFuture.completedFuture(this.region);
        }
        if (bucketName == null || this.provider == null) {
            return CompletableFuture.completedFuture(US_EAST_1);
        }
        region = this.regionCache.get(bucketName);
        if (region != null) {
            return CompletableFuture.completedFuture(region);
        }
        CompletableFuture<Response> future = this.executeAsync(Method.GET, bucketName, null, US_EAST_1, null, this.newMultimap("location", null), null, 0);
        return future.thenApply(response -> {
            String location;
            try (ResponseBody body2 = response.body();){
                LocationConstraint lc = Xml.unmarshal(LocationConstraint.class, body2.charStream());
                location = lc.location() == null || lc.location().equals("") ? US_EAST_1 : (lc.location().equals("EU") && this.awsDomainSuffix != null ? "eu-west-1" : lc.location());
            }
            catch (XmlParserException e) {
                throw new CompletionException(e);
            }
            this.regionCache.put(bucketName, location);
            return location;
        });
    }

    @Deprecated
    protected String getRegion(String bucketName, String region) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            return this.getRegionAsync(bucketName, region).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.throwEncapsulatedException(e);
            return null;
        }
    }

    protected CompletableFuture<Response> executeGetAsync(BaseArgs args2, Multimap<String, String> headers, Multimap<String, String> queryParams2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        return this.executeAsync(Method.GET, args2, headers, queryParams2, null, 0);
    }

    @Deprecated
    protected Response executeGet(BaseArgs args2, Multimap<String, String> headers, Multimap<String, String> queryParams2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            return this.executeGetAsync(args2, headers, queryParams2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.throwEncapsulatedException(e);
            return null;
        }
    }

    protected CompletableFuture<Response> executeHeadAsync(BaseArgs args2, Multimap<String, String> headers, Multimap<String, String> queryParams2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        return ((CompletableFuture)this.executeAsync(Method.HEAD, args2, headers, queryParams2, null, 0).exceptionally(e -> {
            ErrorResponseException ex;
            if (e instanceof ErrorResponseException && (ex = (ErrorResponseException)e).errorResponse().code().equals(RETRY_HEAD)) {
                return null;
            }
            throw new CompletionException((Throwable)e);
        })).thenCompose(response -> {
            if (response != null) {
                return CompletableFuture.completedFuture(response);
            }
            try {
                return this.executeAsync(Method.HEAD, args2, headers, queryParams2, null, 0);
            }
            catch (InsufficientDataException | InternalException | XmlParserException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                throw new CompletionException(e);
            }
        });
    }

    @Deprecated
    protected Response executeHead(BaseArgs args2, Multimap<String, String> headers, Multimap<String, String> queryParams2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            return this.executeHeadAsync(args2, headers, queryParams2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.throwEncapsulatedException(e);
            return null;
        }
    }

    protected CompletableFuture<Response> executeDeleteAsync(BaseArgs args2, Multimap<String, String> headers, Multimap<String, String> queryParams2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        return this.executeAsync(Method.DELETE, args2, headers, queryParams2, null, 0).thenApply(response -> {
            if (response != null) {
                response.body().close();
            }
            return response;
        });
    }

    @Deprecated
    protected Response executeDelete(BaseArgs args2, Multimap<String, String> headers, Multimap<String, String> queryParams2) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            return this.executeDeleteAsync(args2, headers, queryParams2).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.throwEncapsulatedException(e);
            return null;
        }
    }

    protected CompletableFuture<Response> executePostAsync(BaseArgs args2, Multimap<String, String> headers, Multimap<String, String> queryParams2, Object data) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        return this.executeAsync(Method.POST, args2, headers, queryParams2, data, 0);
    }

    @Deprecated
    protected Response executePost(BaseArgs args2, Multimap<String, String> headers, Multimap<String, String> queryParams2, Object data) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            return this.executePostAsync(args2, headers, queryParams2, data).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.throwEncapsulatedException(e);
            return null;
        }
    }

    protected CompletableFuture<Response> executePutAsync(BaseArgs args2, Multimap<String, String> headers, Multimap<String, String> queryParams2, Object data, int length) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        return this.executeAsync(Method.PUT, args2, headers, queryParams2, data, length);
    }

    @Deprecated
    protected Response executePut(BaseArgs args2, Multimap<String, String> headers, Multimap<String, String> queryParams2, Object data, int length) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            return this.executePutAsync(args2, headers, queryParams2, data, length).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.throwEncapsulatedException(e);
            return null;
        }
    }

    protected CompletableFuture<Integer> calculatePartCountAsync(List<ComposeSource> sources) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        long[] objectSize = new long[]{0L};
        int index = 0;
        CompletionStage<Integer> completableFuture = CompletableFuture.supplyAsync(() -> 0);
        for (ComposeSource src : sources) {
            int i = ++index;
            completableFuture = completableFuture.thenCombine(this.statObjectAsync(new StatObjectArgs(src)), (partCount, statObjectResponse) -> {
                src.buildHeaders(statObjectResponse.size(), statObjectResponse.etag());
                long size = statObjectResponse.size();
                if (src.length() != null) {
                    size = src.length();
                } else if (src.offset() != null) {
                    size -= src.offset().longValue();
                }
                if (size < 0x500000L && sources.size() != 1 && i != sources.size()) {
                    throw new IllegalArgumentException("source " + src.bucket() + "/" + src.object() + ": size " + size + " must be greater than " + 0x500000);
                }
                objectSize[0] = objectSize[0] + size;
                if (objectSize[0] > 0x50000000000L) {
                    throw new IllegalArgumentException("destination object size must be less than 5497558138880");
                }
                if (size > 0x140000000L) {
                    long count = size / 0x140000000L;
                    long lastPartSize = size - count * 0x140000000L;
                    if (lastPartSize > 0L) {
                        ++count;
                    } else {
                        lastPartSize = 0x140000000L;
                    }
                    if (lastPartSize < 0x500000L && sources.size() != 1 && i != sources.size()) {
                        throw new IllegalArgumentException("source " + src.bucket() + "/" + src.object() + ": for multipart split upload of " + size + ", last part size is less than " + 0x500000);
                    }
                    partCount = partCount + (int)count;
                } else {
                    Integer n = partCount;
                    Integer n2 = partCount = Integer.valueOf(partCount + 1);
                }
                if (partCount > 10000) {
                    throw new IllegalArgumentException("Compose sources create more than allowed multipart count 10000");
                }
                return partCount;
            });
        }
        return completableFuture;
    }

    @Deprecated
    protected int calculatePartCount(List<ComposeSource> sources) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        long objectSize = 0L;
        int partCount = 0;
        int i = 0;
        for (ComposeSource src : sources) {
            ++i;
            StatObjectResponse stat = null;
            try {
                stat = this.statObjectAsync(new StatObjectArgs(src)).get();
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            catch (ExecutionException e) {
                this.throwEncapsulatedException(e);
            }
            src.buildHeaders(stat.size(), stat.etag());
            long size = stat.size();
            if (src.length() != null) {
                size = src.length();
            } else if (src.offset() != null) {
                size -= src.offset().longValue();
            }
            if (size < 0x500000L && sources.size() != 1 && i != sources.size()) {
                throw new IllegalArgumentException("source " + src.bucket() + "/" + src.object() + ": size " + size + " must be greater than " + 0x500000);
            }
            if ((objectSize += size) > 0x50000000000L) {
                throw new IllegalArgumentException("destination object size must be less than 5497558138880");
            }
            if (size > 0x140000000L) {
                long count = size / 0x140000000L;
                long lastPartSize = size - count * 0x140000000L;
                if (lastPartSize > 0L) {
                    ++count;
                } else {
                    lastPartSize = 0x140000000L;
                }
                if (lastPartSize < 0x500000L && sources.size() != 1 && i != sources.size()) {
                    throw new IllegalArgumentException("source " + src.bucket() + "/" + src.object() + ": for multipart split upload of " + size + ", last part size is less than " + 0x500000);
                }
                partCount += (int)count;
            } else {
                ++partCount;
            }
            if (partCount <= 10000) continue;
            throw new IllegalArgumentException("Compose sources create more than allowed multipart count 10000");
        }
        return partCount;
    }

    protected Iterable<Result<Item>> listObjectsV2(final ListObjectsArgs args2) {
        return new Iterable<Result<Item>>(){

            @Override
            public Iterator<Result<Item>> iterator() {
                return new ObjectIterator(){
                    private ListBucketResultV2 result = null;

                    @Override
                    protected void populateResult() throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
                        this.listObjectsResult = null;
                        this.itemIterator = null;
                        this.prefixIterator = null;
                        ListObjectsV2Response response = S3Base.this.listObjectsV2(args2.bucket(), args2.region(), args2.delimiter(), args2.useUrlEncodingType() ? "url" : null, args2.startAfter(), args2.maxKeys(), args2.prefix(), this.result == null ? args2.continuationToken() : this.result.nextContinuationToken(), args2.fetchOwner(), args2.includeUserMetadata(), args2.extraHeaders(), args2.extraQueryParams());
                        this.result = response.result();
                        this.listObjectsResult = response.result();
                    }
                };
            }
        };
    }

    protected Iterable<Result<Item>> listObjectsV1(final ListObjectsArgs args2) {
        return new Iterable<Result<Item>>(){

            @Override
            public Iterator<Result<Item>> iterator() {
                return new ObjectIterator(){
                    private ListBucketResultV1 result = null;

                    @Override
                    protected void populateResult() throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
                        String nextMarker;
                        this.listObjectsResult = null;
                        this.itemIterator = null;
                        this.prefixIterator = null;
                        String string = nextMarker = this.result == null ? args2.marker() : this.result.nextMarker();
                        if (nextMarker == null) {
                            nextMarker = this.lastObjectName;
                        }
                        ListObjectsV1Response response = S3Base.this.listObjectsV1(args2.bucket(), args2.region(), args2.delimiter(), args2.useUrlEncodingType() ? "url" : null, nextMarker, args2.maxKeys(), args2.prefix(), args2.extraHeaders(), args2.extraQueryParams());
                        this.result = response.result();
                        this.listObjectsResult = response.result();
                    }
                };
            }
        };
    }

    protected Iterable<Result<Item>> listObjectVersions(final ListObjectsArgs args2) {
        return new Iterable<Result<Item>>(){

            @Override
            public Iterator<Result<Item>> iterator() {
                return new ObjectIterator(){
                    private ListVersionsResult result = null;

                    @Override
                    protected void populateResult() throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
                        this.listObjectsResult = null;
                        this.itemIterator = null;
                        this.prefixIterator = null;
                        ListObjectVersionsResponse response = S3Base.this.listObjectVersions(args2.bucket(), args2.region(), args2.delimiter(), args2.useUrlEncodingType() ? "url" : null, this.result == null ? args2.keyMarker() : this.result.nextKeyMarker(), args2.maxKeys(), args2.prefix(), this.result == null ? args2.versionIdMarker() : this.result.nextVersionIdMarker(), args2.extraHeaders(), args2.extraQueryParams());
                        this.result = response.result();
                        this.listObjectsResult = response.result();
                    }
                };
            }
        };
    }

    protected PartReader newPartReader(Object data, long objectSize, long partSize, int partCount) {
        if (data instanceof RandomAccessFile) {
            return new PartReader((RandomAccessFile)data, objectSize, partSize, partCount);
        }
        if (data instanceof InputStream) {
            return new PartReader((InputStream)data, objectSize, partSize, partCount);
        }
        return null;
    }

    @Deprecated
    protected ObjectWriteResponse putObject(PutObjectBaseArgs args2, Object data, long objectSize, long partSize, int partCount, String contentType) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        try {
            return this.putObjectAsync(args2, data, objectSize, partSize, partCount, contentType).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.throwEncapsulatedException(e);
            return null;
        }
    }

    private Multimap<String, String> getCommonListObjectsQueryParams(String delimiter, String encodingType, Integer maxKeys, String prefix) {
        Multimap<String, String> queryParams2 = this.newMultimap("delimiter", delimiter == null ? "" : delimiter, "max-keys", Integer.toString(maxKeys > 0 ? maxKeys : 1000), "prefix", prefix == null ? "" : prefix);
        if (encodingType != null) {
            queryParams2.put("encoding-type", encodingType);
        }
        return queryParams2;
    }

    public void setTimeout(long connectTimeout, long writeTimeout, long readTimeout) {
        this.httpClient = HttpUtils.setTimeout(this.httpClient, connectTimeout, writeTimeout, readTimeout);
    }

    @SuppressFBWarnings(value={"SIC"}, justification="Should not be used in production anyways.")
    public void ignoreCertCheck() throws KeyManagementException, NoSuchAlgorithmException {
        this.httpClient = HttpUtils.disableCertCheck(this.httpClient);
    }

    public void setAppInfo(String name, String version) {
        if (name == null || version == null) {
            return;
        }
        this.userAgent = MinioProperties.INSTANCE.getDefaultUserAgent() + " " + name.trim() + "/" + version.trim();
    }

    public void traceOn(OutputStream traceStream) {
        if (traceStream == null) {
            throw new IllegalArgumentException("trace stream must be provided");
        }
        this.traceStream = new PrintWriter((Writer)new OutputStreamWriter(traceStream, StandardCharsets.UTF_8), true);
    }

    public void traceOff() throws IOException {
        this.traceStream = null;
    }

    @Deprecated
    public void enableAccelerateEndpoint() {
        this.awsS3Prefix = "s3-accelerate.";
    }

    @Deprecated
    public void disableAccelerateEndpoint() {
        this.awsS3Prefix = "s3.";
    }

    public void enableDualStackEndpoint() {
        this.awsDualstack = true;
    }

    public void disableDualStackEndpoint() {
        this.awsDualstack = false;
    }

    public void enableVirtualStyleEndpoint() {
        this.useVirtualStyle = true;
    }

    public void disableVirtualStyleEndpoint() {
        this.useVirtualStyle = false;
    }

    public void setAwsS3Prefix(@Nonnull String awsS3Prefix) {
        if (awsS3Prefix == null) {
            throw new IllegalArgumentException("null Amazon AWS S3 domain prefix");
        }
        if (!HttpUtils.AWS_S3_PREFIX_REGEX.matcher(awsS3Prefix).find()) {
            throw new IllegalArgumentException("invalid Amazon AWS S3 domain prefix " + awsS3Prefix);
        }
        this.awsS3Prefix = awsS3Prefix;
    }

    protected CompletableFuture<StatObjectResponse> statObjectAsync(StatObjectArgs args2) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        this.checkArgs(args2);
        args2.validateSsec(this.baseUrl);
        return this.executeHeadAsync(args2, args2.getHeaders(), args2.versionId() != null ? this.newMultimap("versionId", args2.versionId()) : null).thenApply(response -> new StatObjectResponse(response.headers(), args2.bucket(), args2.region(), args2.object()));
    }

    protected CompletableFuture<AbortMultipartUploadResponse> abortMultipartUploadAsync(String bucketName, String region, String objectName, String uploadId, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        return ((CompletableFuture)this.getRegionAsync(bucketName, region).thenCompose(location -> {
            try {
                return this.executeAsync(Method.DELETE, bucketName, objectName, (String)location, this.httpHeaders(extraHeaders), this.merge(extraQueryParams, this.newMultimap(UPLOAD_ID, uploadId)), null, 0);
            }
            catch (InsufficientDataException | InternalException | XmlParserException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                throw new CompletionException(e);
            }
        })).thenApply(response -> {
            try {
                AbortMultipartUploadResponse abortMultipartUploadResponse = new AbortMultipartUploadResponse(response.headers(), bucketName, region, objectName, uploadId);
                return abortMultipartUploadResponse;
            }
            finally {
                response.close();
            }
        });
    }

    @Deprecated
    protected AbortMultipartUploadResponse abortMultipartUpload(String bucketName, String region, String objectName, String uploadId, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) throws NoSuchAlgorithmException, InsufficientDataException, IOException, InvalidKeyException, ServerException, XmlParserException, ErrorResponseException, InternalException, InvalidResponseException {
        try {
            return this.abortMultipartUploadAsync(bucketName, region, objectName, uploadId, extraHeaders, extraQueryParams).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.throwEncapsulatedException(e);
            return null;
        }
    }

    protected CompletableFuture<ObjectWriteResponse> completeMultipartUploadAsync(String bucketName, String region, String objectName, String uploadId, Part[] parts, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        Multimap<String, String> queryParams2 = this.newMultimap(extraQueryParams);
        queryParams2.put(UPLOAD_ID, uploadId);
        return ((CompletableFuture)this.getRegionAsync(bucketName, region).thenCompose(location -> {
            try {
                return this.executeAsync(Method.POST, bucketName, objectName, (String)location, this.httpHeaders(extraHeaders), queryParams2, new CompleteMultipartUpload(parts), 0);
            }
            catch (InsufficientDataException | InternalException | XmlParserException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                throw new CompletionException(e);
            }
        })).thenApply(response -> {
            /*
             * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
             * 
             * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [6[CATCHBLOCK]], but top level block is 3[TRYBLOCK]
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
             *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
             *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1050)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
             *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
             *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
             *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
             *     at org.benf.cfr.reader.Main.main(Main.java:54)
             */
            throw new IllegalStateException("Decompilation failed");
        });
    }

    @Deprecated
    protected ObjectWriteResponse completeMultipartUpload(String bucketName, String region, String objectName, String uploadId, Part[] parts, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) throws NoSuchAlgorithmException, InsufficientDataException, IOException, InvalidKeyException, ServerException, XmlParserException, ErrorResponseException, InternalException, InvalidResponseException {
        try {
            return this.completeMultipartUploadAsync(bucketName, region, objectName, uploadId, parts, extraHeaders, extraQueryParams).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.throwEncapsulatedException(e);
            return null;
        }
    }

    protected CompletableFuture<CreateMultipartUploadResponse> createMultipartUploadAsync(String bucketName, String region, String objectName, Multimap<String, String> headers, Multimap<String, String> extraQueryParams) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        Multimap<String, String> queryParams2 = this.newMultimap(extraQueryParams);
        queryParams2.put("uploads", "");
        Multimap<String, String> headersCopy = this.newMultimap(headers);
        if (!headersCopy.containsKey("Content-Type")) {
            headersCopy.put("Content-Type", "application/octet-stream");
        }
        return ((CompletableFuture)this.getRegionAsync(bucketName, region).thenCompose(location -> {
            try {
                return this.executeAsync(Method.POST, bucketName, objectName, (String)location, this.httpHeaders(headersCopy), queryParams2, null, 0);
            }
            catch (InsufficientDataException | InternalException | XmlParserException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                throw new CompletionException(e);
            }
        })).thenApply(response -> {
            try {
                InitiateMultipartUploadResult result = Xml.unmarshal(InitiateMultipartUploadResult.class, response.body().charStream());
                CreateMultipartUploadResponse createMultipartUploadResponse = new CreateMultipartUploadResponse(response.headers(), bucketName, region, objectName, result);
                return createMultipartUploadResponse;
            }
            catch (XmlParserException e) {
                throw new CompletionException(e);
            }
            finally {
                response.close();
            }
        });
    }

    @Deprecated
    protected CreateMultipartUploadResponse createMultipartUpload(String bucketName, String region, String objectName, Multimap<String, String> headers, Multimap<String, String> extraQueryParams) throws NoSuchAlgorithmException, InsufficientDataException, IOException, InvalidKeyException, ServerException, XmlParserException, ErrorResponseException, InternalException, InvalidResponseException {
        try {
            return this.createMultipartUploadAsync(bucketName, region, objectName, headers, extraQueryParams).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.throwEncapsulatedException(e);
            return null;
        }
    }

    protected CompletableFuture<DeleteObjectsResponse> deleteObjectsAsync(String bucketName, String region, List<DeleteObject> objectList, boolean quiet, boolean bypassGovernanceMode, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        if (objectList == null) {
            objectList = new LinkedList<DeleteObject>();
        }
        if (objectList.size() > 1000) {
            throw new IllegalArgumentException("list of objects must not be more than 1000");
        }
        Multimap<String, String> headers = this.merge(extraHeaders, bypassGovernanceMode ? this.newMultimap("x-amz-bypass-governance-retention", "true") : null);
        List<DeleteObject> objects = objectList;
        return ((CompletableFuture)this.getRegionAsync(bucketName, region).thenCompose(location -> {
            try {
                return this.executeAsync(Method.POST, bucketName, null, (String)location, this.httpHeaders(headers), this.merge(extraQueryParams, this.newMultimap("delete", "")), new DeleteRequest(objects, quiet), 0);
            }
            catch (InsufficientDataException | InternalException | XmlParserException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                throw new CompletionException(e);
            }
        })).thenApply(response -> {
            try {
                String bodyContent = response.body().string();
                try {
                    if (Xml.validate(DeleteError.class, bodyContent)) {
                        DeleteError error = Xml.unmarshal(DeleteError.class, bodyContent);
                        DeleteResult result = new DeleteResult(error);
                        DeleteObjectsResponse deleteObjectsResponse = new DeleteObjectsResponse(response.headers(), bucketName, region, result);
                        return deleteObjectsResponse;
                    }
                }
                catch (XmlParserException error) {
                    // empty catch block
                }
                DeleteResult result = Xml.unmarshal(DeleteResult.class, bodyContent);
                DeleteObjectsResponse deleteObjectsResponse = new DeleteObjectsResponse(response.headers(), bucketName, region, result);
                return deleteObjectsResponse;
            }
            catch (XmlParserException | IOException e) {
                throw new CompletionException(e);
            }
            finally {
                response.close();
            }
        });
    }

    @Deprecated
    protected DeleteObjectsResponse deleteObjects(String bucketName, String region, List<DeleteObject> objectList, boolean quiet, boolean bypassGovernanceMode, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) throws NoSuchAlgorithmException, InsufficientDataException, IOException, InvalidKeyException, ServerException, XmlParserException, ErrorResponseException, InternalException, InvalidResponseException {
        try {
            return this.deleteObjectsAsync(bucketName, region, objectList, quiet, bypassGovernanceMode, extraHeaders, extraQueryParams).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.throwEncapsulatedException(e);
            return null;
        }
    }

    protected CompletableFuture<ListObjectsV2Response> listObjectsV2Async(String bucketName, String region, String delimiter, String encodingType, String startAfter, Integer maxKeys, String prefix, String continuationToken, boolean fetchOwner, boolean includeUserMetadata, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        Multimap<String, String> queryParams2 = this.merge(extraQueryParams, this.getCommonListObjectsQueryParams(delimiter, encodingType, maxKeys, prefix));
        queryParams2.put("list-type", "2");
        if (continuationToken != null) {
            queryParams2.put("continuation-token", continuationToken);
        }
        if (fetchOwner) {
            queryParams2.put("fetch-owner", "true");
        }
        if (startAfter != null) {
            queryParams2.put("start-after", startAfter);
        }
        if (includeUserMetadata) {
            queryParams2.put("metadata", "true");
        }
        return ((CompletableFuture)this.getRegionAsync(bucketName, region).thenCompose(location -> {
            try {
                return this.executeAsync(Method.GET, bucketName, null, (String)location, this.httpHeaders(extraHeaders), queryParams2, null, 0);
            }
            catch (InsufficientDataException | InternalException | XmlParserException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                throw new CompletionException(e);
            }
        })).thenApply(response -> {
            try {
                ListBucketResultV2 result = Xml.unmarshal(ListBucketResultV2.class, response.body().charStream());
                ListObjectsV2Response listObjectsV2Response = new ListObjectsV2Response(response.headers(), bucketName, region, result);
                return listObjectsV2Response;
            }
            catch (XmlParserException e) {
                throw new CompletionException(e);
            }
            finally {
                response.close();
            }
        });
    }

    @Deprecated
    protected ListObjectsV2Response listObjectsV2(String bucketName, String region, String delimiter, String encodingType, String startAfter, Integer maxKeys, String prefix, String continuationToken, boolean fetchOwner, boolean includeUserMetadata, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) throws InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, ServerException, XmlParserException, ErrorResponseException, InternalException, InvalidResponseException, IOException {
        try {
            return this.listObjectsV2Async(bucketName, region, delimiter, encodingType, startAfter, maxKeys, prefix, continuationToken, fetchOwner, includeUserMetadata, extraHeaders, extraQueryParams).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.throwEncapsulatedException(e);
            return null;
        }
    }

    protected CompletableFuture<ListObjectsV1Response> listObjectsV1Async(String bucketName, String region, String delimiter, String encodingType, String marker, Integer maxKeys, String prefix, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        Multimap<String, String> queryParams2 = this.merge(extraQueryParams, this.getCommonListObjectsQueryParams(delimiter, encodingType, maxKeys, prefix));
        if (marker != null) {
            queryParams2.put("marker", marker);
        }
        return ((CompletableFuture)this.getRegionAsync(bucketName, region).thenCompose(location -> {
            try {
                return this.executeAsync(Method.GET, bucketName, null, (String)location, this.httpHeaders(extraHeaders), queryParams2, null, 0);
            }
            catch (InsufficientDataException | InternalException | XmlParserException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                throw new CompletionException(e);
            }
        })).thenApply(response -> {
            try {
                ListBucketResultV1 result = Xml.unmarshal(ListBucketResultV1.class, response.body().charStream());
                ListObjectsV1Response listObjectsV1Response = new ListObjectsV1Response(response.headers(), bucketName, region, result);
                return listObjectsV1Response;
            }
            catch (XmlParserException e) {
                throw new CompletionException(e);
            }
            finally {
                response.close();
            }
        });
    }

    @Deprecated
    protected ListObjectsV1Response listObjectsV1(String bucketName, String region, String delimiter, String encodingType, String marker, Integer maxKeys, String prefix, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) throws NoSuchAlgorithmException, InsufficientDataException, IOException, InvalidKeyException, ServerException, XmlParserException, ErrorResponseException, InternalException, InvalidResponseException {
        try {
            return this.listObjectsV1Async(bucketName, region, delimiter, encodingType, marker, maxKeys, prefix, extraHeaders, extraQueryParams).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.throwEncapsulatedException(e);
            return null;
        }
    }

    protected CompletableFuture<ListObjectVersionsResponse> listObjectVersionsAsync(String bucketName, String region, String delimiter, String encodingType, String keyMarker, Integer maxKeys, String prefix, String versionIdMarker, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        Multimap<String, String> queryParams2 = this.merge(extraQueryParams, this.getCommonListObjectsQueryParams(delimiter, encodingType, maxKeys, prefix));
        if (keyMarker != null) {
            queryParams2.put("key-marker", keyMarker);
        }
        if (versionIdMarker != null) {
            queryParams2.put("version-id-marker", versionIdMarker);
        }
        queryParams2.put("versions", "");
        return ((CompletableFuture)this.getRegionAsync(bucketName, region).thenCompose(location -> {
            try {
                return this.executeAsync(Method.GET, bucketName, null, (String)location, this.httpHeaders(extraHeaders), queryParams2, null, 0);
            }
            catch (InsufficientDataException | InternalException | XmlParserException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                throw new CompletionException(e);
            }
        })).thenApply(response -> {
            try {
                ListVersionsResult result = Xml.unmarshal(ListVersionsResult.class, response.body().charStream());
                ListObjectVersionsResponse listObjectVersionsResponse = new ListObjectVersionsResponse(response.headers(), bucketName, region, result);
                return listObjectVersionsResponse;
            }
            catch (XmlParserException e) {
                throw new CompletionException(e);
            }
            finally {
                response.close();
            }
        });
    }

    @Deprecated
    protected ListObjectVersionsResponse listObjectVersions(String bucketName, String region, String delimiter, String encodingType, String keyMarker, Integer maxKeys, String prefix, String versionIdMarker, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) throws NoSuchAlgorithmException, InsufficientDataException, IOException, InvalidKeyException, ServerException, XmlParserException, ErrorResponseException, InternalException, InvalidResponseException {
        try {
            return this.listObjectVersionsAsync(bucketName, region, delimiter, encodingType, keyMarker, maxKeys, prefix, versionIdMarker, extraHeaders, extraQueryParams).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.throwEncapsulatedException(e);
            return null;
        }
    }

    private Part[] uploadParts(PutObjectBaseArgs args2, String uploadId, PartReader partReader, PartSource firstPartSource) throws InterruptedException, ExecutionException, InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        Part[] parts = new Part[10000];
        int partNumber = 0;
        PartSource partSource = firstPartSource;
        do {
            ++partNumber;
            SetMultimap<String, String> ssecHeaders = null;
            if (args2.sse() != null && args2.sse() instanceof ServerSideEncryptionCustomerKey) {
                ssecHeaders = Multimaps.forMap(args2.sse().headers());
            }
            UploadPartResponse response = this.uploadPartAsync(args2.bucket(), args2.region(), args2.object(), partSource, partNumber, uploadId, ssecHeaders, null).get();
            parts[partNumber - 1] = new Part(partNumber, response.etag());
        } while ((partSource = partReader.getPart()) != null);
        return parts;
    }

    private CompletableFuture<ObjectWriteResponse> putMultipartObjectAsync(PutObjectBaseArgs args2, Multimap<String, String> headers, PartReader partReader, PartSource firstPartSource) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        return CompletableFuture.supplyAsync(() -> {
            String uploadId = null;
            ObjectWriteResponse response = null;
            try {
                CreateMultipartUploadResponse createMultipartUploadResponse = this.createMultipartUploadAsync(args2.bucket(), args2.region(), args2.object(), headers, args2.extraQueryParams()).get();
                uploadId = createMultipartUploadResponse.result().uploadId();
                Part[] parts = this.uploadParts(args2, uploadId, partReader, firstPartSource);
                response = this.completeMultipartUploadAsync(args2.bucket(), args2.region(), args2.object(), uploadId, parts, null, null).get();
            }
            catch (InsufficientDataException | InternalException | XmlParserException | IOException | InterruptedException | InvalidKeyException | NoSuchAlgorithmException | ExecutionException e) {
                if (uploadId == null) {
                    Throwable throwable = e;
                    if (throwable instanceof ExecutionException) {
                        throwable = ((ExecutionException)throwable).getCause();
                    }
                    if (throwable instanceof CompletionException) {
                        throwable = ((CompletionException)throwable).getCause();
                    }
                    throw new CompletionException(throwable);
                }
                try {
                    this.abortMultipartUploadAsync(args2.bucket(), args2.region(), args2.object(), uploadId, null, null).get();
                }
                catch (InsufficientDataException | InternalException | XmlParserException | IOException | InterruptedException | InvalidKeyException | NoSuchAlgorithmException | ExecutionException ex) {
                    Throwable throwable = ex;
                    if (throwable instanceof ExecutionException) {
                        throwable = ((ExecutionException)throwable).getCause();
                    }
                    if (throwable instanceof CompletionException) {
                        throwable = ((CompletionException)throwable).getCause();
                    }
                    throw new CompletionException(throwable);
                }
            }
            return response;
        });
    }

    protected CompletableFuture<ObjectWriteResponse> putObjectAsync(PutObjectBaseArgs args2, Object data, long objectSize, long partSize, int partCount, String contentType) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        PartReader partReader = this.newPartReader(data, objectSize, partSize, partCount);
        if (partReader == null) {
            throw new IllegalArgumentException("data must be RandomAccessFile or InputStream");
        }
        Multimap<String, String> headers = this.newMultimap(args2.extraHeaders());
        headers.putAll(args2.genHeaders());
        if (!headers.containsKey("Content-Type")) {
            headers.put("Content-Type", contentType);
        }
        return CompletableFuture.supplyAsync(() -> {
            try {
                return partReader.getPart();
            }
            catch (IOException | NoSuchAlgorithmException e) {
                throw new CompletionException(e);
            }
        }).thenCompose(partSource -> {
            try {
                if (partReader.partCount() == 1) {
                    return this.putObjectAsync(args2.bucket(), args2.region(), args2.object(), (PartSource)partSource, headers, args2.extraQueryParams());
                }
                return this.putMultipartObjectAsync(args2, headers, partReader, (PartSource)partSource);
            }
            catch (InsufficientDataException | InternalException | XmlParserException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                throw new CompletionException(e);
            }
        });
    }

    private CompletableFuture<ObjectWriteResponse> putObjectAsync(String bucketName, String region, String objectName, PartSource partSource, Multimap<String, String> headers, Multimap<String, String> extraQueryParams) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        return ((CompletableFuture)this.getRegionAsync(bucketName, region).thenCompose(location -> {
            try {
                return this.executeAsync(Method.PUT, bucketName, objectName, (String)location, this.httpHeaders(headers), extraQueryParams, partSource, 0);
            }
            catch (InsufficientDataException | InternalException | XmlParserException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                throw new CompletionException(e);
            }
        })).thenApply(response -> {
            try {
                ObjectWriteResponse objectWriteResponse = new ObjectWriteResponse(response.headers(), bucketName, region, objectName, response.header("ETag").replaceAll("\"", ""), response.header("x-amz-version-id"));
                return objectWriteResponse;
            }
            finally {
                response.close();
            }
        });
    }

    protected CompletableFuture<ObjectWriteResponse> putObjectAsync(String bucketName, String region, String objectName, Object data, long length, Multimap<String, String> headers, Multimap<String, String> extraQueryParams) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        if (!(data instanceof InputStream || data instanceof RandomAccessFile || data instanceof byte[] || data instanceof CharSequence)) {
            throw new IllegalArgumentException("data must be InputStream, RandomAccessFile, byte[] or String");
        }
        PartReader partReader = this.newPartReader(data, length, length, 1);
        if (partReader != null) {
            return this.putObjectAsync(bucketName, region, objectName, partReader.getPart(), headers, extraQueryParams);
        }
        return ((CompletableFuture)this.getRegionAsync(bucketName, region).thenCompose(location -> {
            try {
                return this.executeAsync(Method.PUT, bucketName, objectName, (String)location, this.httpHeaders(headers), extraQueryParams, data, (int)length);
            }
            catch (InsufficientDataException | InternalException | XmlParserException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                throw new CompletionException(e);
            }
        })).thenApply(response -> {
            try {
                ObjectWriteResponse objectWriteResponse = new ObjectWriteResponse(response.headers(), bucketName, region, objectName, response.header("ETag").replaceAll("\"", ""), response.header("x-amz-version-id"));
                return objectWriteResponse;
            }
            finally {
                response.close();
            }
        });
    }

    @Deprecated
    protected ObjectWriteResponse putObject(String bucketName, String region, String objectName, Object data, long length, Multimap<String, String> headers, Multimap<String, String> extraQueryParams) throws NoSuchAlgorithmException, InsufficientDataException, IOException, InvalidKeyException, ServerException, XmlParserException, ErrorResponseException, InternalException, InvalidResponseException {
        try {
            return this.putObjectAsync(bucketName, region, objectName, data, length, headers, extraQueryParams).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.throwEncapsulatedException(e);
            return null;
        }
    }

    protected CompletableFuture<ListMultipartUploadsResponse> listMultipartUploadsAsync(String bucketName, String region, String delimiter, String encodingType, String keyMarker, Integer maxUploads, String prefix, String uploadIdMarker, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        Multimap<String, String> queryParams2 = this.merge(extraQueryParams, this.newMultimap("uploads", "", "delimiter", delimiter != null ? delimiter : "", "max-uploads", maxUploads != null ? maxUploads.toString() : "1000", "prefix", prefix != null ? prefix : ""));
        if (encodingType != null) {
            queryParams2.put("encoding-type", encodingType);
        }
        if (keyMarker != null) {
            queryParams2.put("key-marker", keyMarker);
        }
        if (uploadIdMarker != null) {
            queryParams2.put("upload-id-marker", uploadIdMarker);
        }
        return ((CompletableFuture)this.getRegionAsync(bucketName, region).thenCompose(location -> {
            try {
                return this.executeAsync(Method.GET, bucketName, null, (String)location, this.httpHeaders(extraHeaders), queryParams2, null, 0);
            }
            catch (InsufficientDataException | InternalException | XmlParserException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                throw new CompletionException(e);
            }
        })).thenApply(response -> {
            try {
                ListMultipartUploadsResult result = Xml.unmarshal(ListMultipartUploadsResult.class, response.body().charStream());
                ListMultipartUploadsResponse listMultipartUploadsResponse = new ListMultipartUploadsResponse(response.headers(), bucketName, region, result);
                return listMultipartUploadsResponse;
            }
            catch (XmlParserException e) {
                throw new CompletionException(e);
            }
            finally {
                response.close();
            }
        });
    }

    @Deprecated
    protected ListMultipartUploadsResponse listMultipartUploads(String bucketName, String region, String delimiter, String encodingType, String keyMarker, Integer maxUploads, String prefix, String uploadIdMarker, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) throws NoSuchAlgorithmException, InsufficientDataException, IOException, InvalidKeyException, ServerException, XmlParserException, ErrorResponseException, InternalException, InvalidResponseException {
        try {
            return this.listMultipartUploadsAsync(bucketName, region, delimiter, encodingType, keyMarker, maxUploads, prefix, uploadIdMarker, extraHeaders, extraQueryParams).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.throwEncapsulatedException(e);
            return null;
        }
    }

    protected CompletableFuture<ListPartsResponse> listPartsAsync(String bucketName, String region, String objectName, Integer maxParts, Integer partNumberMarker, String uploadId, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        Multimap<String, String> queryParams2 = this.merge(extraQueryParams, this.newMultimap(UPLOAD_ID, uploadId, "max-parts", maxParts != null ? maxParts.toString() : "1000"));
        if (partNumberMarker != null) {
            queryParams2.put("part-number-marker", partNumberMarker.toString());
        }
        return ((CompletableFuture)this.getRegionAsync(bucketName, region).thenCompose(location -> {
            try {
                return this.executeAsync(Method.GET, bucketName, objectName, (String)location, this.httpHeaders(extraHeaders), queryParams2, null, 0);
            }
            catch (InsufficientDataException | InternalException | XmlParserException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                throw new CompletionException(e);
            }
        })).thenApply(response -> {
            try {
                ListPartsResult result = Xml.unmarshal(ListPartsResult.class, response.body().charStream());
                ListPartsResponse listPartsResponse = new ListPartsResponse(response.headers(), bucketName, region, objectName, result);
                return listPartsResponse;
            }
            catch (XmlParserException e) {
                throw new CompletionException(e);
            }
            finally {
                response.close();
            }
        });
    }

    @Deprecated
    protected ListPartsResponse listParts(String bucketName, String region, String objectName, Integer maxParts, Integer partNumberMarker, String uploadId, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) throws NoSuchAlgorithmException, InsufficientDataException, IOException, InvalidKeyException, ServerException, XmlParserException, ErrorResponseException, InternalException, InvalidResponseException {
        try {
            return this.listPartsAsync(bucketName, region, objectName, maxParts, partNumberMarker, uploadId, extraHeaders, extraQueryParams).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.throwEncapsulatedException(e);
            return null;
        }
    }

    protected CompletableFuture<UploadPartResponse> uploadPartAsync(String bucketName, String region, String objectName, PartSource partSource, int partNumber, String uploadId, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        return ((CompletableFuture)this.getRegionAsync(bucketName, region).thenCompose(location -> {
            try {
                return this.executeAsync(Method.PUT, bucketName, objectName, (String)location, this.httpHeaders(extraHeaders), this.merge(extraQueryParams, this.newMultimap("partNumber", Integer.toString(partNumber), UPLOAD_ID, uploadId)), partSource, 0);
            }
            catch (InsufficientDataException | InternalException | XmlParserException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                throw new CompletionException(e);
            }
        })).thenApply(response -> {
            try {
                UploadPartResponse uploadPartResponse = new UploadPartResponse(response.headers(), bucketName, region, objectName, uploadId, partNumber, response.header("ETag").replaceAll("\"", ""));
                return uploadPartResponse;
            }
            finally {
                response.close();
            }
        });
    }

    protected CompletableFuture<UploadPartResponse> uploadPartAsync(String bucketName, String region, String objectName, Object data, long length, String uploadId, int partNumber, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        if (!(data instanceof InputStream || data instanceof RandomAccessFile || data instanceof byte[] || data instanceof CharSequence)) {
            throw new IllegalArgumentException("data must be InputStream, RandomAccessFile, byte[] or String");
        }
        PartReader partReader = this.newPartReader(data, length, length, 1);
        if (partReader != null) {
            return this.uploadPartAsync(bucketName, region, objectName, partReader.getPart(), partNumber, uploadId, extraHeaders, extraQueryParams);
        }
        return ((CompletableFuture)this.getRegionAsync(bucketName, region).thenCompose(location -> {
            try {
                return this.executeAsync(Method.PUT, bucketName, objectName, (String)location, this.httpHeaders(extraHeaders), this.merge(extraQueryParams, this.newMultimap("partNumber", Integer.toString(partNumber), UPLOAD_ID, uploadId)), data, (int)length);
            }
            catch (InsufficientDataException | InternalException | XmlParserException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                throw new CompletionException(e);
            }
        })).thenApply(response -> {
            try {
                UploadPartResponse uploadPartResponse = new UploadPartResponse(response.headers(), bucketName, region, objectName, uploadId, partNumber, response.header("ETag").replaceAll("\"", ""));
                return uploadPartResponse;
            }
            finally {
                response.close();
            }
        });
    }

    @Deprecated
    protected UploadPartResponse uploadPart(String bucketName, String region, String objectName, Object data, long length, String uploadId, int partNumber, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) throws NoSuchAlgorithmException, InsufficientDataException, IOException, InvalidKeyException, ServerException, XmlParserException, ErrorResponseException, InternalException, InvalidResponseException {
        try {
            return this.uploadPartAsync(bucketName, region, objectName, data, length, uploadId, partNumber, extraHeaders, extraQueryParams).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.throwEncapsulatedException(e);
            return null;
        }
    }

    @Deprecated
    protected UploadPartCopyResponse uploadPartCopy(String bucketName, String region, String objectName, String uploadId, int partNumber, Multimap<String, String> headers, Multimap<String, String> extraQueryParams) throws NoSuchAlgorithmException, InsufficientDataException, IOException, InvalidKeyException, ServerException, XmlParserException, ErrorResponseException, InternalException, InvalidResponseException {
        try {
            return this.uploadPartCopyAsync(bucketName, region, objectName, uploadId, partNumber, headers, extraQueryParams).get();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            this.throwEncapsulatedException(e);
            return null;
        }
    }

    protected CompletableFuture<UploadPartCopyResponse> uploadPartCopyAsync(String bucketName, String region, String objectName, String uploadId, int partNumber, Multimap<String, String> headers, Multimap<String, String> extraQueryParams) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        return ((CompletableFuture)this.getRegionAsync(bucketName, region).thenCompose(location -> {
            try {
                return this.executeAsync(Method.PUT, bucketName, objectName, (String)location, this.httpHeaders(headers), this.merge(extraQueryParams, this.newMultimap("partNumber", Integer.toString(partNumber), UPLOAD_ID, uploadId)), null, 0);
            }
            catch (InsufficientDataException | InternalException | XmlParserException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                throw new CompletionException(e);
            }
        })).thenApply(response -> {
            try {
                CopyPartResult result = Xml.unmarshal(CopyPartResult.class, response.body().charStream());
                UploadPartCopyResponse uploadPartCopyResponse = new UploadPartCopyResponse(response.headers(), bucketName, region, objectName, uploadId, partNumber, result);
                return uploadPartCopyResponse;
            }
            catch (XmlParserException e) {
                throw new CompletionException(e);
            }
            finally {
                response.close();
            }
        });
    }

    @Override
    public void close() throws Exception {
        if (this.closeHttpClient) {
            this.httpClient.dispatcher().executorService().shutdown();
            this.httpClient.connectionPool().evictAll();
        }
    }

    static {
        try {
            RequestBody.create(new byte[0], null);
        }
        catch (NoSuchMethodError ex) {
            throw new RuntimeException("Unsupported OkHttp library found. Must use okhttp >= 4.11.0", ex);
        }
        DEFAULT_CONNECTION_TIMEOUT = TimeUnit.MINUTES.toMillis(5L);
        TRACE_QUERY_PARAMS = ImmutableSet.of("retention", "legal-hold", "tagging", UPLOAD_ID);
    }

    protected static class NotificationResultRecords {
        Response response = null;
        Scanner scanner = null;
        ObjectMapper mapper = null;

        public NotificationResultRecords(Response response) {
            this.response = response;
            this.scanner = new Scanner(response.body().charStream()).useDelimiter("\n");
            this.mapper = ((JsonMapper.Builder)((JsonMapper.Builder)JsonMapper.builder().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)).configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)).build();
        }

        public CloseableIterator<Result<NotificationRecords>> closeableIterator() {
            return new CloseableIterator<Result<NotificationRecords>>(){
                String recordsString = null;
                NotificationRecords records = null;
                boolean isClosed = false;

                @Override
                public void close() throws IOException {
                    if (!this.isClosed) {
                        try {
                            response.body().close();
                            scanner.close();
                        }
                        finally {
                            this.isClosed = true;
                        }
                    }
                }

                public boolean populate() {
                    if (this.isClosed) {
                        return false;
                    }
                    if (this.recordsString != null) {
                        return true;
                    }
                    while (scanner.hasNext()) {
                        this.recordsString = scanner.next().trim();
                        if (this.recordsString.equals("")) continue;
                    }
                    if (this.recordsString == null || this.recordsString.equals("")) {
                        try {
                            this.close();
                        }
                        catch (IOException e) {
                            this.isClosed = true;
                        }
                        return false;
                    }
                    return true;
                }

                @Override
                public boolean hasNext() {
                    return this.populate();
                }

                @Override
                public Result<NotificationRecords> next() {
                    if (this.isClosed) {
                        throw new NoSuchElementException();
                    }
                    if ((this.recordsString == null || this.recordsString.equals("")) && !this.populate()) {
                        throw new NoSuchElementException();
                    }
                    try {
                        this.records = mapper.readValue(this.recordsString, NotificationRecords.class);
                        Result<NotificationRecords> result = new Result<NotificationRecords>(this.records);
                        return result;
                    }
                    catch (JsonMappingException e) {
                        Result<NotificationRecords> result = new Result<NotificationRecords>(e);
                        return result;
                    }
                    catch (JsonParseException e) {
                        Result<NotificationRecords> result = new Result<NotificationRecords>(e);
                        return result;
                    }
                    catch (IOException e) {
                        Result<NotificationRecords> result = new Result<NotificationRecords>(e);
                        return result;
                    }
                    finally {
                        this.recordsString = null;
                        this.records = null;
                    }
                }
            };
        }
    }

    private abstract class ObjectIterator
    implements Iterator<Result<Item>> {
        protected Result<Item> error;
        protected Iterator<? extends Item> itemIterator;
        protected Iterator<DeleteMarker> deleteMarkerIterator;
        protected Iterator<Prefix> prefixIterator;
        protected boolean completed = false;
        protected ListObjectsResult listObjectsResult;
        protected String lastObjectName;

        private ObjectIterator() {
        }

        protected abstract void populateResult() throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException;

        protected synchronized void populate() {
            try {
                this.populateResult();
            }
            catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidResponseException | ServerException | XmlParserException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
                this.error = new Result(e);
            }
            if (this.listObjectsResult != null) {
                this.itemIterator = this.listObjectsResult.contents().iterator();
                this.deleteMarkerIterator = this.listObjectsResult.deleteMarkers().iterator();
                this.prefixIterator = this.listObjectsResult.commonPrefixes().iterator();
            } else {
                this.itemIterator = new LinkedList().iterator();
                this.deleteMarkerIterator = new LinkedList().iterator();
                this.prefixIterator = new LinkedList().iterator();
            }
        }

        @Override
        public boolean hasNext() {
            if (this.completed) {
                return false;
            }
            if (this.error == null && this.itemIterator == null && this.deleteMarkerIterator == null && this.prefixIterator == null) {
                this.populate();
            }
            if (this.error == null && !this.itemIterator.hasNext() && !this.deleteMarkerIterator.hasNext() && !this.prefixIterator.hasNext() && this.listObjectsResult.isTruncated()) {
                this.populate();
            }
            if (this.error != null) {
                return true;
            }
            if (this.itemIterator.hasNext()) {
                return true;
            }
            if (this.deleteMarkerIterator.hasNext()) {
                return true;
            }
            if (this.prefixIterator.hasNext()) {
                return true;
            }
            this.completed = true;
            return false;
        }

        @Override
        public Result<Item> next() {
            if (this.completed) {
                throw new NoSuchElementException();
            }
            if (this.error == null && this.itemIterator == null && this.deleteMarkerIterator == null && this.prefixIterator == null) {
                this.populate();
            }
            if (this.error == null && !this.itemIterator.hasNext() && !this.deleteMarkerIterator.hasNext() && !this.prefixIterator.hasNext() && this.listObjectsResult.isTruncated()) {
                this.populate();
            }
            if (this.error != null) {
                this.completed = true;
                return this.error;
            }
            Item item = null;
            if (this.itemIterator.hasNext()) {
                item = this.itemIterator.next();
                item.setEncodingType(this.listObjectsResult.encodingType());
                this.lastObjectName = item.objectName();
            } else if (this.deleteMarkerIterator.hasNext()) {
                item = this.deleteMarkerIterator.next();
            } else if (this.prefixIterator.hasNext()) {
                item = this.prefixIterator.next().toItem();
            }
            if (item != null) {
                item.setEncodingType(this.listObjectsResult.encodingType());
                return new Result<Item>(item);
            }
            this.completed = true;
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

