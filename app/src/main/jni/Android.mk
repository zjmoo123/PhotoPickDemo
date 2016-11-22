LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := hello
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_SRC_FILES := \
	/home/zjm/android/workspace/PhotoPickDemo/app/src/main/jni/Hello.c \

LOCAL_C_INCLUDES += /home/zjm/android/workspace/PhotoPickDemo/app/src/main/jni
LOCAL_C_INCLUDES += /home/zjm/android/workspace/PhotoPickDemo/app/src/debug/jni

include $(BUILD_SHARED_LIBRARY)
