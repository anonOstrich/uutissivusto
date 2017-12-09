# uutissivusto
Helsingin yliopiston web-palvelinohjeilmoinnin harjoitustyö

# aiheen kuvaus
Uutisten lisäämiseen, muokkaamiseen ja tarkasteluun käytettävä web-sovellus. Etusivulla kuka tahansa pystyy listaamaan uutiset haluamansa määreen perusteella ja klikkaamaan uutista päästäkseen yksittäisen uutisen sivulle. Yksittäisellä sivulla on uutisen varsinainen teksti ja tarkemmat tiedot. 

# tyypilliset käyttötapaukset

As a user I want to see all the news so that I can choose those that interest me. 
As a poster I want to write a piece of news so that everyone can access it. 
As a poster I want to modify my earlier article so that new information can be added / errors can be fixed.
As a user I want to see only the news from a certain time period so that I can search for reporting on specific events. 
As a user I want to view all the informatin of a particular news article so that I can satisfy my curiosity. 

# tyypilliset ominaisuudet
Kaikki uutiset listattuna etusivulla. 
Uutisia voivat katsoa kaikki, kirjoittaa vain kirjoittajat ja muokata vain kyseisen uutisen kirjoittajat.


# käytetyn tietokannan skeema
CREATE TABLE Account(id long primary key, username, password)
CREATE TABLE Article(id long primary key, lead, mainText, published datetime, account_id foreign key)
CREATE TABLE FileObject tms (id long primary key, byte[] content, article_id foreign key) 


# käyttöohje
Mene osoitteeseen vanhaset.herokuapp.com, jossa sovellus on käynnissä. Perinteisen selainnäkymän pitäisi olla selvä käyttöliittymä, joka ei kaipaa enempää selitystä. 
HUOM!
On kaksi tunnusta, joilla on oikeudet kirjoittaa ja muokata uutisia. 
tunnus: ????, salasana: ????
tunnus: ????, salasana: ????

# asennusohje
Selaimen lisäksi muuta asennusta ei tarvitse. Sivujen on varmistettu toimivan ainakin uusimmilla Chrome- ja Firefox-selaimilla. 

# puuttuvat ominaisuudet ja muut puutteet
Yksilöiminen esimerkiksi vain evästeiden tai käyttäjien kirjautumisen avulla. Tällaisten käyttäjien mieltymykset etusivun ulkoasusta voisi pitää mielessä, eikä tarvitsisi joka kerta järjestää katsomiskertojen mukaan, jos näin haluaa etusivun nähdä. Kirjautumisen avulla voisi olla mahdollista myös poimia kiinnostavia uutisia omaan uutislistaa, josta ne löytää tulevaisuudessakin helposti. 

#todo
- tietokannassa rivinvaihdot pysytvät, thymeleaf ei osaa korvata html-vastikkeilla -> uutinen tulee yhtenä kappaleena. Netissä fixejä tuntuisi olevan monia.

