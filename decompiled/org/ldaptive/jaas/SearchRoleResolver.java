/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.jaas;

import java.util.Set;
import org.ldaptive.AbstractSearchOperationFactory;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.LdapException;
import org.ldaptive.SearchOperation;
import org.ldaptive.SearchRequest;
import org.ldaptive.SearchResponse;
import org.ldaptive.jaas.LdapRole;
import org.ldaptive.jaas.RoleResolver;

public class SearchRoleResolver
extends AbstractSearchOperationFactory
implements RoleResolver {
    public SearchRoleResolver() {
    }

    public SearchRoleResolver(ConnectionFactory cf) {
        this.setConnectionFactory(cf);
    }

    @Override
    public Set<LdapRole> search(SearchRequest request) throws LdapException {
        SearchOperation op = this.createSearchOperation();
        SearchResponse result = op.execute(request);
        if (!result.isSuccess()) {
            throw new LdapException("Unsuccessful role search: " + result);
        }
        return LdapRole.toRoles(result);
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "factory=" + this.getConnectionFactory() + "]";
    }
}

