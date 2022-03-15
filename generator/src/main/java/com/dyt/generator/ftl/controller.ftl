package com.dyt.business.controller.admin;

import com.dyt.server.domain.${Domain};
import com.dyt.server.dto.${Domain}Dto;
import com.dyt.server.dto.PageDto;
import com.dyt.server.dto.ResponseDto;
import com.dyt.server.exception.ValidatorException;
import com.dyt.server.service.${Domain}Service;
import com.dyt.server.util.ValidatorUtil;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/admin/${domain}")
public class ${Domain}Controller {

private static final Logger LOG = LoggerFactory.getLogger(${Domain}Controller.class);
public static final String BUSINESS_NAME = "大章";

@Resource
private ${Domain}Service ${domain}Service;

/**
* 列表查询
*/
@PostMapping("/list")
public ResponseDto list(@RequestBody PageDto pageDto) {
ResponseDto responseDto = new ResponseDto();

${domain}Service.list(pageDto);
responseDto.setContent(pageDto);
return responseDto;
}

/**
* 保存，id有值时更新，无值时新增
*/
@PostMapping("/save")
public ResponseDto save(@RequestBody ${Domain}Dto ${domain}Dto) {
//保存校验


ResponseDto responseDto = new ResponseDto();
${domain}Service.save(${domain}Dto);
responseDto.setContent(${domain}Dto);
return responseDto;
}

/**
* 删除
*/
@DeleteMapping("/delete/{id}")
public ResponseDto delete(@PathVariable String id) {
ResponseDto responseDto = new ResponseDto();
${domain}Service.delete(id);
return responseDto;
}

}
