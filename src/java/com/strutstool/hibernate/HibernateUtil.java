package com.strutstool.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.JDBCConnectionException;

/**
 * Classe de utilidades com o Hibernate
 *
 * @author maycon
 */
public class HibernateUtil {

    private static SessionFactory factory;

    static {
        try {
            Configuration conf = new Configuration();
            conf.configure();

            factory = conf.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Cria uma sessão com o Hibernate
     * @return
     * @throws HibernateException
     */
    public static Session getSession() throws HibernateException {
        Session session = factory.openSession();
        return session;
    }

    /**
     * Tenta efetuar conexão com banco de dados
     * @throws JDBCConnectionException Lançada em caso de erro na conexão
     */
    public static void tryConnection() throws JDBCConnectionException {
        try {
            Transaction t = (Transaction) getSession().beginTransaction();
        } catch (JDBCConnectionException ex) {
            throw ex;
        }
    }
}