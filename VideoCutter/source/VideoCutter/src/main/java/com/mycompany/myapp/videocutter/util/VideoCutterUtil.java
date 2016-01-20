package com.mycompany.myapp.videocutter.util;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.video.Video;

import com.mycompany.myapp.VideoUploadModel;



/**
 * This class provides utilities to process video.
 */
public class VideoCutterUtil {
	
	public ArrayList<Mat> split(VideoUploadModel videofile) {
		VideoCapture capture = new VideoCapture();
		// System.out.printf("FPS:%d", fps);
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
		ArrayList<Mat> array = new ArrayList<Mat>();
		String path = "resources/images";
		for (int i = 0; i< inArray.size(); i++){
			Mat img = array.get(i);
			String fileName = "frame" + Integer.toString(i); //not being stored anywhere
			path = path + "/" + fileName;
			Highgui.imwrite(fileName, img);
		}
	}

}
