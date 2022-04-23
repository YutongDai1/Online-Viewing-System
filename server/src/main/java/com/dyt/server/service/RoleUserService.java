package com.dyt.server.service;

import com.dyt.server.domain.RoleUser;
import com.dyt.server.domain.RoleUserExample;
import com.dyt.server.dto.RoleUserDto;
import com.dyt.server.dto.PageDto;
import com.dyt.server.mapper.RoleUserMapper;
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
public class RoleUserService {

    @Resource
    private RoleUserMapper roleUserMapper;

    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RoleUserExample roleUserExample = new RoleUserExample();

        List<RoleUser> roleUsers = roleUserMapper.selectByExample(roleUserExample);

        PageInfo<RoleUser> pageInfo = new PageInfo<>(roleUsers);
        pageDto.setTotal(pageInfo.getTotal());
        List
                <RoleUserDto> roleUserDtos = CopyUtil.copyList(roleUsers, RoleUserDto.class);

        pageDto.setList(roleUserDtos);

    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(RoleUserDto roleUserDto) {
        RoleUser roleUser = new RoleUser();
        BeanUtils.copyProperties(roleUserDto, roleUser);
        if (StringUtils.isEmpty(roleUserDto.getId())) {
            this.insert(roleUser);
        } else {
            this.update(roleUser);
        }
    }

    /**
     * 新增
     */
    public void insert(RoleUser roleUser) {


        roleUser.setId(UuidUtil.getShortUuid());
        roleUserMapper.insert(roleUser);
    }

    /**
     * 更新
     */
    public void update(RoleUser roleUser) {

        roleUserMapper.updateByPrimaryKey(roleUser);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        roleUserMapper.deleteByPrimaryKey(id);
    }


}
