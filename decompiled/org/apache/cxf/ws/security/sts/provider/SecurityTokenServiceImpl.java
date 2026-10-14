/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.sts.provider;

import java.security.Principal;
import java.util.Map;
import javax.annotation.Resource;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.soap.SOAPFaultException;
import org.apache.cxf.binding.soap.saaj.SAAJFactoryResolver;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.ws.security.sts.provider.SecurityTokenService;
import org.apache.cxf.ws.security.sts.provider.model.RequestSecurityTokenCollectionType;
import org.apache.cxf.ws.security.sts.provider.model.RequestSecurityTokenResponseCollectionType;
import org.apache.cxf.ws.security.sts.provider.model.RequestSecurityTokenResponseType;
import org.apache.cxf.ws.security.sts.provider.model.RequestSecurityTokenType;
import org.apache.cxf.ws.security.sts.provider.operation.CancelOperation;
import org.apache.cxf.ws.security.sts.provider.operation.IssueOperation;
import org.apache.cxf.ws.security.sts.provider.operation.IssueSingleOperation;
import org.apache.cxf.ws.security.sts.provider.operation.KeyExchangeTokenOperation;
import org.apache.cxf.ws.security.sts.provider.operation.RenewOperation;
import org.apache.cxf.ws.security.sts.provider.operation.RequestCollectionOperation;
import org.apache.cxf.ws.security.sts.provider.operation.ValidateOperation;

public class SecurityTokenServiceImpl
implements SecurityTokenService {
    private CancelOperation cancelOperation;
    private IssueOperation issueOperation;
    private IssueSingleOperation issueSingleOperation;
    private KeyExchangeTokenOperation keyExchangeTokenOperation;
    private RenewOperation renewOperation;
    private RequestCollectionOperation requestCollectionOperation;
    private ValidateOperation validateOperation;
    @Resource
    private WebServiceContext context;

    public void setCancelOperation(CancelOperation cancelOperation) {
        this.cancelOperation = cancelOperation;
    }

    public void setIssueOperation(IssueOperation issueOperation) {
        this.issueOperation = issueOperation;
    }

    public void setIssueSingleOperation(IssueSingleOperation issueSingleOperation) {
        this.issueSingleOperation = issueSingleOperation;
    }

    public void setKeyExchangeTokenOperation(KeyExchangeTokenOperation keyExchangeTokenOperation) {
        this.keyExchangeTokenOperation = keyExchangeTokenOperation;
    }

    public void setRenewOperation(RenewOperation renewOperation) {
        this.renewOperation = renewOperation;
    }

    public void setRequestCollectionOperation(RequestCollectionOperation requestCollectionOperation) {
        this.requestCollectionOperation = requestCollectionOperation;
    }

    public void setValidateOperation(ValidateOperation validateOperation) {
        this.validateOperation = validateOperation;
    }

    @Override
    public RequestSecurityTokenResponseType validate(RequestSecurityTokenType request) {
        if (this.validateOperation == null) {
            this.throwUnsupportedOperation("Validate");
        }
        return this.validateOperation.validate(request, this.getPrincipal(), this.getMessageContext());
    }

    @Override
    public RequestSecurityTokenResponseCollectionType requestCollection(RequestSecurityTokenCollectionType requestCollection) {
        if (this.requestCollectionOperation == null) {
            this.throwUnsupportedOperation("RequestCollection");
        }
        return this.requestCollectionOperation.requestCollection(requestCollection, this.getPrincipal(), this.getMessageContext());
    }

    @Override
    public RequestSecurityTokenResponseType keyExchangeToken(RequestSecurityTokenType request) {
        if (this.keyExchangeTokenOperation == null) {
            this.throwUnsupportedOperation("KeyExchangeToken");
        }
        return this.keyExchangeTokenOperation.keyExchangeToken(request, this.getPrincipal(), this.getMessageContext());
    }

    @Override
    public RequestSecurityTokenResponseCollectionType issue(RequestSecurityTokenType request) {
        if (this.issueOperation == null) {
            this.throwUnsupportedOperation("Issue");
        }
        return this.issueOperation.issue(request, this.getPrincipal(), this.getMessageContext());
    }

    @Override
    public RequestSecurityTokenResponseType issueSingle(RequestSecurityTokenType request) {
        if (this.issueSingleOperation == null) {
            this.throwUnsupportedOperation("IssueSingle");
        }
        return this.issueSingleOperation.issueSingle(request, this.getPrincipal(), this.getMessageContext());
    }

    @Override
    public RequestSecurityTokenResponseType cancel(RequestSecurityTokenType request) {
        if (this.cancelOperation == null) {
            this.throwUnsupportedOperation("Cancel");
        }
        return this.cancelOperation.cancel(request, this.getPrincipal(), this.getMessageContext());
    }

    @Override
    public RequestSecurityTokenResponseType renew(RequestSecurityTokenType request) {
        if (this.renewOperation == null) {
            this.throwUnsupportedOperation("Renew");
        }
        return this.renewOperation.renew(request, this.getPrincipal(), this.getMessageContext());
    }

    protected Principal getPrincipal() {
        return this.context.getUserPrincipal();
    }

    protected Map<String, Object> getMessageContext() {
        return this.context.getMessageContext();
    }

    private void throwUnsupportedOperation(String string) {
        try {
            SOAPFault fault = SAAJFactoryResolver.createSOAPFactory(null).createFault();
            fault.setFaultString("Unsupported operation " + string);
            throw new SOAPFaultException(fault);
        }
        catch (SOAPException e) {
            throw new Fault(e);
        }
    }
}

