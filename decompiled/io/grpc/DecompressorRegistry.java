/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import io.grpc.Codec;
import io.grpc.Decompressor;
import io.grpc.ExperimentalApi;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1704")
@ThreadSafe
public final class DecompressorRegistry {
    static final Joiner ACCEPT_ENCODING_JOINER = Joiner.on(',');
    private static final DecompressorRegistry DEFAULT_INSTANCE = DecompressorRegistry.emptyInstance().with(new Codec.Gzip(), true).with(Codec.Identity.NONE, false);
    private final Map<String, DecompressorInfo> decompressors;
    private final byte[] advertisedDecompressors;

    public static DecompressorRegistry emptyInstance() {
        return new DecompressorRegistry();
    }

    public static DecompressorRegistry getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public DecompressorRegistry with(Decompressor d, boolean advertised) {
        return new DecompressorRegistry(d, advertised, this);
    }

    private DecompressorRegistry(Decompressor d, boolean advertised, DecompressorRegistry parent) {
        String encoding = d.getMessageEncoding();
        Preconditions.checkArgument(!encoding.contains(","), "Comma is currently not allowed in message encoding");
        int newSize = parent.decompressors.size();
        if (!parent.decompressors.containsKey(d.getMessageEncoding())) {
            ++newSize;
        }
        LinkedHashMap<String, DecompressorInfo> newDecompressors = new LinkedHashMap<String, DecompressorInfo>(newSize);
        for (DecompressorInfo di : parent.decompressors.values()) {
            String previousEncoding = di.decompressor.getMessageEncoding();
            if (previousEncoding.equals(encoding)) continue;
            newDecompressors.put(previousEncoding, new DecompressorInfo(di.decompressor, di.advertised));
        }
        newDecompressors.put(encoding, new DecompressorInfo(d, advertised));
        this.decompressors = Collections.unmodifiableMap(newDecompressors);
        this.advertisedDecompressors = ACCEPT_ENCODING_JOINER.join(this.getAdvertisedMessageEncodings()).getBytes(Charset.forName("US-ASCII"));
    }

    private DecompressorRegistry() {
        this.decompressors = new LinkedHashMap<String, DecompressorInfo>(0);
        this.advertisedDecompressors = new byte[0];
    }

    public Set<String> getKnownMessageEncodings() {
        return this.decompressors.keySet();
    }

    byte[] getRawAdvertisedMessageEncodings() {
        return this.advertisedDecompressors;
    }

    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1704")
    public Set<String> getAdvertisedMessageEncodings() {
        HashSet<String> advertisedDecompressors = new HashSet<String>(this.decompressors.size());
        for (Map.Entry<String, DecompressorInfo> entry : this.decompressors.entrySet()) {
            if (!entry.getValue().advertised) continue;
            advertisedDecompressors.add(entry.getKey());
        }
        return Collections.unmodifiableSet(advertisedDecompressors);
    }

    @Nullable
    public Decompressor lookupDecompressor(String messageEncoding) {
        DecompressorInfo info = this.decompressors.get(messageEncoding);
        return info != null ? info.decompressor : null;
    }

    private static final class DecompressorInfo {
        final Decompressor decompressor;
        final boolean advertised;

        DecompressorInfo(Decompressor decompressor, boolean advertised) {
            this.decompressor = Preconditions.checkNotNull(decompressor, "decompressor");
            this.advertised = advertised;
        }
    }
}

