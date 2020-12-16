package com.muzi.flowable.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: libin
 * @Date: 2020/12/03 10:53
 */
@Data
public class Leave {

    String userId;
    String day;
    String reason;
}
