package com.exostar.userprofile.controller;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import javax.validation.Valid;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.exostar.userprofile.service.UserBatchService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping ("/csv")
@Slf4j
public class CsvUploadController {
    @Autowired
    private UserBatchService userBatchService;

    @RequestMapping (path = "/upload", method = RequestMethod.POST)
    public ResponseEntity<Object> uploadCsv(@Valid @RequestParam MultipartFile file) {
        try {
            log.info("Received file");
            String destFile = System.getProperty("user.dir") + "/" + file.getOriginalFilename()
                                                                                .substring(0, file.getOriginalFilename().lastIndexOf('.')) + System
                    .currentTimeMillis() + ".csv";
            FileUtils.copyToFile(file.getInputStream(), new File(destFile));
            CompletableFuture.runAsync(()->userBatchService.processUserFile(destFile));
        } catch (IOException ioe) {
            log.error("Error saving file: {} size: {}", file.getOriginalFilename(), file.getSize());
        }
        return ResponseEntity.accepted().build();
    }

}
