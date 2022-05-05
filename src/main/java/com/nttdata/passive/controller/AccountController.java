package com.nttdata.passive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.passive.model.Accounts;
import com.nttdata.passive.service.IAccountService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AccountController {
	
	@Autowired
	private final IAccountService accountService;
	
	@PostMapping("/create/account")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Accounts> create (@RequestBody Accounts accounts){
        return accountService.create(Mono.just(accounts));
    }
	
	@ResponseStatus(HttpStatus.CREATED)
	@PutMapping(value = "/put/{accountNumber}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Mono<Accounts> update(@PathVariable("accountNumber") String accountNumber, @RequestBody Accounts accounts){
		return accountService.update(accountNumber, Mono.just(accounts));
	}
	
	@GetMapping(value = "/get/all",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseBody
	public Flux<Accounts> findAll(){
	   return accountService.getAllAccounts();
	}
	
	@GetMapping("/get/{accountNumber}")
	@ResponseBody
	public ResponseEntity<Mono<Accounts>> findById(@PathVariable("accountNumber") String accountNumber){
	    Mono<Accounts> accountMono= accountService.getById(accountNumber);
	    return new ResponseEntity<Mono<Accounts>>(accountMono,accountMono != null? HttpStatus.OK:HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/delete/{accountNumber}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable("accountNumber") String accountNumber){
        return accountService.deleteEmp(accountNumber)
                .map(r->ResponseEntity.ok().<Void> build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
	
}
