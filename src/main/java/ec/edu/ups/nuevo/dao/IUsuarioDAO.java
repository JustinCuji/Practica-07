/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.ups.nuevo.dao;

import ec.edu.ups.nuevo.modelo.Usuario;
import java.util.List;

/**
 *
 * @author davidvargas
 */
public interface IUsuarioDAO {
    void agregarUsuario(Usuario usuario);
    void actualizarUsuario(Usuario usuario);
    void eliminarUsuario(String identificacion);
    Usuario buscarUsuarioPorId(String id);
    List<Usuario> obtenerTodosUsuarios();
}
