/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.ups.nuevo.idao;

import ec.edu.ups.nuevo.dao.ILibroDAO;
import ec.edu.ups.nuevo.modelo.Libro;
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
public class LibroDAO implements ILibroDAO {

    private String ruta;

    private RandomAccessFile archivoEscritura;
    //private List<Libro> listaLibros;  // Almacenamiento simulado en memoria
    private RandomAccessFile archivosLectura;

    public LibroDAO() {
        this.ruta = "src\\main\\resources\\rutas\\libro.txt";
    }

    @Override
    public void agregarLibro(Libro libro) {
        try {
            archivoEscritura = new RandomAccessFile(ruta, "rw");
            archivoEscritura.seek(archivoEscritura.length());
            archivoEscritura.writeUTF(libro.getTitulo());
            archivoEscritura.writeUTF(libro.getAutor());
            archivoEscritura.writeInt(libro.getAño());
            archivoEscritura.writeBoolean(libro.isDisponible());
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
    public void actualizarLibro(Libro libro) {
        try {
            RandomAccessFile archivo = new RandomAccessFile(ruta, "rw");
            int bytesPorLibro = 59;
            long numLibros = archivo.length() / bytesPorLibro;

            for (int i = 0; i < numLibros; i++) {
                archivo.seek(i * bytesPorLibro);
                String tituloLibro = archivo.readUTF();
                if (tituloLibro.equals(libro.getTitulo())) {
                    archivo.seek(i * bytesPorLibro);  // Volver al inicio del registro
                    archivo.writeUTF(libro.getTitulo());
                    archivo.writeUTF(libro.getAutor());
                    archivo.writeInt(libro.getAño());
                    archivo.writeBoolean(libro.isDisponible());
                    break;
                }
            }

            archivo.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ruta no encontrada");
        } catch (IOException e) {
            System.out.println("Error de Lectura/Escritura");
        } catch (Exception e) {
            System.out.println("Error General");
        }
    }

    @Override
    public void eliminarLibro(Libro libro) {
        try {
            RandomAccessFile archivo = new RandomAccessFile(ruta, "rw");
            int bytesPorLibro = 59;
            long numLibros = archivo.length() / bytesPorLibro;

            for (int i = 0; i < numLibros; i++) {
                archivo.seek(i * bytesPorLibro);
                String tituloLibro = archivo.readUTF();
                if (tituloLibro.equals(libro.getTitulo())) {
                    // Encontramos el libro a eliminar
                    for (int j = i; j < numLibros - 1; j++) {
                        // Mover los registros hacia arriba para llenar el espacio
                        archivo.seek((j + 1) * bytesPorLibro);
                        byte[] datos = new byte[bytesPorLibro];
                        archivo.readFully(datos);
                        archivo.seek(j * bytesPorLibro);
                        archivo.write(datos);
                    }
                    // Truncar el archivo para eliminar el último registro duplicado
                    archivo.setLength((numLibros - 1) * bytesPorLibro);
                    break;
                }
            }

            archivo.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ruta no encontrada");
        } catch (IOException e) {
            System.out.println("Error de Lectura/Escritura");
        } catch (Exception e) {
            System.out.println("Error General");
        }
    }

    @Override
    public Libro buscarLibro(String titulo) {
        try {
            archivosLectura = new RandomAccessFile(ruta, "r");
            int bytesPorLibro = 59;
            long numLibros = archivosLectura.length() / bytesPorLibro;

            for (int i = 0; i < numLibros; i++) {
                archivosLectura.seek(i * bytesPorLibro);
                String tituloLibro = archivosLectura.readUTF();

                if (tituloLibro.equals(titulo)) {
                    // El título ya se ha leído, así que no es necesario leerlo de nuevo
                    String autor = archivosLectura.readUTF();
                    int anio = archivosLectura.readInt();
                    boolean disponible = archivosLectura.readBoolean();

                    Libro libro = new Libro(titulo, autor, anio);
                    libro.setDisponible(disponible); // Suponiendo que tienes un método setter para 'disponible'

                    archivosLectura.close();
                    return libro;
                } else {
                    // Saltar la lectura de los campos restantes del libro actual
                    archivosLectura.skipBytes(bytesPorLibro - 2 * Short.BYTES - Integer.BYTES);
                }
            }

            archivosLectura.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ruta no encontrada");
        } catch (IOException e) {
            System.out.println("Error de Lectura");
        } catch (Exception e) {
            System.out.println("Error General");
        }
        return null;
    }

    @Override
    public List<Libro> obtenerTodosLibros() {
        List<Libro> listaLibros = new ArrayList<>();
        try {
            RandomAccessFile archivoLectura = new RandomAccessFile(ruta, "r");
            int bytesPorLibro = 59;
            long numLibros = archivoLectura.length() / bytesPorLibro;
            for (int i = 0; i < numLibros; i++) {
                archivoLectura.seek(i * bytesPorLibro);
                String titulo = archivoLectura.readUTF();
                String autor = archivoLectura.readUTF();
                int anio = archivoLectura.readInt();
                boolean disponible = archivoLectura.readBoolean();
                Libro libro = new Libro(titulo, autor, anio);
                listaLibros.add(libro);
            }
            archivoLectura.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ruta no encontrada");
        } catch (IOException e) {
            System.out.println("Error de lectura");
        } catch (Exception e) {
            System.out.println("Error General");
        }
        return listaLibros;
    }

}
