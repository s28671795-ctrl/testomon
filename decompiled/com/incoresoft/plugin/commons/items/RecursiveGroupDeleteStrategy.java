/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.items;

import com.incoresoft.plugin.commons.database.CRUDRepository;
import com.incoresoft.plugin.commons.items.Item;
import com.incoresoft.plugin.commons.items.ItemGroup;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jooq.Condition;

public class RecursiveGroupDeleteStrategy<G extends ItemGroup, I extends Item> {
    private final CRUDRepository<?, G> groupRepository;
    private final CRUDRepository<?, I> itemRepository;

    public void delete(int groupId) {
        Map<Integer, List<G>> parentIdGroupsMap = this.groupRepository.getAll(new Condition[0]).stream().collect(Collectors.groupingBy(ItemGroup::getParentId));
        Map<Integer, List<I>> parentIdItemsMap = this.itemRepository.getAll(new Condition[0]).stream().collect(Collectors.groupingBy(Item::getParentId));
        this.delete(groupId, parentIdGroupsMap, parentIdItemsMap);
    }

    private void delete(int id, Map<Integer, List<G>> parentIdGroupsMap, Map<Integer, List<I>> parentIdItemsMap) {
        if (parentIdItemsMap.containsKey(id)) {
            parentIdItemsMap.get(id).forEach(item -> this.itemRepository.delete(item.getId()));
        }
        if (parentIdGroupsMap.containsKey(id)) {
            parentIdGroupsMap.get(id).forEach(group -> this.delete(group.getId(), parentIdGroupsMap, parentIdItemsMap));
        }
        this.groupRepository.delete(id);
    }

    public RecursiveGroupDeleteStrategy(CRUDRepository<?, G> groupRepository, CRUDRepository<?, I> itemRepository) {
        this.groupRepository = groupRepository;
        this.itemRepository = itemRepository;
    }
}

