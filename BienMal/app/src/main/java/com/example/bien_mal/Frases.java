package com.example.bien_mal;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Frases
{
    private List<String> frases;

    public Frases()
    {
        frases = new LinkedList<>();
        rellenaLista();
    }

    public void rellenaLista()
    {
        frases.add("¿Quién es el presidente de España?");
        frases.add("¿Quién es el presidente de Rusia?");
        frases.add("¿Quién es el rey de España?");
        frases.add("¿Quién es la reina de Inglaterra?");
        frases.add("¿Quién es el presidente de Estados Unidos?");
        frases.add("¿Cada cuántos años es un año bisiesto?");
        frases.add("Cuántos días tiene un mes bisiesto?");
        frases.add("El marido de tu hija es tu...");
        frases.add("El hermano de tu padre es tu...");
        frases.add("La madre de tu novia es tu...");
        frases.add("La hija del hermano de tu madre es tu...");
        frases.add("¿Cuántos segundos tienen 2 minutos?");
        frases.add("¿Cuántos minutos tienen 60 segundos?");
        frases.add("¿Cuántos años hay en 365 días?");
        frases.add("¿Cómo se llama el año que tiene 366 días?");
        frases.add("La guitarra es un instrumento de...");
        frases.add("La trompeta es un instrumento de...");
        frases.add("El piano es un instrumento de...");
        frases.add("¿Qué significan las siglas de OVNI?");
        frases.add("¿Qué significan las siglas de PP?");
        frases.add("¿Qué significan las siglas de PSOE?");
        frases.add("Lo que pasa en Las Vegas se queda en...");
        frases.add("¿De qué color era el caballo blanco de Santiago?");
        frases.add("¿De qué color es el traje de Papá Noel?");
        frases.add("¿De qué color son las hojas de los árboles?");
        frases.add("Colores de la bandera de España");
        frases.add("Colores de la bandera de Francia");
        frases.add("Colores de la bandera de Italia");
        frases.add("Colores de la bandera de Brasil");
        frases.add("Colores de la bandera de Estados Unidos");
        frases.add("Nombra un presidente que haya sido asesinado");
        frases.add("Película en la que aparece Leonardo DiCaprio");
        frases.add("Película en la que aparece Johnny Depp");
        frases.add("Película en la que aparece Brad Pitt");
        frases.add("Película en la que aparece Jennifer Lawrence");
        frases.add("Película en la que aparece Emma Watson");
        frases.add("Nombra un actor de El príncipe de Bel-Air");
        frases.add("Nombra un personaje de Star Wars");
        frases.add("Nombra un personaje de El señor de los anillos");
        frases.add("Nombra una película Disney");
        frases.add("Nombra un color del arcoiris");
        frases.add("Nombra un color del parchís");
        frases.add("Nombra un color del UNO");
        frases.add("Nombra una marca de coche");
        frases.add("Nombra una marca de colonia");
        frases.add("Nombra una marca de ropa interior");
        frases.add("Nombra una marca de deporte");
        frases.add("Nombra una marca de smartphones");
        frases.add("Nombra una marca de ropa");
        frases.add("Nombra una cadena de comida rápida");
        frases.add("Nombra un signo del zodíaco");
        frases.add("Nombra una constelación");
        frases.add("Nombra uno de los cinco sentidos");
        frases.add("¿Cuántos ojos tiene una persona?");
        frases.add("Canción de Alejandro Sanz");
        frases.add("Canción de Daddy Yankee");
        frases.add("Canción de Pablo Alborán");
        frases.add("Canción de David Bisbal");
        frases.add("¿De quién es la canción Asereje?");
        frases.add("¿A qué cantante le robaron el carro?");
        frases.add("Nombra un planeta del Sistema Solar");
        frases.add("¿Qué es lo contrario de suelo?");
        frases.add("¿Qué es lo contrario de frío?");
        frases.add("¿Qué es lo contrario de sol?");
        frases.add("¿Qué es lo contrario de triste?");
        frases.add("¿Cuál es la raíz cuadrada de 4?");
        frases.add("Programa de televisión en el que salen dos hormigas");
        frases.add("¿Cuántos huevos puede poner un gallo al día?");
        frases.add("¿Cuál es la hembra del pollo?");
        frases.add("¿Qué parte del ordenador tiene nombre de animal?");
        frases.add("Continúa la canción:\nEra un domingo en la tarde y...");
        frases.add("Continúa la canción:\nDale a tu cuerpo alegría...");
        frases.add("Continúa la canción:\nCuando zarpa el...");
        frases.add("Continúa la canción:\nTengo, tengo la camisa...");
        frases.add("Sigue el refrán:\nAnte la duda...");
        frases.add("Sigue el refrán:\nNo por mucho madrugar...");
        frases.add("Sigue el refrán:\nMás vale pájaro en mano...");
        frases.add("Sigue el refrán:\nA caballo regalado...");
        frases.add("Sigue el refrán:\nAl pan pan...");
        frases.add("Sigue el refrán:\nAunque la mona se vista de seda...");
        frases.add("Sigue el villancico:\n25 de Diciembre...");
        frases.add("Sigue el villancico:\nHacia Belén va una burra...");
        frases.add("¿Cuál es la capital de España?");
        frases.add("Fruta que se come sin pelar");
        frases.add("Hueso del brazo");
        frases.add("Hueso de la pierna");
        frases.add("Ingrediente de la paella");
        frases.add("Instrumento que no tenga cuerdas");
        frases.add("Animal que no tiene alas");
        frases.add("Animal que no tiene patas");
        frases.add("Animal que no tiene aleta");
        frases.add("Persona que no puede ver");
        frases.add("Persona que no puede oir");
        frases.add("Persona que no puede hablar");
        frases.add("El mejor amigo de Astérix");
        frases.add("¿Cuántas patas tiene una araña?");
        frases.add("¿Dónde te encuentras si adelantas al segundo en una carrera?");
        frases.add("¿Qué beben las vacas?");
        frases.add("Nombra un famoso con bigote");
        frases.add("¿Qué hora fue hace 5 minutos?");
        frases.add("Nombra un producto del Mercadona");
        frases.add("Nombra un deporte de riesgo");
        frases.add("Di el nombre de la persona a tu derecha");
        frases.add("Di el nombre de la persona a tu izquierda");
        frases.add("Di el nombre de la persona que tienes enfrente");
        frases.add("¿Quién descubrió América?");
        frases.add("¿Cuántos lados tiene un pentágono?");
        frases.add("¿Cuántos lados tiene un círculo?");
    }

    public void annadeFrase(String frase)
    {
        if (!frases.contains(frase))
        {
            frases.add(frase);
        }
    }
    public void annadeLista(List<String> frasesNuevas) {frases.addAll(frasesNuevas);}
    public String fraseAleatoria()
    {
        Random r = new Random();

        int n = r.nextInt(frases.size());
        String frase = frases.get(n);
        frases.remove(n);

        return frase;
    }
    public String bienMal()
    {
        Random r = new Random();

        int n = r.nextInt(2);
        if (n == 1)
        {
            return "Bien";
        }
        else
        {
            return "Mal";
        }
    }
    public boolean listaVacia() {return frases.size() == 0;}
}
