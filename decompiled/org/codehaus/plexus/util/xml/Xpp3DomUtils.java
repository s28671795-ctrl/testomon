/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.util.xml;

import java.io.IOException;
import java.util.HashMap;
import org.codehaus.plexus.util.xml.SerializerXMLWriter;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.codehaus.plexus.util.xml.Xpp3DomWriter;
import org.codehaus.plexus.util.xml.pull.XmlSerializer;

public class Xpp3DomUtils {
    public static final String CHILDREN_COMBINATION_MODE_ATTRIBUTE = "combine.children";
    public static final String CHILDREN_COMBINATION_MERGE = "merge";
    public static final String CHILDREN_COMBINATION_APPEND = "append";
    public static final String DEFAULT_CHILDREN_COMBINATION_MODE = "merge";
    public static final String SELF_COMBINATION_MODE_ATTRIBUTE = "combine.self";
    public static final String SELF_COMBINATION_OVERRIDE = "override";
    public static final String SELF_COMBINATION_MERGE = "merge";
    public static final String ID_COMBINATION_MODE_ATTRIBUTE = "combine.id";
    public static final String KEYS_COMBINATION_MODE_ATTRIBUTE = "combine.keys";
    public static final String DEFAULT_SELF_COMBINATION_MODE = "merge";

    public void writeToSerializer(String namespace, XmlSerializer serializer, Xpp3Dom dom) throws IOException {
        SerializerXMLWriter xmlWriter = new SerializerXMLWriter(namespace, serializer);
        Xpp3DomWriter.write(xmlWriter, dom);
        if (xmlWriter.getExceptions().size() > 0) {
            throw (IOException)xmlWriter.getExceptions().get(0);
        }
    }

    private static void mergeIntoXpp3Dom(Xpp3Dom dominant, Xpp3Dom recessive, Boolean childMergeOverride) {
        if (recessive == null) {
            return;
        }
        boolean mergeSelf = true;
        String selfMergeMode = dominant.getAttribute(SELF_COMBINATION_MODE_ATTRIBUTE);
        if (Xpp3DomUtils.isNotEmpty(selfMergeMode) && SELF_COMBINATION_OVERRIDE.equals(selfMergeMode)) {
            mergeSelf = false;
        }
        if (mergeSelf) {
            Xpp3Dom[] children;
            String[] recessiveAttrs;
            if (Xpp3DomUtils.isEmpty(dominant.getValue()) && !Xpp3DomUtils.isEmpty(recessive.getValue())) {
                dominant.setValue(recessive.getValue());
                dominant.setInputLocation(recessive.getInputLocation());
            }
            for (String attr : recessiveAttrs = recessive.getAttributeNames()) {
                if (!Xpp3DomUtils.isEmpty(dominant.getAttribute(attr))) continue;
                dominant.setAttribute(attr, recessive.getAttribute(attr));
            }
            boolean mergeChildren = true;
            if (childMergeOverride != null) {
                mergeChildren = childMergeOverride;
            } else {
                String childMergeMode = dominant.getAttribute(CHILDREN_COMBINATION_MODE_ATTRIBUTE);
                if (Xpp3DomUtils.isNotEmpty(childMergeMode) && CHILDREN_COMBINATION_APPEND.equals(childMergeMode)) {
                    mergeChildren = false;
                }
            }
            String keysValue = recessive.getAttribute(KEYS_COMBINATION_MODE_ATTRIBUTE);
            for (Xpp3Dom recessiveChild : children = recessive.getChildren()) {
                String idValue = recessiveChild.getAttribute(ID_COMBINATION_MODE_ATTRIBUTE);
                Object childDom = null;
                if (Xpp3DomUtils.isNotEmpty(idValue)) {
                    for (Xpp3Dom dominantChild : dominant.getChildren()) {
                        if (!idValue.equals(dominantChild.getAttribute(ID_COMBINATION_MODE_ATTRIBUTE))) continue;
                        childDom = dominantChild;
                        mergeChildren = true;
                    }
                } else if (Xpp3DomUtils.isNotEmpty(keysValue)) {
                    String[] keys2 = keysValue.split(",");
                    HashMap<String, String> recessiveKeyValues = new HashMap<String, String>(keys2.length);
                    for (String key : keys2) {
                        recessiveKeyValues.put(key, recessiveChild.getAttribute(key));
                    }
                    for (Xpp3Dom dominantChild : dominant.getChildren()) {
                        HashMap<String, String> dominantKeyValues = new HashMap<String, String>(keys2.length);
                        for (String key : keys2) {
                            dominantKeyValues.put(key, dominantChild.getAttribute(key));
                        }
                        if (!recessiveKeyValues.equals(dominantKeyValues)) continue;
                        childDom = dominantChild;
                        mergeChildren = true;
                    }
                } else {
                    childDom = dominant.getChild(recessiveChild.getName());
                }
                if (mergeChildren && childDom != null) {
                    Xpp3DomUtils.mergeIntoXpp3Dom((Xpp3Dom)childDom, recessiveChild, childMergeOverride);
                    continue;
                }
                dominant.addChild(new Xpp3Dom(recessiveChild));
            }
        }
    }

    public static Xpp3Dom mergeXpp3Dom(Xpp3Dom dominant, Xpp3Dom recessive, Boolean childMergeOverride) {
        if (dominant != null) {
            Xpp3DomUtils.mergeIntoXpp3Dom(dominant, recessive, childMergeOverride);
            return dominant;
        }
        return recessive;
    }

    public static Xpp3Dom mergeXpp3Dom(Xpp3Dom dominant, Xpp3Dom recessive) {
        if (dominant != null) {
            Xpp3DomUtils.mergeIntoXpp3Dom(dominant, recessive, null);
            return dominant;
        }
        return recessive;
    }

    public static boolean isNotEmpty(String str) {
        return str != null && str.length() > 0;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
}

