package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

/**
 * @author baiziyu
 * @time 2019-11-25 13:51
 */
public interface RouteService {
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String ranme);
}
