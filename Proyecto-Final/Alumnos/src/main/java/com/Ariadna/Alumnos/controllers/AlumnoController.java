package com.Ariadna.Alumnos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


import com.Ariadna.Alumnos.model.Alumno;
import com.Ariadna.Alumnos.repository.AlumnoRepository;

@RestController
@RequestMapping("/alumnos")
@CrossOrigin(origins = "http://localhost:5173")
public class AlumnoController {
    @Autowired
    private AlumnoRepository alumnoRepository;

    // Metodo get para traer todos los alumnos 
    @GetMapping("/traer-alumnos")
    public List<Alumno> TraerAlumnos() {
        return alumnoRepository.findAll();
    }
    // Metodo para traer un alumno por algo en especifico
    @GetMapping("/traer-alumno/{id}")
    public ResponseEntity<Alumno> buscarAlumno(@PathVariable Long id) {
        return alumnoRepository.findById(id)
        .map(alumno -> ResponseEntity.ok(alumno))
        .orElse(ResponseEntity.notFound().build());
    }

    // Metodo para insertar un alumno
    @PostMapping("/insertar-alumno")
    public Alumno insertarAlumno(@RequestBody Alumno alumno) {
        return alumnoRepository.save(alumno);
    }

    //Metodo para editar un alumno
    @PutMapping("/editar-alumno/{id}")
    public ResponseEntity<Alumno> ActualizarAlumno(@PathVariable Long id, @RequestBody Alumno alumno) {
        return alumnoRepository.findById(id).map(alumnoExistente -> {
            alumnoExistente.setNumeroControl(alumno.getNumeroControl());
            alumnoExistente.setNombre(alumno.getNombre());
            alumnoExistente.setApellidos(alumno.getApellidos());
            alumnoExistente.setCorreo(alumno.getCorreo());
            alumnoExistente.setTelefono(alumno.getTelefono());
            alumnoExistente.setCarrera(alumno.getCarrera());
            alumnoExistente.setSemestre(alumno.getSemestre());
            alumnoExistente.setGrupo(alumno.getGrupo());
            alumnoExistente.setImagenURL(alumno.getImagenURL());

            Alumno actualizado = alumnoRepository.save(alumnoExistente);
            return ResponseEntity.ok(actualizado);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Metodo para eliminar un alumno
    @DeleteMapping("/eliminar-alumno/{id}")
    public void eliminarAlumno(@PathVariable Long id) {
        alumnoRepository.deleteById(id);
    }
    

}
