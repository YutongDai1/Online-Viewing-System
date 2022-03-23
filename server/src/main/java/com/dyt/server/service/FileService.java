package com.dyt.server.service;

import com.dyt.server.domain.File;
import com.dyt.server.domain.FileExample;
import com.dyt.server.dto.FileDto;
import com.dyt.server.dto.PageDto;
import com.dyt.server.mapper.FileMapper;
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
public class FileService {

    @Resource
    private FileMapper fileMapper;

    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        FileExample fileExample = new FileExample();

        List<File> files = fileMapper.selectByExample(fileExample);

        PageInfo<File> pageInfo = new PageInfo<>(files);
        pageDto.setTotal(pageInfo.getTotal());
        List
                <FileDto> fileDtos = CopyUtil.copyList(files, FileDto.class);

        pageDto.setList(fileDtos);

    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(FileDto fileDto) {
        File file = new File();
        BeanUtils.copyProperties(fileDto, file);
        if (StringUtils.isEmpty(fileDto.getId())) {
            this.insert(file);
        } else {
            this.update(file);
        }
    }

    /**
     * 新增
     */
    public void insert(File file) {

        Date now = new Date();

        file.setCreatedAt(now);
        file.setUpdatedAt(now);
        file.setId(UuidUtil.getShortUuid());
        fileMapper.insert(file);
    }

    /**
     * 更新
     */
    public void update(File file) {
        file.setUpdatedAt(new Date());

        fileMapper.updateByPrimaryKey(file);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        fileMapper.deleteByPrimaryKey(id);
    }


}
