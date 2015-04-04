package com.nebulahelix.torre3.service;

import com.nebulahelix.torre3.entity.Anuncios;
import com.nebulahelix.torre3.entity.Avatar;
import com.nebulahelix.torre3.entity.json.Casilla;
import com.nebulahelix.torre3.entity.json.Mensaje;
import com.nebulahelix.torre3.entity.json.Notificacion;
import com.nebulahelix.torre3.entity.json.Pasillo;
import com.nebulahelix.torre3.entity.json.Segmento;
import com.nebulahelix.torre3.entity.json.Tipo;
import com.nebulahelix.torre3.entity.json.avatarJson;
import com.nebulahelix.torre3.util.HibernateUtil2;
import java.util.List;
import java.util.logging.Logger;
import javax.inject.Singleton;
import javax.ws.rs.GET;
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
    
}
