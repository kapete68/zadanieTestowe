
## Uwagi

W dotychczasowych projektach zazwyczaj korzystałem z języka angielskiego, dlatego niektóre nazwy funkcji i zmiennych w tym projekcie mogą wydawać się nietypowe. Wynika to z faktu, że czasami trudno było dobrać odpowiednie angielskie określenia.

W cześciach kodu są dodane komentarze, umieszczałem je kiedy czułem, że mogą być potrzebne.

## Instrukcje uruchomienia

### 1. Docker
```bash
docker run -d --name ogloszenie -e POSTGRES_PASSWORD=password -p 5432:5432 postgres
psql -h localhost -U postgres
create database ogloszenia;

./gradlew bootRun


```

## 2.Postman

Z pewnością Państwo wiedzą ale powiem tylko, że najpierw trzeba zrobić posta, aby utworzyć ogłoszenie. Następnie skopiować id posta i wklejać je do url.