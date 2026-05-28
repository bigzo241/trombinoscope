package com.mamadou.trombinoscope.dataAccess;

import java.sql.Connection;
import java.util.List;

public abstract class DAO<T> {
    public Connection connect = ConnectionPostgresSQL.getInstance();

    /**
     * Permet de récupérer un objet via son ID
     * @param id
     * @return T l'objet démandé
     */
    public abstract T find(long id);

    /**
     * Permet de récupérer tous les objets
     * @return une liste d'objets
     */
    public abstract List<T> findAll();

    /**
     * Permet de créer une entrée dans la base de données
     * par rapport à un objet
     * @param obj
     */
    public abstract void create(T obj);

    /**
     * Permet de mettre à jour les données d'une entrée dans la base
     * @param obj
     */
    public abstract T update(T obj);

    /**
     * Permet la suppression d'une entrée de la base
     * @param obj
     */
    public abstract void delete(T obj);
}
