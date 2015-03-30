package com.nebulahelix.torre3.service;

import com.nebulahelix.torre3.entity.Avatar;
import com.nebulahelix.torre3.entity.Proyectos;
import com.nebulahelix.torre3.entity.Skills;
import com.nebulahelix.torre3.entity.Usuarios;
import com.nebulahelix.torre3.entity.json.Notificacion;
import com.nebulahelix.torre3.entity.json.Tipo;
import com.nebulahelix.torre3.entity.json.Token;
import com.nebulahelix.torre3.entity.json.UsuarioJson;
import com.nebulahelix.torre3.entity.json.avatarJson;
import com.nebulahelix.torre3.util.Encriptado;
import com.nebulahelix.torre3.util.HibernateUtil2;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.logging.Logger;
import javax.ws.rs.PathParam;



/**
 *
 * @author Chemasmas
 */
@Path("/usuario")
public class Usuario {
    
    static final Logger log=Logger.getLogger("Usuario");
    

    @GET
    @Path("/status")
    @Produces(MediaType.TEXT_HTML)
    public Response status()
    {
        log.info("Estatus del Servicio");
        StringBuilder respuesta=new StringBuilder();
        respuesta.append("Base de Datos<br>");
        Session sesion=HibernateUtil2.getSessionFactory().openSession();
        try{
            
            sesion.beginTransaction();
            log.info("Probando la Base de datos");
            log.info("Correcto");
            log.info("----------------------------------------------------------");
            respuesta.append("Servicio Funcionando<br>");
        }catch(Exception e)
        {
            log.severe("Algo salio mal");
            log.severe(e.getMessage());
            log.severe("--------------------------------------------------------");
            respuesta.append("Problema con la base de datos<br>");
        }
        finally
        {
            sesion.close();
        }
        
        return Response.ok(respuesta.toString()).build();
    }

    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response test()
    {
        //log.finest("Ok!");
        log.info("Funcionando");
        log.warning("Si algo Falla");
        log.severe("Se murio");
        Token a=new Token();
        a.setToken("Test");

        
        //return Response.status(200).entity(a).build();
        return Response.ok(a).build();
    }
    
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("usr") String usr,
                        @FormParam("pwd") String pwd,
                        @FormParam("uuid") String uuid)
    {
        log.info("Servicio de Login llamado");        
        List res;
        Session sesion;
        
        try
        {
            sesion=HibernateUtil2.getSessionFactory().openSession();
            Query query=sesion.createQuery("FROM Usuarios where usrName= :name AND usrPass = :pass");
            query.setParameter("name", usr);
            query.setParameter("pass", Encriptado.SHA256(pwd));
            log.info(query.getQueryString());
            res=query.list();
            if(!res.isEmpty())
            {
                log.info("El usuario fue encontrado");
                Usuarios u= (Usuarios)res.get(0);
                
                u.setToken(Encriptado.SHA256(u.getUsrPass()+uuid));
                Token t=new Token();
                t.setToken(u.getToken());
                sesion.update(u);
                sesion.getTransaction().commit();
                sesion.close();
                log.info("Solicitud procesada exitosamente");
                return Response.ok(t).build();
            }
            else
               {
                
                log.warning("Intento de Login Fallido");
                log.warning("El intento fallido de login");
                Notificacion n=new Notificacion();
                n.setTipo(Tipo.Fallo);
                n.setNotificacion("Esa pareja Usuario/Password NO esta registrada");

                return Response.ok(n).build();
            }
        }
        catch(Exception e)
        {
            log.severe("Algo salio mal");
            log.severe(e.getMessage());
            log.severe("--------------------------------------------------------");
                     
            Notificacion n=new Notificacion();
            n.setNotificacion("El servicio NO esta disponible<br> revise el status del Servicio<br>");
            n.setTipo(Tipo.Fatal);
            return Response.ok(n).build();
        }
    }
    
    @POST
    @Path("/login2")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("usr") String usr,
                        @FormParam("token") String token)
    {
        log.info("Test validacion de token");
        Session sesion;
        try
        {
            sesion=HibernateUtil2.getSessionFactory().openSession();
            Query query=sesion.createQuery("FROM Usuarios where usrName= :name AND token = :token");
            query.setParameter("name", usr);
            query.setParameter("token", token);
            
            log.info(query.getQueryString());
            List res=query.list();
            if(!res.isEmpty())
            {
                log.info("Usuario Encontrado");
                Usuarios u= (Usuarios)res.get(0);
                Notificacion m=new Notificacion();
                m.setTipo(Tipo.Exito);
                m.setNotificacion("Usuario Loggueado");
                sesion.close();

                return Response.ok(m).build();
            }
            else
            {
                log.info("Usuario no encontrado");
                Notificacion m=new Notificacion();
                m.setTipo(Tipo.Fallo);
                m.setNotificacion("Esa pareja Token/usuario no es valida");

                return Response.ok(m).build();
            }
        }
        catch(Exception e)
        {
            log.severe("Algo salio mal");
            log.severe(e.getMessage());
            log.severe("--------------------------------------------------------");
                     
            Notificacion n=new Notificacion();
            n.setNotificacion("El servicio NO esta disponible<br> revise el status del Servicio<br>");
            n.setTipo(Tipo.Fatal);
            return Response.ok(n).build();
        }
    }
    
    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    public Response Registrar(
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
        
        log.info("Registrando");
        
        //Validacion de Vacio
        if(usuario.isEmpty() || contrasena.isEmpty() || nombre.isEmpty() || matricula.isEmpty()
                || carrera.isEmpty() || celular.isEmpty())
        {
            log.warning("Peticion incompleta");
            
            Notificacion n= new Notificacion();
            n.setTipo(Tipo.Fallo);
            n.setNotificacion("La peticion fallo, faltan campos");
        }
        else
        {
            
            try
            {
                log.info("Preparando la entidad");
                Usuarios u=new Usuarios();
                u.setUsrName(usuario);
                u.setUsrPass(Encriptado.SHA256(contrasena));
        
                u.setNombreCompleto(nombre);
                u.setMatricula(matricula);
                u.setTelCelular(celular);
                u.setCarrera(carrera);
                //u.setToken(Encriptado.SHA256(u.getUsrPass()+uuid));
                u.setToken("DUMMY");
                
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
                
                //Hora de la persistencia
                log.info("Iniciando la transaccion");
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
                
                log.info("Transaccion exitosa, Retornando Token");
                Token t=new Token();
                t.setToken(u.getToken());
                return Response.ok(t).build();
            }
            catch(Exception e)
            {
                log.severe("Fallo de hibernate");
                
                Notificacion n=new Notificacion();
                n.setTipo(Tipo.Fallo);
                n.setNotificacion("No se realizo la operacion");
                return Response.ok(n).build();
            }
        }
        Notificacion n= new Notificacion();
        n.setTipo(Tipo.Informativo);
        n.setNotificacion("Error Desconocido");
        return Response.ok(n).build();
    }
    
    @GET
    @Path("/{id}/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuario(@PathParam("id") long id)
    {
        log.info("Iniciando La operacion De recuperar Usuario");
        Session sesion;
        
        try
        {
            sesion=HibernateUtil2.getSessionFactory().openSession();
        
            sesion.beginTransaction();

            Query query=sesion.createQuery("FROM Usuarios where id = :idU");
            query.setParameter("idU", id);

            log.info(query.getQueryString());
            
            List<Usuarios> res=query.list();
            if(res.isEmpty())
            {
                //Algo fallo
                Notificacion n=new Notificacion();
                n.setTipo(Tipo.Fallo);
                n.setNotificacion("Ese ID no es valido");
                return Response.ok(n).build();
            }
            else
            {
                Usuarios u=res.get(0);
                UsuarioJson uj=new UsuarioJson();

                uj.setCarrera(u.getCarrera());
                uj.setId(u.getId());
                uj.setMatricula(u.getMatricula());
                uj.setNombreCompleto(u.getNombreCompleto());
                uj.setTelCelular(u.getTelCelular());

                return Response.ok(uj).build();
            }
        }
        catch(Exception e)
        {
            log.severe("Fallo de hibernate");
                
            e.printStackTrace();
            Notificacion n=new Notificacion();
            n.setTipo(Tipo.Fallo);
            n.setNotificacion("No se realizo la operacion");
            
            return Response.ok(n).build();
        }
        
    }
    
    @POST
    @Path("/{id}/edit")
    @Produces(MediaType.APPLICATION_JSON)
    public Response editarUsuario(@PathParam("id") long id,
                                  @FormParam("nombre") String nombre,
                                  @FormParam("matricula") String matricula,
                                  @FormParam("carrera") String carrera,
                                  @FormParam("celular") String celular)
    {
        
        log.info("Editando Usuario");
        Session sesion;
        try
        {
            sesion=HibernateUtil2.getSessionFactory().openSession();
        
            sesion.beginTransaction();
            
            Query query=sesion.createQuery("FROM Usuarios where id= :idU");
            query.setParameter("idU", id);

            log.info(query.getQueryString());
            
            List<Usuarios> res=query.list();
            if(res.isEmpty())
            {
                //Algo fallo
                Notificacion n=new Notificacion();
                n.setTipo(Tipo.Fallo);
                n.setNotificacion("Ese ID no es valido");
                return Response.ok(n).build();
            }
            else
            {
                Usuarios u=res.get(0);
                // Solo modifico los valores que no estan en blanco
                if(!nombre.isEmpty())
                    u.setNombreCompleto(nombre);
                if(!matricula.isEmpty())
                    u.setMatricula(matricula);
                if(!carrera.isEmpty())
                    u.setCarrera(carrera);
                if(!celular.isEmpty())
                    u.setTelCelular(celular);
                
                sesion.update(u);
                
                Notificacion n=new Notificacion();
                n.setTipo(Tipo.Exito);
                n.setNotificacion("Usuario actualizado extiosamente");
                
                return Response.ok(n).build();
            }
            
            
        }catch(Exception e)
        {
            log.severe("Fallo de hibernate");
                
            Notificacion n=new Notificacion();
            n.setTipo(Tipo.Fallo);
            n.setNotificacion("No se realizo la operacion");
            
            return Response.ok(n).build();
        }
    }
    
    @GET
    @Path("/{id}/avatar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvatar(@PathParam("id") long id)
    {
        log.info("Return Avatar");
        Session sesion;
        try
        {
            sesion=HibernateUtil2.getSessionFactory().openSession();
        
            sesion.beginTransaction();
            Query q1= sesion.createQuery("FROM Usuarios where id = :idU");
            q1.setParameter("idU", id);
            
            List usuarios=q1.list();
            if(usuarios.isEmpty())
            {
                log.warning("No se encontro ese usuario");
                Notificacion n= new Notificacion();
                n.setTipo(Tipo.Fallo);
                n.setNotificacion("Fallo encontrando ese usuario");
                
                return Response.ok(n).build();
            }
            else
            {
                Usuarios u=(Usuarios)usuarios.get(0);
                Query query=sesion.createQuery("FROM Avatar where usuarios = :usr");
                query.setParameter("usr", u);
                
                List avatar=query.list();
                
                if(avatar.isEmpty())
                {
                    log.warning("No se encontro ese Avatar");
                    Notificacion n= new Notificacion();
                    n.setTipo(Tipo.Fallo);
                    n.setNotificacion("Fallo encontrando el avatar de ese usuario");
                
                    return Response.ok(n).build();
                }
                else
                {
                    log.info("Avatar encontrado!");
                    
                    Avatar av=(Avatar)avatar.get(0);
                    avatarJson aj= new avatarJson();
                    aj.setBrazos(av.getBrazos());
                    aj.setCabeza(av.getCabeza());
                    aj.setPiernas(av.getPiernas());
                    aj.setTorso(av.getTorso());

                    return Response.ok(aj).build();
                }
            }   
        }
        catch(Exception e)
        {
            log.severe("Fallo de hibernate");
                
            Notificacion n=new Notificacion();
            n.setTipo(Tipo.Fallo);
            n.setNotificacion("No se realizo la operacion");
            
            return Response.ok(n).build();
        }
    }

    /*
    @GET
    @Path("/{id}/avatar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response proto()
    {
        log.info("Return Avatar");
        Session sesion;
        try
        {
            sesion=HibernateUtil2.getSessionFactory().openSession();
        
            sesion.beginTransaction();
        }
        catch(Exception e)
        {
            log.severe("Fallo de hibernate");
                
            Notificacion n=new Notificacion();
            n.setTipo(Tipo.Fallo);
            n.setNotificacion("No se realizo la operacion");
            
            return Response.ok(n).build();
        }
        return Response.serverError().build();
    }
    */

}
