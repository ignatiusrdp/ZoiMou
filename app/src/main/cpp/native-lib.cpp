#include <jni.h>
#include <string>
#include <time.h>

using namespace std;

extern "C" JNIEXPORT jstring JNICALL
Java_id_ac_ui_cs_mobileprogramming_ignatiusrahardi_zoimou_services_GetWeatherService_getTime(
        JNIEnv* env,
        jobject /* this */) {
    time_t now = time(0);
    tm* localtm = localtime(&now);
    return env->NewStringUTF(asctime(localtm));
}