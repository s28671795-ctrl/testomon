/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Result<T> {
    private final T type;
    private final Exception ex;

    public Result(T type) {
        this.type = type;
        this.ex = null;
    }

    public Result(Exception ex) {
        this.type = null;
        this.ex = ex;
    }

    public T get() throws ErrorResponseException, IllegalArgumentException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, JsonMappingException, JsonParseException, NoSuchAlgorithmException, ServerException, XmlParserException {
        if (this.ex == null) {
            return this.type;
        }
        if (this.ex instanceof ErrorResponseException) {
            throw (ErrorResponseException)this.ex;
        }
        if (this.ex instanceof IllegalArgumentException) {
            throw (IllegalArgumentException)this.ex;
        }
        if (this.ex instanceof InsufficientDataException) {
            throw (InsufficientDataException)this.ex;
        }
        if (this.ex instanceof InternalException) {
            throw (InternalException)this.ex;
        }
        if (this.ex instanceof InvalidKeyException) {
            throw (InvalidKeyException)this.ex;
        }
        if (this.ex instanceof InvalidResponseException) {
            throw (InvalidResponseException)this.ex;
        }
        if (this.ex instanceof IOException) {
            throw (IOException)this.ex;
        }
        if (this.ex instanceof JsonMappingException) {
            throw (JsonMappingException)this.ex;
        }
        if (this.ex instanceof JsonParseException) {
            throw (JsonParseException)this.ex;
        }
        if (this.ex instanceof NoSuchAlgorithmException) {
            throw (NoSuchAlgorithmException)this.ex;
        }
        if (this.ex instanceof ServerException) {
            throw (ServerException)this.ex;
        }
        if (this.ex instanceof XmlParserException) {
            throw (XmlParserException)this.ex;
        }
        throw new RuntimeException("Exception not handled", this.ex);
    }
}

