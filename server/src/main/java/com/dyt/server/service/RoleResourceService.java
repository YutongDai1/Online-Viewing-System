package com.dyt.server.service;

import com.dyt.server.domain.RoleResource;
import com.dyt.server.domain.RoleResourceExample;
import com.dyt.server.dto.RoleResourceDto;
import com.dyt.server.dto.PageDto;
import com.dyt.server.mapper.RoleResourceMapper;
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
public class RoleResourceService {

    @Resource
    private RoleResourceMapper roleResourceMapper;

    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RoleResourceExample roleResourceExample = new RoleResourceExample();

        List<RoleResource> roleResources = roleResourceMapper.selectByExample(roleResourceExample);

        PageInfo<RoleResource> pageInfo = new PageInfo<>(roleResources);
        pageDto.setTotal(pageInfo.getTotal());
        List
                <RoleResourceDto> roleResourceDtos = CopyUtil.copyList(roleResources, RoleResourceDto.class);

        pageDto.setList(roleResourceDtos);

    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(RoleResourceDto roleResourceDto) {
        RoleResource roleResource = new RoleResource();
        BeanUtils.copyProperties(roleResourceDto, roleResource);
        if (StringUtils.isEmpty(roleResourceDto.getId())) {
            this.insert(roleResource);
        } else {
            this.update(roleResource);
        }
    }

    /**
     * 新增
     */
    public void insert(RoleResource roleResource) {


        roleResource.setId(UuidUtil.getShortUuid());
        roleResourceMapper.insert(roleResource);
    }

    /**
     * 更新
     */
    public void update(RoleResource roleResource) {

        roleResourceMapper.updateByPrimaryKey(roleResource);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        roleResourceMapper.deleteByPrimaryKey(id);
    }


}
