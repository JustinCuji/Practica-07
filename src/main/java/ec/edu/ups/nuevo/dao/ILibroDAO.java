/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.ups.nuevo.dao;

import ec.edu.ups.nuevo.modelo.Libro;
import java.util.List;

/**
 *
 * @author davidvargas
 */
public interface ILibroDAO {
    public void agregarLibro(Libro libro);
    public void actualizarLibro(Libro libro);
    public void eliminarLibro(Libro libro);
    public Libro buscarLibro(String titulo);
    List<Libro> obtenerTodosLibros();
}
