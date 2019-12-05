package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;

import java.util.List;

/**
 * @author baiziyu
 * @time 2019-11-19 19:22
 */
public interface CategoryService {
    public List<Category> findAll();
}
