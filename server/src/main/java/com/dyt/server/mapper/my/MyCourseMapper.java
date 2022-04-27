package com.dyt.server.mapper.my;

import com.dyt.server.dto.CourseDto;
import com.dyt.server.dto.CoursePageDto;
import com.dyt.server.dto.SortDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyCourseMapper {
    int updateTime(@Param("courseId") String courseId);


    int updateSort(SortDto sortDto);

    int moveSortsBackward(SortDto sortDto);

    int moveSortsForward(SortDto sortDto);

    List<CourseDto> list(@Param("pageDto") CoursePageDto pageDto);
}
