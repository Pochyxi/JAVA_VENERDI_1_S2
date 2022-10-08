package it.Adiener;

import Classi.Archivio;
import Classi.Catalogo;
import Classi.Libro;

import InterfacesAndEnums.Genere;


import java.io.IOException;



public class Main {
    public static void main( String[] args ) {
        Catalogo libro = new Libro(1, "Quinto", 1957, 1300, "A.D.L.V", Genere.AZIONE );

        Archivio archivio = new Archivio();

//        archivio.scrivi( libro );
//        archivio.rimuovi( 4 );

        archivio.getDatabases();








//        System.out.println( "___________" );
//        System.out.println( archivio.filtraPerISBN( 2 ) );
//        System.out.println( archivio.filtraPerAnno( 1957 ) );
//
//        System.out.println( "___________" );
//        System.out.println( archivio.filtraPerAutore( "A.D.L.V" ) );


    }

}


