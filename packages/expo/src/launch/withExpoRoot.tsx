import * as ErrorRecovery from 'expo-error-recovery';
import * as Font from 'expo-font';
import * as React from 'react';
import { StyleSheet } from 'react-native';

import Notifications from '../Notifications/Notifications';
import RootErrorBoundary from './RootErrorBoundary';
import { InitialProps } from './withExpoRoot.types';

export default function withExpoRoot<P extends InitialProps>(
  AppRootComponent: React.ComponentType<P>
): React.ComponentType<P> {
  return class ExpoRootComponent extends React.Component<P> {
    componentWillMount() {
      if (StyleSheet.setStyleAttributePreprocessor) {
        StyleSheet.setStyleAttributePreprocessor('fontFamily', Font.processFontFamily);
      }
      const { exp } = this.props;
      if (exp.notification) {
        Notifications._setInitialNotification(exp.notification);
      }
    }

    render() {
      const props = {
        ...this.props,
        exp: { ...this.props.exp, errorRecovery: ErrorRecovery.recoveredProps },
      };
      if (__DEV__) {
        return (
          <RootErrorBoundary>
            <AppRootComponent {...props} />
          </RootErrorBoundary>
        );
      } else {
        return <AppRootComponent {...props} />;
      }
    }
  };
}
