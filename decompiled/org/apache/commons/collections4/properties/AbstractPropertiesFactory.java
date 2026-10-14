/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.collections4.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public abstract class AbstractPropertiesFactory<T extends Properties> {
    protected AbstractPropertiesFactory() {
    }

    protected abstract T createProperties();

    public T load(ClassLoader classLoader, String name) throws IOException {
        try (InputStream inputStream2 = classLoader.getResourceAsStream(name);){
            T t2 = this.load(inputStream2);
            return t2;
        }
    }

    public T load(File file) throws FileNotFoundException, IOException {
        try (FileInputStream inputStream2 = new FileInputStream(file);){
            T t2 = this.load(inputStream2);
            return t2;
        }
    }

    public T load(InputStream inputStream2) throws IOException {
        if (inputStream2 == null) {
            return null;
        }
        T properties = this.createProperties();
        ((Properties)properties).load(inputStream2);
        return properties;
    }

    public T load(Path path) throws IOException {
        try (InputStream inputStream2 = Files.newInputStream(path, new OpenOption[0]);){
            T t2 = this.load(inputStream2);
            return t2;
        }
    }

    public T load(Reader reader) throws IOException {
        T properties = this.createProperties();
        ((Properties)properties).load(reader);
        return properties;
    }

    public T load(String name) throws IOException {
        try (FileInputStream inputStream2 = new FileInputStream(name);){
            T t2 = this.load(inputStream2);
            return t2;
        }
    }

    public T load(URI uri) throws IOException {
        return this.load(Paths.get(uri));
    }

    public T load(URL url2) throws IOException {
        try (InputStream inputStream2 = url2.openStream();){
            T t2 = this.load(inputStream2);
            return t2;
        }
    }
}

