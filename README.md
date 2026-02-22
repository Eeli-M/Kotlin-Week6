## Mitä Room tekee (Entity – DAO – Database – Repository – ViewModel – UI)

Room on Androidin kirjasto, joka käyttää SQLite-tietokantaa.

- `@Entity`-annotaatiolla määritellään tietokantataulun rakenne
- DAO (Data Access Object) vastaa tiedon hakemisesta ja tallentamisesta tietokantaan, esim.
  - `@Insert`
  - `@Update`
- Roomin tietokantaluokka yhdistää Entityn ja DAO:n
- Repository toimii ViewModelin ja tietokannan välillä, kutsuen DAO:n funktioita ja palauttaen datan Flow-muodossa
- ViewModel mm.
  - Käsittelee toimintalogiikka
  - Käynnistää coroutineja
  - Tarjoaa UI:lle StateFlow-tilan
- UI näyttää sovelluksen käyttäjälle ja kutsuu ViewModelin funktioita

## Projektisi rakenne

Projektissa on MVVM-rakenne
- Modelissa luodaan tietokannan taulu
- ViewModelissa on sovelluksen toimintalogiikkaa
- Viewissä käyttäjälle näytettävät asiat

## Miten datavirta kulkee

UI:ssa kutsutaan ViewModelin funktioita  
-> ViewModel käynnistää coroutinen  
-> Repository kutsuu DAO-funktiota (esim. `@Update`)  
-> Tieto päivitetään Room-tietokantaan  
-> UI:n `collectAsState()` huomaa muutoksen ja päivittää näkymän käyttäjälle
