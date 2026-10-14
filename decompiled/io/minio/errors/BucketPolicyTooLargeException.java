/*
 * Decompiled with CFR 0.152.
 */
package io.minio.errors;

import io.minio.errors.MinioException;

public class BucketPolicyTooLargeException
extends MinioException {
    private static final long serialVersionUID = -4589481215582512545L;

    public BucketPolicyTooLargeException(String bucketName) {
        super("Bucket policy is larger than 20KiB size for bucket " + bucketName);
    }
}

