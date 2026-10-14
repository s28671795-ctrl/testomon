/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.java9.java.lang.module;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import org.codehaus.commons.compiler.java8.java.util.Optional;
import org.codehaus.commons.compiler.java9.java.lang.module.ModuleReader;
import org.codehaus.commons.compiler.util.reflect.Classes;
import org.codehaus.commons.compiler.util.reflect.Methods;

public class ModuleReference {
    private static final Class<?> CLASS = Classes.load("java.lang.module.ModuleReference");
    private static final Method METHOD_location = Classes.getDeclaredMethod(CLASS, "location", new Class[0]);
    private static final Method METHOD_open = Classes.getDeclaredMethod(CLASS, "open", new Class[0]);
    private final Object delegate;

    public ModuleReference(Object delegate) {
        this.delegate = delegate;
    }

    public Optional<URI> location() {
        return new Optional<URI>(Methods.invoke(METHOD_location, this.delegate, new Object[0]));
    }

    public ModuleReader open() throws IOException {
        return new ModuleReader(Methods.invoke(METHOD_open, this.delegate, new Object[0]));
    }
}

