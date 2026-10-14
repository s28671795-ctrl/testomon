/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.io.BaseEncoding;
import io.minio.Digest;
import io.minio.S3Escaper;
import io.minio.Time;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Request;

public class Signer {
    private static final Set<String> IGNORED_HEADERS = ImmutableSet.of("accept-encoding", "authorization", "user-agent");
    private static final Set<String> PRESIGN_IGNORED_HEADERS = ImmutableSet.of("accept-encoding", "authorization", "user-agent", "content-md5", "x-amz-content-sha256", "x-amz-date", new String[]{"x-amz-security-token"});
    private Request request;
    private String contentSha256;
    private ZonedDateTime date;
    private String region;
    private String accessKey;
    private String secretKey;
    private String prevSignature;
    private String scope;
    private Map<String, String> canonicalHeaders;
    private String signedHeaders;
    private HttpUrl url;
    private String canonicalQueryString;
    private String canonicalRequest;
    private String canonicalRequestHash;
    private String stringToSign;
    private byte[] signingKey;
    private String signature;
    private String authorization;

    private Signer(Request request, String contentSha256, ZonedDateTime date, String region, String accessKey, String secretKey, String prevSignature) {
        this.request = request;
        this.contentSha256 = contentSha256;
        this.date = date;
        this.region = region;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.prevSignature = prevSignature;
    }

    private void setScope(String serviceName) {
        this.scope = this.date.format(Time.SIGNER_DATE_FORMAT) + "/" + this.region + "/" + serviceName + "/aws4_request";
    }

    private void setCanonicalHeaders(Set<String> ignored_headers) {
        this.canonicalHeaders = new TreeMap<String, String>();
        Headers headers = this.request.headers();
        for (String name : headers.names()) {
            String signedHeader = name.toLowerCase(Locale.US);
            if (ignored_headers.contains(signedHeader)) continue;
            this.canonicalHeaders.put(signedHeader, headers.values(name).stream().map(value -> value.replaceAll("( +)", " ").trim()).collect(Collectors.joining(",")));
        }
        this.signedHeaders = Joiner.on(";").join(this.canonicalHeaders.keySet());
    }

    private void setCanonicalQueryString() {
        String encodedQuery = this.url.encodedQuery();
        if (encodedQuery == null) {
            this.canonicalQueryString = "";
            return;
        }
        Multimap signedQueryParams = MultimapBuilder.treeKeys().arrayListValues().build();
        for (String queryParam : encodedQuery.split("&")) {
            String[] tokens = queryParam.split("=");
            if (tokens.length > 1) {
                signedQueryParams.put(tokens[0], tokens[1]);
                continue;
            }
            signedQueryParams.put(tokens[0], "");
        }
        this.canonicalQueryString = Joiner.on("&").withKeyValueSeparator("=").join(signedQueryParams.entries());
    }

    private void setCanonicalRequest() throws NoSuchAlgorithmException {
        this.setCanonicalHeaders(IGNORED_HEADERS);
        this.url = this.request.url();
        this.setCanonicalQueryString();
        this.canonicalRequest = this.request.method() + "\n" + this.url.encodedPath() + "\n" + this.canonicalQueryString + "\n" + Joiner.on("\n").withKeyValueSeparator(":").join(this.canonicalHeaders) + "\n\n" + this.signedHeaders + "\n" + this.contentSha256;
        this.canonicalRequestHash = Digest.sha256Hash(this.canonicalRequest);
    }

    private void setStringToSign() {
        this.stringToSign = "AWS4-HMAC-SHA256\n" + this.date.format(Time.AMZ_DATE_FORMAT) + "\n" + this.scope + "\n" + this.canonicalRequestHash;
    }

    private void setChunkStringToSign() throws NoSuchAlgorithmException {
        this.stringToSign = "AWS4-HMAC-SHA256-PAYLOAD\n" + this.date.format(Time.AMZ_DATE_FORMAT) + "\n" + this.scope + "\n" + this.prevSignature + "\n" + Digest.sha256Hash("") + "\n" + this.contentSha256;
    }

    private void setSigningKey(String serviceName) throws NoSuchAlgorithmException, InvalidKeyException {
        String aws4SecretKey = "AWS4" + this.secretKey;
        byte[] dateKey = Signer.sumHmac(aws4SecretKey.getBytes(StandardCharsets.UTF_8), this.date.format(Time.SIGNER_DATE_FORMAT).getBytes(StandardCharsets.UTF_8));
        byte[] dateRegionKey = Signer.sumHmac(dateKey, this.region.getBytes(StandardCharsets.UTF_8));
        byte[] dateRegionServiceKey = Signer.sumHmac(dateRegionKey, serviceName.getBytes(StandardCharsets.UTF_8));
        this.signingKey = Signer.sumHmac(dateRegionServiceKey, "aws4_request".getBytes(StandardCharsets.UTF_8));
    }

    private void setSignature() throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] digest = Signer.sumHmac(this.signingKey, this.stringToSign.getBytes(StandardCharsets.UTF_8));
        this.signature = BaseEncoding.base16().encode(digest).toLowerCase(Locale.US);
    }

    private void setAuthorization() {
        this.authorization = "AWS4-HMAC-SHA256 Credential=" + this.accessKey + "/" + this.scope + ", SignedHeaders=" + this.signedHeaders + ", Signature=" + this.signature;
    }

    public static String getChunkSignature(String chunkSha256, ZonedDateTime date, String region, String secretKey, String prevSignature) throws NoSuchAlgorithmException, InvalidKeyException {
        Signer signer = new Signer(null, chunkSha256, date, region, null, secretKey, prevSignature);
        signer.setScope("s3");
        signer.setChunkStringToSign();
        signer.setSigningKey("s3");
        signer.setSignature();
        return signer.signature;
    }

    private static Request signV4(String serviceName, Request request, String region, String accessKey, String secretKey, String contentSha256) throws NoSuchAlgorithmException, InvalidKeyException {
        ZonedDateTime date = ZonedDateTime.parse(request.header("x-amz-date"), Time.AMZ_DATE_FORMAT);
        Signer signer = new Signer(request, contentSha256, date, region, accessKey, secretKey, null);
        signer.setScope(serviceName);
        signer.setCanonicalRequest();
        signer.setStringToSign();
        signer.setSigningKey(serviceName);
        signer.setSignature();
        signer.setAuthorization();
        return request.newBuilder().header("Authorization", signer.authorization).build();
    }

    public static Request signV4S3(Request request, String region, String accessKey, String secretKey, String contentSha256) throws NoSuchAlgorithmException, InvalidKeyException {
        return Signer.signV4("s3", request, region, accessKey, secretKey, contentSha256);
    }

    public static Request signV4Sts(Request request, String region, String accessKey, String secretKey, String contentSha256) throws NoSuchAlgorithmException, InvalidKeyException {
        return Signer.signV4("sts", request, region, accessKey, secretKey, contentSha256);
    }

    private void setPresignCanonicalRequest(int expires) throws NoSuchAlgorithmException {
        this.setCanonicalHeaders(PRESIGN_IGNORED_HEADERS);
        HttpUrl.Builder urlBuilder = this.request.url().newBuilder();
        urlBuilder.addEncodedQueryParameter(S3Escaper.encode("X-Amz-Algorithm"), S3Escaper.encode("AWS4-HMAC-SHA256"));
        urlBuilder.addEncodedQueryParameter(S3Escaper.encode("X-Amz-Credential"), S3Escaper.encode(this.accessKey + "/" + this.scope));
        urlBuilder.addEncodedQueryParameter(S3Escaper.encode("X-Amz-Date"), S3Escaper.encode(this.date.format(Time.AMZ_DATE_FORMAT)));
        urlBuilder.addEncodedQueryParameter(S3Escaper.encode("X-Amz-Expires"), S3Escaper.encode(Integer.toString(expires)));
        urlBuilder.addEncodedQueryParameter(S3Escaper.encode("X-Amz-SignedHeaders"), S3Escaper.encode(this.signedHeaders));
        this.url = urlBuilder.build();
        this.setCanonicalQueryString();
        this.canonicalRequest = this.request.method() + "\n" + this.url.encodedPath() + "\n" + this.canonicalQueryString + "\n" + Joiner.on("\n").withKeyValueSeparator(":").join(this.canonicalHeaders) + "\n\n" + this.signedHeaders + "\n" + this.contentSha256;
        this.canonicalRequestHash = Digest.sha256Hash(this.canonicalRequest);
    }

    public static HttpUrl presignV4(Request request, String region, String accessKey, String secretKey, int expires) throws NoSuchAlgorithmException, InvalidKeyException {
        String contentSha256 = "UNSIGNED-PAYLOAD";
        ZonedDateTime date = ZonedDateTime.parse(request.header("x-amz-date"), Time.AMZ_DATE_FORMAT);
        Signer signer = new Signer(request, contentSha256, date, region, accessKey, secretKey, null);
        signer.setScope("s3");
        signer.setPresignCanonicalRequest(expires);
        signer.setStringToSign();
        signer.setSigningKey("s3");
        signer.setSignature();
        return signer.url.newBuilder().addEncodedQueryParameter(S3Escaper.encode("X-Amz-Signature"), S3Escaper.encode(signer.signature)).build();
    }

    public static String credential(String accessKey, ZonedDateTime date, String region) {
        return accessKey + "/" + date.format(Time.SIGNER_DATE_FORMAT) + "/" + region + "/s3/aws4_request";
    }

    public static String postPresignV4(String stringToSign, String secretKey, ZonedDateTime date, String region) throws NoSuchAlgorithmException, InvalidKeyException {
        Signer signer = new Signer(null, null, date, region, null, secretKey, null);
        signer.stringToSign = stringToSign;
        signer.setSigningKey("s3");
        signer.setSignature();
        return signer.signature;
    }

    public static byte[] sumHmac(byte[] key, byte[] data) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(key, "HmacSHA256"));
        mac.update(data);
        return mac.doFinal();
    }
}

