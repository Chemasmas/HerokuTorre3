package com.nebulahelix.torre3.service;

import com.nebulahelix.torre3.entity.json.Casilla;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author Chemasmas
 */

@Path("/torre")
@Singleton
public class Torre3 {
    
    public static Mundo mundo=new Mundo();
    
    static final Logger log=Logger.getLogger("Mundo");
    
    public static void addUsr(int x,int y, int z,long id)
    {
        mundo.getMundo().get(x).segmento.get(y).pasillo.get(z).getUsuarios().add(id);
    }
    
    
    @Path("/")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response mundo()
    {
        return Response.ok(mundo.toString()).build();
    }
    
    @Path("/{x}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response mundo(@PathParam("x") int x)
    {
        return Response.ok(mundo.getMundo().get(x)).build();
    }
}

@XmlRootElement
class Pasillo
{
    public List<Casilla> getPasillo() {
        if(pasillo==null)
            pasillo= new ArrayList<Casilla>(10);
        return pasillo;
    }

    public void setPasillo(List<Casilla> pasillo) {
        this.pasillo = pasillo;
    }
    List<Casilla> pasillo;
    
    @Override
    public String toString()
    {
        StringBuilder sb=new StringBuilder();
        
        for (Casilla casilla : pasillo) {
            sb.append("{");
            sb.append(casilla);
            sb.append("}");
        }
        
        return sb.toString();
    }
}

@XmlRootElement
class Segmento
{
    List<Pasillo> segmento;

    public List<Pasillo> getSegmento() {
        if(segmento==null)
            segmento=new ArrayList<Pasillo>(7);
        return segmento;
    }

    public void setSegmento(List<Pasillo> segmento) {
        this.segmento = segmento;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb=new StringBuilder();
        
        for (Pasillo pasillo : segmento) {
            sb.append("{");
            sb.append(pasillo);
            sb.append("}");
        }
        
        return sb.toString();
    }
}

@XmlRootElement
class Mundo
{

    List<Segmento> mundo;
    
    public List<Segmento> getMundo() {
        if(mundo==null)
            mundo=new ArrayList<Segmento>(3);
        return mundo;
    }

    public void setMundo(List<Segmento> mundo) {
        this.mundo = mundo;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb=new StringBuilder();
        
        for (Segmento segmento : mundo) {
            sb.append("{");
            sb.append(segmento);
            sb.append("}");
        }
        
        return sb.toString();
    }
}
