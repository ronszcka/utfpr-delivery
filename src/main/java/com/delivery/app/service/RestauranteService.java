package com.delivery.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.delivery.app.entity.Restaurante;
import com.delivery.app.exception.NotFoundException;
import com.delivery.app.repository.RestauranteRepository;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@Service
public class RestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	public List<Restaurante> listarRestaurantes() {
		
		return restauranteRepository.findAll();
		
	}
	
	public Restaurante buscarRestauranteEspecifico(Long id) {
		
		Optional<Restaurante> restaurante = restauranteRepository.findById(id);
		
		if (restaurante.isEmpty()) {
			//return null;
			throw new NotFoundException("Restaurante não encontrado");
		}
		
		return restaurante.get();
		
	}
	
	public Restaurante criarRestaurante(Restaurante restaurante) {
		
		return restauranteRepository.save(restaurante);
		
	}
	
	public Restaurante atualizarRestaurante(Long id, Restaurante restaurante) {
		
		Restaurante restauranteAtual = this.buscarRestauranteEspecifico(id);
		
		BeanUtils.copyProperties(restaurante, restauranteAtual, "id");
		
		return restauranteRepository.save(restauranteAtual);
		
	}
	
}
