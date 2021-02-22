package io.github.akash11235.searchshare;

import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.auth.AuthenticationContext;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.session.Session;

public class SmbConnection {
	private Session session = null;

	private Connection connection;
	
	public Session getSession() {
		return session;
	}
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	
	public Session establishConnection(String hostname_ip, UserSmbCredential usc, boolean forceConnect) throws Exception{
		if (session == null || forceConnect) {
			SMBClient client = new SMBClient();
			try {
				this.connection = client.connect(hostname_ip);
		        AuthenticationContext ac = new AuthenticationContext(usc.getUserId(),
		        		usc.getPassword().toCharArray(),
		        		usc.getDomain());
		        this.session = connection.authenticate(ac);

			} finally {
//				connection.close();
				System.out.println("In finally of SmbConnection.establishConnection");
			}
		}
		return this.session;
	}


}
