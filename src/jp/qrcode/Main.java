package jp.qrcode;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * 二维码生成与解析
 * @author 郭
 *
 */
public class Main {
	
	/**
	 * 文本 --> 二维码
	 */
	public static void enCode(){
		//日期格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HHmmssSSS");
		//获取当前时间
		Date d = new Date();
		//格式化
		String time = sdf.format(d);
		//获取文字
		Scanner in = new Scanner(System.in);
		//加密   文字 --> 二维码
		System.out.println("你想要哪些信息变成二维码？");
		String content = in.nextLine();
		//获取当前系统用户
		String user = System.getProperty("user.name");
		//设置二维码存放路径 ，默认桌面
		String imgPath = "C:/Users/"+user+"/Desktop/生成的二维码/"+time+".png";
		//为二维码加上logo null-->路径 
		//System.out.println("为二维码添加logo？请输入logo的绝对路径！");
		String logoPath = "C:\\Users\\郭飞阳\\Pictures\\ico\\spring-1.png";
		//设置二维码格式 jpg
		String imgType = "png";
		//生成二维码
		try {
			QRCodeUtil.encoderQRCode(content, imgPath, imgType, logoPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("生成信息："+content);
		System.out.println("生成路径："+imgPath);
		System.out.println("二维码生成完毕！");
	}
	
	
	/**
	 * 二维码 --> 文本
	 */
	public static void deCode(){
		Scanner in = new Scanner(System.in);
		System.out.println("请输入二维码文件路径，如：C:/a/b.jpg");
		String imgPath=in.next();
		try{
			String code = QRCodeUtil.decodeQRCode(imgPath);
			System.out.println("扫描结果为："+code);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		enCode();

	}
}
