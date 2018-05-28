package com.travel.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.travel.utils.StringUtils;

public class FileUtils {
	public static String save(String path, String folder, String filename, File file) throws IOException {
		return save(path, folder, filename, (InputStream) (new FileInputStream(file)));
	}

	@SuppressWarnings("unused")
	public static String save(String path, String folder, String filename, InputStream inputStream) throws IOException {
		FileOutputStream outputStream = null;

		try {
			File e = new File(path + folder);
			if (!e.exists()) {
				e.mkdirs();
			}

			outputStream = new FileOutputStream(e + "/" + filename);
			byte[] buf = new byte[1024];
			boolean len = false;

			int len1;
			while ((len1 = inputStream.read(buf)) > 0) {
				outputStream.write(buf, 0, len1);
			}
		} catch (Exception arg10) {
			arg10.printStackTrace();
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}

			if (outputStream != null) {
				outputStream.close();
			}

		}

		return folder + filename;
	}

	public static String suffix(String filename) {
		int dot = filename.lastIndexOf(".");
		String suffix = "";
		if (dot != -1) {
			suffix = filename.substring(dot + 1);
			if (StringUtils.isNotBlank(suffix)) {
				suffix = suffix.toLowerCase();
			}
		}

		return suffix;
	}

	public static Boolean writeFile(File file, String content) {
		if (file.exists()) {
			return Boolean.FALSE;
		} else {
			FileOutputStream stream = null;

			try {
				file.createNewFile();
				stream = new FileOutputStream(file);
				stream.write(content.getBytes("UTF-8"));
				Boolean e = Boolean.TRUE;
				return e;
			} catch (IOException arg12) {
				arg12.printStackTrace();
			} finally {
				try {
					if (stream != null) {
						stream.close();
					}
				} catch (IOException arg11) {
					arg11.printStackTrace();
				}

			}

			return Boolean.FALSE;
		}
	}
}
