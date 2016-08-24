/**
 * 
 */
package com.ddyyyg.shop.utils;

import android.os.Environment;
import android.util.Log;

import com.ddyyyg.app.App;
import com.ddyyyg.app.AppConfig;

import java.io.File;

/**
 * Created by QuestZhang on 16/6/7.
 */
public class LogUtil {

	private static boolean mOpen = true; // 日志开关
	private static final String LEVEL_D = "D";
	private static final String LEVEL_E = "E";
	private static final String LEVEL_I = "I";
	private static final String LEVEL_V = "V";
	private static final String LEVEL_W = "W";

	public static final String TAG = LogUtil.class.getSimpleName();

	public LogUtil() {
		super();
		// Log.d(tag, msg)
		// Log.e(tag, msg)
		// Log.i(tag, msg)
		// Log.v(tag, msg)
		// Log.w(tag, msg)
	}

	public void setDebug(boolean isOpen) {
		mOpen = isOpen;
	}

	public boolean getDebug() {
		return mOpen;
	}

	/**
	 * 输出debug级别的日志
	 * 
	 * @param tag
	 *            TAG标记
	 * @param msg
	 *            日志内容
	 */
	public static void d(String tag, String msg) {
		if (!mOpen) {
			return;
		}
		if (msg == null) {
			msg = "无日志信息";
		}
		Log.d(tag, msg);
		writeToFile(LEVEL_D, tag, msg, null);
	}

	/**
	 * 输出error级别的日志
	 * 
	 * @param tag
	 *            TAG标记
	 * @param msg
	 *            日志内容
	 */
	public static void e(String tag, String msg) {
		if (!mOpen) {
			return;
		}
		if (msg == null) {
			msg = "无日志信息";
		}
		Log.e(tag, msg);
		writeToFile(LEVEL_E, tag, msg, null);
	}

	/**
	 * 输出info级别的日志
	 * 
	 * @param tag
	 *            TAG标记
	 * @param msg
	 *            日志内容
	 */
	public static void i(String tag, String msg) {
		if (!mOpen) {
			return;
		}
		if (msg == null) {
			msg = "无日志信息";
		}
		Log.i(tag, msg);
		writeToFile(LEVEL_I, tag, msg, null);
	}

	/**
	 * 输出VERBOSE级别的日志
	 * 
	 * @param tag
	 *            TAG标记
	 * @param msg
	 *            日志内容
	 */
	public static void v(String tag, String msg) {
		if (!mOpen) {
			return;
		}
		if (msg == null) {
			msg = "无日志信息";
		}
		Log.v(tag, msg);
		writeToFile(LEVEL_V, tag, msg, null);
	}

	/**
	 * 输出WARN级别的日志
	 * 
	 * @param tag
	 *            TAG标记
	 * @param msg
	 *            日志内容
	 */
	public static void w(String tag, String msg) {
		if (!mOpen) {
			return;
		}
		if (msg == null) {
			msg = "无日志信息";
		}
		Log.w(tag, msg);
		writeToFile(LEVEL_W, tag, msg, null);
	}

	/**
	 * 将日志信息写到文件里面
	 * 
	 * @param levelD
	 *            日志级别
	 * @param tag
	 *            日志标记
	 * @param msg
	 *            日志内容
	 * @param object
	 *            保留字段
	 */
	private static void writeToFile(final String levelD, final String tag, final String msg, Object object) {
		App.getThreadPool().execute(new Runnable() {
			public void run() {
				String time = TimeUtil.getCurrentTime(TimeUtil.TIME_FORMAT_FOUR);
				StringBuffer sb = new StringBuffer();
				sb.append("level=").append(levelD).append("\ttime=").append(time).append("\tclassname=").append(tag)
						.append("\tMessage=").append(msg);
				sb.append(System.getProperties().getProperty("line.separator"));
				File logfileDir = Environment.getExternalStoragePublicDirectory(AppConfig.DIR_ROOT);
				File file = new File(logfileDir, "log-" + TimeUtil.getCurrentTime(TimeUtil.TIME_FORMAT_13)
						+ AppConfig.FILE_NAME_EXTENSION_LOG);
				FileHelper.writeStringToFile(file, sb.toString());
			}
		});
	}
}
