package model;

import javax.persistence.*;

@Entity
@Table(name= "produit")
public class Produit {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "code",nullable = false, unique = true)
    private String code;

    @Column(name = "libelle", nullable = false)
    private String libelle;

    @Column(name = "type", nullable =false, columnDefinition = "VARCHAR(50)")
    private String type;

    @Column(name= "quantite_stock",nullable=false)
    private int quantiteStock;

    @Column(name = "disponibilite", nullable=false)
    private boolean disponibilite;

    public Produit() {}

    public Produit(String code, String libelle, String type, int quantiteStock, boolean disponibilite) {
        this.code = code;
        this.libelle = libelle;
        this.type = type;
        this.quantiteStock = quantiteStock;
        this.disponibilite = disponibilite;
    }


    public int getId() { 
        return id; 
    }
    
    public void setId(int id) { 
        this.id = id; 
    }

    public String getCode(){ 
        return code; 
    }
    
    public void setCode(String code) {
        this.code = code; 
    }

    public String getLibelle() {
        return libelle; 
    }
    public void setLibelle(String libelle) { 
        this.libelle = libelle; 
    }

    public String getType(){ 
        return type; 
    }
    
    public void setType(String type) { 
        this.type = type; 
    }

    public int getQuantiteStock() {
        return quantiteStock; 
    }
    
    public void setQuantiteStock(int quantiteStock) { 
        this.quantiteStock = quantiteStock; 
    }

    public boolean isDisponibilite() {
        return disponibilite; 
    }
    
    public void setDisponibilite(boolean disponibilite){
        this.disponibilite = disponibilite; 
    }

}
