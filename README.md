# Formulierung & Schwierigkeitsgrad

Das Beispiel ist sehr klar und verständlich formuliert. Die Schwierigkeit ist angemessen.
Die eigentliche Herausforderung liegt darin mit den gegebenen Freiheiten umzugehen und eine gute Struktur zu finden.

<br>

# Vorschläge

- Ich habe alles umschrieben, da nur angefordert wurde, dass man die Dateinamen und die Ordnerstruktur nicht
  manipuliert.

- Wenn `COLORS` mit ganzen Zahlen dargestellt wird, die sogar über die Array-Indizien hinausgehen, dann würde
  ich sie entweder mit einem `enum` ersetzen oder -1 als "ungültige Farbe" definieren (in diesem Fall würde ich
  auch kein `switch` statement brauchen um die Zahl zur Farbe zu konvertieren).

  > "Der Wert 0 codiert, dass die spielende Person noch keine Farbe ausgewält hat, die anderen Werte entsprechen
  > den Indizes der ausgewählten Farben im Array COLORS plus eins"

- Globale Variablen die nicht immutable sind sollten vermieden werden, da man so Seiteneffekte schwer nachvollziehen
  kann. Stattdessen würde ich die Variablen mit jedem Methodenaufruf übergeben.

- Anzahl der Verzweigungen in Game Loop sollte minimiert werden. Oder wie es im Linux Kernel Coding Style Guide heißt:
  "if you need more than 3 levels of indentation, you’re screwed anyway, and should fix your program."

