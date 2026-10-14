/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.minio.Signer;
import io.minio.Time;
import io.minio.credentials.Credentials;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;

public class PostPolicy {
    private static final List<String> RESERVED_ELEMENTS = Arrays.asList("bucket", "x-amz-algorithm", "x-amz-credential", "x-amz-date", "policy", "x-amz-signature");
    private static final String ALGORITHM = "AWS4-HMAC-SHA256";
    private static final String EQ = "eq";
    private static final String STARTS_WITH = "starts-with";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private String bucketName;
    private ZonedDateTime expiration;
    private Map<String, Map<String, String>> conditions;
    private Long lowerLimit = null;
    private Long upperLimit = null;

    public PostPolicy(@Nonnull String bucketName, @Nonnull ZonedDateTime expiration) {
        if (bucketName.isEmpty()) {
            throw new IllegalArgumentException("bucket name cannot be empty");
        }
        LinkedHashMap<String, Map<String, String>> conditions = new LinkedHashMap<String, Map<String, String>>();
        conditions.put(EQ, new LinkedHashMap());
        conditions.put(STARTS_WITH, new LinkedHashMap());
        this.bucketName = bucketName;
        this.expiration = expiration;
        this.conditions = conditions;
    }

    private String trimDollar(String element) {
        return element.startsWith("$") ? element.substring(1, element.length()) : element;
    }

    public void addEqualsCondition(@Nonnull String element, @Nonnull String value) {
        if (element.isEmpty()) {
            throw new IllegalArgumentException("condition element cannot be empty");
        }
        if ("success_action_redirect".equals(element = this.trimDollar(element)) || "redirect".equals(element) || "content-length-range".equals(element)) {
            throw new IllegalArgumentException(element + " is unsupported for equals condition");
        }
        if (RESERVED_ELEMENTS.contains(element)) {
            throw new IllegalArgumentException(element + " cannot be set");
        }
        this.conditions.get(EQ).put(element, value);
    }

    public void removeEqualsCondition(@Nonnull String element) {
        if (element.isEmpty()) {
            throw new IllegalArgumentException("condition element cannot be empty");
        }
        this.conditions.get(EQ).remove(this.trimDollar(element));
    }

    public void addStartsWithCondition(@Nonnull String element, @Nonnull String value) {
        if (element.isEmpty()) {
            throw new IllegalArgumentException("condition element cannot be empty");
        }
        if ("success_action_status".equals(element = this.trimDollar(element)) || "content-length-range".equals(element) || element.startsWith("x-amz-") && !element.startsWith("x-amz-meta-")) {
            throw new IllegalArgumentException(element + " is unsupported for starts-with condition");
        }
        if (RESERVED_ELEMENTS.contains(element)) {
            throw new IllegalArgumentException(element + " cannot be set");
        }
        this.conditions.get(STARTS_WITH).put(element, value);
    }

    public void removeStartsWithCondition(String element) {
        if (element.isEmpty()) {
            throw new IllegalArgumentException("condition element cannot be empty");
        }
        this.conditions.get(STARTS_WITH).remove(this.trimDollar(element));
    }

    public void addContentLengthRangeCondition(long lowerLimit, long upperLimit) {
        if (lowerLimit < 0L) {
            throw new IllegalArgumentException("lower limit cannot be negative number");
        }
        if (upperLimit < 0L) {
            throw new IllegalArgumentException("upper limit cannot be negative number");
        }
        if (lowerLimit > upperLimit) {
            throw new IllegalArgumentException("lower limit cannot be greater than upper limit");
        }
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    public void addContentLengthRangeCondition(int lowerLimit, int upperLimit) {
        this.addContentLengthRangeCondition((long)lowerLimit, (long)upperLimit);
    }

    public void removeContentLengthRangeCondition() {
        this.lowerLimit = null;
        this.upperLimit = null;
    }

    public Map<String, String> formData(@Nonnull Credentials creds, @Nonnull String region) throws NoSuchAlgorithmException, InvalidKeyException {
        if (creds == null) {
            throw new IllegalArgumentException("credentials cannot be null");
        }
        if (region.isEmpty()) {
            throw new IllegalArgumentException("region cannot be empty");
        }
        if (!this.conditions.get(EQ).containsKey("key") && !this.conditions.get(STARTS_WITH).containsKey("key")) {
            throw new IllegalArgumentException("key condition must be set");
        }
        HashMap<String, Object> policyMap = new HashMap<String, Object>();
        policyMap.put("expiration", this.expiration.format(Time.EXPIRATION_DATE_FORMAT));
        LinkedList<List<Object>> conditionList = new LinkedList<List<Object>>();
        conditionList.add(Arrays.asList(EQ, "$bucket", this.bucketName));
        for (Map.Entry<String, Map<String, String>> condition : this.conditions.entrySet()) {
            for (Map.Entry<String, String> entry : condition.getValue().entrySet()) {
                conditionList.add(Arrays.asList(condition.getKey(), "$" + entry.getKey(), entry.getValue()));
            }
        }
        if (this.lowerLimit != null && this.upperLimit != null) {
            conditionList.add(Arrays.asList("content-length-range", this.lowerLimit, this.upperLimit));
        }
        ZonedDateTime utcNow = ZonedDateTime.now(Time.UTC);
        String credential = Signer.credential(creds.accessKey(), utcNow, region);
        String amzDate = utcNow.format(Time.AMZ_DATE_FORMAT);
        conditionList.add(Arrays.asList(EQ, "$x-amz-algorithm", ALGORITHM));
        conditionList.add(Arrays.asList(EQ, "$x-amz-credential", credential));
        if (creds.sessionToken() != null) {
            conditionList.add(Arrays.asList(EQ, "$x-amz-security-token", creds.sessionToken()));
        }
        conditionList.add(Arrays.asList(EQ, "$x-amz-date", amzDate));
        policyMap.put("conditions", conditionList);
        byte[] policyBytes = null;
        try {
            policyBytes = objectMapper.writeValueAsString(policyMap).getBytes(StandardCharsets.UTF_8);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String policy = Base64.getEncoder().encodeToString(policyBytes);
        String signature = Signer.postPresignV4(policy, creds.secretKey(), utcNow, region);
        HashMap<String, String> formData = new HashMap<String, String>();
        formData.put("x-amz-algorithm", ALGORITHM);
        formData.put("x-amz-credential", credential);
        if (creds.sessionToken() != null) {
            formData.put("x-amz-security-token", creds.sessionToken());
        }
        formData.put("x-amz-date", amzDate);
        formData.put("policy", policy);
        formData.put("x-amz-signature", signature);
        return formData;
    }

    public String bucket() {
        return this.bucketName;
    }
}

