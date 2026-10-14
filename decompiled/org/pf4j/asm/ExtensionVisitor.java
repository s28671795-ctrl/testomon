/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j.asm;

import java.util.Arrays;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Type;
import org.pf4j.Extension;
import org.pf4j.asm.ExtensionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ExtensionVisitor
extends ClassVisitor {
    private static final Logger log = LoggerFactory.getLogger(ExtensionVisitor.class);
    private static final int ASM_VERSION = 458752;
    private final ExtensionInfo extensionInfo;

    ExtensionVisitor(ExtensionInfo extensionInfo) {
        super(458752);
        this.extensionInfo = extensionInfo;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        if (!Type.getType(descriptor).getClassName().equals(Extension.class.getName())) {
            return super.visitAnnotation(descriptor, visible);
        }
        return new AnnotationVisitor(458752){

            @Override
            public AnnotationVisitor visitArray(final String name) {
                if ("ordinal".equals(name) || "plugins".equals(name) || "points".equals(name)) {
                    return new AnnotationVisitor(458752, super.visitArray(name)){

                        @Override
                        public void visit(String key, Object value) {
                            log.debug("Load annotation attribute {} = {} ({})", name, value, value.getClass().getName());
                            if ("ordinal".equals(name)) {
                                ((ExtensionVisitor)ExtensionVisitor.this).extensionInfo.ordinal = Integer.parseInt(value.toString());
                            } else if ("plugins".equals(name)) {
                                if (value instanceof String) {
                                    log.debug("Found plugin {}", value);
                                    ((ExtensionVisitor)ExtensionVisitor.this).extensionInfo.plugins.add((String)value);
                                } else if (value instanceof String[]) {
                                    log.debug("Found plugins {}", (Object)Arrays.toString((String[])value));
                                    ((ExtensionVisitor)ExtensionVisitor.this).extensionInfo.plugins.addAll(Arrays.asList((String[])value));
                                } else {
                                    log.debug("Found plugin {}", (Object)value.toString());
                                    ((ExtensionVisitor)ExtensionVisitor.this).extensionInfo.plugins.add(value.toString());
                                }
                            } else {
                                String pointClassName = ((Type)value).getClassName();
                                log.debug("Found point " + pointClassName);
                                ((ExtensionVisitor)ExtensionVisitor.this).extensionInfo.points.add(pointClassName);
                            }
                            super.visit(key, value);
                        }
                    };
                }
                return super.visitArray(name);
            }
        };
    }
}

