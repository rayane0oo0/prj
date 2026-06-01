package service;

import model.Produit;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class ProduitService {

    private static final ProduitService instance = new ProduitService();

    private ProduitService() {}

    public static ProduitService getInstance() {
        return instance;
    }

    public boolean ajouterProduit(Produit produit) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        
        try {
            session.save(produit);
            tx.commit();
            return true;
        } catch (Exception e) {
            tx.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean modifierProduit(Produit produit) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        
        try {
            session.update(produit);
            tx.commit();
            return true;
        } catch (Exception e) {
            tx.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean supprimerProduit(Produit produit) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        try {
            session.delete(produit);
            tx.commit();
            return true;
        } catch (Exception e) {
            tx.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean supprimerProduitParId(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        try {
            Produit p = new Produit();
            p.setId(id);
            session.delete(p);
            tx.commit();
            return true;
        } catch (Exception e) {
            tx.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    public List<Produit> getAllProduits() {
        HibernateUtil.resetSessionFactory();
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        try {
            return session.createSQLQuery("SELECT * FROM produit ORDER BY id")
                          .addEntity(Produit.class)
                          .list();
        } catch (Exception e) {
            return new ArrayList<>();
        } finally {
            session.close();
        }
    }

    public List<Produit> rechercher(String mot) {
        if (mot == null || mot.trim().isEmpty()) {
            return getAllProduits();
        }
        
        HibernateUtil.resetSessionFactory();
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        try {
            String sql = "SELECT * FROM produit WHERE LOWER(libelle) LIKE :mot OR LOWER(type) LIKE :mot";
            
            return session.createSQLQuery(sql).addEntity(Produit.class)
                          .setParameter("mot", "%" + mot.toLowerCase() + "%").list();
        } catch (Exception e) {
            return new ArrayList<>();
        } finally {
            session.close();
        }
    }

    public Produit getProduitById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        try {
            return (Produit) session.get(Produit.class, id);
        } catch (Exception e) {
            return null;
        } finally {
            session.close();
        }
    }
}