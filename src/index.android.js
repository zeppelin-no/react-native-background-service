import { AppRegistry, NativeModules, DeviceEventEmitter } from 'react-native';

import type { BackgroundTaskInterface } from './types';

const BackgroundService = NativeModules.BackgroundService;
// const { AppState, BackgroundJob: RNBackgroundJob } = NativeModules;
const JOB_KEY = 'BackgroundTask';

const backgroundConfig = {
  period: 9000, // 15 minutes
  timeout: 30,
};

const jobs = {};

// const BackgroundTask: BackgroundTaskInterface = {
  //
  //   register: (task, options = backgroundConfig) => {
  //     // Cancel any existing tasks, as we can only have one to match iOS, and we
  //     // have no way to tell whether the function has changed or not.
  //     RNBackgroundJob.cancelAll();
  //
  //     // Register the headless task
  //
  //     // Schedule it to run as a periodic task
  //     AppState.getCurrentAppState(
  //       ({ appState }) => {
  //         RNBackgroundJob.schedule(
  //           JOB_KEY,
  //           options.timeout,
  //           options.period,
  //           true, // persist after restart
  //           appState === 'active',
  //           RNBackgroundJob.ANY, // network type
  //           false, // requires charging
  //           false, // requires device idle
  //         );
  //       },
  //       () => { console.error('Cant get AppState'); }
  //     );
  //   },
  //
  //   cancel: () => {
  //     RNBackgroundJob.cancelAll();
  //   },
  //
  //   finish: () => {
  //     // Needed for iOS, no-op on Android
  //   },
// };

const BackgroundTask: BackgroundTaskInterface = {
  register: async (task) => {
    const fn = async () => { task(); };

    AppRegistry.registerHeadlessTask(JOB_KEY, () => fn);

    DeviceEventEmitter.addListener("RNBackgroundJob", task);

    const response = await BackgroundService.register(task);

    return response;
  },

  cancel: () => {},

  finish: () => {},
};

export default BackgroundTask;
