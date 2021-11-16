# API Smart-Record

## Najważniejsze informacje

* Wszystkie zapytania zwracają tablice lub obiekty JSON
* Podstawowe jednostki w API to: przedmiot, wypożyczenie, użytkownik.

## Kody odpowiedzi używane tym w API
Każda błędna odpowiedź zwraca wiadomość z informacja jaki konkrtnie błąd wystąpił

* HTTP `4XX` - używane dla błędnych zapytań. Błąd zawsze po stronie klienta
* HTTP `400` - zapytanie zawiera niepoprawne dane.
* HTTP `403` - weryfikacja niepoprawna.
* HTTP `404` - zasób `GET` nie istnieje.
* HTTP `409` - zasób `POST` już istnieje.
* HTTP `5XX` - błąd po stronie serwera.

## Format danych

* Dane w zapytaniach `GET` powinny być wysłane w URI
* Dane w zapytaniach `POST`, `PUT` i `DELETE` powinny być wysłane w `requestBody` w formacie JSON (`application/json`). Kolejność parametrów jest dowolna

## Zabezpieczenia

* Każdy punkt końcowy jest zabezpieczony i wymaga autoryzacji tokenem
* Różne punkty końcowe są dostępne dla użytkowników z różnymi uprawnieniami, zgodnie z podziałem punktów końcowych w tym dokumencie.
* Token generuje się przez zapytanie do endpointu [/login](#logowanie-uzytkownika)
* Token ma być wysłany przez użytkownika w nagłówku `Authorization`
* Token ma zawierać przedrostek `Bearer`, jest on z nim generowany
* Wygenerowany token ma ważność 14 dni i może być odnawiany (odnawianie zostanie zaimplementowane w przyszłości)
* Aby wylogować sie (zakończyć sesję) należy usunąć token z pamięci klienta. Tokeny nie są przechowywane w bazie danych, tylko sprawdzane na podstawie klucza (zgodnie ze standardem JWT)

## Punkty końcowe 

### Tabela poglądowa

| Zasób | POST | PUT | GET | DELETE |
|-------|------|-----|-----|--------|
|/item  |Dodanie nowego przedmiotu|Aktualizacja grupy przedmiotów|Pobranie listy przedmiotów|Usunięcie grupy przedmiotów|
|/item/{id}|Błąd|Aktualizacja przedmiotu|Pobranie informacji o przedmiocie|Usunięcie przedmiotu|
|/user| Dodanie nowego użytkownika | Aktualizacja danych bieżącego użytkownika| Pobranie listy użytkowników | Usunięcie grupy użytkowników
|/user/{id}| Brak | Aktualizacja danych użytkownika | Pobranie danych użytkownika | Usunięcie użytkownika
|/login| Loguje użytkownika, generuje ważny 14 dni token|Błąd |Błąd | Błąd
|/user/{id}/rental| Dodanie wypożyczenia przez użytkownika | Błąd | Pobranie wypożyczeń użytkownika| Zwrócenie przedmiotu 
|/rental| Dodanie nowego wypożyczenia | Błąd | Pobranie listy wypożyczeń | Zakończenie grupy wypożyczeń
|/rental/{id}| Błąd| Błąd | Pobranie informacji o wypożyczeniu | Zakończenie wypożyczenia
|/archive| Błąd| Błąd | Pobranie historii wypożyczeń | Usunięcie grupy wypożyczeń |
|/archive/{id}| Błąd| Błąd | Pobranie informacji o wypożyczeniu | Usunięcie wypożyczenia 

### Definicje ENUM

Sortowanie (sort)

| Wartość| Opis   |
|--------|--------|
|nameAsc |według nazwy, rosnąco|
|nameDesc |według nazwy, malejąco|
|quantityAsc |według ilości, rosnąco|
|quantityDesc |według ilości, malejąco|

### Punkty końcowe dla pracownika

#### Pobranie informacji o przedmiocie 

Zwraca informacje wyświetlane w widoku przedmiotu. Jest dostępne dla pracownika, magazyniera oraz administratora.

```
GET /item/{id}
```
|Nazwa|Typ|Wymagany|Opis|
|-----|---|--------|----|
|byCode|bool|nie|jeżeli true w uri zamiast zmiennej id powinien znaleźć się numer kodu kreskowego|



Odpowiedź

HTTP `200`

```javascript
{
	"id":1234,               //id przedmiotu
	"name":"Wiertarka Bosch" //nazwa przedmiotu
	"description":"Wiert..." //opis
	"isReturnable":false,    //czy przedmiot jest zwrotny
	"quantity":5,            //ilosc
	"timeLimit":48,          //dozwolony czas wypozyczenia w godzinach
	"criticalQuantity":2.    //ilość krytyczna
	"category":              //kategoria
	{
		"id":5,               //id kategorii
		"name":"ubrania"      //nazwa kategorii
	}
	"allowedGroups:          //dozwolone grupy
	[
		{
			"id":2,                    //id grupy
			"name":"Dział marketingu"  //nazwa grupy
		}
	]
}
```

#### Pobieranie listy przedmiotów

Jest dostępne dla pracownika magazyniera oraz administratora.

```
GET /item 
```

Parametry

|Nazwa|Typ|Wymagany|Opis|
|-----|---|--------|----|
|name |string|nie|do filtrowania, może być niepełna |
|isReturnable |bool|nie|do filtrowania|
|category |bool|nie|do filtrowania|
|isRent |bool|nie|do filtrowania|
|sort|string|nie|domyslnie "nameUp"|

Odpowiedź

HTTP `200`

```javascript
[
	{
		"id":1234,               //id przedmiotu
		"name":"Wiertarka Bosch" //nazwa przedmiotu
		"isReturnable":false,    //czy przedmiot jest zwrotny
		"quantity":5,            //ilosc
		"rentalTime":48,         //czas w godzinach
		"criticalQuantity":2.    //ilość krytyczna
	}
]
```
#### Aktualizacja danych użytkownika

Jest dostępna dla pracownika, magazyniera i administratora. Resztę atrybutów można zaktualizować z poziomu administratora poprzez endpoint /user/{id}
```
PUT /user
```

Parametry

|Nazwa|Typ|Wymagany|Opis|
|-----|---|--------|----|
|email|string|tak||
|password|string|tak||

Odpowiedź

HTTP `200`

#### Wypożyczanie przedmiotu

```
POST /user/{id}/rental 
```

Zapytanie jest **tablicą obiektów** z poniższymi parametrami.

Parametry

|Nazwa|Typ|Wymagany|Opis|
|-----|---|--------|----|
|code|int|tak|jest to numer kodu kreskowego przedmiotu|
|quantity|int|nie|jeśli bezzwrotny to podawana jest ilość|

Odpowiedź

HTTP `201`

```
{
	"id":1234   //id wypożyczenia
}
```

#### Zwracanie przedmiotu

```
DELETE /user/{id}/rental 
```

Zapytanie jest **tablicą obiektów** z poniższymi parametrami.

Parametry

|Nazwa|Typ|Wymagany|Opis|
|-----|---|--------|----|
|code|int|tak|jest to numer kodu kreskowego przedmiotu|

Odpowiedź

HTTP `204`

#### Pobranie listy wypożyczonych przedmiotów

```
GET /user/{id}/rental 
```


Odpowiedź

HTTP `201`

```javascript
[
	{
		"id":1234,               //id przedmiotu
		"name":"Wiertarka Bosch" //nazwa przedmiotu
		"timeToReturn":48,       //czas do zwrotu
	}
]
```


#### Logowanie uzytkownika

```
POST /login
```
Parametry

|Nazwa|Typ|Wymagany|Opis|
|-----|---|--------|----|
|username|string|tak|email użytkownika|
|password|string|tak|hash hasła użytkownika|

Algorytm hashujący do wybrania


Odpowiedź

HTTP `200`

```javascript
[
	{
		"token": "Bearer eyJhbG..." //token, który będzie używany do weryfikacji
	}
]
```

#### Wylogowanie użytkownika

Aby wylogować użytkownika należy usunąć token. 

--


###Punkty końcowe dla magazyniera


#### Dodawanie nowego przedmiotu

```
POST /item 
```

Parametry

|Nazwa|Typ|Wymagany|Opis|
|-----|---|--------|----|
|name |string|tak||
|category |int|tak||
|isReturnable| bool|tak|
|description|string|tak||
|crticalQuantity|int|tak||
|timeLimit|string|tak||
|allowedGroups|string|tak||

Odpowiedź

HTTP `201`

```
{
	"id":1234   //id dodanego przedmiotu
}
```

#### Aktualizacja grupy przedmiotów

```
PUT /item 
```

#####Opcjonalnie dodane zostanie zapytanie PATCH, ktore nie wymaga wszystkich parametrow.

Zapytanie jest **tablicą obiektów** z poniższymi parametrami.

Parametry

|Nazwa|Typ|Wymagany|Opis|
|-----|---|--------|----|
|id |string|tak||
|name |string|tak||
|category |string|tak||
|description|string|tak||
|crticalQuantity|int|tak||
|timeLimit|string|tak||
|allowedGroups|string|tak||

Odpowiedź

HTTP `200`


#### Usunięcie grupy przedmiotów
```
DELETE /item 
```
Zapytanie jest **tablicą obiektów** z poniższymi parametrami.

Parametry

|Nazwa|Typ|Wymagany|Opis|
|-----|---|--------|----|
| id  |int| tak    |    |

Odpowiedź

HTTP `204`

#### Aktualizacja przedmiotu

```
PUT /item/{id}
```

Parametry

|Nazwa|Typ|Wymagany|Opis|
|-----|---|--------|----|
|name |string|tak||
|category |string|tak||
|description|string|tak||
|crticalQuantity|int|tak||
|timeLimit|string|tak||
|allowedGroups|string|tak||

Odpowiedź

HTTP `200`


#### Usuwanie przedmiotu

```
DELETE /item/{id}
```

Odpowiedź

HTTP `204`

#### Dodawanie nowego użytkownika

```
POST /user
```


Parametry

|Nazwa|Typ|Wymagany|Opis|
|-----|---|--------|----|
|name |string|tak||
|surname |int|tak||
|isReturnable| bool|tak|
|description|string|tak||
|crticalQuantity|int|tak||
|timeLimit|string|tak||
|allowedGroups|string|tak||

Odpowiedź

HTTP `201`

```
{
	"id":1234   //id dodanego użytkownika
}
```


