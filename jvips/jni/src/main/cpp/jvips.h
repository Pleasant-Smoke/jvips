//
// Created by x99 on 12/30/22.
//
#include <jni.h>
#include <vips/vips.h>
#include <optional>
#include <memory>

using namespace std;
#ifndef JVIPS_JNI_JVIPS_H
#define JVIPS_JNI_JVIPS_H

struct Primitive {
    jclass clazz;
    jfieldID value;
    jmethodID factory;
    GType gtype;
};

struct Argument {
    unique_ptr<string> name;
    jlong type;
    jobject value;
    optional<jobject> _value;
    bool valueNotNull;
};


struct Logger {
    jobject instance;
    jclass clazz;
    int level;
    jmethodID info;
    jmethodID debug;
    jmethodID warning;
    jmethodID error;
    jmethodID trace;
};
#endif //JVIPS_JNI_JVIPS_H
