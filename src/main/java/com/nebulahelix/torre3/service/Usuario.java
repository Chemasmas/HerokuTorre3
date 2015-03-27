package com.nebulahelix.torre3.service;

import com.nebulahelix.torre3.entity.Proyectos;
import com.nebulahelix.torre3.entity.Skills;
import com.nebulahelix.torre3.entity.Usuarios;
import com.nebulahelix.torre3.entity.json.UsuarioJson;
import com.nebulahelix.torre3.util.Encriptado;
import com.nebulahelix.torre3.util.HibernateUtil2;
import com.sun.media.jfxmedia.logging.Logger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Chemasmas
 */
@Path("/usuario")
public class Usuario {

    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response test()
    {
        Token a=new Token();
        a.setToken("Test");

        return Response.status(200).entity(a).build();
    }
    
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("usr") String usr,
                        @FormParam("pwd") String pwd,
                        @FormParam("uuid") String uuid)
    {
        System.out.println("Datos recibidos");
        System.err.println(usr);
        System.err.println(pwd);
        System.err.println(uuid);
        Session sesion=HibernateUtil2.getSessionFactory().openSession();
        Query query=sesion.createQuery("FROM Usuarios where usrName= :name AND usrPass = :pass");
        query.setParameter("name", usr);
        query.setParameter("pass", Encriptado.SHA256(pwd));
        
        List res=query.list();
        
        if(res.size()!=0)
        {
            Usuarios u= (Usuarios)res.get(0);
            u.setToken(Encriptado.SHA256(u.getUsrPass()+uuid));
            Token t=new Token();
            t.setToken(u.getToken());
            sesion.update(u);
            sesion.close();
            return Response.status(200).entity(t).build();
        }
        else
           {
            Mensaje m=new Mensaje();
            m.setTipo(Tipo.Fallo);
            m.setTitulo("Usuario No encontrado");
            m.setDetalle("Ese usuario no se encuentra en la base de datos");
            
            //Logger.logMsg(Logger.ERROR, "No se encontro Ningun Usuario");
            return Response.status(404).entity(m).build();
        }
    }
    
    @POST
    @Path("/login2")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("usr") String usr,
                        @FormParam("token") String token)
    {
        //Logger.logMsg(Logger.INFO, "Login llamado");
        Session sesion=HibernateUtil2.getSessionFactory().openSession();
        Query query=sesion.createQuery("FROM Usuarios where usrName= :name AND token = :token");
        query.setParameter("name", usr);
        query.setParameter("token", token);
        
        List res=query.list();
        
        if(res.size()!=0)
        {
            Usuarios u= (Usuarios)res.get(0);
            //Token t=new Token();
            //t.setToken(u.getToken());
            Mensaje m=new Mensaje();
            m.setDetalle("El usuario ya esta logueado");
            m.setTipo(Tipo.Exito);
            m.setTitulo("Usuario Loggueado");
            sesion.close();
            
            return Response.status(200).entity(m).build();
        }
        else
        {
            Mensaje m=new Mensaje();
            m.setTipo(Tipo.Fallo);
            m.setTitulo("Usuario No encontrado");
            m.setDetalle("Ese usuario no se encuentra en la base de datos");
            
            return Response.status(404).entity(m).build();
        }
    }
    
    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    public Token Registrar(
                        @FormParam("usr") String usuario,
                        @FormParam("pwd") String contrasena,
                        @FormParam("nombre") String nombre,
                        @FormParam("matricula") String matricula,
                        @FormParam("carrera") String carrera,
                        @FormParam("habilidades") String habilidades,
                        @FormParam("servicios") String servicios,
                        @FormParam("conocimientos") String conocimientos,
                        @FormParam("intereses") String intereses,
                        @FormParam("celular") String celular,
                        @FormParam("proyectosPrevios") String proyectosPrevios,
                        @FormParam("proyectosActuales") String proyectosActuales)//,
                        //@FormParam("uuid") String uuid)
    {
        System.out.println("Inicio registro de "+nombre);
        
        Usuarios u=new Usuarios();
        u.setUsrName(usuario);
        u.setUsrPass(Encriptado.SHA256(contrasena));
        
        u.setNombreCompleto(nombre);
        u.setMatricula(matricula);
        u.setTelCelular(celular);
        u.setCarrera(carrera);
        //u.setToken(Encriptado.SHA256(u.getUsrPass()+uuid));
        //Skills
        HashSet<Skills> skills=new HashSet<Skills>();
        StringTokenizer sth=new StringTokenizer(habilidades,",");
        while(sth.hasMoreTokens())
        {
            Skills a=new Skills();
            a.setSkill(sth.nextToken());
            a.setTipoSkill(1);
            a.setUsuarios(u);
            skills.add(a);
        }
        
        StringTokenizer sts=new StringTokenizer(servicios,",");
        while(sts.hasMoreTokens())
        {
            Skills a=new Skills();
            a.setSkill(sts.nextToken());
            a.setTipoSkill(2);
            a.setUsuarios(u);
            skills.add(a);
        }
        
        StringTokenizer stc=new StringTokenizer(conocimientos,",");
        while(stc.hasMoreTokens())
        {
            Skills a=new Skills();
            a.setSkill(stc.nextToken());
            a.setTipoSkill(3);
            a.setUsuarios(u);
            skills.add(a);
        }
        
        StringTokenizer sti=new StringTokenizer(intereses,",");
        while(sti.hasMoreTokens())
        {
            Skills a=new Skills();
            a.setSkill(sti.nextToken());
            a.setTipoSkill(4);
            a.setUsuarios(u);
            skills.add(a);
        }
        u.setSkillses(skills);
        
        //Proyectos
        HashSet<Proyectos> proyectos=new HashSet<Proyectos>();
        StringTokenizer stp=new StringTokenizer(proyectosPrevios,",");
        while(stp.hasMoreTokens())
        {
            Proyectos p=new Proyectos();
            p.setActivo(false);
            p.setUsuarios(u);
            StringTokenizer separa=new StringTokenizer(stp.nextToken(),":");
            p.setNombre(separa.nextToken()); // nombre del proyecto
            if(separa.hasMoreTokens())
                p.setDescripcion(separa.nextToken());// si la coloco una descripcion
            
            proyectos.add(p);
        }
        
        StringTokenizer sta=new StringTokenizer(proyectosActuales,",");
        while(sta.hasMoreTokens())
        {
            Proyectos p=new Proyectos();
            p.setActivo(true);
            p.setUsuarios(u);
            StringTokenizer separa=new StringTokenizer(sta.nextToken(),":");
            p.setNombre(separa.nextToken()); // nombre del proyecto
            if(separa.hasMoreTokens())
                p.setDescripcion(separa.nextToken()); // si la coloco una descripcion
            
            proyectos.add(p);
        }
        u.setProyectoses(proyectos);
        
        Session sesion=HibernateUtil2.getSessionFactory().openSession();
        
        sesion.beginTransaction();
        
        sesion.save(u);
        
        for (Skills skill :(Set<Skills>)u.getSkillses()) {
            sesion.save(skill);
        }
        
        for (Proyectos proyecto :(Set<Proyectos>)u.getProyectoses()) {
            sesion.save(proyecto);
        }
        
        sesion.getTransaction().commit();
        
        sesion.close();
        
        Token t=new Token();
        t.setToken(u.getToken());
        
        return t;
    }
}

@XmlRootElement
class Token
{
    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
}

enum Tipo{
    Exito,Informativo,Fallo
}

@XmlRootElement
class ListaMsg
{
    List<Mensaje> mensajes;
    
    public ListaMsg()
    {
        mensajes=new ArrayList<Mensaje>();
    }
    
    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }
}

class Mensaje
{
    private String titulo;
    private String detalle;
    private Tipo tipo;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}

@XmlRootElement
class avatarJson
{
    private int cabeza;
    private int torso;
    private int brazos;
    private int piernas;

    public int getCabeza() {
        return cabeza;
    }

    public void setCabeza(int cabeza) {
        this.cabeza = cabeza;
    }

    public int getTorso() {
        return torso;
    }

    public void setTorso(int torso) {
        this.torso = torso;
    }

    public int getBrazos() {
        return brazos;
    }

    public void setBrazos(int brazos) {
        this.brazos = brazos;
    }

    public int getPiernas() {
        return piernas;
    }

    public void setPiernas(int piernas) {
        this.piernas = piernas;
    }
}
