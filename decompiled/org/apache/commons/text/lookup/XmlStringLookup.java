/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.text.lookup;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.xml.xpath.XPathFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.lookup.AbstractStringLookup;
import org.apache.commons.text.lookup.IllegalArgumentExceptions;
import org.xml.sax.InputSource;

final class XmlStringLookup
extends AbstractStringLookup {
    private static final Map<String, Boolean> DEFAULT_FEATURES = new HashMap<String, Boolean>(1);
    static final XmlStringLookup INSTANCE;
    private final Map<String, Boolean> xPathFactoryFeatures;

    XmlStringLookup(Map<String, Boolean> xPathFactoryFeatures) {
        this.xPathFactoryFeatures = Objects.requireNonNull(xPathFactoryFeatures, "xPathFfactoryFeatures");
    }

    @Override
    public String lookup(String key) {
        String string;
        block11: {
            if (key == null) {
                return null;
            }
            String[] keys2 = key.split(SPLIT_STR);
            int keyLen = keys2.length;
            if (keyLen != 2) {
                throw IllegalArgumentExceptions.format("Bad XML key format [%s]; expected format is DocumentPath:XPath.", key);
            }
            String documentPath = keys2[0];
            String xpath = StringUtils.substringAfter(key, 58);
            InputStream inputStream2 = Files.newInputStream(Paths.get(documentPath, new String[0]), new OpenOption[0]);
            try {
                XPathFactory factory2 = XPathFactory.newInstance();
                for (Map.Entry<String, Boolean> p : this.xPathFactoryFeatures.entrySet()) {
                    factory2.setFeature(p.getKey(), p.getValue());
                }
                string = factory2.newXPath().evaluate(xpath, new InputSource(inputStream2));
                if (inputStream2 == null) break block11;
            }
            catch (Throwable throwable) {
                try {
                    if (inputStream2 != null) {
                        try {
                            inputStream2.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (Exception e) {
                    throw IllegalArgumentExceptions.format(e, "Error looking up XML document [%s] and XPath [%s].", documentPath, xpath);
                }
            }
            inputStream2.close();
        }
        return string;
    }

    static {
        DEFAULT_FEATURES.put("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE);
        INSTANCE = new XmlStringLookup(DEFAULT_FEATURES);
    }
}

