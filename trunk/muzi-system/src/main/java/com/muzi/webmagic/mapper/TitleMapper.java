package com.muzi.webmagic.mapper;

import com.muzi.webmagic.entity.Title;
import com.muzi.webmagic.entity.TitleClass;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TitleMapper {

    @Insert({"insert into t_title(name,href,image_url) values (#{name},#{href},#{imageUrl})"})
    void saveTitle(Title title);

    @Insert({"insert into t_title_class(name,time,url,sequence) values (#{name},#{time},#{url},#{sequence})"})
    void saveTitleClass(TitleClass title);
}
