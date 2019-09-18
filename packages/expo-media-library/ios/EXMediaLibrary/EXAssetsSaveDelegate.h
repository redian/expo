// Copyright 2015-present 650 Industries. All rights reserved.

#import <Foundation/Foundation.h>
#import <UMCore/UMUtilities.h>
#import <EXMediaLibrary/EXMediaLibrary.h>

typedef void(^EXAssetsSaveCallback)(id asset, NSError *error);

@interface EXAssetsSaveDelegate : NSObject

- (void)writeImage:(UIImage *)image withCallback:(EXAssetsSaveCallback)callback;

- (void)writeVideo:(NSString *)movieUrl withCallback:(EXAssetsSaveCallback) callback;

@end
