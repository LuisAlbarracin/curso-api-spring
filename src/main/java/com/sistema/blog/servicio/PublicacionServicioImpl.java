package com.sistema.blog.servicio;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.blog.dto.PublicacionDTO;
import com.sistema.blog.entidades.Publicacion;
import com.sistema.blog.excepciones.ResourceNotFoundException;
import com.sistema.blog.repository.PublicacionRepositorio;

@Service
public class PublicacionServicioImpl implements PublicacionServicio {

	@Autowired
	private PublicacionRepositorio publicacionRepositorio;
	
	@Override
	public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO) {
		
		Publicacion publicacion = this.mapearEntidad(publicacionDTO);
		Publicacion nuevaPublicacion = this.publicacionRepositorio.save(publicacion);
		PublicacionDTO publicacionRespuesta = this.mapearDTO(nuevaPublicacion);
		return publicacionRespuesta;
	}

	@Override
	public List<PublicacionDTO> obtenerTodasLasPublicaciones() {
		// TODO Auto-generated method stub
		List<Publicacion> publicaciones = this.publicacionRepositorio.findAll();
		return publicaciones.stream().map(publicacion -> this.mapearDTO(publicacion)).collect(Collectors.toList());
	}
	
	// Convierte Entidad a DTO
	private PublicacionDTO mapearDTO(Publicacion publicacion) {
		PublicacionDTO publicacionDTO = new PublicacionDTO();
		
		publicacionDTO.setId(publicacion.getId());
		publicacionDTO.setTitulo(publicacion.getTitulo());
		publicacionDTO.setDescripcion(publicacion.getDescripcion());
		publicacionDTO.setContenido(publicacion.getContenido());
		
		return publicacionDTO;
	}
	
	// Convierte DTO a Entidad
	private Publicacion mapearEntidad(PublicacionDTO publicacionDTO) {
		Publicacion publicacion = new Publicacion();
		
		publicacion.setTitulo(publicacionDTO.getTitulo());
		publicacion.setDescripcion(publicacionDTO.getDescripcion());
		publicacion.setContenido(publicacionDTO.getContenido());
		
		return publicacion;
	}

	@Override
	public PublicacionDTO obtenerPublicacionPorId(Long id) {
		Publicacion publicacion = this.publicacionRepositorio
				.findById(id).orElseThrow(() -> new ResourceNotFoundException("publicacion", "id", id));
		return this.mapearDTO(publicacion);
	}

	@Override
	public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, Long id) {
		// TODO Auto-generated method stub
		Publicacion publicacion = this.publicacionRepositorio
				.findById(id).orElseThrow(() -> new ResourceNotFoundException("publicacion", "id", id));

		publicacion.setTitulo(publicacionDTO.getTitulo());
		publicacion.setDescripcion(publicacionDTO.getDescripcion());
		publicacion.setContenido(publicacionDTO.getContenido());
		
		Publicacion publicacionActualizada = this.publicacionRepositorio.save(publicacion);
		return this.mapearDTO(publicacionActualizada);
	}

	@Override
	public void eliminarPublicacion(Long id) {
		Publicacion publicacion = this.publicacionRepositorio
				.findById(id).orElseThrow(() -> new ResourceNotFoundException("publicacion", "id", id));
		this.publicacionRepositorio.delete(publicacion);
	}	

}
