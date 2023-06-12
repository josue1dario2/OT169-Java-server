package com.alkemy.ong.service;


import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.entity.News;

import java.util.Map;

public  interface  NewsService {

     NewsDto save(NewsDto newsDto) throws Exception;

    void delete(String id);

    NewsDto getNewsById(String id) throws Exception;

    NewsDto updateNews(String id, NewsDto newsDto);

    News getOne(String id) throws Exception;

    Map<String, Object> getAllPages(Integer page) throws Exception;
}
