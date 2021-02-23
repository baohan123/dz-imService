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

    private Long talker;
    private Integer pageNum;
    private Integer pageSize;
    private Long startTime;
    private Long endTime;

    public Long getTalker() {
        return talker;
    }

    public void setTalker(Long talker) {
        this.talker = talker;
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

    /**
     * 组装分页信息
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
