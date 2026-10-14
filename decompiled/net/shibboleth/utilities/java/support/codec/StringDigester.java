/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.codec;

import com.google.common.base.Function;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.ParameterName;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.codec.Base64Support;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringDigester
implements Function<String, String> {
    @Nonnull
    public static final Charset DEFAULT_INPUT_CHARSET = Charset.forName("UTF-8");
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(StringDigester.class);
    @Nonnull
    @NotEmpty
    private String digestAlgorithm;
    @Nonnull
    private OutputFormat outputFormat;
    @Nonnull
    private Charset inputCharset;
    @Nullable
    private String salt;
    private boolean requireSalt;

    public StringDigester(@Nonnull @ParameterName(name="algorithm") @NotEmpty String algorithm, @Nonnull @ParameterName(name="format") OutputFormat format) throws NoSuchAlgorithmException {
        this(algorithm, format, DEFAULT_INPUT_CHARSET);
    }

    public StringDigester(@Nonnull @ParameterName(name="algorithm") @NotEmpty String algorithm, @Nonnull @ParameterName(name="format") OutputFormat format, @Nullable @ParameterName(name="charset") Charset charset) throws NoSuchAlgorithmException {
        this.digestAlgorithm = Constraint.isNotNull(StringSupport.trimOrNull(algorithm), "Digest algorithm was null or empty");
        MessageDigest.getInstance(this.digestAlgorithm);
        this.outputFormat = format;
        this.inputCharset = charset != null ? charset : DEFAULT_INPUT_CHARSET;
        this.requireSalt = false;
    }

    public void setSalt(@Nullable @NotEmpty String s2) {
        this.salt = s2 != null && !s2.isEmpty() ? s2 : null;
    }

    public void setRequireSalt(boolean flag) {
        this.requireSalt = flag;
    }

    @Override
    @Nullable
    public String apply(@Nullable String input) {
        MessageDigest digest;
        String trimmed = StringSupport.trimOrNull(input);
        if (trimmed == null) {
            this.log.debug("Trimmed input was null, returning null");
            return null;
        }
        if (this.salt != null) {
            trimmed = this.salt + trimmed;
        } else if (this.requireSalt) {
            this.log.debug("Salt was required but missing, no data returned");
            return null;
        }
        this.log.debug("Digesting input '{}' as charset '{}' with digest algorithm '{}' and output format '{}'", new Object[]{trimmed, this.inputCharset.displayName(), this.digestAlgorithm, this.outputFormat});
        byte[] inputBytes = trimmed.getBytes(this.inputCharset);
        try {
            digest = MessageDigest.getInstance(this.digestAlgorithm);
        }
        catch (NoSuchAlgorithmException e) {
            this.log.error("Digest algorithm '{}' was invalid", (Object)this.digestAlgorithm, (Object)e);
            return null;
        }
        byte[] digestedBytes = digest.digest(inputBytes);
        if (digestedBytes == null) {
            this.log.debug("Digested output was null, returning null");
            return null;
        }
        String output = null;
        switch (this.outputFormat) {
            case BASE64: {
                output = Base64Support.encode(digestedBytes, false);
                break;
            }
            case HEX_LOWER: {
                output = new String(Hex.encodeHex(digestedBytes, true));
                break;
            }
            case HEX_UPPER: {
                output = new String(Hex.encodeHex(digestedBytes, false));
                break;
            }
        }
        this.log.debug("Produced digested and formatted output '{}'", (Object)output);
        return output;
    }

    public static enum OutputFormat {
        BASE64,
        HEX_LOWER,
        HEX_UPPER;

    }
}

