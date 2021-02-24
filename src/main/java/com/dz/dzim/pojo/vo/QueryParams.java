package com.dz.dzim.pojo.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dz.dzim.pojo.doman.MeetingChattingEntity;

/**
 * @author 查询类
 * @className QueryParams
 * @description TODO
 * @date 2021/2/5 10:06
 */
public class QueryParams {
    /**
     * 用户id
     */
    private Long member;
    /**
     * 客服id
     */
    private Long waiter;
    private Integer pageNum;
    private Integer pageSize;
    private Long startTime;
    private Long endTime;

    public Long getMember() {
        return member;
    }

    public void setMember(Long member) {
        this.member = member;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getWaiter() {
        return waiter;
    }

    public void setWaiter(Long waiter) {
        this.waiter = waiter;
    }

    /**
     * 组装分页信息
     *
     * @param params
     * @return
     */
    public static Page<MeetingChattingEntity> getPage(QueryParams params) {
        Integer pageNum = params.getPageNum();
        Integer pageSize = params.getPageSize();
        pageNum = null == pageNum ? 0 : pageNum;
        pageSize = null == pageSize ? 0 : pageSize;
        return new Page<>(pageNum, pageSize);
    }
}
