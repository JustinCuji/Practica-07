/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.ups.nuevo.controlador;

import ec.edu.ups.nuevo.dao.ILibroDAO;
import ec.edu.ups.nuevo.modelo.Libro;
import java.util.List;

/**
 *
 * @author davidvargas
 */
public class LibroControlador {
    private ILibroDAO libroDAO;
    private Libro libro;

    public LibroControlador(ILibroDAO libroDAO) {
        this.libroDAO = libroDAO;
    }
    
    public void mostrarInformacionLibro(Libro libro) {
        libro.mostrarInformacion();
    }
    
    public void agregarLibro(Libro libro){
        this.libro = libro;
        libroDAO.agregarLibro(libro);
    }
    public Libro buscar(String titulo){
        return libroDAO.buscarLibro(titulo); 
    }
    public boolean actualizarLibro(Libro libro){
        Libro telefonoEncontrado = this.buscar(libro.getTitulo());
        if(telefonoEncontrado != null){
            libroDAO.actualizarLibro(libro);
            return true;
        }
        return false;
    }
    public boolean eliminarLibro(Libro libro){
        Libro libroEncontrado = this.buscar(libro.getTitulo());
        if(libroEncontrado != null){
            libroDAO.eliminarLibro(libro);
            return true;
        }
        return false;
    }
    
    public List<Libro> listarLibros(){
        return libroDAO.obtenerTodosLibros();
    }
}
