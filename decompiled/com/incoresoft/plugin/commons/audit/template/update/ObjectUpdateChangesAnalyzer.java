/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.audit.template.update;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import com.incoresoft.middleware.http.serialization.Serialization;
import com.incoresoft.plugin.commons.audit.template.update.changes.FieldAdded;
import com.incoresoft.plugin.commons.audit.template.update.changes.FieldArrayElementAdded;
import com.incoresoft.plugin.commons.audit.template.update.changes.FieldArrayElementDeleted;
import com.incoresoft.plugin.commons.audit.template.update.changes.FieldArrayElementsChanged;
import com.incoresoft.plugin.commons.audit.template.update.changes.FieldChange;
import com.incoresoft.plugin.commons.audit.template.update.changes.FieldDeleted;
import com.incoresoft.plugin.commons.audit.template.update.changes.FieldObjectChanged;
import com.incoresoft.plugin.commons.audit.template.update.changes.FieldValueUpdated;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectUpdateChangesAnalyzer {
    private static final Logger log = LoggerFactory.getLogger(ObjectUpdateChangesAnalyzer.class);

    public <T> List<FieldChange> getChanges(T old, T updated) {
        Object oldNode = Serialization.objectMapper().valueToTree(old);
        Object newNode = Serialization.objectMapper().valueToTree(updated);
        return this.getChanges((JsonNode)oldNode, (JsonNode)newNode, "");
    }

    List<FieldChange> getChanges(JsonNode old, JsonNode updated, String fieldName) {
        if (old instanceof ObjectNode) {
            ObjectNode oldObject = (ObjectNode)old;
            if (updated instanceof ObjectNode) {
                ObjectNode updatedObject = (ObjectNode)updated;
                return this.getChanges(oldObject, updatedObject, fieldName);
            }
        }
        if (old instanceof ArrayNode) {
            ArrayNode oldArray = (ArrayNode)old;
            if (updated instanceof ArrayNode) {
                ArrayNode updatedArray = (ArrayNode)updated;
                return this.getChanges(oldArray, updatedArray, fieldName);
            }
        }
        if (old instanceof ValueNode) {
            ValueNode oldValue = (ValueNode)old;
            if (updated instanceof ValueNode) {
                ValueNode updatedValue = (ValueNode)updated;
                return this.getChanges(oldValue, updatedValue, fieldName);
            }
        }
        log.warn("Warning during logging entity update changes: basic types of field {} differ: {}, {}", fieldName, old.getClass(), updated.getClass());
        return List.of(new FieldObjectChanged(fieldName));
    }

    private List<FieldChange> getChanges(ArrayNode old, ArrayNode updated, String fieldName) {
        List<JsonNode> oldElements = this.getElements(old);
        List<JsonNode> updatedElements = this.getElements(updated);
        if (this.areAllNodesValues(oldElements) && this.areAllNodesValues(updatedElements)) {
            List<ValueNode> oldValueNodes = oldElements.stream().map(node -> (ValueNode)node).toList();
            List<ValueNode> updatedValueNodes = updatedElements.stream().map(node -> (ValueNode)node).toList();
            return this.getChangesFromValueNodes(oldValueNodes, updatedValueNodes, fieldName);
        }
        if (this.areAllNodesObjects(oldElements) && this.areAllNodesObjects(updatedElements)) {
            List<ObjectNode> oldObjectNodes = oldElements.stream().map(node -> (ObjectNode)node).toList();
            List<ObjectNode> updatedObjectNodes = updatedElements.stream().map(node -> (ObjectNode)node).toList();
            return this.getChangesFromObjectNodes(oldObjectNodes, updatedObjectNodes, fieldName);
        }
        if (oldElements.equals(updatedElements)) {
            return List.of();
        }
        return List.of(new FieldArrayElementsChanged(fieldName));
    }

    private List<FieldChange> getChangesFromObjectNodes(List<ObjectNode> old, List<ObjectNode> updated, String fieldName) {
        int i;
        boolean doAllOldHaveIds = old.stream().allMatch(node -> node.get("id") != null);
        boolean doAllUpdatedHaveIds = updated.stream().allMatch(node -> node.get("id") != null);
        if (!doAllOldHaveIds || !doAllUpdatedHaveIds) {
            if (CollectionUtils.isEqualCollection(old, updated)) {
                return List.of();
            }
            return List.of(new FieldArrayElementsChanged(fieldName));
        }
        Map<String, ObjectNode> oldIdsToNodesMap = old.stream().collect(Collectors.toMap(node -> node.get("id").asText(), node -> node));
        Map<String, ObjectNode> updatedIdsToNodesMap = updated.stream().collect(Collectors.toMap(node -> node.get("id").asText(), node -> node));
        HashMap<String, Integer> oldIdsIndexesMap = new HashMap<String, Integer>();
        HashMap<String, Integer> updatedIdsIndexesMap = new HashMap<String, Integer>();
        for (i = 0; i < old.size(); ++i) {
            oldIdsIndexesMap.put(old.get(i).get("id").asText(), i);
        }
        for (i = 0; i < updated.size(); ++i) {
            updatedIdsIndexesMap.put(updated.get(i).get("id").asText(), i);
        }
        Collection<String> remainingIds = CollectionUtils.intersection(oldIdsIndexesMap.keySet(), updatedIdsIndexesMap.keySet());
        Collection<String> addedIds = ObjectUpdateChangesAnalyzer.diff(updatedIdsIndexesMap.keySet(), oldIdsIndexesMap.keySet());
        Collection<String> deletedIds = ObjectUpdateChangesAnalyzer.diff(oldIdsIndexesMap.keySet(), updatedIdsIndexesMap.keySet());
        ArrayList<FieldChange> changes = new ArrayList<FieldChange>();
        for (String addedId : addedIds) {
            changes.add(new FieldArrayElementAdded(this.appendListIndex(fieldName, (Integer)updatedIdsIndexesMap.get(addedId)), addedId));
        }
        for (String deletedId : deletedIds) {
            changes.add(new FieldArrayElementDeleted(this.appendListIndex(fieldName, (Integer)oldIdsIndexesMap.get(deletedId)), deletedId));
        }
        for (String remainingId : remainingIds) {
            changes.addAll(this.getChanges(oldIdsToNodesMap.get(remainingId), updatedIdsToNodesMap.get(remainingId), this.appendListIndex(fieldName, (Integer)updatedIdsIndexesMap.get(remainingId))));
        }
        return changes;
    }

    private List<FieldChange> getChangesFromValueNodes(List<ValueNode> old, List<ValueNode> updated, String fieldName) {
        int i;
        HashMap<String, Integer> oldValuesIndexesMap = new HashMap<String, Integer>();
        HashMap<String, Integer> updatedValuesIndexesMap = new HashMap<String, Integer>();
        for (i = 0; i < old.size(); ++i) {
            oldValuesIndexesMap.put(old.get(i).asText(), i);
        }
        for (i = 0; i < updated.size(); ++i) {
            updatedValuesIndexesMap.put(updated.get(i).asText(), i);
        }
        Collection<String> addedValues = ObjectUpdateChangesAnalyzer.diff(updatedValuesIndexesMap.keySet(), oldValuesIndexesMap.keySet());
        Collection<String> deletedValues = ObjectUpdateChangesAnalyzer.diff(oldValuesIndexesMap.keySet(), updatedValuesIndexesMap.keySet());
        ArrayList<FieldChange> changes = new ArrayList<FieldChange>();
        for (String addedValue : addedValues) {
            changes.add(new FieldArrayElementAdded(this.appendListIndex(fieldName, (Integer)updatedValuesIndexesMap.get(addedValue)), addedValue));
        }
        for (String deletedValue : deletedValues) {
            changes.add(new FieldArrayElementDeleted(this.appendListIndex(fieldName, (Integer)oldValuesIndexesMap.get(deletedValue)), deletedValue));
        }
        return changes;
    }

    private List<FieldChange> getChanges(ValueNode old, ValueNode updated, String fieldName) {
        return old.equals(updated) ? List.of() : List.of(new FieldValueUpdated(fieldName, old.asText(), updated.asText()));
    }

    private List<FieldChange> getChanges(ObjectNode old, ObjectNode updated, String fieldName) {
        ArrayList<FieldChange> changes = new ArrayList<FieldChange>();
        Map<String, JsonNode> oldFields = this.getFields(old);
        Map<String, JsonNode> updatedFields = this.getFields(updated);
        Collection<String> remainingFields = CollectionUtils.intersection(oldFields.keySet(), updatedFields.keySet());
        Collection<String> addedFields = ObjectUpdateChangesAnalyzer.diff(updatedFields.keySet(), remainingFields);
        Collection<String> deletedFields = ObjectUpdateChangesAnalyzer.diff(oldFields.keySet(), remainingFields);
        for (String addedField : addedFields) {
            changes.add(new FieldAdded(this.appendChildFieldName(fieldName, addedField)));
        }
        for (String removedField : deletedFields) {
            changes.add(new FieldDeleted(this.appendChildFieldName(fieldName, removedField)));
        }
        for (String remainingField : remainingFields) {
            changes.addAll(this.getChanges(oldFields.get(remainingField), updatedFields.get(remainingField), this.appendChildFieldName(fieldName, remainingField)));
        }
        return changes;
    }

    private boolean areAllNodesValues(List<JsonNode> nodes) {
        return nodes.stream().allMatch(node -> node instanceof ValueNode);
    }

    private boolean areAllNodesObjects(List<JsonNode> nodes) {
        return nodes.stream().allMatch(node -> node instanceof ObjectNode);
    }

    private Map<String, JsonNode> getFields(ObjectNode node) {
        HashMap<String, JsonNode> result = new HashMap<String, JsonNode>();
        Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field2 = fields.next();
            result.put(field2.getKey(), node.get(field2.getKey()));
        }
        return result;
    }

    private List<JsonNode> getElements(ArrayNode node) {
        ArrayList<JsonNode> result = new ArrayList<JsonNode>();
        Iterator<JsonNode> elements = node.elements();
        while (elements.hasNext()) {
            result.add(elements.next());
        }
        return result;
    }

    private String appendListIndex(String fieldName, int index) {
        return fieldName + "[" + index + "]";
    }

    private String appendChildFieldName(String parentName, String childName) {
        if (parentName.isEmpty()) {
            return childName;
        }
        return parentName + "." + childName;
    }

    private static <T> Collection<T> diff(Collection<T> c1, Collection<T> c2) {
        if (c1 == null || c1.isEmpty() || c2 == null || c2.isEmpty()) {
            return c1;
        }
        ArrayList<T> difference = new ArrayList<T>();
        for (T item : c1) {
            if (c2.contains(item)) continue;
            difference.add(item);
        }
        return difference;
    }
}

