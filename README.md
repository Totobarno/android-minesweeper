# Android aknakereső alkalmazás

## Bemutatás

Az alkalmazás egy aknakereső játék. A játék célja, hogy megtaláljuk az aknákat egy négyzetrácsos játéktéren, amiben számok segítenek.
A felfedett számok azt mondják meg mennyi akna van a velük szomszédos mezőkben. A játékosnak lehtősége van megjelölni az aknának vélt mezőket.
A játék akkor ér véget, ha a játékos megjelöli az összes aknát, vagy rányom egy aknára.
A játékban 3 nehézségi szint van könnyű, 9x9-es tábla 10 aknával, közepes, 16x16-os tábla 40 aknával és nehéz, 30x16-os tábla 99 aknával.

## Az alkalmazás működése

Az alkalmazás használatához internetelérés szükséges. Belépéskor egy bejelentkező képernyő fogadja a felhasználót. A játékba egy email, jelszó párossal lehet belépni. 
Ha a felhasználónak nincs még fiókja, a "Create an account" gombra nyomva egy regisztrációs felületre kerül, ahol létre lehet hozni egy fiókot.

A főmenüben a "New Game" gombra nyomva megjelenik egy újabb menü, 
ahol ki lehet választani az új játék nehézségi szintjét. A nehézségi szint kiválasztása után elindul a játék. A "Load Game" gombra nyomva lehet betölteni egy korábban elmentett játékot. 
Ha nincs mentett játék, akkor elindul egy új játék könnyű fokozaton. A "Leaderboard" gombra nyomva megjelenik a nehézségi szintet kiválasztó menü, 
ahol a nehézség kiválasztása után megjelenik az adott szinthez tartozó dicsőséglista. 
A "Settings" gombot kiválasztva be lehet állítani az alkalmazás színeit.

Ha a játék elindult megjelenik a játéktér. Ha a játékos röviden rányom egy négyzetre, akkor az kinyílik és láthatóvá válik a benne található szám. Ha a négyzetben 0-ás szám szerepel, 
akkor az összes szomszédos mező kinyílik, amíg nem lesz egy 0-ától különböző szám. Ha a mező egy aknát tartalmaz, akkor a játék véget ér és felfedésre kerül az összes akna, amit 9-es szám jelöl. 
Ha a játékos hosszan nyomja meg az egyik négyzetet, akkor az meg lesz jelölve. A megjelölt mezőket F betű jelöli, ekkor a négyzetet nem lehet kinyitni. A jelölés levételéhez újra hosszan kell megnyomni a jelölt mezőt. 
A képernyő tetején egy számláló mutatja mennyi akna van még. Kilépni a bal felső sarokban található gombbal lehet, akkor kerül elmentésre a játék, ha ezzel lép ki a felhasználó. A négyzetrácsot lehet mozgatni, és benne zoom-olni.

Ha a játék valamilyen módon véget ér, akkor egy dialógus ablak ugrik fel. Ebben az ablakban látható az elért pontszám és a felhasználó itt adhatja meg a nevét. 
Minden helyesen megjelölt akna +1 pont és minden helytelen jelölés -1 pont. A "Share score" gombra nyomva a játékos megoszthatja az elért eredményt szövegként. 
A megosztás tartalmazza az elért pontszámot, a megadott nevet és a teljesítés dátumát. A "Save score" gombra nyomva a felhasználó elmentheti az eredményt a dicsőséglistába, ezután eltünik a dialógus ablak. 
Ha a játékos nem akarja elmenteni az eredményt a "Dismiss" gombra nyomva lehet eltűntetni a dialógus ablakot. A "Return to Main Menu" gombbal lehet visszatérni a főmenübe.

A dicsőséglistában egy elemben megjelenik a megadott név, a megszerzett pont, valamint a teljesítés dátuma. A lista alapértelmezett módon a pontok alapján csökkenő sorrendben jelenik meg. 
A játékosnak lehetősége van a képernyő alján található gombokkal rendezni a listát. A "name" gomb a név szerint abc sorrendben rendez, a "score" gomb a pontok alapján csökkenő sorrendben, 
míg a "date" gomb időrendi sorrendben jeleníti meg az elemeket a legrégebbitől kezdve. A bal felső sarokban található gombbal a nehézségi szintet kiválasztó menübe lehet visszajutni.

A beállítások menüben 3 színt lehet beállítani. A "Button color" gombbal a gombok és a fejléc színét, a "Background color" gombbal a háttér színét, a "Text color" gombbal a szöveg színét lehet állítani. 
Mindhárom esetben egy dialógus ablak jelenik meg. A kör alakú ábrán lehet kiválasztani a színt, az első sávon a szín átlátszóságát lehet beállítani, a második sávon a szín árnyalatát lehet állítani. 
Az ablakban a "Confirm" gombra nyomva lehet a változtatást elfogadni, vagy a "Dismiss" gombra nyomva lehet elutasítani. 
Az eredeti színeket a "Default color" gombra nyomva lehet visszaállítani.