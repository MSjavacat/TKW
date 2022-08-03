package com.ms.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MS
 * @create 2022-07-21-16:08
 */
@Data
public class ChapterVo {
    private String id;
    private String title;

    // 表示小节
    private List<VideoVo> children = new ArrayList<>();
}
