package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;

import java.util.List;

/**
 * @author baiziyu
 * @time 2019-11-19 19:19
 */
public interface CategoryDao {

    /**
     * find all
     * @return
     */
    public List<Category> findAll();
}
