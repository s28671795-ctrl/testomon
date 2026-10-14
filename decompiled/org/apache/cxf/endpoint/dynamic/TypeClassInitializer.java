/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.endpoint.dynamic;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.jaxb.JAXBUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.ASMHelper;
import org.apache.cxf.common.util.PrimitiveUtils;
import org.apache.cxf.service.ServiceModelVisitor;
import org.apache.cxf.service.factory.ServiceConstructionException;
import org.apache.cxf.service.model.FaultInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.service.model.UnwrappedOperationInfo;
import org.apache.ws.commons.schema.XmlSchemaElement;

public class TypeClassInitializer
extends ServiceModelVisitor {
    private static final Logger LOG = LogUtils.getL7dLogger(TypeClassInitializer.class);
    JAXBUtils.S2JJAXBModel model;
    boolean allowWrapperOperations;
    boolean isFault;

    public TypeClassInitializer(ServiceInfo serviceInfo, JAXBUtils.S2JJAXBModel model, boolean allowWr) {
        super(serviceInfo);
        this.model = model;
        this.allowWrapperOperations = allowWr;
    }

    @Override
    public void begin(MessagePartInfo part) {
        Class<?> cls;
        JAXBUtils.TypeAndAnnotation typeAndAnnotation;
        OperationInfo op = part.getMessageInfo().getOperation();
        if (!this.isFault && !this.allowWrapperOperations && op.isUnwrappedCapable() && !op.isUnwrapped()) {
            return;
        }
        QName name = part.isElement() ? part.getElementQName() : part.getTypeQName();
        JAXBUtils.Mapping mapping = this.model.get(name);
        JAXBUtils.JType jType = null;
        if (mapping != null) {
            jType = mapping.getType().getTypeClass();
        }
        if (jType == null && (typeAndAnnotation = this.model.getJavaType(part.getTypeQName())) != null) {
            jType = typeAndAnnotation.getTypeClass();
        }
        if (jType == null && part.isElement() && part.getXmlSchema() instanceof XmlSchemaElement && ((XmlSchemaElement)part.getXmlSchema()).getSchemaTypeName() == null) {
            UnwrappedOperationInfo oInfo = (UnwrappedOperationInfo)op;
            op = oInfo.getWrappedOperation();
            mapping = part.getMessageInfo() == oInfo.getInput() ? this.model.get(op.getInput().getFirstMessagePart().getElementQName()) : this.model.get(op.getOutput().getFirstMessagePart().getElementQName());
            if (mapping != null) {
                jType = mapping.getType().getTypeClass();
                try {
                    Iterator<JAXBUtils.JType> i = jType.classes();
                    while (i.hasNext()) {
                        JAXBUtils.JType jt = i.next();
                        if (!jt.name().equalsIgnoreCase(part.getElementQName().getLocalPart())) continue;
                        jType = jt;
                    }
                }
                catch (Throwable i) {
                    // empty catch block
                }
            }
        }
        if (jType == null) {
            throw new ServiceConstructionException(new Message("NO_JAXB_CLASSMapping", LOG, name));
        }
        try {
            int arrayCount = 0;
            JAXBUtils.JType rootType = jType;
            while (rootType.isArray()) {
                rootType = rootType.elementType();
                ++arrayCount;
            }
            if (arrayCount == 0 && part.isElement() && part.getXmlSchema() instanceof XmlSchemaElement && ((XmlSchemaElement)part.getXmlSchema()).getMaxOccurs() > 1L) {
                arrayCount = 1;
            }
            cls = this.getClassByName(rootType);
            if (arrayCount > 0) {
                int[] dimensions = new int[arrayCount];
                while (arrayCount > 0) {
                    dimensions[--arrayCount] = 0;
                }
                Object emptyArray = Array.newInstance(cls, dimensions);
                cls = emptyArray.getClass();
            }
        }
        catch (ClassNotFoundException e) {
            throw new ServiceConstructionException(e);
        }
        part.setTypeClass(cls);
        if (this.isFault) {
            try {
                part.getMessageInfo().setProperty(Class.class.getName(), this.createFaultClass(cls));
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        super.begin(part);
    }

    private Class<?> createFaultClass(Class<?> cls) {
        return new ExceptionCreator().createExceptionClass(cls);
    }

    private Class<?> getClassByName(JAXBUtils.JType jType) throws ClassNotFoundException {
        Class<?> cls = !jType.isPrimitive() ? ClassLoaderUtils.loadClass(jType.binaryName(), this.getClass()) : PrimitiveUtils.getClass(jType.fullName());
        return cls;
    }

    @Override
    public void begin(FaultInfo fault) {
        this.isFault = true;
    }

    @Override
    public void end(FaultInfo fault) {
        this.isFault = false;
    }

    private static class ExceptionCreator
    extends ASMHelper {
        private ExceptionCreator() {
        }

        public Class<?> createExceptionClass(Class<?> bean) {
            String newClassName = bean.getName() + "_Exception";
            newClassName = newClassName.replaceAll("\\$", ".");
            Class<?> cls = super.findClass((newClassName = ExceptionCreator.periodToSlashes(newClassName)).replace('/', '.'), bean);
            if (cls == null) {
                ASMHelper.ClassWriter cw = this.createClassWriter();
                cw.visit(ASMHelper.Opcodes.V1_5, ASMHelper.Opcodes.ACC_PUBLIC | ASMHelper.Opcodes.ACC_SUPER, newClassName, null, "java/lang/Exception", null);
                String beanClassCode = ExceptionCreator.getClassCode(bean);
                ASMHelper.FieldVisitor fv = cw.visitField(0, "faultInfo", beanClassCode, null, null);
                fv.visitEnd();
                ASMHelper.MethodVisitor mv = cw.visitMethod(ASMHelper.Opcodes.ACC_PUBLIC, "<init>", "(Ljava/lang/String;" + beanClassCode + ")V", null, null);
                mv.visitCode();
                mv.visitLabel(this.createLabel());
                mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 0);
                mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 1);
                mv.visitMethodInsn(ASMHelper.Opcodes.INVOKESPECIAL, "java/lang/Exception", "<init>", "(Ljava/lang/String;)V", false);
                mv.visitLabel(this.createLabel());
                mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 0);
                mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 2);
                mv.visitFieldInsn(ASMHelper.Opcodes.PUTFIELD, newClassName, "faultInfo", beanClassCode);
                mv.visitLabel(this.createLabel());
                mv.visitInsn(ASMHelper.Opcodes.RETURN);
                mv.visitLabel(this.createLabel());
                mv.visitMaxs(2, 3);
                mv.visitEnd();
                mv = cw.visitMethod(ASMHelper.Opcodes.ACC_PUBLIC, "getFaultInfo", "()" + beanClassCode, null, null);
                mv.visitCode();
                mv.visitLabel(this.createLabel());
                mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 0);
                mv.visitFieldInsn(ASMHelper.Opcodes.GETFIELD, newClassName, "faultInfo", beanClassCode);
                mv.visitInsn(ASMHelper.Opcodes.ARETURN);
                mv.visitLabel(this.createLabel());
                mv.visitMaxs(1, 1);
                mv.visitEnd();
                cw.visitEnd();
                return super.loadClass(bean.getName() + "_Exception", bean, cw.toByteArray());
            }
            return cls;
        }
    }
}

