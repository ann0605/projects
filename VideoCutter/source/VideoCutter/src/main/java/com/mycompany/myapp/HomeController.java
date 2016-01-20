package com.mycompany.myapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.opencv.core.Mat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.myapp.videocutter.PicOutputModel;
import com.mycompany.myapp.videocutter.util.VideoCutterUtil;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		model.addAttribute("model", new VideoUploadModel());
		return "uploadvideo";
	}

	/**
	 * Displays upload screen.
	 * 
	 * @return Model that contains "today"
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public ModelAndView goUpload() {
		ModelAndView mav = new ModelAndView("uploadvideo");

		mav.addObject("today", new Date());

		return mav;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST, params = "upload")
	public ModelAndView uploadHandler(@ModelAttribute("model") VideoUploadModel videoModel, BindingResult br) {
		ModelAndView mv = new ModelAndView("uploadvideo");
		logger.debug("Processing uploading...");

		if (br.hasErrors()) {
			logger.error("Binding errors: " + br.getErrorCount());
			for (ObjectError objError : br.getAllErrors()) {
				logger.error("Error message:" + objError.getDefaultMessage());
			}
			mv.addObject("nError", br.getErrorCount());
		} else {
			if (videoModel != null) {
				logger.debug("Video title: " + videoModel.getVideoTitle());
				if (videoModel.getVideoFile() != null) {
					logger.debug("Video Size: " + videoModel.getVideoFile().getSize());
				}
			}
			mv.addObject("videoTitle", videoModel.getVideoTitle());
			mv.addObject("videoSize", videoModel.getVideoFile().getSize() + " bytes");
			mv.addObject("originalFileName", videoModel.getVideoFile().getOriginalFilename());
		}

		return mv;

	}

	@RequestMapping(value = "/viewframes", method = RequestMethod.GET)
	public ModelAndView goViewFrames() {
		ModelAndView mav = new ModelAndView("viewframes");
		return mav;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST, params = "viewframes")
	public ModelAndView viewFrames(@ModelAttribute("model") VideoUploadModel videoModel, BindingResult br) {
		ModelAndView mav = new ModelAndView("viewframes");
		if (br.hasErrors()) {
			logger.error("Binding errors: " + br.getErrorCount());
			for (ObjectError objError : br.getAllErrors()) {
				logger.error("Error message:" + objError.getDefaultMessage());
			}
			mav.addObject("nError", br.getErrorCount());
		} else {
			if (videoModel != null) {
				VideoCutterUtil util = new VideoCutterUtil();
				ArrayList<Mat> images = util.split(videoModel);
				util.save(images);
				for (int i = 0; i < images.size(); i++) {
					PicOutputModel pic = new PicOutputModel();
					String path = pic.getPath();
					mav.addObject("path", path);
				}
				logger.debug("successfully split: " + videoModel.getVideoTitle());
			}
		}
		return mav;
	}
}

/*public class HomeController {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(){
		return "uploadvideo";
	}

	private static final String VIDEOS = "videos";
	private static final String TOMCAT_HOME_PROPERTY = "catalina.home";
    private static final String TOMCAT_HOME_PATH = System.getProperty(TOMCAT_HOME_PROPERTY);
    private static final String VIDEOS_PATH = TOMCAT_HOME_PATH + File.separator + VIDEOS;

    private static final File 	VIDEOS_DIR = new File(VIDEOS_PATH);
    private static final String VIDEOS_DIR_ABSOLUTE_PATH = VIDEOS_DIR.getAbsolutePath() + File.separator;
    private static final String FAILED_UPLOAD_MESSAGE = "You failed to upload [%s] because the file because %s";
    private static final String SUCCESS_UPLOAD_MESSAGE = "You successfully uploaded file = [%s]";

    
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
//    public String uploadFileHandler(@RequestParam("name") String name,
    public String uploadFileHandler (@RequestParam("file") MultipartFile file) {
        ModelAndView mv = new ModelAndView("uploadvideo");
        String name = "title";
            logger.debug("name = " + name);
        	createDirIfNeeded();
            mv.addObject("message", createFile(name, file));

            mv.addObject("path", VIDEOS_DIR_ABSOLUTE_PATH + name +".mov");
            mv.addObject("fileName", name);
            mv.addObject("fileSize", file.getSize());
           return "uploadvideo";
    }
    

    private String createFile(String name, MultipartFile file) {
    	try{
    	File video = new File(VIDEOS_DIR_ABSOLUTE_PATH + name);
    	BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(video));
    	stream.write(file.getBytes());
    	stream.close();
		return String.format(SUCCESS_UPLOAD_MESSAGE, name);
    } catch (Exception e){
    	return String.format(FAILED_UPLOAD_MESSAGE,  name, e.getMessage());
    }
	}

	private void createDirIfNeeded() {
        if (!VIDEOS_DIR.exists()) {
        	VIDEOS_DIR.mkdirs();
        }
    }
}
	*/
