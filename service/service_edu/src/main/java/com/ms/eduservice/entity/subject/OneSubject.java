package com.ms.eduservice.entity.subject;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MS
 * @create 2022-07-20-16:40
 */
@Data
public class OneSubject {
    private String id;
    private String title;

    // 二级分类列表
    private List<TwoSubject> children = new ArrayList<>();
}
