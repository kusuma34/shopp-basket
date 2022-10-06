package com.example.shoppingcart.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ImageUploadService {
	//public final String uploadDir = new ClassPathResource("static/productImages/").getFile().getAbsolutePath();

//	public ImageUploadService()throws IOException{
//		
//	}
	public String uploadImage(MultipartFile file){
		try {
			Path resourceDirectory = Paths.get("src","main","resources", "static", "productImages");
			String imageUUID = file.getOriginalFilename();
//			Path newPathAndFileName = Paths.get(uploadDir,imageUUID);
//			//String filePath=uploadDir+File.separator+imageUUID;
//			String filePath=resourceDirectory+File.separator+imageUUID;
//			System.out.println(filePath);
//			File f=new File(filePath);
//			if(!f.exists()) {
//				f.mkdir();
//			}
//			//Files.copy(file.getInputStream(), Paths.get(filePath));
//			Files.write(newPathAndFileName, file.getBytes());
//			return imageUUID;
			
			String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/productImages";
			//String UPLOAD_DIRECTORY = resourceDirectory+File.separator+imageUUID;;
			Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
			//String filePath=resourceDirectory+File.separator+imageUUID;
	        System.out.println(fileNameAndPath);
	        Files.write(fileNameAndPath, file.getBytes());
	        return imageUUID;
//	        

//	        String fileName = StringUtils.cleanPath(file.getOriginalFilename());	 
//	        String uploadDir = "productImages/" + imageUUID;	 
//	        FileUploadService.saveFile(uploadDir, fileName, file);         
//	        return fileName;
		}
		catch(Exception e) {
			return "";
		}
	}
}
