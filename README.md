# SpringBoot CRUD Aplikacija za Upravljanje Korisnicima

Ovaj projekt demonstrira CRUD (Create, Read, Update, Delete) aplikaciju koristeći Spring Boot i Thymeleaf za upravljanje korisničkim podacima. Obezbeđuje funkcionalnosti za listanje, dodavanje, brisanje i uređivanje korisnika. U nastavku su uputstva i delovi koda za postavljanje i korištenje ovih funkcionalnosti.

## Preduvijeti

- Java Development Kit (JDK) - verzija 1.8 ili novija
- Spring Boot
- Maven
- Thymeleaf
- Bootstrap (za stilizovanje)

## Dodavanje Korisnika

### Koraci:
1. **Kreiranje Controller-a za prikaz korisnika**:
   Kreiraj `UserController` u paketu `controllers` sa sledećim kodom:

   ```java
   package ba.sum.fsre.ednevnik.controllers;

   import ba.sum.fsre.ednevnik.models.User;
   import ba.sum.fsre.ednevnik.repositories.UserRepository;
   import org.springframework.ui.Model;
   import org.springframework.web.bind.annotation.GetMapping;
   import org.springframework.stereotype.Controller;
   import org.springframework.beans.factory.annotation.Autowired;

   @Controller
   public class UserController {

       @Autowired
       UserRepository userRepository;

       @GetMapping("/users")
       public String listUsers(Model model){
           List<User> users = userRepository.findAll();
           model.addAttribute("users", users);
           return "users/index";
       }
   }
   ```
U ovom kontroleru, metoda listUsers se poziva kada korisnik pristupi putanji `/users`. Metoda
koristi `UserRepository` za dohvaćanje liste svih korisnika iz baze podataka, a potom šalje te
podatke na prikaz u Thymeleaf predlošku `users/index`.
Dodatne Napomene:
- Anotacija `@Controller`: Označava klasu kao Spring MVC kontroler.
- Anotacija `@Autowired`: Spring će automatski injektirati UserRepository instancu, što
  omogućava pristup metodama za upravljanje korisničkim podacima.
- Model objekt: Koristi se za prenos podataka iz kontrolera u Thymeleaf šablon.
- `userRepository.findAll()`: Dohvaća sve korisnike iz baze podataka.
- `return "users/index"`: Određuje koji će Thymeleaf predložak biti prikazan kao
  odgovor. U ovom slučaju, to je predložak smješten u
  `src/main/resources/templates/users/index.html`.
  Nakon što napravite `UserController`, sljedeći korak je stvaranje odgovarajućeg Thymeleaf
  predloška za prikaz liste korisnika. Ovaj predložak treba biti kreiran u
  `src/main/resources/templates/users/index.html` i trebao bi koristiti Bootstrap ili drugi
  CSS okvir za oblikovanje tablice s korisnicima.
2. **Izgradnja Thymeleaf predloška:**
Predložak za listanje korisnika `index.html` lociran u `src/main/resources/templates/users/index.html` koristi Bootstrap za oblikovanje tablice s korisničkim podacima.
    ```html
    <!DOCTYPE html>
    <html xmlns:th="http://www.thymeleaf.org">
        <head>
            <title>Lista Korisnika</title>
            <link rel="stylesheet"
    href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.cs
    s">
        </head>
        <body>
            <div class="container mt-5">
            <h2>Lista Korisnika</h2>
            <table class="table">
                <thead>
                    <tr>
                        <th>Ime</th>
                        <th>Prezime</th>
                        <th>Email</th>
                        <!-- Dodajte dodatne stupce ako je potrebno -->
                    </tr>
                </thead>
                <tbody>
                    <tr th:each="user : ${users}">
                        <td th:text="${user.ime}">Ime</td>
                        <td th:text="${user.prezime}">Prezime</td>
                        <td th:text="${user.email}">Email</td>
                        <!-- Dodajte dodatne ćelije ako je potrebno -->
                    </tr>
                </tbody>
            </table>
            </div>
        </body>
    </html>
    ```
3. **Funkcionalnost dodavanja novih korisnika**
Da biste omogućili dodavanje novih korisnika u vašu Spring Boot aplikaciju, potrebno je
implementirati nekoliko ključnih komponenata: kontroler za obradu zahtjeva, servis za
interakciju s bazom podataka, Thymeleaf formu za unos podataka, te naravno, ažuriranje sučelja.
Da biste implementirali funkcionalnost za dodavanje novih korisnika u vašu Spring Boot
aplikaciju, slijedite ove korake:
- Dodaj HTML formu u `src/main/resources/templates/users/add.html` za unos novih korisnika. Koristi sledeći HTML kod:
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
   <head>
      <title>Dodaj Novog Korisnika</title>
      <link rel="stylesheet"
         href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.cs
         s">
   </head>
   <body>
      <div class="container mt-5">
         <h2>Dodaj Novog Korisnika</h2>
         <form th:action="@{/users/add}" th:object="${user}" method="post">
            <div class="form-group">
               <label for="ime">Ime:</label>
               <input type="text" th:field="*{ime}" id="ime" class="form-
                  control" required th:errorclass="is-invalid"/>
               <div class="invalid-feedback" th:if="${#fields.hasErrors('ime')}"
                  th:errors="*{ime}"></div>
            </div>
            <div class="form-group">
               <label for="prezime">Prezime:</label>
               <input type="text" th:field="*{prezime}" id="prezime"
                  class="form-control" required th:errorclass="is-invalid"/>
               <div class="invalid-feedback"
                  th:if="${#fields.hasErrors('prezime')}" th:errors="*{prezime}"></div>
            </div>
            <div class="form-group">
               <label for="email">Email:</label>
               <input type="email" th:field="*{email}" id="email" class="form-
                  control" required th:errorclass="is-invalid"/>
               <div class="invalid-feedback"
                  th:if="${#fields.hasErrors('email')}" th:errors="*{email}"></div>
            </div>
            <div class="form-group">
               <label for="lozinka">Lozinka:</label>
               <input type="password" th:field="*{lozinka}" id="lozinka"
                  class="form-control" required th:errorclass="is-invalid"/>
               <div class="invalid-feedback"
                  th:if="${#fields.hasErrors('lozinka')}" th:errors="*{lozinka}"></div>
            </div>
            <div class="form-group">
               <label for="potvrdaLozinke">Potvrdite Lozinku:</label>
               <input type="password" th:field="*{potvrdaLozinke}"
                  id="potvrdaLozinke" class="form-control" required th:errorclass="is-
                  invalid"/>
               <div class="invalid-feedback"
                  th:if="${#fields.hasErrors('potvrdaLozinke')}"
                  th:errors="*{potvrdaLozinke}"></div>
            </div>
            <button type="submit" class="btn btn-primary">Dodaj
            Korisnika</button>
         </form>
      </div>
   </body>
</html>
```

U ovoj formi, korišteni su Thymeleaf atributi (`th:field`) kako bi se povezali inputi forme s poljima vašeg User modela. Također, dodana je validacija za svako polje koristeći `th:errorclass` i `th:errors` kako bi se prikazale poruke o greškama koje ste definirali u  modelu.
- Polja ime, prezime, i email su povezana s odgovarajućim poljima modela.
- Dodani su inputi lozinka i potvrdaLozinke za unos lozinke i njene potvrde.
- Thymeleaf prikazuje odgovarajuće poruke o greškama ako unos ne zadovoljava validacijske kriterije definirane u modelu.

Da biste dodali funkcionalnost za dodavanje novog korisnika u vašoj Spring Boot aplikaciji,
potrebno je ažurirati `UserController` s dvije metode. Ove metode će upravljati prikazivanjem
obrasca za dodavanje korisnika i obradom podataka koji se unose u obrazac. Evo detaljnijeg
objašnjenja: Dodajte metodu u `UserController` koja će prikazivati formu za dodavanje novog
korisnika, kao i metodu za obradu POST zahtjeva te forme.

```java
    // U klasi UserController
    @GetMapping("/users/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "users/add";
    }
    @PostMapping("/users/add")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()){
            model.addAttribute("user", user);
            return "users/add";
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String passwordEncoded = encoder.encode(user.getLozinka());
            user.setLozinka(passwordEncoded);
            user.setPotvrdaLozinke(passwordEncoded);
            userRepository.save(user);
            return "redirect:/users";
        }
    }
```
U metodi `showAddUserForm`, koristi se `Model` objekt kako bi se proslijedio prazan `User` objekt na formu za unos novog korisnika. Metoda `addUser` se poziva kada korisnik pošalje formu za unos novog korisnika. U ovoj metodi, koristi se `@Valid` anotacija kako bi se provjerilo da li su uneseni podaci ispravni. Ako su podaci ispravni, korisnik se sprema u bazu podataka koristeći `userRepository.save(user)`, a zatim se korisnik preusmjerava na listu korisnika. U suprotnom, korisnik se vraća na formu za unos novog korisnika kako bi ispravio greške.
U vašem index.html predlošku (ili gdje god želite), dodajte link ili gumb koji vodi na formu za
dodavanje novog korisnika:
```html
<a href="/users/add" class="btn btn-primary">Dodaj Novog Korisnika</a>
```
4. **Funkcionalnost brisanja korisnika**

Da biste omogućili brisanje korisnika iz vaše Spring Boot aplikacije, potrebno je implementirati
nekoliko ključnih komponenata: kontroler za obradu zahtjeva, servis za interakciju s bazom
podataka, te naravno, ažuriranje sučelja.

Dodajte metodu u UserController koja će upravljati brisanjem korisnika:
```java
    // U klasi UserController
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        return "redirect:/users";
    }
```

U ovoj metodi, koristi se `@PathVariable` anotacija kako bi se dohvatila vrijednost ID-a korisnika koji se želi obrisati. Zatim se korisnik dohvaća iz baze podataka koristeći `userRepository.findById(id)`, a zat
im se briše iz baze podataka koristeći `userRepository.delete(user)`. Na kraju, korisnik se preusmjerava na listu korisnika.

U vašem index.html predlošku (ili gdje god želite), dodajte link ili gumb koji će omogućiti brisanje korisnika:
```html
<tr th:each="user : ${users}">
    <td th:text="${user.ime}">Ime</td>
    <td th:text="${user.prezime}">Prezime</td>
    <td th:text="${user.email}">Email</td>
    <!-- Dodajte dodatne ćelije ako je potrebno -->
    <td>
        <form th:action="@{/users/delete/{userId}(userId=${user.id})}" method="post">
            <button type="submit" class="btn btn-danger">Obriši</button>
        </form>
    </td>
</tr
```
Ovdje se koristi Thymeleaf th:action za dinamičko generiranje URL-a za brisanje koji
sadrži ID korisnika. Gumb "Obriši" unutar <form> taga šalje POST zahtjev na server za brisanje određenog
korisnika.

5. **Funkcionalnost uređivanja korisnika**

Da biste omogućili uređivanje korisnika u vašoj Spring Boot aplikaciji, potrebno je implementirati
nekoliko ključnih komponenata: kontroler za obradu zahtjeva, servis za interakciju s bazom
podataka, te naravno, ažuriranje sučelja.

Dodajte metode u UserController koje će upravljati prikazivanjem obrasca za uređivanje i
obradom podataka iz obrasca:

```java
    // U klasi UserController
    @GetMapping("/users/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Neispravan ID korisnika:" + id));
        model.addAttribute("user", user);
        return "users/update";
    }
    @PostMapping("/users/edit/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "users/update";
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String passwordEncoded = encoder.encode(user.getLozinka());
        user.setLozinka(passwordEncoded);
        user.setPotvrdaLozinke(passwordEncoded);
        userRepository.save(user);
        return "redirect:/users";
    }
```

Napravite Thymeleaf predložak edit.html unutar src/main/resources/templates/users
sličan formi za dodavanje, ali s popunjenim podacima korisnika:

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
   <head>
      <title>Uredi Korisnika</title>
      <link rel="stylesheet"
         href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.cs
         s">
   </head>
   <body>
      <div class="container mt-5">
         <h2>Uredi Korisnika</h2>
         <form th:action=" @{/users/edit/{userId}(userId=${user.id})}
         th:object="${user}" method="post">
         <div class="form-group">
            <label for="ime">Ime:</label>
            <input type="text" th:field="*{ime}" id="ime" class="form-control"
               required th:errorclass="is-invalid"/>
            <div class="invalid-feedback" th:if="${#fields.hasErrors('ime')}"
               th:errors="*{ime}"></div>
         </div>
         <div class="form-group">
            <label for="prezime">Prezime:</label>
            <input type="text" th:field="*{prezime}" id="prezime" class="form-
               control" required th:errorclass="is-invalid"/>
            <div class="invalid-feedback" th:if="${#fields.hasErrors('prezime')}"
               th:errors="*{prezime}"></div>
         </div>
         <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" th:field="*{email}" id="email" class="form-control"
               required th:errorclass="is-invalid"/>
            <div class="invalid-feedback" th:if="${#fields.hasErrors('email')}"
               th:errors="*{email}"></div>
         </div>
         <div class="form-group">
            <label for="lozinka">Lozinka:</label>
            <input type="password" th:field="*{lozinka}" id="lozinka" class="form-
               control" required th:errorclass="is-invalid"/>
            <div class="invalid-feedback" th:if="${#fields.hasErrors('lozinka')}"
               th:errors="*{lozinka}"></div>
         </div>
         <div class="form-group">
            <label for="potvrdaLozinke">Potvrdite Lozinku:</label>
            <input type="password" th:field="*{potvrdaLozinke}" id="potvrdaLozinke"
               class="form-control" th:errorclass="is-invalid"/>
            <div class="invalid-feedback"
               th:if="${#fields.hasErrors('potvrdaLozinke')}"
               th:errors="*{potvrdaLozinke}"></div>
         </div>
         <button type="submit" class="btn btn-primary">Spremi</button>
         </form>
      </div>
   </body>
</html>
```
U Thymeleaf predlošku koji prikazuje listu korisnika, dodajte link ili gumb za uređivanje pored
svakog korisnika:
```html
<tr th:each="user : ${users}">
    <td th:text="${user.ime}">Ime</td>
    <td th:text="${user.prezime}">Prezime</td>
    <td th:text="${user.email}">Email</td>
    <!-- Dodajte dodatne ćelije ako je potrebno -->
    <td>
        <a th:href="@{/users/edit/{userId}(userId=${user.id})}" class="btn btn-
            primary">Uredi</a>
    </td>
</tr>
```
Ovdje se koristi Thymeleaf th:href za dinamičko generiranje URL-a za uređivanje koji sadrži ID korisnika. Link "Uredi" vodi na formu za uređivanje određenog korisnika.