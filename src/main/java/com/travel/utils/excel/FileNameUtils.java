package com.travel.utils.excel;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;

import com.travel.utils.StringUtils;
import com.travel.utils.time.DateUtils;

public class FileNameUtils {
	protected static final Log logger = LogFactoryImpl.getLog(FileNameUtils.class);

	public static final String encodeFilename(String agent, String filename, String suffix)
			throws UnsupportedEncodingException {
		filename = StringUtils.isNotBlank(filename) ? filename : "temp";
		filename = filename + "-" + DateUtils.format("yyyyMMddHHmmss") + "." + suffix;
		logger.info("User-Agent:" + agent);
		return StringUtils.isNotBlank(agent) && agent.toUpperCase().contains("FIREFOX")
				? new String(filename.getBytes(), "ISO-8859-1")
				: URLEncoder.encode(filename, "UTF-8");
	}
}
