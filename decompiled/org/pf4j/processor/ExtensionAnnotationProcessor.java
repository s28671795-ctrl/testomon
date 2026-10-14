/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j.processor;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import org.pf4j.Extension;
import org.pf4j.ExtensionPoint;
import org.pf4j.processor.ExtensionStorage;
import org.pf4j.processor.LegacyExtensionStorage;
import org.pf4j.util.ClassUtils;

public class ExtensionAnnotationProcessor
extends AbstractProcessor {
    private static final String STORAGE_CLASS_NAME = "pf4j.storageClassName";
    private static final String IGNORE_EXTENSION_POINT = "pf4j.ignoreExtensionPoint";
    private Map<String, Set<String>> extensions = new HashMap<String, Set<String>>();
    private Map<String, Set<String>> oldExtensions = new HashMap<String, Set<String>>();
    private ExtensionStorage storage;
    private boolean ignoreExtensionPoint;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.info("%s init", ExtensionAnnotationProcessor.class.getName());
        this.info("Options %s", processingEnv.getOptions());
        this.initStorage();
        this.initIgnoreExtensionPoint();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton("*");
    }

    @Override
    public Set<String> getSupportedOptions() {
        HashSet<String> options = new HashSet<String>();
        options.add(STORAGE_CLASS_NAME);
        options.add(IGNORE_EXTENSION_POINT);
        return options;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            return false;
        }
        this.info("Processing @%s", Extension.class.getName());
        for (Element element : roundEnv.getElementsAnnotatedWith(Extension.class)) {
            if (element.getKind() == ElementKind.ANNOTATION_TYPE) continue;
            this.processExtensionElement(element);
        }
        ArrayList<TypeElement> extensionAnnotations = new ArrayList<TypeElement>();
        for (TypeElement typeElement : annotations) {
            if (ClassUtils.getAnnotationMirror(typeElement, Extension.class) == null) continue;
            extensionAnnotations.add(typeElement);
        }
        for (TypeElement typeElement : extensionAnnotations) {
            this.info("Processing @%s", typeElement);
            for (Element element : roundEnv.getElementsAnnotatedWith(typeElement)) {
                this.processExtensionElement(element);
            }
        }
        this.oldExtensions = this.storage.read();
        for (Map.Entry<String, Set<String>> entry : this.oldExtensions.entrySet()) {
            String extensionPoint = entry.getKey();
            if (this.extensions.containsKey(extensionPoint)) {
                this.extensions.get(extensionPoint).addAll((Collection<String>)entry.getValue());
                continue;
            }
            this.extensions.put(extensionPoint, entry.getValue());
        }
        if (this.extensions.size() > 0) {
            this.storage.write(this.extensions);
        }
        return false;
    }

    public ProcessingEnvironment getProcessingEnvironment() {
        return this.processingEnv;
    }

    public void error(String message, Object ... args2) {
        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, String.format(message, args2));
    }

    public void error(Element element, String message, Object ... args2) {
        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, String.format(message, args2), element);
    }

    public void info(String message, Object ... args2) {
        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, String.format(message, args2));
    }

    public void info(Element element, String message, Object ... args2) {
        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, String.format(message, args2), element);
    }

    public String getBinaryName(TypeElement element) {
        return this.processingEnv.getElementUtils().getBinaryName(element).toString();
    }

    public Map<String, Set<String>> getExtensions() {
        return this.extensions;
    }

    public Map<String, Set<String>> getOldExtensions() {
        return this.oldExtensions;
    }

    public ExtensionStorage getStorage() {
        return this.storage;
    }

    private void processExtensionElement(Element element) {
        if (!(element instanceof TypeElement)) {
            this.error(element, "Put annotation only on classes (no methods, no fields)", new Object[0]);
            return;
        }
        if (!this.ignoreExtensionPoint && !this.isExtension(element.asType())) {
            this.error(element, "%s is not an extension (it doesn't implement ExtensionPoint)", element);
            return;
        }
        TypeElement extensionElement = (TypeElement)element;
        List<TypeElement> extensionPointElements = this.findExtensionPoints(extensionElement);
        if (extensionPointElements.isEmpty()) {
            this.error(element, "No extension points found for extension %s", extensionElement);
            return;
        }
        String extension = this.getBinaryName(extensionElement);
        for (TypeElement extensionPointElement : extensionPointElements) {
            String extensionPoint = this.getBinaryName(extensionPointElement);
            Set extensionPoints = this.extensions.computeIfAbsent(extensionPoint, k -> new TreeSet());
            extensionPoints.add(extension);
        }
    }

    private List<TypeElement> findExtensionPoints(TypeElement extensionElement) {
        List extensionPointClasses;
        ArrayList<TypeElement> extensionPointElements = new ArrayList<TypeElement>();
        AnnotationValue annotatedExtensionPoints = ClassUtils.getAnnotationValue(extensionElement, Extension.class, "points");
        List list = extensionPointClasses = annotatedExtensionPoints != null ? (List)annotatedExtensionPoints.getValue() : null;
        if (extensionPointClasses != null && !extensionPointClasses.isEmpty()) {
            for (AnnotationValue extensionPointClass : extensionPointClasses) {
                String string = extensionPointClass.getValue().toString();
                TypeElement extensionPointElement = this.processingEnv.getElementUtils().getTypeElement(string);
                extensionPointElements.add(extensionPointElement);
            }
        } else {
            boolean bl;
            List<? extends TypeMirror> interfaces = extensionElement.getInterfaces();
            for (TypeMirror typeMirror : interfaces) {
                boolean isExtensionPoint2 = this.processingEnv.getTypeUtils().isSubtype(typeMirror, this.getExtensionPointType());
                if (!isExtensionPoint2) continue;
                extensionPointElements.add(this.getElement(typeMirror));
            }
            TypeMirror superclass = extensionElement.getSuperclass();
            if (superclass.getKind() != TypeKind.NONE && (bl = this.processingEnv.getTypeUtils().isSubtype(superclass, this.getExtensionPointType()))) {
                extensionPointElements.add(this.getElement(superclass));
            }
            if (extensionPointElements.isEmpty() && this.ignoreExtensionPoint) {
                if (interfaces.isEmpty()) {
                    this.error(extensionElement, "Cannot use %s as extension point with %s compiler arg (it doesn't implement any interface)", extensionElement, IGNORE_EXTENSION_POINT);
                } else if (interfaces.size() == 1) {
                    extensionPointElements.add(this.getElement(interfaces.get(0)));
                } else {
                    this.error(extensionElement, "Cannot use %s as extension point with %s compiler arg (it implements multiple interfaces)", extensionElement, IGNORE_EXTENSION_POINT);
                }
            }
        }
        return extensionPointElements;
    }

    private boolean isExtension(TypeMirror typeMirror) {
        return this.processingEnv.getTypeUtils().isAssignable(typeMirror, this.getExtensionPointType());
    }

    private TypeMirror getExtensionPointType() {
        return this.processingEnv.getElementUtils().getTypeElement(ExtensionPoint.class.getName()).asType();
    }

    private void initStorage() {
        String storageClassName = this.processingEnv.getOptions().get(STORAGE_CLASS_NAME);
        if (storageClassName == null) {
            storageClassName = System.getProperty(STORAGE_CLASS_NAME);
        }
        if (storageClassName != null) {
            try {
                Class<?> storageClass = this.getClass().getClassLoader().loadClass(storageClassName);
                Constructor<?> constructor = storageClass.getConstructor(ExtensionAnnotationProcessor.class);
                this.storage = (ExtensionStorage)constructor.newInstance(this);
            }
            catch (Exception e) {
                this.error(e.getMessage(), new Object[0]);
            }
        }
        if (this.storage == null) {
            this.storage = new LegacyExtensionStorage(this);
        }
    }

    private void initIgnoreExtensionPoint() {
        this.ignoreExtensionPoint = this.getProcessingEnvironment().getOptions().containsKey(IGNORE_EXTENSION_POINT) || System.getProperty(IGNORE_EXTENSION_POINT) != null;
    }

    private TypeElement getElement(TypeMirror typeMirror) {
        return (TypeElement)((DeclaredType)typeMirror).asElement();
    }
}

