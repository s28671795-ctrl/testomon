/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth;

import java.util.Arrays;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.ConnectionFactoryManager;
import org.ldaptive.LdapException;
import org.ldaptive.SearchOperation;
import org.ldaptive.SearchResponse;
import org.ldaptive.auth.AbstractSearchEntryResolver;
import org.ldaptive.auth.AuthenticationCriteria;
import org.ldaptive.auth.AuthenticationHandlerResponse;

public class SearchEntryResolver
extends AbstractSearchEntryResolver
implements ConnectionFactoryManager {
    public SearchEntryResolver() {
    }

    public SearchEntryResolver(ConnectionFactory cf) {
        this.setConnectionFactory(cf);
    }

    @Override
    public SearchResponse performLdapSearch(AuthenticationCriteria criteria, AuthenticationHandlerResponse response) throws LdapException {
        if (this.getConnectionFactory() == null) {
            return response.getConnection().operation(this.createSearchRequest(criteria)).execute();
        }
        SearchOperation op = this.createSearchOperation();
        return op.execute(this.createSearchRequest(criteria));
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "factory=" + this.getConnectionFactory() + ", " + "baseDn=" + this.getBaseDn() + ", " + "userFilter=" + this.getUserFilter() + ", " + "userFilterParameters=" + Arrays.toString(this.getUserFilterParameters()) + ", " + "allowMultipleEntries=" + this.getAllowMultipleEntries() + ", " + "subtreeSearch=" + this.getSubtreeSearch() + ", " + "derefAliases=" + (Object)((Object)this.getDerefAliases()) + ", " + "binaryAttributes=" + Arrays.toString(this.getBinaryAttributes()) + ", " + "entryHandlers=" + Arrays.toString(this.getEntryHandlers()) + "]";
    }
}

