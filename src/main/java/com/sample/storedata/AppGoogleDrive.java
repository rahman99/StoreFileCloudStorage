package com.sample.storedata;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;

public class AppGoogleDrive {

	public static void main(String[] args) {
//		File mediaFile = new File("/tmp/driveFile.jpg");
//		InputStreamContent mediaContent =
//		    new InputStreamContent("image/jpeg",
//		        new BufferedInputStream(new FileInputStream(mediaFile)));
//		mediaContent.setLength(mediaFile.length());
//
//		Drive.Files.Insert request = drive.files().insert(fileMetadata, mediaContent);
//		request.getMediaHttpUploader().setProgressListener(new CustomProgressListener());
//		request.execute();
		
		int cut = new String("/media/rahman/DATA/zDropMan/").length();
		String test = "/media/rahman/DATA/zDropMan/folder2/demows.zip";
		String cutting = test.substring(cut);
		System.out.println(cutting);
	}

}
