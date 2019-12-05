package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author baiziyu
 * @time 2019-11-19 19:23
 */
public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        Jedis jedis = JedisUtil.getJedis();
        //Set<String> categorys = jedis.zrange("category", 0, -1);
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);

        List<Category> all = null;
        if(categorys == null || categorys.size() == 0) {
            all = categoryDao.findAll();
            for (int i = 0; i < all.size(); i++) {
                jedis.zadd("category", all.get(i).getCid(), all.get(i).getCname());
            }
        } else {
            all = new ArrayList<Category>();
            for(Tuple tuple : categorys) {
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int)tuple.getScore());
                all.add(category);
            }
        }
        return all;
    }
}
