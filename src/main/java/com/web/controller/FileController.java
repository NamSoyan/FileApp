package com.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.web.components.FileComponent;
import com.web.mapper.FileDTO;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = {"http://localhost:3000"}) //react 요청이기 때문에 crossOrigin 필요
@Slf4j
@RestController
public class FileController {

	@GetMapping("/")
	public String index() {
		return "File Upload Server !!";
	}
	
	@Autowired
	private FileComponent fc;
	
	@PostMapping("/fileUpload") //해당 주소 호출 시, localhost8080
	public Map<String, Object> fileUpload(@RequestParam("file") MultipartFile file) {
//		log.info(" file : {} ", file); //무조건 log 찍어 보면서 진행하기 
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", true);
		resultMap.put("url", fc.upload2(file));
	    return resultMap;
	}

	@PostMapping("/filesUpload") //배열로 이루어져있음
	public Map<String, Object> filesUpload(@RequestParam("file") MultipartFile[] files) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", false);

		List<String> list = new ArrayList<>();
		for(int i = 0; i < files.length; i++) {
			list.add(fc.upload2(files[i]));
		}
		
		if(list.size() > 0) {
			resultMap.put("state", true);
			resultMap.put("list", list);
		}
	    return resultMap;
	}
	
	@GetMapping("/view")
	public ResponseEntity<?> view(@RequestParam("url") String url) {
		return fc.getFile(url);
	}
	
	@PostMapping("/all")
	public List<FileDTO> getAll() {
		return fc.getData();	
	}
	
}
