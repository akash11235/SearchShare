package io.github.akash11235.searchshare;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PatchSearcher {
//first write a prototype which can list the files from a shared folder 
	final static boolean prototype=true;
	final static String[] filedMapping = {"Domain","UserId", "Password", "shared_path", "filename"};
	static String domain,userId, password,shared_path, filename[];
	Properties prop = new Properties();
	UserSmbCredential uc;
	SmbConnection sc;
	SmbFIleOperations sfo;
	public PatchSearcher() throws FileNotFoundException {
		//initialize the properties map
		InputStream in = PatchSearcher.class.getClassLoader().getResourceAsStream("config.properties");
		if(in != null){
			try {
				this.prop.load(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else throw new FileNotFoundException("property file 'config.properties' not found in the classpath");
    }
	public static void printFields(String[] args){
		int i = 0;
		for(String s : args) {
			System.out.println(filedMapping[i]+"="+s);
			i++;
		}
	}
	
	public static void main(String[] args) throws Exception{
		/*
		 * args[0] : domain
		 * args[1] : userId
		 * args[2] : password
		 * args[3] : shared path
		 * args[4] : filename
		 * 
		 */
		if(args.length == 0) {
			System.out.println("Usage:");
			System.out.println("PatchSearcher <Domain>* <UserId>* <Password>* <shared_path>* [<filename>]");
			System.out.println("* marked fields are mandatory");
			System.out.println("filename is not mandatory. if none is provided *.* will be taken");

			if(!prototype)
				System.exit(0);
		}
		else if(args.length < 4 ) {
			// start validating the mandatory fields 
			System.out.println("Not enough mandatory fields..");
			printFields(args);
			System.exit(-1);
		}
		else {
			printFields(args);
			domain = args[0];
			userId = args[1]; 
			password = args[2];
			shared_path = args[3];
			if (args.length == 4) {
				filename = new String[1];
				filename[0] = "*.*";
			}
			else {
				filename = new String[args.length - 4];
				for(int j = 0; j < args.length - 4; j++)
					filename[j]=args[5+j];
			}
			
		}

		PatchSearcher ps = new PatchSearcher();

		filename = new String[1];
		filename[0] = "*.*";
		if (args.length == 0) {
			domain      = ps.prop.getProperty("domain");
			userId      = ps.prop.getProperty("userId");
			password    = ps.prop.getProperty("password");
			shared_path = ps.prop.getProperty("shared_path");
		}

		ps.uc = new UserSmbCredential(domain,
				                      userId,
				                      password);

		String ip = shared_path.substring(2, shared_path.indexOf("\\",2));
		int share_sidx = shared_path.indexOf("\\",2)+1;
		int share_eidx = shared_path.indexOf("\\",share_sidx); 
		String share = shared_path.substring(share_sidx, share_eidx);
		String path = shared_path.substring(share_eidx+1,shared_path.length());
		ps.sc = new SmbConnection();
		ps.sc.establishConnection(ip,ps.uc, true);
		ps.sfo = new SmbFIleOperations(ps.sc.getSession(),share,path,filename[0]);
		ps.sfo.listFiles();
		//done with the operations. now time to call close
		ps.sc.getSession().close();
		ps.sc.getConnection().close();
	}
		
}
