package com.dyt.server.service;

import com.dyt.server.domain.MemberCourse;
import com.dyt.server.domain.MemberCourseExample;
import com.dyt.server.dto.MemberCourseDto;
import com.dyt.server.dto.PageDto;
import com.dyt.server.mapper.MemberCourseMapper;
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

import java.util.Date;

@Service
public class MemberCourseService {

    @Resource
    private MemberCourseMapper memberCourseMapper;

    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        MemberCourseExample memberCourseExample = new MemberCourseExample();

        List<MemberCourse> memberCourses = memberCourseMapper.selectByExample(memberCourseExample);

        PageInfo<MemberCourse> pageInfo = new PageInfo<>(memberCourses);
        pageDto.setTotal(pageInfo.getTotal());
        List
                <MemberCourseDto> memberCourseDtos = CopyUtil.copyList(memberCourses, MemberCourseDto.class);

        pageDto.setList(memberCourseDtos);

    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(MemberCourseDto memberCourseDto) {
        MemberCourse memberCourse = new MemberCourse();
        BeanUtils.copyProperties(memberCourseDto, memberCourse);
        if (StringUtils.isEmpty(memberCourseDto.getId())) {
            this.insert(memberCourse);
        } else {
            this.update(memberCourse);
        }
    }

    /**
     * 新增
     */
    public void insert(MemberCourse memberCourse) {

        Date now = new Date();

        memberCourse.setId(UuidUtil.getShortUuid());
        memberCourseMapper.insert(memberCourse);
    }

    /**
     * 更新
     */
    public void update(MemberCourse memberCourse) {

        memberCourseMapper.updateByPrimaryKey(memberCourse);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        memberCourseMapper.deleteByPrimaryKey(id);
    }


}
