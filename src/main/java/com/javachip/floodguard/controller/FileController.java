package com.javachip.floodguard.controller;

import com.javachip.floodguard.service.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/upload")
public class FileController {

    @Autowired
    private UploadFileService uploadFileService;

    @PostMapping("/file")
    public String fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        uploadFileService.fileupload(file);



        return "파일이 업로드 되었습니다.";
    }
}
