package cn.queue.common.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * MD5加密
 * 单向加密算法
 * 特点：加密速度快，不需要秘钥，但是安全性不高，需要搭配随机盐值使用
 *
 */
public class MD5Util {

	public static String sign(String content, String salt, String charset) {
		content = content + salt;
		return DigestUtils.md5Hex(getContentBytes(content, charset));
	}

	public static boolean verify(String content, String sign, String salt, String charset) {
		content = content + salt;
		String mysign = DigestUtils.md5Hex(getContentBytes(content, charset));
		return mysign.equals(sign);
	}

	private static byte[] getContentBytes(String content, String charset) {
		if (!"".equals(charset)) {
			try {
				return content.getBytes(charset);
			} catch (UnsupportedEncodingException var3) {
				throw new RuntimeException("MD5签名过程中出现错误,指定的编码集错误");
			}
		} else {
			return content.getBytes();
		}
	}

	//获取文件md5加密后的字符串
    public static String getFileMD5(MultipartFile file) throws Exception {
		InputStream fis = file.getInputStream();
		//用字节输出流对文件进行输出
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int byteRead;
		//while读取文件内容
		while((byteRead = fis.read(buffer)) > 0){
			baos.write(buffer, 0, byteRead);
		}
		fis.close();
		//对文件字节流进行md5加密
        //本质:对字节数组进行md5加密,然后返回一个字符串
		return DigestUtils.md5Hex(baos.toByteArray());
    }

	public static void main(String[] args) {
		String charset = "UTF-8";
		String jm = sign("12345","test",charset);
		System.out.println( sign("12345","test",charset));

	}
}