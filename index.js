// @flow

import { NativeModules, DeviceEventEmitter } from 'react-native'

import deviceSpesific from './src';


// export type UploadEvent = 'progress' | 'error' | 'completed' | 'cancelled'
//
// export type NotificationArgs = {
//   enabled: boolean
// }
//
// export type StartUploadArgs = {
//   url: string,
//   path: string,
//   headers?: Object,
//   notification?: NotificationArgs
// }
//
// const NativeModule = NativeModules.BackgroundService;
//
// console.log(NativeModule);
//
// export const register = (eventType: UploadEvent, uploadId: string, listener: Function) => {
//   console.log('BackgroundService - register');
//
//   // return DeviceEventEmitter.addListener(eventPrefix + eventType, (data) => {
//   //   if (!uploadId || !data || !data.id || data.id === uploadId) {
//   //     listener(data)
//   //   }
//   // })
// };

export default deviceSpesific;
