package com.delivery.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.app.dto.RestauranteInputDTO;
import com.delivery.app.dto.RestauranteResumidoDTO;
import com.delivery.app.entity.Restaurante;
import com.delivery.app.mapper.RestauranteInputMapper;
import com.delivery.app.mapper.RestauranteResumidoMapper;
import com.delivery.app.service.RestauranteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private RestauranteResumidoMapper restauranteResumidoMapper;
	
	@Autowired
	private RestauranteInputMapper restauranteInputMapper;
	
	@GetMapping
	@ResponseBody
	public List<RestauranteResumidoDTO> listarRestaurantes() {
		
		List<Restaurante> restaurantes = restauranteService.listarRestaurantes(); 
		
		List<RestauranteResumidoDTO> restaurantesDTO = restauranteResumidoMapper.map(restaurantes);
		
		return restaurantesDTO;
		
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	public Restaurante buscarRestauranteEspecifico(@PathVariable Long id) {
		
		return restauranteService.buscarRestauranteEspecifico(id);
		
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	private Restaurante criarRestaurante(@RequestBody @Valid RestauranteInputDTO restauranteInputDTO) {
		
		Restaurante restaurante = restauranteInputMapper.map(restauranteInputDTO);
		
		restaurante = restauranteService.criarRestaurante(restaurante);
		
		return restaurante;
		
	}
	
	@PutMapping("/{id}")
	@ResponseBody
	private Restaurante atualizarRestaurante(@PathVariable Long id, @RequestBody @Valid RestauranteInputDTO restauranteInputDTO) {
		
		Restaurante restaurante = restauranteInputMapper.map(restauranteInputDTO);
		
		restaurante = restauranteService.atualizarRestaurante(id, restaurante);
		
		return restaurante;
		
	}
	
}
