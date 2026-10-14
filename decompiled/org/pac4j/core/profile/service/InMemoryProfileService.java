/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.profile.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.definition.CommonProfileDefinition;
import org.pac4j.core.profile.factory.ProfileFactory;
import org.pac4j.core.profile.service.AbstractProfileService;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.core.util.serializer.JsonSerializer;

public class InMemoryProfileService<U extends CommonProfile>
extends AbstractProfileService<U> {
    public Map<String, Map<String, Object>> profiles;
    public ProfileFactory profileFactory;

    public InMemoryProfileService(ProfileFactory profileFactory) {
        this(new HashMap<String, Map<String, Object>>(), profileFactory);
    }

    public InMemoryProfileService(Map<String, Map<String, Object>> profiles, ProfileFactory profileFactory) {
        this.profiles = profiles;
        this.profileFactory = profileFactory;
    }

    @Override
    protected void internalInit(boolean forceReinit) {
        CommonHelper.assertNotNull("passwordEncoder", this.getPasswordEncoder());
        this.defaultProfileDefinition(new CommonProfileDefinition(this.profileFactory));
        this.setSerializer(new JsonSerializer(CommonProfile.class));
        super.internalInit(forceReinit);
    }

    @Override
    protected void insert(Map<String, Object> attributes) {
        String id = (String)attributes.get(this.getIdAttribute());
        this.logger.debug("Inserting doc id: {} with attributes: {}", (Object)id, (Object)attributes);
        this.profiles.put(id, attributes);
    }

    @Override
    protected void update(Map<String, Object> attributes) {
        String id = (String)attributes.get(this.getIdAttribute());
        this.logger.debug("Updating id: {} with attributes: {}", (Object)id, (Object)attributes);
        Map<String, Object> profile = this.profiles.get(id);
        if (profile != null) {
            profile.putAll(attributes);
        } else {
            this.profiles.put(id, attributes);
        }
    }

    @Override
    protected void deleteById(String id) {
        this.logger.debug("Delete id: {}", (Object)id);
        this.profiles.remove(id);
    }

    private Map<String, Object> populateAttributes(Map<String, Object> rowAttributes, List<String> names) {
        return rowAttributes.entrySet().stream().filter(p -> names == null || names.contains(p.getKey())).collect(HashMap::new, (m4, v) -> m4.put((String)v.getKey(), v.getValue()), HashMap::putAll);
    }

    @Override
    protected List<Map<String, Object>> read(List<String> names, String key, String value) {
        ArrayList<Map<String, Object>> listAttributes;
        this.logger.debug("Reading key / value: {} / {}", (Object)key, (Object)value);
        if (key.equals(this.getIdAttribute())) {
            listAttributes = new ArrayList<Map<String, Object>>();
            Map<String, Object> profile = this.profiles.get(value);
            if (profile != null) {
                listAttributes.add(this.populateAttributes(profile, names));
            }
        } else {
            listAttributes = this.profiles.entrySet().stream().filter(p -> ((Map)p.getValue()).get(key) != null && ((Map)p.getValue()).get(key).equals(value)).map(p -> this.populateAttributes((Map)p.getValue(), names)).collect(Collectors.toList());
        }
        this.logger.debug("Found: {}", (Object)listAttributes);
        return listAttributes;
    }
}

