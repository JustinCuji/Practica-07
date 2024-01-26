/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.ups.nuevo.idao;

import ec.edu.ups.nuevo.dao.IUsuarioDAO;
import ec.edu.ups.nuevo.modelo.Libro;
import ec.edu.ups.nuevo.modelo.Prestamo;
import ec.edu.ups.nuevo.modelo.Usuario;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author davidvargas
 */
public class UsuarioDAO implements IUsuarioDAO {

    private String ruta;

    //private static List<Usuario> listaUsuarios = new ArrayList<>();
    private RandomAccessFile archivoEscritura;
    private RandomAccessFile archivoLectura;

    public UsuarioDAO() {
        this.ruta = "src\\main\\resources\\rutas\\usurario.txt";
    }

    @Override
    public void agregarUsuario(Usuario usuario) {
        try {
            archivoEscritura = new RandomAccessFile(ruta, "rw");
            archivoEscritura.seek(archivoEscritura.length());

            //archivoEscritura.writeUTF(usuario.getIdentificacion()); // Agregado para escribir el ID del usuario
            archivoEscritura.writeUTF(usuario.getNombre());
            archivoEscritura.writeUTF(usuario.getIdentificacion());
            archivoEscritura.writeUTF(usuario.getCorreo());
            archivoEscritura.writeUTF(usuario.getTelefono());

            List<Prestamo> listaPrestamos = (List<Prestamo>) usuario.getListaPrestamos();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (int i = 0; i < 10; i++) {
                if (i < listaPrestamos.size()) {
                    Prestamo prestamo = listaPrestamos.get(i);
                    Libro libro = prestamo.getLibro();
                    Usuario usuarioPrestamo = prestamo.getUsuario();

                    archivoEscritura.writeUTF(libro.getTitulo());
                    archivoEscritura.writeUTF(libro.getAutor());
                    archivoEscritura.writeInt(libro.getAño());
                    archivoEscritura.writeBoolean(libro.isDisponible());

                    archivoEscritura.writeUTF(usuarioPrestamo.getNombre());
                    archivoEscritura.writeUTF(usuarioPrestamo.getIdentificacion());
                    archivoEscritura.writeUTF(usuarioPrestamo.getCorreo());
                    archivoEscritura.writeUTF(usuarioPrestamo.getTelefono());

                    // Escribir las fechas de préstamo y devolución
                    archivoEscritura.writeUTF(dateFormat.format(prestamo.getFechaPrestamo()));
                    archivoEscritura.writeUTF(dateFormat.format(prestamo.getFechaDevolucion()));
                } else {
                    // Si no hay más préstamos, escribir cadenas vacías y valores por defecto
                    archivoEscritura.writeUTF("");
                    archivoEscritura.writeUTF("");
                    archivoEscritura.writeInt(0);
                    archivoEscritura.writeBoolean(false);

                    archivoEscritura.writeUTF("");
                    archivoEscritura.writeUTF("");
                    archivoEscritura.writeUTF("");
                    archivoEscritura.writeUTF("");

                    // Escribir fechas de préstamo y devolución por defecto (puedes ajustar esto según tus necesidades)
                    archivoEscritura.writeUTF("");
                    archivoEscritura.writeUTF("");
                }
            }

            archivoEscritura.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: Ruta no encontrada");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error de escritura");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error General");
            e.printStackTrace();
        }
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        try {
            RandomAccessFile archivo = new RandomAccessFile(ruta, "rw");
            int bytesPorUsuario = 318;
            long numUsuarios = archivo.length() / bytesPorUsuario;

            for (int i = 0; i < numUsuarios; i++) {
                archivo.seek(i * bytesPorUsuario);
                String id = archivo.readUTF();
                if (id.equals(usuario.getIdentificacion())) {
                    archivo.seek(i * bytesPorUsuario);
                    archivo.writeUTF(usuario.getNombre());
                    archivo.writeUTF(usuario.getIdentificacion());
                    archivo.writeUTF(usuario.getCorreo());
                    archivo.writeUTF(usuario.getTelefono());
                    List<Prestamo> listaPrestamo = (List<Prestamo>) usuario.getListaPrestamos();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    for (int j = 0; j < 10; j++) {
                        archivo.writeUTF(listaPrestamo.get(j).getLibro().getTitulo());
                        archivo.writeUTF(listaPrestamo.get(j).getLibro().getAutor());
                        archivo.writeInt(listaPrestamo.get(j).getLibro().getAño());
                        archivo.writeBoolean(listaPrestamo.get(j).getLibro().isDisponible());

                        archivo.writeUTF(listaPrestamo.get(j).getUsuario().getNombre());
                        archivo.writeUTF(listaPrestamo.get(j).getUsuario().getIdentificacion());
                        archivo.writeUTF(listaPrestamo.get(j).getUsuario().getCorreo());
                        archivo.writeUTF(listaPrestamo.get(j).getUsuario().getTelefono());

                        archivo.writeUTF(dateFormat.format(listaPrestamo.get(j).getFechaPrestamo()));
                        archivo.writeUTF(dateFormat.format(listaPrestamo.get(j).getFechaDevolucion()));
                    }
                    archivo.close();
                    return;
                }
            }
            archivo.close();

        } catch (FileNotFoundException e) {
            System.out.println("Ruta no encontrada");
        } catch (IOException e) {
            System.out.println("Error de lectura/escritura");
        } catch (Exception e) {
            System.out.println("Error General");
        }
    }

    @Override
    public void eliminarUsuario(String identificacion) {
        try {
            RandomAccessFile archivo = new RandomAccessFile(ruta, "rw");
            int bytesPorUsuario = 318;
            long numUsuarios = archivo.length() / bytesPorUsuario;

            for (int i = 0; i < numUsuarios; i++) {
                archivo.seek(i * bytesPorUsuario);
                String identificacionUsuario = archivo.readUTF();
                if (identificacionUsuario.equals(identificacion)) {
                    // Encontramos el usuario a eliminar
                    for (int j = i; j < numUsuarios - 1; j++) {
                        // Mover los registros hacia arriba para llenar el espacio
                        archivo.seek((j + 1) * bytesPorUsuario);
                        byte[] datos = new byte[bytesPorUsuario];
                        archivo.readFully(datos);
                        archivo.seek(j * bytesPorUsuario);
                        archivo.write(datos);
                    }
                    // Truncar el archivo para eliminar el último registro duplicado
                    archivo.setLength((numUsuarios - 1) * bytesPorUsuario);
                    break;
                }
            }

            archivo.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ruta no encontrada");
        } catch (IOException e) {
            System.out.println("Error de lectura/escritura");
        } catch (Exception e) {
            System.out.println("Error General");
        }
    }

    @Override
    public Usuario buscarUsuarioPorId(String id) {
        try {
            archivoLectura = new RandomAccessFile(ruta, "r");
            int bytesPorUsuario = 318;  // Ajustar según la longitud real de los datos de usuario
            long numUsuarios = archivoLectura.length() / bytesPorUsuario;

            for (int i = 0; i < numUsuarios; i++) {
                archivoLectura.seek(i * bytesPorUsuario);
                String idUsuario = archivoLectura.readUTF();

                if (idUsuario.equals(id)) {
                    String nombre = archivoLectura.readUTF();
                    String identificacion = archivoLectura.readUTF();
                    String correo = archivoLectura.readUTF();
                    String telefono = archivoLectura.readUTF();

                    Usuario usuario = new Usuario(nombre, identificacion, correo, telefono);
                    for (int j = 0; j < 10; j++) {
                        String titulo = archivoLectura.readUTF();
                        if (!titulo.isEmpty()) {
                            String autor = archivoLectura.readUTF();
                            int anio = archivoLectura.readInt();
                            boolean dis = archivoLectura.readBoolean();

                            String nombrePrestamo = archivoLectura.readUTF();
                            String identificacionPrestamo = archivoLectura.readUTF();
                            String correoPrestamo = archivoLectura.readUTF();
                            String telefonoPrestamo = archivoLectura.readUTF();

                            Usuario usuarioPrestamo = new Usuario(nombrePrestamo, identificacionPrestamo, correoPrestamo, telefonoPrestamo);
                            Libro libroPrestamo = new Libro(titulo, autor, anio);
                            libroPrestamo.setDisponible(dis);

                            Prestamo prestamo = new Prestamo(libroPrestamo, usuarioPrestamo);

                            // Leer las fechas de préstamo y devolución
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date fechaPrestamo = dateFormat.parse(archivoLectura.readUTF());
                            Date fechaDevolucion = dateFormat.parse(archivoLectura.readUTF());

                            prestamo.setFechaPrestamo(fechaPrestamo);
                            prestamo.setFechaDevolucion(fechaDevolucion);

                            usuario.agregarPrestamo(prestamo);
                        }
                    }

                    archivoLectura.close();
                    return usuario;
                } else {
                    // Saltar la lectura de los campos restantes del usuario actual
                    archivoLectura.skipBytes(bytesPorUsuario - (idUsuario.length() * 2));
                }
            }

            archivoLectura.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ruta no encontrada");
        } catch (IOException e) {
            System.out.println("Error de lectura");
        } catch (Exception e) {
            System.out.println("Error General");
        }
        return null;
    }

    @Override
    public List<Usuario> obtenerTodosUsuarios() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        try {
            RandomAccessFile archivoLectura = new RandomAccessFile(ruta, "r");
            int bytesPorUsuario = 318;
            long numUsuario = archivoLectura.length() / bytesPorUsuario;
            for (int i = 0; i < 10; i++) {
                archivoLectura.seek(i * bytesPorUsuario);
                String nombre = archivoLectura.readUTF();
                String identificacionn = archivoLectura.readUTF();
                String correo = archivoLectura.readUTF();
                String telefono = archivoLectura.readUTF();
                Usuario usuario = new Usuario(nombre, identificacionn, correo, telefono);
                for (int j = 0; j < 10; j++) {
                    String titulo = archivoLectura.readUTF();
                    String autor = archivoLectura.readUTF();
                    int anio = archivoLectura.readInt();
                    Libro libro = new Libro(titulo, autor, anio);

                    String nombree = archivoLectura.readUTF();
                    String idetifi = archivoLectura.readUTF();
                    String correeo = archivoLectura.readUTF();
                    String telef = archivoLectura.readUTF();
                    Usuario usuarioo = new Usuario(nombree, idetifi, correeo, telef);
                    Prestamo prestamo = new Prestamo(libro, usuarioo);
                    usuario.agregarPrestamo(prestamo);
                }
                listaUsuarios.add(usuario);
            }
            archivoLectura.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ruta no encontrada");
        } catch (IOException e) {
            System.out.println("Error de lectura");
        } catch (Exception e) {
            System.out.println("Error General");
        }
        return listaUsuarios;
    }
}
