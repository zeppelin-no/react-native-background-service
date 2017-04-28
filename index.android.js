import { AppRegistry, NativeModules } from 'react-native';
import type { BackgroundTaskInterface } from './types';

const { AppState, BackgroundJob: RNBackgroundJob } = NativeModules;
const JOB_KEY = 'BackgroundTask';

const backgroundConfig = {
  period: 9000, // 15 minutes
  timeout: 30,
};

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
  register: (task) => {
    console.log('something');
    const fn = async () => { task(); };
    console.log(fn);
    AppState.getCurrentAppState(
      (something) => {
        console.log(something);
      },
      () => { console.error('Cant get AppState'); }
    );
    AppRegistry.registerHeadlessTask(JOB_KEY, () => fn);
  },

  cancel: () => {},

  finish: () => {},
};

// AppRegistry.registerHeadlessTask('BackgroundTask', () =>
//   require('./SomeTaskName')
// );

export default BackgroundTask;
