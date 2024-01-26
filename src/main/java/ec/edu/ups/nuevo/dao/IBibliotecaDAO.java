/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.ups.nuevo.dao;

import ec.edu.ups.nuevo.modelo.Biblioteca;
import ec.edu.ups.nuevo.modelo.Libro;
import ec.edu.ups.nuevo.modelo.Usuario;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface IBibliotecaDAO {
    public void agregarBiblioteca(Biblioteca biblioteca);
    public void actualizarBiblioteca(Biblioteca biblioteca);
    public void eliminarBiblioteca(Biblioteca biblioteca);
    public Biblioteca buscarBiblioteca (String nombre);
    public List<Biblioteca> listarBiblioteca(); 
    //List<Libro> obtenerTodosLibros();
    //List<Usuario> obetenerTodosUsuarios();
}
