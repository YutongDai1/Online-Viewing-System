package com.dyt.server.service;

import com.dyt.server.domain.Resource;
import com.dyt.server.domain.ResourceExample;
import com.dyt.server.dto.PageDto;
import com.dyt.server.dto.ResourceDto;
import com.dyt.server.mapper.ResourceMapper;
import com.dyt.server.util.CopyUtil;
import com.dyt.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


@Service
public class ResourceService {

    @javax.annotation.Resource
    private ResourceMapper resourceMapper;

    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        ResourceExample resourceExample = new ResourceExample();

        List<Resource> resources = resourceMapper.selectByExample(resourceExample);

        PageInfo<Resource> pageInfo = new PageInfo<>(resources);
        pageDto.setTotal(pageInfo.getTotal());
        List
                <ResourceDto> resourceDtos = CopyUtil.copyList(resources, ResourceDto.class);

        pageDto.setList(resourceDtos);

    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(ResourceDto resourceDto) {
        Resource resource = new Resource();
        BeanUtils.copyProperties(resourceDto, resource);
        if (StringUtils.isEmpty(resourceDto.getId())) {
            this.insert(resource);
        } else {
            this.update(resource);
        }
    }

    /**
     * 新增
     */
    public void insert(Resource resource) {


        resource.setId(UuidUtil.getShortUuid());
        resourceMapper.insert(resource);
    }

    /**
     * 更新
     */
    public void update(Resource resource) {

        resourceMapper.updateByPrimaryKey(resource);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        resourceMapper.deleteByPrimaryKey(id);
    }


}
