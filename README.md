# Organizer-App 📅

Die Organizer-App ist eine in Java entwickelte Desktop-Anwendung zur Verwaltung von Aufgaben. Sie basiert auf einem modularen Aufbau, verwendet Gradle als Build-System und ist darauf ausgelegt, später auf Android portiert werden zu können.

---

## 🔧 Funktionen

- ✅ Aufgabenverwaltung mit Titel, Fälligkeitsdatum, Priorität, Erstellungsdatum
- 📥 Import & 📤 Export von Aufgaben im CSV-Format
- 🔃 Sortierfunktionen nach:
  - Priorität
  - Fälligkeitsdatum
  - Titel (alphabetisch)
  - Erstellungsdatum (neueste zuerst)
- 🧩 Gut strukturierter Code nach MVC-Prinzipien
- 🛠️ Vollständig Gradle-konfiguriert

---

## 🗂️ Projektstruktur

```
Organizer_Application/
├── build.gradle            # Gradle Build-Skript
├── settings.gradle         # Gradle Settings
├── .gitignore              # Git Ignore-Datei
├── README.md               # Projektbeschreibung
└── src/
    └── main/
        ├── java/
        │   └── organizerapp/
        │       ├── main/              # Einstiegspunkt (Main.java)
        │       ├── model/             # Datenmodell (Task.java)
        │       ├── util/              # Hilfsklassen (Priority, IDGenerator)
        │       ├── io/                # Dateiimport/-export (TaskCSVHandler)
        │       └── service/           # Sortierlogik (ArrangeTaskList)
        └── resources/                 # (optional)
```

---

## 🚀 Starten

### Voraussetzungen

- Java 17 oder neuer
- Gradle (lokal oder über Wrapper)

### Kompilieren & Ausführen

```bash
# Im Projektverzeichnis
./gradlew build
./gradlew run
```

---

## 📁 Beispiel: CSV-Dateiformat

```csv
1;Einkaufen;2025-08-15;HIGH;2025-07-28
2;Arzttermin;2025-08-10;MEDIUM;2025-07-28
```

Format:
```
ID;Titel;Fälligkeitsdatum;Priorität;Erstellungsdatum
```

---

## ✏️ Weiterentwicklung

Geplant sind:
- GUI mit JavaFX oder Swing
- Android-Port mit Jetpack Compose
- Erinnerungsfunktionen
- Kategorisierung von Aufgaben

---

## 📜 Lizenz

MIT License – siehe [LICENSE](LICENSE)

---

## 👤 Autor

**Kai Augustin**  
GitHub: [Goldwurst](https://github.com/Goldwurst)

---