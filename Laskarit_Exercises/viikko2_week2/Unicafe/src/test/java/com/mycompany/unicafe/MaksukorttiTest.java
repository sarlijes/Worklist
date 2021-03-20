package com.mycompany.unicafe;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti != null);
    }

    @Test
    public void rahanSaldoAlussaOikein() {
        assertTrue(kortti.saldo() == 10);
        assertThat(kortti.saldo(), is(equalTo(10)));
    }

    @Test
    public void rahanLataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(10);
        assertThat(kortti.saldo(), is(equalTo(20)));
        kortti.lataaRahaa(1000);
        assertThat(kortti.saldo(), is(equalTo(1020)));
    }

    @Test
    public void rahanOttaminenToimii() {
        kortti.otaRahaa(2);
        assertThat(kortti.saldo(), is(equalTo(8)));
        kortti.otaRahaa(8);
        assertThat(kortti.saldo(), is(equalTo(0)));
    }

    @Test
    public void saldoVaheneeOikeinJosRahaaOnTarpeeksi() {
        kortti.otaRahaa(2);
        assertThat(kortti.saldo(), is(equalTo(8)));
    }

    @Test
    public void saldoEiMuutuJosRahaaOnLiianVahan() {
        kortti.otaRahaa(58);
        assertThat(kortti.saldo(), is(equalTo(10)));
    }

    @Test
    public void metodiPalauttaaTrueJosRahatRiittivat() {
        assertThat(kortti.otaRahaa(2), is(true));
    }

    @Test
    public void metodiPalauttaaFalseJosRahatEivatRiittaneet() {
        assertThat(kortti.otaRahaa(2222), is(false));
    }

    @Test
    public void toStringPalauttaaOikein() {
        Maksukortti k = new Maksukortti(100);
        assertThat(k.toString(), is(equalTo("saldo: 1.0")));
    }
}
