package com.dyt.server.service;

import com.dyt.server.domain.Member;
import com.dyt.server.domain.MemberExample;
import com.dyt.server.dto.LoginMemberDto;
import com.dyt.server.dto.MemberDto;
import com.dyt.server.dto.PageDto;
import com.dyt.server.exception.BusinessException;
import com.dyt.server.exception.BusinessExceptionCode;
import com.dyt.server.mapper.MemberMapper;
import com.dyt.server.util.CopyUtil;
import com.dyt.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class MemberService {
    private static final Logger LOG = LoggerFactory.getLogger(MemberService.class);
    @Resource
    private MemberMapper memberMapper;

    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        MemberExample memberExample = new MemberExample();

        List<Member> members = memberMapper.selectByExample(memberExample);

        PageInfo<Member> pageInfo = new PageInfo<>(members);
        pageDto.setTotal(pageInfo.getTotal());
        List
                <MemberDto> memberDtos = CopyUtil.copyList(members, MemberDto.class);

        pageDto.setList(memberDtos);

    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(MemberDto memberDto) {
        Member member = new Member();
        BeanUtils.copyProperties(memberDto, member);
        if (StringUtils.isEmpty(memberDto.getId())) {
            this.insert(member);
        } else {
            this.update(member);
        }
    }

    /**
     * 新增
     */
    public void insert(Member member) {

        Date now = new Date();

        member.setId(UuidUtil.getShortUuid());
        member.setRegisterTime(now);
        memberMapper.insert(member);
    }

    /**
     * 更新
     */
    public void update(Member member) {

        memberMapper.updateByPrimaryKey(member);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        memberMapper.deleteByPrimaryKey(id);
    }


    /**
     * 按手机号查找
     *
     * @param mobile
     * @return
     */
    public Member selectByMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return null;
        }
        MemberExample example = new MemberExample();
        example.createCriteria().andMobileEqualTo(mobile);
        List<Member> memberList = memberMapper.selectByExample(example);
        if (memberList == null || memberList.size() == 0) {
            return null;
        } else {
            return memberList.get(0);
        }

    }

    /**
     * 登录
     *
     * @param memberDto
     */
    public LoginMemberDto login(MemberDto memberDto) {
        Member member = selectByMobile(memberDto.getMobile());
        if (member == null) {
            LOG.info("手机号不存在, {}", memberDto.getMobile());
            throw new BusinessException(BusinessExceptionCode.LOGIN_MEMBER_ERROR);
        } else {
            if (member.getPassword().equals(memberDto.getPassword())) {
                // 登录成功
                LoginMemberDto loginMemberDto = CopyUtil.copy(member, LoginMemberDto.class);
                return loginMemberDto;
            } else {
                LOG.info("密码不对, 输入密码：{}, 数据库密码：{}", memberDto.getPassword(), member.getPassword());
                throw new BusinessException(BusinessExceptionCode.LOGIN_MEMBER_ERROR);
            }
        }
    }

    /**
     * 按手机号查找
     *
     * @param mobile
     * @return
     */
    public MemberDto findByMobile(String mobile) {
        Member member = this.selectByMobile(mobile);
        return CopyUtil.copy(member, MemberDto.class);
    }

    /**
     * 重置密码
     */
    public void resetPassword(MemberDto memberDto) throws BusinessException {
        Member memberDb = this.selectByMobile(memberDto.getMobile());
        if (memberDb == null) {
            throw new BusinessException(BusinessExceptionCode.MEMBER_NOT_EXIST);
        } else {
            Member member = new Member();
            member.setId(memberDb.getId());
            member.setPassword(memberDto.getPassword());
            memberMapper.updateByPrimaryKeySelective(member);
        }
    }
}
