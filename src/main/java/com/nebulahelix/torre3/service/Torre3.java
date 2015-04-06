package com.nebulahelix.torre3.service;

import com.nebulahelix.torre3.entity.Anuncios;
import com.nebulahelix.torre3.entity.Avatar;
import com.nebulahelix.torre3.entity.Usuarios;
import com.nebulahelix.torre3.entity.json.Casilla;
import com.nebulahelix.torre3.entity.json.ListaMsg;
import com.nebulahelix.torre3.entity.json.Mensaje;
import com.nebulahelix.torre3.entity.json.Notificacion;
import com.nebulahelix.torre3.entity.json.Pasillo;
import com.nebulahelix.torre3.entity.json.Segmento;
import com.nebulahelix.torre3.entity.json.Tipo;
import com.nebulahelix.torre3.entity.json.avatarJson;
import com.nebulahelix.torre3.util.HibernateUtil2;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.inject.Singleton;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.hibernate.Query;
import org.hibernate.Session;


/**
 *
 * @author Chemasmas
 */

@Path("/torre")
@Singleton
public class Torre3 {
    
    private static Segmento mundo;

    private static void addAnuncio(Anuncios anuncio) {
        
        Mensaje m=new Mensaje();
        m.setTitulo(anuncio.getTitulo());
        m.setDetalle(anuncio.getContenido());
        mundo.getPasillos().get(anuncio.getPiso()).getCasillas().get(anuncio.getBloque()).addAnuncio(m);
    }

    private static avatarJson getUsr(Long id) {
        for (Pasillo pasillo : mundo.getPasillos()) {
            for (Casilla c: pasillo.getCasillas()) {
                for (avatarJson aj : c.getUsuarios()) {
                    if(aj.getId()==id)
                        return aj;
                }
            }
        }
        return null;
    }
    
    private void iniciarMundo()
    {
        mundo=new Segmento();
        
        mundo.getPasillos().add(new Pasillo());
        mundo.getPasillos().add(new Pasillo());
        mundo.getPasillos().add(new Pasillo());
        mundo.getPasillos().add(new Pasillo());
        mundo.getPasillos().add(new Pasillo());
        mundo.getPasillos().add(new Pasillo());
        mundo.getPasillos().add(new Pasillo());
        
        //Anuncios
        Session sesion=HibernateUtil2.getSessionFactory().openSession();
        sesion.beginTransaction();
        
        Query anuncios=sesion.createQuery("from Anuncios where FechaExpiracion > current_date()");
        
        List<Anuncios> anuncio=anuncios.list();
        for (Anuncios anuncio1 : anuncio) {
            //La seccion siempre es uno
            Torre3.addAnuncio(anuncio1);
        }
        
    }
    
    static final Logger log=Logger.getLogger("Mundo");
    
    public static void addUsr(int y,int x,long id)
    {
        try
        {
            
            Session sesion=HibernateUtil2.getSessionFactory().openSession();
            sesion.beginTransaction();

            avatarJson aj=new avatarJson();

            Query q=sesion.createQuery("select a from Usuarios as u , Avatar as a where u.id=a.usuarios.id and u.id= :id");
            q.setParameter("id",id);
            
            List<Avatar> avatares=q.list();
            if(avatares.isEmpty())
            {
                Notificacion n=new Notificacion();
                n.setTipo(Tipo.Fallo);
                n.setNotificacion("Id Invalido");
            }
            else
            {
                Casilla c = mundo.getPasillos().get(y).getCasillas().get(x);
                
                Avatar av=avatares.get(0);
                aj.setBrazos(av.getBrazos());
                aj.setCabeza(av.getCabeza());
                //Es el id de avatar
                aj.setId(av.getUsuarios().getId());
                aj.setPiernas(av.getPiernas());
                aj.setTorso(av.getTorso());
                
                c.addUser(aj);
                
                log.info("Usuario Agregado");
            }
        }
        catch(Exception e)
        {
            Notificacion n=new Notificacion();
            n.setNotificacion("Error del Servidor");
            n.setTipo(Tipo.Fatal);
            
        }
    }
    
    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response mundo()
    {
        if(mundo==null) iniciarMundo();
        return Response.ok(mundo).build();
    }
    
    @Path("/{y}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response mundo(@PathParam("y") int y)
    {
        if(mundo==null) iniciarMundo();
        try
        {
            Pasillo p=mundo.getPasillos().get(y);
            return Response.ok(p).build();
        }
        catch(Exception ie)
        {
            Notificacion n=new Notificacion();
            n.setNotificacion("Peticion invalida");
            n.setTipo(Tipo.Fallo);
            return Response.ok(n).build();
        }
    }
    
    @Path("/{y}/{x}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response mundo(@PathParam("x") int x,
                         @PathParam("y") int y)
    {
        if(mundo==null) iniciarMundo();
        try
        {
            Casilla c=mundo.getPasillos().get(y).getCasillas().get(x);
            return Response.ok(c).build();
        }
        catch(Exception ie)
        {
            Notificacion n=new Notificacion();
            n.setNotificacion("Peticion invalida");
            n.setTipo(Tipo.Fallo);
            return Response.ok(n).build();
        }
    }
    
    @Path("/{y}/{x}/anuncios")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response anunciosMundo(@PathParam("x") int x,
                         @PathParam("y") int y)
    {
        if(mundo==null) iniciarMundo();
        try
        {
            Casilla c=mundo.getPasillos().get(y).getCasillas().get(x);
            ListaMsg l=new ListaMsg();
            
            l.setMensajes(c.getMensajes());
            
            return Response.ok(l).build();
        }
        catch(Exception ie)
        {
            Notificacion n=new Notificacion();
            n.setNotificacion("Peticion invalida");
            n.setTipo(Tipo.Fallo);
            return Response.ok(n).build();
        }
    }
    
    
    @Path("/{y}/{x}/anuncios/add")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAnuncio(@PathParam("x") int x,
                        @PathParam("y") int y,
                        @FormParam("usr") String usr,
                        @FormParam("token") String token,
                        @DefaultValue("Sin Titulo") @FormParam("titulo") String titulo,
                        @FormParam("contenido") String contenido)
    {   
        log.info("Test validacion de token");
        Session sesion;
        try {
            sesion = HibernateUtil2.getSessionFactory().openSession();
            Query query = sesion.createQuery("FROM Usuarios where usrName= :name AND token = :token");
            query.setParameter("name", usr);
            query.setParameter("token", token);

            log.info(query.getQueryString());
            List res = query.list();
            if (!res.isEmpty()) {
                log.info("Usuario Encontrado");
                Usuarios u = (Usuarios) res.get(0);
                
                Anuncios a=new Anuncios();
                a.setSeccion(1); // Por el momento esa seria la tercera dimension
                a.setPiso(y);
                a.setBloque(x);
                a.setContenido(contenido);
                Date hoy=new Date(System.currentTimeMillis());
                Date limite=new Date(hoy.getYear(),hoy.getMonth(),hoy.getDate()+7);
                a.setFechaPublicacion(hoy);
                a.setFechaExpiracion(limite);
                a.setTitulo(titulo);
                a.setUsuarios(u);
                
                sesion.save(a);
                Notificacion m = new Notificacion();
                m.setTipo(Tipo.Exito);
                m.setNotificacion("Mensaje Agregado");
                sesion.close();

                return Response.ok(m).build();
            } else {
                log.info("Usuario no encontrado");
                Notificacion m = new Notificacion();
                m.setTipo(Tipo.Fallo);
                m.setNotificacion("Esa pareja Token/usuario no es valida");

                return Response.ok(m).build();
            }
        } catch (Exception e) {
            log.severe("Algo salio mal");
            log.severe(e.getMessage());
            log.severe("--------------------------------------------------------");

            Notificacion n = new Notificacion();
            n.setNotificacion("El servicio NO esta disponible<br> revise el status del Servicio<br>");
            n.setTipo(Tipo.Fatal);
            return Response.ok(n).build();
        }
        
    }

    @Path("/izq")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response izquierda(@FormParam("usr") String usr,
                        @FormParam("token") String token)
    {   
        log.info("Moviendose");
        Session sesion;
        try {
            sesion = HibernateUtil2.getSessionFactory().openSession();
            Query query = sesion.createQuery("FROM Usuarios where usrName= :name AND token = :token");
            query.setParameter("name", usr);
            query.setParameter("token", token);

            log.info(query.getQueryString());
            List res = query.list();
            if (!res.isEmpty()) {
                log.info("Usuario Encontrado");
                Usuarios u = (Usuarios) res.get(0);
                
                Casilla actual=buscarCasillaUsuario(u);
                Pasillo pactual=buscarPasillo(u);
                if(pactual == null)
                {
                    log.info("colocar al usuario por inexistencia");
                    Torre3.addUsr(0, 0, u.getId());
                    
                    return Response.ok(mundo.getPasillos().get(0)).build();
                }
                else
                {
                    int y=mundo.getPasillos().indexOf(pactual);
                    int x=pactual.getCasillas().indexOf(actual);
                    avatarJson aj=Torre3.getUsr(u.getId());
                    actual.getUsuarios().remove(aj);
                    //Izquierda
                    Torre3.addUsr(y, x-1, u.getId());
                    
                    Pasillo pas=mundo.getPasillos().get(y);
                    return Response.ok(pas).build();
                }
            } else {
                log.info("Usuario no encontrado");
                Notificacion m = new Notificacion();
                m.setTipo(Tipo.Fallo);
                m.setNotificacion("Esa pareja Token/usuario no es valida");

                return Response.ok(m).build();
            }
        } catch (Exception e) {
            log.severe("Algo salio mal");
            log.severe(e.getMessage());
            log.severe("--------------------------------------------------------");

            Notificacion n = new Notificacion();
            n.setNotificacion("El servicio NO esta disponible<br> revise el status del Servicio<br>");
            n.setTipo(Tipo.Fatal);
            return Response.ok(n).build();
        }    
    }

    private Casilla buscarCasillaUsuario(Usuarios u) {
        for (Pasillo pasillo : mundo.getPasillos()) {
            for (Casilla c: pasillo.getCasillas()) {
                for (avatarJson aj : c.getUsuarios()) {
                    if(aj.getId()==u.getId())
                        return c;
                }
            }
        }
        
        return null;
    }

    private Pasillo buscarPasillo(Usuarios u) {
        for (Pasillo pasillo : mundo.getPasillos()) {
            for (Casilla c: pasillo.getCasillas()) {
                for (avatarJson aj : c.getUsuarios()) {
                    if(aj.getId()==u.getId())
                        return pasillo;
                }
            }
        }
        
        return null;
    }
    
    @Path("/der")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response derecha(@FormParam("usr") String usr,
                        @FormParam("token") String token)
    {   
        log.info("Moviendose");
        Session sesion;
        try {
            sesion = HibernateUtil2.getSessionFactory().openSession();
            Query query = sesion.createQuery("FROM Usuarios where usrName= :name AND token = :token");
            query.setParameter("name", usr);
            query.setParameter("token", token);

            log.info(query.getQueryString());
            List res = query.list();
            if (!res.isEmpty()) {
                log.info("Usuario Encontrado");
                Usuarios u = (Usuarios) res.get(0);
                
                Casilla actual=buscarCasillaUsuario(u);
                Pasillo pactual=buscarPasillo(u);
                if(pactual == null)
                {
                    log.info("colocar al usuario por inexistencia");
                    Torre3.addUsr(0, 0, u.getId());
                    
                    return Response.ok(mundo.getPasillos().get(0)).build();
                }
                else
                {
                    int y=mundo.getPasillos().indexOf(pactual);
                    int x=pactual.getCasillas().indexOf(actual);
                    avatarJson aj=Torre3.getUsr(u.getId());
                    actual.getUsuarios().remove(aj);
                    //Izquierda
                    Torre3.addUsr(y, x+1, u.getId());
                    
                    Pasillo pas=mundo.getPasillos().get(y);
                    return Response.ok(pas).build();
                }
            } else {
                log.info("Usuario no encontrado");
                Notificacion m = new Notificacion();
                m.setTipo(Tipo.Fallo);
                m.setNotificacion("Esa pareja Token/usuario no es valida");

                return Response.ok(m).build();
            }
        } catch (Exception e) {
            log.severe("Algo salio mal");
            log.severe(e.getMessage());
            log.severe("--------------------------------------------------------");

            Notificacion n = new Notificacion();
            n.setNotificacion("El servicio NO esta disponible<br> revise el status del Servicio<br>");
            n.setTipo(Tipo.Fatal);
            return Response.ok(n).build();
        }    
    }
    
    @Path("/arr")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response arriba(@FormParam("usr") String usr,
                        @FormParam("token") String token)
    {   
        log.info("Moviendose");
        Session sesion;
        try {
            sesion = HibernateUtil2.getSessionFactory().openSession();
            Query query = sesion.createQuery("FROM Usuarios where usrName= :name AND token = :token");
            query.setParameter("name", usr);
            query.setParameter("token", token);

            log.info(query.getQueryString());
            List res = query.list();
            if (!res.isEmpty()) {
                log.info("Usuario Encontrado");
                Usuarios u = (Usuarios) res.get(0);
                
                Casilla actual=buscarCasillaUsuario(u);
                Pasillo pactual=buscarPasillo(u);
                if(pactual == null)
                {
                    log.info("colocar al usuario por inexistencia");
                    Torre3.addUsr(0, 0, u.getId());
                    
                    return Response.ok(mundo.getPasillos().get(0)).build();
                }
                else
                {
                    int y=mundo.getPasillos().indexOf(pactual);
                    int x=pactual.getCasillas().indexOf(actual);
                    avatarJson aj=Torre3.getUsr(u.getId());
                    actual.getUsuarios().remove(aj);
                    //Izquierda
                    Torre3.addUsr(y+1,x, u.getId());
                    
                    Pasillo pas=mundo.getPasillos().get(y+1);
                    return Response.ok(pas).build();
                }
            } else {
                log.info("Usuario no encontrado");
                Notificacion m = new Notificacion();
                m.setTipo(Tipo.Fallo);
                m.setNotificacion("Esa pareja Token/usuario no es valida");

                return Response.ok(m).build();
            }
        } catch (Exception e) {
            log.severe("Algo salio mal");
            log.severe(e.getMessage());
            log.severe("--------------------------------------------------------");

            Notificacion n = new Notificacion();
            n.setNotificacion("El servicio NO esta disponible<br> revise el status del Servicio<br>");
            n.setTipo(Tipo.Fatal);
            return Response.ok(n).build();
        }    
    }
    
    @Path("/aba")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response abajo(@FormParam("usr") String usr,
                        @FormParam("token") String token)
    {   
        log.info("Moviendose");
        Session sesion;
        try {
            sesion = HibernateUtil2.getSessionFactory().openSession();
            Query query = sesion.createQuery("FROM Usuarios where usrName= :name AND token = :token");
            query.setParameter("name", usr);
            query.setParameter("token", token);

            log.info(query.getQueryString());
            List res = query.list();
            if (!res.isEmpty()) {
                log.info("Usuario Encontrado");
                Usuarios u = (Usuarios) res.get(0);
                
                Casilla actual=buscarCasillaUsuario(u);
                Pasillo pactual=buscarPasillo(u);
                if(pactual == null)
                {
                    log.info("colocar al usuario por inexistencia");
                    Torre3.addUsr(0, 0, u.getId());
                    
                    return Response.ok(mundo.getPasillos().get(0)).build();
                }
                else
                {
                    int y=mundo.getPasillos().indexOf(pactual);
                    int x=pactual.getCasillas().indexOf(actual);
                    avatarJson aj=Torre3.getUsr(u.getId());
                    actual.getUsuarios().remove(aj);
                    
                    Torre3.addUsr(y-1, x, u.getId());
                    
                    Pasillo pas=mundo.getPasillos().get(y-1);
                    return Response.ok(pas).build();
                }
            } else {
                log.info("Usuario no encontrado");
                Notificacion m = new Notificacion();
                m.setTipo(Tipo.Fallo);
                m.setNotificacion("Esa pareja Token/usuario no es valida");

                return Response.ok(m).build();
            }
        } catch (Exception e) {
            log.severe("Algo salio mal");
            log.severe(e.getMessage());
            log.severe("--------------------------------------------------------");

            Notificacion n = new Notificacion();
            n.setNotificacion("El servicio NO esta disponible<br> revise el status del Servicio<br>");
            n.setTipo(Tipo.Fatal);
            return Response.ok(n).build();
        }    
    }
}
