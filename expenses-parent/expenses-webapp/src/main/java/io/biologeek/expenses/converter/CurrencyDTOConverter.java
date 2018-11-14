package io.biologeek.expenses.converter;

import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import io.biologeek.expenses.api.beans.CurrencyDTO;

@Component
public class CurrencyDTOConverter {
	
	
	public CurrencyDTO toDto(Currency cur) {
		CurrencyDTO dto = new CurrencyDTO();
		dto.setCode(cur.getCurrencyCode());
		dto.setName(cur.getDisplayName());
		dto.setSymbol(cur.getSymbol());
		return dto;
	}
	
	
	public List<CurrencyDTO> toDto(List<Currency> cur) {
		return cur.stream().map(this::toDto).collect(Collectors.toList());
	}

}
