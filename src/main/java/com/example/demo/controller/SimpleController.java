package com.example.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static com.example.demo.controller.SimpleController.PATH_PREFIX;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(PATH_PREFIX)
@Tag(name = "sample-controller", description = "Sample controller")
@RequiredArgsConstructor
public class SimpleController {

	public static final String PATH_PREFIX = "/api/v1/sample";

	@GetMapping(produces = APPLICATION_JSON_VALUE)
	@Operation(description = "Gives a simple JSON object back")
	@ApiResponse(responseCode = "200", description = "Returns a response.")
	public Flux<String> getRequest() {
		return Flux.just("index");
	}
}
