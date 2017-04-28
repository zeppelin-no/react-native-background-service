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

    // eventEmitter.addListener('fetch', task);

    RNBackgroundFetch.status(status => {
      switch (status) {
        case RNBackgroundFetch.STATUS_RESTRICTED:
          console.log('RNBackgroundFetch restricted');
          break;
        case RNBackgroundFetch.STATUS_DENIED:
          console.log('RNBackgroundFetch denied');
          break;
        case RNBackgroundFetch.STATUS_AVAILABLE:
          console.log('RNBackgroundFetch is enabled');
          break;
        default:
          console.log('all is good');
          break;
      }
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
