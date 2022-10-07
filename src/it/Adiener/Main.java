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
        Catalogo libro1 = new Libro( "ciao", 2022, 200, "BUDKA", Genere.COMMEDIA );
        Catalogo rivista1 = new Rivista( "mamma", 2022, 560, Periodicita.SETTIMANALE );
        Catalogo libro2 = new Libro( "come", 2022, 200, "BUDKA", Genere.COMMEDIA );
        Catalogo rivista2 = new Rivista( "volo", 2022, 560, Periodicita.SETTIMANALE );

        System.out.println( libro1.getCodiceISBN() );
        System.out.println( rivista1.getCodiceISBN() );

        Archivio archivio = new Archivio();

//        archivio.scrivi( libro2 );
//        archivio.rimuovi( 1017 );

        archivio.getDatabases();

        System.out.println( archivio.filtraPerISBN( 1016 ) );
        System.out.println( archivio.filtraPerAnno( 2010 ) );
        System.out.println( "___________" );
        System.out.println( archivio.filtraPerAutore( "BUDKA" ) );


    }

     

}


