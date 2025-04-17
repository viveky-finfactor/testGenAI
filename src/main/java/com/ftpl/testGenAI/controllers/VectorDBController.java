package com.ftpl.testGenAI.controllers;

import com.ftpl.testGenAI.model.VectorDocument;
import com.ftpl.testGenAI.model.VectorRequest;
import com.ftpl.testGenAI.services.VectorDBService;
import dev.langchain4j.data.document.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vector")
public class VectorDBController {

    @Autowired
    private VectorDBService vectorDBService;

    @PostMapping("/insert")
    public String insertVectorData(@RequestBody VectorRequest vectorRequest) {
        Metadata metadata = convertToMetadata(vectorRequest.getMetadata());
        vectorDBService.insertVectorData(vectorRequest.getId(), vectorRequest.getText(), metadata);
        return "Vector data inserted successfully";
    }

    @GetMapping("/get/{id}")
    public List<VectorDocument> getVectorDataById(@PathVariable String id) {
        return vectorDBService.getVectorDataById(id);
    }

    @PutMapping("/update/{id}")
    public String updateVectorData(@PathVariable String id, @RequestBody VectorRequest vectorRequest) {
        Metadata metadata = convertToMetadata(vectorRequest.getMetadata());
        vectorDBService.updateVectorData(id, vectorRequest.getText(), metadata);
        return "Vector data updated successfully";
    }

    @GetMapping("/getAll")
    public List<VectorDocument> getAllVectorData() {
        return vectorDBService.findAll();
    }

    private Metadata convertToMetadata(Map<String, Object> rawMap) {
        Metadata metadata = new Metadata();
        if (rawMap != null) {
            rawMap.forEach((key, value) -> {
                if (key != null && value != null) {
                    metadata.put(key, value.toString());
                }
            });
        }
        return metadata;
    }
}
