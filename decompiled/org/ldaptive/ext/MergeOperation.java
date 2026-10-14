/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.ldaptive.AddRequest;
import org.ldaptive.AddResponse;
import org.ldaptive.AttributeModification;
import org.ldaptive.Connection;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.DeleteRequest;
import org.ldaptive.DeleteResponse;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapException;
import org.ldaptive.ModifyRequest;
import org.ldaptive.ModifyResponse;
import org.ldaptive.Result;
import org.ldaptive.ResultCode;
import org.ldaptive.SearchRequest;
import org.ldaptive.SearchResponse;
import org.ldaptive.ext.MergeRequest;
import org.ldaptive.handler.ResultPredicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MergeOperation {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ConnectionFactory connectionFactory;
    private ResultPredicate throwCondition;

    public MergeOperation() {
    }

    public MergeOperation(ConnectionFactory factory2) {
        this.connectionFactory = factory2;
    }

    public ConnectionFactory getConnectionFactory() {
        return this.connectionFactory;
    }

    public void setConnectionFactory(ConnectionFactory factory2) {
        this.connectionFactory = factory2;
    }

    public ResultPredicate getThrowCondition() {
        return this.throwCondition;
    }

    public void setThrowCondition(ResultPredicate function) {
        this.throwCondition = function;
    }

    public Result execute(MergeRequest request) throws LdapException {
        try (Connection conn = this.connectionFactory.getConnection();){
            Result result;
            conn.open();
            LdapEntry sourceEntry = request.getEntry();
            SearchResponse searchResult = conn.operation(SearchRequest.objectScopeSearchRequest(sourceEntry.getDn(), request.getSearchAttributes())).execute();
            if (searchResult.getResultCode() != ResultCode.SUCCESS && searchResult.getResultCode() != ResultCode.NO_SUCH_OBJECT) {
                throw new LdapException(searchResult.getResultCode(), String.format("Error searching for entry: %s, response did not return success or no_such_object: %s", sourceEntry, searchResult));
            }
            if (searchResult.entrySize() == 0) {
                if (request.getDeleteEntry()) {
                    this.logger.info("Target entry does not exist, no delete performed for request {}", (Object)request);
                    result = null;
                } else {
                    result = this.add(conn, request, sourceEntry);
                    if (this.throwCondition != null) {
                        this.throwCondition.testAndThrow(result);
                    }
                }
            } else if (request.getDeleteEntry()) {
                result = this.delete(conn, request, sourceEntry);
                if (this.throwCondition != null) {
                    this.throwCondition.testAndThrow(result);
                }
            } else {
                result = this.modify(conn, request, sourceEntry, searchResult.getEntry());
                if (this.throwCondition != null) {
                    this.throwCondition.testAndThrow(result);
                }
            }
            Result result2 = result;
            return result2;
        }
    }

    protected Result modify(Connection conn, MergeRequest request, LdapEntry source2, LdapEntry target) throws LdapException {
        AttributeModification[] modifications = LdapEntry.computeModifications(source2, target);
        if (modifications != null && modifications.length > 0) {
            List<String> l;
            ArrayList<AttributeModification> resultModifications = new ArrayList<AttributeModification>(modifications.length);
            String[] includeAttrs = request.getIncludeAttributes();
            String[] excludeAttrs = request.getExcludeAttributes();
            if (includeAttrs != null && includeAttrs.length > 0) {
                l = Arrays.asList(includeAttrs);
                for (AttributeModification am : modifications) {
                    if (!l.contains(am.getAttribute().getName())) continue;
                    resultModifications.add(am);
                }
            } else if (excludeAttrs != null && excludeAttrs.length > 0) {
                l = Arrays.asList(excludeAttrs);
                for (AttributeModification am : modifications) {
                    if (l.contains(am.getAttribute().getName())) continue;
                    resultModifications.add(am);
                }
            } else {
                Collections.addAll(resultModifications, modifications);
            }
            if (!resultModifications.isEmpty()) {
                this.logger.info("Modifying target entry {} with modifications {} from source entry {} for request {}", target, resultModifications, source2, request);
                ModifyResponse result = conn.operation((ModifyRequest)ModifyRequest.builder().dn(target.getDn()).modifications((AttributeModification[])resultModifications.toArray(AttributeModification[]::new)).build()).execute();
                this.logger.info("Modified target entry {} with modifications {} from source entry {} for request {}", target, resultModifications, source2, request);
                return result;
            }
        }
        this.logger.info("Target entry {} equals source entry {}, no modification performed for request {}", target, source2, request);
        return null;
    }

    protected Result add(Connection conn, MergeRequest request, LdapEntry entry) throws LdapException {
        AddResponse result = conn.operation((AddRequest)AddRequest.builder().dn(entry.getDn()).attributes(entry.getAttributes()).build()).execute();
        this.logger.info("Added entry {} for request {}", (Object)entry, (Object)request);
        return result;
    }

    protected Result delete(Connection conn, MergeRequest request, LdapEntry entry) throws LdapException {
        DeleteResponse result = conn.operation(new DeleteRequest(entry.getDn())).execute();
        this.logger.info("Delete entry {} for request {}", (Object)entry, (Object)request);
        return result;
    }
}

