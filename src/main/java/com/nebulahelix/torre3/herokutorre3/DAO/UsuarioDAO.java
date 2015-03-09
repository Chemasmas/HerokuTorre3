/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nebulahelix.torre3.herokutorre3.DAO;

import com.nebulahelix.torre3.herokutorre3.entity.Avatar;
import com.nebulahelix.torre3.herokutorre3.entity.Proyecto;
import com.nebulahelix.torre3.herokutorre3.entity.Skill;
import com.nebulahelix.torre3.herokutorre3.entity.Usuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chemasmas
 */
public class UsuarioDAO {
    
    public Usuario getUsuario(int id)
    {
        Usuario u=null;
        
        
        return u;
    }
    
    static public String guardarUsuario(Usuario u)
    {
        try {
            Connection con=BD.getConnection();
            
            //Usamos CallableStatement para los procecimientos
            CallableStatement createUser=null;
            String createUserS="{? = call registrarUsuario(?,?,?,?,?,?,?,?)}";
            
            createUser=con.prepareCall(createUserS);
            createUser.registerOutParameter(1, Types.INTEGER);
            createUser.setString(2, u.getUsername());
            createUser.setString(3, u.getPassword());
            createUser.setString(4, u.getNombreCompleto());
            createUser.setString(5, u.getCarrera());
            createUser.setString(6, u.getMatricula());
            createUser.setString(7, u.getCorreo());
            createUser.setString(8, u.getTelCelular());
            createUser.setString(9, u.getPassword()); //Falta la politica de creacion del token
            
            createUser.execute();
            
            int id=createUser.getInt(1);
            
            createUser.close();
            
            for (Skill skill : u.getSkills()) {
                CallableStatement addSkill=null;
                String addSkillS="{call addSkill(?,?,?)}";
                addSkill=con.prepareCall(addSkillS);
                
                addSkill.setInt(1, id);
                addSkill.setString(2,skill.getSkill());
                addSkill.setInt(3,skill.getTipo());
                
                addSkill.execute();
                
                addSkill.close();
            }
            
            for (Proyecto proyecto : u.getProyectos()) {
                CallableStatement addProyecto=null;
                String addProyectoS="{call addProyecto(?,?,?,?)}";
                addProyecto=con.prepareCall(addProyectoS);
                
                addProyecto.setInt(1, id);
                addProyecto.setString(2,proyecto.getNombre());
                addProyecto.setString(3,proyecto.getDescripcion());
                addProyecto.setBoolean(4,proyecto.isActivo());
                
                addProyecto.execute();
                
                addProyecto.close();
            }
            
            CallableStatement setAvatar=null;
            String setAvatarS="{call setAvatar(?,?,?,?,?)}";
            
            setAvatar=con.prepareCall(setAvatarS);
            
            Avatar a= u.getAvatar();
            
            setAvatar.setInt(1,id);
            setAvatar.setInt(2,a.getCabeza());
            setAvatar.setInt(3,a.getTorso());
            setAvatar.setInt(4,a.getBrazos());
            setAvatar.setInt(5,a.getPiernas());
            
            setAvatar.execute();
            
            setAvatar.close();
            
            return u.getPassword();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}
