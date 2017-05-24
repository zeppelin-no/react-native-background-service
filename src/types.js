// @flow

type Periods = 'INTERVAL_DAY' | 'INTERVAL_FIFTEEN_MINUTES' | 'INTERVAL_HALF_DAY' | 'INTERVAL_HALF_HOUR' | 'INTERVAL_HOUR';

export type RegisterOptions = {
  period?: Periods,
}

export type BackgroundTaskInterface = {
  register: (task: () => void, options?: RegisterOptions) => void,
  cancel: () => void,
  finish: () => void,
}
