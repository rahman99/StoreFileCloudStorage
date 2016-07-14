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
    	final String APP_KEY = "xxxxxxx";
        final String APP_SECRET = "yyyyy";
        String fileDropBoxLocation = "/watest.txt";
        String fileTargetLocation = "/media/rahman/DATA/watest.txt";

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
        String accessToken = "zzzzzzzzzzzzzz";

        DbxClient client = new DbxClient(config, accessToken);

        System.out.println("Linked account: " + client.getAccountInfo().displayName);

        File inputFile = new File(fileTargetLocation); //file location in local.
        FileInputStream inputStream = new FileInputStream(inputFile);
        try {
            DbxEntry.File uploadedFile = client.uploadFile(fileDropBoxLocation,//file location in dropbox
                DbxWriteMode.add(), inputFile.length(), inputStream);
            System.out.println("Uploaded: " + uploadedFile.toString());
        } finally {
            inputStream.close();
        }
        
        
//        DbxEntry.WithChildren listing = client.getMetadataWithChildren("/");
//        System.out.println("Files in the root path:");
//        for (DbxEntry child : listing.children) {
//            System.out.println("	" + child.name + ": " + child.toString());
//        }
//
//        FileOutputStream outputStream = new FileOutputStream("test.txt");
//        try {
//            DbxEntry.File downloadedFile = client.getFile("/media/rahman/DATA/test.txt", null,
//                outputStream);
//            System.out.println("Metadata: " + downloadedFile.toString());
//        } finally {
//            outputStream.close();
//        }
    }
}
