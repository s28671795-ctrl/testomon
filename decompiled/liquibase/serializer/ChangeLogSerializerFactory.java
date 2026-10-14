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
import liquibase.serializer.ChangeLogSerializer;
import liquibase.servicelocator.PrioritizedService;

public class ChangeLogSerializerFactory {
    private static ChangeLogSerializerFactory instance;
    private final Map<String, List<ChangeLogSerializer>> serializers = new ConcurrentHashMap<String, List<ChangeLogSerializer>>();

    public static synchronized void reset() {
        instance = new ChangeLogSerializerFactory();
    }

    public static synchronized ChangeLogSerializerFactory getInstance() {
        if (instance == null) {
            instance = new ChangeLogSerializerFactory();
        }
        return instance;
    }

    private ChangeLogSerializerFactory() {
        try {
            for (ChangeLogSerializer serializer : Scope.getCurrentScope().getServiceLocator().findInstances(ChangeLogSerializer.class)) {
                this.register(serializer);
            }
        }
        catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    public List<ChangeLogSerializer> getSerializers(String fileNameOrExtension) {
        List<ChangeLogSerializer> changeLogSerializers = this.serializers.get(fileNameOrExtension = fileNameOrExtension.replaceAll(".*\\.", ""));
        if (changeLogSerializers == null) {
            return Collections.emptyList();
        }
        return changeLogSerializers;
    }

    public ChangeLogSerializer getSerializer(String fileNameOrExtension) {
        List<ChangeLogSerializer> changeLogSerializers = this.getSerializers(fileNameOrExtension);
        if (changeLogSerializers.isEmpty()) {
            throw new RuntimeException("No serializers associated with the filename or extension '" + fileNameOrExtension + "'");
        }
        return changeLogSerializers.get(0);
    }

    public void register(ChangeLogSerializer changeLogSerializer) {
        for (String extension : changeLogSerializer.getValidFileExtensions()) {
            List changeLogSerializers = this.serializers.computeIfAbsent(extension, k -> new ArrayList());
            changeLogSerializers.add(changeLogSerializer);
            changeLogSerializers.sort(PrioritizedService.COMPARATOR);
        }
    }

    public void unregister(ChangeLogSerializer changeLogSerializer) {
        Iterator<Map.Entry<String, List<ChangeLogSerializer>>> entryIterator = this.serializers.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<String, List<ChangeLogSerializer>> entry = entryIterator.next();
            List<ChangeLogSerializer> changeLogSerializers = entry.getValue();
            changeLogSerializers.removeIf(value -> value.equals(changeLogSerializer));
            if (!changeLogSerializers.isEmpty()) continue;
            entryIterator.remove();
        }
    }

    public Map<String, List<ChangeLogSerializer>> getSerializers() {
        return this.serializers;
    }
}

