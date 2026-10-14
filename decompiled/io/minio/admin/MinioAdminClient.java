/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  edu.umd.cs.findbugs.annotations.SuppressFBWarnings
 */
package io.minio.admin;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.minio.Digest;
import io.minio.MinioProperties;
import io.minio.S3Escaper;
import io.minio.Signer;
import io.minio.Time;
import io.minio.admin.AddServiceAccountResp;
import io.minio.admin.Crypto;
import io.minio.admin.GetServiceAccountInfoResp;
import io.minio.admin.GroupAddUpdateRemoveInfo;
import io.minio.admin.GroupInfo;
import io.minio.admin.ListServiceAccountResp;
import io.minio.admin.QuotaUnit;
import io.minio.admin.Status;
import io.minio.admin.UserInfo;
import io.minio.admin.messages.DataUsageInfo;
import io.minio.admin.messages.info.Message;
import io.minio.credentials.Credentials;
import io.minio.credentials.Provider;
import io.minio.credentials.StaticProvider;
import io.minio.http.HttpUtils;
import io.minio.http.Method;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.bouncycastle.crypto.InvalidCipherTextException;

public class MinioAdminClient {
    private static final long DEFAULT_CONNECTION_TIMEOUT = TimeUnit.MINUTES.toMillis(1L);
    private static final MediaType DEFAULT_MEDIA_TYPE = MediaType.parse("application/octet-stream");
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Pattern SERVICE_ACCOUNT_NAME_REGEX = Pattern.compile("^(?!-)(?!_)[a-z_\\d-]{1,31}(?<!-)(?<!_)$", 2);
    private String userAgent = MinioProperties.INSTANCE.getDefaultUserAgent();
    private PrintWriter traceStream;
    private HttpUrl baseUrl;
    private String region;
    private Provider provider;
    private OkHttpClient httpClient;

    private MinioAdminClient(HttpUrl baseUrl, String region, Provider provider, OkHttpClient httpClient) {
        this.baseUrl = baseUrl;
        this.region = region;
        this.provider = provider;
        this.httpClient = httpClient;
    }

    private Credentials getCredentials() {
        Credentials creds = this.provider.fetch();
        if (creds == null) {
            throw new RuntimeException("Credential provider returns null credential");
        }
        return creds;
    }

    private Response execute(Method method2, Command command, Multimap<String, String> queryParamMap, byte[] body2) throws InvalidKeyException, IOException, NoSuchAlgorithmException {
        Credentials creds = this.getCredentials();
        HttpUrl.Builder urlBuilder = this.baseUrl.newBuilder().host(this.baseUrl.host()).addEncodedPathSegments(S3Escaper.encodePath("minio/admin/v3/" + command.toString()));
        if (queryParamMap != null) {
            for (Map.Entry<String, String> entry : queryParamMap.entries()) {
                urlBuilder.addEncodedQueryParameter(S3Escaper.encode(entry.getKey()), S3Escaper.encode(entry.getValue()));
            }
        }
        HttpUrl url2 = urlBuilder.build();
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(url2);
        requestBuilder.header("Host", HttpUtils.getHostHeader(url2));
        requestBuilder.header("Accept-Encoding", "identity");
        requestBuilder.header("User-Agent", this.userAgent);
        requestBuilder.header("x-amz-date", ZonedDateTime.now().format(Time.AMZ_DATE_FORMAT));
        if (creds.sessionToken() != null) {
            requestBuilder.header("X-Amz-Security-Token", creds.sessionToken());
        }
        if (body2 == null && method2 != Method.GET && method2 != Method.HEAD) {
            body2 = HttpUtils.EMPTY_BODY;
        }
        if (body2 != null) {
            requestBuilder.header("x-amz-content-sha256", Digest.sha256Hash(body2, body2.length));
            requestBuilder.method(method2.toString(), RequestBody.create(body2, DEFAULT_MEDIA_TYPE));
        } else {
            requestBuilder.header("x-amz-content-sha256", "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");
        }
        Request request = requestBuilder.build();
        request = Signer.signV4S3(request, this.region, creds.accessKey(), creds.secretKey(), request.header("x-amz-content-sha256"));
        PrintWriter traceStream = this.traceStream;
        if (traceStream != null) {
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
                traceBuilder.append("\n").append(new String(body2, StandardCharsets.UTF_8));
            }
            traceStream.println(traceBuilder.toString());
        }
        OkHttpClient httpClient = this.httpClient;
        Response response = httpClient.newCall(request).execute();
        if (traceStream != null) {
            String trace = response.protocol().toString().toUpperCase(Locale.US) + " " + response.code() + "\n" + response.headers();
            traceStream.println(trace);
            ResponseBody responseBody = response.peekBody(0x100000L);
            traceStream.println(responseBody.string());
            traceStream.println("----------END-HTTP----------");
        }
        if (response.isSuccessful()) {
            return response;
        }
        throw new RuntimeException("Request failed with response: " + response.body().string());
    }

    public void addUser(@Nonnull String accessKey, @Nonnull UserInfo.Status status, @Nullable String secretKey, @Nullable String policyName, @Nullable List<String> memberOf) throws NoSuchAlgorithmException, InvalidKeyException, IOException, InvalidCipherTextException {
        if (accessKey == null || accessKey.isEmpty()) {
            throw new IllegalArgumentException("access key must be provided");
        }
        UserInfo userInfo = new UserInfo(status, secretKey, policyName, memberOf);
        Credentials creds = this.getCredentials();
        Response response = this.execute(Method.PUT, Command.ADD_USER, ImmutableMultimap.of("accessKey", accessKey), Crypto.encrypt(OBJECT_MAPPER.writeValueAsBytes(userInfo), creds.secretKey()));
        Throwable throwable = null;
        if (response != null) {
            if (throwable != null) {
                try {
                    response.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
            } else {
                response.close();
            }
        }
    }

    public UserInfo getUserInfo(String accessKey) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        try (Response response = this.execute(Method.GET, Command.USER_INFO, ImmutableMultimap.of("accessKey", accessKey), null);){
            byte[] jsonData = response.body().bytes();
            UserInfo userInfo = OBJECT_MAPPER.readValue(jsonData, UserInfo.class);
            return userInfo;
        }
    }

    public Map<String, UserInfo> listUsers() throws NoSuchAlgorithmException, InvalidKeyException, IOException, InvalidCipherTextException {
        try (Response response = this.execute(Method.GET, Command.LIST_USERS, null, null);){
            Credentials creds = this.getCredentials();
            byte[] jsonData = Crypto.decrypt(response.body().byteStream(), creds.secretKey());
            MapType mapType = OBJECT_MAPPER.getTypeFactory().constructMapType(HashMap.class, String.class, UserInfo.class);
            Map map = (Map)OBJECT_MAPPER.readValue(jsonData, (JavaType)mapType);
            return map;
        }
    }

    public void deleteUser(@Nonnull String accessKey) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        if (accessKey == null || accessKey.isEmpty()) {
            throw new IllegalArgumentException("access key must be provided");
        }
        Response response = this.execute(Method.DELETE, Command.REMOVE_USER, ImmutableMultimap.of("accessKey", accessKey), null);
        Throwable throwable = null;
        if (response != null) {
            if (throwable != null) {
                try {
                    response.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
            } else {
                response.close();
            }
        }
    }

    public void addUpdateGroup(@Nonnull String group, @Nullable Status groupStatus, @Nullable List<String> members) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        if (group == null || group.isEmpty()) {
            throw new IllegalArgumentException("group must be provided");
        }
        GroupAddUpdateRemoveInfo groupAddUpdateRemoveInfo = new GroupAddUpdateRemoveInfo(group, groupStatus, members, false);
        Response response = this.execute(Method.PUT, Command.ADD_UPDATE_REMOVE_GROUP, null, OBJECT_MAPPER.writeValueAsBytes(groupAddUpdateRemoveInfo));
        Throwable throwable = null;
        if (response != null) {
            if (throwable != null) {
                try {
                    response.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
            } else {
                response.close();
            }
        }
    }

    public GroupInfo getGroupInfo(String group) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        try (Response response = this.execute(Method.GET, Command.GROUP_INFO, ImmutableMultimap.of("group", group), null);){
            byte[] jsonData = response.body().bytes();
            GroupInfo groupInfo = OBJECT_MAPPER.readValue(jsonData, GroupInfo.class);
            return groupInfo;
        }
    }

    public List<String> listGroups() throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        try (Response response = this.execute(Method.GET, Command.LIST_GROUPS, null, null);){
            byte[] jsonData = response.body().bytes();
            CollectionType mapType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, String.class);
            List list = (List)OBJECT_MAPPER.readValue(jsonData, (JavaType)mapType);
            return list;
        }
    }

    public void removeGroup(@Nonnull String group) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        if (group == null || group.isEmpty()) {
            throw new IllegalArgumentException("group must be provided");
        }
        GroupAddUpdateRemoveInfo groupAddUpdateRemoveInfo = new GroupAddUpdateRemoveInfo(group, null, null, true);
        Response response = this.execute(Method.PUT, Command.ADD_UPDATE_REMOVE_GROUP, null, OBJECT_MAPPER.writeValueAsBytes(groupAddUpdateRemoveInfo));
        Throwable throwable = null;
        if (response != null) {
            if (throwable != null) {
                try {
                    response.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
            } else {
                response.close();
            }
        }
    }

    public void setBucketQuota(@Nonnull String bucketName, long size, @Nonnull QuotaUnit unit) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        HashMap<String, Object> quotaEntity = new HashMap<String, Object>();
        if (size > 0L) {
            quotaEntity.put("quotatype", "hard");
        }
        quotaEntity.put("quota", unit.toBytes(size));
        Response response = this.execute(Method.PUT, Command.SET_BUCKET_QUOTA, ImmutableMultimap.of("bucket", bucketName), OBJECT_MAPPER.writeValueAsBytes(quotaEntity));
        Throwable throwable = null;
        if (response != null) {
            if (throwable != null) {
                try {
                    response.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
            } else {
                response.close();
            }
        }
    }

    public long getBucketQuota(String bucketName) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try (Response response = this.execute(Method.GET, Command.GET_BUCKET_QUOTA, ImmutableMultimap.of("bucket", bucketName), null);){
            MapType mapType = OBJECT_MAPPER.getTypeFactory().constructMapType(HashMap.class, String.class, JsonNode.class);
            long l = ((Map)OBJECT_MAPPER.readValue(response.body().bytes(), (JavaType)mapType)).entrySet().stream().filter(entry -> "quota".equals(entry.getKey())).findFirst().map(entry -> Long.valueOf(((JsonNode)entry.getValue()).toString())).orElseThrow(() -> new IllegalArgumentException("found not quota"));
            return l;
        }
    }

    public void clearBucketQuota(@Nonnull String bucketName) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        this.setBucketQuota(bucketName, 0L, QuotaUnit.KB);
    }

    public void addCannedPolicy(@Nonnull String name, @Nonnull String policy) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name must be provided");
        }
        if (policy == null || policy.isEmpty()) {
            throw new IllegalArgumentException("policy must be provided");
        }
        Response response = this.execute(Method.PUT, Command.ADD_CANNED_POLICY, ImmutableMultimap.of("name", name), policy.getBytes(StandardCharsets.UTF_8));
        Throwable throwable = null;
        if (response != null) {
            if (throwable != null) {
                try {
                    response.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
            } else {
                response.close();
            }
        }
    }

    public void setPolicy(@Nonnull String userOrGroupName, boolean isGroup, @Nonnull String policyName) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        if (userOrGroupName == null || userOrGroupName.isEmpty()) {
            throw new IllegalArgumentException("user/group name must be provided");
        }
        if (policyName == null || policyName.isEmpty()) {
            throw new IllegalArgumentException("policy name must be provided");
        }
        Response response = this.execute(Method.PUT, Command.SET_USER_OR_GROUP_POLICY, ImmutableMultimap.of("userOrGroup", userOrGroupName, "isGroup", String.valueOf(isGroup), "policyName", policyName), null);
        Throwable throwable = null;
        if (response != null) {
            if (throwable != null) {
                try {
                    response.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
            } else {
                response.close();
            }
        }
    }

    public Map<String, String> listCannedPolicies() throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        try (Response response = this.execute(Method.GET, Command.LIST_CANNED_POLICIES, null, null);){
            MapType mapType = OBJECT_MAPPER.getTypeFactory().constructMapType(HashMap.class, String.class, JsonNode.class);
            HashMap<String, String> policies = new HashMap<String, String>();
            ((Map)OBJECT_MAPPER.readValue(response.body().bytes(), (JavaType)mapType)).forEach((key, value) -> policies.put((String)key, value.toString()));
            HashMap<String, String> hashMap = policies;
            return hashMap;
        }
    }

    public void removeCannedPolicy(@Nonnull String name) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name must be provided");
        }
        Response response = this.execute(Method.DELETE, Command.REMOVE_CANNED_POLICY, ImmutableMultimap.of("name", name), null);
        Throwable throwable = null;
        if (response != null) {
            if (throwable != null) {
                try {
                    response.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
            } else {
                response.close();
            }
        }
    }

    public DataUsageInfo getDataUsageInfo() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try (Response response = this.execute(Method.GET, Command.DATA_USAGE_INFO, null, null);){
            DataUsageInfo dataUsageInfo = OBJECT_MAPPER.readValue(response.body().bytes(), DataUsageInfo.class);
            return dataUsageInfo;
        }
    }

    public Message getServerInfo() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try (Response response = this.execute(Method.GET, Command.INFO, null, null);){
            Message message = OBJECT_MAPPER.readValue(response.body().charStream(), Message.class);
            return message;
        }
    }

    public Credentials addServiceAccount(@Nonnull String accessKey, @Nonnull String secretKey, @Nullable String targetUser, @Nullable Map<String, Object> policy, @Nullable String name, @Nullable String description, @Nullable ZonedDateTime expiration) throws NoSuchAlgorithmException, InvalidKeyException, IOException, InvalidCipherTextException {
        if (accessKey == null || accessKey.isEmpty()) {
            throw new IllegalArgumentException("access key must be provided");
        }
        if (secretKey == null || secretKey.isEmpty()) {
            throw new IllegalArgumentException("secret key must be provided");
        }
        if (name != null && !SERVICE_ACCOUNT_NAME_REGEX.matcher(name).find()) {
            throw new IllegalArgumentException("name must contain non-empty alphanumeric,  underscore and hyphen characters not longer than 32 characters");
        }
        if (description != null && description.length() > 256) {
            throw new IllegalArgumentException("description must be at most 256 characters long");
        }
        HashMap<String, Object> serviceAccount = new HashMap<String, Object>();
        serviceAccount.put("accessKey", accessKey);
        serviceAccount.put("secretKey", secretKey);
        if (targetUser != null && !targetUser.isEmpty()) {
            serviceAccount.put("targetUser", targetUser);
        }
        if (policy != null && !policy.isEmpty()) {
            serviceAccount.put("policy", policy);
        }
        if (name != null && !name.isEmpty()) {
            serviceAccount.put("name", name);
        }
        if (description != null && !description.isEmpty()) {
            serviceAccount.put("description", description);
        }
        if (expiration != null) {
            serviceAccount.put("expiration", expiration.format(Time.EXPIRATION_DATE_FORMAT));
        }
        Credentials creds = this.getCredentials();
        try (Response response = this.execute(Method.PUT, Command.ADD_SERVICE_ACCOUNT, null, Crypto.encrypt(OBJECT_MAPPER.writeValueAsBytes(serviceAccount), creds.secretKey()));){
            byte[] jsonData = Crypto.decrypt(response.body().byteStream(), creds.secretKey());
            Credentials credentials = OBJECT_MAPPER.readValue(jsonData, AddServiceAccountResp.class).credentials();
            return credentials;
        }
    }

    public void updateServiceAccount(@Nonnull String accessKey, @Nullable String newSecretKey, @Nullable Map<String, Object> newPolicy, @Nullable boolean newStatus, @Nullable String newName, @Nullable String newDescription, @Nullable ZonedDateTime newExpiration) throws NoSuchAlgorithmException, InvalidKeyException, IOException, InvalidCipherTextException {
        if (accessKey == null || accessKey.isEmpty()) {
            throw new IllegalArgumentException("access key must be provided");
        }
        if (newName != null && !SERVICE_ACCOUNT_NAME_REGEX.matcher(newName).find()) {
            throw new IllegalArgumentException("new name must contain non-empty alphanumeric,  underscore and hyphen characters not longer than 32 characters");
        }
        if (newDescription != null && newDescription.length() > 256) {
            throw new IllegalArgumentException("new description must be at most 256 characters long");
        }
        HashMap<String, Object> serviceAccount = new HashMap<String, Object>();
        if (newSecretKey != null && !newSecretKey.isEmpty()) {
            serviceAccount.put("newSecretKey", newSecretKey);
        }
        if (newPolicy != null && !newPolicy.isEmpty()) {
            serviceAccount.put("newPolicy", newPolicy);
        }
        serviceAccount.put("newStatus", newStatus ? "on" : "off");
        if (newName != null && !newName.isEmpty()) {
            serviceAccount.put("newName", newName);
        }
        if (newDescription != null && !newDescription.isEmpty()) {
            serviceAccount.put("newDescription", newDescription);
        }
        if (newExpiration != null) {
            serviceAccount.put("newExpiration", newExpiration.format(Time.EXPIRATION_DATE_FORMAT));
        }
        Credentials creds = this.getCredentials();
        Response response = this.execute(Method.POST, Command.UPDATE_SERVICE_ACCOUNT, ImmutableMultimap.of("accessKey", accessKey), Crypto.encrypt(OBJECT_MAPPER.writeValueAsBytes(serviceAccount), creds.secretKey()));
        Throwable throwable = null;
        if (response != null) {
            if (throwable != null) {
                try {
                    response.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
            } else {
                response.close();
            }
        }
    }

    public void deleteServiceAccount(@Nonnull String accessKey) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        if (accessKey == null || accessKey.isEmpty()) {
            throw new IllegalArgumentException("access key must be provided");
        }
        Response response = this.execute(Method.DELETE, Command.DELETE_SERVICE_ACCOUNT, ImmutableMultimap.of("accessKey", accessKey), null);
        Throwable throwable = null;
        if (response != null) {
            if (throwable != null) {
                try {
                    response.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
            } else {
                response.close();
            }
        }
    }

    public ListServiceAccountResp listServiceAccount(@Nonnull String username) throws NoSuchAlgorithmException, InvalidKeyException, IOException, InvalidCipherTextException {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("user name must be provided");
        }
        try (Response response = this.execute(Method.GET, Command.LIST_SERVICE_ACCOUNTS, ImmutableMultimap.of("user", username), null);){
            Credentials creds = this.getCredentials();
            byte[] jsonData = Crypto.decrypt(response.body().byteStream(), creds.secretKey());
            ListServiceAccountResp listServiceAccountResp = OBJECT_MAPPER.readValue(jsonData, ListServiceAccountResp.class);
            return listServiceAccountResp;
        }
    }

    public GetServiceAccountInfoResp getServiceAccountInfo(@Nonnull String accessKey) throws NoSuchAlgorithmException, InvalidKeyException, IOException, InvalidCipherTextException {
        if (accessKey == null || accessKey.isEmpty()) {
            throw new IllegalArgumentException("access key must be provided");
        }
        try (Response response = this.execute(Method.GET, Command.INFO_SERVICE_ACCOUNT, ImmutableMultimap.of("accessKey", accessKey), null);){
            Credentials creds = this.getCredentials();
            byte[] jsonData = Crypto.decrypt(response.body().byteStream(), creds.secretKey());
            GetServiceAccountInfoResp getServiceAccountInfoResp = OBJECT_MAPPER.readValue(jsonData, GetServiceAccountInfoResp.class);
            return getServiceAccountInfoResp;
        }
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

    public static Builder builder() {
        return new Builder();
    }

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    public static final class Builder {
        private HttpUrl baseUrl;
        private String region = "";
        private Provider provider;
        private OkHttpClient httpClient;

        public Builder endpoint(String endpoint) {
            this.baseUrl = HttpUtils.getBaseUrl(endpoint);
            return this;
        }

        public Builder endpoint(String endpoint, int port, boolean secure) {
            HttpUrl url2 = HttpUtils.getBaseUrl(endpoint);
            if (port < 1 || port > 65535) {
                throw new IllegalArgumentException("port must be in range of 1 to 65535");
            }
            this.baseUrl = url2.newBuilder().port(port).scheme(secure ? "https" : "http").build();
            return this;
        }

        public Builder endpoint(HttpUrl url2) {
            HttpUtils.validateNotNull(url2, "url");
            HttpUtils.validateUrl(url2);
            this.baseUrl = url2;
            return this;
        }

        public Builder endpoint(URL url2) {
            HttpUtils.validateNotNull(url2, "url");
            return this.endpoint(HttpUrl.get(url2));
        }

        public Builder region(String region) {
            HttpUtils.validateNotNull(region, "region");
            this.region = region;
            return this;
        }

        public Builder credentials(String accessKey, String secretKey) {
            this.provider = new StaticProvider(accessKey, secretKey, null);
            return this;
        }

        public Builder credentialsProvider(Provider provider) {
            HttpUtils.validateNotNull(provider, "credential provider");
            this.provider = provider;
            return this;
        }

        public Builder httpClient(OkHttpClient httpClient) {
            HttpUtils.validateNotNull(httpClient, "http client");
            this.httpClient = httpClient;
            return this;
        }

        public MinioAdminClient build() {
            HttpUtils.validateNotNull(this.baseUrl, "base url");
            HttpUtils.validateNotNull(this.provider, "credential provider");
            if (this.httpClient == null) {
                this.httpClient = HttpUtils.newDefaultHttpClient(DEFAULT_CONNECTION_TIMEOUT, DEFAULT_CONNECTION_TIMEOUT, DEFAULT_CONNECTION_TIMEOUT);
            }
            return new MinioAdminClient(this.baseUrl, this.region, this.provider, this.httpClient);
        }
    }

    private static enum Command {
        ADD_USER("add-user"),
        USER_INFO("user-info"),
        LIST_USERS("list-users"),
        REMOVE_USER("remove-user"),
        ADD_CANNED_POLICY("add-canned-policy"),
        SET_USER_OR_GROUP_POLICY("set-user-or-group-policy"),
        LIST_CANNED_POLICIES("list-canned-policies"),
        REMOVE_CANNED_POLICY("remove-canned-policy"),
        SET_BUCKET_QUOTA("set-bucket-quota"),
        GET_BUCKET_QUOTA("get-bucket-quota"),
        DATA_USAGE_INFO("datausageinfo"),
        ADD_UPDATE_REMOVE_GROUP("update-group-members"),
        GROUP_INFO("group"),
        LIST_GROUPS("groups"),
        INFO("info"),
        ADD_SERVICE_ACCOUNT("add-service-account"),
        UPDATE_SERVICE_ACCOUNT("update-service-account"),
        LIST_SERVICE_ACCOUNTS("list-service-accounts"),
        DELETE_SERVICE_ACCOUNT("delete-service-account"),
        INFO_SERVICE_ACCOUNT("info-service-account");

        private final String value;

        private Command(String value) {
            this.value = value;
        }

        public String toString() {
            return this.value;
        }
    }
}

