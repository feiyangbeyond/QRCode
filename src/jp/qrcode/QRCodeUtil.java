package jp.qrcode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;

import jp.sourceforge.qrcode.QRCodeDecoder;

import com.swetake.util.Qrcode;

public class QRCodeUtil {
	private QRCodeUtil(){}
	
	/**
	 *  二维码 --> 字符串
	 * @param imgPath 图片的路径
	 * @throws IOException io
	 * @throws UnsupportedEncodingException 不支持的格式异常
	 */
	public static String decodeQRCode(String imgPath) throws IOException {
		//硬盘中的image --> 内存中的bufferedimage
		BufferedImage read = ImageIO.read(new File(imgPath));
		//把二维码转换成字符串
		QRCodeDecoder decoder = new QRCodeDecoder();
		MyCodeImage tdcImage = new MyCodeImage(read);
		String content = new String(decoder.decode(tdcImage),"utf-8");
		return content;
}
	
	/**
	 *  文字信息 --> 二维码
	 * @param content 要加密的信息
	 * @param imgPath 生成二维码的路径
	 * @param imgType 生成二维码的图片格式
	 * @throws IOException io
	 */
	public static void encoderQRCode(String content, String imgPath, String imgType, String logoPath) throws IOException{
		//文件流
		File file = new File(imgPath);
		//不存在路径就创建
		if(!file.exists()){
			file.mkdirs();
		}
		//接受图片
		BufferedImage bufImg = qRcodeCommon(content,imgType,logoPath);
		//写入硬盘
		ImageIO.write(bufImg, imgType, file);
		
	}

	/**
	 * 生成二维码
	 * @param content 文本信息
	 * @param imgType 图片格式
	 * @param logoPath logo的路径
	 * @throws IOException io
	 */
	private static BufferedImage qRcodeCommon(String content, String imgType, String logoPath) throws IOException{
		//内存中的图片
		BufferedImage bufImg = null;
		//Qrcode对象   字符串-->boolean[][]
		Qrcode qrcode = new Qrcode();
		//设置二维码的排错率 7% L<M<Q<H 30%
		qrcode.setQrcodeErrorCorrect('M');
		//设置可存放的信息类型 N：数字   A：数字+A-Z  B都支持
		qrcode.setQrcodeEncodeMode('B');
		//设置版本1-40
		int version = 6;
		qrcode.setQrcodeVersion(version);
		//把字符串转换成byte数组
		byte[] contentBytes = content.getBytes("utf-8");
		//转换成布尔数组
		boolean[][] codeOut = qrcode.calQrcode(contentBytes);
		//因为上边设置的二维码的大小最大是40 太小 转换一下
		int imgSize = 67+12*(version-1);
		//内存中的图片
		bufImg = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_RGB);
		//创建一个画板
		Graphics2D g2 = bufImg.createGraphics();
		//设置背景色
		g2.setBackground(Color.WHITE);
		//初始化 -- 设置白色的背景的范围
		g2.clearRect(0, 0, imgSize, imgSize);
		//设置画板上画的图像的颜色 -- 二维码
		g2.setColor(Color.BLACK);
		//像素偏移量  --二维码的白边
		int pixoff = 2;
		//遍历二位数组
		for(int i=0; i < codeOut.length; i++){
			for(int j = 0; j < codeOut.length; j++){
				if(codeOut[i][j]){
					//true就画黑  3--设置的每个像素点的边长
					g2.fillRect(i*3+pixoff, j*3+pixoff, 3, 3);
				}
			}
		}
		//判断是否传入logo路径
		if(logoPath != null && logoPath != ""){
			//得到logo
			Image logo = ImageIO.read(new File(logoPath));
			//把logo画上
			g2 = bufImg.createGraphics();
			g2.drawImage(logo, imgSize*2/5, imgSize*2/5, imgSize/5, imgSize/5, null);
		}
		//释放
		g2.dispose();
		//刷新
		bufImg.flush();
		//返回图片
		return bufImg;
	}
}


























