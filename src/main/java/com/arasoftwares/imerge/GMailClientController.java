package com.arasoftwares.imerge;

import java.util.Base64;

import com.arasoftwares.imerge.domain.ResponseWrapper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class GMailClientController {

    @GetMapping
    public String sayHi() {
        System.out.println("say hi method called");
        return "<html><head><meta name='google-site-verification' content='yOnmr52jMDmWBnHCu0oMMy-B4CqJc8bug__iKNgzYFQ' />"
                + "<title>Gmail-Push Notification </title>" + "</head><body><h1>Hello World!</h1>"
                + "<p>Welcome to the Web App</body></html>";
    }

    @PostMapping("webhook")
    public ResponseEntity<?> recieveMail(@RequestBody final ResponseWrapper responseWrapper) {
        System.out.println(responseWrapper);
        try {
            final String decodeRequest = new String(
                    Base64.getMimeDecoder().decode(responseWrapper.getMessage().getData().getBytes()));
            System.out.println(decodeRequest);
        } catch (final Exception e) {
            System.out.println(e.getMessage());
        }
        return ResponseEntity.noContent().build();

    }
}
