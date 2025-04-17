package com.ftpl.testGenAI.controllers;

import com.ftpl.testGenAI.model.VectorDocument;
import com.ftpl.testGenAI.model.VectorRequest;
import com.ftpl.testGenAI.services.VectorDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/vector")
public class VectorDBController {

    @Autowired
    private VectorDBService vectorDBService;

    @PostMapping("/insert")
    public String insertVectorData(@RequestBody VectorRequest vectorRequest) {
        // Call the service to insert the vector data
        vectorDBService.insertVectorData(vectorRequest.getId(), vectorRequest.getVector(), vectorRequest.getMetadata());
        return "Vector data inserted successfully";
    }

    // Get Vector Data by ID
    @GetMapping("/get/{id}")
    public List<VectorDocument> getVectorDataById(@PathVariable String id) {
        return vectorDBService.getVectorDataById(id);
    }

    // Update Vector Data by ID
    @PutMapping("/update/{id}")
    public String updateVectorData(@PathVariable String id, @RequestBody VectorRequest vectorRequest) {
        vectorDBService.updateVectorData(id, vectorRequest.getVector(), vectorRequest.getMetadata());
        return "Vector data updated successfully";
    }
}

