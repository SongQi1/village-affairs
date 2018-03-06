package com.bocs.util.qrcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.bocs.util.qrcode.BufferedImageLuminanceSource;

/**
 * @Description: (普通二维码生成)
 * @author：Relieved
 * @date：2014-11-7 下午04:42:35
 */
public class QRCode {

	/**
	 * 
	 * @param text 二维码内容
	 * @param width 二维码宽度
	 * @param height  二维码高度度
	 * @param imagePath 生成二维码图片存放路径
	 * @throws WriterException 
	 * @throws IOException 
	 */
	public static void createQRCode(String text, int width, int height, String imagePath) throws WriterException, IOException {
		BitMatrix bitMatrix = createBitMatrix(text, width, height);
		File outputFile = new File(imagePath);
		MatrixToImageWriter.writeToFile(bitMatrix, "png", outputFile);
	}
	
	/**
	 * 
	 * @param text
	 * @param imagePath
	 * @param imageType
	 * @throws WriterException
	 * @throws IOException
	 */
	public static void createQRCode(String text, String imagePath, String imageType) throws WriterException, IOException{
		BitMatrix bitMatrix = createBitMatrix(text, 300, 300);
		File outputFile = new File(imagePath);
		MatrixToImageWriter.writeToFile(bitMatrix, imageType, outputFile);
	}
	
	/**
	 * 
	 * @param text
	 * @param outputStream
	 * @throws WriterException
	 * @throws IOException
	 */
	public static void createQRCode(String text, OutputStream outputStream) throws WriterException, IOException{
		BitMatrix matrix = createBitMatrix(text, 300, 300);
		MatrixToImageWriter.writeToStream(matrix, "png", outputStream);
	}
	
	private static BitMatrix createBitMatrix(String text, int width, int height) throws WriterException{
		if (width == 0) {
			width = 300;
		}
		if (height == 0) {
			height = 300;
		}
		/**
		 * 设置二维码的参数
		 */
		HashMap<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
		// 内容所使用编码
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		return new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
	}

	/**
	 * 二维码的解析
	 * @param file
	 * @throws NotFoundException 
	 */
	public String readQRCode(File file) throws IOException, NotFoundException {
		MultiFormatReader formatReader = new MultiFormatReader();
		
		if (!file.exists()) {
			throw new IOException("Can not find file!");
		}
		
		BufferedImage image = ImageIO.read(file);
		LuminanceSource source = new BufferedImageLuminanceSource(image);
		Binarizer binarizer = new HybridBinarizer(source);
		BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
		
		Map<DecodeHintType, String> hints = new HashMap<DecodeHintType, String>();
		hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
		Result result = formatReader.decode(binaryBitmap, hints);
		return result.getText();
	}
}
