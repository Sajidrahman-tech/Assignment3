package com.example.container2.Controller;

import com.example.container2.Model.ErrorModel;
import com.example.container2.Model.Input;
import com.example.container2.Model.Output;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@RestController
public class Controller2 {

    @GetMapping("/test")
    public String showResponse() {
        return "is Working Fine container2";
    }

    @PostMapping
    public ResponseEntity<Object> processFile(@RequestBody Input input) {
        String filePath =  "/Saajid_PV_dir/"+input.getFile();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int sum = 0;

            // Read the header line
            String header = reader.readLine();
            if (header == null || !header.trim().equalsIgnoreCase("product, amount")) {
                System.out.println("trimmed header is "+header.trim()+".   Header read: '" + header + "'");
                return ResponseEntity.ok().body(new ErrorModel(input.getFile()," 1 Input file not in CSV format."));
            }
            
            boolean hasValidRows = false;
            boolean isFileEmpty = true;

            while ((line = reader.readLine()) != null) {
                // Trim the line to avoid issues with leading/trailing spaces
                line = line.trim();
                System.out.println("line "+line);
                
                isFileEmpty = false;

                // Count the commas to ensure no extra or less commas
                int commaCount = line.length() - line.replace(",", "").length();
                if (commaCount != 1) {  // We expect exactly one comma separating two columns
                  
                    return ResponseEntity.ok().body(new ErrorModel(input.getFile(), "2 Input file not in CSV format."));
                }

                String[] columns = line.split(",");
                
                // Ensure exactly two columns exist after splitting
                if (columns.length != 2) {
                    return ResponseEntity.ok().body(new ErrorModel(input.getFile(), "3 Input file not in CSV format."));
                }

                // Check if the second column is a valid integer
                try {
                    int amount = Integer.parseInt(columns[1].trim());
                    hasValidRows = true;

                    if (columns[0].trim().equalsIgnoreCase(input.getProduct())) {
                        sum += amount;
                    }
                } catch (NumberFormatException e) {
                    return ResponseEntity.ok().body(new ErrorModel(input.getFile(), "4 Input file not in CSV format."));
                }
            }

            if (isFileEmpty) {
                return ResponseEntity.ok().body(new ErrorModel(input.getFile(), "Input file is empty."));
            }

            // Ensure at least one data row was present
            if (!hasValidRows) {
                    return ResponseEntity.ok().body(new ErrorModel(input.getFile(),"5 Input file not in CSV format."));
            }

            return ResponseEntity.ok(new Output(input.getFile(), sum));

        } catch (FileNotFoundException e) {
            return ResponseEntity.badRequest().body(new ErrorModel(input.getFile(), "File not found."));
        } 
        catch (IOException e) {
            return ResponseEntity.ok().body(new ErrorModel(input.getFile(), "6 Input file not in CSV format."));
        }
    }
}
