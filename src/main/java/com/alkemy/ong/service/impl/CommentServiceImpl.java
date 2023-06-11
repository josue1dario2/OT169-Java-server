package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.CommentRequestDto;
import com.alkemy.ong.dto.CommentResponseDto;
import com.alkemy.ong.entity.Comment;
import com.alkemy.ong.entity.News;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.ICommentService;
import com.alkemy.ong.service.NewsService;
import com.alkemy.ong.service.UserService;
import com.alkemy.ong.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private CommentRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private NewsService newsService;


    @Override
    public List<CommentResponseDto> getAllComments() {
        return repository.findAll(Sort.by("timestamp").descending())
                         .stream()
                         .map(comment -> Mapper.mapToDto(comment, new CommentResponseDto()))
                         .collect(Collectors.toList());

    }

    @Override
    public ResponseEntity<Void> addComment(CommentRequestDto commentRequestDto) {
        Optional<User> optionalUser = userRepository.findById(commentRequestDto.getUser_id());
        Optional<News> optionalNews = newsRepository.findById(commentRequestDto.getPost_id());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build(); //return 404 if the given user does not exist
        } else if (optionalNews.isEmpty()) {
            return ResponseEntity.notFound().build(); //return 404 if the given post does not exist
        }

        repository.save(new Mapper().mapFromDto(commentRequestDto,new Comment(),optionalUser.get(),optionalNews.get()));

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> putComment(String id, CommentRequestDto commentRequestDto) {
        Optional<User> optionalUser = optionalUser = userRepository.findById(commentRequestDto.getUser_id());//check if new user id exits
        Optional<News> optionalNews = optionalNews = newsRepository.findById(commentRequestDto.getPost_id());//check if new news id exists
        Optional<Comment> optionalComment = repository.findById(id);//check if the comment exits

        if(optionalComment.isEmpty() || optionalUser.isEmpty() || optionalNews.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();//return 404 if the given id comment,id news or id user does not exist
        }

        userService.isUserAllowed(optionalComment.get().getUser_id().getId()); //return 403 if the user is not the one who wrote the comment or authenticated user has no admin role
        repository.save(new Mapper().mapFromDto(commentRequestDto,optionalComment.get(),optionalUser.get(),optionalNews.get()));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    @Transactional
    public ResponseEntity<Void> delete(String id){
        if(!repository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        Optional<Comment> comment  = repository.findById(id);

        userService.isUserAllowed(comment.get().getUser_id().getId());

        repository.delete(comment.get());
        return ResponseEntity.ok().build();
    }

    @Override
    public List<CommentResponseDto> getAllCommentsNews(String idNews) throws Exception {
        News entity = newsService.getOne(idNews);
        if(entity == null){
            throw new Exception("Id not found");
        }
        List<Comment> entities = repository.findByNewsId(entity);
        if(entities.isEmpty()){
            throw new Exception("Empty list");
        }
        return entities.stream()
                .map(comment -> Mapper.mapToDto(comment, new CommentResponseDto()))
                .collect(Collectors.toList());

    }
}
