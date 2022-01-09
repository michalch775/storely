# API Smart-Record

- [API Smart-Record](#api-smart-record)
	- [Najważniejsze informacje](#najważniejsze-informacje)
	- [Kody odpowiedzi używane tym w API](#kody-odpowiedzi-używane-tym-w-api)
	- [Format danych](#format-danych)
	- [Zabezpieczenia](#zabezpieczenia)
	- [Punkty końcowe](#punkty-końcowe)
		- [Tabela poglądowa](#tabela-poglądowa)
		- [Definicje ENUM](#definicje-enum)
		- [Punkty końcowe dla pracownika](#punkty-końcowe-dla-pracownika)
			- [Pobranie informacji o przedmiocie](#pobranie-informacji-o-przedmiocie)
			- [Pobieranie listy przedmiotów](#pobieranie-listy-przedmiotów)
			- [Aktualizacja danych użytkownika](#aktualizacja-danych-użytkownika)
			- [Wypożyczanie przedmiotu](#wypożyczanie-przedmiotu)
			- [Zwracanie przedmiotu](#zwracanie-przedmiotu)
			- [Pobranie listy wypożyczonych przedmiotów](#pobranie-listy-wypożyczonych-przedmiotów)
			- [Logowanie uzytkownika](#logowanie-uzytkownika)
			- [Wylogowanie użytkownika](#wylogowanie-użytkownika)
		- [Punkty końcowe dla magazyniera](#punkty-końcowe-dla-magazyniera)
			- [Dodawanie nowego przedmiotu](#dodawanie-nowego-przedmiotu)
			- [Aktualizacja grupy przedmiotów](#aktualizacja-grupy-przedmiotów)
			- [Usunięcie grupy przedmiotów](#usunięcie-grupy-przedmiotów)
			- [Aktualizacja przedmiotu](#aktualizacja-przedmiotu)
			- [Usuwanie przedmiotu](#usuwanie-przedmiotu)
			- [Dodawanie nowego użytkownika](#dodawanie-nowego-użytkownika)

## Najważniejsze informacje

* Wszystkie zapytania zwracają tablice lub obiekty JSON
* Podstawowe jednostki w API to: przedmiot `item`, wypożyczenie `rental`, użytkownik `user`.

## Kody odpowiedzi używane tym w API
Każda błędna odpowiedź zwraca wiadomość z informacja jaki konkrtnie błąd wystąpił

* HTTP `4XX` - używane dla błędnych zapytań. Błąd zawsze po stronie klienta
* HTTP `400` - niepoprawne zapytanie.
* HTTP `403` - autoryzacja nideudana.
* HTTP `404` - zasób `GET` nie istnieje.
* HTTP `409` - zasób `POST` już istnieje.
* HTTP `5XX` - błąd po stronie serwera.

## Format danych

* Dane w zapytaniach `GET` i `DELETE` powinny być wysłane w URI
* Dane w zapytaniach `POST` i `PUT` powinny być wysłane w `requestBody` w formacie JSON (`application/json`). Kolejność parametrów jest dowolna.

## Zabezpieczenia

* Każdy punkt końcowy jest zabezpieczony i wymaga autoryzacji tokenem.
* Różne punkty końcowe są dostępne dla użytkowników z różnymi uprawnieniami, zgodnie z podziałem punktów końcowych poniej.
* Token generuje się przez zapytanie do endpointu [/login](#logowanie-uzytkownika).
* Token ma być wysłany przez użytkownika w nagłówku `Authorization`.
* Token ma zawierać przedrostek `Bearer `, jest on z nim generowany.
* Wygenerowany token ma ważność 14 dni i może być odnawiany (odnawianie zostanie zaimplementowane w przyszłości).
* Aby wylogować się (zakończyć sesję) należy usunąć token z pamięci klienta. Tokeny nie są przechowywane w bazie danych, tylko sprawdzane na podstawie klucza (zgodnie ze standardem JWT).

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
|nameASC |według nazwy, rosnąco|
|nameDESC |według nazwy, malejąco|
|quantityASC |według ilości, rosnąco|
|quantityDESC |według ilości, malejąco|

Rola (role)

| Wartość| Opis   |
|--------|--------|
|EMPLOYEE|pracownik, użytkownik z tą rolą ma dostęp tylko do panelu pracownika|
|WAREHOUSEMAN|magazynier, ma dostęp do panelu magazyniera i pracownika (w aplikacji mobilnej)|
|ADMIN|administrator, ma dostęp do panelu pracownika, magazyniera i administratora|

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
|isReturnable |boolean|nie|do filtrowania|
|category | integer |nie|do filtrowania|
|isRent | boolean |nie|do filtrowania|
|sort|enum (string)|nie|domyslnie "nameUp"|

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

Jest dostępna dla pracownika, magazyniera i administratora. Resztę atrybutów można zaktualizować z poziomu administratora poprzez endpoint `/user/{id}`

```
PUT /user
```

Byc moze zostanie zmienione na `PATCH`

Parametry

|Nazwa|Typ|Wymagany|Opis|
|-----|---|--------|----|
|password|string|tak||
|newUsername|string|tak|jako username należy podać email użytkowinka|
|newPassword|string|tak||

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
[
	{
		"id":1234        //id wypożyczenia
		"item_id":1234   //id przedmiotu
	}
]
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

___

### Punkty końcowe dla magazyniera


#### Dodawanie nowego przedmiotu

```
POST /item 
```

Parametry

|Nazwa|Typ|Wymagany|Opis|
|-----|---|--------|----|
|code |long|tak||
|quantity |integer|tak||
|itemTemplateId |long|nie|id wzorca przedmiotu |
|itemTemplate|object|nie|należy zawrzeć jeśli nie zawarto itemTemplateId|

obiekt  `itemTemplate`

|Nazwa|Typ|Wymagany|Opis|
|-----|---|--------|----|
|name |string|tak||
|category |string|tak||
|description|string|nie||
|crticalQuantity|int|nie||
|timeLimit|string|nie|jesli brak to nielimitowany|
|allowedGroups|array of integers|nie|zawiera id dozwolonych grup|


Odpowiedź

HTTP `200`

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


