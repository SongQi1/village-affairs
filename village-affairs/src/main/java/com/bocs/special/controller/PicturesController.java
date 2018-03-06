package com.bocs.special.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bocs.special.service.PicturesService;


/**
 * <p>
 * 服务明细图片
 * </p>
 *
 * @author SongQi
 * @since 2017-09-09
 */
@RestController
@RequestMapping("/pictures")
public class PicturesController {

	@Autowired
	private PicturesService picturesService;

	@PostMapping("/removePic")
	public void removePic(ModelMap modelMap, String id){
		picturesService.delete(id);
	}

	@PostMapping("/uploadPic")
	public void uploadPic(@RequestParam("file") MultipartFile file, String serviceDetailId) {
		picturesService.uploadPic(file, serviceDetailId);
	}

	@RequestMapping("/viewimage/{id}")
	public void viewPic(@PathVariable(value = "id") String id, HttpServletResponse response) {
		File file = picturesService.getPicFile(id);

		if(file != null){
			response.setContentType("img/jpeg");
			response.setCharacterEncoding("utf-8");
			InputStream is = null;
			OutputStream os = null;
			try {
				is = new FileInputStream(file);
				os = response.getOutputStream();
				byte[] b = new byte[2048];
				int length;
				while ((length = is.read(b)) > 0) {
					os.write(b, 0, length);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					if(os != null){
						os.close();
					}
					if(is != null){
						is.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}