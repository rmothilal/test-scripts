package com.modusbox.uuid_gen;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.io.*;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
public class HomeController {

    private Logger logger = Logger.getLogger(HomeController.class.getName());

    @RequestMapping("/generate_uuids")
    public String generateUUIDToFile(@RequestParam String count) throws IOException {
        File f = new File("v4_uuids.txt");
        for (int i = 0; i < Integer.parseInt(count); i++) {
            FileUtils.writeStringToFile(f,UUID.randomUUID().toString()+System.getProperty("line.separator"),true);
        }

        return "Hello";
    }
}
