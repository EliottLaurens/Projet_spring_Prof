package fr.limayrac.ProjetSpring.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Declaration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String refDossier;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.S")
    private Date dateCreation;
    private String status;

    // Ajout des détails de la formation
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateFormation;
    private String lieuFormation;
    private String intituleFormation;

    private String transportType;

    // Ajout des champs pour les frais d'hébergement
    private Boolean hebergementGratuit;


    // Ajout des champs pour les frais de restauration
    private Boolean fraisRestauration;
    private Double montantRestauration; // Si vous souhaitez capturer le montant

    // Ajout des champs pour les coordonnées bancaires
    private String titulaireCompte;
    private String iban;
    private String bic;

    public Long getId(){
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRefDossier(String refDossier) {
        this.refDossier = refDossier;
    }

    public String getRefDossier() {
        return this.refDossier;
    }
    public void setDateCreation(Date date) {
        this.dateCreation = date;
    }

    public Date getDateCreation() {
        return this.dateCreation;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public Date getDateFormation() {
        return dateFormation;
    }

    public void setDateFormation(Date dateFormation) {
        this.dateFormation = dateFormation;
    }

    public String getLieuFormation() {
        return lieuFormation;
    }

    public void setLieuFormation(String lieuFormation) {
        this.lieuFormation = lieuFormation;
    }

    public String getIntituleFormation() {
        return intituleFormation;
    }

    public void setIntituleFormation(String intituleFormation) {
        this.intituleFormation = intituleFormation;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public Boolean isHebergementGratuit() {
        return hebergementGratuit;
    }

    public void setHebergementGratuit(Boolean hebergementGratuit) {
        this.hebergementGratuit = hebergementGratuit;
    }

    public Boolean isFraisRestauration() {
        return fraisRestauration;
    }

    public void setFraisRestauration(Boolean fraisRestauration) {
        this.fraisRestauration = fraisRestauration;
    }

    public Double getMontantRestauration() {
        return montantRestauration;
    }

    public void setMontantRestauration(Double montantRestauration) {
        this.montantRestauration = montantRestauration;
    }

    public String getTitulaireCompte() {
        return titulaireCompte;
    }

    public void setTitulaireCompte(String titulaireCompte) {
        this.titulaireCompte = titulaireCompte;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }
}
