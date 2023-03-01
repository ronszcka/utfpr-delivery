package com.delivery.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.delivery.app.entity.Restaurante;
import com.delivery.app.repository.RestauranteRepository;

@Service
public class RestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	public List<Restaurante> listarRestaurantes() {
		
		return restauranteRepository.findAll();
		
	}
	
}
