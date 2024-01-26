/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.ups.nuevo.modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author davidvargas
 */
public class Biblioteca {    
    private String nombre;
    private String direccion;
    private List<Libro> listaLibros;
    private List<Usuario> listaUsuarios;

    public Biblioteca(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.listaLibros = new ArrayList<>();
        this.listaUsuarios = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Libro> getListaLibros() {
        return listaLibros;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }


    public void setListaLibros(ArrayList<Libro> listaLibros) {
        this.listaLibros = listaLibros;
    }

    

    public void setListaUsuarios(ArrayList<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    @Override
    public String toString() {
        return "Biblioteca{" + "nombre=" + nombre + ", direccion=" + direccion + ", listaLibros=" + listaLibros + ", listaUsuarios=" + listaUsuarios + '}';
    }

    public void agregarLibro(Libro libro) {
        listaLibros.add(libro);
    }

    public void registrarUsuario(Usuario usuario) {
        listaUsuarios.add(usuario);
    }

    public Libro buscarLibro(String criterio) {
        for (Libro libro : listaLibros) {
            if (libro.getTitulo().equalsIgnoreCase(criterio) ||
                libro.getAutor().equalsIgnoreCase(criterio) ||
                String.valueOf(libro.getAño()).equalsIgnoreCase(criterio)) {
                return libro;
            }
        }
        return null;
    }
    public void prestarLibro(Libro libro, Usuario usuario) {
        if (libro.isDisponible()) {
            Prestamo prestamo = new Prestamo(libro, usuario);
            usuario.agregarPrestamo(prestamo);
            libro.prestar();
            System.out.println("Préstamo exitoso.");
        } else {
            System.out.println("El libro no está disponible para préstamo.");
        }
    }
    
    public Usuario buscarUsuario(String cedula) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getIdentificacion().equalsIgnoreCase(cedula)) {
                return usuario;
            }
        }
        return null;
    }
    void devolverLibro(Libro libroDevolver, Usuario usuarioDevolucion) {
      
        boolean libroPrestado = false;

        for (Prestamo prestamo : usuarioDevolucion.getListaPrestamos()) {
            if (prestamo.getLibro().equals(libroDevolver) && prestamo.esPrestamoVigente()) {
                libroPrestado = true;
                break;
            }
        }
        

        if (libroPrestado) {
            libroDevolver.setDisponible(true);

          
            Iterator<Prestamo> iterator = usuarioDevolucion.getListaPrestamos().iterator();
            while (iterator.hasNext()) {
                Prestamo prestamo = iterator.next();
                if (prestamo.getLibro().equals(libroDevolver) && prestamo.esPrestamoVigente()) {
                    iterator.remove();
                    break;
                }
            }

            System.out.println("Devolución exitosa. Libro nuevamente marcado como disponible.");
        } else {
            System.out.println("Error: El usuario no tiene el libro prestado");
        }
    }
}
