/**
 * 
 */
package com.dd1yyg.shop.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by QuestZhang on 16/6/7.
 */
public class FileHelper {
	private static String mEncode = "UTF-8"; // 默认的编码格式是UTF-8
	private static final int DEFAULT_CHAR_SIZE = 1024 * 8;
	private static final boolean DEFAULT_IS_APPEND = true; //

	public void setEncoding(String encode) {
		mEncode = encode;
	}

	public String getEncoding() {
		return mEncode;
	}

	public static String readFileToString(File file) {
		return readFileToString(file, mEncode);
	}

	/**
	 * 读取文件数据转成String格式
	 * 
	 * @param file
	 *            文件
	 * @param encoding
	 *            编码格式
	 * @return String字符串
	 */
	private static String readFileToString(File file, String encoding) {
		InputStreamReader in = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		StringWriter sw = new StringWriter();
		int len = 0;
		char[] buffer = null;

		if (file == null)
			throw new NullPointerException("no file for read!");
		if (file.exists()) {
			try {
				in = encoding == null ? new InputStreamReader(
						new FileInputStream(file)) : new InputStreamReader(
						new FileInputStream(file), encoding);
				br = new BufferedReader(in);
				buffer = new char[DEFAULT_CHAR_SIZE];
				bw = new BufferedWriter(sw);
				while ((len = br.read(buffer, 0, (int) file.length())) != -1) {
					bw.write(buffer, 0, len);
				}
				bw.flush();
				return bw.toString();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (sw != null) {
						sw.close();
						sw = null;
					}
					if (br != null) {
						br.close();
						br = null;
					}
					if (bw != null) {
						bw.close();
						bw = null;
					}
					if (in != null) {
						in.close();
						in = null;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "";
	}

	public static boolean writeStringToFile(File file, String content) {
		return writeStringToFile(file, content, DEFAULT_IS_APPEND);
	}

	private static boolean writeStringToFile(File file, String content,
			boolean isAppend) {
		boolean isOk = false;
		char[] buffer = null;
		int count = 0;
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			if (!file.exists()) {
				createNewFileAndParentDir(file);
			}
			if (file.exists()) {
				br = new BufferedReader(new StringReader(content));
				bw = new BufferedWriter(new FileWriter(file, isAppend));
				buffer = new char[DEFAULT_CHAR_SIZE];
				int len = 0;
				while ((len = br.read(buffer, 0, DEFAULT_CHAR_SIZE)) != -1) {
					bw.write(buffer, 0, len);
					count += len;
				}
				bw.flush();
			}
			isOk = content.length() == count;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null) {
					bw.close();
					bw = null;
				}
				if (br != null) {
					br.close();
					br = null;
				}
				buffer = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return isOk;
	}

	/**
	 * 二进制存储为文件
	 * @param file
	 * @param content
	 * @return
	 */
	public static boolean writeBinnaryToFile(File file, byte[] content) {
		return writeBinnaryToFile(file, content, DEFAULT_IS_APPEND);
	}

	private static boolean writeBinnaryToFile(File file, byte[] content,
			boolean isAppend) {
		boolean isOk = false;
		BufferedOutputStream out = null;
		try {
			if (!file.exists()) {
				createNewFileAndParentDir(file);
			}
			if (file.exists()) {
				out = new BufferedOutputStream(new FileOutputStream(file));
				out.write(content);
				isOk = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			isOk = false;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return isOk;
	}

	/**
	 * 创建文件及其父目录
	 * 
	 * @param file
	 * @return
	 */
	public static boolean createNewFileAndParentDir(File file) {
		boolean isCreateNewFileOk = true;
		isCreateNewFileOk = createParentDir(file);
		// 创建父目录失败，直接返回false，不再创建子文件
		if (isCreateNewFileOk) {
			if (!file.exists()) {
				try {
					isCreateNewFileOk = file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
					isCreateNewFileOk = false;
				}
			}
		}
		return isCreateNewFileOk;
	}

	/**
	 * 创建文件父目录
	 * 
	 * @param file
	 * @return
	 */
	public static boolean createParentDir(File file) {
		boolean isMkdirs = true;
		if (!file.exists()) {
			File dir = file.getParentFile();
			if (!dir.exists()) {
				isMkdirs = dir.mkdirs();
			}
		}
		return isMkdirs;
	}
}
