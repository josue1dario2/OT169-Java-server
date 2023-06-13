package com.alkemy.ong.controller;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.NewsService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Api("News endpoints")
@RestController
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;
    @Autowired
    public NewsController(NewsService newsService){
        this.newsService = newsService;
    }

    @ApiOperation(value = "Add News and returns the new created ")
    @ApiResponses( value={
            @ApiResponse(code = 400, message = "Bad Request / Invalid field"),
            @ApiResponse(code = 401, message ="Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden" ),
            @ApiResponse(code = 201, message = "Created", response = NewsDto.class)
    })
    @ApiImplicitParams( value = {
            @ApiImplicitParam (name = "NewsDto", value = "Entity to create",
                                required = true,
                                allowEmptyValue = false,
                                paramType = "application/json",
                                dataTypeClass = NewsDto.class)
    })
    @PostMapping(produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<NewsDto> save( @Valid @RequestBody NewsDto news) throws Exception {

        NewsDto savedNews= newsService.save(news);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedNews);
    }


    @ApiOperation(value = "Get News details by ID ")
    @ApiResponses( value={

            @ApiResponse(code = 401, message ="Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden" ),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 200, message = "Ok", response = NewsDto.class)
    })
    @GetMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<NewsDto> getNewsById(@ApiParam (value = "News id found", required = true, allowEmptyValue = false)
                                                   @PathVariable String id) throws Exception {
        try{
            NewsDto newsDto= newsService.getNewsById(id);
            return ResponseEntity.status(HttpStatus.OK).body(newsDto);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }



 
    @ApiOperation(value = "Update News if exist ")
    @ApiResponses( value={

            @ApiResponse(code = 401, message ="Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden" ),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 200, message = "Ok", response = NewsDto.class)
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id",
                    value = "Id of News",
                    required = true, allowEmptyValue = false,
                    paramType = "path", dataTypeClass = String.class,
                    example = "1"),
            @ApiImplicitParam(name= "RequestBody",
                    value = "Update params of News",
                    required = true,
                    paramType = "RequestBody",
                    dataTypeClass = NewsDto.class
                    )       })
    @PutMapping(value = "/{id}", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<NewsDto> updateNews(@PathVariable String id,@Valid @RequestBody NewsDto newsDto) throws ResponseStatusException {
            newsService.updateNews( id, newsDto);
        return ResponseEntity.status(HttpStatus.OK).body(newsDto);
    }



    @ApiOperation(value = "Delete News if exist ")
    @ApiResponses( value={

            @ApiResponse(code = 401, message ="Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden" ),
            @ApiResponse(code = 404, message = "Not Found", response = ResponseStatusException.class),
            @ApiResponse(code = 200, message = "Ok", response = ResponseEntity.class)
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id",
                    value = "Id of News",
                    required = true, allowEmptyValue = false,
                    paramType = "path", dataTypeClass = String.class,
                    example = "1"),
    })
    @DeleteMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<Void>delete(@PathVariable String id) {
        newsService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @ApiOperation(value = "Return News paginated list.")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message ="Unauthorized"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ResponseEntity.class),
            @ApiResponse(code = 200, message = "Ok", response = ResponseEntity.class),
            })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "page", value = "Page of the list",
                    required = true,
                    paramType = "query",
                    dataTypeClass = Integer.class,
                    example = "0"), })
    @GetMapping(produces ={"application/json"} )
    public ResponseEntity<Map<String, Object>> getAllPage(@RequestParam(defaultValue = "0") Integer page){
        try {
            Map<String, Object> news = newsService.getAllPages(page);
            return ResponseEntity.ok(news);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

