/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.ups.nuevo.dao;

import ec.edu.ups.nuevo.modelo.Libro;
import ec.edu.ups.nuevo.modelo.Prestamo;
import ec.edu.ups.nuevo.modelo.Usuario;
import java.util.List;

/**
 *
 * @author davidvargas
 */
public interface IPrestamoDAO {
    void agregarPrestamo(Prestamo prestamo);
    void actualizarPrestamo(Prestamo prestamo);
    void eliminarPrestamo(String idUsuario, String tituloLibro);
    Prestamo buscarPrestamo(Libro libro, String idUsuario);
    List<Prestamo> obtenerTodosPrestamos();
}
