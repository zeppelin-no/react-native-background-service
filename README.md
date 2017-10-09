# react-native-background-service

Background service for android and iOS in react-native.
Uses `react-native-background-fetch` for iOS

## Options

| Option | Value | type | Note |
|-|-|-|-|
| period  | `INTERVAL_DAY`, `INTERVAL_HALF_DAY`, `INTERVAL_HALF_HOUR`, `INTERVAL_FIFTEEN_MINUTES`, `INTERVAL_HOUR`, `MANUAL` | string | one of |
| interval | ie. `5000` | int  | needs `MANUAL` as `period`, is in milliseconds |
