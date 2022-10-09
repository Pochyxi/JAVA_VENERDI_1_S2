package Classi;

import InterfacesAndEnums.Genere;
import InterfacesAndEnums.Periodicita;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Archivio {
    Scanner scanner = new Scanner(System.in);
    String databaseString; // UNA RAPPRESENTAZIONE STRINGA DEL DATABASE
    ArrayList<Catalogo> database; // IL RISULTATO DEL METODO LEGGI() CHE ELABORA IL DATABASE


    public Archivio() {
        try {
            this.database = this.leggi();
        } catch( IOException e ) {
            throw new RuntimeException( e );
        }
    } // APPENA VIENE ISTANZIATO UN ARCHIVIO IL METODO LEGGI() SETTERA' IL DATABASE

    //////////////////////////////// METODI PRIVATI //////////////////////////////////
    private Genere determinaGenere( String str ) {
        return switch (str) {
            case "FANTASY" -> Genere.FANTASY;
            case "COMMEDIA" -> Genere.COMMEDIA;
            case "HORROR" -> Genere.HORROR;
            case "AZIONE" -> Genere.AZIONE;
            case "ROMANZI" -> Genere.ROMANZI;
            default -> Genere.FANTASY;
        };
    } // MI SERVE PER LA CREAZIONE DELL'ENUM GENERE

    private Periodicita determinaPeriodicita( String str ) {
        return switch (str) {
            case "SETTIMANALE" -> Periodicita.SETTIMANALE;
            case "MENSILE" -> Periodicita.MENSILE;
            case "SEMESTRALE" -> Periodicita.SEMESTRALE;
            default -> Periodicita.SETTIMANALE;
        };
    } // MI SERVE PER LA CREAZIONE DELL'ENUM PERIODICITA'

    private int determinaISBN() {
        try {
            if( this.leggi().size() == 0 ) {
                return 0;
            }
            return this.leggi().get( leggi().size() - 1 ).getCodiceISBN();
        } catch( IOException e ) {
            throw new RuntimeException( e );
        }
    } // DETERMINA IN FASE DI CREAZIONE IL CODICE UNIVOCO DI OGNI ELEMENTO DEL CATALOGO

    //////////////////////////////// METODI ACCESSIBILI //////////////////////////////////
    public void getDatabases() { // RITORNA UN ARRAY DI OGGETTI DI CLASSE CATALOGO COLLEGATO AL DATABASE

        for( Catalogo catalogo : database ) {
            StringBuilder linea = new StringBuilder( "-" );
            for( int i = 0 ; i < catalogo.toString().length() ; i++ ) {
                linea.append( "-" );
            }

            System.out.println( linea );
            System.out.println( catalogo );
            System.out.println( linea );


        }
        System.out.println( "Ci sono " + database.size() + " elementi nel catalogo" );
        System.out.println();
    }

    public List<Catalogo> filtraPerISBN( int codiceIsbn ) { // RITORNA UN OGGETTO DI TIPO CATALOGO CHE HA QUEL ISBN
        return this.database.stream()
                .filter( item -> item.getCodiceISBN() == codiceIsbn )
                .toList();
    }

    public List<Catalogo> filtraPerAnno( int anno ) { // RITORNA UN ARRAY DI OGGETTI DI CLASSE CATALOGO CHE
        return database.stream()                      // CORRISPONDONO ALL'ANNO
                .filter( item -> item.getAnnoPubblicazione() == anno )
                .collect( Collectors.toList() );
    }

    public List<Catalogo> filtraPerAutore( String autore ) {  // RITORNA UN ARRAY DI OGGETTI DI CLASSE CATALOGO CHE
        return database.stream()                              // CORRISPONDONO ALL'AUTORE
                .filter( e -> e instanceof Libro )
                .filter( e -> (( Libro ) e).getAutore().equals( autore ) )
                .collect( Collectors.toList() );
    }

    public void scrivi( Catalogo item ) {  // SERVE PER SCRIVERE UN NUOVO ITEM NEL DATABASE
        File database = new File( "docs/database.txt" );
        String encoding = "UTF-8";

        String oggettoStringato = item instanceof Libro ?
                "" + (this.determinaISBN() + 1)
                        + "-" + item.getTitolo()
                        + "-" + item.getAnnoPubblicazione()
                        + "-" + item.getNumeroPagine()
                        + "-" + (( Libro ) item).getAutore()
                        + "-" + (( Libro ) item).getGenere()
                        + "-" + "LIBRO"
                        + "/\n"
                :
                        "" + (this.determinaISBN() + 1)
                                + "-" + item.getTitolo()
                                + "-" + item.getAnnoPubblicazione()
                                + "-" + item.getNumeroPagine()
                                + "-" + (( Rivista ) item).getPeriodicita()
                                + "-" + "RIVISTA"
                                + "/\n";

        try {
            FileUtils.writeStringToFile( database, oggettoStringato, encoding, true );
            this.database = this.leggi();
        } catch( IOException e ) {
            throw new RuntimeException( e );
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

                String[] arrOfItems = this.databaseString.split( "/\n" );

                for( String item : arrOfItems ) {
                    String[] stringaSplit = item.split( "-" );

                    for( int j = 0 ; j < stringaSplit.length ; j++ ) {

                        if( stringaSplit.length == 7 ) {
                            Catalogo obj = new Libro(
                                    Integer.parseInt( stringaSplit[ 0 ] ),
                                    stringaSplit[ 1 ],
                                    Integer.parseInt( stringaSplit[ 2 ] ),
                                    Integer.parseInt( stringaSplit[ 3 ] ),
                                    stringaSplit[ 4 ],
                                    determinaGenere( stringaSplit[ 5 ] ) );
                            if( j == stringaSplit.length - 1 ) {
                                catalogoList.add( obj );
                            }
                        } else if( stringaSplit.length == 6 ) {
                            Catalogo obj = new Rivista(
                                    Integer.parseInt( stringaSplit[ 0 ] ),
                                    stringaSplit[ 1 ],
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
    } // ELABORA IL CONTENUTO DEL DATABASE, RITORNA UN ARRAY

    // DI OGGETTI DI CLASSE CATALOGO
    public void rimuovi( int isbn ) {

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

        if( controllo ) {
            for( Catalogo value : this.database ) {
                if( value.getCodiceISBN() != isbn ) {
                    arraySenzaElemento.add( value );

                } else {
                    System.out.println();
                    System.out.println( "*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*" );
                    System.out.println( "Elimino l'elemento: " + value );
                    System.out.println( "*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*" );
                    System.out.println();
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

    } // RIMUOVE DAL DATABASE L'ITEM CHE CONTIENE QUEL CODICE ISBN

    public void modaleNuovoItem() {
        boolean esegui = true;
        do {
            System.out.println();
            System.out.println( "AGGIUNGI ELEMENTO AL CATALOGO" );
            System.out.println();
            System.out.println( """
                    Selezionare la tipologia
                    1 per aggiungere un libro
                    2 per aggiungere una rivista
                    0 per uscire""" );
            try {
                int scelta = scanner.nextInt();
                switch (scelta) {
                    case 1 -> {
                        System.out.println();
                        System.out.println( "Hai scelto libro" );
                        scanner.nextLine();
                        System.out.println( "Inserisci il titolo del libro" );
                        String titolo = scanner.nextLine();
                        System.out.println( "Il titolo scelto è: " + titolo );
                        System.out.println( "Inserisci l'anno di pubblicazione" );
                        int anno = scanner.nextInt();
                        System.out.println( "L'anno di pubblicazione: " + anno );
                        System.out.println( "Inserisci il numero di pagine" );
                        int pagine = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println( "Il numero di pagine è: " + pagine );
                        System.out.println( "Inserisci il nome dell'autore" );
                        String nome = scanner.nextLine();
                        System.out.println( "Il nome dell'autore: " + nome );
                        System.out.println( "Inserisci la categoria del libro" );
                        String categoria = scanner.nextLine().toUpperCase();
                        System.out.println( "La categoria del libro: " + categoria );
                        Catalogo nuovoLibro = new Libro( titolo, anno, pagine, nome, determinaGenere( categoria ) );
                        scrivi( nuovoLibro );
                    }
                    case 2 -> {
                        System.out.println();
                        System.out.println( "Hai scelto rivista" );
                        scanner.nextLine();
                        System.out.println( "Inserisci il titolo della rivista" );
                        String titolo = scanner.nextLine();
                        System.out.println( "Il tipolo scelto è: " + titolo );
                        System.out.println( "Inserisci l'anno di pubblicazione" );
                        int anno = scanner.nextInt();
                        System.out.println( "L'anno di pubblicazione: " + anno );
                        System.out.println( "Inserisci il numero di pagine" );
                        int pagine = scanner.nextInt();
                        System.out.println( "L'numero di pagine: " + pagine );
                        scanner.nextLine();
                        System.out.println( "Inserisci la periodicità dela rivista" );
                        String periodicita = scanner.nextLine().toUpperCase();
                        System.out.println( "La periodicità  della rivista è: " + periodicita );
                        Catalogo nuovaRivista = new Rivista( titolo, anno, pagine,
                                determinaPeriodicita( periodicita ) );
                        scrivi( nuovaRivista );
                    }
                    case 0 -> {
                        System.out.println();
                        System.out.println( "Hai scelto di uscire" );
                        esegui = false;
                    }
                    default -> System.out.println( "input non valido" );
                }
            } catch( NumberFormatException e ) {
                System.out.println( "Errore nell'inserimento dell'input" );
            }
            System.out.println();
            System.out.println( "ECCO IL TUO CATALOGO AGGIORNATO" );
            getDatabases();
            System.out.println();

        } while( esegui );
    }

    public void modaleRimuovi() {

        boolean esegui = true;

        do {
            System.out.println();
            System.out.println( "*___CATALOGO____*" );
            System.out.println();
            getDatabases();
            System.out.println();

            System.out.println( "ELIMINA ELEMENTO DAL CATALOGO TRAMITE CODICE ISBN" );
            System.out.println( "Inserisci il codice ISBN per eliminare, 0 per uscire" );


            try {
                int codice = scanner.nextInt();

                if( codice == 0 ) {
                    esegui = false;
                    System.out.println();
                    System.out.println( "Hai scelto di uscire" );
                } else {
                    rimuovi( codice );
                }

            } catch( NumberFormatException e ) {
                System.out.println( "Si accettano solo numeri! Riprova" );
            }
        } while( esegui );

    }

    public void modaleRicerca() {
        boolean esegui = true;

        do {
            System.out.println();
            System.out.println( "MODALITA' RICERCA" );
            System.out.println( "Seleziona il tipo di ricerca" );
            System.out.println( """
                    1. Ricerca per codice ISBN
                    2. Ricerca anno di pubblicazione
                    3. Ricerca per nome autore
                    4. Visualizza catalogo
                    0. Per uscire""" );

            try {
                int scelta = scanner.nextInt();
                switch (scelta) {
                    case 1 -> {
                        System.out.println();
                        System.out.println( "Hai scelto ISBN" );
                        System.out.println( "inserisci il codice ISBN: " );
                        try {
                            int codice = scanner.nextInt();
                            List<Catalogo> trovato = filtraPerISBN( codice );
                            if( trovato.size() > 0 ) {
                                System.out.println();
                                System.out.println( "Risultati: " + trovato.size() );
                                System.out.println( trovato );
                            } else {
                                System.out.println( "Nessun elemento trovato" );
                            }
                            System.out.println();

                        } catch( InputMismatchException e ) {
                            System.out.println( "Qualcosa è andato storto, Riprova" );
                            scanner.nextLine();
                        }
                    }
                    case 2 -> {
                        System.out.println();
                        System.out.println( "Hai scelto ricerca per anno" );
                        System.out.println( "inserisci l'anno di pubblicazione " );
                        try {
                            int anno = scanner.nextInt();
                            List<Catalogo> trovato = filtraPerAnno( anno );
                            if( trovato.size() > 0 ) {
                                System.out.println();
                                System.out.println( "Risultati: " + trovato.size() );
                                for ( Catalogo item : trovato ) {
                                    System.out.println( item );
                                }

                            } else {
                                System.out.println( "Nessun elemento trovato" );
                            }
                            System.out.println();
                        } catch( InputMismatchException e ) {
                            System.out.println( "Qualcosa è andato storto, Riprova" );
                        }
                    }
                    case 3 -> {
                        System.out.println();
                        System.out.println( "Hai scelto ricerca per nome autore" );
                        System.out.println( "inserisci il nome dell'autore" );
                        scanner.nextLine();
                        try {
                            String autore = scanner.nextLine();
                            List<Catalogo> trovato = filtraPerAutore( autore );
                            if( trovato.size() > 0 ) {
                                System.out.println();
                                System.out.println( "Risultati: " + trovato.size() );
                                for ( Catalogo item : trovato ) {
                                    System.out.println( item );
                                }

                            } else {
                                System.out.println( "Nessun elemento trovato" );
                            }
                            System.out.println();
                        } catch( InputMismatchException e ) {
                            System.out.println( "Qualcosa è andato storto, Riprova" );
                        }
                    }
                    case 0 -> {
                        System.out.println( "Hai scelto di uscire" );
                        esegui = false;
                    }
                    case 4 -> {
                        System.out.println( "Visualizzo il catalogo" );
                        getDatabases();
                    }
                    default -> {
                        System.out.println("inserisci un input valido!");
                    }
                }
            } catch( InputMismatchException e ) {
                System.out.println( "Qualcosa è andato storto! Riprova" );
                scanner.nextLine();
            }

        } while( esegui );
    }

    public void open() {
        boolean esegui = true;

        do {
            System.out.println();
            System.out.println("MENU");
            System.out.println( """
                1. Per visualizzare il catalogo
                2. Per aggiungere un nuovo libro/rivista
                3. Per rimuovere un libro/rivista
                4. Per effettuare una ricerca in base al codice ISBN, anno e nome autore
                0. Per uscire""");

            try {
                int scelta = scanner.nextInt();

                switch( scelta ) {
                    case 0 -> {
                        System.out.println("ARRIVEDERCI!");
                        esegui = false;
                    }
                    case 1 -> {
                        System.out.println("Visualizzo il catalogo");
                        getDatabases();
                    }
                    case 2 -> {
                        System.out.println();
                        System.out.println("Hai scelto di aggiungere un nuovo libro/rivista");
                        modaleNuovoItem();
                    }
                    case 3 -> {
                        System.out.println("Hai scelto di rimuovere un libro/rivista");
                        modaleRimuovi();
                    }
                    case 4 -> {
                        System.out.println("Hai scelto di effettuare una ricerca");
                        modaleRicerca();
                    }
                    default -> {
                        System.out.println("Inserisci un input valido");
                    }
                }

            } catch( InputMismatchException e ) {
                System.out.println("Qualcosa è andato storto, riprova");
                scanner.nextLine();
            }
        } while( esegui );



        scanner.close();
    }
}
