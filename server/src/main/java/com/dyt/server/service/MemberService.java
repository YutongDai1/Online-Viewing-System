package com.dyt.server.service;

import com.dyt.server.domain.Member;
import com.dyt.server.domain.MemberExample;
import com.dyt.server.dto.MemberDto;
import com.dyt.server.dto.PageDto;
import com.dyt.server.mapper.MemberMapper;
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
public class MemberService {

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


}
