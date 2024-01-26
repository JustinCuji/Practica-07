/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.ups.nuevo.controlador;

import ec.edu.ups.nuevo.dao.IUsuarioDAO;
import ec.edu.ups.nuevo.modelo.Libro;
import ec.edu.ups.nuevo.modelo.Usuario;
import java.util.List;

/**
 *
 * @author davidvargas
 */
public class UsuarioControlador {
    private IUsuarioDAO usuarioDAO;
    private Usuario usuario;

    public UsuarioControlador(IUsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public void registrarUsuario(Usuario usuario) {
        // Puedes agregar lógica adicional aquí antes de registrar el usuario, si es necesario.
        this.usuario = usuario;
        usuarioDAO.agregarUsuario(usuario);
    }

    public void solicitarPrestamo(Usuario usuario, Libro libro) {
        usuario.solicitarPrestamo(libro);
    }

    public void devolverLibro(Usuario usuario, Libro libro) {
        usuario.devolverLibro(libro);
    }

    public Usuario buscarUsuarioPorId(String id) {
        this.usuario = usuarioDAO.buscarUsuarioPorId(id);
        return this.usuario;
    }
    
    public boolean actualizar(Usuario usuario){
        Usuario personaEncontrada = this.buscarUsuarioPorId(usuario.getIdentificacion());
        if(personaEncontrada != null){
            usuarioDAO.actualizarUsuario(usuario);
            return true;
        }
        return false;
    }
    public boolean eliminar(String id){
        Usuario usuario = this.buscarUsuarioPorId(id);
        if(usuario != null){
            usuarioDAO.eliminarUsuario(id);
            return true;
        }
        return false;
    }
    
    public List<Usuario> listarUsuarios(){
        return usuarioDAO.obtenerTodosUsuarios();
    }
}
 
