package com.nttdata.passive.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//1xxxxxxx --> cuenta ahorro
//2xxxxxxx --> cuenta corriente
//3xxxxxxx --> cuenta plazo fijo

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Accounts")
public class Accounts {

	private String id;
	@Id
	private String accountNumber;
	private Float balance;
	private Float withdrawal;
	private Float deposit;
	
	public Accounts(String accountNumber, Float balance, Float withdrawal, Float deposit) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.withdrawal = withdrawal;
        this.deposit = deposit;
    }
}
