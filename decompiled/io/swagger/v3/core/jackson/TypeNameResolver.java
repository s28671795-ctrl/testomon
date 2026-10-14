/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.core.jackson;

import com.fasterxml.jackson.databind.JavaType;
import io.swagger.v3.core.util.AnnotationsUtils;
import io.swagger.v3.core.util.PrimitiveType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

public class TypeNameResolver {
    public static final TypeNameResolver std = new TypeNameResolver();
    private boolean useFqn = false;

    protected TypeNameResolver() {
    }

    public boolean getUseFqn() {
        return this.useFqn;
    }

    public void setUseFqn(boolean useFqn) {
        this.useFqn = useFqn;
    }

    public String nameForType(JavaType type, Options ... options) {
        return this.nameForType(type, options.length == 0 ? Collections.emptySet() : EnumSet.copyOf(Arrays.asList(options)));
    }

    public String nameForType(JavaType type, Set<Options> options) {
        if (type.hasGenericTypes()) {
            return this.nameForGenericType(type, options);
        }
        String name = this.findStdName(type);
        return name == null ? this.nameForClass(type, options) : name;
    }

    protected String nameForClass(JavaType type, Set<Options> options) {
        return this.nameForClass(type.getRawClass(), options);
    }

    protected String nameForClass(Class<?> cls, Set<Options> options) {
        if (options.contains((Object)Options.SKIP_API_MODEL)) {
            return this.getNameOfClass(cls);
        }
        Schema mp = AnnotationsUtils.getSchemaDeclaredAnnotation(cls);
        String modelName = mp == null ? null : StringUtils.trimToNull(mp.name());
        return modelName == null ? this.getNameOfClass(cls) : modelName;
    }

    protected String getNameOfClass(Class<?> cls) {
        return this.useFqn ? cls.getName() : cls.getSimpleName();
    }

    protected String nameForGenericType(JavaType type, Set<Options> options) {
        StringBuilder generic2 = new StringBuilder(this.nameForClass(type, options));
        int count = type.containedTypeCount();
        for (int i = 0; i < count; ++i) {
            JavaType arg = type.containedType(i);
            String argName = PrimitiveType.fromType(arg) != null ? this.nameForClass(arg, options) : this.nameForType(arg, options);
            generic2.append(WordUtils.capitalize(argName));
        }
        return generic2.toString();
    }

    protected String findStdName(JavaType type) {
        return PrimitiveType.getCommonName(type);
    }

    public static enum Options {
        SKIP_API_MODEL;

    }
}

