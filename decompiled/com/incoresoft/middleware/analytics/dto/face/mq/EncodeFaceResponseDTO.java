/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.face.mq;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.Arrays;

public final class EncodeFaceResponseDTO {
    @JSONField(name="image")
    private final String encodedImage;
    @JSONField(name="code")
    private final int code;
    @JSONField(name="encoding")
    private final float[] encoding;
    @JSONField(name="points")
    private final double[] points;
    @JSONField(name="message")
    private final String message;
    @JSONField(name="status")
    private final String status;

    public EncodeFaceResponseDTO(String encodedImage, int code, float[] encoding, double[] points, String message, String status) {
        this.encodedImage = encodedImage;
        this.code = code;
        this.encoding = encoding;
        this.points = points;
        this.message = message;
        this.status = status;
    }

    public String getEncodedImage() {
        return this.encodedImage;
    }

    public int getCode() {
        return this.code;
    }

    public float[] getEncoding() {
        return this.encoding;
    }

    public double[] getPoints() {
        return this.points;
    }

    public String getMessage() {
        return this.message;
    }

    public String getStatus() {
        return this.status;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof EncodeFaceResponseDTO)) {
            return false;
        }
        EncodeFaceResponseDTO other = (EncodeFaceResponseDTO)o;
        if (this.getCode() != other.getCode()) {
            return false;
        }
        String this$encodedImage = this.getEncodedImage();
        String other$encodedImage = other.getEncodedImage();
        if (this$encodedImage == null ? other$encodedImage != null : !this$encodedImage.equals(other$encodedImage)) {
            return false;
        }
        if (!Arrays.equals(this.getEncoding(), other.getEncoding())) {
            return false;
        }
        if (!Arrays.equals(this.getPoints(), other.getPoints())) {
            return false;
        }
        String this$message = this.getMessage();
        String other$message = other.getMessage();
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) {
            return false;
        }
        String this$status = this.getStatus();
        String other$status = other.getStatus();
        return !(this$status == null ? other$status != null : !this$status.equals(other$status));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getCode();
        String $encodedImage = this.getEncodedImage();
        result = result * 59 + ($encodedImage == null ? 43 : $encodedImage.hashCode());
        result = result * 59 + Arrays.hashCode(this.getEncoding());
        result = result * 59 + Arrays.hashCode(this.getPoints());
        String $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        String $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        return result;
    }

    public String toString() {
        return "EncodeFaceResponseDTO(encodedImage=" + this.getEncodedImage() + ", code=" + this.getCode() + ", encoding=" + Arrays.toString(this.getEncoding()) + ", points=" + Arrays.toString(this.getPoints()) + ", message=" + this.getMessage() + ", status=" + this.getStatus() + ")";
    }
}

