package com.nebulahelix.torre3.entity;
// Generated 17/03/2015 02:07:05 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Usuarios generated by hbm2java
 */
public class Usuarios  implements java.io.Serializable {


     private Long id;
     private String usrName;
     private String usrPass;
     private String nombreCompleto;
     private String carrera;
     private String matricula;
     private String telCelular;
     private String token;
     private Set proyectoses = new HashSet(0);
     private Set skillses = new HashSet(0);
     private Set avatars = new HashSet(0);
     private Set anuncioses = new HashSet(0);

    public Usuarios() {
    }

	
    public Usuarios(String usrName, String usrPass, String nombreCompleto, String carrera, String matricula, String telCelular, String token) {
        this.usrName = usrName;
        this.usrPass = usrPass;
        this.nombreCompleto = nombreCompleto;
        this.carrera = carrera;
        this.matricula = matricula;
        this.telCelular = telCelular;
        this.token = token;
    }
    public Usuarios(String usrName, String usrPass, String nombreCompleto, String carrera, String matricula, String telCelular, String token, Set proyectoses, Set skillses, Set avatars, Set anuncioses) {
       this.usrName = usrName;
       this.usrPass = usrPass;
       this.nombreCompleto = nombreCompleto;
       this.carrera = carrera;
       this.matricula = matricula;
       this.telCelular = telCelular;
       this.token = token;
       this.proyectoses = proyectoses;
       this.skillses = skillses;
       this.avatars = avatars;
       this.anuncioses = anuncioses;
    }
   
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsrName() {
        return this.usrName;
    }
    
    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }
    public String getUsrPass() {
        return this.usrPass;
    }
    
    public void setUsrPass(String usrPass) {
        this.usrPass = usrPass;
    }
    public String getNombreCompleto() {
        return this.nombreCompleto;
    }
    
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    public String getCarrera() {
        return this.carrera;
    }
    
    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
    public String getMatricula() {
        return this.matricula;
    }
    
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    public String getTelCelular() {
        return this.telCelular;
    }
    
    public void setTelCelular(String telCelular) {
        this.telCelular = telCelular;
    }
    public String getToken() {
        return this.token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    public Set getProyectoses() {
        return this.proyectoses;
    }
    
    public void setProyectoses(Set proyectoses) {
        this.proyectoses = proyectoses;
    }
    public Set getSkillses() {
        return this.skillses;
    }
    
    public void setSkillses(Set skillses) {
        this.skillses = skillses;
    }
    public Set getAvatars() {
        return this.avatars;
    }
    
    public void setAvatars(Set avatars) {
        this.avatars = avatars;
    }
    public Set getAnuncioses() {
        return this.anuncioses;
    }
    
    public void setAnuncioses(Set anuncioses) {
        this.anuncioses = anuncioses;
    }




}


