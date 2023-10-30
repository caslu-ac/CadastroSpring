//package com.example.spring.client;
//
//import com.example.spring.model.Usuario;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Log4j2
//public class SpringClient {
//    public static void main(String[] args) {
//        ResponseEntity<Usuario> entity = new RestTemplate().getForEntity("http://localhost:8080/find/{id}", Usuario.class, 24);
//        log.info(entity);
//        Usuario object = new RestTemplate().getForObject("http://localhost:8080/find/{id}", Usuario.class, 24);
//        log.info(object);
//
//        Usuario[] usuarios = new RestTemplate().getForObject("http://localhost:8080/listarTodos", Usuario[].class);
//        log.info(Arrays.toString(usuarios));
//
////        ResponseEntity<List<Usuario>> exchange = new RestTemplate().exchange("http://localhost:8080/listarTodos", HttpMethod.GET, null,
////                new ParameterizedTypeReference<List<Usuario>>(){});
////
////        log.info(exchange.getBody());
//    }
//}
