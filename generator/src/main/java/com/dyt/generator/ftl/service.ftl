package com.dyt.server.service;

import com.dyt.server.domain.${Domain};
import com.dyt.server.domain.${Domain}Example;
import com.dyt.server.dto.${Domain}Dto;
import com.dyt.server.dto.PageDto;
import com.dyt.server.mapper.${Domain}Mapper;
import com.dyt.server.util.CopyUtil;
import com.dyt.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ${Domain}Service {

@Resource
private ${Domain}Mapper ${domain}Mapper;

/**
* 列表查询
*/
public void list(PageDto pageDto) {
PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
${Domain}Example ${domain}Example = new ${Domain}Example();
List<${Domain}> ${domain}s = ${domain}Mapper.selectByExample(${domain}Example);

PageInfo<${Domain}> pageInfo = new PageInfo<>(${domain}s);
pageDto.setTotal(pageInfo.getTotal());
List
<${Domain}Dto> ${domain}Dtos = CopyUtil.copyList(${domain}s, ${Domain}Dto.class);

    pageDto.setList(${domain}Dtos);

    }

    /**
    * 保存，id有值时更新，无值时新增
    */
    public void save(${Domain}Dto ${domain}Dto) {
    ${Domain} ${domain} = new ${Domain}();
    BeanUtils.copyProperties(${domain}Dto, ${domain});
    if (StringUtils.isEmpty(${domain}Dto.getId())) {
    this.insert(${domain});
    } else {
    this.update(${domain});
    }
    }

    /**
    * 新增
    */
    public void insert(${Domain} ${domain}) {
    ${domain}.setId(UuidUtil.getShortUuid());
    ${domain}Mapper.insert(${domain});
    }

    /**
    * 更新
    */
    public void update(${Domain} ${domain}) {
    ${domain}Mapper.updateByPrimaryKey(${domain});
    }

    /**
    * 删除
    */
    public void delete(String id) {
    ${domain}Mapper.deleteByPrimaryKey(id);
    }


    }
