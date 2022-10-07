package Classi;

public abstract class Catalogo {

    private static int counter = 1000;
    private int codiceISBN;
    private String titolo;
    private int annoPubblicazione;

    private int numeroPagine;

    public Catalogo( String titolo, int annoPubblicazione, int numeroPagine ){
        this.setTitolo( titolo );
        this.setAnnoPubblicazione( annoPubblicazione);
        this.setNumeroPagine( numeroPagine );
        setCodiceISBN();
    }


    // SETTERS

    public void setTitolo( String titolo ) {
        this.titolo = titolo;
    }

    public void setAnnoPubblicazione( int annoPubblicazione ) {
        this.annoPubblicazione = annoPubblicazione;
    }

    public void setNumeroPagine( int numeroPagine ) {
        this.numeroPagine = numeroPagine;
    }

    private void setCodiceISBN() {
        this.codiceISBN = this.counter++;
    }

    // GETTERS

    public int getCodiceISBN() {
        return codiceISBN;
    }

    public String getTitolo() {
        return titolo;
    }

    public int getAnnoPubblicazione() {
        return annoPubblicazione;
    }

    public int getNumeroPagine() {
        return numeroPagine;
    }
}
