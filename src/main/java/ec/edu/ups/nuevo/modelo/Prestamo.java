/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.ups.nuevo.modelo;

import java.util.Date;

/**
 *
 * @author davidvargas
 */
public class Prestamo {

    private Libro libro;
    private Usuario usuario;
    private Date fechaPrestamo;
    private Date fechaDevolucion;
    
    public Prestamo(Libro libro, Usuario usuario) {
        this.libro = libro;
        this.usuario = usuario;
        this.fechaPrestamo = new Date();
    }
    
    public int calcularDiasPrestamo() {
        long tiempoPrestamo = new Date().getTime() - fechaPrestamo.getTime();

        return (int) (tiempoPrestamo / (1000 * 60 * 60 * 24));
    }
    
    public boolean esPrestamoVigente() {

        int maxDiasPrestamo = 14;

        return calcularDiasPrestamo() <= maxDiasPrestamo;
    }
    
    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
    
    public void devolver() {
        if (!esPrestamoVigente()) {
            System.out.println("El préstamo ya ha vencido, no se puede devolver.");
            return;
        }

        fechaDevolucion = new Date();  // Establecer la fecha de devolución
        libro.devolver();  // Marcar el libro como disponible
        System.out.println("Libro devuelto con éxito.");
    }
}
