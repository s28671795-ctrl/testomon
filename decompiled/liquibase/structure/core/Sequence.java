/*
 * Decompiled with CFR 0.152.
 */
package liquibase.structure.core;

import java.math.BigInteger;
import liquibase.structure.AbstractDatabaseObject;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Schema;

public class Sequence
extends AbstractDatabaseObject {
    public Sequence() {
    }

    public Sequence(String catalogName, String schemaName, String sequenceName) {
        this.setSchema(new Schema(catalogName, schemaName));
        this.setName(sequenceName);
    }

    @Override
    public DatabaseObject[] getContainingObjects() {
        return null;
    }

    @Override
    public String getName() {
        return (String)((Object)this.getAttribute("name", String.class));
    }

    @Override
    public Sequence setName(String name) {
        this.setAttribute("name", name);
        return this;
    }

    public BigInteger getStartValue() {
        return (BigInteger)((Object)this.getAttribute("startValue", BigInteger.class));
    }

    public Sequence setStartValue(BigInteger startValue) {
        this.setAttribute("startValue", startValue);
        return this;
    }

    public BigInteger getIncrementBy() {
        return (BigInteger)((Object)this.getAttribute("incrementBy", BigInteger.class));
    }

    public Sequence setIncrementBy(BigInteger incrementBy) {
        this.setAttribute("incrementBy", incrementBy);
        return this;
    }

    public boolean equals(Object o) {
        boolean schemasEqual;
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Sequence sequence2 = (Sequence)o;
        if (this.getSchema() != null && sequence2.getSchema() != null && !(schemasEqual = this.getSchema().equals(sequence2.getSchema()))) {
            return false;
        }
        return !(this.getName() == null ? sequence2.getName() != null : !this.getName().equalsIgnoreCase(sequence2.getName()));
    }

    public int hashCode() {
        return this.getName() != null ? this.getName().toUpperCase().hashCode() : 0;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public Schema getSchema() {
        return (Schema)((Object)this.getAttribute("schema", Schema.class));
    }

    public Sequence setSchema(Schema schema) {
        this.setAttribute("schema", schema);
        return this;
    }

    public Sequence setSchema(String catalog, String schema) {
        return this.setSchema(new Schema(catalog, schema));
    }

    public BigInteger getMinValue() {
        return (BigInteger)((Object)this.getAttribute("minValue", BigInteger.class));
    }

    public Sequence setMinValue(BigInteger minValue) {
        this.setAttribute("minValue", minValue);
        return this;
    }

    public BigInteger getMaxValue() {
        return (BigInteger)((Object)this.getAttribute("maxValue", BigInteger.class));
    }

    public Sequence setMaxValue(BigInteger maxValue) {
        this.setAttribute("maxValue", maxValue);
        return this;
    }

    public Boolean getWillCycle() {
        return (Boolean)((Object)this.getAttribute("willCycle", Boolean.class));
    }

    public Sequence setWillCycle(Boolean willCycle) {
        this.setAttribute("willCycle", willCycle);
        return this;
    }

    public Boolean getOrdered() {
        return (Boolean)((Object)this.getAttribute("ordered", Boolean.class));
    }

    public Sequence setOrdered(Boolean isOrdered) {
        this.setAttribute("ordered", isOrdered);
        return this;
    }

    public BigInteger getLastReturnedValue() {
        return (BigInteger)((Object)this.getAttribute("lastReturnedValue", BigInteger.class));
    }

    public Sequence setLastReturnedValue(BigInteger lastReturnedValue) {
        this.setAttribute("lastReturnedValue", lastReturnedValue);
        return this;
    }

    public BigInteger getCacheSize() {
        return (BigInteger)((Object)this.getAttribute("cacheSize", BigInteger.class));
    }

    public Sequence setCacheSize(BigInteger cacheSize) {
        this.setAttribute("cacheSize", cacheSize);
        return this;
    }

    public String getDataType() {
        return (String)((Object)this.getAttribute("dataType", String.class));
    }

    public Sequence setDataType(String dataType) {
        this.setAttribute("dataType", dataType);
        return this;
    }
}

