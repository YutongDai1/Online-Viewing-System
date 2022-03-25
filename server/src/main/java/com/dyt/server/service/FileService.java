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
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
        File fileDb = selectByKey(fileDto.getKey());
        if (fileDb == null) {
            this.insert(file);
        } else {
            fileDb.setShardIndex(fileDto.getShardIndex());
            this.update(fileDb);
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

    public File selectByKey(String key) {
        FileExample example = new FileExample();
        example.createCriteria().andKeyEqualTo(key);
        List<File> fileList = fileMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(fileList)) {
            return null;
        } else {
            return fileList.get(0);
        }
    }


}
