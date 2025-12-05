package com.example.onlinecodeeditor.controller;
import com.example.onlinecodeeditor.service.CodeExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CodeExecutionController {

    @Autowired
    private CodeExecutionService codeExecutionService;

    @PostMapping("/execute")
    public Map<String, Object> execute(@RequestBody Map<String, String> request){
        String sourceCode = request.get("sourceCode");
        String input = request.getOrDefault("input", "");
        return codeExecutionService.executeJavaCode(sourceCode, input);
    }
}
