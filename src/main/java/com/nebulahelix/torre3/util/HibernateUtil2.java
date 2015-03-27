package com.nebulahelix.torre3.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Chemasmas
 */
public class HibernateUtil2 {
    private static final SessionFactory sessionFactory=buildSession();
    
    private static SessionFactory buildSession()
    {
            return new Configuration().configure().buildSessionFactory();
    }
    
    public static SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }
    
    public static void shutdown()
    {
        getSessionFactory().close();
    }
    
    /*
    public static void main(String[] args) {
        System.out.println("Prueba Hibernate");
        Session sesion=HibernateUtil.getSessionFactory().openSession();
        
        sesion.beginTransaction();
        Usuarios usuario=new Usuarios();
        usuario.setAnuncios(null);
        usuario.setAvatar(null);
        usuario.setCarrera("Ingeniera en Computacion");
        usuario.setMatricula("2123065656");
        usuario.setNombreCompleto("Sierra Florido Jorge Humberto");
        usuario.setTelCelular("55555555");
        usuario.setUsrName("Chemasmas");
        usuario.setUsrPass(Encriptado.SHA256("dios00Gaia"));
        usuario.setToken("Chemasmas");
        
        sesion.save(usuario);
        
        sesion.getTransaction().commit();
        
        System.out.println(Encriptado.SHA256("dios00Gaia"));
        System.out.println(Encriptado.SHA256("dios00Gaia").length());
        HibernateUtil.shutdown();
    }
    */
}
