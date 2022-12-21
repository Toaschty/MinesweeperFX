# MineSweeperFX
## Verwendung der Anwendung

#### Start-Bildschirm
* Size: Hier kann die Größe des Feldes angegeben werden
  * Small: Kleines Feld
  * Middle: Mittleres Feld
  * Large: Großes Feld
  * Custom: Benutzerdefinierte Größe des Feldes
    * Weitere Felder erscheinen, in denen die Größe eingetragen werden kann
* Difficulty: Hier kann die Schwierigkeit des Spiels eingestellt werden
  * Easy: Einfachste Schwierigkeit
  * Normal: Normale Schwierigkeit
  * Hard: Schwerste Schwierigkeit
* START: Mit dem Drücken dieses Knopfes startet das Spiel
* _"duckon"_: Aktiviert die Enten
* _"duckoff"_: Deaktiviert die Enten

#### Spiel-Bildschirm
* Informationen in der oberen Leiste:
  * Größe und Schwierigkeit des derzeitigen Spiels
  * Zeit, die seit dem Start der derzeitigen Runde vergangen ist
  * Anzahl markierter Felder mit der Anzahl aller vorhanden Bomben
* Spielregeln: Es wird nach den Regeln von "MineSweeper" gespielt
  * Im Spielbrett befinden sich x Bomben
  * Feld kann entweder leer sein, eine Zahl oder eine Bombe beinhalten
    * Zahl gibt an, wie viele Bomben sich in den direkt angrenzenden (auch diagonalen) Feldern befinden
    * Wird eine Bombe aufgedeckt, ist das Spiel verloren
  * Ziel des Spiels ist, alle Felder aufgedeckt und alle Bomben markiert zu haben
  * Aufdecken / Markieren:
    * Linksklick: Decke das Feld auf
    * Rechtsklick: Markiere das Feld als Bombe ( oder als ? ). Erneutes Klicken löscht die Markierung

#### End-Bildschirm
Ist das Spiel beendet, wird diese Anzeige angezeigt:
* Je nach Spiel: "You win!" oder "You loose!"
* Die gespielte Zeit
* Knopf, mit diesem man ins Start-Menu geschickt wird
