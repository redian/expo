// Copyright 2018-present 650 Industries. All rights reserved.

#import <EXErrorRecovery/EXErrorRecoveryModule.h>

@implementation EXErrorRecoveryModule

UM_EXPORT_MODULE(ExpoErrorRecovery);

UM_EXPORT_METHOD_AS(saveRecoveryProps,
                    saveRecoveryProps:(NSString *)props
                    resolve:(UMPromiseResolveBlock)resolve
                    rejecter:(UMPromiseRejectBlock)reject)
{
  if (props) {
    if (![self setRecoveryProps:props]) {
      return reject(@"E_ERROR_RECOVERY_SAVE_FAILED", @"Couldn't save props.", nil);
    }
  }
  resolve(nil);
}

- (NSDictionary *)constantsToExport
{
  return @{
           @"recoveredProps": [self consumeRecoveryProps]
           };
}

- (BOOL)setRecoveryProps:(NSString *)props
{
  NSUserDefaults *preferences = [NSUserDefaults standardUserDefaults];
  [preferences setObject:props forKey:USER_DEFAULTS_KEY];
  return [preferences synchronize];
}

- (NSString *)consumeRecoveryProps
{
  NSUserDefaults *preferences = [NSUserDefaults standardUserDefaults];
  NSString *props = [preferences objectForKey:USER_DEFAULTS_KEY];
  if (props) {
    [preferences removeObjectForKey:USER_DEFAULTS_KEY];
    [preferences synchronize];
  }
  return props;
}

@end
