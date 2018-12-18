package com.wzw.demo.vo;

public class Order {
    private Integer groupId,cusId;//status为 已完成 进行中 这两种

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getCusId() {
        return cusId;
    }

    public void setCusId(Integer cusId) {
        this.cusId = cusId;
    }
}
