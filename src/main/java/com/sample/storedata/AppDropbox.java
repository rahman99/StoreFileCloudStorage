package com.sample.storedata;

import com.dropbox.core.*;
import com.dropbox.core.json.JsonReader.FileLoadException;

import java.io.*;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Hello world!
 *
 */
public class AppDropbox 
{
    public static void main( String[] args ) throws IOException, DbxException, FileLoadException
    {
    	final String APP_KEY = "4plxnju6x2aafmk";//"xxxxxxx";
        final String APP_SECRET = "qniph1nndry39um";//"yyyyy";
        String accessToken = "bvt9J-_iprsAAAAAAAAAQt4teZl15ik2Z6KwU_ZvF7J6OuzQThtSHZd8HcPCi9mD";//"zzzzzzzzzzzzzz";
        String fileDropBoxLocation = "/mantest/watest.txt";
        String fileTargetLocation = "/media/rahman/DATA/zDropMan";

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

        /*File inputFile = new File(fileTargetLocation); //file location in local.
        FileInputStream inputStream = new FileInputStream(inputFile);
        try {
            DbxEntry.File uploadedFile = client.uploadFile(fileDropBoxLocation,//file location in dropbox
                DbxWriteMode.add(), inputFile.length(), inputStream);
            System.out.println("Uploaded: " + uploadedFile.toString());
        } finally {
            inputStream.close();
        }*/
        
        //////////////////////////listing/////////////////////////
        /*DbxEntry.WithChildren listing = client.getMetadataWithChildren("/Document");
        System.out.println("Files in the root path:");
        for (DbxEntry child : listing.children) {
            System.out.println("	" + child.name + ": " + child.toString());
        }*/
        DbxEntry.WithChildren listing = null;
        String pathMetaData = "/Document";
//        try {
        	listing = client.getMetadataWithChildren(pathMetaData);
//            System.out.println("Files in the root path:");
//            for (DbxEntry child : listing.children) {
//            	
////                System.out.println("	" + child.name + ": " + child.toString());
////                if(child.isFolder()==true){
////                	pathMetaData = pathMetaData+"/"+child.name;
////                }
//            }
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

        ////////////////download//////////////////////////////
//        FileOutputStream outputStream = new FileOutputStream(fileTargetLocation);
        try {
//            DbxEntry.File downloadedFile = client.getFile(fileDropBoxLocation, null,
//                outputStream);
//            System.out.println("Metadata: " + downloadedFile.toString());
        	for (DbxEntry child : listing.children){
        	      if(child.isFile()==true) {
        	    	  client.getFile(pathMetaData +"/"+ child.name, null, new FileOutputStream(fileTargetLocation+"/"+child.name));
        	    	  System.out.println(child.name);
        	      }
        	      else if(child.isFolder()==true){
        	    	  new File(fileTargetLocation+"/"+child.name).mkdir();
        	    	  fileTargetLocation = fileTargetLocation+"/"+child.name;
        	      }
        	}
        } catch (Exception e) {
			System.err.println("catch execute");
		}
    }
}
