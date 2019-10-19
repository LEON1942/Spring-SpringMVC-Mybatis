package com.leon.mybatis.entity;

import java.util.Date;

public class Orders {
    private String id;
    private String orderNo;
    private String orderType;
    private Integer productId;
    private String productInfo;
    private String orderMessage;
    private String files;
    private Integer factoryId;
    private String factoryName;
    private String deviceNo;
    private String robotStatus;
    private Integer payStatus;
    private Integer orderStatus;
    private Integer producingStatus;
    private Integer expressStatus;
    private String printStatus;
    private Date builtTime;
    private Date lastStatusTime;
    private String fontName;
    private String stamperContent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    public String getOrderMessage() {
        return orderMessage;
    }

    public void setOrderMessage(String orderMessage) {
        this.orderMessage = orderMessage;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public Integer getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(Integer factoryId) {
        this.factoryId = factoryId;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getRobotStatus() {
        return robotStatus;
    }

    public void setRobotStatus(String robotStatus) {
        this.robotStatus = robotStatus;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getProducingStatus() {
        return producingStatus;
    }

    public void setProducingStatus(Integer producingStatus) {
        this.producingStatus = producingStatus;
    }

    public Integer getExpressStatus() {
        return expressStatus;
    }

    public void setExpressStatus(Integer expressStatus) {
        this.expressStatus = expressStatus;
    }

    public String getPrintStatus() {
        return printStatus;
    }

    public void setPrintStatus(String printStatus) {
        this.printStatus = printStatus;
    }

    public Date getBuiltTime() {
        return builtTime;
    }

    public void setBuiltTime(Date builtTime) {
        this.builtTime = builtTime;
    }

    public Date getLastStatusTime() {
        return lastStatusTime;
    }

    public void setLastStatusTime(Date lastStatusTime) {
        this.lastStatusTime = lastStatusTime;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public String getStamperContent() {
        return stamperContent;
    }

    public void setStamperContent(String stamperContent) {
        this.stamperContent = stamperContent;
    }
}
