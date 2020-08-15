package org.example.mongo.controllers;

import org.example.mongo.models.Quotes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SocialLoginController {

    @GetMapping("loginSubmit")
    public ResponseEntity<Quotes> getQuotes(@RequestParam(name = "type") String type) {
        Quotes quotes = new Quotes();
        quotes.setId(null);
        quotes.setAuthor(type);

        if (quotes == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(quotes);
    }
}
