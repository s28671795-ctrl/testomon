/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.transform;

import java.util.Date;
import org.simpleframework.xml.transform.DateFactory;
import org.simpleframework.xml.transform.DateType;
import org.simpleframework.xml.transform.Transform;

class DateTransform<T extends Date>
implements Transform<T> {
    private final DateFactory<T> factory;

    public DateTransform(Class<T> type) throws Exception {
        this.factory = new DateFactory<T>(type);
    }

    @Override
    public synchronized T read(String text) throws Exception {
        Date date = DateType.getDate(text);
        Long time = date.getTime();
        return this.factory.getInstance(time);
    }

    @Override
    public synchronized String write(T date) throws Exception {
        return DateType.getText(date);
    }
}

