package Classi;

public abstract class Catalogo {
    private int codiceISBN;
    private String titolo;
    private int annoPubblicazione;

    private int numeroPagine;

    public Catalogo(int codice, String titolo, int annoPubblicazione, int numeroPagine ){
        this.codiceISBN = codice;
        this.titolo = titolo;
        this.annoPubblicazione = annoPubblicazione;
        this.numeroPagine = numeroPagine;setTitolo( titolo );
        this.setAnnoPubblicazione( annoPubblicazione);
        this.setNumeroPagine( numeroPagine );
    }

    public Catalogo(String titolo, int annoPubblicazione, int numeroPagine ){
        this.titolo = titolo;
        this.annoPubblicazione = annoPubblicazione;
        this.numeroPagine = numeroPagine;setTitolo( titolo );
        this.setAnnoPubblicazione( annoPubblicazione);
        this.setNumeroPagine( numeroPagine );
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
