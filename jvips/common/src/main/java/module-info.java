module com.pss.jvips.common {
    uses com.pss.jvips.common.context.VipsGlobalContextFactory;
    requires org.jetbrains.annotations;

    exports com.pss.jvips.common.image;
    exports com.pss.jvips.common.concurrent;
    exports com.pss.jvips.common.context;
    exports com.pss.jvips.common.api;
    exports com.pss.jvips.common.annotations;
    exports com.pss.jvips.common.generated;
    exports com.pss.jvips.common.generated.args;
    exports com.pss.jvips.common.generated.types;
   // exports com.pss.jvips.common.generated.callback;
    exports com.pss.jvips.common.generated.result;

    exports com.pss.jvips.common.impl;
    exports com.pss.jvips.common.cleaner;
    exports com.pss.jvips.common.data;
    exports com.pss.jvips.common.util to com.pss.jvips.jni, com.pss.jvips.panama;
}