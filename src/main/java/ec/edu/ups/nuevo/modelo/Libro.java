/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.ups.nuevo.modelo;

/**
 *
 * @author davidvargas
 */
public class Libro implements Prestable {
    private String titulo;
    private String autor;
    private int año;
    private boolean disponible;
    

    public Libro(String titulo, String autor, int año) {
        this.titulo = titulo;
        this.autor = autor;
        this.año = año;
        this.disponible = true;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", año=" + año +
                ", disponible=" + disponible +
                '}';
    }
    
    @Override
    public void prestar() {
        disponible = false;
    }

    @Override
    public void devolver() {
        disponible = true;
    }
    
    public void mostrarInformacion() {
        System.out.println("Información del Libro:");
        System.out.println("Título: " + titulo);
        System.out.println("Autor: " + autor);
        System.out.println("Año: " + año);
        System.out.println("Disponible: " + (disponible ? "Sí" : "No"));
    }
}
