/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.http.answers;

import com.incoresoft.plugin.commons.http.answers.Answer;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name="PaginationAnswer", description="Pagination answer class description")
public class PaginationAnswer<T>
extends Answer {
    protected List<T> data;
    @Schema(name="pages", description="Total pages", implementation=Integer.class, example="10")
    protected int pages;
    @Schema(name="total", description="Total items", implementation=Integer.class, example="100")
    protected long total;

    public PaginationAnswer(List<T> data, long total, int pageSize) {
        super("ok");
        this.data = data;
        this.total = total;
        this.pages = (int)Math.ceil((double)total / (double)pageSize);
    }

    public List<T> getData() {
        return this.data;
    }

    public int getPages() {
        return this.pages;
    }

    public long getTotal() {
        return this.total;
    }
}

