/*
 * Decompiled with CFR 0.152.
 */
package liquibase.serializer.core.yaml;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import liquibase.GlobalConfiguration;
import liquibase.diff.compare.DatabaseObjectCollectionComparator;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.serializer.LiquibaseSerializable;
import liquibase.serializer.SnapshotSerializer;
import liquibase.serializer.core.yaml.YamlSerializer;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.statement.DatabaseFunction;
import liquibase.statement.SequenceCurrentValueFunction;
import liquibase.statement.SequenceNextValueFunction;
import liquibase.structure.DatabaseObject;
import liquibase.structure.DatabaseObjectCollection;
import liquibase.structure.core.Column;
import liquibase.util.ISODateFormat;
import liquibase.util.StringUtil;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;

public class YamlSnapshotSerializer
extends YamlSerializer
implements SnapshotSerializer {
    private boolean alreadySerializingObject;

    @Override
    public void write(DatabaseSnapshot snapshot, OutputStream out) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue()));
        writer.write(this.serialize(snapshot, true));
    }

    @Override
    protected Object toMap(LiquibaseSerializable object) {
        if (object instanceof DatabaseObject) {
            if (object instanceof Column && ((Column)object).isForIndex()) {
                return super.toMap(object);
            }
            if (this.alreadySerializingObject) {
                String snapshotId = ((DatabaseObject)object).getSnapshotId();
                if (snapshotId == null) {
                    String name = ((DatabaseObject)object).getName();
                    Class<Object> table = ((DatabaseObject)object).getAttribute("table", Object.class);
                    if (table == null) {
                        table = ((DatabaseObject)object).getAttribute("relation", Object.class);
                    }
                    if (table != null) {
                        name = table + "." + name;
                    }
                    if (((DatabaseObject)object).getSchema() != null) {
                        name = ((DatabaseObject)object).getSchema().toString() + "." + name;
                    }
                    throw new UnexpectedLiquibaseException("Found a null snapshotId for " + StringUtil.lowerCaseFirst(object.getClass().getSimpleName()) + " " + name);
                }
                return ((DatabaseObject)object).getClass().getName() + "#" + snapshotId;
            }
            this.alreadySerializingObject = true;
            Object map = super.toMap(object);
            this.alreadySerializingObject = false;
            return map;
        }
        if (object instanceof DatabaseObjectCollection) {
            TreeMap returnMap = new TreeMap();
            for (Map.Entry<Class<? extends DatabaseObject>, Set<? extends DatabaseObject>> entry : ((DatabaseObjectCollection)object).toMap().entrySet()) {
                ArrayList<DatabaseObject> value = new ArrayList<DatabaseObject>((Collection)entry.getValue());
                value.sort(new DatabaseObjectCollectionComparator());
                returnMap.put(entry.getKey().getName(), value);
            }
            return returnMap;
        }
        return super.toMap(object);
    }

    @Override
    protected YamlSerializer.LiquibaseRepresenter getLiquibaseRepresenter(DumperOptions options) {
        return new SnapshotLiquibaseRepresenter(options);
    }

    @Override
    public int getPriority() {
        return 1;
    }

    public static class SnapshotLiquibaseRepresenter
    extends YamlSerializer.LiquibaseRepresenter {
        public SnapshotLiquibaseRepresenter(DumperOptions options) {
            super(options);
        }

        @Override
        protected void init() {
            this.multiRepresenters.put(DatabaseFunction.class, new TypeStoringAsStringRepresenter());
            this.multiRepresenters.put(SequenceNextValueFunction.class, new TypeStoringAsStringRepresenter());
            this.multiRepresenters.put(SequenceCurrentValueFunction.class, new TypeStoringAsStringRepresenter());
            this.multiRepresenters.put(java.util.Date.class, new TypeStoringAsStringRepresenter());
            this.multiRepresenters.put(Date.class, new TypeStoringAsStringRepresenter());
            this.multiRepresenters.put(Integer.class, new TypeStoringAsStringRepresenter());
            this.multiRepresenters.put(BigInteger.class, new TypeStoringAsStringRepresenter());
            this.multiRepresenters.put(Number.class, new TypeStoringAsStringRepresenter());
            this.multiRepresenters.put(Enum.class, new TypeStoringAsStringRepresenter());
        }

        private class TypeStoringAsStringRepresenter
        implements Represent {
            private TypeStoringAsStringRepresenter() {
            }

            @Override
            public Node representData(Object data) {
                String value = data instanceof java.util.Date ? new ISODateFormat().format((java.util.Date)data) : (data instanceof Enum ? ((Enum)data).name() : data.toString());
                return SnapshotLiquibaseRepresenter.this.representScalar(Tag.STR, value + "!{" + data.getClass().getName() + "}");
            }
        }
    }
}

