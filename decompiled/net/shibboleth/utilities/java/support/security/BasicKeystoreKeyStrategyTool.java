/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.beust.jcommander.JCommander
 *  com.beust.jcommander.Parameter
 *  com.beust.jcommander.converters.BaseConverter
 */
package net.shibboleth.utilities.java.support.security;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.BaseConverter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyException;
import java.security.KeyStore;
import java.util.Properties;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.annotation.constraint.Positive;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicKeystoreKeyStrategyTool {
    @Nonnull
    private Logger log = LoggerFactory.getLogger(BasicKeystoreKeyStrategyTool.class);
    @Nonnull
    private final CommandLineArgs args = new CommandLineArgs();

    public void setKeyType(@Nonnull @NotEmpty String type) {
        this.args.keyType = Constraint.isNotNull(StringSupport.trimOrNull(type), "Key type cannot be null or empty");
    }

    public void setKeySize(@Positive int size) {
        Constraint.isGreaterThan(0L, size, "Key size must be greater than 0");
        this.args.keySize = size;
    }

    public void setKeyAlias(@Nonnull @NotEmpty String alias) {
        this.args.keyAlias = Constraint.isNotNull(StringSupport.trimOrNull(alias), "Key alias base cannot be null or empty");
    }

    public void setKeyCount(@Positive int count) {
        Constraint.isGreaterThan(0L, count, "Key count must be greater than 0");
        this.args.keyCount = count;
    }

    public void setKeystoreType(@Nonnull @NotEmpty String type) {
        this.args.keystoreType = Constraint.isNotNull(StringSupport.trimOrNull(type), "Keystore type cannot be null or empty");
    }

    public void setKeystoreFile(@Nonnull File file) {
        this.args.keystoreFile = Constraint.isNotNull(file, "Keystore file cannot be null");
    }

    public void setKeystorePassword(@Nullable String password) {
        this.args.keystorePassword = password;
    }

    public void setVersionFile(@Nonnull File file) {
        this.args.versionFile = Constraint.isNotNull(file, "Key versioning file cannot be null");
    }

    public void changeKey() throws Exception {
        String oldAlias;
        int currentVersion;
        KeyStore ks = KeyStore.getInstance(this.args.keystoreType);
        try (FileInputStream ksIn = this.args.keystoreFile.exists() ? new FileInputStream(this.args.keystoreFile) : null;){
            ks.load(ksIn, this.args.keystorePassword.toCharArray());
        }
        Properties versionInfo = new Properties();
        if (this.args.versionFile.exists()) {
            x2 = null;
            try (FileInputStream versionIn = new FileInputStream(this.args.versionFile);){
                versionInfo.load(versionIn);
            }
            catch (Throwable x2) {
                x2 = x2;
                throw x2;
            }
        }
        if ((currentVersion = Integer.parseInt(versionInfo.getProperty("CurrentVersion", "0"))) == 0) {
            this.log.info("No existing versioning property, initializing...");
        } else {
            this.log.info("Incrementing key version from {} to {}", (Object)currentVersion, (Object)(currentVersion + 1));
        }
        String newKeyAlias = this.args.keyAlias + Integer.toString(++currentVersion);
        if (ks.containsAlias(newKeyAlias)) {
            this.log.error("Keystore already contains an entry named {}, exiting", (Object)newKeyAlias);
            throw new KeyException("Entry for new key already exists");
        }
        KeyGenerator keyGenerator = KeyGenerator.getInstance(this.args.keyType);
        keyGenerator.init(this.args.keySize);
        SecretKey newKey = keyGenerator.generateKey();
        ks.setKeyEntry(newKeyAlias, newKey, this.args.keystorePassword.toCharArray(), null);
        for (int oldVersion = currentVersion - this.args.keyCount; oldVersion > 0 && ks.containsAlias(oldAlias = this.args.keyAlias + Integer.toString(oldVersion)); --oldVersion) {
            this.log.info("Deleting old key: {}", (Object)oldAlias);
            ks.deleteEntry(oldAlias);
        }
        try (FileOutputStream ksOut = new FileOutputStream(this.args.keystoreFile);){
            ks.store(ksOut, this.args.keystorePassword.toCharArray());
        }
        var9_18 = null;
        try (FileOutputStream versionOut = new FileOutputStream(this.args.versionFile);){
            versionInfo.setProperty("CurrentVersion", Integer.toString(currentVersion));
            versionInfo.store(versionOut, null);
        }
        catch (Throwable throwable) {
            var9_18 = throwable;
            throw throwable;
        }
    }

    public static void main(@Nonnull String[] args2) throws Exception {
        BasicKeystoreKeyStrategyTool tool = new BasicKeystoreKeyStrategyTool();
        JCommander jc = new JCommander((Object)tool.args, args2);
        if (tool.args.help) {
            jc.setProgramName("BasicKeystoreKeyStrategyTool");
            jc.usage();
            return;
        }
        tool.changeKey();
    }

    private static class CommandLineArgs {
        @Nonnull
        @NotEmpty
        public static final String HELP = "--help";
        @Nonnull
        @NotEmpty
        public static final String KEY_TYPE = "--type";
        @Nonnull
        @NotEmpty
        public static final String KEY_SIZE = "--size";
        @Nonnull
        @NotEmpty
        public static final String KEY_ALIAS = "--alias";
        @Nonnull
        @NotEmpty
        public static final String KEY_COUNT = "--count";
        @Nonnull
        @NotEmpty
        public static final String STORE_TYPE = "--storetype";
        @Nonnull
        @NotEmpty
        public static final String STORE_FILE = "--storefile";
        @Nonnull
        @NotEmpty
        public static final String STORE_PASS = "--storepass";
        @Nonnull
        @NotEmpty
        public static final String VERSION_FILE = "--versionfile";
        @Parameter(names={"--help"}, description="Display program usage", help=true)
        private boolean help;
        @Parameter(names={"--type"}, description="Type of key to generate (default: AES)")
        @Nonnull
        @NotEmpty
        private String keyType = "AES";
        @Parameter(names={"--size"}, description="Size of key to generate (default: 128)")
        @Positive
        private int keySize = 128;
        @Parameter(names={"--alias"}, required=true, description="Base name of key alias")
        @Nullable
        private String keyAlias;
        @Parameter(names={"--count"}, description="Number of keys to maintain (default: 30)")
        @Positive
        private int keyCount = 30;
        @Parameter(names={"--storetype"}, description="Type of keystore to generate (default: JCEKS)")
        @Nonnull
        @NotEmpty
        private String keystoreType = "JCEKS";
        @Parameter(names={"--storefile"}, required=true, converter=FileConverter.class, description="Path to keystore")
        @Nullable
        private File keystoreFile;
        @Parameter(names={"--storepass"}, required=true, description="Password for keystore")
        @Nullable
        private String keystorePassword;
        @Parameter(names={"--versionfile"}, required=true, converter=FileConverter.class, description="Path to key versioning file")
        @Nullable
        private File versionFile;

        private CommandLineArgs() {
        }
    }

    public static class FileConverter
    extends BaseConverter<File> {
        public FileConverter(String optionName) {
            super(optionName);
        }

        public File convert(String value) {
            return new File(value);
        }
    }
}

