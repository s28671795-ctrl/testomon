/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import org.ldaptive.AbandonRequest;
import org.ldaptive.AddRequest;
import org.ldaptive.AddResponse;
import org.ldaptive.BindRequest;
import org.ldaptive.BindResponse;
import org.ldaptive.CompareOperationHandle;
import org.ldaptive.CompareRequest;
import org.ldaptive.DeleteRequest;
import org.ldaptive.DeleteResponse;
import org.ldaptive.LdapException;
import org.ldaptive.LdapURL;
import org.ldaptive.ModifyDnRequest;
import org.ldaptive.ModifyDnResponse;
import org.ldaptive.ModifyRequest;
import org.ldaptive.ModifyResponse;
import org.ldaptive.OperationHandle;
import org.ldaptive.SearchOperationHandle;
import org.ldaptive.SearchRequest;
import org.ldaptive.control.RequestControl;
import org.ldaptive.extended.ExtendedOperationHandle;
import org.ldaptive.extended.ExtendedRequest;
import org.ldaptive.sasl.DefaultSaslClientRequest;
import org.ldaptive.sasl.SaslClientRequest;

public interface Connection
extends AutoCloseable {
    public void operation(AbandonRequest var1);

    public OperationHandle<AddRequest, AddResponse> operation(AddRequest var1);

    public OperationHandle<BindRequest, BindResponse> operation(BindRequest var1);

    public CompareOperationHandle operation(CompareRequest var1);

    public OperationHandle<DeleteRequest, DeleteResponse> operation(DeleteRequest var1);

    public ExtendedOperationHandle operation(ExtendedRequest var1);

    public OperationHandle<ModifyRequest, ModifyResponse> operation(ModifyRequest var1);

    public OperationHandle<ModifyDnRequest, ModifyDnResponse> operation(ModifyDnRequest var1);

    public SearchOperationHandle operation(SearchRequest var1);

    public BindResponse operation(SaslClientRequest var1) throws LdapException;

    public BindResponse operation(DefaultSaslClientRequest var1) throws LdapException;

    public LdapURL getLdapURL();

    public boolean isOpen();

    public void open() throws LdapException;

    @Override
    default public void close() {
        this.close(null);
    }

    public void close(RequestControl ... var1);
}

