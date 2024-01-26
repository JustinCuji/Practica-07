/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.ups.nuevo.idao;

import ec.edu.ups.nuevo.dao.IPrestamoDAO;
import ec.edu.ups.nuevo.modelo.Libro;
import ec.edu.ups.nuevo.modelo.Prestamo;
import ec.edu.ups.nuevo.modelo.Usuario;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author davidvargas
 */
public class PrestamoDAO implements IPrestamoDAO {

    private String ruta;

    //private List<Prestamo> listaPrestamos;  // Almacenamiento simulado en memoria
    private RandomAccessFile archivoEscritura;
    private RandomAccessFile archivoLectura;

    public PrestamoDAO() {
        this.ruta = "src\\main\\resources\\rutas\\prestamo.txt";
    }

    @Override
    public void agregarPrestamo(Prestamo prestamo) {
        try {
            archivoEscritura = new RandomAccessFile(ruta, "rw");
            archivoEscritura.seek(archivoEscritura.length());

            ArrayList<Prestamo> listaPrestamos = prestamo.getUsuario().obtenerTodosLosPrestamos();
            for (Prestamo prestamoEnLista : listaPrestamos) {
                archivoEscritura.writeUTF(prestamoEnLista.getLibro().getTitulo());
                archivoEscritura.writeUTF(prestamoEnLista.getLibro().getAutor());
                archivoEscritura.writeInt(prestamoEnLista.getLibro().getAño());
                archivoEscritura.writeBoolean(prestamoEnLista.getLibro().isDisponible());

                archivoEscritura.writeUTF(prestamoEnLista.getUsuario().getIdentificacion());
                archivoEscritura.writeLong(prestamoEnLista.getFechaPrestamo().getTime());

                if (prestamoEnLista.getFechaDevolucion() != null) {
                    archivoEscritura.writeLong(prestamoEnLista.getFechaDevolucion().getTime());
                } else {
                    archivoEscritura.writeLong(-1);
                }
            }
            archivoEscritura.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ruta no encontrada");
        } catch (IOException e) {
            System.out.println("Error de Escritura");
        } catch (Exception e) {
            System.out.println("Error General");
        }
    }

    @Override
    public void actualizarPrestamo(Prestamo prestamo) {
        try {
            RandomAccessFile archivoTemporal = new RandomAccessFile(ruta + "_temp", "rw");
            int bytesPorPrestamo = 363;
            long numPrestamos = archivoEscritura.length() / bytesPorPrestamo;

            for (int i = 0; i < numPrestamos; i++) {
                archivoEscritura.seek(i * bytesPorPrestamo);

                String tituloLibro = archivoEscritura.readUTF();
                String identificacionUsuario = archivoEscritura.readUTF();

                if (tituloLibro.equals(prestamo.getLibro().getTitulo())
                        && identificacionUsuario.equals(prestamo.getUsuario().getIdentificacion())) {
                    // Actualiza la información del Prestamo
                    archivoEscritura.writeUTF(prestamo.getLibro().getTitulo());
                    archivoEscritura.writeUTF(prestamo.getUsuario().getIdentificacion());
                    archivoEscritura.writeLong(prestamo.getFechaPrestamo().getTime());

                    // Check if there is a return date and write it
                    if (prestamo.getFechaDevolucion() != null) {
                        archivoEscritura.writeLong(prestamo.getFechaDevolucion().getTime());
                    } else {
                        // If no return date, write a placeholder value (e.g., -1)
                        archivoEscritura.writeLong(-1);
                    }

                    // Resto de la información del Prestamo
                    // ...
                    // Resto de la información del Prestamo
                    archivoEscritura.writeUTF(prestamo.getLibro().getAutor());
                    archivoEscritura.writeInt(prestamo.getLibro().getAño());
                    archivoEscritura.writeBoolean(prestamo.getLibro().isDisponible());

                    archivoEscritura.writeUTF(prestamo.getUsuario().getNombre());
                    archivoEscritura.writeUTF(prestamo.getUsuario().getCorreo());
                    archivoEscritura.writeUTF(prestamo.getUsuario().getTelefono());

                    // Resto de la información del Prestamo
                    // ...
                } else {
                    // Si no coincide, escribe el Prestamo en el archivo temporal
                    archivoTemporal.writeUTF(tituloLibro);
                    archivoTemporal.writeUTF(identificacionUsuario);
                    // Resto de la información del Prestamo
                    // ...
                }
            }

            // Cierra los archivos
            archivoEscritura.close();
            archivoTemporal.close();

            // Renombra el archivo temporal al original
            File archivoOriginal = new File(ruta);
            File archivoTemp = new File(ruta + "_temp");
            archivoTemp.renameTo(archivoOriginal);

        } catch (FileNotFoundException e) {
            System.out.println("Ruta no encontrada");
        } catch (IOException e1) {
            System.out.println("Error de Escritura");
        } catch (Exception e) {
            System.out.println("Error General");
        }
    }

    @Override
    public void eliminarPrestamo(String idUsuario, String tituloLibro) {
        try {
            RandomAccessFile archivoTemporal = new RandomAccessFile(ruta + "_temp", "rw");
            int bytesPorPrestamo = 363;
            long numPrestamos = archivoEscritura.length() / bytesPorPrestamo;

            for (int i = 0; i < numPrestamos; i++) {
                archivoEscritura.seek(i * bytesPorPrestamo);

                String tituloPrestamo = archivoEscritura.readUTF();
                String identificacionUsuario = archivoEscritura.readUTF();

                // Verifica si el Prestamo actual coincide con los criterios
                if (!tituloPrestamo.equals(tituloLibro)
                        || !identificacionUsuario.equals(idUsuario)) {
                    // Si no coincide, escribe el Prestamo en el archivo temporal
                    archivoTemporal.writeUTF(tituloPrestamo);
                    archivoTemporal.writeUTF(identificacionUsuario);
                    // Resto de la información del Prestamo
                    // ...
                }
            }

            // Cierra los archivos
            archivoEscritura.close();
            archivoTemporal.close();

            // Renombra el archivo temporal al original
            File archivoOriginal = new File(ruta);
            File archivoTemp = new File(ruta + "_temp");
            archivoTemp.renameTo(archivoOriginal);

        } catch (FileNotFoundException e) {
            System.out.println("Ruta no encontrada");
        } catch (IOException e1) {
            System.out.println("Error de Escritura");
        } catch (Exception e) {
            System.out.println("Error General");
        }
    }

    @Override
    public List<Prestamo> obtenerTodosPrestamos() {
        List<Prestamo> prestamos = new ArrayList<>();

        try {
            archivoLectura = new RandomAccessFile(ruta, "r");
            int bytesPorPrestamo = 363;
            long numPrestamos = archivoLectura.length() / bytesPorPrestamo;

            for (int i = 0; i < numPrestamos; i++) {
                archivoLectura.seek(i * bytesPorPrestamo);
                //String titulo = archivoLectura.readUTF();
                //String idUsuarioo = archivoLectura.readUTF();

                String tituloo = archivoLectura.readUTF();
                String autor = archivoLectura.readUTF();
                int anio = archivoLectura.readInt();
                boolean dispo = archivoLectura.readBoolean();
                Libro libroo = new Libro(tituloo, autor, anio);

                String nombre = archivoLectura.readUTF();
                String identificacio = archivoLectura.readUTF();
                String correo = archivoLectura.readUTF();
                String telefono = archivoLectura.readUTF();
                Usuario usuario = new Usuario(nombre, identificacio, correo, telefono);
                for (int j = 0; j < 10; j++) {
                    String tituloo0 = archivoLectura.readUTF();
                    String autorr = archivoLectura.readUTF();
                    int anioo = archivoLectura.readInt();
                    boolean dispoo = archivoLectura.readBoolean();
                    Libro librooo = new Libro(tituloo0, autorr, anioo);

                    String nombree = archivoLectura.readUTF();
                    String identificacioo = archivoLectura.readUTF();
                    String correo0 = archivoLectura.readUTF();
                    String telefonoo = archivoLectura.readUTF();
                    Usuario usurariooo = new Usuario(nombree, identificacioo, correo0, telefonoo);
                    long fechaPrestamo = archivoLectura.readLong();
                    long fechaDevolucion = archivoLectura.readLong();
                    Prestamo prestamo = new Prestamo(librooo, usurariooo);
                    usuario.agregarPrestamo(prestamo);
                }

                long fechaPrestamo = archivoLectura.readLong();
                long fechaDevolucion = archivoLectura.readLong();
                Prestamo prestamo = new Prestamo(libroo, usuario);

                // Agrega el Prestamo a la lista
                prestamos.add(prestamo);
            }

            // Cierra el archivo de lectura
            archivoLectura.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ruta no encontrada");
        } catch (IOException e1) {
            System.out.println("Error de Lectura");
        } catch (Exception e) {
            System.out.println("Error General");
        }

        return prestamos;
    }

    @Override
    public Prestamo buscarPrestamo(Libro libro, String idUsuario
    ) {

        try {
            archivoLectura = new RandomAccessFile(ruta, "r");
            int bytesPorPrestamo = 363;
            long numPrestamos = archivoLectura.length() / bytesPorPrestamo;
            for (int i = 0; i < numPrestamos; i++) {
                archivoLectura.seek(i * bytesPorPrestamo);
                String titulo = archivoLectura.readUTF();
                String idUsuarioo = archivoLectura.readUTF();

                if (titulo.equals(libro.getTitulo()) && idUsuarioo.equals(idUsuario)) {
                    String tituloo = archivoLectura.readUTF();
                    String autor = archivoLectura.readUTF();
                    int anio = archivoLectura.readInt();
                    boolean dispo = archivoLectura.readBoolean();
                    Libro libroo = new Libro(tituloo, autor, anio);

                    String nombre = archivoLectura.readUTF();
                    String identificacio = archivoLectura.readUTF();
                    String correo = archivoLectura.readUTF();
                    String telefono = archivoLectura.readUTF();
                    Usuario usuario = new Usuario(nombre, identificacio, correo, telefono);
                    for (int j = 0; j < 10; j++) {
                        String tituloo0 = archivoLectura.readUTF();
                        String autorr = archivoLectura.readUTF();
                        int anioo = archivoLectura.readInt();
                        boolean dispoo = archivoLectura.readBoolean();
                        Libro librooo = new Libro(tituloo0, autorr, anioo);

                        String nombree = archivoLectura.readUTF();
                        String identificacioo = archivoLectura.readUTF();
                        String correo0 = archivoLectura.readUTF();
                        String telefonoo = archivoLectura.readUTF();
                        Usuario usurariooo = new Usuario(nombree, identificacioo, correo0, telefonoo);
                        long fechaPrestamo = archivoLectura.readLong();
                        long fechaDevolucion = archivoLectura.readLong();
                        Prestamo prestamo = new Prestamo(librooo, usurariooo);
                        usuario.agregarPrestamo(prestamo);
                    }

                    long fechaPrestamo = archivoLectura.readLong();
                    long fechaDevolucion = archivoLectura.readLong();
                    Prestamo prestamo = new Prestamo(libroo, usuario);

                    archivoLectura.close();
                    return prestamo;

                }
            }
            archivoLectura.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ruta no encontrada");
        } catch (IOException e) {
            System.out.println("Error de Lectura");
        } catch (Exception e) {
            System.out.println("Error General");
        }
        return null;
    }
}
