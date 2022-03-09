package com.dyt.system.service;

import com.dyt.system.domain.Test;
import com.dyt.system.mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestService {

    @Resource
    private TestMapper testMapper;


    public List<Test> list()
    {
        return testMapper.list();
    }


}
