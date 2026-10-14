/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.backup.database.data;

import com.incoresoft.backup.database.data.BackupIgnoredTables;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.jooq.ForeignKey;
import org.jooq.Schema;
import org.jooq.Table;

public class JooqTableOrderManager {
    public List<Table<?>> getTablesOrderedForDeleting(Collection<Table<?>> tables, Collection<ForeignKey<?, ?>> foreignKeys, BackupIgnoredTables ignoredTables) {
        List<Table<?>> orderedForInserting = this.getTablesOrderedForInserting(tables, foreignKeys, ignoredTables);
        ArrayList orderedForDeleting = new ArrayList(orderedForInserting);
        Collections.reverse(orderedForDeleting);
        return orderedForDeleting;
    }

    public List<Table<?>> getTablesOrderedForInserting(Schema schema, BackupIgnoredTables ignoredTables) {
        return this.getTablesOrderedForInserting(schema.getTables(), schema.getForeignKeys(), ignoredTables);
    }

    public List<Table<?>> getTablesOrderedForInserting(Collection<Table<?>> tables, Collection<ForeignKey<?, ?>> foreignKeys, BackupIgnoredTables ignoredTables) {
        Map<Table<?>, Set<Table<?>>> dependentTableRequiredTablesMap = this.getDependentTableRequiredTablesMap(tables, foreignKeys);
        this.removeDependentTablesOnIgnoredTables(dependentTableRequiredTablesMap, ignoredTables);
        ArrayList ordered = new ArrayList();
        while (!dependentTableRequiredTablesMap.isEmpty()) {
            int elementsBeforeIteration = dependentTableRequiredTablesMap.size();
            this.executeInsertTableOrderingIteration(dependentTableRequiredTablesMap, ordered);
            int elementsAfterIteration = dependentTableRequiredTablesMap.size();
            if (elementsBeforeIteration != elementsAfterIteration) continue;
            throw new RuntimeException("Circular dependency detected");
        }
        return ordered;
    }

    private void executeInsertTableOrderingIteration(Map<Table<?>, Set<Table<?>>> dependentTableRequiredTablesMap, List<Table<?>> ordered) {
        List<Table> independentTables = dependentTableRequiredTablesMap.entrySet().stream().filter(entry -> ((Set)entry.getValue()).isEmpty()).map(Map.Entry::getKey).toList();
        for (Table table : independentTables) {
            dependentTableRequiredTablesMap.remove(table);
            ordered.add(table);
        }
        dependentTableRequiredTablesMap.replaceAll((key, value) -> value.stream().filter(table -> !independentTables.contains(table)).collect(Collectors.toSet()));
    }

    private Map<Table<?>, Set<Table<?>>> getDependentTableRequiredTablesMap(Collection<Table<?>> tables, Collection<ForeignKey<?, ?>> foreignKeys) {
        HashMap result = new HashMap();
        for (ForeignKey<?, ?> foreignKey : foreignKeys) {
            Table dependentTableName = foreignKey.getTable();
            Table requiredTableName = foreignKey.getKey().getTable();
            Set existing = result.getOrDefault(dependentTableName, new HashSet());
            if (!dependentTableName.getName().equals(requiredTableName.getName())) {
                existing.add(requiredTableName);
            }
            result.put(dependentTableName, existing);
        }
        for (Table table : tables) {
            if (result.containsKey(table)) continue;
            result.put(table, new HashSet());
        }
        return result;
    }

    private void removeDependentTablesOnIgnoredTables(Map<Table<?>, Set<Table<?>>> dependentTableRequiredTablesMap, BackupIgnoredTables ignoredTables) {
        while (!ignoredTables.isEmpty()) {
            ignoredTables = this.executeRemovingIgnoredTablesIteration(dependentTableRequiredTablesMap, ignoredTables);
        }
    }

    private BackupIgnoredTables executeRemovingIgnoredTablesIteration(Map<Table<?>, Set<Table<?>>> dependentTableRequiredTablesMap, BackupIgnoredTables ignoredTables) {
        HashSet removingTables = new HashSet();
        for (Table<?> table : dependentTableRequiredTablesMap.keySet()) {
            if (!ignoredTables.contains(table)) continue;
            removingTables.add(table);
        }
        for (Table<Object> table : removingTables) {
            dependentTableRequiredTablesMap.remove(table);
        }
        BackupIgnoredTables nextIgnoredTables = new BackupIgnoredTables();
        for (Table<?> dependentTable : dependentTableRequiredTablesMap.keySet()) {
            Set<Table<?>> requiredTables = dependentTableRequiredTablesMap.get(dependentTable);
            for (Table<?> requiredTable : requiredTables) {
                if (!ignoredTables.contains(requiredTable)) continue;
                nextIgnoredTables.add(dependentTable);
                requiredTables.remove(requiredTable);
            }
        }
        return nextIgnoredTables;
    }
}

