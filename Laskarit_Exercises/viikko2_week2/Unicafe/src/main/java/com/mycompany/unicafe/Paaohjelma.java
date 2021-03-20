package com.mycompany.unicafe;

public class Paaohjelma {

    public static void main(String[] args) {
        Kassapaate unicafeExactum = new Kassapaate();
        Maksukortti kortti = new Maksukortti(100);
        System.out.println(kortti);
        unicafeExactum.syoEdullisesti(kortti);

        System.out.println(unicafeExactum.edullisiaLounaitaMyyty());
        System.out.println(kortti);
    }
}
