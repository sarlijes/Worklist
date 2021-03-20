package com.mycompany.unicafe;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class KassapaateTest {

    Maksukortti kortti;
    Kassapaate kassa;

    @Before
    public void setUp() {
        kortti = new Maksukortti(1000);
        kassa = new Kassapaate();
    }

    @Test
    public void luotuKorttiJaKassaOlemassa() {
        assertTrue(kortti != null);
        assertTrue(kassa != null);
    }

    @Test
    public void uudenKassanpaatteenTiedotOikein() {
        assertThat(kassa.kassassaRahaa(), is(equalTo(100_000)));
        assertThat(kassa.edullisiaLounaitaMyyty(), is(0));
        assertThat(kassa.maukkaitaLounaitaMyyty(), is(0));
    }
    @Test
    public void kateisOstoToimiiTasarahalla() {
        assertThat(kassa.syoEdullisesti(240), is(0));
        assertThat(kassa.syoMaukkaasti(400), is(0));
    }

    @Test
    public void kateisOstoToimiiVaihtorahallaJaVaihtorahaOikein() {
        assertThat(kassa.syoEdullisesti(300), is(60));
        assertThat(kassa.syoMaukkaasti(500), is(100));
    }

    @Test
    public void riittavaMaksuKasvattaaKassanRahaMaaraa() {
        kassa.syoEdullisesti(240);
        assertThat(kassa.kassassaRahaa(), is(100_240));
        kassa.syoEdullisesti(1000);
        assertThat(kassa.kassassaRahaa(), is(100_480));
    }

    @Test
    public void riittamatonMaksuToimii() {
        assertThat(kassa.syoEdullisesti(88), is(88));
        assertThat(kassa.kassassaRahaa(), is(100_000));
    }

    @Test
    public void riittavaMaksuKasvataaMyytyjenLounaidenMaaraa() {
        kassa.syoEdullisesti(240);
        kassa.syoEdullisesti(240);
        assertThat(kassa.edullisiaLounaitaMyyty(), is(2));
        kassa.syoMaukkaasti(400);
        kassa.syoMaukkaasti(400);
        assertThat(kassa.maukkaitaLounaitaMyyty(), is(2));
    }

    @Test
    public void riittamatonMaksuEiMuutaKassanTilaaJaRahatPalautetaan() {
        assertThat(kassa.syoMaukkaasti(7), is(7));
        assertThat(kassa.kassassaRahaa(), is(equalTo(100_000)));
        assertThat(kassa.maukkaitaLounaitaMyyty(), is(0));
        assertThat(kassa.edullisiaLounaitaMyyty(), is(0));
    }

    @Test
    public void korttiOstoPalauttaaTrue() {
        assertThat(kassa.syoMaukkaasti(kortti), is(true));
        assertThat(kassa.syoEdullisesti(kortti), is(true));
    }


    @Test
    public void kunKortillaRahaaVoiOstaaLounaitaKassanTilaPaivittyy() {
        kassa.syoEdullisesti(kortti);
        assertThat(kassa.kassassaRahaa(), is(100_000));
        assertThat(kassa.edullisiaLounaitaMyyty(), is(1));

        kassa.syoMaukkaasti(kortti);
        assertThat(kassa.kassassaRahaa(), is(100_000));
        assertThat(kassa.maukkaitaLounaitaMyyty(), is(1));
    }

    @Test
    public void kunKortillaRahaaVoiOstaaLounaitaKortinSaldoPaivittyy() {
        assertThat(kassa.syoEdullisesti(kortti), is(true));
        assertThat(kortti.saldo(), is(760));

        kassa.syoMaukkaasti(kortti);
        assertThat(kortti.saldo(), is(360));
    }


    @Test
    public void kunKortillaEiTarpeeksiRahaaEiMyydä() {
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);

        assertThat(kassa.syoEdullisesti(kortti), is(false));
        assertThat(kassa.edullisiaLounaitaMyyty(), is(0));
        assertThat(kassa.maukkaitaLounaitaMyyty(), is(2));
        assertThat(kassa.syoMaukkaasti(kortti), is(false));

        assertThat(kortti.saldo(), is(200));

    }
//kortille rahaa ladattaessa kortin saldo muuttuu ja kassassa oleva rahamäärä kasvaa ladatulla summalla

    @Test
    public void kassassaOlevaRahamaaraEiMuutuKortillaOstettaessa() {
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);

        assertThat(kassa.kassassaRahaa(), is(100_000));
    }


    @Test
    public void korttinLatausLisaaKortinSaldoaJaKassanRahamaaraa() {
        kassa.lataaRahaaKortille(kortti, 100);
        assertThat(kortti.saldo(), is(1100));
        assertThat(kassa.kassassaRahaa(), is(100_100));
    }

    @Test
    public void eiVoiLatadaNegatiivistaSaldoa() {
        kassa.lataaRahaaKortille(kortti, -100);
        assertThat(kortti.saldo(), is(1000));
        assertThat(kassa.kassassaRahaa(), is(100_000));
    }




}
