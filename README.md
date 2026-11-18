# ⏱️ Wear OS Stopwatch (watch ↔ phone sync)

**A Wear OS stopwatch built with Compose for Wear OS that stays in sync with a companion phone app.**

Start, stop or reset on the watch and the command is sent to the phone through the Wearable Data Layer. Commands sent from the phone control the watch the same way.

![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=flat-square&logo=kotlin&logoColor=white)
![Wear OS](https://img.shields.io/badge/Wear_OS-API_30+-4285F4?style=flat-square&logo=wearos&logoColor=white)
![Compose](https://img.shields.io/badge/Compose_for_Wear_OS-4285F4?style=flat-square&logo=jetpackcompose&logoColor=white)

---

## ✨ Features

- Stopwatch UI built with **Compose for Wear OS**
- **Two-way sync** with the phone using the Wearable `MessageClient` on the `/sync` path
- Finds connected devices with `NodeClient` and sends messages to all of them
- State exposed with `StateFlow` from a `ViewModel`, updated every 100 ms with coroutines

## 🧩 How it works

```
Watch (this repo)                          Phone (companion app)
StopwatchScreen ─► StopwatchViewModel      
        │                                   
        └─► PhoneCommunicator ── MessageClient "/sync" ──► phone app
                    ▲                                         │
                    └──────────── commands from phone ◄───────┘
```

The phone side lives in [stopwatch_Mobile_connection_to_watch](https://github.com/Maignardi/stopwatch_Mobile_connection_to_watch).

## 🧰 Tech stack

Kotlin · Jetpack Compose for Wear OS · Coroutines + StateFlow · Google Play Services Wearable (`MessageClient`, `NodeClient`) · Wear Tiles

## 🚀 Getting started

1. Clone this repository and the phone app.
2. Run this project on a Wear OS emulator or watch (API 30+).
3. Run the phone app on a device paired with the watch.
4. Start the stopwatch on either side and watch the other one follow.

---

Made by [Matheus Maignardi](https://github.com/Maignardi)
