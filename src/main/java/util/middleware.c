#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "util_ScriptExecutor.h"
char* execute(const char* filename,const char** arg,int arglen){
    char command[384];
    int i;
    char* result = malloc(sizeof(char) * 15);
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
    if(fgets(result,30,outputf) != NULL){
        //do got something
        pclose(outputf);
        return result;
    }

    return "None";

}
JNIEXPORT jstring JNICALL Java_util_ScriptExecutor_execute (JNIEnv *env, jobject cls, jstring filename, jobjectArray argarr){
    (*env)->FindClass(env,"java/lang/String");
    int arrlen;
    arrlen = (*env)->GetArrayLength(env,argarr);
    const char** argumentarray = malloc(sizeof(const char*) * arrlen);
    const char* filename_str = (*env)->GetStringUTFChars(env,filename,0);
    int i;
    for (i = 0; i< arrlen;i++){
        jstring arg = (jstring)((*env)->GetObjectArrayElement(env,argarr,i));
        const char* str = (*env)->GetStringUTFChars(env,arg, 0);
        argumentarray[i] = str;
    }
    char* result = execute(filename_str,argumentarray,arrlen);
    return (*env)->NewStringUTF(env,result);

}