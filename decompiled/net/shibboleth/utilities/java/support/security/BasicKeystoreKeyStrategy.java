/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.crypto.SecretKey;
import net.shibboleth.utilities.java.support.annotation.Duration;
import net.shibboleth.utilities.java.support.annotation.constraint.NonNegative;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.component.AbstractInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.logic.ConstraintViolationException;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resource.Resource;
import net.shibboleth.utilities.java.support.security.DataSealerKeyStrategy;
import net.shibboleth.utilities.java.support.security.KeyNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicKeystoreKeyStrategy
extends AbstractInitializableComponent
implements DataSealerKeyStrategy {
    @Nonnull
    @NotEmpty
    public static final String CURRENT_VERSION_PROP = "CurrentVersion";
    @Nonnull
    private Logger log = LoggerFactory.getLogger(BasicKeystoreKeyStrategy.class);
    @NonnullAfterInit
    private String keystoreType = "JCEKS";
    @NonnullAfterInit
    private Resource keystoreResource;
    @NonnullAfterInit
    private Resource keyVersionResource;
    @NonnullAfterInit
    private String keystorePassword;
    @NonnullAfterInit
    private String keyAlias;
    @NonnullAfterInit
    private String keyPassword;
    @NonnullAfterInit
    private String currentAlias;
    @NonnullAfterInit
    private SecretKey defaultKey;
    @Duration
    @NonNegative
    private long updateInterval = 900000L;
    private Timer updateTaskTimer;
    private Timer internalTaskTimer;
    private TimerTask updateTask;

    public void setKeystoreType(@Nonnull @NotEmpty String type) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.keystoreType = Constraint.isNotNull(StringSupport.trimOrNull(type), "Keystore type cannot be null or empty");
    }

    public void setKeystoreResource(@Nonnull @NotEmpty Resource resource) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.keystoreResource = Constraint.isNotNull(resource, "Keystore resource cannot be null");
    }

    public void setKeyVersionResource(@Nonnull @NotEmpty Resource resource) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.keyVersionResource = Constraint.isNotNull(resource, "Key version resource cannot be null");
    }

    public void setKeystorePassword(@Nonnull @NotEmpty String password) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.keystorePassword = Constraint.isNotNull(password, "Keystore password cannot be null");
    }

    public void setKeyAlias(@Nonnull @NotEmpty String alias) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.keyAlias = Constraint.isNotNull(StringSupport.trimOrNull(alias), "Key alias base cannot be null or empty");
    }

    public void setKeyPassword(@Nonnull @NotEmpty String password) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.keyPassword = Constraint.isNotNull(password, "Key password cannot be null");
    }

    @Duration
    public void setUpdateInterval(@Duration @NonNegative long interval) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.updateInterval = Constraint.isGreaterThanOrEqual(0L, interval, "Update interval must be greater than or equal to zero");
    }

    public void setUpdateTaskTimer(@Nullable Timer timer) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.updateTaskTimer = timer;
    }

    @Override
    public void doInitialize() throws ComponentInitializationException {
        try {
            try {
                Constraint.isNotNull(this.keystoreType, "Keystore type cannot be null");
                Constraint.isNotNull(this.keystoreResource, "Keystore resource cannot be null");
                Constraint.isNotNull(this.keyVersionResource, "Key version resource cannot be null");
                Constraint.isNotNull(this.keystorePassword, "Keystore password cannot be null");
                Constraint.isNotNull(this.keyAlias, "Key alias base cannot be null");
                Constraint.isNotNull(this.keyPassword, "Key password cannot be null");
            }
            catch (ConstraintViolationException e) {
                throw new ComponentInitializationException(e);
            }
            this.updateDefaultKey();
        }
        catch (KeyException e) {
            this.log.error("Error loading default key from base name '{}'", (Object)this.keyAlias, (Object)e);
            throw new ComponentInitializationException("Exception loading the default key", e);
        }
        if (this.updateInterval > 0L) {
            this.updateTask = new TimerTask(){

                @Override
                public void run() {
                    try {
                        BasicKeystoreKeyStrategy.this.updateDefaultKey();
                    }
                    catch (KeyException keyException) {
                        // empty catch block
                    }
                }
            };
            this.internalTaskTimer = this.updateTaskTimer == null ? new Timer(true) : this.updateTaskTimer;
            this.internalTaskTimer.schedule(this.updateTask, this.updateInterval, this.updateInterval);
        }
    }

    @Override
    protected void doDestroy() {
        if (this.updateTask != null) {
            this.updateTask.cancel();
            this.updateTask = null;
            if (this.updateTaskTimer == null) {
                this.internalTaskTimer.cancel();
            }
            this.internalTaskTimer = null;
        }
        super.doDestroy();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Nonnull
    public Pair<String, SecretKey> getDefaultKey() throws KeyException {
        ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
        BasicKeystoreKeyStrategy basicKeystoreKeyStrategy = this;
        synchronized (basicKeystoreKeyStrategy) {
            return new Pair<String, SecretKey>(this.currentAlias, this.defaultKey);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Nonnull
    public SecretKey getKey(@Nonnull @NotEmpty String name) throws KeyException {
        BasicKeystoreKeyStrategy basicKeystoreKeyStrategy = this;
        synchronized (basicKeystoreKeyStrategy) {
            if (this.defaultKey != null && name.equals(this.currentAlias)) {
                return this.defaultKey;
            }
        }
        try {
            KeyStore ks = KeyStore.getInstance(this.keystoreType);
            ks.load(this.keystoreResource.getInputStream(), this.keystorePassword.toCharArray());
            Key loadedKey = ks.getKey(name, this.keyPassword.toCharArray());
            if (loadedKey == null) {
                this.log.info("Key '{}' not found", (Object)name);
                throw new KeyNotFoundException("Key was not present in keystore");
            }
            if (!(loadedKey instanceof SecretKey)) {
                this.log.error("Key '{}' is not a symmetric key", (Object)name);
                throw new KeyException("Key was of incorrect type");
            }
            return (SecretKey)loadedKey;
        }
        catch (IOException | KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException | CertificateException e) {
            this.log.error("Error loading key named '{}'", (Object)name, (Object)e);
            throw new KeyException(e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void updateDefaultKey() throws KeyException {
        BasicKeystoreKeyStrategy basicKeystoreKeyStrategy = this;
        synchronized (basicKeystoreKeyStrategy) {
            try (InputStream is = this.keyVersionResource.getInputStream();){
                Properties props = new Properties();
                props.load(is);
                StringBuilder builder = new StringBuilder(this.keyAlias);
                builder.append(props.getProperty(CURRENT_VERSION_PROP, ""));
                String newAlias = builder.toString();
                if (this.currentAlias == null) {
                    this.log.info("Loading initial default key: {}", (Object)newAlias);
                } else if (!this.currentAlias.equals(newAlias)) {
                    this.log.info("Updating default key from {} to {}", (Object)this.currentAlias, (Object)newAlias);
                } else {
                    this.log.debug("Default key version has not changed, still {}", (Object)this.currentAlias);
                    return;
                }
                this.defaultKey = this.getKey(newAlias);
                this.currentAlias = newAlias;
                this.log.info("Default key updated to {}", (Object)this.currentAlias);
            }
            catch (IOException e) {
                this.log.error("IOException updating key version", e);
                throw new KeyException(e);
            }
        }
    }
}

