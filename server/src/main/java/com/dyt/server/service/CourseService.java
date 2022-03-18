package com.dyt.server.service;

import com.dyt.server.domain.Course;
import com.dyt.server.domain.CourseExample;
import com.dyt.server.dto.CourseDto;
import com.dyt.server.dto.PageDto;
import com.dyt.server.mapper.CourseMapper;
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
public class CourseService {

    @Resource
    private CourseMapper courseMapper;

    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CourseExample courseExample = new CourseExample();

        courseExample.setOrderByClause("sort asc");
        List<Course> courses = courseMapper.selectByExample(courseExample);

        PageInfo<Course> pageInfo = new PageInfo<>(courses);
        pageDto.setTotal(pageInfo.getTotal());
        List
                <CourseDto> courseDtos = CopyUtil.copyList(courses, CourseDto.class);

        pageDto.setList(courseDtos);

    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(CourseDto courseDto) {
        Course course = new Course();
        BeanUtils.copyProperties(courseDto, course);
        if (StringUtils.isEmpty(courseDto.getId())) {
            this.insert(course);
        } else {
            this.update(course);
        }
    }

    /**
     * 新增
     */
    public void insert(Course course) {

        Date now = new Date();
        course.setCreatedAt(now);
        course.setUpdatedAt(now);
        course.setId(UuidUtil.getShortUuid());
        courseMapper.insert(course);
    }

    /**
     * 更新
     */
    public void update(Course course) {
        course.setUpdatedAt(new Date());

        courseMapper.updateByPrimaryKey(course);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        courseMapper.deleteByPrimaryKey(id);
    }


}
