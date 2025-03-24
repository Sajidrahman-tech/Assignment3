package com.example.demo.Service;

import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {


    public void storeFile(String fileName, String data) throws IOException {
//        File directory = new File(storagePath);
//        if (!directory.exists()) {
//            directory.mkdirs();
//        }
        System.out.println("Indie the service store fucntion File name is "+fileName+"sajid");
        String filePath ="/Saajid_PV_dir/"+fileName;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(data);
            System.out.println("File created successfully: " +"/files/"+ fileName);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
