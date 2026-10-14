/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.net;

import java.net.MalformedURLException;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.net.SimpleURLCanonicalizer;
import net.shibboleth.utilities.java.support.net.URIComparator;
import net.shibboleth.utilities.java.support.net.URIException;

public class BasicURLComparator
implements URIComparator {
    private boolean caseInsensitive;

    public boolean isCaseInsensitive() {
        return this.caseInsensitive;
    }

    public void setCaseInsensitive(boolean flag) {
        this.caseInsensitive = flag;
    }

    @Override
    public boolean compare(@Nullable String uri1, @Nullable String uri2) throws URIException {
        if (uri1 == null) {
            return uri2 == null;
        }
        if (uri2 == null) {
            return uri1 == null;
        }
        String uri1Canon = null;
        try {
            uri1Canon = SimpleURLCanonicalizer.canonicalize(uri1);
        }
        catch (MalformedURLException e) {
            throw new URIException("URI was invalid: " + uri1Canon);
        }
        String uri2Canon = null;
        try {
            uri2Canon = SimpleURLCanonicalizer.canonicalize(uri2);
        }
        catch (MalformedURLException e) {
            throw new URIException("URI was invalid: " + uri2Canon);
        }
        if (this.isCaseInsensitive()) {
            return uri1Canon.equalsIgnoreCase(uri2Canon);
        }
        return uri1Canon.equals(uri2Canon);
    }
}

