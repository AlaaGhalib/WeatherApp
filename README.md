# Weather Forecast App

Detta är en enkel Android-applikation som använder Jetpack Compose, Retrofit och Open-Meteo API för att hämta och visa väderdata baserat på användarens inmatade latitud och longitud. Applikationen stödjer offline-cache via SharedPreferences och hanterar nätverksanslutning på ett robust sätt.

## Funktioner

- Hämtar väderdata per timme från Open-Meteo API
- Användaren matar in latitud och longitud manuellt
- Visar temperatur och molntäcke per timme
- Offline stöd genom lokal cache (SharedPreferences)
- Enkel navigation mellan skärmar med Navigation Compose
- Kontroll av internetanslutning innan API-anrop

## Krav
- Android Studio
- Kotlin
- Jetpack Compose
- Retrofit2
- Gson

