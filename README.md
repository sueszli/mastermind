## Feedback

Die gegebene Formulierung des Beispiels ist lobenswert, klar und verständlich. Es ermöglicht auch AnfängerInnen ein
einfaches Verständnis der Aufgabenstellung.

Die Schwierigkeit des Beispiels ist insgesamt angemessen, obwohl die Implementierung mehr Zeit erfordert, als es auf den
ersten Blick erscheinen mag.

Deshalb denke ich ist die Aufgabenstellung sehr _zeitaufwendig_ aber nicht sehr _kompliziert_.

<br>

### Besondere Herausforderungen

Eine große Herausforderung bestand für mich beispielsweise darin, den richtigen Pfad für die `back_button.png`-Datei
anzugeben:

```
var path="./Aufgabenblatt6/src/back_button.png";
Image img=Image.fromFile(path);
cd.drawImage(xPos,rectHeight*COLORS.length,rectWidth,rectHeight,img);
```

Es hat außerdem eine beträchtliche Zeit gedauert, um den Randfall zu verstehen, bei dem der Hinweis aus der vorherigen
Runde nicht verschwinden sollte, wenn in der aktuellen Runde das erste Element gelöscht wird. - Möglicherweise ist das
aber etwas zu spezifisch für eine allgemeine Bewertung der Aufgabenstellung.

<br>

### Verbesserungsvorschläge

Im Hinblick auf Verbesserungsvorschläge könnte die Aufgabenstellung beispielsweise das Wort "Komplettierung" durch "
Vervollständigung" ersetzen, um eine eindeutigere Beschreibung zu liefern - insbesondere für nicht-muttersprachliche
ProgrammiererInnen. Man könnte allgemein eine leichtere Sprache verwenden, um die Aufgabenstellung zu vereinfachen.

Darüber hinaus wäre es noch besser, die Spielreihenfolge von oben nach unten und von links nach rechts vorzugeben, um
dem natürlichen Lesefluss zu folgen (wie in meiner Lösung umgesetzt).

Weiters habe ich habe in meiner Lösung alles umschrieben, da laut Angabe nur Manipulation der Dateinamen und der
Ordnerstruktur in derAngabe verboten war. Ich bin mir aber unsicher, ob ich das wirklich durfte. Dies könnte in der
Aufgabenstellung deutlicher herausgestellt werden.

Eine weitere mögliche Verbesserung betrifft die Darstellung der `COLORS` mit ganzen Zahlen, die sogar über die
Array-Indizes hinausgehen. Hier könnte man entweder ein `enum` verwenden oder den Wert -1 als "ungültige Farbe"
definieren. In diesem Fall würde auch kein `switch`-Statement benötigt werden, um die Zahl in eine Farbe umzuwandeln.
Eine klarere Beschreibung könnte wie folgt lauten:

> "Der Wert -1 gibt an, dass die spielende Person noch keine Farbe ausgewählt hat. Die anderen Werte entsprechen den
> Indizes der ausgewählten Farben im Array COLORS, wobei 0 zum ersten Index gehört."

Es ist empfehlenswert, globale Variablen, die nicht immutable sind, zu vermeiden, da dies die Nachverfolgung von
Seiteneffekten erschwert. Stattdessen sollten die Variablen bei jedem Methodenaufruf als Parameter übergeben werden.

Schließlich wäre es wünschenswert, die Anzahl der Verzweigungen in der Game Loop zu minimieren. Wie im Linux Kernel
Coding Style Guide empfohlen wird: "if you need more than 3 levels of indentation, you’re screwed anyway, and should fix
your program." Dies kann zu einer klareren und wartungsfreundlicheren Codebasis führen.
