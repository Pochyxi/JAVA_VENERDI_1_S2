package Classi;

import InterfacesAndEnums.Genere;
import InterfacesAndEnums.Periodicita;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


public class Archivio {
    String databaseString;
    ArrayList<Catalogo> database;

    public Archivio() {
        try {
            this.leggi();
        } catch( IOException e ) {
            System.out.println( "Qualcosa è andato storto" );
        }

    }

    private Genere determinaGenere( String str ) {
        switch (str) {
            case "FANTASY":
                return Genere.FANTASY;
            case "COMMEDIA":
                return Genere.COMMEDIA;
            case "HORROR":
                return Genere.HORROR;
            case "AZIONE":
                return Genere.AZIONE;
            case "ROMANZI":
                return Genere.ROMANZI;
            default:
                return Genere.FANTASY;
        }
    }

    private Periodicita determinaPeriodicita( String str ) {
        switch (str) {
            case "SETTIMANALE":
                return Periodicita.SETTIMANALE;
            case "MENSILE":
                return Periodicita.MENSILE;
            case "SEMESTRALE":
                return Periodicita.SEMESTRALE;
            default:
                return Periodicita.SETTIMANALE;
        }
    }

    public void getDatabases() {

        for( int i = 0; i < database.size(); i++ ) {
            System.out.println(database.get(i));
        }
        System.out.println("Ci sono " + database.size() + " elementi nel catalogo");
    }

    // questo metodo dovrà ritornare un oggetto di tipo Libro/Rivista dalla lista del catalogo tramite isbn
    public Catalogo filtraPerISBN( int codiceIsbn ) {

        for( int i = 0; i < database.size(); i++ ) {
            if( database.get(i).getCodiceISBN() == codiceIsbn ) {
                return database.get(i);
            }
        }
        return null;
    }

    // questo metodo dovrà ritornare una lista di oggetti di tipo Catalogo filtrati per anno
    public List<Catalogo> filtraPerAnno( int anno ) {

        List<Catalogo> lista = new ArrayList<Catalogo>();

        for( int i = 0; i < database.size(); i++ ) {
            if( database.get(i).getAnnoPubblicazione() == anno ) {
               lista.add(database.get(i));
            }
        }
        return lista;
    }

    // questo metodo dovrà ritornare una lista di oggetti di tipo Catalogo filtrati per autore
    public List<Libro> filtraPerAutore( String autore ) {

        List<Libro> lista = new ArrayList<>();
        List<Libro> listaFiltrata = new ArrayList<>();
        this.database.stream()
                .filter( e -> e instanceof Libro )
                .forEach( e -> lista.add((Libro)e) );

        for( int i = 0; i < lista.size(); i++ ) {
            if( lista.get(i).getAutore().equals( autore ) ) {
                listaFiltrata.add(lista.get(i));
            }
        }
        return listaFiltrata;
    }

    public void scrivi( Catalogo item ) {
        File database = new File( "docs/database.txt" );
        String encoding = "UTF-8";

        if( item instanceof Libro ) {
            String oggettoStringato =
                    " " + item.getTitolo()
                            + " " + item.getAnnoPubblicazione()
                            + " " + item.getNumeroPagine()
                            + " " + (( Libro ) item).getAutore()
                            + " " + (( Libro ) item).getGenere()
                            + " " + "LIBRO"
                            + "/";

            try {
                FileUtils.writeStringToFile( database, oggettoStringato, encoding, true );
            } catch( IOException e ) {
                throw new RuntimeException( e );
            }

        } else {
            String oggettoStringato =
                    " " + item.getTitolo()
                            + " " + item.getAnnoPubblicazione()
                            + " " + item.getNumeroPagine()
                            + " " + (( Rivista ) item).getPeriodicita()
                            + " " + "RIVISTA"
                            + "/";

            try {
                FileUtils.writeStringToFile( database, oggettoStringato, encoding, true );
            } catch( IOException e ) {
                throw new RuntimeException( e );
            }

        }
    }

    public void leggi() throws IOException {

        File database = new File( "docs/database.txt" );
        String encoding = "UTF-8";


        if( database.exists() ) {
            try {
                // creo il file
                String dataContent = FileUtils.readFileToString( database, encoding );
                System.out.println( "il contenuto del file è " + dataContent );
                this.databaseString = dataContent;

                String[] arr = this.databaseString.split( "/" );



                ArrayList<Catalogo> catalogoList = new ArrayList<>();

                for( int i = 0 ; i < arr.length ; i++ ) {
                    String[] stringaSplit = arr[ i ].split( " " );


                    for( int j = 0 ; j < stringaSplit.length ; j++ ) {

                        if( stringaSplit.length == 7 ) {
                            Catalogo obj = new Libro( stringaSplit[ 1 ], Integer.parseInt( stringaSplit[ 2 ] ),
                                    Integer.parseInt( stringaSplit[ 3 ] ),
                                    stringaSplit[ 4 ], determinaGenere( stringaSplit[5] ) );
                            if (j == stringaSplit.length - 1) {
                                catalogoList.add( obj );
                            }
                        } else if( stringaSplit.length == 6 ) {
                            Catalogo obj = new Rivista( stringaSplit[ 1 ], Integer.parseInt( stringaSplit[ 2 ] ),
                                    Integer.parseInt( stringaSplit[ 3 ] )
                                    , determinaPeriodicita( stringaSplit[4] ) );
                            if (j == stringaSplit.length - 1) {
                                catalogoList.add( obj );
                            }
                        }
                    }
                    this.database = catalogoList;
                }


            } catch( IOException e ) {
                System.out.println( "Qualcosa è andato storto nel recupero delle risorse" );
            }
        } else {
            String text = "";
            System.out.println( "il file  non esiste!" );
            FileUtils.writeStringToFile( database, text, encoding );
        }
    }

    public void rimuovi(int isbn) {
        ArrayList<Catalogo> arrLess = new ArrayList<>();
        boolean controllo = false;

        for( int i = 0 ; i < this.database.size() ; i++ ) {
            if( this.database.get( i ).getCodiceISBN() == isbn ) {
                controllo = true;
            }
        }

        if( controllo ) {
            for ( int i = 0; i < this.database.size(); i++ ) {
                if ( this.database.get( i ).getCodiceISBN() != isbn ) {
                    arrLess.add( this.database.get( i ));
                }
            }

            System.out.println(arrLess);

            String text = "";
            File database = new File( "docs/database.txt" );
            String encoding = "UTF-8";

            try {
                FileUtils.writeStringToFile( database, text, encoding );
            } catch( IOException e ) {
                throw new RuntimeException( e );
            }

            for ( int i = 0; i < arrLess.size(); i++ ) {
                this.scrivi( arrLess.get( i ) );
            }

        } else {
            System.out.println( "L'elemento non esiste" );
        }



    }
}
