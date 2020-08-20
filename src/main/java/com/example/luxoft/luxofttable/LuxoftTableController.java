package com.example.luxoft.luxofttable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class LuxoftTableController {

    private LuxoftTableService luxoftTableService;

    @Autowired
    public LuxoftTableController(LuxoftTableService luxoftTableService) {
        this.luxoftTableService = luxoftTableService;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {

        luxoftTableService.saveTableDataFromFile(file);

        return "Records from file saved";
    }

    @GetMapping(path = "/")
    @ResponseBody
    public LuxoftTable getSingleRecord(@RequestParam("id") String id) {

        if (id.isEmpty()) {
            throw new IllegalArgumentException("Request param can't be empty");
        }

        return luxoftTableService.getById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @DeleteMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String deleteSingleRecord(@RequestParam("id") String id) {

        if (id.isEmpty()) {
            throw new IllegalArgumentException("Request param can't be empty");
        }

        luxoftTableService.deleteSingleRecord(id);

        return "Record deleted";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleEmptyParam(IllegalArgumentException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoItemFound(EmptyResultDataAccessException ex) {
        return ex.getMessage();
    }
}
