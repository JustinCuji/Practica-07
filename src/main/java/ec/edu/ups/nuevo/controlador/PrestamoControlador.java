/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.ups.nuevo.controlador;

import ec.edu.ups.nuevo.dao.IPrestamoDAO;
import ec.edu.ups.nuevo.idao.LibroDAO;
import ec.edu.ups.nuevo.modelo.Prestamo;
import ec.edu.ups.nuevo.modelo.Libro;
import ec.edu.ups.nuevo.modelo.Usuario;

/**
 *
 * @author davidvargas
 */
public class PrestamoControlador {

    private IPrestamoDAO prestamoDAO;
    private UsuarioControlador usuarioControlador;
    private Prestamo prestamo;
    private Usuario usuario;
    private Libro libro;

    public PrestamoControlador(IPrestamoDAO prestamoDAO, UsuarioControlador usuarioControlador) {
        this.prestamoDAO = prestamoDAO;
        this.usuarioControlador = usuarioControlador;
    }
    
    public boolean eliminar(String tituloLibro, String idUsuario) {
        prestamoDAO.eliminarPrestamo(idUsuario, tituloLibro);
        return true;  // Puedes ajustar esto según tus necesidades
    }

    public Prestamo buscarPrestamo(String tituloLibro, Usuario usuario) {
        this.prestamo = prestamoDAO.buscarPrestamo(libro, tituloLibro);
        return this.prestamo;
    }

    public boolean actualizarPrestamo(String tituloLibro, Usuario usuario) {
        Prestamo prestamoEncontrado = this.buscarPrestamo(tituloLibro, usuario);

        if (prestamoEncontrado != null) {
            if (!prestamoEncontrado.esPrestamoVigente()) {
                System.out.println("El préstamo ya ha vencido, no se puede actualizar.");
                return false;
            }

            // Realiza las actualizaciones necesarias en el objeto prestamoEncontrado
            prestamoEncontrado.devolver();  // Marcar el préstamo como devuelto
            prestamoDAO.actualizarPrestamo(prestamoEncontrado);
            return true;
        }

        return false;
    }
    
    
}
