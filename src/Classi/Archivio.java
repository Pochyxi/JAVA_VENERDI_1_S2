package Classi;

import InterfacesAndEnums.Genere;
import InterfacesAndEnums.Periodicita;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Archivio {
    String databaseString;
    ArrayList<Catalogo> database;

    public Archivio( ) {
        try {
            this.database = this.leggi();
        } catch( IOException e ) {
            throw new RuntimeException( e );
        }
    }

    private Genere determinaGenere( String str ) {
        return switch (str) {
            case "FANTASY" -> Genere.FANTASY;
            case "COMMEDIA" -> Genere.COMMEDIA;
            case "HORROR" -> Genere.HORROR;
            case "AZIONE" -> Genere.AZIONE;
            case "ROMANZI" -> Genere.ROMANZI;
            default -> Genere.FANTASY;
        };
    }

    private Periodicita determinaPeriodicita( String str ) {
        return switch (str) {
            case "SETTIMANALE" -> Periodicita.SETTIMANALE;
            case "MENSILE" -> Periodicita.MENSILE;
            case "SEMESTRALE" -> Periodicita.SEMESTRALE;
            default -> Periodicita.SETTIMANALE;
        };
    }

    public void getDatabases() {

        for( Catalogo catalogo : database ) {
            System.out.println( catalogo );
        }
        System.out.println("Ci sono " + database.size() + " elementi nel catalogo");
    }

    public Catalogo filtraPerISBN( int codiceIsbn ) {

        for( Catalogo catalogo : database ) {
            if( catalogo.getCodiceISBN() == codiceIsbn ) {
                return catalogo;
            }
        }
        return null;
    }

    public List<Catalogo> filtraPerAnno( int anno ) {

        List<Catalogo> lista = new ArrayList<>();

        for( Catalogo catalogo : database ) {
            if( catalogo.getAnnoPubblicazione() == anno ) {
                lista.add( catalogo );
            }
        }
        return lista;
    }

    public List<Libro> filtraPerAutore( String autore ) {

        List<Libro> lista = new ArrayList<>();
        List<Libro> listaFiltrata = new ArrayList<>();
        this.database.stream()
                .filter( e -> e instanceof Libro )
                .forEach( e -> lista.add((Libro)e) );

        for( Libro libro : lista ) {
            if( libro.getAutore().equals( autore ) ) {
                listaFiltrata.add( libro );
            }
        }
        return listaFiltrata;
    }

    public int determinaISBN() {
        try {
            if( this.leggi().size() == 0 ) { return 1; }
           return this.leggi().get( leggi().size() - 1 ).getCodiceISBN();
        } catch( IOException e ) {
            throw new RuntimeException( e );
        }
    }

    public void scrivi( Catalogo item ) {
        File database = new File( "docs/database.txt" );
        String encoding = "UTF-8";

        if( item instanceof Libro ) {
            String oggettoStringato =
                    "" + (this.determinaISBN() + 1)
                            + "-" + item.getTitolo()
                            + "-" + item.getAnnoPubblicazione()
                            + "-" + item.getNumeroPagine()
                            + "-" + (( Libro ) item).getAutore()
                            + "-" + (( Libro ) item).getGenere()
                            + "-" + "LIBRO"
                            + "/";

            try {
                FileUtils.writeStringToFile( database, oggettoStringato, encoding, true );
                this.database = this.leggi();
            } catch( IOException e ) {
                throw new RuntimeException( e );
            }

        } else {
            String oggettoStringato =
                    "" + this.determinaISBN() + 1
                            + "-" + item.getTitolo()
                            + "-" + item.getAnnoPubblicazione()
                            + "-" + item.getNumeroPagine()
                            + "-" + (( Rivista ) item).getPeriodicita()
                            + "-" + "RIVISTA"
                            + "/";

            try {
                FileUtils.writeStringToFile( database, oggettoStringato, encoding, true );
                this.database = this.leggi();
            } catch( IOException e ) {
                throw new RuntimeException( e );
            }

        }
    }

    public ArrayList<Catalogo> leggi() throws IOException {

        File database = new File( "docs/database.txt" );
        String encoding = "UTF-8";
        ArrayList<Catalogo> catalogoList = new ArrayList<>();

        if( database.exists() ) {
            try {
                // creo il file
                this.databaseString = FileUtils.readFileToString( database, encoding );

                String[] arrOfItems = this.databaseString.split( "/" );

                for( String arrOfItem : arrOfItems ) {
                    String[] stringaSplit = arrOfItem.split( "-" );

                    for( int j = 0 ; j < stringaSplit.length ; j++ ) {

                        if( stringaSplit.length == 7 ) {
                            Catalogo obj = new Libro(
                                    Integer.parseInt( stringaSplit[ 0 ] ), stringaSplit[ 1 ],
                                    Integer.parseInt( stringaSplit[ 2 ] ),
                                    Integer.parseInt( stringaSplit[ 3 ] ),
                                    stringaSplit[ 4 ],
                                    determinaGenere( stringaSplit[ 5 ] ) );
                            if( j == stringaSplit.length - 1 ) {
                                catalogoList.add( obj );
                            }
                        } else if( stringaSplit.length == 6 ) {
                            Catalogo obj = new Rivista( Integer.parseInt( stringaSplit[ 0 ] ), stringaSplit[ 1 ],
                                    Integer.parseInt( stringaSplit[ 2 ] ),
                                    Integer.parseInt( stringaSplit[ 3 ] )
                                    , determinaPeriodicita( stringaSplit[ 4 ] ) );
                            if( j == stringaSplit.length - 1 ) {
                                catalogoList.add( obj );
                            }
                        }
                    }
                }
            } catch( IOException e ) {
                System.out.println( "Qualcosa è andato storto nel recupero delle risorse" );
            }
        } else {
            String text = "";
            System.out.println( "il file  non esiste!" );
            FileUtils.writeStringToFile( database, text, encoding );
        }
        return catalogoList;
    }

    public void rimuovi(int isbn) {

        try {
            this.database = leggi();
        } catch( IOException e ) {
            throw new RuntimeException( e );
        }

        ArrayList<Catalogo> arraySenzaElemento = new ArrayList<>();

        boolean controllo = false;

        for( Catalogo item : this.database ) {
            if( item.getCodiceISBN() == isbn ) {
                controllo = true;
                break;
            }
        }

        System.out.println(controllo);

        if( controllo ) {
            for( Catalogo value : this.database ) {
                if( value.getCodiceISBN() != isbn ) {
                    arraySenzaElemento.add( value );
                }
            }

            String text = "";
            File database = new File( "docs/database.txt" );
            String encoding = "UTF-8";

            try {
                FileUtils.writeStringToFile( database, text, encoding );
            } catch( IOException e ) {
                throw new RuntimeException( e );
            }

            for( Catalogo catalogo : arraySenzaElemento ) {
                this.scrivi( catalogo );
            }

            try {
                this.database = leggi();
            } catch( IOException e ) {
                throw new RuntimeException( e );
            }

        } else {
            System.out.println( "L'elemento non esiste" );
        }

    }
}
