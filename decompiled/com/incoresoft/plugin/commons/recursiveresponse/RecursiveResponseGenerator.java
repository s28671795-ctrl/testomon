/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.recursiveresponse;

import com.incoresoft.Utils.CollectionUtils;
import com.incoresoft.middleware.model.Model;
import com.incoresoft.middleware.model.folders.Folder;
import com.incoresoft.plugin.commons.sorting.SortFields;
import com.incoresoft.plugin.commons.sorting.SortOrders;
import java.lang.runtime.SwitchBootstraps;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;

public class RecursiveResponseGenerator<F extends Folder, M extends Model, R> {
    private final F rootFolder;
    private final Map<Integer, List<F>> parentToFolderMap;
    private final Map<Integer, List<M>> parentToItemMap;
    private final ResponseInstantiator<F, M, R> responseCreator;
    private final Comparator<F> folderComparator;
    private final Comparator<M> itemComparator;
    private final Predicate<M> acceptModelCriteria;
    private final Predicate<F> acceptWholeFolderCriteria;

    public RecursiveResponseGenerator(@NotNull F rootFolder, @NotNull Map<Integer, List<F>> parentToFolderMap, @NotNull Map<Integer, List<M>> parentToItemMap, @NotNull ResponseInstantiator<F, M, R> responseCreator, @NotNull NameExtractor<M> nameExtractor, @NotNull SortFields sortingField, @NotNull SortOrders sortOrders, @NotNull Predicate<M> acceptModelCriteria, @NotNull Predicate<F> acceptWholeFolderCriteria) {
        this.rootFolder = (Folder)Objects.requireNonNull(rootFolder);
        this.parentToFolderMap = Objects.requireNonNull(parentToFolderMap);
        this.parentToItemMap = Objects.requireNonNull(parentToItemMap);
        this.responseCreator = Objects.requireNonNull(responseCreator);
        this.folderComparator = RecursiveResponseGenerator.folderComparator(sortingField, sortOrders);
        this.itemComparator = RecursiveResponseGenerator.itemComparator(nameExtractor, sortingField, sortOrders);
        this.acceptModelCriteria = acceptModelCriteria;
        this.acceptWholeFolderCriteria = acceptWholeFolderCriteria;
    }

    public RecursiveResponseGenerator(@NotNull F rootFolder, @NotNull Map<Integer, List<F>> parentToFolderMap, @NotNull Map<Integer, List<M>> parentToItemMap, @NotNull ResponseInstantiator<F, M, R> responseCreator, @NotNull NameExtractor<M> nameExtractor, @NotNull SortFields sortingField, @NotNull SortOrders sortOrders) {
        this((Folder)rootFolder, (Map<Integer, List<Folder>>)parentToFolderMap, (Map<Integer, List<Model>>)parentToItemMap, (ResponseInstantiator<Folder, Model, R>)responseCreator, (NameExtractor<Model>)nameExtractor, sortingField, sortOrders, model -> true, folder -> false);
    }

    protected RecursiveResponseGenerator(@NotNull F rootFolder, @NotNull Map<Integer, List<F>> parentToFolderMap, @NotNull Map<Integer, List<M>> parentToItemMap, @NotNull ResponseInstantiator<F, M, R> responseCreator, @NotNull Comparator<F> folderComparator, @NotNull Comparator<M> itemComparator) {
        this.rootFolder = (Folder)Objects.requireNonNull(rootFolder);
        this.parentToFolderMap = Objects.requireNonNull(parentToFolderMap);
        this.parentToItemMap = Objects.requireNonNull(parentToItemMap);
        this.responseCreator = Objects.requireNonNull(responseCreator);
        this.folderComparator = Objects.requireNonNull(folderComparator);
        this.itemComparator = Objects.requireNonNull(itemComparator);
        this.acceptModelCriteria = model -> true;
        this.acceptWholeFolderCriteria = folder -> false;
    }

    protected static <F extends Folder> Comparator<F> folderComparator(SortFields sortingField, SortOrders sortOrders) {
        Enum enum_ = sortingField;
        int n = 0;
        Comparator<Folder> comparator = switch (SwitchBootstraps.enumSwitch("enumSwitch", new Object[]{"ID", "CREATION_TIME", "NAME"}, (SortFields)enum_, n)) {
            default -> throw new MatchException(null, null);
            case 0, 1 -> Comparator.comparing(Folder::getId);
            case 2 -> Comparator.comparing(Folder::getName, String.CASE_INSENSITIVE_ORDER);
            case -1 -> Comparator.comparing(Folder::getName, String.CASE_INSENSITIVE_ORDER);
        };
        enum_ = sortOrders;
        n = 0;
        return switch (SwitchBootstraps.enumSwitch("enumSwitch", new Object[]{"ASC", "DESC"}, (SortOrders)enum_, n)) {
            default -> throw new MatchException(null, null);
            case 0 -> comparator;
            case 1 -> comparator.reversed();
            case -1 -> comparator;
        };
    }

    protected static <M extends Model> Comparator<M> itemComparator(NameExtractor<M> nameExtractor, SortFields sortingField, SortOrders sortOrders) {
        Enum enum_ = sortingField;
        int n = 0;
        Comparator<Model> comparator = switch (SwitchBootstraps.enumSwitch("enumSwitch", new Object[]{"ID", "CREATION_TIME", "NAME"}, (SortFields)enum_, n)) {
            default -> throw new MatchException(null, null);
            case 0, 1 -> Comparator.comparingInt(Model::getId);
            case 2 -> Comparator.comparing(nameExtractor::extractName, String.CASE_INSENSITIVE_ORDER);
            case -1 -> Comparator.comparing(nameExtractor::extractName, String.CASE_INSENSITIVE_ORDER);
        };
        enum_ = sortOrders;
        n = 0;
        return switch (SwitchBootstraps.enumSwitch("enumSwitch", new Object[]{"ASC", "DESC"}, (SortOrders)enum_, n)) {
            default -> throw new MatchException(null, null);
            case 0 -> comparator;
            case 1 -> comparator.reversed();
            case -1 -> comparator;
        };
    }

    public R getResponse() {
        return this.getResponse(this.rootFolder);
    }

    private R getResponse(F folder) {
        List<R> innerFolders = this.createInnerFolders(this.parentToFolderMap.get(folder.getId()));
        List<Object> items = this.parentToItemMap.get(folder.getId());
        if (items == null) {
            items = Collections.emptyList();
        }
        items = items.stream().filter(this.acceptModelCriteria).sorted(this.itemComparator).toList();
        return this.responseCreator.instantiate(folder, innerFolders, items);
    }

    @NotNull
    private List<R> createInnerFolders(List<F> folders) {
        if (CollectionUtils.isEmpty(folders)) {
            return Collections.emptyList();
        }
        return folders.stream().sorted(this.folderComparator).map(folder -> this.acceptWholeFolderCriteria.test(folder) ? this.getWholeFolderResponse(folder) : this.getResponse(folder)).filter(Objects::nonNull).toList();
    }

    private R getWholeFolderResponse(F folder) {
        return new RecursiveResponseGenerator<F, M, R>(folder, this.parentToFolderMap, this.parentToItemMap, this.responseCreator, this.folderComparator, this.itemComparator).getResponse();
    }

    public static interface ResponseInstantiator<F extends Folder, M, R> {
        public R instantiate(F var1, List<R> var2, List<M> var3);
    }

    public static interface NameExtractor<T> {
        public String extractName(T var1);
    }
}

