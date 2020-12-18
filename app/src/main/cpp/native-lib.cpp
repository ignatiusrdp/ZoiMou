#include <jni.h>
#include <string>
#include <time.h>

using namespace std;

extern "C" JNIEXPORT jstring JNICALL
Java_id_ac_ui_cs_mobileprogramming_ignatiusrahardi_zoimou_services_GetWeatherService_getTime(
        JNIEnv* env,
        jobject /* this */) {
    // current date/time based on current system
    time_t now = time(0);

// Convert now to tm struct for local timezone
    tm* localtm = localtime(&now);



    return env->NewStringUTF(asctime(localtm));
}