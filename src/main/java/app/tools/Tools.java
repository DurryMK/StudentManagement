package app.tools;

import java.util.Random;

public class Tools {
	// 生成随机号码
	public static String createNumber() {
		Random rd = new Random();
		String str = rd.nextInt(10) + "";
		str += rd.nextInt(10);
		str += rd.nextInt(10);
		str += rd.nextInt(10);
		str += rd.nextInt(10);
		str += rd.nextInt(10);
		return str;
	}
}
