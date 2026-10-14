/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.keymanager;

import java.net.Socket;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Optional;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedKeyManager;
import nl.altindag.ssl.keymanager.DelegatingX509ExtendedKeyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingX509ExtendedKeyManager
extends DelegatingX509ExtendedKeyManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingX509ExtendedKeyManager.class);
    private static final String CHOOSE_ALIAS_LOG_MESSAGE_TEMPLATE = "Attempting to find a %s alias for key types %s%s.%s";
    private static final String FOUND_ALIAS_LOG_MESSAGE_TEMPLATE = "Found the following %s aliases [%s] for key types %s%s.%s";

    public LoggingX509ExtendedKeyManager(X509ExtendedKeyManager keyManager) {
        super(keyManager);
    }

    @Override
    public String chooseClientAlias(String[] keyType, Principal[] issuers, Socket socket) {
        this.logAttemptOfChoosingAlias(ServerOrClient.CLIENT, keyType, issuers, socket, null);
        String alias = super.chooseClientAlias(keyType, issuers, socket);
        this.logAliasIfPresent(ServerOrClient.CLIENT, alias, keyType, issuers, socket, null);
        return alias;
    }

    @Override
    public String chooseEngineClientAlias(String[] keyTypes, Principal[] issuers, SSLEngine sslEngine) {
        this.logAttemptOfChoosingAlias(ServerOrClient.CLIENT, keyTypes, issuers, null, sslEngine);
        String alias = super.chooseEngineClientAlias(keyTypes, issuers, sslEngine);
        this.logAliasIfPresent(ServerOrClient.CLIENT, alias, keyTypes, issuers, null, sslEngine);
        return alias;
    }

    @Override
    public String chooseServerAlias(String keyType, Principal[] issuers, Socket socket) {
        this.logAttemptOfChoosingAlias(ServerOrClient.SERVER, keyType, issuers, socket, null);
        String alias = super.chooseServerAlias(keyType, issuers, socket);
        this.logAliasIfPresent(ServerOrClient.SERVER, alias, keyType, issuers, socket, null);
        return alias;
    }

    @Override
    public String chooseEngineServerAlias(String keyType, Principal[] issuers, SSLEngine sslEngine) {
        this.logAttemptOfChoosingAlias(ServerOrClient.SERVER, new String[]{keyType}, issuers, null, sslEngine);
        String alias = super.chooseEngineServerAlias(keyType, issuers, sslEngine);
        this.logAliasIfPresent(ServerOrClient.SERVER, alias, keyType, issuers, null, sslEngine);
        return alias;
    }

    private void logAttemptOfChoosingAlias(ServerOrClient serverOrClient, String keyType, Principal[] issuers, Socket socket, SSLEngine sslEngine) {
        this.logAttemptOfChoosingAlias(serverOrClient, new String[]{keyType}, issuers, socket, sslEngine);
    }

    private void logAttemptOfChoosingAlias(ServerOrClient serverOrClient, String[] keyTypes, Principal[] issuers, Socket socket, SSLEngine sslEngine) {
        String combinedKeyTypes = Arrays.toString(keyTypes);
        String issuersLogMessage = LoggingX509ExtendedKeyManager.getIssuersLogMessage(issuers);
        String classNameLogMessage = LoggingX509ExtendedKeyManager.getClassNameLogMessage(socket, sslEngine);
        String logMessage = String.format(CHOOSE_ALIAS_LOG_MESSAGE_TEMPLATE, new Object[]{serverOrClient, combinedKeyTypes, classNameLogMessage, issuersLogMessage});
        LOGGER.debug(logMessage);
    }

    private void logAliasIfPresent(ServerOrClient serverOrClient, String alias, String keyType, Principal[] issuers, Socket socket, SSLEngine sslEngine) {
        this.logAliasIfPresent(serverOrClient, alias, new String[]{keyType}, issuers, socket, sslEngine);
    }

    private void logAliasIfPresent(ServerOrClient serverOrClient, String alias, String[] keyTypes, Principal[] issuers, Socket socket, SSLEngine sslEngine) {
        if (alias != null) {
            String combinedKeyTypes = Arrays.toString(keyTypes);
            String issuersLogMessage = LoggingX509ExtendedKeyManager.getIssuersLogMessage(issuers);
            String classNameLogMessage = LoggingX509ExtendedKeyManager.getClassNameLogMessage(socket, sslEngine);
            String logMessage = String.format(FOUND_ALIAS_LOG_MESSAGE_TEMPLATE, new Object[]{serverOrClient, alias, combinedKeyTypes, classNameLogMessage, issuersLogMessage});
            LOGGER.debug(logMessage);
        }
    }

    static Optional<String> getClassnameOfEitherOrOther(Socket socket, SSLEngine sslEngine) {
        if (socket != null) {
            return Optional.of(Socket.class.getSimpleName());
        }
        if (sslEngine != null) {
            return Optional.of(SSLEngine.class.getSimpleName());
        }
        return Optional.empty();
    }

    private static String getClassNameLogMessage(Socket socket, SSLEngine sslEngine) {
        return LoggingX509ExtendedKeyManager.getClassnameOfEitherOrOther(socket, sslEngine).map(className -> ", while also using the " + className + "").orElse("");
    }

    private static String getIssuersLogMessage(Principal[] issuers) {
        return Optional.ofNullable(issuers).filter(principals -> ((Principal[])principals).length > 0).map(Arrays::toString).map(combinedIssuers -> String.format(" See below for list of the issuers:%n%s", combinedIssuers)).orElse("");
    }

    @Override
    public PrivateKey getPrivateKey(String alias) {
        LOGGER.debug("Attempting to get the private key for the alias: {}", (Object)alias);
        PrivateKey privateKey = super.getPrivateKey(alias);
        if (privateKey != null) {
            String logMessage = String.format("Found a private key for the alias: %s", alias);
            LOGGER.debug(logMessage);
        }
        return privateKey;
    }

    @Override
    public X509Certificate[] getCertificateChain(String alias) {
        LOGGER.debug("Attempting to get the certificate chain for the alias: {}", (Object)alias);
        Object[] certificateChain = super.getCertificateChain(alias);
        if (certificateChain != null && certificateChain.length > 0) {
            String combinedCertificateChain = Arrays.toString(certificateChain);
            String logMessage = String.format("Found the certificate chain with a size of %d for the alias: %s. See below for the full chain:%n%s", certificateChain.length, alias, combinedCertificateChain);
            LOGGER.debug(logMessage);
        }
        return certificateChain;
    }

    @Override
    public String[] getClientAliases(String keyType, Principal[] issuers) {
        this.logAttemptOfChoosingAlias(ServerOrClient.CLIENT, keyType, issuers, null, null);
        String[] clientAliases = super.getClientAliases(keyType, issuers);
        this.logAliasIfPresent(ServerOrClient.CLIENT, clientAliases, keyType, issuers);
        return clientAliases;
    }

    @Override
    public String[] getServerAliases(String keyType, Principal[] issuers) {
        this.logAttemptOfChoosingAlias(ServerOrClient.SERVER, keyType, issuers, null, null);
        String[] serverAliases = super.getServerAliases(keyType, issuers);
        this.logAliasIfPresent(ServerOrClient.SERVER, serverAliases, keyType, issuers);
        return serverAliases;
    }

    private void logAliasIfPresent(ServerOrClient serverOrClient, String[] aliases, String keyType, Principal[] issuers) {
        if (aliases != null && aliases.length > 0) {
            this.logAliasIfPresent(serverOrClient, String.join((CharSequence)", ", aliases), new String[]{keyType}, issuers, null, null);
        }
    }

    private static enum ServerOrClient {
        SERVER,
        CLIENT;


        public String toString() {
            return this.name().toLowerCase();
        }
    }
}

