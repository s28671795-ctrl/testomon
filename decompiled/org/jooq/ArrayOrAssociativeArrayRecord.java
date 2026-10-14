/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.List;
import org.jooq.Attachable;
import org.jooq.Catalog;
import org.jooq.DataType;
import org.jooq.Formattable;
import org.jooq.Package;
import org.jooq.Pro;
import org.jooq.Schema;

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
@Pro
public interface ArrayOrAssociativeArrayRecord<E>
extends Attachable,
Formattable {
    public Catalog getCatalog();

    public Schema getSchema();

    public Package getPackage();

    public String getName();

    public DataType<E> getDataType();

    public DataType<?> getArrayType();

    public boolean isSQLUsable();

    public List<E> toList();
}

