/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.concurrent;

import java.util.Collection;
import org.ldaptive.FilterTemplate;
import org.ldaptive.Request;
import org.ldaptive.SearchOperation;
import org.ldaptive.SearchRequest;
import org.ldaptive.SearchResponse;
import org.ldaptive.concurrent.AbstractOperationWorker;

public class SearchOperationWorker
extends AbstractOperationWorker<SearchOperation, SearchRequest, SearchResponse> {
    public SearchOperationWorker() {
        super(new SearchOperation());
    }

    public SearchOperationWorker(SearchOperation op) {
        super(op);
    }

    public Collection<SearchResponse> execute(String ... filters) {
        FilterTemplate[] templates = new FilterTemplate[filters.length];
        for (int i = 0; i < filters.length; ++i) {
            templates[i] = new FilterTemplate(filters[i]);
        }
        return this.execute(templates, (String[])null);
    }

    public Collection<SearchResponse> execute(FilterTemplate ... templates) {
        return this.execute(templates, (String[])null);
    }

    public Collection<SearchResponse> execute(String[] filters, String ... attrs) {
        FilterTemplate[] templates = new FilterTemplate[filters.length];
        for (int i = 0; i < filters.length; ++i) {
            templates[i] = new FilterTemplate(filters[i]);
        }
        return this.execute(templates, attrs);
    }

    public Collection<SearchResponse> execute(FilterTemplate[] templates, String ... attrs) {
        Request[] requests = new SearchRequest[templates.length];
        for (int i = 0; i < templates.length; ++i) {
            requests[i] = SearchRequest.copy(((SearchOperation)this.getOperation()).getRequest());
            if (templates[i] != null) {
                ((SearchRequest)requests[i]).setFilter(templates[i]);
            }
            if (attrs == null) continue;
            ((SearchRequest)requests[i]).setReturnAttributes(attrs);
        }
        return this.execute(requests);
    }
}

