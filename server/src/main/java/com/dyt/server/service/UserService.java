package com.dyt.server.service;

import com.dyt.server.domain.User;
import com.dyt.server.domain.UserExample;
import com.dyt.server.dto.UserDto;
import com.dyt.server.dto.PageDto;
import com.dyt.server.mapper.UserMapper;
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
public class UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        UserExample userExample = new UserExample();

        List<User> users = userMapper.selectByExample(userExample);

        PageInfo<User> pageInfo = new PageInfo<>(users);
        pageDto.setTotal(pageInfo.getTotal());
        List
                <UserDto> userDtos = CopyUtil.copyList(users, UserDto.class);

        pageDto.setList(userDtos);

    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        if (StringUtils.isEmpty(userDto.getId())) {
            this.insert(user);
        } else {
            this.update(user);
        }
    }

    /**
     * 新增
     */
    public void insert(User user) {


        user.setId(UuidUtil.getShortUuid());
        userMapper.insert(user);
    }

    /**
     * 更新
     */
    public void update(User user) {

        userMapper.updateByPrimaryKey(user);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        userMapper.deleteByPrimaryKey(id);
    }


}
