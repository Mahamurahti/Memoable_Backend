# Backend Memoable muistiinpanosovellukselle

## Asennusohje

Asenna Java 11:

#### Oracle:
[https://www.oracle.com/java/technologies/javase-jdk11-downloads.html](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)

<br>

-------


Tai käyttöjärjestelmästä riippuen Openjdk kirjastoa.

#### Centos:
`yum install java-11-openjdk-devel`

#### Ubuntu:
`apt install openjdk-11-jdk-headless`

## Mongodb

Asenna mongodb [ohjeet](https://docs.mongodb.com/manual/installation/)

## Käynnistys

Hae repositorio omalle laitteelle `git clone` komennolla.

### Docker

Jos docker ei ole asennettu, [asennusohjeet](https://docs.docker.com/engine/install/):

Siirry projektin kansioon `cd memoable-backend`.

Säiliön rakennus:

`docker build -t memoable/backend .`

`docker run --net=host --name memoable memoable/backend`

Komento `--net=host` mahdollistaa säiliön kommunikoinnin isäntäjärjestelmään asennettuun mongodb tietokantaan.

Säiliön käynnistys:

`docker start memoable`


### Suoraan isäntäjärjestelmässä

Siirry projektin kansioon `cd memoable-backend`.

Sovelluksen käynnistykseen suorita komento

`./mvnw spring-boot:run`

------

## Käyttöohjeet

### Käyttäjän luonti

Käyttäjän voi luoda lähettämällä Http.POST kutsu osoitteeseen

`{palvelimen_ip};8090/api/user`

jossa lähetetään viestirungossa JSON-formatoituna käyttäjänimi ja salasana muodossa:
```
{
    "username": "{nimi}",
    "password": "{salasana}
}
```

Luonti palauttaa käyttäjän nimen ja ID:n muodossa `{k_nimi}, {käyttäjän_id}`

Jos käyttäjän nimi on jo käytössä palautuu `unameErr`.

### Käyttäjän poisto

Käyttäjän voi poistaa lähettämällä Http.DELETE pyyntö, jossa on Authorization header muodossa `Bearer {token}` osoitteeseen

`{palvelimen_ip}:8090/api/user/delete/{käyttäjän_id}`

<br>

### Kirjautuminen

Backendiin voi kirjautua lähettämällä HTTP.GET pyyntö osoitteeseen

`{palvelimen_ip}:8090/api/login`

jossa lähetetään viestirungossa JSON-formatoituna käyttäjänimi ja salasana muodossa:
```
{
    "username": "{nimi}",
    "password": "{salasana}
}
```

Jos kirjautuminen onnistuu, palvelin palauttaa käyttäjän ID:n ja JWT-tokenin muodossa:

`{id} {token}`

<br>

### Muistiinpanon lisääminen

Muistiinpanon lisääminen toimii länettämällä Http.POST pyynto, jossa on Authorization header muodossa `Bearer {token}` osoitteeseen:

`{palvelimen_ip}:8090/api/notes/save/{käyttäjän_id}`

Muistiinpano lähetetään JSON muodossa.

```
{
    "title": "{otsikko}",
    "content": "{sisältö}",
    "label": "{label}",
    "tag": "{tag}",
    "date": "{date}"
}
```
`date` ja `label` kentät voivat olla tyhjiä, muissa täytyy olla sisältö, muuten palautuu null.

Jos on kirjauduttu sisään ja token on oikea palautetaan tallennettu muistiinpano, muuten palautuu null.

### Muistiinpanojen hakeminen

Muistiinpanoja voidaan hakea lähettämällä Http.GET pyynto, jossa on Authorization header muodossa `Bearer {token}` osoitteeseen:

`{palvelimen_ip}:8090/api/notes/{käyttäjän_id}`

Jos token on oikea, lähettäjälle palautetaan lista Note objekteja.

### Muistiinpanon poistaminen

Muistiinpanon voi poistaa lähettämällä Http.DELETE pyynto, jossa on Authorization header muodossa `Bearer {token}` osoitteeseeen

`{palvelimen_ip}:8090/api/notes/delete/{käyttäjän_id}/{muistiinpanon_id}`

Palautuu True jos poisto onnistuu ja False jos poisto ei onnistu.

<br>
-----

**Tehty käyttämällä Java Spring kirjastoa**

### Tekijä: Jere Salmensaari
