/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.common;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.joda.time.DateTime;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.saml2.common.CacheableSAMLObject;
import org.opensaml.saml.saml2.common.TimeBoundSAMLObject;

public final class SAML2Support {
    private SAML2Support() {
    }

    public static boolean isValid(XMLObject xmlObject) {
        TimeBoundSAMLObject timeBoundObject;
        if (xmlObject instanceof TimeBoundSAMLObject && !(timeBoundObject = (TimeBoundSAMLObject)xmlObject).isValid()) {
            return false;
        }
        XMLObject parent = xmlObject.getParent();
        if (parent != null) {
            return SAML2Support.isValid(parent);
        }
        return true;
    }

    @Nullable
    public static DateTime getEarliestExpiration(@Nullable XMLObject xmlObject) {
        DateTime now = new DateTime();
        return SAML2Support.getEarliestExpiration(xmlObject, null, now);
    }

    @Nullable
    public static DateTime getEarliestExpiration(@Nullable XMLObject xmlObject, @Nullable DateTime candidateTime, @Nonnull DateTime now) {
        List<XMLObject> children;
        DateTime earliestExpiration = candidateTime;
        if (xmlObject instanceof CacheableSAMLObject) {
            earliestExpiration = SAML2Support.getEarliestExpirationFromCacheable((CacheableSAMLObject)xmlObject, earliestExpiration, now);
        }
        if (xmlObject instanceof TimeBoundSAMLObject) {
            earliestExpiration = SAML2Support.getEarliestExpirationFromTimeBound((TimeBoundSAMLObject)xmlObject, earliestExpiration);
        }
        if (xmlObject != null && (children = xmlObject.getOrderedChildren()) != null) {
            for (XMLObject child : xmlObject.getOrderedChildren()) {
                if (child == null) continue;
                earliestExpiration = SAML2Support.getEarliestExpiration(child, earliestExpiration, now);
            }
        }
        return earliestExpiration;
    }

    @Nullable
    public static DateTime getEarliestExpirationFromCacheable(@Nonnull CacheableSAMLObject cacheableObject, @Nullable DateTime candidateTime, @Nonnull DateTime now) {
        DateTime earliestExpiration = candidateTime;
        if (cacheableObject.getCacheDuration() != null && cacheableObject.getCacheDuration() > 0L) {
            DateTime elementExpirationTime = now.plus(cacheableObject.getCacheDuration());
            if (earliestExpiration == null) {
                earliestExpiration = elementExpirationTime;
            } else if (elementExpirationTime != null && elementExpirationTime.isBefore(earliestExpiration)) {
                earliestExpiration = elementExpirationTime;
            }
        }
        return earliestExpiration;
    }

    @Nullable
    public static DateTime getEarliestExpirationFromTimeBound(@Nonnull TimeBoundSAMLObject timeBoundObject, @Nullable DateTime candidateTime) {
        DateTime earliestExpiration = candidateTime;
        DateTime elementExpirationTime = timeBoundObject.getValidUntil();
        if (earliestExpiration == null) {
            earliestExpiration = elementExpirationTime;
        } else if (elementExpirationTime != null && elementExpirationTime.isBefore(earliestExpiration)) {
            earliestExpiration = elementExpirationTime;
        }
        return earliestExpiration;
    }
}

