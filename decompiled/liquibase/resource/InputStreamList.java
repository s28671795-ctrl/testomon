/*
 * Decompiled with CFR 0.152.
 */
package liquibase.resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;
import liquibase.Scope;
import liquibase.logging.Logger;

public class InputStreamList
implements Iterable<InputStream>,
AutoCloseable {
    private final LinkedHashMap<URI, InputStream> streams = new LinkedHashMap();

    public InputStreamList() {
    }

    public InputStreamList(URI uri, InputStream stream) {
        this.streams.put(uri, stream);
    }

    public boolean add(URI uri, InputStream inputStream2) {
        Logger log = Scope.getCurrentScope().getLog(this.getClass());
        boolean duplicate = this.alreadySaw(uri);
        if (duplicate) {
            log.fine("Closing duplicate stream for " + uri);
            try {
                inputStream2.close();
            }
            catch (IOException e) {
                log.warning("Cannot close stream for " + uri, e);
            }
        } else {
            this.streams.put(uri, inputStream2);
        }
        return duplicate;
    }

    protected boolean alreadySaw(URI uri) {
        if (this.streams.isEmpty()) {
            return false;
        }
        if (this.streams.containsKey(uri)) {
            return true;
        }
        String thisUriStringBase = uri.toString().replaceFirst("^file://", "").replaceFirst("^jar:file:", "").replaceFirst("!/", "!");
        for (URI seenURI : this.streams.keySet()) {
            if (!seenURI.toString().replaceFirst("^file://", "").replaceFirst("^jar:file:", "").replaceFirst("!/", "!").equals(thisUriStringBase)) continue;
            return true;
        }
        return false;
    }

    public void addAll(InputStreamList streams) {
        if (streams == null) {
            return;
        }
        for (Map.Entry<URI, InputStream> entry : streams.streams.entrySet()) {
            this.add(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void close() throws IOException {
        for (InputStream stream : this) {
            try {
                stream.close();
            }
            catch (IOException e) {
                Scope.getCurrentScope().getLog(this.getClass()).severe("Error closing stream. Logging error and continuing", e);
            }
        }
    }

    @Override
    public Iterator<InputStream> iterator() {
        return this.streams.values().iterator();
    }

    @Override
    public void forEach(Consumer<? super InputStream> action) {
        this.streams.values().forEach(action);
    }

    @Override
    public Spliterator<InputStream> spliterator() {
        return this.streams.values().spliterator();
    }

    public int size() {
        return this.streams.size();
    }

    public boolean isEmpty() {
        return this.streams.isEmpty();
    }

    public List<URI> getURIs() {
        return new ArrayList<URI>(this.streams.keySet());
    }
}

