package com.dyt.server.service;

import com.dyt.server.domain.Sms;
import com.dyt.server.domain.SmsExample;
import com.dyt.server.dto.SmsDto;
import com.dyt.server.dto.PageDto;
import com.dyt.server.mapper.SmsMapper;
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
public class SmsService {

    @Resource
    private SmsMapper smsMapper;

    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        SmsExample smsExample = new SmsExample();

        List<Sms> smss = smsMapper.selectByExample(smsExample);

        PageInfo<Sms> pageInfo = new PageInfo<>(smss);
        pageDto.setTotal(pageInfo.getTotal());
        List
                <SmsDto> smsDtos = CopyUtil.copyList(smss, SmsDto.class);

        pageDto.setList(smsDtos);

    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(SmsDto smsDto) {
        Sms sms = new Sms();
        BeanUtils.copyProperties(smsDto, sms);
        if (StringUtils.isEmpty(smsDto.getId())) {
            this.insert(sms);
        } else {
            this.update(sms);
        }
    }

    /**
     * 新增
     */
    public void insert(Sms sms) {

        Date now = new Date();

        sms.setId(UuidUtil.getShortUuid());
        smsMapper.insert(sms);
    }

    /**
     * 更新
     */
    public void update(Sms sms) {

        smsMapper.updateByPrimaryKey(sms);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        smsMapper.deleteByPrimaryKey(id);
    }


}
