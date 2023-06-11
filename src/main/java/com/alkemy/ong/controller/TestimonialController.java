package com.alkemy.ong.controller;


import com.alkemy.ong.dto.TestimonialDto;
import com.alkemy.ong.repository.TestimonialRepository;
import com.alkemy.ong.service.TestimonialService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/testimonials")
@Api(tags = "Testimonials endpoints")
public class TestimonialController {
    private final TestimonialService testimonialService;
    @Autowired
    public TestimonialController(TestimonialService testimonialService){
        this.testimonialService = testimonialService;
    }



    @ApiOperation(value = "Add a new testimonial and return it")
    @ApiResponses(value = { @ApiResponse(code = 201 , message= "Create Testimonial"),
            @ApiResponse(code = 401, message = "There aren't authorization headers"),
            @ApiResponse(code = 403, message = "Forbidden/Accessing with invalid role"),
            @ApiResponse(code = 400 , message = "Bad request/Invalid field")
    })
    @PostMapping
    public ResponseEntity<TestimonialDto> save(@Valid @RequestBody TestimonialDto testimonialDto){
        TestimonialDto savedTestimonials = testimonialService.save(testimonialDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTestimonials);
    }

    @ApiOperation(value = "Delete a testimonial by id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Delete Testimonial by id"),
            @ApiResponse(code = 404, message = "Testimonial not found"),
            @ApiResponse(code = 403, message = "Forbidden/Accessing with invalid role" ),
            @ApiResponse(code = 401, message = "There aren't authorization headers")})
    @ApiImplicitParam(name = "id",
                    value = "Id of the testimonial we want to delete",
                    required = true,
                    allowEmptyValue = false,
                    paramType = "path",
                    dataTypeClass = String.class)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        testimonialService.delete(id);
        return  ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "Update a testimonial by id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Update testimonial"),
            @ApiResponse(code = 404, message = "Testimonial not found"),
            @ApiResponse(code = 403, message = "Forbidden/Accessing with invalid role" ),
            @ApiResponse(code = 400 , message = "Bad request/Invalid field"),
            @ApiResponse(code = 401, message = "There aren't authorization headers")

    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id",
                    value = "Id of the testimonial we want to modify",
                    required = true,
                    allowEmptyValue = false,
                    paramType = "path",
                    dataTypeClass = String.class)})
    @PutMapping("/{id}")
    public ResponseEntity<TestimonialDto> updateTestimonials(@Valid @RequestBody TestimonialDto testimonialDto, @PathVariable String id) throws ResponseStatusException{
        try {
            testimonialService.updateTestimonials(testimonialDto, id);
            return ResponseEntity.status(HttpStatus.OK).body(testimonialDto);
        }   catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Get a paginated list of testimonials")
    @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Return a paginated list of testimonials"),
    @ApiResponse(code = 401, message = "There aren't authorization headers")})
    @GetMapping("/pages")
    public ResponseEntity<Map<String, Object>> getAllPage(@RequestParam(defaultValue = "0") int page) {
        try {
            Map<String, Object> testimonial = testimonialService.getAllPages(page);
            return ResponseEntity.ok(testimonial);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
