package com.sample.storedata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;
import com.dropbox.core.DbxWriteMode;

public class DropboxApp {
	
	private static String fileDropBoxLocation = "/mantest2";
    private static final String fileTargetLocation = "/media/rahman/DATA/zDropMan";
    
    private static final String accessToken = "bvt9J-_iprsAAAAAAAAAQt4teZl15ik2Z6KwU_ZvF7J6OuzQThtSHZd8HcPCi9mD";//"zzzzzzzzzzzzzz";
    

	public static void main(String[] args) throws DbxException {
		final String APP_KEY = "4plxnju6x2aafmk";//"xxxxxxx";
	    final String APP_SECRET = "qniph1nndry39um";//"yyyyy";
		

        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

        DbxRequestConfig config = new DbxRequestConfig("text-edit/0.1",
            Locale.getDefault().toString());
        DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);
        
        // Have the user sign in and authorize your app.
        String authorizeUrl = webAuth.start();
//        System.out.println("1. Go to: " + authorizeUrl);
//        System.out.println("2. Click \"Allow\" (you might have to log in first)");
//        System.out.println("3. Copy the authorization code.");
//        String code = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
//        String code = "bvt9J-_iprsAAAAAAAAAMrS2DX7j-VPS34RYgNDOGUIdKGR26SI1le8UOZNUuB1x";

        // This will fail if the user enters an invalid authorization code.
//        DbxAuthFinish authFinish = webAuth.finish(code);
//        String accessToken = authFinish.accessToken;

        DbxClient client = new DbxClient(config, accessToken);

        System.out.println("Linked account: " + client.getAccountInfo().displayName);

        ///////upload folder/////////
//        uploadFolder(client);
        //////////////////////////listing/////////////////////////
        System.out.println("Files in the root path:");
        listFile(client);
        

        ////////////////download//////////////////////////////
//        downloadFile(client, fileDropBoxLocation, fileTargetLocation);
	}
	
	private static List<String> listFilesForFolder(final File folder) {
		List<String> result = new ArrayList<String>();
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            result.addAll(listFilesForFolder(fileEntry));
	        } else if (fileEntry.isFile()){
//	            System.out.println(fileEntry.getName()+" : "+fileEntry.getPath());
	        	result.add(fileEntry.getPath());
	        }
	    }
	    return result;
	}
	
	private static void uploadFolder(DbxClient client){
		final File folder = new File(fileTargetLocation);
        int lengthpathTarget = new String(fileTargetLocation).length();
        for(String path : listFilesForFolder(folder)){
        	File inputFile = new File(path);
        	path = path.substring(lengthpathTarget);
        	uploadFile(client, inputFile, fileDropBoxLocation + path);
        }
	}
	private static void uploadFile(DbxClient client, File inputFile, String fileDropBoxLocation){
        FileInputStream inputStream = null;
        try {
        	inputStream = new FileInputStream(inputFile);
            DbxEntry.File uploadedFile = client.uploadFile(fileDropBoxLocation,//file location in dropbox
                DbxWriteMode.add(), inputFile.length(), inputStream);
            System.out.println("Uploaded: " + uploadedFile.toString());
        } catch (IOException | DbxException e) {
			e.printStackTrace();
		} finally {
            try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
	
	private static void listFile(DbxClient client){
		try {
			DbxEntry.WithChildren listing = client.getMetadataWithChildren(fileDropBoxLocation);
            for (DbxEntry child : listing.children) {
            	if(child.isFile()==true)
            		System.out.println("	" + child.name + ": " +child.path+" ; "+ child.toString());
            	else if (child.isFolder()==true){
            		fileDropBoxLocation = child.path;
//                	System.out.println("	" + child.name + ": " +child.path);
                	listFile(client);
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void downloadFile(DbxClient client, String fileDropBoxLocation, String fileTargetLocation){
		try {
			DbxEntry.WithChildren listing = client.getMetadataWithChildren(fileDropBoxLocation);
			for(DbxEntry child : listing.children){
        		String fileTargetName = fileTargetLocation+"/"+child.name;
        	      if(child.isFile()==true) {
        	    	  client.getFile(child.path, null, new FileOutputStream(fileTargetName));
        	    	  System.out.println(child.name);
        	      }
        	      else if(child.isFolder()==true){
        	    	  new File(fileTargetName).mkdir();
        	    	  downloadFile(client, child.path, fileTargetName);
        	      }
			}
		} catch (DbxException | IOException e) {
			e.printStackTrace();
		} 
	}

}
