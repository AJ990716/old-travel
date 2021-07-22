package com.willpower.travel.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_cms_insurance")
public class Insurance {
  @TableId(value = "id",type = IdType.ASSIGN_UUID)
  private String id;
  private String addUserId;
  private String addTime;
  private long deleteStatus;
  private String modifyUserId;
  private String modifyTime;
  private String title;
  private long insuranceCompany;
  private double price;
  private long type;
  private String resume;
  private long state;
  private String imgUrl;
}
