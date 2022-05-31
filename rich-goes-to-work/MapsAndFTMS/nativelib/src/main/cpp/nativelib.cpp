#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_ritchie_nativelib_NativeLib_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jlongArray JNICALL
Java_com_ritchie_nativelib_NativeLib_searchMapsForlist(JNIEnv *env, jobject thiz, jobject list) {
    // TODO: implement searchMapsForlist()
    jclass list_cls = (*env)->FindClass(env, "java/util/List");
    jmethodID list_get_method = (*env)->GetMethodID(env, list_cls,
    "get", "(I)Ljava/lang/Object;");
}
extern "C"
JNIEXPORT jlongArray JNICALL
Java_com_ritchie_nativelib_NativeLib_searchMapsForStrings(JNIEnv *env, jobject thiz,
                                                          jobjectArray strings) {
    // TODO: implement searchMapsForStrings()
}