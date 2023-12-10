package com.pubo.controller;

import com.pubo.service.GenConfigService;
import com.pubo.service.GeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/generator")
public class GeneratorController {
    @Autowired
    private GeneratorService generatorService;
    @Autowired
    private GenConfigService genConfigService;

    @PostMapping("/{tableName}/{type}")
    public ResponseEntity<Object> generateCode(@PathVariable String tableName, @PathVariable Integer type, HttpServletRequest request, HttpServletResponse response){
            generatorService.generator(genConfigService.find(tableName),generatorService.getColumns(tableName));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
