package com.dyt.server.service;

import com.dyt.server.domain.Chapter;
import com.dyt.server.domain.ChapterExample;
import com.dyt.server.dto.ChapterDto;
import com.dyt.server.mapper.ChapterMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChapterService {

    @Resource
    private ChapterMapper chapterMapper;


    public List<ChapterDto> list() {
        ChapterExample chapterExample = new ChapterExample();
        List<Chapter> chapters = chapterMapper.selectByExample(chapterExample);
        List<ChapterDto> chapterDtos = new ArrayList<ChapterDto>();

        for (int i = 0, l = chapters.size(); i < l; i++) {
            Chapter chapter = chapters.get(i);
            ChapterDto chapterDto = new ChapterDto();
            BeanUtils.copyProperties(chapter, chapterDto);
            chapterDtos.add(chapterDto);

        }

        return chapterDtos;
    }


}
