package jp.qrcode;

import java.awt.image.BufferedImage;

import jp.sourceforge.qrcode.data.QRCodeImage;

public class MyCodeImage implements QRCodeImage {

	BufferedImage buf;
	public MyCodeImage(BufferedImage buf){
		this.buf = buf;
	}
	public int getHeight() {
		return buf.getHeight();
	}

	public int getPixel(int x, int y) {
		return buf.getRGB(x, y);
	}

	public int getWidth() {
		return buf.getWidth();
	}

}
