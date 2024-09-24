package com.web.mapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileDTO {

	private int no;
	private String nm1;
	private String nm2;
	private String extension;
	private String path;
	private String type;
	private boolean del;
	
}
