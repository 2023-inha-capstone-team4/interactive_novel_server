package com.capstone.interactive_novel.common.utils;

import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@UtilityClass
public class FileConvertUtils {
    public static String fileToLobConverter(MultipartFile file)  {
        byte[] bytes;

        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new String(bytes);
    }
}
