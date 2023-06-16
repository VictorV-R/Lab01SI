package org.example;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Lab01 {
    public static void main(String[] args) {

        String poema = leerPoema();
        String poemaPrePro = preProcesamiento(poema);
        Map<Character, Integer> tablaFrecuencias = frecuencias(poemaPrePro);
        imprimirTablaFrecuencias(tablaFrecuencias);
        imprimirCaracteresDeMayorFrec(tablaFrecuencias);

    }

    private static void imprimirCaracteresDeMayorFrec(Map<Character, Integer> tablaFrecuencias) {
        System.out.println("Caracteres de mayor frecuencia:");
        for (Map.Entry<Character, Integer> entry : obtenerCaracteresMasFrecuentes(tablaFrecuencias).entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private static void imprimirTablaFrecuencias(Map<Character, Integer> tablaFrecuencias) {
        System.out.println("Tabla de Frecuencias:");
        for (Map.Entry<Character, Integer> entry : tablaFrecuencias.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private static Map<Character, Integer> obtenerCaracteresMasFrecuentes(Map<Character, Integer> tablaFrecuencias) {
        return tablaFrecuencias.entrySet().stream()
                .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, HashMap::new));
    }

    private static Map<Character, Integer> frecuencias(String poemaPrePro) {
        Map<Character, Integer> tablaFrecuencias = new HashMap<>();
        for (char c : poemaPrePro.toCharArray()) {
            if (Character.isLetter(c)) {
                tablaFrecuencias.put(c, tablaFrecuencias.getOrDefault(c, 0) + 1);
            }
        }
        return tablaFrecuencias;
    }


    private static String preProcesamiento(String poema) {
        System.out.println("Poema Original: " + poema);
        String poema2 = sustituirLetras(poema);
        System.out.println("Sustitucion de letras: " + poema2);
        String poema3 = eliminarTildes(poema2);
        System.out.println("Eliminacion de tildes: " + poema3);
        String poema4 = poema3.toUpperCase();
        System.out.println("Convertir a mayusculas: " + poema4);
        String poema5 = eliminarEspaciosYPuntos(poema4);
        System.out.println("Eliminar espacios y puntos: " + poema5);
        String alfabetoResultante = obtenerAlfabeto(poema5);
        int longitud = alfabetoResultante.length();
        System.out.println("Alfabeto resultante: " + alfabetoResultante);
        System.out.println("Longitud del alfabeto resultante: " + longitud);
        guardarPoemaPre(poema5);
        return poema5;
    }

    private static void guardarPoemaPre(String poema5) {
        FileWriter fichero = null;
        PrintWriter pw;
        try
        {
            fichero = new FileWriter("C:\\Users\\v\\Desktop\\POEMA_PRE.txt");
            pw = new PrintWriter(fichero);
            pw.println(poema5);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Nuevamente aprovechamos el finally para
                // asegurarnos que se cierra el fichero.
                if (null != fichero)
                    fichero.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private static String obtenerAlfabeto(String poema5) {
        return poema5.chars()
                .distinct()
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private static String eliminarEspaciosYPuntos(String poema4) {
        return poema4.replaceAll("[\\s\\p{P}\\p{S}]", "");
    }

    private static String eliminarTildes(String poema) {
        Map<Character, Character> mapaTildes = new HashMap<>();
        mapaTildes.put('á', 'a');
        mapaTildes.put('é', 'e');
        mapaTildes.put('í', 'i');
        mapaTildes.put('ó', 'o');
        mapaTildes.put('ú', 'u');
        mapaTildes.put('Á', 'A');
        mapaTildes.put('É', 'E');
        mapaTildes.put('Í', 'I');
        mapaTildes.put('Ó', 'O');
        mapaTildes.put('Ú', 'U');
        mapaTildes.put('ñ', 'n');
        mapaTildes.put('Ñ', 'N');
        StringBuilder resultado = new StringBuilder();
        for (char c : poema.toCharArray()) {
            if (mapaTildes.containsKey(c)) {
                resultado.append(mapaTildes.get(c));
            } else {
                resultado.append(c);
            }
        }
        return resultado.toString();
    }

    private static String sustituirLetras(String poema) {
        Map<Character, Character> mapaLetras = new HashMap<>();
        mapaLetras.put('a', 'i');
        mapaLetras.put('h', 'j');
        mapaLetras.put('ñ', 'n');
        mapaLetras.put('k', 'l');
        mapaLetras.put('v', 'u');
        mapaLetras.put('w', 'v');
        mapaLetras.put('z', 'y');
        mapaLetras.put('r', 'f');
        mapaLetras.put('A', 'I');
        mapaLetras.put('H', 'J');
        mapaLetras.put('Ñ', 'N');
        mapaLetras.put('K', 'L');
        mapaLetras.put('V', 'U');
        mapaLetras.put('W', 'V');
        mapaLetras.put('Z', 'Y');
        mapaLetras.put('R', 'F');
        StringBuilder resultado = new StringBuilder();
        for (char c : poema.toCharArray()) {
            if (mapaLetras.containsKey(c)) {
                resultado.append(mapaLetras.get(c));
            } else {
                resultado.append(c);
            }
        }
        return resultado.toString();
    }

    private static String leerPoema() {
        StringBuilder poema = new StringBuilder();
        File archivo;
        FileReader fr = null;
        BufferedReader br;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File ("C:\\Users\\v\\Desktop\\poema.txt");
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while((linea=br.readLine())!=null)
                poema.append(linea);
        }
        catch(Exception e){
            e.printStackTrace();
        }finally{
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta
            // una excepcion.
            try{
                if( null != fr ){
                    fr.close();
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
        return poema.toString();
    }
}