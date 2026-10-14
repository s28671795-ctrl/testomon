/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.status;

import com.incoresoft.crypto.Cipher;
import com.incoresoft.middleware.analytics.AnalyticsStatus;
import com.incoresoft.vezha.analytics.errors.AnalyticsErrorOrigin;
import com.incoresoft.vezha.analytics.errors.AnalyticsSideError;
import com.incoresoft.vezha.analytics.errors.BaseAnalyticsError;
import com.incoresoft.vezha.analytics.servers.Server;
import com.incoresoft.vezha.analytics.servers.models.GpuCard;
import com.incoresoft.vezha.utils.PasswordGenerator;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

public final class AnalyticsStatusImpl {
    private final AtomicReference<AnalyticsStatus> status = new AtomicReference();
    private final AtomicReference<Server> server = new AtomicReference();
    private final AtomicInteger lastGpuId = new AtomicInteger();
    private final AtomicLong startAt = new AtomicLong();
    private final AtomicLong lastMessage = new AtomicLong();
    private final AtomicReference<String> startSignature = new AtomicReference();
    private final AtomicReference<String> encryptionKey = new AtomicReference();
    private final Set<BaseAnalyticsError> errors = Collections.newSetFromMap(new ConcurrentHashMap());

    public AnalyticsStatusImpl(AnalyticsStatus analyticsStatus, Integer lastGpuId, Server server, String startSignature, Long startAt) {
        this.status.set(analyticsStatus);
        this.lastGpuId.set(lastGpuId);
        this.server.set(server);
        this.startSignature.set(startSignature);
        this.startAt.set(startAt);
    }

    public boolean checkStartSignature(String sig) {
        return Objects.equals(this.startSignature.get(), sig);
    }

    public String initEncryption() {
        String signature = Optional.ofNullable(this.startSignature.get()).orElseGet(() -> PasswordGenerator.generateAlphaNumericPassword(25));
        this.startSignature.set(signature);
        this.encryptionKey.set(Cipher.generateKeyFromSig(signature));
        return this.encryptionKey.get();
    }

    public void resetEncryption() {
        this.encryptionKey.set(null);
        this.startSignature.set(null);
    }

    public void changeStatus(AnalyticsStatus status, Server server, Integer gpuId, Instant startAt, boolean resetEncryption) {
        this.status.set(status);
        this.server.set(server);
        this.lastGpuId.set(Optional.ofNullable(gpuId).orElse(-1));
        this.startAt.set(Optional.ofNullable(startAt).map(Instant::toEpochMilli).orElse(0L));
        if (resetEncryption) {
            this.resetEncryption();
        }
    }

    public String getPreviewUrl(String pattern, int id) {
        Server currentServer = this.server.get();
        if (currentServer == null) {
            return null;
        }
        Pair<String, Integer> streamingHostPort = this.buildStreamingUri(currentServer);
        return String.format(pattern, streamingHostPort.getLeft(), streamingHostPort.getRight(), id);
    }

    public String getH264Preview(int id) {
        return this.getPreviewUrl("//%s:%d/h264/%d", id);
    }

    public String getMJPEGPreview(int id) {
        return this.getPreviewUrl("//%s:%d/%d", id);
    }

    public boolean getH264Stream() {
        Server currentServer = this.server.get();
        if (currentServer == null) {
            return false;
        }
        HashSet<GpuCard> graphics = new HashSet<GpuCard>(currentServer.getGraphics().values());
        Integer currentGpuId = this.getLastGpuIdValue();
        return currentGpuId != null && graphics.stream().anyMatch(it -> it.getId() == currentGpuId.intValue() && it.getNvEnc() != -1.0);
    }

    private Pair<String, Integer> buildStreamingUri(Server currentServer) {
        if (currentServer.isExternalAddressEnabled()) {
            return Pair.of(currentServer.getExternalIpAddress(), currentServer.getExternalPort());
        }
        if (currentServer.getExternalInterface() != null && currentServer.getNetInterfaces().containsKey(currentServer.getExternalInterface())) {
            return Pair.of((String)currentServer.getNetInterfaces().get(currentServer.getExternalInterface()), currentServer.getPreviewPort());
        }
        return Pair.of(currentServer.getIpAddress(), currentServer.getPreviewPort());
    }

    private boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    public Set<BaseAnalyticsError> getErrors() {
        return Collections.unmodifiableSet(this.errors);
    }

    public void setAnalyticsSideErrors(Set<AnalyticsSideError> errors2) {
        this.clearAnalyticsSideErrors();
        this.errors.addAll(errors2.stream().filter(it -> it.getOrigin() == AnalyticsErrorOrigin.ANALYTICS).collect(Collectors.toUnmodifiableSet()));
    }

    private void clearAnalyticsSideErrors() {
        this.errors.removeIf(it -> it.getOrigin() == AnalyticsErrorOrigin.ANALYTICS);
    }

    public void setErrors(@NotNull Set<BaseAnalyticsError> errors2) {
        this.clearErrors();
        this.errors.addAll(errors2);
    }

    public void addError(@NotNull BaseAnalyticsError error) {
        this.errors.add(error);
    }

    public void removeError(@NotNull BaseAnalyticsError error) {
        this.errors.remove(error);
    }

    public void clearErrors() {
        this.errors.clear();
    }

    public Instant getStartInstant() {
        if (this.getServerValue() == null || this.startAt.get() == 0L) {
            return null;
        }
        return Optional.of(this.startAt).map(AtomicLong::get).map(Instant::ofEpochMilli).orElse(null);
    }

    public Server getServerValue() {
        return Optional.of(this.server).map(AtomicReference::get).orElse(null);
    }

    public Integer getLastGpuIdValue() {
        return Optional.of(this.lastGpuId).map(AtomicInteger::get).filter(it -> it != -1).orElse(null);
    }

    public void setStatusValue(AnalyticsStatus analyticsStatus) {
        this.status.set(analyticsStatus);
    }

    public AnalyticsStatus getStatusValue() {
        return Optional.of(this.status).map(AtomicReference::get).orElse(null);
    }

    public void setLastGpuIdValue(int optimalGPU) {
        this.lastGpuId.set(optimalGPU);
    }

    public String getStartSignatureValue() {
        return Optional.of(this.startSignature).map(AtomicReference::get).orElse(null);
    }

    public void updateLastMessage() {
        this.lastMessage.set(Instant.now().toEpochMilli());
    }

    public String getEncryptionKeyValue() {
        return Optional.of(this.encryptionKey).map(AtomicReference::get).orElse(null);
    }

    public void setServerValue(Server server) {
        this.server.set(server);
    }

    public AtomicReference<AnalyticsStatus> getStatus() {
        return this.status;
    }

    public AtomicReference<Server> getServer() {
        return this.server;
    }

    public AtomicInteger getLastGpuId() {
        return this.lastGpuId;
    }

    public AtomicLong getStartAt() {
        return this.startAt;
    }

    public AtomicLong getLastMessage() {
        return this.lastMessage;
    }

    public AtomicReference<String> getStartSignature() {
        return this.startSignature;
    }

    public AtomicReference<String> getEncryptionKey() {
        return this.encryptionKey;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AnalyticsStatusImpl)) {
            return false;
        }
        AnalyticsStatusImpl other = (AnalyticsStatusImpl)o;
        AtomicReference<AnalyticsStatus> this$status = this.getStatus();
        AtomicReference<AnalyticsStatus> other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) {
            return false;
        }
        AtomicReference<Server> this$server = this.getServer();
        AtomicReference<Server> other$server = other.getServer();
        if (this$server == null ? other$server != null : !this$server.equals(other$server)) {
            return false;
        }
        AtomicInteger this$lastGpuId = this.getLastGpuId();
        AtomicInteger other$lastGpuId = other.getLastGpuId();
        if (this$lastGpuId == null ? other$lastGpuId != null : !this$lastGpuId.equals(other$lastGpuId)) {
            return false;
        }
        AtomicLong this$startAt = this.getStartAt();
        AtomicLong other$startAt = other.getStartAt();
        if (this$startAt == null ? other$startAt != null : !this$startAt.equals(other$startAt)) {
            return false;
        }
        AtomicLong this$lastMessage = this.getLastMessage();
        AtomicLong other$lastMessage = other.getLastMessage();
        if (this$lastMessage == null ? other$lastMessage != null : !this$lastMessage.equals(other$lastMessage)) {
            return false;
        }
        AtomicReference<String> this$startSignature = this.getStartSignature();
        AtomicReference<String> other$startSignature = other.getStartSignature();
        if (this$startSignature == null ? other$startSignature != null : !this$startSignature.equals(other$startSignature)) {
            return false;
        }
        AtomicReference<String> this$encryptionKey = this.getEncryptionKey();
        AtomicReference<String> other$encryptionKey = other.getEncryptionKey();
        if (this$encryptionKey == null ? other$encryptionKey != null : !this$encryptionKey.equals(other$encryptionKey)) {
            return false;
        }
        Set<BaseAnalyticsError> this$errors = this.getErrors();
        Set<BaseAnalyticsError> other$errors = other.getErrors();
        return !(this$errors == null ? other$errors != null : !((Object)this$errors).equals(other$errors));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        AtomicReference<AnalyticsStatus> $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        AtomicReference<Server> $server = this.getServer();
        result = result * 59 + ($server == null ? 43 : $server.hashCode());
        AtomicInteger $lastGpuId = this.getLastGpuId();
        result = result * 59 + ($lastGpuId == null ? 43 : $lastGpuId.hashCode());
        AtomicLong $startAt = this.getStartAt();
        result = result * 59 + ($startAt == null ? 43 : $startAt.hashCode());
        AtomicLong $lastMessage = this.getLastMessage();
        result = result * 59 + ($lastMessage == null ? 43 : $lastMessage.hashCode());
        AtomicReference<String> $startSignature = this.getStartSignature();
        result = result * 59 + ($startSignature == null ? 43 : $startSignature.hashCode());
        AtomicReference<String> $encryptionKey = this.getEncryptionKey();
        result = result * 59 + ($encryptionKey == null ? 43 : $encryptionKey.hashCode());
        Set<BaseAnalyticsError> $errors = this.getErrors();
        result = result * 59 + ($errors == null ? 43 : ((Object)$errors).hashCode());
        return result;
    }

    public String toString() {
        return "AnalyticsStatusImpl(status=" + String.valueOf(this.getStatus()) + ", server=" + String.valueOf(this.getServer()) + ", lastGpuId=" + String.valueOf(this.getLastGpuId()) + ", startAt=" + String.valueOf(this.getStartAt()) + ", lastMessage=" + String.valueOf(this.getLastMessage()) + ", startSignature=" + String.valueOf(this.getStartSignature()) + ", encryptionKey=" + String.valueOf(this.getEncryptionKey()) + ", errors=" + String.valueOf(this.getErrors()) + ")";
    }
}

