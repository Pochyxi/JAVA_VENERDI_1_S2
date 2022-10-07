package Classi;

import InterfacesAndEnums.Periodicita;

public class Rivista extends Catalogo {

    Periodicita periodicita;

    public Rivista( String titolo, int annoPubblicazione, int numeroPagine, Periodicita periodicita  ) {

        super( titolo, annoPubblicazione, numeroPagine );
        this.periodicita = periodicita;

    }

    @Override
    public String toString() {
        return "ISBN: " + this.getCodiceISBN()
                + " - " + " Titolo: " + this.getTitolo()
                + " - " + " Anno: " + this.getAnnoPubblicazione()
                + " - " + " Pagine: " + this.getNumeroPagine();
    }

    // GETTERS AND SETTERS
    public Periodicita getPeriodicita() {
        return periodicita;
    }

    public void setPeriodicita( Periodicita periodicita ) {
        this.periodicita = periodicita;
    }
}
