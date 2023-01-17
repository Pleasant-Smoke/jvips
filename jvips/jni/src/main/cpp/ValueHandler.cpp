//
// Created by x99 on 12/7/22.
//
#include <jni.h>
#include <vips/vips.h>
#include <string>
#include "ValueHandler.h"
using namespace std;
template <class T>

class AbstractValueHandler {
    protected:
        jmethodID factory;
        jclass clazz;
        jfieldID field;
        GType type;
    public:
        AbstractValueHandler(GType type)
                :  type(type) {}

    public:
        bool handleValue(JNIEnv *env, VipsOperation *op, GValue *gvalue, jobject *obj, std::string *property) {
            if(env->IsInstanceOf(*obj, clazz)){
                T v = getValue(env, obj);
                g_value_init(gvalue, type);
                setValue(gvalue, &v);
                g_object_set_property(G_OBJECT(op), property->c_str(), gvalue);
                g_value_unset(gvalue);
            }
        }

        virtual jobject box(JNIEnv *env, T *value) {}

        virtual void setValue(GValue *gvalue, T *value){}

        virtual T getValue(JNIEnv *env, jobject *obj){}

};

class JLong : AbstractValueHandler<jlong> {
    public:
        explicit JLong() : AbstractValueHandler(G_TYPE_LONG) {}

        void setValue(GValue *gvalue, jlong *value) override {
            g_value_set_long(gvalue, *value);
        }

        jlong getValue(JNIEnv *env, jobject *obj) override {
           return env->GetLongField(*obj, field);
        }

        jobject box(JNIEnv *env, jlong *obj) override {
           return env->CallStaticObjectMethod(clazz, factory, *obj);
        }
};

class JInt : AbstractValueHandler<jint> {
    public:
        explicit JInt() : AbstractValueHandler(G_TYPE_INT) {}

        void setValue(GValue *gvalue, jint *value) override {
            g_value_set_int(gvalue, *value);
        }

        jint getValue(JNIEnv *env, jobject *obj) override {
           return env->GetIntField(*obj, field);
        }

        jobject box(JNIEnv *env, jint *obj) override {
           return env->CallStaticObjectMethod(clazz, factory, *obj);
        }
};

class JDouble : AbstractValueHandler<jdouble> {
    public:
        explicit JDouble() : AbstractValueHandler(G_TYPE_DOUBLE) {}

        void setValue(GValue *gvalue, jdouble *value) override {
            g_value_set_double(gvalue, *value);
        }

        jdouble getValue(JNIEnv *env, jobject *obj) override {
           return env->GetDoubleField(*obj, field);
        }

        jobject box(JNIEnv *env, jdouble *obj) override {
           return env->CallStaticObjectMethod(clazz, factory, *obj);
        }
};