#include <jni.h>
#include <stdio.h>

JNIEXPORT jstring JNICALL Java_com_example_zjm_photopickdemo_jniTest_JniUtils_getStringFromC(JNIEnv *env, jobject obj){
    return (*env)->NewStringUTF(env, "This is Jni test!!!");
}

JNIEXPORT jstring JNICALL
Java_com_example_zjm_photopickdemo_jniTest_JniUtils_setString(JNIEnv *env, jobject instance) {

    // TODO
    return (*env)->NewStringUTF(env, "");
}