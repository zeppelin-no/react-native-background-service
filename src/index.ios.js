import RNBackgroundFetch from 'react-native-background-fetch';

import type { BackgroundTaskInterface } from './types';

const backgroundConfig = {
  stopOnTerminate: false,
};

const BackgroundTask: BackgroundTaskInterface = {

  register: (task, options = backgroundConfig) => {
    // Cancel existing tasks
    RNBackgroundFetch.stop();

    // Configure the native module
    // Automatically calls RNBackgroundFetch#start
    RNBackgroundFetch.configure(
      options,
      task,
      () => { console.error('Device doesnt support Background Fetch'); },
    );
  },

  status: () => {
    return new Promise((resolve) => {
      RNBackgroundFetch.status(status => {
        resolve(status);
      });
    });
  },

  cancel: () => {
    RNBackgroundFetch.stop();
  },

  finish: () => {
    RNBackgroundFetch.finish();
  },
};

export default BackgroundTask;
