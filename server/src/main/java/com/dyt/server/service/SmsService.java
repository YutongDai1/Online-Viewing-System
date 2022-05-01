package com.dyt.server.service;

import com.dyt.server.domain.Sms;
import com.dyt.server.domain.SmsExample;
import com.dyt.server.dto.PageDto;
import com.dyt.server.dto.SmsDto;
import com.dyt.server.enums.SmsStatusEnum;
import com.dyt.server.exception.BusinessException;
import com.dyt.server.exception.BusinessExceptionCode;
import com.dyt.server.mapper.SmsMapper;
import com.dyt.server.util.CopyUtil;
import com.dyt.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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

    /**
     * 发送短信验证码
     * 同手机号同操作1分钟内不能重复发送短信
     *
     * @param smsDto
     */
    public void sendCode(SmsDto smsDto) {
        SmsExample example = new SmsExample();
        SmsExample.Criteria criteria = example.createCriteria();
        // 查找1分钟内有没有同手机号同操作发送记录且没被用过
        criteria.andMobileEqualTo(smsDto.getMobile())
                .andUseEqualTo(smsDto.getUse())
                .andStatusEqualTo(SmsStatusEnum.NOT_USED.getCode())
                .andAtGreaterThan(new Date(new Date().getTime() - 1 * 60 * 1000));
        List<Sms> smsList = smsMapper.selectByExample(example);

        if (smsList == null || smsList.size() == 0) {
            saveAndSend(smsDto);
        } else {
            throw new BusinessException(BusinessExceptionCode.MOBILE_CODE_TOO_FREQUENT);
        }
    }

    /**
     * 保存并发送短信验证码
     *
     * @param smsDto
     */
    private void saveAndSend(SmsDto smsDto) {
        // 生成6位数字
        String code = String.valueOf((int) (((Math.random() * 9) + 1) * 100000));
        smsDto.setAt(new Date());
        smsDto.setStatus(SmsStatusEnum.NOT_USED.getCode());
        smsDto.setCode(code);
        this.save(smsDto);

        // TODO 调第三方短信接口发送短信
    }
}
