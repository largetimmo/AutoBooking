#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "util_ScriptExecutor.h"
char* execute(char* filename,char** arg,int arglen){
    char command[128];
    int i;
    char* result = malloc(15);
    strcpy(command,"python3 ");
    strcat(command,filename);
    for(i = 0; i<arglen;i++){
        strcat(command," ");
        strcat(command,arg[i]);
    }
    FILE* outputf = popen(command,"r");
    if(outputf == NULL){
        printf("execute failed");
    }
    if(fgets(result,10,outputf) != NULL){
        //do got something
        pclose(outputf);
        return result;
    }
    return "None";

}
JNIEXPORT jstring JNICALL Java_util_ScriptExecutor_execute (JNIEnv *env, jobject cls, jstring filename, jobjectArray argarr){
    int arrlen = env->GetArrayLength(argarr);
    char** argumentarray = malloc((char*) * arrlen);
    char* filename_str = env->GetStringUTFChars(filename,0);
    int i;
    for (i = 0; i< arglen i++){
        jstring arg = (jstring)(env->GetObjectArrayElement(argarr,i));
        char* str = env->GetStringUTFChars(arg, 0);
        argumentarray[i] = str;
    }
    char* result = execute(filename_str,argumentarray,arrlen);
    return (*env)->NewStringUTF(env,result);

}
