package Classi;

import InterfacesAndEnums.Genere;

public class Libro extends Catalogo{
    String autore;
    Genere genere;

    public Libro(int codice, String titolo, int annoPubblicazione, int numeroPagine, String autore, Genere genere  ) {
        super(codice, titolo, annoPubblicazione, numeroPagine );
        this.autore = autore;
        this.genere = genere;
    }

    @Override
    public String toString() {
        return "ISBN: " + this.getCodiceISBN()
                + " - " + " Titolo: " + this.getTitolo()
                + " - " + " Anno: " + this.getAnnoPubblicazione()
                + " - " + " Autore: " + this.getAutore()
                + " - " + " Pagine: " + this.getNumeroPagine();
    }

    // GETTER & SETTER
    public String getAutore() {
        return autore;
    }

    public void setAutore( String autore ) {
        this.autore = autore;
    }

    public Genere getGenere() {
        return genere;
    }

    public void setGenere( Genere genere ) {
        this.genere = genere;
    }
}
