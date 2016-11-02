package com.telstra.weblm.weblmagent;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public class SimpleTransformer implements ClassFileTransformer {

	public SimpleTransformer() {
		super();
	}

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		if (className.equalsIgnoreCase("com/avaya/weblm/k/b")) {
			ClassPool pool = ClassPool.getDefault();
			CtClass cl = null;
			try {
				cl = pool.makeClass(new ByteArrayInputStream(classfileBuffer));
				CtMethod[] methods = cl.getDeclaredMethods();
				for (int i = 0; i < methods.length; i++) {
					String name = (methods[i]).getName();
					if (name.equalsIgnoreCase("d")) {
						changeMethod(methods[i]);
					}
				}
				return cl.toBytecode();
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
		}
		return classfileBuffer;
	}

	private void changeMethod(CtMethod method) {
		try {
			String retType = method.getReturnType().getName();
			if (retType.contains("String")) {
				method.setBody("return System.getProperty(\"macAddress\",\"000C29294A2B\");");
			} else {
			}
		} catch (NotFoundException e) {
			e.printStackTrace(System.out);
		} catch (CannotCompileException e) {
			e.printStackTrace(System.out);
		}
	}
}
