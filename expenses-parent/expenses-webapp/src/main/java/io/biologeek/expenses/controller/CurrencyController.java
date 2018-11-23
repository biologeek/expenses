package io.biologeek.expenses.controller;

import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.biologeek.expenses.api.beans.CurrencyDTO;
import io.biologeek.expenses.converter.CurrencyDTOConverter;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {

	@Autowired
	private CurrencyDTOConverter currencyConverter;

	@GetMapping("")
	public ResponseEntity<List<CurrencyDTO>> getCurrencies() {

		return new ResponseEntity<>(//
				Currency.getAvailableCurrencies().stream()//
						.map(currencyConverter::toDto)//
						.sorted((t, u) -> {
							return t.getCode().compareTo(u.getCode());
						})//
						.collect(Collectors.toList()),
				HttpStatus.OK);

	}
}
