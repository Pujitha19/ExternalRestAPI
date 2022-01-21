package com.example.External.Rest.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
public class Controller {
    //    @Value("${Url}")
//    private String url;
    private String url = "https://httpbin.org/";

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping(value = "/get")

    public List<Object> getdetails(){
        try {


            return Arrays.asList("Hello Get Method");
        }
        catch (ResourceAccessException e){
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/post")
    public ResponseEntity<String> posted(@RequestBody Employee employee){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Employee> entity = new HttpEntity<Employee>(employee,headers);

        log.info("Adding the data");

        ResponseEntity<String> post =restTemplate.postForEntity(url+"/post", employee, String.class);
        return post;
    }

    @PutMapping("/put")
    public List<Object> put(@RequestBody Employee employeepost){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Employee> entity = new HttpEntity<Employee>(employeepost,headers);
        ResponseEntity<Employee> e =restTemplate.exchange(url+"/put", HttpMethod.PUT,entity,Employee.class);
        System.out.println(e);
        log.info("updating data");
        return Arrays.asList(e);
    }

    @DeleteMapping("/delete")
    public List<Object> DeleteData(Employee employee)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Employee> entity = new HttpEntity<Employee>(employee,headers);
        ResponseEntity<Employee> responseEntity = restTemplate.exchange(url+"/delete", HttpMethod.DELETE, entity, Employee.class);
        System.out.println(responseEntity);
        log.info("Deleted data");
        return Arrays.asList(responseEntity);
    }

}
