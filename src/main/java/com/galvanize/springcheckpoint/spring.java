package com.galvanize.springcheckpoint;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class spring {

    // Hello World
    @GetMapping("/")
    public String helloWorld() {
        return "Hello from Spring!";
    }

    // Camelize
    @GetMapping("/camelize")
    public String getSnake_case(@RequestParam(required = true) String original,
                                @RequestParam(required = false, defaultValue = "false") Boolean initialCap){
        System.out.println("In the camelize function.");
        if (initialCap == true){
            original = original.replace(original.charAt(0), Character.toUpperCase(original.charAt(0)));
            System.out.println(original);
        }
        return original;
    }

    // Redact
    @GetMapping("/redact")
    public String getRedact(@RequestParam(required = true) String original,
                            @RequestParam String[] badWord){
        String[] tempStringArray = original.split(" ");
        for (int s = 0; s < tempStringArray.length; s++) {
            for (int i = 0; i < badWord.length; i++) {
                    tempStringArray[s] = tempStringArray[s].replaceAll(badWord[i],"*".repeat(badWord[i].length()));
            }
        }  //should be able to refactor this down further
        original = String.join(" ", tempStringArray);
        return original;
    }

    //Endcode
    @PostMapping("/encode")
    public String getEncode(@RequestParam(required = true) String message,
                            @RequestParam String key){
        String alphabet = "abcdefghijklmnopqrstuvwzyz";
        String encoded = "";
        for (int i = 0; i < message.length(); i++){ //checking each letter in message
            for (int j = 0; j < alphabet.length(); j++){ // I should be able to avoid the double loop....
                if (message.charAt(i) == alphabet.charAt(j)) {
                    encoded += key.charAt(j);
                }
            }
            if (Character.isWhitespace(message.charAt(i)) == true){
                encoded += " ";
            }
        }
        return encoded;
    }

    // SED
    @RequestMapping("/s/{find}/{replace}")
    public String getSed(@PathVariable Map<String,String> querystring,
                         @RequestParam String original){
        String find = querystring.get("find"); //likely easier way to capture this directly
        String replace = querystring.get("replace");
        String finalString = original.replaceAll(find,replace);
        return finalString;
    }

}
