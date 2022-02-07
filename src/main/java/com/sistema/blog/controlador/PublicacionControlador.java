package com.sistema.blog.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.blog.dto.PublicacionDTO;
import com.sistema.blog.servicio.PublicacionServicio;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionControlador {
	
	@Autowired
	private PublicacionServicio publicacionServicio;
	
	@PostMapping
	public ResponseEntity<PublicacionDTO> guardarPublicacion(@RequestBody PublicacionDTO publicacionDTO){
		return new ResponseEntity<>(this.publicacionServicio.crearPublicacion(publicacionDTO), HttpStatus.CREATED);
	}
	
	@GetMapping
	public List<PublicacionDTO> listarPublicaciones(){
		return this.publicacionServicio.obtenerTodasLasPublicaciones();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PublicacionDTO> obtenerPublicacionPorId(@PathVariable(name = "id") Long id){
		return ResponseEntity.ok(this.publicacionServicio.obtenerPublicacionPorId(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PublicacionDTO> actualizarPublicacion(@RequestBody PublicacionDTO publicacionDTO, @PathVariable(name = "id") Long id){
		PublicacionDTO publicacionRespuesta = this.publicacionServicio.actualizarPublicacion(publicacionDTO, id);
		return new ResponseEntity<>(publicacionRespuesta, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminarPublicacion(@PathVariable(name = "id") Long id){
		this.publicacionServicio.eliminarPublicacion(id);
		return new ResponseEntity<>("Publicacion Eliminada con exito", HttpStatus.OK);
	}
	
}
