package com.bocs.special.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import com.bocs.special.model.Picture;

import core.service.Service;

public interface PicturesService extends Service<Picture>{
	
	void uploadPic(MultipartFile file, String serviceDetailId); 
	File getPicFile(String id);
	void delete(String id);
}
