package manev.damyan.auth.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

@RestController
public class AllNonMappedRequestsController {

    @RequestMapping(value = "**")
    public String getAllNonMappedRequests(@RequestBody(required = false) String requestBody, HttpServletRequest request)
            throws JsonProcessingException {

        System.out.println("---------------- Non-mapped endpoint hit");

        System.out.println("Method is: " + request.getMethod());
        System.out.println("Path is: " + request.getRequestURI());
        StreamSupport.stream(
                        Spliterators.spliteratorUnknownSize(request.getHeaderNames().asIterator(), Spliterator.ORDERED), false)
                .forEach(header -> System.out.println("Header  -> " + header + request.getHeader(header)));

        if (request.getQueryString() != null) {
            System.out.println("Query parameters are: " + Arrays.asList(request.getQueryString().split("&")));
        }

        if (requestBody != null) {
            ObjectMapper mapper = new ObjectMapper();
            Object json = mapper.readValue(requestBody, Object.class); // Parse JSON
            ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter(); // Pretty printer
            String prettyJson = writer.writeValueAsString(json);
            System.out.println("Body is: " + prettyJson);
        } else {
            System.out.println("Body is: null");
        }

        System.out.println("-------------- Before returning of non-handled method");
        return "handled ^_^";
    }
}
