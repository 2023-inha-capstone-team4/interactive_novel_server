package com.capstone.interactive_novel.common.utils;

import lombok.experimental.UtilityClass;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@UtilityClass
public class FileUtils {
    public static String fileToLobConverter(MultipartFile file)  {
        byte[] bytes;

        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new String(bytes);
    }

    public static boolean checkExtension(MultipartFile file, List<String> extensionList) {
        String fileName = file.getOriginalFilename();
        if(ObjectUtils.isEmpty(fileName)) {
            return false;
        }
        fileName = fileName.toLowerCase();

        for (String s : extensionList) {
            if (fileName.endsWith(s)) {
                return true;
            }
        }
        return false;
    }
}
