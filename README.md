# DecorationShop

Egy kis segítség mit merre találsz meg.

## Adatmodell, activity, intent
Product.java tartalmazza

Activityk száma 3 felett van

Intentek segítségével minden oldal elérhető

## Firebase regisztráció és login
Regisztráció szükséges az árufeltöltéshez, illetve a vásárláshoz is
    [RegistrationActivity]

ProductsActivity és AddProductsActivity csak bejelentkezve érhető el

## GUI
Beviteli mezők értéke mindenhol beállítva

strings.xml-be kivannak szervezve a szövegek

ConstraintLayoutra példa: activity_main.xml

LinearLayoutra példa: activity_main.xml (landscape)

Reszponzív layout, elforgatás esetén is "igényes" marad

## Lifecycle Hook
Ha kitöltjük a bejelentkezés alatt az e-mail cím, majd rákattintunk a regisztrációra, akkor ott is megjelenik az e-mail cím
    [MainActivity --> onPause]

## Animációk, értesítés
(1) Termékek listázása esetén a nem láthatóak balról beúsznak
    [ProductAdapter 48-52 sor]

(2) Ha egy termék elfogy, akkor jobbra kiúszik
    [ProductAdapter 96-98. sor]

Új termék hozzáadása esetén az alkalmazás értesítést küld arról
    [NotificationHandler, AddProductsActivity 86sor]

## CRUD műveletek, lekérdezések
Create: AddProductsActivity --> addProduct()

Read: ProductsActivity -> listAll()
    összetett lekérdezés: a termékek név szerint vannak rendezve

Update: ProductAdapter -> update()

Delete: ProductAdapter -> delete()