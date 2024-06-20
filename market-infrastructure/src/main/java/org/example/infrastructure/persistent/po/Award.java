package org.example.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

@Data
public class Award {

    //自增id
    private Long id;

    //奖品id
    private String awardId;

    //奖品标识
    private String awardKey;

    //奖品配置
    private String awardConfig;

    //奖品描述
    private String awardDesc;

    //创建时间
    private Date createTime;

    //修改时间
    private Date updateTime;
}
