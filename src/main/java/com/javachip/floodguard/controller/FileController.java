package com.javachip.floodguard.controller;

import com.javachip.floodguard.service.SatisfactionService;
import com.javachip.floodguard.dto.BlackListDTO;
import com.javachip.floodguard.service.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/upload")
public class FileController {

    @Autowired
    private UploadFileService uploadFileService;

    @PostMapping("/file")
    public String fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        uploadFileService.fileupload(file);
        System.out.println(file.getName());
        System.out.println("파일 올라옴");
        return "파일이 업로드 되었습니다.";
    }
}
