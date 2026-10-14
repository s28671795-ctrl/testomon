/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.core.jackson;

import com.fasterxml.jackson.databind.introspect.Annotated;
import io.swagger.v3.core.util.AnnotationsUtils;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.MapSchema;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.XML;
import java.lang.annotation.Annotation;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import org.apache.commons.lang3.StringUtils;

class JAXBAnnotationsHelper {
    public static final String JAXB_DEFAULT = "##default";

    private JAXBAnnotationsHelper() {
    }

    public static void apply(Annotated member, Annotation[] annotations, Schema property) {
        XmlElement elem;
        XmlAttribute attr;
        XmlElementWrapper wrapper = member.getAnnotation(XmlElementWrapper.class);
        if (wrapper == null) {
            wrapper = AnnotationsUtils.getAnnotation(XmlElementWrapper.class, annotations);
        }
        if ((attr = member.getAnnotation(XmlAttribute.class)) == null) {
            attr = AnnotationsUtils.getAnnotation(XmlAttribute.class, annotations);
        }
        if ((elem = member.getAnnotation(XmlElement.class)) == null) {
            elem = AnnotationsUtils.getAnnotation(XmlElement.class, annotations);
        }
        if (wrapper != null) {
            JAXBAnnotationsHelper.applyElement(wrapper, property);
        } else if (elem != null) {
            JAXBAnnotationsHelper.applyElement(elem, property);
        } else if (attr != null && JAXBAnnotationsHelper.isAttributeAllowed(property)) {
            JAXBAnnotationsHelper.applyAttribute(attr, property);
        }
    }

    private static void applyElement(XmlElementWrapper wrapper, Schema property) {
        if (wrapper != null) {
            XML xml = JAXBAnnotationsHelper.getXml(property);
            xml.setWrapped(true);
            if (!(JAXB_DEFAULT.equals(wrapper.name()) || wrapper.name().isEmpty() || wrapper.name().equals(property.getName()))) {
                xml.setName(wrapper.name());
            }
        }
    }

    private static void applyElement(XmlElement element, Schema property) {
        if (element != null) {
            JAXBAnnotationsHelper.setName(element.namespace(), element.name(), property);
        }
    }

    private static void applyAttribute(XmlAttribute attribute, Schema property) {
        if (attribute != null) {
            XML xml = JAXBAnnotationsHelper.getXml(property);
            xml.setAttribute(true);
            JAXBAnnotationsHelper.setName(attribute.namespace(), attribute.name(), property);
        }
    }

    private static XML getXml(Schema property) {
        XML existing = property.getXml();
        if (existing != null) {
            return existing;
        }
        XML created = new XML();
        property.setXml(created);
        return created;
    }

    private static boolean setName(String ns, String name, Schema property) {
        String useNS;
        String useName;
        boolean apply2 = false;
        String cleanName = StringUtils.trimToNull(name);
        if (!JAXBAnnotationsHelper.isEmpty(cleanName) && !cleanName.equals(property.getName())) {
            useName = cleanName;
            apply2 = true;
        } else {
            useName = null;
        }
        String cleanNS = StringUtils.trimToNull(ns);
        if (!JAXBAnnotationsHelper.isEmpty(cleanNS)) {
            useNS = cleanNS;
            apply2 = true;
        } else {
            useNS = null;
        }
        if (apply2) {
            JAXBAnnotationsHelper.getXml(property).name(useName).namespace(useNS);
        }
        return apply2;
    }

    private static boolean isAttributeAllowed(Schema property) {
        for (Class item : new Class[]{ArraySchema.class, MapSchema.class, ObjectSchema.class}) {
            if (!item.isInstance(property)) continue;
            return false;
        }
        return StringUtils.isBlank(property.get$ref());
    }

    private static boolean isEmpty(String name) {
        return StringUtils.isEmpty(name) || JAXB_DEFAULT.equals(name);
    }
}

