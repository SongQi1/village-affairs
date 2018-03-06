package com.bocs.special.service.impl;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bocs.special.dao.PictureDao;
import com.bocs.special.dao.ServiceDetailDao;
import com.bocs.special.model.Picture;
import com.bocs.special.model.ServiceDetail;
import com.bocs.special.service.PicturesService;
import com.bocs.util.DateUtil;
import com.bocs.util.PropertiesUtils;

import core.service.BaseService;

@Service
public class PictureServiceImpl extends BaseService<Picture> implements PicturesService{
	
	@Resource
	private PictureDao pictureDao;
	
	@Resource
	private ServiceDetailDao serviceDetailDao;
	/**
     * 上传图片
     *
     * @param file
     * @param customerId
     * @param picType
     * @return
     */
    public void uploadPic(MultipartFile file, String serviceDetailId) {
        //保存图片至服务器
        String rootPath = PropertiesUtils.getBasicValue("pic.store.path");
        if(! rootPath.endsWith(File.separator)){
            rootPath += File.separator;
        }
        String fileDir = DateUtil.getDateTime("yyyyMMdd") + File.separator + DateUtil.getDateTime("HHmmss") + file.getOriginalFilename();
        String imgPath = rootPath + fileDir;
        File imgFile = new File(imgPath);
        File parent = imgFile.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        try {
            file.transferTo(new File(imgPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Picture picture = new Picture();
        picture.setPictureUrl(fileDir);
        ServiceDetail serviceDetail = serviceDetailDao.get(serviceDetailId);
        picture.setServiceDetail(serviceDetail);
        pictureDao.persist(picture);
    }

    /**
     * 查找图片
     * @param fileId
     * @return
     */
    public File getPicFile(String fileId) {
        Picture picture = pictureDao.get(fileId);
        if(picture != null){
            String rootPath = PropertiesUtils.getBasicValue("pic.store.path");
            if(! rootPath.endsWith(File.separator)){
                rootPath += File.separator;
            }

            String fileDir =  picture.getPictureUrl();

            String imgPath = rootPath + fileDir;
            File imgFile = new File(imgPath);
            return imgFile;
        }
        return null;
    }

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		pictureDao.deleteByPK(id);
	}


}
