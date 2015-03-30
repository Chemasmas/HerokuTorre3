package com.nebulahelix.torre3.entity.json;

/**
 * Esta Interface sirve para marcar a las entidades que generara la factory
 * MessageEntityFactory
 * 
 * Debes asegurar que todas puedan puedan ser procesadas por MessageBody<T>
 * para evitar una excepcion por no poder ser procesadas por Jersey.
 * 
 * @author Chemasmas
 * @see com.nebulahelix.torre3.entity.json.MessageEntityFactory
 */

public interface MessageEntity {
    
}
