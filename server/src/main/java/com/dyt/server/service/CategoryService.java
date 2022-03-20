package com.dyt.server.service;

import com.dyt.server.domain.Category;
import com.dyt.server.domain.CategoryExample;
import com.dyt.server.dto.CategoryDto;
import com.dyt.server.dto.PageDto;
import com.dyt.server.mapper.CategoryMapper;
import com.dyt.server.util.CopyUtil;
import com.dyt.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;


@Service
public class CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 列表查询
     */
    public List<CategoryDto> all() {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
        List<CategoryDto> categoryDtoList = CopyUtil.copyList(categoryList, CategoryDto.class);
        return categoryDtoList;
    }

    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CategoryExample categoryExample = new CategoryExample();

        categoryExample.setOrderByClause("sort asc");
        List<Category> categorys = categoryMapper.selectByExample(categoryExample);

        PageInfo<Category> pageInfo = new PageInfo<>(categorys);
        pageDto.setTotal(pageInfo.getTotal());
        List<CategoryDto> categoryDtos = CopyUtil.copyList(categorys, CategoryDto.class);

        pageDto.setList(categoryDtos);

    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(CategoryDto categoryDto) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDto, category);
        if (StringUtils.isEmpty(categoryDto.getId())) {
            this.insert(category);
        } else {
            this.update(category);
        }
    }

    /**
     * 新增
     */
    public void insert(Category category) {


        category.setId(UuidUtil.getShortUuid());
        categoryMapper.insert(category);
    }

    /**
     * 更新
     */
    public void update(Category category) {

        categoryMapper.updateByPrimaryKey(category);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        categoryMapper.deleteByPrimaryKey(id);
    }


}
