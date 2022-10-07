package it.Adiener;

import Classi.Archivio;
import Classi.Catalogo;
import Classi.Libro;
import Classi.Rivista;
import InterfacesAndEnums.Genere;
import InterfacesAndEnums.Periodicita;

import java.util.Scanner;
import java.util.stream.Stream;

public class Main {
    public static void main( String[] args ) {
        Catalogo libro1 = new Libro( "ciao a tutti", 2022, 200, "BUDKA", Genere.COMMEDIA );
        Catalogo rivista1 = new Rivista( "Pirati dei mari", 2022, 560, Periodicita.SETTIMANALE );
        Catalogo libro2 = new Libro( "Mamma mia", 2000, 800, "BUDKA", Genere.HORROR );
        Catalogo rivista2 = new Rivista( "volo", 2022, 560, Periodicita.SETTIMANALE );

        Archivio archivio = new Archivio();

//        archivio.scrivi( libro2 );
//        archivio.rimuovi( 1014 );

        archivio.getDatabases();

//        System.out.println( "___________" );
//        System.out.println( archivio.filtraPerISBN( 1015 ) );
//        System.out.println( archivio.filtraPerAnno( 2010 ) );
//
//        System.out.println( "___________" );
//        System.out.println( archivio.filtraPerAutore( "BUDKA" ) );


    }

}


