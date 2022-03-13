package com.dyt.business.controller.admin;

import com.dyt.server.domain.Chapter;
import com.dyt.server.dto.ChapterDto;
import com.dyt.server.dto.PageDto;
import com.dyt.server.service.ChapterService;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/admin/chapter")
public class ChapterController {

    private static final Logger LOG = LoggerFactory.getLogger(ChapterController.class);
    @Resource
    private ChapterService chapterService;

    @RequestMapping("/list")
    public PageDto list(@RequestBody PageDto pageDto) {
        LOG.info("pageDto:{}", pageDto);
        chapterService.list(pageDto);
        return pageDto;
    }

}
