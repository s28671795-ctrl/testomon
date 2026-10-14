/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.util;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.LongFunction;
import java.util.function.Predicate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;
import nl.altindag.ssl.SSLFactory;
import nl.altindag.ssl.util.internal.CollectorsUtils;

public final class SSLSessionUtils {
    private static final LongFunction<ZonedDateTime> EPOCH_TIME_MAPPER = epochTime -> ZonedDateTime.ofInstant(Instant.ofEpochMilli(epochTime), ZoneOffset.UTC);

    private SSLSessionUtils() {
    }

    public static void invalidateCaches(SSLFactory sslFactory) {
        SSLSessionUtils.invalidateServerCaches(sslFactory);
        SSLSessionUtils.invalidateClientCaches(sslFactory);
    }

    public static void invalidateServerCaches(SSLFactory sslFactory) {
        SSLSessionUtils.invalidateServerCaches(sslFactory.getSslContext());
    }

    public static void invalidateClientCaches(SSLFactory sslFactory) {
        SSLSessionUtils.invalidateClientCaches(sslFactory.getSslContext());
    }

    public static void invalidateCaches(SSLContext sslContext) {
        SSLSessionUtils.invalidateServerCaches(sslContext);
        SSLSessionUtils.invalidateClientCaches(sslContext);
    }

    public static void invalidateServerCaches(SSLContext sslContext) {
        SSLSessionUtils.invalidateCaches(sslContext.getServerSessionContext());
    }

    public static void invalidateClientCaches(SSLContext sslContext) {
        SSLSessionUtils.invalidateCaches(sslContext.getClientSessionContext());
    }

    public static void invalidateCaches(SSLSessionContext sslSessionContext) {
        SSLSessionUtils.getSslSessions(sslSessionContext).forEach(SSLSession::invalidate);
    }

    public static void invalidateCachesBefore(SSLFactory sslFactory, ZonedDateTime upperBoundary) {
        SSLSessionUtils.invalidateCachesBefore(sslFactory.getSslContext(), upperBoundary);
    }

    public static void invalidateCachesBefore(SSLContext sslContext, ZonedDateTime upperBoundary) {
        SSLSessionUtils.invalidateCachesBefore(sslContext.getServerSessionContext(), upperBoundary);
        SSLSessionUtils.invalidateCachesBefore(sslContext.getClientSessionContext(), upperBoundary);
    }

    public static void invalidateCachesBefore(SSLSessionContext sslSessionContext, ZonedDateTime upperBoundary) {
        SSLSessionUtils.invalidateCachesWithTimeStamp(sslSessionContext, sslSessionCreationTime -> sslSessionCreationTime.isBefore(upperBoundary));
    }

    public static void invalidateCachesAfter(SSLFactory sslFactory, ZonedDateTime lowerBoundary) {
        SSLSessionUtils.invalidateCachesAfter(sslFactory.getSslContext(), lowerBoundary);
    }

    public static void invalidateCachesAfter(SSLContext sslContext, ZonedDateTime lowerBoundary) {
        SSLSessionUtils.invalidateCachesAfter(sslContext.getServerSessionContext(), lowerBoundary);
        SSLSessionUtils.invalidateCachesAfter(sslContext.getClientSessionContext(), lowerBoundary);
    }

    public static void invalidateCachesAfter(SSLSessionContext sslSessionContext, ZonedDateTime lowerBoundary) {
        SSLSessionUtils.invalidateCachesWithTimeStamp(sslSessionContext, sslSessionCreationTime -> sslSessionCreationTime.isAfter(lowerBoundary));
    }

    public static void invalidateCachesBetween(SSLFactory sslFactory, ZonedDateTime lowerBoundary, ZonedDateTime upperBoundary) {
        SSLSessionUtils.invalidateCachesBetween(sslFactory.getSslContext(), lowerBoundary, upperBoundary);
    }

    public static void invalidateCachesBetween(SSLContext sslContext, ZonedDateTime lowerBoundary, ZonedDateTime upperBoundary) {
        SSLSessionUtils.invalidateCachesBetween(sslContext.getServerSessionContext(), lowerBoundary, upperBoundary);
        SSLSessionUtils.invalidateCachesBetween(sslContext.getClientSessionContext(), lowerBoundary, upperBoundary);
    }

    public static void invalidateCachesBetween(SSLSessionContext sslSessionContext, ZonedDateTime lowerBoundary, ZonedDateTime upperBoundary) {
        Predicate<ZonedDateTime> isAfterLowerBoundary = sslSessionCreationTime -> sslSessionCreationTime.isAfter(lowerBoundary);
        Predicate<ZonedDateTime> isBeforeUpperBoundary = sslSessionCreationTime -> sslSessionCreationTime.isBefore(upperBoundary);
        SSLSessionUtils.invalidateCachesWithTimeStamp(sslSessionContext, isAfterLowerBoundary.and(isBeforeUpperBoundary));
    }

    private static void invalidateCachesWithTimeStamp(SSLSessionContext sslSessionContext, Predicate<ZonedDateTime> timeStampPredicate) {
        SSLSessionUtils.getSslSessions(sslSessionContext).stream().filter(sslSession -> timeStampPredicate.test(EPOCH_TIME_MAPPER.apply(sslSession.getCreationTime()))).forEach(SSLSession::invalidate);
    }

    public static void updateSessionTimeout(SSLFactory sslFactory, int timeoutInSeconds) {
        SSLSessionUtils.updateSessionTimeout(sslFactory.getSslContext(), timeoutInSeconds);
    }

    public static void updateSessionTimeout(SSLContext sslContext, int timeoutInSeconds) {
        SSLSessionUtils.validateSessionTimeout(timeoutInSeconds);
        sslContext.getClientSessionContext().setSessionTimeout(timeoutInSeconds);
        sslContext.getServerSessionContext().setSessionTimeout(timeoutInSeconds);
    }

    public static void updateSessionCacheSize(SSLFactory sslFactory, int cacheSizeInBytes) {
        SSLSessionUtils.updateSessionCacheSize(sslFactory.getSslContext(), cacheSizeInBytes);
    }

    public static void updateSessionCacheSize(SSLContext sslContext, int cacheSizeInBytes) {
        SSLSessionUtils.validateSessionCacheSize(cacheSizeInBytes);
        sslContext.getClientSessionContext().setSessionCacheSize(cacheSizeInBytes);
        sslContext.getServerSessionContext().setSessionCacheSize(cacheSizeInBytes);
    }

    public static void validateSessionTimeout(int timeoutInSeconds) {
        if (timeoutInSeconds < 0) {
            throw new IllegalArgumentException(String.format("Unsupported timeout has been provided. Timeout should be equal or greater than [%d], but received [%d]", 0, timeoutInSeconds));
        }
    }

    public static void validateSessionCacheSize(int cacheSizeInBytes) {
        if (cacheSizeInBytes < 0) {
            throw new IllegalArgumentException(String.format("Unsupported cache size has been provided. Cache size should be equal or greater than [%d], but received [%d]", 0, cacheSizeInBytes));
        }
    }

    public static List<SSLSession> getServerSslSessions(SSLFactory sslFactory) {
        return SSLSessionUtils.getServerSslSessions(sslFactory.getSslContext());
    }

    public static List<SSLSession> getServerSslSessions(SSLContext sslContext) {
        return SSLSessionUtils.getSslSessions(sslContext.getServerSessionContext());
    }

    public static List<SSLSession> getClientSslSessions(SSLFactory sslFactory) {
        return SSLSessionUtils.getClientSslSessions(sslFactory.getSslContext());
    }

    public static List<SSLSession> getClientSslSessions(SSLContext sslContext) {
        return SSLSessionUtils.getSslSessions(sslContext.getClientSessionContext());
    }

    public static List<SSLSession> getSslSessions(SSLSessionContext sslSessionContext) {
        return Collections.list(sslSessionContext.getIds()).stream().map(sslSessionContext::getSession).filter(Objects::nonNull).collect(CollectorsUtils.toUnmodifiableList());
    }
}

