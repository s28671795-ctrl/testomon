/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.jooq.CommonTableExpression;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.ResultQuery;
import org.jooq.WindowDefinition;
import org.jooq.WindowSpecification;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.DerivedColumnListImpl;
import org.jooq.impl.QOM;
import org.jooq.impl.QualifiedName;
import org.jooq.impl.SimpleQueryPart;
import org.jooq.impl.Tools;
import org.jooq.impl.UnqualifiedName;
import org.jooq.impl.WindowDefinitionImpl;

abstract class AbstractName
extends AbstractQueryPart
implements Name,
SimpleQueryPart,
QOM.UEmpty {
    static final UnqualifiedName NO_NAME = new UnqualifiedName("");

    AbstractName() {
    }

    @Override
    public final Name append(String name) {
        return this.append(new UnqualifiedName(name));
    }

    @Override
    public final Name append(Name name) {
        if (this.empty()) {
            return name;
        }
        if (name.empty()) {
            return this;
        }
        Name[] p1 = this.parts();
        Name[] p2 = name.parts();
        Name[] array = new Name[p1.length + p2.length];
        System.arraycopy(p1, 0, array, 0, p1.length);
        System.arraycopy(p2, 0, array, p1.length, p2.length);
        return new QualifiedName(array);
    }

    @Override
    public final WindowDefinition as() {
        return new WindowDefinitionImpl(this, null);
    }

    @Override
    public final WindowDefinition as(WindowSpecification window) {
        return new WindowDefinitionImpl(this, window);
    }

    @Override
    public final <R extends Record> CommonTableExpression<R> as(ResultQuery<R> query) {
        return this.fields(new String[0]).as((ResultQuery)query);
    }

    @Override
    public final <R extends Record> CommonTableExpression<R> asMaterialized(ResultQuery<R> query) {
        return this.fields(new String[0]).asMaterialized((ResultQuery)query);
    }

    @Override
    public final <R extends Record> CommonTableExpression<R> asNotMaterialized(ResultQuery<R> query) {
        return this.fields(new String[0]).asNotMaterialized((ResultQuery)query);
    }

    @Override
    public final DerivedColumnListImpl fields(String ... fieldNames) {
        return this.fields(Tools.names(fieldNames));
    }

    @Override
    public final DerivedColumnListImpl fields(Name ... fieldNames) {
        return new DerivedColumnListImpl(this.unqualifiedName(), fieldNames);
    }

    @Override
    public final DerivedColumnListImpl fields(Function<? super Field<?>, ? extends String> fieldNameFunction) {
        return this.fields((T f, U i) -> (String)fieldNameFunction.apply((Field<?>)f));
    }

    @Override
    public final DerivedColumnListImpl fields(BiFunction<? super Field<?>, ? super Integer, ? extends String> fieldNameFunction) {
        return new DerivedColumnListImpl(this.first(), fieldNameFunction);
    }

    @Override
    public final DerivedColumnListImpl fields(String fieldName1) {
        return this.fields(new String[]{fieldName1});
    }

    @Override
    public final DerivedColumnListImpl fields(String fieldName1, String fieldName2) {
        return this.fields(new String[]{fieldName1, fieldName2});
    }

    @Override
    public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3) {
        return this.fields(new String[]{fieldName1, fieldName2, fieldName3});
    }

    @Override
    public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4) {
        return this.fields(new String[]{fieldName1, fieldName2, fieldName3, fieldName4});
    }

    @Override
    public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5) {
        return this.fields(new String[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5});
    }

    @Override
    public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5, String fieldName6) {
        return this.fields(new String[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6});
    }

    @Override
    public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5, String fieldName6, String fieldName7) {
        return this.fields(new String[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7});
    }

    @Override
    public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5, String fieldName6, String fieldName7, String fieldName8) {
        return this.fields(new String[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8});
    }

    @Override
    public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5, String fieldName6, String fieldName7, String fieldName8, String fieldName9) {
        return this.fields(new String[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9});
    }

    @Override
    public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5, String fieldName6, String fieldName7, String fieldName8, String fieldName9, String fieldName10) {
        return this.fields(new String[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10});
    }

    @Override
    public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5, String fieldName6, String fieldName7, String fieldName8, String fieldName9, String fieldName10, String fieldName11) {
        return this.fields(new String[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11});
    }

    @Override
    public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5, String fieldName6, String fieldName7, String fieldName8, String fieldName9, String fieldName10, String fieldName11, String fieldName12) {
        return this.fields(new String[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11, fieldName12});
    }

    @Override
    public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5, String fieldName6, String fieldName7, String fieldName8, String fieldName9, String fieldName10, String fieldName11, String fieldName12, String fieldName13) {
        return this.fields(new String[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11, fieldName12, fieldName13});
    }

    @Override
    public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5, String fieldName6, String fieldName7, String fieldName8, String fieldName9, String fieldName10, String fieldName11, String fieldName12, String fieldName13, String fieldName14) {
        return this.fields(new String[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11, fieldName12, fieldName13, fieldName14});
    }

    @Override
    public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5, String fieldName6, String fieldName7, String fieldName8, String fieldName9, String fieldName10, String fieldName11, String fieldName12, String fieldName13, String fieldName14, String fieldName15) {
        return this.fields(new String[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11, fieldName12, fieldName13, fieldName14, fieldName15});
    }

    @Override
    public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5, String fieldName6, String fieldName7, String fieldName8, String fieldName9, String fieldName10, String fieldName11, String fieldName12, String fieldName13, String fieldName14, String fieldName15, String fieldName16) {
        return this.fields(new String[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11, fieldName12, fieldName13, fieldName14, fieldName15, fieldName16});
    }

    @Override
    public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5, String fieldName6, String fieldName7, String fieldName8, String fieldName9, String fieldName10, String fieldName11, String fieldName12, String fieldName13, String fieldName14, String fieldName15, String fieldName16, String fieldName17) {
        return this.fields(new String[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11, fieldName12, fieldName13, fieldName14, fieldName15, fieldName16, fieldName17});
    }

    @Override
    public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5, String fieldName6, String fieldName7, String fieldName8, String fieldName9, String fieldName10, String fieldName11, String fieldName12, String fieldName13, String fieldName14, String fieldName15, String fieldName16, String fieldName17, String fieldName18) {
        return this.fields(new String[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11, fieldName12, fieldName13, fieldName14, fieldName15, fieldName16, fieldName17, fieldName18});
    }

    @Override
    public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5, String fieldName6, String fieldName7, String fieldName8, String fieldName9, String fieldName10, String fieldName11, String fieldName12, String fieldName13, String fieldName14, String fieldName15, String fieldName16, String fieldName17, String fieldName18, String fieldName19) {
        return this.fields(new String[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11, fieldName12, fieldName13, fieldName14, fieldName15, fieldName16, fieldName17, fieldName18, fieldName19});
    }

    @Override
    public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5, String fieldName6, String fieldName7, String fieldName8, String fieldName9, String fieldName10, String fieldName11, String fieldName12, String fieldName13, String fieldName14, String fieldName15, String fieldName16, String fieldName17, String fieldName18, String fieldName19, String fieldName20) {
        return this.fields(new String[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11, fieldName12, fieldName13, fieldName14, fieldName15, fieldName16, fieldName17, fieldName18, fieldName19, fieldName20});
    }

    @Override
    public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5, String fieldName6, String fieldName7, String fieldName8, String fieldName9, String fieldName10, String fieldName11, String fieldName12, String fieldName13, String fieldName14, String fieldName15, String fieldName16, String fieldName17, String fieldName18, String fieldName19, String fieldName20, String fieldName21) {
        return this.fields(new String[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11, fieldName12, fieldName13, fieldName14, fieldName15, fieldName16, fieldName17, fieldName18, fieldName19, fieldName20, fieldName21});
    }

    @Override
    public final DerivedColumnListImpl fields(String fieldName1, String fieldName2, String fieldName3, String fieldName4, String fieldName5, String fieldName6, String fieldName7, String fieldName8, String fieldName9, String fieldName10, String fieldName11, String fieldName12, String fieldName13, String fieldName14, String fieldName15, String fieldName16, String fieldName17, String fieldName18, String fieldName19, String fieldName20, String fieldName21, String fieldName22) {
        return this.fields(new String[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11, fieldName12, fieldName13, fieldName14, fieldName15, fieldName16, fieldName17, fieldName18, fieldName19, fieldName20, fieldName21, fieldName22});
    }

    @Override
    public final DerivedColumnListImpl fields(Name fieldName1) {
        return this.fields(new Name[]{fieldName1});
    }

    @Override
    public final DerivedColumnListImpl fields(Name fieldName1, Name fieldName2) {
        return this.fields(new Name[]{fieldName1, fieldName2});
    }

    @Override
    public final DerivedColumnListImpl fields(Name fieldName1, Name fieldName2, Name fieldName3) {
        return this.fields(new Name[]{fieldName1, fieldName2, fieldName3});
    }

    @Override
    public final DerivedColumnListImpl fields(Name fieldName1, Name fieldName2, Name fieldName3, Name fieldName4) {
        return this.fields(new Name[]{fieldName1, fieldName2, fieldName3, fieldName4});
    }

    @Override
    public final DerivedColumnListImpl fields(Name fieldName1, Name fieldName2, Name fieldName3, Name fieldName4, Name fieldName5) {
        return this.fields(new Name[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5});
    }

    @Override
    public final DerivedColumnListImpl fields(Name fieldName1, Name fieldName2, Name fieldName3, Name fieldName4, Name fieldName5, Name fieldName6) {
        return this.fields(new Name[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6});
    }

    @Override
    public final DerivedColumnListImpl fields(Name fieldName1, Name fieldName2, Name fieldName3, Name fieldName4, Name fieldName5, Name fieldName6, Name fieldName7) {
        return this.fields(new Name[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7});
    }

    @Override
    public final DerivedColumnListImpl fields(Name fieldName1, Name fieldName2, Name fieldName3, Name fieldName4, Name fieldName5, Name fieldName6, Name fieldName7, Name fieldName8) {
        return this.fields(new Name[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8});
    }

    @Override
    public final DerivedColumnListImpl fields(Name fieldName1, Name fieldName2, Name fieldName3, Name fieldName4, Name fieldName5, Name fieldName6, Name fieldName7, Name fieldName8, Name fieldName9) {
        return this.fields(new Name[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9});
    }

    @Override
    public final DerivedColumnListImpl fields(Name fieldName1, Name fieldName2, Name fieldName3, Name fieldName4, Name fieldName5, Name fieldName6, Name fieldName7, Name fieldName8, Name fieldName9, Name fieldName10) {
        return this.fields(new Name[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10});
    }

    @Override
    public final DerivedColumnListImpl fields(Name fieldName1, Name fieldName2, Name fieldName3, Name fieldName4, Name fieldName5, Name fieldName6, Name fieldName7, Name fieldName8, Name fieldName9, Name fieldName10, Name fieldName11) {
        return this.fields(new Name[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11});
    }

    @Override
    public final DerivedColumnListImpl fields(Name fieldName1, Name fieldName2, Name fieldName3, Name fieldName4, Name fieldName5, Name fieldName6, Name fieldName7, Name fieldName8, Name fieldName9, Name fieldName10, Name fieldName11, Name fieldName12) {
        return this.fields(new Name[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11, fieldName12});
    }

    @Override
    public final DerivedColumnListImpl fields(Name fieldName1, Name fieldName2, Name fieldName3, Name fieldName4, Name fieldName5, Name fieldName6, Name fieldName7, Name fieldName8, Name fieldName9, Name fieldName10, Name fieldName11, Name fieldName12, Name fieldName13) {
        return this.fields(new Name[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11, fieldName12, fieldName13});
    }

    @Override
    public final DerivedColumnListImpl fields(Name fieldName1, Name fieldName2, Name fieldName3, Name fieldName4, Name fieldName5, Name fieldName6, Name fieldName7, Name fieldName8, Name fieldName9, Name fieldName10, Name fieldName11, Name fieldName12, Name fieldName13, Name fieldName14) {
        return this.fields(new Name[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11, fieldName12, fieldName13, fieldName14});
    }

    @Override
    public final DerivedColumnListImpl fields(Name fieldName1, Name fieldName2, Name fieldName3, Name fieldName4, Name fieldName5, Name fieldName6, Name fieldName7, Name fieldName8, Name fieldName9, Name fieldName10, Name fieldName11, Name fieldName12, Name fieldName13, Name fieldName14, Name fieldName15) {
        return this.fields(new Name[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11, fieldName12, fieldName13, fieldName14, fieldName15});
    }

    @Override
    public final DerivedColumnListImpl fields(Name fieldName1, Name fieldName2, Name fieldName3, Name fieldName4, Name fieldName5, Name fieldName6, Name fieldName7, Name fieldName8, Name fieldName9, Name fieldName10, Name fieldName11, Name fieldName12, Name fieldName13, Name fieldName14, Name fieldName15, Name fieldName16) {
        return this.fields(new Name[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11, fieldName12, fieldName13, fieldName14, fieldName15, fieldName16});
    }

    @Override
    public final DerivedColumnListImpl fields(Name fieldName1, Name fieldName2, Name fieldName3, Name fieldName4, Name fieldName5, Name fieldName6, Name fieldName7, Name fieldName8, Name fieldName9, Name fieldName10, Name fieldName11, Name fieldName12, Name fieldName13, Name fieldName14, Name fieldName15, Name fieldName16, Name fieldName17) {
        return this.fields(new Name[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11, fieldName12, fieldName13, fieldName14, fieldName15, fieldName16, fieldName17});
    }

    @Override
    public final DerivedColumnListImpl fields(Name fieldName1, Name fieldName2, Name fieldName3, Name fieldName4, Name fieldName5, Name fieldName6, Name fieldName7, Name fieldName8, Name fieldName9, Name fieldName10, Name fieldName11, Name fieldName12, Name fieldName13, Name fieldName14, Name fieldName15, Name fieldName16, Name fieldName17, Name fieldName18) {
        return this.fields(new Name[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11, fieldName12, fieldName13, fieldName14, fieldName15, fieldName16, fieldName17, fieldName18});
    }

    @Override
    public final DerivedColumnListImpl fields(Name fieldName1, Name fieldName2, Name fieldName3, Name fieldName4, Name fieldName5, Name fieldName6, Name fieldName7, Name fieldName8, Name fieldName9, Name fieldName10, Name fieldName11, Name fieldName12, Name fieldName13, Name fieldName14, Name fieldName15, Name fieldName16, Name fieldName17, Name fieldName18, Name fieldName19) {
        return this.fields(new Name[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11, fieldName12, fieldName13, fieldName14, fieldName15, fieldName16, fieldName17, fieldName18, fieldName19});
    }

    @Override
    public final DerivedColumnListImpl fields(Name fieldName1, Name fieldName2, Name fieldName3, Name fieldName4, Name fieldName5, Name fieldName6, Name fieldName7, Name fieldName8, Name fieldName9, Name fieldName10, Name fieldName11, Name fieldName12, Name fieldName13, Name fieldName14, Name fieldName15, Name fieldName16, Name fieldName17, Name fieldName18, Name fieldName19, Name fieldName20) {
        return this.fields(new Name[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11, fieldName12, fieldName13, fieldName14, fieldName15, fieldName16, fieldName17, fieldName18, fieldName19, fieldName20});
    }

    @Override
    public final DerivedColumnListImpl fields(Name fieldName1, Name fieldName2, Name fieldName3, Name fieldName4, Name fieldName5, Name fieldName6, Name fieldName7, Name fieldName8, Name fieldName9, Name fieldName10, Name fieldName11, Name fieldName12, Name fieldName13, Name fieldName14, Name fieldName15, Name fieldName16, Name fieldName17, Name fieldName18, Name fieldName19, Name fieldName20, Name fieldName21) {
        return this.fields(new Name[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11, fieldName12, fieldName13, fieldName14, fieldName15, fieldName16, fieldName17, fieldName18, fieldName19, fieldName20, fieldName21});
    }

    @Override
    public final DerivedColumnListImpl fields(Name fieldName1, Name fieldName2, Name fieldName3, Name fieldName4, Name fieldName5, Name fieldName6, Name fieldName7, Name fieldName8, Name fieldName9, Name fieldName10, Name fieldName11, Name fieldName12, Name fieldName13, Name fieldName14, Name fieldName15, Name fieldName16, Name fieldName17, Name fieldName18, Name fieldName19, Name fieldName20, Name fieldName21, Name fieldName22) {
        return this.fields(new Name[]{fieldName1, fieldName2, fieldName3, fieldName4, fieldName5, fieldName6, fieldName7, fieldName8, fieldName9, fieldName10, fieldName11, fieldName12, fieldName13, fieldName14, fieldName15, fieldName16, fieldName17, fieldName18, fieldName19, fieldName20, fieldName21, fieldName22});
    }

    @Override
    public abstract int hashCode();

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that instanceof AbstractName) {
            AbstractName n = (AbstractName)that;
            if (this.qualified() != n.qualified()) {
                return false;
            }
            return Arrays.equals(this.getName(), n.getName());
        }
        return super.equals(that);
    }

    @Override
    public final boolean equalsIgnoreCase(Name that) {
        String[] thatName;
        if (this == that) {
            return true;
        }
        String[] thisName = this.getName();
        if (thisName.length != (thatName = that.getName()).length) {
            return false;
        }
        for (int i = 0; i < thisName.length; ++i) {
            if (thisName[i] == null && thatName[i] == null) continue;
            if (thisName[i] == null || thatName[i] == null) {
                return false;
            }
            if (thisName[i].equalsIgnoreCase(thatName[i])) continue;
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Name o) {
        return this.unquotedName().toString().compareTo(o.unquotedName().toString());
    }
}

