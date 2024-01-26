/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.ups.nuevo.controlador;

import ec.edu.ups.nuevo.dao.IBibliotecaDAO;
import ec.edu.ups.nuevo.dao.ILibroDAO;
import ec.edu.ups.nuevo.dao.IPrestamoDAO;
import ec.edu.ups.nuevo.dao.IUsuarioDAO;
import ec.edu.ups.nuevo.idao.BibliotecaDAO;
import ec.edu.ups.nuevo.modelo.Biblioteca;
import ec.edu.ups.nuevo.modelo.Usuario;
import ec.edu.ups.nuevo.modelo.Libro;
import ec.edu.ups.nuevo.modelo.Prestamo;
import java.util.List;

/**
 *
 * @author davidvargas
 */
public class Biblioteca_Controlador {

    private ILibroDAO libroDAO;
    private IUsuarioDAO usuarioDAO;
    private IPrestamoDAO prestamoDAO;
    private IBibliotecaDAO bibliotecaDAO;

    public Biblioteca_Controlador(ILibroDAO libroDAO, IUsuarioDAO usuarioDAO, IPrestamoDAO prestamoDAO, IBibliotecaDAO bibliotecaDAO) {
        this.libroDAO = libroDAO;
        this.usuarioDAO = usuarioDAO;
        this.prestamoDAO = prestamoDAO;
        this.bibliotecaDAO = bibliotecaDAO;
    }

    public void crearBiblioteca(Biblioteca biblioteca) {
        bibliotecaDAO.agregarBiblioteca(biblioteca);

    }

    public Biblioteca buscarBiblioteca(String nombre) {
        return bibliotecaDAO.buscarBiblioteca(nombre);
    }

    public void agregarLibro(String tituloLibro, String nombreBiblioteca) {
        // Obtener la biblioteca por su nombre
        Biblioteca biblioteca = bibliotecaDAO.buscarBiblioteca(nombreBiblioteca);

        if (biblioteca != null) {
            // Verificar si el libro ya existe en la biblioteca
            Libro libroExistente = biblioteca.buscarLibro(tituloLibro);

            if (libroExistente == null) {
                // Si no existe, crear el libro y agregarlo a la biblioteca
                String autor = libroExistente.getAutor();
                int anio = libroExistente.getAño();
                Libro nuevoLibro = new Libro(tituloLibro, autor, anio);
                //libroDAO.agregarLibro(nuevoLibro);
                biblioteca.agregarLibro(nuevoLibro);

                // Actualizar la biblioteca después de agregar el libro
                bibliotecaDAO.actualizarBiblioteca(biblioteca);

                System.out.println("Libro agregado exitosamente a la biblioteca.");
            } else {
                System.out.println("El libro ya existe en la biblioteca.");
            }
        } else {
            System.out.println("No se encontró la biblioteca.");
        }
    }
    
    public void listarLibrosDeBiblioteca(String nombreBiblioteca) {
        Biblioteca biblioteca = bibliotecaDAO.buscarBiblioteca(nombreBiblioteca);

        if (biblioteca != null) {
            List<Libro> libros = biblioteca.getListaLibros();

            if (!libros.isEmpty()) {
                System.out.println("Libros en la biblioteca '" + nombreBiblioteca + "':");

                for (Libro libro : libros) {
                    System.out.println(libro);
                }
            } else {
                System.out.println("La biblioteca no tiene libros registrados.");
            }
        } else {
            System.out.println("No se encontró la biblioteca.");
        }
    }

    public void registrarUsuario(Usuario usuario) {
        // Verificar si el usuario ya existe en la biblioteca
        Biblioteca biblioteca = (Biblioteca) bibliotecaDAO.listarBiblioteca();
        if (biblioteca != null) {
            Usuario usuarioExistente = biblioteca.buscarUsuario(usuario.getIdentificacion());
            if (usuarioExistente == null) {
                // Si no existe, agregar el usuario
                usuarioDAO.agregarUsuario(usuario);
                biblioteca.registrarUsuario(usuario);
                // Actualizar la biblioteca después de agregar el usuario
                bibliotecaDAO.actualizarBiblioteca(biblioteca);
            } else {
                System.out.println("El usuario ya existe en la biblioteca.");
            }
        } else {
            System.out.println("No se encontró la biblioteca.");
        }
    }

    public Libro buscarLibro(String titulo) {
        return libroDAO.buscarLibro(titulo);
    }

    public void prestarLibro(Libro libro, Usuario usuario) {
        if (libro.isDisponible()) {
            Prestamo prestamo = new Prestamo(libro, usuario);
            prestamoDAO.agregarPrestamo(prestamo);
            libro.prestar();
        } else {
            System.out.println("El libro no está disponible para préstamo.");
        }
    }
    
    public List<Biblioteca> listaBibliotecas(){
        return bibliotecaDAO.listarBiblioteca();
    }
}
