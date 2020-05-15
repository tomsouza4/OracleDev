import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;

public class UpdateAttrOID {
    
    public static void main(String[] argv) throws Exception {
        DirContext ctx = null;

        try{
            ModificationItem[] mods = new ModificationItem[1];
        
            String attributeName = "uniquemember";
            String updateAttribute = " "; 
            String baseObject = "cn=XMLP_SCHEDULER,ou=groups,dc=example,dc=com";
            String user = "cn=Directory Manager";
            String passwd = "Oracle123";
            String url = "ldap://identity.oracleads.com:2389";

            if(!updateAttribute.trim().isEmpty() && updateAttribute != null){                
                mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, 
                                                    new BasicAttribute(attributeName, updateAttribute));                                                             
            }else{
                throw new Exception("updateAttribute is a space or blank or null");
            }    
            
            Hashtable<String, String> env = new Hashtable<String, String>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, url);
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, user);
            env.put(Context.SECURITY_CREDENTIALS, passwd);
        
            ctx = new InitialDirContext(env);
            
            ctx.modifyAttributes(baseObject, mods);
            ctx.close();

        } catch (NamingException e) {
            e.printStackTrace();
            ctx.close();
        } finally {
            if (ctx != null) {
                    try {
                            ctx.close();
                    } catch (NamingException e) {

                            e.printStackTrace();
                    }
            }
        }        
    }
}