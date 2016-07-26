package com.sample.storedata;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

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
		try {
			URL website = new URL("https://en.wikipedia.org/wiki/Non-blocking_I/O_(Java)");
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = new FileOutputStream("/media/rahman/DATA/zDropMan/wiki.html");
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
