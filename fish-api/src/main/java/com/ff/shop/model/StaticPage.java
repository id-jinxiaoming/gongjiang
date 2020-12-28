package com.ff.shop.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.ff.common.base.BaseModel;

@TableName(value = "tb_static_page")
public class StaticPage extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField("credit_evaluation")
    private String creditEvaluation;

    @TableField("consulting_service")
    private String consultingService;

    @TableField("about_us")
    private String aboutUs;

    @TableField("customer_service")
    private String customerService;

    private String workmates;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreditEvaluation() {
        return creditEvaluation;
    }

    public void setCreditEvaluation(String creditEvaluation) {
        this.creditEvaluation = creditEvaluation;
    }

    public String getConsultingService() {
        return consultingService;
    }

    public void setConsultingService(String consultingService) {
        this.consultingService = consultingService;
    }

    public String getAboutUs() {
        return aboutUs;
    }

    public void setAboutUs(String aboutUs) {
        this.aboutUs = aboutUs;
    }

    public String getCustomerService() {
        return customerService;
    }

    public void setCustomerService(String customerService) {
        this.customerService = customerService;
    }

    public String getWorkmates() {
        return workmates;
    }

    public void setWorkmates(String workmates) {
        this.workmates = workmates;
    }
}
