# Arkkitehtuurikuvaus

## Toiminnallisuus

Toiminnallisuus on kuvattu [vaatimusmäärittelyssä](https://github.com/sarlijes/Worklist/blob/master/Documentation/requirement-analysis.md)

## Rakenne

Ohjelman pakkausrakenne on seuraava:

- pakkaus ```entity``` sisältää tietokantatauluja kuvaavat luokat
- pakkaus ```dao``` sisältää tietokantayhteyksiin ja liittyvän logiikan ja apuluokat
- pakkaus ```ui``` sisältää käyttöliittymätoiminnallisuudet

## Tiedon pysyväistallennus

- tiedot tallennetaan H2-tietokantaan
- yksikkötesteissä käytössä ajonaikainen "in-memory"-H2-tietokanta

## Alustava kuvaus sovelluslogiikasta

Sovelluksen loogisen datamallin muodostavat luokat Job, Employee ja Material, jotka kuvaavat sovellukseen kirjattavia työtilauksia, sovelluksen käyttäjiä sekä työtilausten materiaaleja:

<img src="https://github.com/sarlijes/Worklist/blob/master/Documentation/pictures/class_diagram.PNG?raw=true">

UI-luokat huolehtivat käyttöliittymäkomponenttien sijoittelusta ja logiikasta. Ne kutsuvat tarvittaessa DAO (suunnittelumalli [Data Access Object](https://en.wikipedia.org/wiki/Data_access_object))-luokkia, jotka huolehtivat tietojen pysyväistallennuksesta, tietokannasta lukemisesta ja muista tietokantaoperaatioista. DAO-luokat saavat tietokantayhteyden ```Connection```-olion parametrinään, joten samoja DAO-luokkia hyödynnetään myös automaattisissa yksikkötesteissä.

Esimerkki käyttäjän kirjautumisesta sekvenssikaaviona:

<img src="https://github.com/sarlijes/Worklist/blob/master/Documentation/pictures/employee_login_sequence.PNG?raw=true">