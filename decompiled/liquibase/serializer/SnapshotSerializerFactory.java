/*
 * Decompiled with CFR 0.152.
 */
package liquibase.serializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import liquibase.Scope;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.serializer.SnapshotSerializer;
import liquibase.servicelocator.PrioritizedService;

public class SnapshotSerializerFactory {
    private static SnapshotSerializerFactory instance;
    private final Map<String, List<SnapshotSerializer>> serializers = new ConcurrentHashMap<String, List<SnapshotSerializer>>();

    public static synchronized void reset() {
        instance = new SnapshotSerializerFactory();
    }

    public static synchronized SnapshotSerializerFactory getInstance() {
        if (instance == null) {
            instance = new SnapshotSerializerFactory();
        }
        return instance;
    }

    private SnapshotSerializerFactory() {
        try {
            for (SnapshotSerializer serializer : Scope.getCurrentScope().getServiceLocator().findInstances(SnapshotSerializer.class)) {
                this.register(serializer);
            }
        }
        catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    public Map<String, List<SnapshotSerializer>> getSerializers() {
        return this.serializers;
    }

    public List<SnapshotSerializer> getSerializers(String fileNameOrExtension) {
        List<SnapshotSerializer> snapshotSerializers = this.serializers.get(fileNameOrExtension = fileNameOrExtension.replaceAll(".*\\.", ""));
        if (snapshotSerializers == null) {
            return Collections.emptyList();
        }
        return snapshotSerializers;
    }

    public SnapshotSerializer getSerializer(String fileNameOrExtension) {
        List<SnapshotSerializer> snapshotSerializers = this.getSerializers(fileNameOrExtension);
        if (snapshotSerializers.isEmpty()) {
            throw new RuntimeException("No serializers associated with the filename or extension '" + fileNameOrExtension + "'");
        }
        return snapshotSerializers.get(0);
    }

    public void register(SnapshotSerializer snapshotSerializer) {
        for (String extension : snapshotSerializer.getValidFileExtensions()) {
            List snapshotSerializers = this.serializers.computeIfAbsent(extension, k -> new ArrayList());
            snapshotSerializers.add(snapshotSerializer);
            snapshotSerializers.sort(PrioritizedService.COMPARATOR);
        }
    }

    public void unregister(SnapshotSerializer snapshotSerializer) {
        Iterator<Map.Entry<String, List<SnapshotSerializer>>> entryIterator = this.serializers.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<String, List<SnapshotSerializer>> entry = entryIterator.next();
            List<SnapshotSerializer> snapshotSerializers = entry.getValue();
            snapshotSerializers.removeIf(value -> value.equals(snapshotSerializer));
            if (!snapshotSerializers.isEmpty()) continue;
            entryIterator.remove();
        }
    }
}

