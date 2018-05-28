package com.travel.code.utils.upload.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.travel.controller.DefaultController;
import com.travel.entity.Image;
import com.travel.entity.Menu;
import com.travel.service.Service;
import com.travel.support.CommonResponse;

@Controller
@RequestMapping("upload")
public class UploadController extends DefaultController<Image, String> {

	private static final String _fileupload_url = "D:\\upload\\images\\";

	@Override
	public Service<Image, String> service() {
		return null;
	}

	@Override
	public String path() {
		return "";
	}

	@ResponseBody
	@RequestMapping(value = "images")
	public CommonResponse imageUpload(@RequestParam("fileupload") MultipartFile file, Model model, Menu entity) {
		CommonResponse retval = new CommonResponse(false);
		
		Map<Object, Object> map = new HashMap<>();
		if(file == null) {
			retval.setMessage("请选择图片");
		}else {
//			String fileName = file.getOriginalFilename();
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				String file_name = sdf.format(new Date()).toString()+"_"+file.getOriginalFilename();
//				String file_name = file.getOriginalFilename();
				uploadFile(file.getBytes(), _fileupload_url, file_name);
				map.put("remote_path", file_name);
				entity.setImgSrc(file_name);
				model.addAttribute(ENTITY, entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			retval.setData(map);
		}
		return retval;
	}

	public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
		File targetFile = new File(filePath);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
//		file.transferTo(targetFile);
		FileOutputStream out = new FileOutputStream(fileName);
		out.write(file);
		out.flush();
		out.close();
	}

}
