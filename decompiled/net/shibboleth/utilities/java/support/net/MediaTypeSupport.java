/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.net;

import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.net.MediaType;
import java.util.HashSet;
import java.util.Set;
import net.shibboleth.utilities.java.support.net.StripMediaTypeParametersFunction;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

public final class MediaTypeSupport {
    private static final Function<MediaType, MediaType> STRIP_PARAMS = new StripMediaTypeParametersFunction();

    private MediaTypeSupport() {
    }

    public static boolean validateContentType(String contentType, Set<MediaType> validTypes, boolean noContentTypeIsValid, boolean isOneOfStrategy) {
        String contentTypeValue = StringSupport.trimOrNull(contentType);
        if (contentTypeValue != null) {
            if (isOneOfStrategy) {
                MediaType mediaType = MediaType.parse(contentTypeValue);
                for (MediaType validType : validTypes) {
                    if (!mediaType.is(validType)) continue;
                    return true;
                }
                return false;
            }
            MediaType mediaType = MediaType.parse(contentTypeValue).withoutParameters();
            HashSet<MediaType> validTypesWithoutParameters = new HashSet<MediaType>();
            validTypesWithoutParameters.addAll(Collections2.filter(Collections2.transform(validTypes, STRIP_PARAMS), Predicates.notNull()));
            return validTypesWithoutParameters.contains(mediaType);
        }
        return noContentTypeIsValid;
    }
}

