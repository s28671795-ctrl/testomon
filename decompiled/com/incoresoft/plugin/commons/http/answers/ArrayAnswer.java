/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.http.answers;

import com.incoresoft.plugin.commons.http.answers.PaginationAnswer;
import java.util.List;

public class ArrayAnswer<T>
extends PaginationAnswer<T> {
    public ArrayAnswer(List<T> data) {
        super(data, data.isEmpty() ? 0L : 1L, data.size());
    }
}

