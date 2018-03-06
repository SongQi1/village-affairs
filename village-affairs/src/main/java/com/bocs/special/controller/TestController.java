package com.bocs.special.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bocs.util.qrcode.QRCode;
import com.google.zxing.WriterException;


@Controller
public class TestController {

	@RequestMapping(value="/createQRCodePage", method = { RequestMethod.POST, RequestMethod.GET })
	public String createQRCodePage(String text){
		return "create_qr_code";
	}
	
	@RequestMapping(value="/createQRCode", method = { RequestMethod.POST, RequestMethod.GET })
	public String createQRCode(String text, HttpServletRequest request) throws WriterException, IOException{
		String root = request.getServletContext().getRealPath("/") + "static" +File.separator;
		String imagePath = root + "qrcode.png";
		QRCode.createQRCode(text, imagePath, "png");
		request.setAttribute("src", "/static/qrcode.png");
		request.setAttribute("text", text);
		return "create_qr_code";
	}
	
}
