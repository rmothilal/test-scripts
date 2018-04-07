package com.modusbox.uuid_gen;

import org.springframework.web.bind.annotation.*;
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

    @RequestMapping(value = "/participants/{Type}/{Id}",method = RequestMethod.PUT)
    public void getParticipants(@PathVariable("Type") String type, @PathVariable("Id") String id, @RequestHeader String correlationId, @RequestBody String payload) throws IOException {
        logger.info("Header: "+correlationId+" Body: "+ payload);
        File f = new File("/users/murthy/"+correlationId+".txt");
        FileUtils.writeStringToFile(f,payload+System.getProperty("line.separator"),true);
    }
}
