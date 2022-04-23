package com.dyt.server.service;

import com.dyt.server.domain.Role;
import com.dyt.server.domain.RoleExample;
import com.dyt.server.domain.RoleResource;
import com.dyt.server.domain.RoleResourceExample;
import com.dyt.server.dto.PageDto;
import com.dyt.server.dto.RoleDto;
import com.dyt.server.mapper.RoleMapper;
import com.dyt.server.mapper.RoleResourceMapper;
import com.dyt.server.util.CopyUtil;
import com.dyt.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;


@Service
public class RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleResourceMapper roleResourceMapper;
    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RoleExample roleExample = new RoleExample();

        List<Role> roles = roleMapper.selectByExample(roleExample);

        PageInfo<Role> pageInfo = new PageInfo<>(roles);
        pageDto.setTotal(pageInfo.getTotal());
        List
                <RoleDto> roleDtos = CopyUtil.copyList(roles, RoleDto.class);

        pageDto.setList(roleDtos);

    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(RoleDto roleDto) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDto, role);
        if (StringUtils.isEmpty(roleDto.getId())) {
            this.insert(role);
        } else {
            this.update(role);
        }
    }

    /**
     * 新增
     */
    public void insert(Role role) {


        role.setId(UuidUtil.getShortUuid());
        roleMapper.insert(role);
    }

    /**
     * 更新
     */
    public void update(Role role) {

        roleMapper.updateByPrimaryKey(role);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        roleMapper.deleteByPrimaryKey(id);
    }


    /**
     * 按角色保存资源
     */
    public void saveResource(RoleDto roleDto) {
        String roleId = roleDto.getId();
        List<String> resourceIds = roleDto.getResourceIds();
        // 清空库中所有的当前角色下的记录
        RoleResourceExample example = new RoleResourceExample();
        example.createCriteria().andIdEqualTo(roleId);
        roleResourceMapper.deleteByExample(example);

        //保存角色资源
        for (int i = 0; i < resourceIds.size(); i++) {
            RoleResource roleResource = new RoleResource();
            roleResource.setId(UuidUtil.getShortUuid());
            roleResource.setRoleId(roleId);
            roleResource.setResourceId(resourceIds.get(i));
            roleResourceMapper.insert(roleResource);
        }
    }


}
