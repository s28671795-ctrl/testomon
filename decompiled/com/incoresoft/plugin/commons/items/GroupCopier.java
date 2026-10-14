/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.items;

import com.incoresoft.middleware.model.Model;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.plugin.commons.items.BaseItemGroupManager;
import com.incoresoft.plugin.commons.items.BaseItemManager;
import com.incoresoft.plugin.commons.items.ItemGroup;
import com.incoresoft.plugin.commons.utils.ItemNamingUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.ImmutablePair;

public abstract class GroupCopier<M extends Model, G extends ItemGroup> {
    private final BaseItemManager<M> itemManager;
    private final BaseItemGroupManager<G> groupManager;
    private final Function<M, String> nameRetriever;
    private final Function<M, Integer> parentIdRetriever;
    private final CopiedItemInstantiator<M> copiedItemInstantiator;
    private final CopiedGroupInstantiator<G> copiedGroupInstantiator;

    protected GroupCopier(BaseItemManager<M> itemManager, BaseItemGroupManager<G> groupManager, Function<M, String> nameRetriever, Function<M, Integer> parentIdRetriever, CopiedItemInstantiator<M> copiedItemInstantiator, CopiedGroupInstantiator<G> copiedGroupInstantiator) {
        this.itemManager = itemManager;
        this.groupManager = groupManager;
        this.nameRetriever = nameRetriever;
        this.parentIdRetriever = parentIdRetriever;
        this.copiedItemInstantiator = copiedItemInstantiator;
        this.copiedGroupInstantiator = copiedGroupInstantiator;
    }

    public void copy(User user, List<G> originals, int newParentId) {
        for (ItemGroup group : originals) {
            this.copy(user, group, newParentId);
        }
    }

    public G copy(User user, G original, int newParentId) {
        Map allItems = this.itemManager.getAll().stream().collect(Collectors.toMap(Model::getId, layout -> layout, (a, b) -> a, HashMap::new));
        Map allGroups = this.groupManager.getAll().stream().collect(Collectors.toMap(ItemGroup::getId, group -> group, (a, b) -> a, HashMap::new));
        ItemGroup addedGroup = (ItemGroup)this.groupManager.add(this.copiedGroupInstantiator.instantiate(user, this.createCopyNameForGroup(original, p -> this.retrieveNamesFromMap((Pattern)p, allGroups.values())), original, newParentId));
        this.copyChildGroups(user, original, addedGroup, allItems, allGroups);
        return (G)addedGroup;
    }

    protected void copyChildGroups(User user, G originalParent, G newParent, Map<Integer, M> allItems, Map<Integer, G> allGroups) {
        List<Model> items = allItems.values().stream().filter(item -> this.parentIdRetriever.apply(item).intValue() == originalParent.getId()).map(it -> this.copiedItemInstantiator.instantiate(user, this.createCopyNameForItem(this.nameRetriever.apply(it), allItems), (Model)it, newParent.getId())).toList();
        this.itemManager.addAll(items);
        List<ImmutablePair> groups2 = new ArrayList<G>(allGroups.values()).stream().filter(group -> newParent.getId() != group.getId()).filter(group -> group.getParentId() == originalParent.getId()).map(it -> {
            ItemGroup item = this.copiedGroupInstantiator.instantiate(user, this.createCopyNameForGroup(it, p -> this.retrieveNamesFromMap((Pattern)p, allGroups.values())), (ItemGroup)it, newParent.getId());
            ItemGroup added = this.groupManager.add(item);
            allGroups.put(added.getId(), added);
            return new ImmutablePair<ItemGroup, ItemGroup>((ItemGroup)it, added);
        }).toList();
        for (ImmutablePair group2 : groups2) {
            this.copyChildGroups(user, (ItemGroup)group2.left, (ItemGroup)group2.right, allItems, allGroups);
        }
    }

    protected String createCopyNameForGroup(G original, Function<Pattern, List<String>> retrieveNames) {
        return ItemNamingUtils.getCopyName(((ItemGroup)original).getName(), retrieveNames);
    }

    protected List<String> retrieveNamesFromMap(Pattern pattern, Collection<G> allGroups) {
        return allGroups.stream().map(ItemGroup::getName).filter(layoutGroup -> pattern.matcher((CharSequence)layoutGroup).matches()).toList();
    }

    private String createCopyNameForItem(String originalName, Map<Integer, M> allItems) {
        return ItemNamingUtils.getCopyName(originalName, p -> this.retrieveNames((Pattern)p, allItems.values()));
    }

    private List<String> retrieveNames(Pattern pattern, Collection<M> items) {
        return items.stream().map(this.nameRetriever).filter(name -> pattern.matcher((CharSequence)name).matches()).toList();
    }

    public BaseItemManager<M> getItemManager() {
        return this.itemManager;
    }

    public BaseItemGroupManager<G> getGroupManager() {
        return this.groupManager;
    }

    public CopiedGroupInstantiator<G> getCopiedGroupInstantiator() {
        return this.copiedGroupInstantiator;
    }

    public static interface CopiedItemInstantiator<M extends Model> {
        public M instantiate(User var1, String var2, M var3, int var4);
    }

    public static interface CopiedGroupInstantiator<G extends ItemGroup> {
        public G instantiate(User var1, String var2, G var3, int var4);
    }
}

