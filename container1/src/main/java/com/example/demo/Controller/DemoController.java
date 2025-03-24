package com.example.demo.Controller;

import com.example.demo.Model.InputModel;
import com.example.demo.Model.StoreFileErrorResponse;
import com.example.demo.Model.StoreFileRequest;
import com.example.demo.Model.StoreFileResponse;
import com.example.demo.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Map;

@RestController
public class DemoController {

    @Autowired
    private FileService fileService;


    @Value("${container2.url}")
    private String container2Url;

    @GetMapping("/test")
    public String showResponse(){
        System.out.println("inside test routeß");
        return "is Working Fine inida ";
    }

    @PostMapping("/store-file")
    public ResponseEntity<Object> storeFile(@RequestBody StoreFileRequest request) {
        if (request.getFile() == null || request.getFile().isEmpty()) {
            System.out.println("File name jkdjksais "+request.getFile()+"sajid1234");
//            return ResponseEntity.badRequest().body(
//                    new StoreFileResponse(null, null, "Invalid JSON input.")
//            );

            return  ResponseEntity.badRequest().body(new StoreFileErrorResponse(null,"Invalid JSON input."));

        }

        try {
            fileService.storeFile(request.getFile(), request.getData());
            return ResponseEntity.ok(
                    new StoreFileResponse(request.getFile(), "Success.")
            );
            
        } catch (Exception e) {
            System.out.println("Error catch  "+request.getFile()+"sajid");

            return ResponseEntity.internalServerError().body(
                    new StoreFileErrorResponse(request.getFile(),
                            "Error while storing the file to the storage.")
            );
        }
    }




    @PostMapping("/calculate")
    public ResponseEntity<Object> createUser(@RequestBody InputModel input){
        if (input.getFile() == null || input.getFile() =="") {
            return ResponseEntity.badRequest().body(new ErrorResponse(input.getFile(),"Invalid JSON input."));
        }

        System.out.println("Indise the calculate File name is "+input.getFile()+"sajid");

        String filename = input.getFile();
        String filePath = "/Saajid_PV_dir/" + filename; // Path inside the container

        File file = new File(filePath);
        if (file.exists()){
            System.out.println("EXISTS"+filePath);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Object> response = restTemplate.postForEntity(container2Url, input, Object.class);
            return response;
        }
        else{
            System.out.println("NOT EXISTS"+filename);
            return ResponseEntity.badRequest().body(new ErrorResponse(input.getFile(),"File not found."));
        }
    }

    public static class ErrorResponse {
        private String file;
        private String error ;

        public ErrorResponse(String file,String error) {
            this.file = file;
            this.error = error;
        }
        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }
    }

}
