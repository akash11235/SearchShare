package io.github.akash11235.searchshare;

import com.hierynomus.msfscc.fileinformation.FileIdBothDirectoryInformation;
import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.auth.AuthenticationContext;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.session.Session;
import com.hierynomus.smbj.share.DiskShare;
import com.hierynomus.smbj.share.File;

import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;

public class SmbFIleOperations {
	private Session session;
	private String shareMount;
	private String path;
	private String smbPath;
	private String filename;
	private List<File> fileList;

	public SmbFIleOperations(Session session, String shareMount, String path, String filename){
		this.session = session;
		this.shareMount = shareMount;
		this.path = path;
		this.filename = filename;
	}
	private String getFullName(String fileName){
		return smbPath+"\\"+path+"\\"+fileName;
	}
	public void searchFilesinZip(Session session, String shareMount, String sharedFolder, String zipFilename, String searchFilename) throws Exception {
	// to search for matching filesnames inside a zip
	}

	/*
	* This is the listFiles method which lists all files at sharedFolder matching the file pattern
	* filename. The shared folder must belong to shareMount
   	* @param session : com.hierynomus.smbj.session.Session object
   	* @param shareMount : String
   	* @param sharedFolder : String
   	* @param filename : String
   	*
    * @return Nothing.
 	* @exception Exception On input error.
	* @see Exception
   	*/
	public void listFiles() throws Exception {
        // Connect to Share
		System.out.println("shareMount  :"+shareMount);
		System.out.println("sharedFolder:"+path);
	    System.out.println("filename    :"+filename);
		try (DiskShare share = (DiskShare) session.connectShare(shareMount)) {
			smbPath	= share.getSmbPath().toString();
			System.out.println("Smb Path ="+smbPath);
            for (FileIdBothDirectoryInformation f : share.list(path,filename)) {
                System.out.println(getFullName(f.getFileName()));
			}
        }
    }
}
