package com.mycompany.myapp.videocutter.util;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.video.Video;

import com.mycompany.myapp.VideoUploadModel;
import com.mycompany.myapp.videocutter.PicOutputModel;



/**
 * This class provides utilities to process video.
 */
public class VideoCutterUtil {
	
	public ArrayList<Mat> split(VideoUploadModel videofile) {
		VideoCapture capture = new VideoCapture(videofile.getVideoFile().getOriginalFilename());
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		Mat frame = null;
		int frameNumber = 0;
		String fileName = "";
		ArrayList<Mat> imgList = new ArrayList<Mat>();
		// convert mat frame into buffered image
		while(videofile.getVideoFile().getSize() != 0) {
			capture.read(frame);
			imgList.add(frame);
			frameNumber++;
		}
		return imgList;
	}
	
	public void save(ArrayList<Mat> inArray){
		
		String path = "resources/images";
		for (int i = 0; i< inArray.size(); i++){
			PicOutputModel pic = new PicOutputModel();
			Mat img = inArray.get(i);
			String fileName = "frame" + Integer.toString(i); //not being stored anywhere
			path = path + "/" + fileName;
			pic.setPath(path);
			pic.setFileName(fileName);
			Highgui.imwrite(fileName, img);
			
		}
	}

}
