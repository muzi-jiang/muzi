package com.muzi.webmagic.service.impl;

import com.muzi.webmagic.entity.Title;
import com.muzi.webmagic.entity.TitleClass;
import com.muzi.webmagic.mapper.TitleMapper;
import com.muzi.webmagic.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class TitleImplService implements TitleService {

    @Autowired
    private TitleMapper titleMapper;

    @Override
    public void saveTitle(Title title) {
        titleMapper.saveTitle(title);
    }

    @Override
    public void saveTitleClass(TitleClass title) {
        titleMapper.saveTitleClass(title);
    }
}
