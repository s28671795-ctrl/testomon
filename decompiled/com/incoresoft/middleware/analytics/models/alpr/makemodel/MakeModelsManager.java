/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.models.alpr.makemodel;

import com.google.inject.Inject;
import com.incoresoft.Utils.StringUtils;
import com.incoresoft.middleware.analytics.crypto.aes128.AES128Wrapper;
import com.incoresoft.middleware.analytics.models.alpr.makemodel.MakeModel;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

public class MakeModelsManager {
    private static final String KEY = "make_models_field";
    private static final String LABELS_FILE = "data.bin";
    private final Map<Integer, MakeModel> makeModels;

    @Inject
    public MakeModelsManager() {
        this(MakeModelsManager.readMakeModelsFromResources());
    }

    public MakeModelsManager(@NotNull Collection<MakeModel> makeModels) {
        this.makeModels = makeModels.stream().collect(Collectors.toUnmodifiableMap(MakeModel::getId, Function.identity()));
    }

    private static List<MakeModel> readMakeModelsFromResources() {
        String labels = null;
        try (InputStream inputStream2 = MakeModelsManager.class.getClassLoader().getResourceAsStream(LABELS_FILE);){
            if (inputStream2 != null) {
                labels = IOUtils.toString(inputStream2);
            }
        }
        if (labels == null) {
            throw new RuntimeException("Could not read labels from resources.");
        }
        byte[] decoded = Base64.getDecoder().decode(new AES128Wrapper().decode(KEY, labels));
        Scanner scanner = new Scanner(new ByteArrayInputStream(decoded));
        try {
            int index = 200000;
            LinkedList<MakeModel> makeModels = new LinkedList<MakeModel>();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String trim = line.trim();
                if (trim.isBlank()) continue;
                List list = Arrays.stream(trim.split("\\t")).map(it -> it.trim().toUpperCase(Locale.ROOT)).filter(it -> !it.isBlank()).collect(Collectors.toList());
                int id = index++;
                makeModels.add(new MakeModel(id, (String)list.get(0), (String)list.get(1)));
            }
            LinkedList<MakeModel> linkedList = makeModels;
            scanner.close();
            return linkedList;
        }
        catch (Throwable throwable) {
            try {
                try {
                    scanner.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<MakeModel> getAll() {
        return List.copyOf(this.makeModels.values());
    }

    public List<String> getMakes() {
        return this.makeModels.values().stream().map(MakeModel::getMake).distinct().sorted().collect(Collectors.toList());
    }

    public List<String> getMakeModels(String make) {
        return this.makeModels.values().stream().filter(it -> it.getMake().equals(make)).map(MakeModel::getModel).distinct().sorted().collect(Collectors.toList());
    }

    public MakeModel getMakeModel(int id) {
        if (!this.makeModels.containsKey(id)) {
            return MakeModel.UNKNOWN;
        }
        return this.makeModels.get(id);
    }

    public Set<Integer> getMakeModelsIds(String make, String model) {
        if (StringUtils.isEmpty(make)) {
            return Collections.emptySet();
        }
        String makeUppercase = make.toUpperCase();
        String modelUppercase = Optional.ofNullable(model).map(String::toUpperCase).orElse(null);
        return this.makeModels.values().stream().filter(it -> {
            if (StringUtils.isEmpty(modelUppercase)) {
                return it.getMake().equals(makeUppercase);
            }
            return it.getMake().equals(makeUppercase) && it.getModel().equals(modelUppercase);
        }).map(MakeModel::getId).collect(Collectors.toSet());
    }
}

