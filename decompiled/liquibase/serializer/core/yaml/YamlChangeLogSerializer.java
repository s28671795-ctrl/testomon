/*
 * Decompiled with CFR 0.152.
 */
package liquibase.serializer.core.yaml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import liquibase.GlobalConfiguration;
import liquibase.changelog.ChangeLogChild;
import liquibase.changelog.ChangeSet;
import liquibase.serializer.ChangeLogSerializer;
import liquibase.serializer.LiquibaseSerializable;
import liquibase.serializer.core.yaml.YamlSerializer;

public class YamlChangeLogSerializer
extends YamlSerializer
implements ChangeLogSerializer {
    @Override
    protected Comparator<String> getComparator(LiquibaseSerializable object) {
        if (object instanceof ChangeSet) {
            return new ChangeSetComparator();
        }
        return super.getComparator(object);
    }

    @Override
    public <T extends ChangeLogChild> void write(List<T> children, OutputStream out) throws IOException {
        ArrayList<Object> maps = new ArrayList<Object>();
        for (ChangeLogChild changeSet : children) {
            maps.add(this.toMap(changeSet));
        }
        HashMap<String, ArrayList<Object>> containerMap = new HashMap<String, ArrayList<Object>>();
        containerMap.put("databaseChangeLog", maps);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue()));
        writer.write(this.yaml.dumpAsMap(containerMap));
        writer.write("\n");
        writer.flush();
    }

    @Override
    public void append(ChangeSet changeSet, File changeLogFile) throws IOException {
    }

    @Override
    public int getPriority() {
        return 1;
    }

    private static class ChangeSetComparator
    implements Comparator<String> {
        private static final Map<String, Integer> order = new HashMap<String, Integer>();

        private ChangeSetComparator() {
        }

        @Override
        public int compare(String o1, String o2) {
            int orderCompare;
            Integer o2Order;
            Integer o1Order = order.get(o1);
            if (o1Order == null) {
                o1Order = 10;
            }
            if ((o2Order = order.get(o2)) == null) {
                o2Order = 10;
            }
            if ((orderCompare = o1Order.compareTo(o2Order)) == 0) {
                return o1.compareTo(o2);
            }
            return orderCompare;
        }

        static {
            order.put("id", 1);
            order.put("author", 2);
            order.put("changes", Integer.MAX_VALUE);
        }
    }
}

