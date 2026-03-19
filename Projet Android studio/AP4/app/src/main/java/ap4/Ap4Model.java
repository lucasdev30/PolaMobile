package ap4;

import java.util.Date;

public class Ap4Model {
    public int idArtiste;
    public String nomArtiste;
    public Date dateConcert; //
    public double prixPlace;
    public int imgArtiste;

    public Ap4Model() {
    }
    public int getIdArtiste() { return idArtiste; }
    public void setIdArtiste(int idArtiste) { this.idArtiste = idArtiste; }


    public String getNomArtiste() { return nomArtiste; }
    public void setNomArtiste(String nomArtiste) { this.nomArtiste = nomArtiste; }


    public Date getDateConcert() { return dateConcert; }
    public void setDateConcert(Date dateConcert) { this.dateConcert = dateConcert; }


    public double getPrixPlace() { return prixPlace; }
    public void setPrixPlace(double prixPlace) { this.prixPlace = prixPlace; }


    public int getImgArtiste() { return imgArtiste; }
    public void setImgArtiste(int imgArtiste) { this.imgArtiste = imgArtiste; }


}

