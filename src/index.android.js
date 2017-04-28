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
    console.log('BackgroundTask');
    const fn = async () => { task(); };

    AppRegistry.registerHeadlessTask(JOB_KEY, () => fn);

    DeviceEventEmitter.addListener("RNBackgroundJob", task);
    console.log(fn);
    // AppState.getCurrentAppState(
    //   (something) => {
    //     console.log(something);
    //   },
    //   () => { console.error('Cant get AppState'); }
    // );
    console.log('waiting for response');

    // if (existingJob) {
    //   jobs[jobKey].registered = true;
    // } else {
    //   const scheduledJob = nativeJobs.filter(nJob => nJob.jobKey == jobKey);
    //   const scheduled = scheduledJob[0] != undefined;
    //   jobs[jobKey] = { registered: true, scheduled, job };
    // }

    const response = await BackgroundService.register(task);

    console.log(response);
  },

  cancel: () => {},

  finish: () => {},
};

// AppRegistry.registerHeadlessTask('BackgroundTask', () =>
//   require('./SomeTaskName')
// );

export default BackgroundTask;
