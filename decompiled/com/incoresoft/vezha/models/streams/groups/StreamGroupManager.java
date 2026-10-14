/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.streams.groups;

import com.google.inject.Inject;
import com.incoresoft.Utils.CollectionUtils;
import com.incoresoft.Utils.Pair;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.plugin.commons.params.PaginationParams;
import com.incoresoft.plugin.commons.sorting.SortParams;
import com.incoresoft.vezha.database.StreamsRepository;
import com.incoresoft.vezha.dto.streams.FullStreamDTO;
import com.incoresoft.vezha.dto.streams.StreamSearchParams;
import com.incoresoft.vezha.models.streams.StreamImpl;
import com.incoresoft.vezha.models.streams.exceptions.ParentStreamGroupDoesNotExistException;
import com.incoresoft.vezha.models.streams.exceptions.StreamGroupIsNotEmptyException;
import com.incoresoft.vezha.models.streams.exceptions.StreamGroupIsParentToItselfException;
import com.incoresoft.vezha.models.streams.exceptions.StreamGroupWithThisNameExistsException;
import com.incoresoft.vezha.models.streams.groups.StreamGroup;
import com.incoresoft.vezha.models.streams.model.GroupsAndStreamsResponse;
import com.incoresoft.vezha.models.streams.model.GroupsResponse;
import com.incoresoft.vezha.models.users.UserImpl;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nonnull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StreamGroupManager {
    private final Map<Integer, StreamGroup> groups = new ConcurrentHashMap<Integer, StreamGroup>();
    private final StreamsRepository streamsRepository;

    @Inject
    public StreamGroupManager(@NotNull StreamsRepository streamsRepository) {
        this.streamsRepository = Objects.requireNonNull(streamsRepository);
    }

    @Inject
    public void init() {
        this.groups.put(StreamGroup.ROOT.getId(), StreamGroup.ROOT);
        for (StreamGroup group : this.streamsRepository.getStreamGroups()) {
            this.groups.put(group.getId(), group);
        }
    }

    public StreamGroup get(int id) {
        return this.groups.get(id);
    }

    public Collection<StreamGroup> getGroups() {
        return this.groups.values();
    }

    public Collection<StreamGroup> getGroups(UserImpl user) {
        return this.getGroups().stream().filter(it -> user.ownsItem(it.getClientId())).toList();
    }

    public StreamGroup add(StreamGroup group) {
        Objects.requireNonNull(group);
        if (!this.groupNameIsUnique(group)) {
            throw new StreamGroupWithThisNameExistsException(group.getName());
        }
        if (!this.parentForThisGroupExists(group)) {
            throw new ParentStreamGroupDoesNotExistException(group.getParentId());
        }
        StreamGroup addedGroup = this.streamsRepository.upsertGroup(group);
        this.groups.put(addedGroup.getId(), addedGroup);
        return addedGroup;
    }

    public StreamGroup update(StreamGroup group) {
        Objects.requireNonNull(group);
        if (!this.exists(group.getId())) {
            throw new IllegalArgumentException(String.format("Cannot update group - group with id %d not found", group.getId()));
        }
        if (!this.groupNameIsUnique(group)) {
            throw new StreamGroupWithThisNameExistsException(group.getName());
        }
        if (!this.parentForThisGroupExists(group)) {
            throw new ParentStreamGroupDoesNotExistException(group.getParentId());
        }
        if (this.groupIsParentToItself(group)) {
            throw new StreamGroupIsParentToItselfException();
        }
        StreamGroup updatedGroup = this.streamsRepository.upsertGroup(group);
        this.groups.put(updatedGroup.getId(), updatedGroup);
        return updatedGroup;
    }

    public boolean groupNameIsUnique(StreamGroup group) {
        return this.groupNameIsUnique(group.getId(), group.getName());
    }

    public boolean groupNameIsUnique(@Nullable Integer excludedId, @NotNull String name) {
        return this.groups.values().stream().noneMatch(it -> (excludedId == null || it.getId() != excludedId.intValue()) && it.getName().equals(name));
    }

    public boolean parentForThisGroupExists(StreamGroup group) {
        return this.exists(group.getParentId());
    }

    public boolean exists(int groupId) {
        return this.groups.containsKey(groupId) || groupId == 0;
    }

    public boolean groupIsParentToItself(StreamGroup group) {
        HashSet<Integer> restrictedParentIds = new HashSet<Integer>();
        StreamGroup currentGroup = group;
        while (!restrictedParentIds.contains(StreamGroup.ROOT.getId())) {
            if (restrictedParentIds.contains(currentGroup.getId())) {
                return true;
            }
            restrictedParentIds.add(currentGroup.getId());
            currentGroup = this.get(currentGroup.getParentId());
        }
        return false;
    }

    public void delete(int id) {
        if (StreamGroup.ROOT.getId() == id) {
            throw new IllegalArgumentException("Cannot delete root group");
        }
        if (!this.exists(id)) {
            throw new IllegalArgumentException(String.format("Cannot delete group - group with id %d not found", id));
        }
        if (this.groups.values().stream().anyMatch(it -> it.getParentId() == id)) {
            throw new StreamGroupIsNotEmptyException();
        }
        if (this.streamsRepository.getAll(false).stream().anyMatch(it -> it.getFolderId() == id)) {
            throw new StreamGroupIsNotEmptyException();
        }
        this.streamsRepository.deleteGroup(id);
        this.groups.remove(id);
    }

    public GroupsAndStreamsResponse getStreamGroupResponse(UserImpl user, StreamSearchParams streamSearchParams, PaginationParams paginationParams, SortParams sortParams) {
        Objects.requireNonNull(user);
        return new ResponseGenerator().getGroupsAndStreams(user, StreamGroup.ROOT, this.parentToGroups(user), this.parentToStreams(user, streamSearchParams, paginationParams, sortParams));
    }

    public GroupsResponse getGroupsResponse(@NotNull UserImpl user) {
        Objects.requireNonNull(user);
        return new ResponseGenerator().getGroups(StreamGroup.ROOT, this.parentToGroups(user));
    }

    @Nonnull
    private Map<Integer, List<Stream>> parentToStreams(UserImpl user, StreamSearchParams streamSearchParams, PaginationParams paginationParams, SortParams sortParams) {
        HashMap<Integer, List> parentToStreamMap = new HashMap<Integer, List>();
        Pair<Long, List<StreamImpl>> streamsData = this.streamsRepository.getAll(user, streamSearchParams, paginationParams, sortParams);
        for (Stream stream : (List)streamsData.second) {
            parentToStreamMap.computeIfAbsent(stream.getFolderId(), it -> new LinkedList()).add(stream);
        }
        return Collections.unmodifiableMap(parentToStreamMap);
    }

    @Nonnull
    private Map<Integer, List<StreamGroup>> parentToGroups(UserImpl user) {
        HashMap<Integer, List> parentToGroupMap = new HashMap<Integer, List>();
        for (StreamGroup group : this.getGroups(user)) {
            parentToGroupMap.computeIfAbsent(group.getParentId(), it -> new LinkedList()).add(group);
        }
        return Collections.unmodifiableMap(parentToGroupMap);
    }

    private static class ResponseGenerator {
        private ResponseGenerator() {
        }

        public GroupsResponse getGroups(StreamGroup group, Map<Integer, List<StreamGroup>> parentToGroupMap) {
            List<StreamGroup> streamGroups = parentToGroupMap.get(group.getId());
            List<GroupsResponse> groups2 = CollectionUtils.isNotEmpty(streamGroups) ? streamGroups.stream().map(it -> this.getGroups((StreamGroup)it, parentToGroupMap)).toList() : Collections.emptyList();
            return new GroupsResponse(group, groups2);
        }

        public GroupsAndStreamsResponse getGroupsAndStreams(User user, StreamGroup group, Map<Integer, List<StreamGroup>> parentToGroupMap, Map<Integer, List<Stream>> parentToStreamMap) {
            List<StreamGroup> streamGroups = parentToGroupMap.get(group.getId());
            List<GroupsAndStreamsResponse> groups2 = CollectionUtils.isNotEmpty(streamGroups) ? streamGroups.stream().map(it -> this.getGroupsAndStreams(user, (StreamGroup)it, parentToGroupMap, parentToStreamMap)).toList() : Collections.emptyList();
            List<Stream> streams = parentToStreamMap.get(group.getId());
            if (streams == null) {
                streams = Collections.emptyList();
            }
            return new GroupsAndStreamsResponse(group, groups2, streams.stream().map(it -> new FullStreamDTO(user, (Stream)it)).toList());
        }
    }
}

