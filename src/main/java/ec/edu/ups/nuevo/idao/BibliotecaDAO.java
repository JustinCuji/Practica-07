/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.ups.nuevo.idao;

import ec.edu.ups.nuevo.dao.IBibliotecaDAO;
import ec.edu.ups.nuevo.modelo.Biblioteca;
import ec.edu.ups.nuevo.modelo.Libro;
import ec.edu.ups.nuevo.modelo.Usuario;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class BibliotecaDAO implements IBibliotecaDAO {

    private String ruta;
    private RandomAccessFile archivoEscritura;
    private RandomAccessFile archivoLectura;

    public BibliotecaDAO() {
        this.ruta = "src\\main\\resources\\rutas\\biblioteca.txt";
    }

    @Override
    public void agregarBiblioteca(Biblioteca biblioteca) {
        try {
            archivoEscritura = new RandomAccessFile(ruta, "rw");
            archivoEscritura.seek(archivoEscritura.length());
            // Escribe la información de la biblioteca
            archivoEscritura.writeUTF(biblioteca.getNombre());
            archivoEscritura.writeUTF(biblioteca.getDireccion());

            List<Libro> listaLibros = biblioteca.getListaLibros();
            for (int i = 0; i < 10; i++) {
                archivoEscritura.writeUTF(listaLibros.get(i).getTitulo());
                archivoEscritura.writeUTF(listaLibros.get(i).getAutor());
                archivoEscritura.writeInt(listaLibros.get(i).getAño());
                archivoEscritura.writeBoolean(listaLibros.get(i).isDisponible());
            }

            List<Usuario> listaUsuarios = biblioteca.getListaUsuarios();
            for (int i = 0; i < 10; i++) {
                archivoEscritura.writeUTF(listaUsuarios.get(i).getNombre());
                archivoEscritura.writeUTF(listaUsuarios.get(i).getIdentificacion());
                archivoEscritura.writeUTF(listaUsuarios.get(i).getCorreo());
                archivoEscritura.writeUTF(listaUsuarios.get(i).getTelefono());
            }
            archivoEscritura.close();

        } catch (FileNotFoundException e) {
            System.out.println("Ruta no encontrada");
        } catch (IOException e1) {
            System.out.println("Error de Escritura");
        } catch (Exception e) {
            System.out.println("Error General");
        }
    }

    @Override
    public void actualizarBiblioteca(Biblioteca biblioteca) {
        try {
            archivoLectura = new RandomAccessFile(ruta, "rw");
            int bytesPorBiblioteca = 54;
            long numBiblio = archivoLectura.length() / bytesPorBiblioteca;

            for (int i = 0; i < numBiblio; i++) {
                archivoLectura.seek(i * bytesPorBiblioteca);
                String nombre = archivoLectura.readUTF();
                String direccion = archivoLectura.readUTF();

                if (nombre.equals(biblioteca.getNombre()) && direccion.equals(biblioteca.getDireccion())) {
                    // Si es la biblioteca a actualizar, escribimos la nueva información
                    archivoLectura.seek(i * bytesPorBiblioteca);
                    archivoLectura.writeUTF(biblioteca.getNombre());
                    archivoLectura.writeUTF(biblioteca.getDireccion());

                    // Escribe la información de los libros
                    for (Libro libro : biblioteca.getListaLibros()) {
                        archivoLectura.writeUTF(libro.getTitulo());
                        archivoLectura.writeUTF(libro.getAutor());
                        archivoLectura.writeInt(libro.getAño());
                        archivoLectura.writeBoolean(libro.isDisponible());
                    }

                    // Escribe la información de los usuarios
                    for (Usuario usuario : biblioteca.getListaUsuarios()) {
                        archivoLectura.writeUTF(usuario.getIdentificacion());
                        archivoLectura.writeUTF(usuario.getNombre());
                        archivoLectura.writeUTF(usuario.getCorreo());
                        archivoLectura.writeUTF(usuario.getTelefono());
                    }
                    break; // Salimos del bucle una vez actualizada la biblioteca
                }
            }

            // Cierra el archivo
            archivoLectura.close();

        } catch (FileNotFoundException e) {
            System.out.println("Ruta no encontrada");
        } catch (IOException e1) {
            System.out.println("Error de Lectura/Escritura");
        } catch (Exception e) {
            System.out.println("Error General");
        }
    }

    @Override
    public void eliminarBiblioteca(Biblioteca biblioteca) {
        try {
            archivoLectura = new RandomAccessFile(ruta, "rw");
            int bytesPorBiblioteca = 54;
            long numBiblio = archivoLectura.length() / bytesPorBiblioteca;

            for (int i = 0; i < numBiblio; i++) {
                archivoLectura.seek(i * bytesPorBiblioteca);
                String nombre = archivoLectura.readUTF();
                String direccion = archivoLectura.readUTF();

                if (nombre.equals(biblioteca.getNombre()) && direccion.equals(biblioteca.getDireccion())) {
                    // Si es la biblioteca a eliminar, no copiamos la información
                    // Saltamos la información de los libros
                    archivoLectura.seek(i * bytesPorBiblioteca);
                    archivoLectura.writeUTF(""); // Escribimos un string vacío para eliminar el registro
                    archivoLectura.writeUTF(""); // Escribimos un string vacío para eliminar el registro
                    archivoLectura.seek(archivoLectura.getFilePointer() + (biblioteca.getListaLibros().size() * 39));

                    // Saltamos la información de los usuarios
                    archivoLectura.seek(archivoLectura.getFilePointer() + (biblioteca.getListaUsuarios().size() * 35));
                    break; // Salimos del bucle una vez eliminada la biblioteca
                }
            }

            // Cierra el archivo
            archivoLectura.close();

        } catch (FileNotFoundException e) {
            System.out.println("Ruta no encontrada");
        } catch (IOException e1) {
            System.out.println("Error de Lectura/Escritura");
        } catch (Exception e) {
            System.out.println("Error General");
        }
    }

    @Override
    public Biblioteca buscarBiblioteca(String nombre) {
        try {
            archivoLectura = new RandomAccessFile(ruta, "r");
            int bytesPorBiblioteca = 54;
            long numBiblio = archivoLectura.length() / bytesPorBiblioteca;

            for (int i = 0; i < numBiblio; i++) {
                archivoLectura.seek(i * bytesPorBiblioteca);
                String nombreBiblioteca = archivoLectura.readUTF();
                String direccion = archivoLectura.readUTF();

                if (nombreBiblioteca.equals(nombre)) {
                    // Si encontramos la biblioteca, creamos el objeto y leemos la información
                    Biblioteca biblioteca = new Biblioteca(nombreBiblioteca, direccion);

                    // Leemos la información de los libros
                    for (int j = 0; j < 10; j++) {
                        String titulo = archivoLectura.readUTF();
                        String autor = archivoLectura.readUTF();
                        int año = archivoLectura.readInt();
                        boolean disponible = archivoLectura.readBoolean();
                        Libro libro = new Libro(titulo, autor, año);
                        libro.setDisponible(disponible);
                        biblioteca.agregarLibro(libro);
                    }

                    // Leemos la información de los usuarios
                    for (int j = 0; j < 10; j++) {
                        String identificacion = archivoLectura.readUTF();
                        String nombreUsuario = archivoLectura.readUTF();
                        String correo = archivoLectura.readUTF();
                        String telefono = archivoLectura.readUTF();
                        Usuario usuario = new Usuario(identificacion, nombreUsuario, correo, telefono);
                        biblioteca.registrarUsuario(usuario);
                    }
                    // Cierra el archivo
                    archivoLectura.close();

                    return biblioteca;
                } else {
                    // Si no es la biblioteca que estamos buscando, saltamos la información
                    archivoLectura.seek(archivoLectura.getFilePointer() + (3 * 39) + (2 * 35));
                }
            }

            // Cierra el archivo
            archivoLectura.close();

        } catch (FileNotFoundException e) {
            System.out.println("Ruta no encontrada");
        } catch (IOException e1) {
            System.out.println("Error de Lectura");
        } catch (Exception e) {
            System.out.println("Error General");
        }

        return null;
    }

    @Override
    public List<Biblioteca> listarBiblioteca() {
        try {
            archivoLectura = new RandomAccessFile(ruta, "r");
            int bytesPorBiblioteca = 54;
            long numBiblio = archivoLectura.length() / bytesPorBiblioteca;
            List<Biblioteca> bibliotecas = new ArrayList<>();

            for (int i = 0; i < numBiblio; i++) {
                archivoLectura.seek(i * bytesPorBiblioteca);
                String nombre = archivoLectura.readUTF();
                String direccion = archivoLectura.readUTF();
                Biblioteca biblioteca = new Biblioteca(nombre, direccion);

                // Leemos la información de los libros
                for (int j = 0; j < 3; j++) {
                    String titulo = archivoLectura.readUTF();
                    String autor = archivoLectura.readUTF();
                    int año = archivoLectura.readInt();
                    boolean disponible = archivoLectura.readBoolean();
                    Libro libro = new Libro(titulo, autor, año);
                    libro.setDisponible(disponible);
                    biblioteca.agregarLibro(libro);
                }

                // Leemos la información de los usuarios
                for (int j = 0; j < 2; j++) {
                    String identificacion = archivoLectura.readUTF();
                    String nombreUsuario = archivoLectura.readUTF();
                    String correo = archivoLectura.readUTF();
                    String telefono = archivoLectura.readUTF();
                    Usuario usuario = new Usuario(identificacion, nombreUsuario, correo, telefono);
                    biblioteca.registrarUsuario(usuario);
                }

                bibliotecas.add(biblioteca);
            }

            // Cierra el archivo
            archivoLectura.close();

            return bibliotecas;

        } catch (FileNotFoundException e) {
            System.out.println("Ruta no encontrada");
        } catch (IOException e1) {
            System.out.println("Error de Lectura");
        } catch (Exception e) {
            System.out.println("Error General");
        }

        return null;
    }

}
