#include <jni.h>
#include <string.h>
#include <string>
#include <stdio.h>
#include <fcntl.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>
#include <malloc.h>
#include <android/log.h>

#define TAG    "liujunjie" // 这个是自定义的LOG的标识
#define LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__)
extern "C" JNIEXPORT jstring JNICALL
Java_com_liqi_nativelib_NativeLib_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}



/*
 * *en 可以理解为一个上下文，里面有很多方法
 * thiz 谁调用的这个方法就是谁的实例*/
extern "C"
JNIEXPORT jint JNICALL
Java_com_liqi_nativelib_NativeLib_getIn(JNIEnv *env, jobject thiz) {
    // TODO: implement getIn()

    return 4;

}
extern "C"
JNIEXPORT jint JNICALL
Java_com_liqi_nativelib_NativeLib_add(JNIEnv *env, jobject thiz, jint ssr) {
    // TODO: implement add()

    return 4;
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_liqi_nativelib_NativeLib_addString(JNIEnv *env, jobject thiz) {
    // TODO: implement addString()
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_liqi_nativelib_NativeLib_count(JNIEnv *env, jobject thiz, jint ss, jint ss2) {
    // TODO: implement count()
    int sssr = ss+ss2;

    return sssr;

}

extern "C"
JNIEXPORT jint JNICALL
Java_com_liqi_nativelib_NativeLib_ModifyMemory(JNIEnv *env, jobject thiz) {
    // TODO: implement ModifyMemory()
   /* long start = 0x7ffc9c6d8000;
    long end = 0x7ffc9c6f9000;
    int value = 59;//我们要搜索的值
    char filename[256] = {0};
    int pid = 163147;
    int wbuf = 70; //这个是我们修改后的值
    int fd ;
    long size;
    int count = 0;//计数
    int *buf;
    int block_size = sizeof(int);
    int i =0;
    int ret;
    long addr= 0x7ffc9c6f7694;

    size = end - start;
    //内存分配 分配所需的内存空间，并返回一个指向它的指针 失败的话就返回一个null
    // 参数1 要被分配的元素个数 ， 元素大小
    // int i, n;
    // int *a;
    // printf("要输入的元素个数：");
    // scanf("%d",&n);
    // a = (int*)calloc(n, sizeof(int));
    buf = (int*)calloc(1, size);
    //函数原型：  void memset ( void *s , char ch, unsigned n )
    //函数功能：将s为首地址的一片连续的n个字节内存单元都赋值为ch
    memset(buf, 0, size);
    //格式化输出函数  第一个参数是一个指向字符串数组的指针  第二个参数
    //					第二个参数是一个字符串，包含了要被写入字符串的文本
    //    				int sprintf(char *str, const char *format, ...)
    // 将filename这个char类型字节容器用来包装 mem这个文件的字节流
    sprintf(filename,"/proc/%d/mem",pid);
    // open函数原型，获得一个文件的描述符号 将filename 这个文件开启读写模式，返回一个int 类型的描述符号
    fd = open(filename,O_RDWR | O_SYNC);
    // pread64函数原型
    //ret = pread64(fd,buf,size,start);

    lseek(fd, start, size);
    read(fd, buf, size);
    //ret = pread64(fd,buf,size,addr);
    printf("  = 0x%02x. \r\n", *(int*)buf);
    if(ret == -1)
        printf("err. \r\n");

    for(i;i<size;i+=block_size)
    {
        int search_value = *(int*)(buf + i);
        if(search_value == value)
        {
            //搜索到数值
            printf("index:%d address:%lx value:%d\n",i,start + i,search_value);
            count++;
            ret = pwrite64(fd,&wbuf,sizeof(wbuf),start+i);
            printf(" ret = %d . \r\n", ret);
        }
    }
    printf("搜索到：%d个数值\n",count);
*/
    return 0;

}
extern "C"
JNIEXPORT jint JNICALL
Java_com_liqi_nativelib_NativeLib_ModifyMemory2(JNIEnv *env, jobject thiz, jint pids) {
    // TODO: implement ModifyMemory2()

    /*long start = 0x7ffc9c6d8000;
    long end = 0x7ffc9c6f9000;
    //long start =;
    //long end =0x00602000;
    int value = 50;//我们要搜索的值
    char filename[256] = {0};
    int pid = pids;
    int wbuf = 70; //这个是我们修改后的值
    int fd ;
    long size;
    int count = 0;//计数
    int *buf;
    int block_size = sizeof(int);
    int i =0;
    int ret;
    long addr= 0x7ffc9c6f7694;

    size = end - start;
    //内存分配 分配所需的内存空间，并返回一个指向它的指针 失败的话就返回一个null
    // 参数1 要被分配的元素个数 ， 元素大小
    // int i, n;
    // int *a;
    // printf("要输入的元素个数：");
    // scanf("%d",&n);
    // a = (int*)calloc(n, sizeof(int));
    buf = (int*)calloc(1, size);
    //函数原型：  void memset ( void *s , char ch, unsigned n )
    //函数功能：将s为首地址的一片连续的n个字节内存单元都赋值为ch
    memset(buf, 0, size);
    //格式化输出函数  第一个参数是一个指向字符串数组的指针  第二个参数
    //					第二个参数是一个字符串，包含了要被写入字符串的文本
    //    				int sprintf(char *str, const char *format, ...)
    // 将filename这个char类型字节容器用来包装 mem这个文件的字节流
    sprintf(filename,"/proc/%d/mem",pid);
    // open函数原型，获得一个文件的描述符号 将filename 这个文件开启读写模式，返回一个int 类型的描述符号
    fd = open(filename,O_RDWR | O_SYNC);
    // pread64函数原型
    ret = pread64(fd,buf,size,start);

   // lseek(fd, start, size);
    //read(fd, buf, size);
    //ret = pread64(fd,buf,size,addr);
    printf("  = 0x%02x. \r\n", *(int*)buf);
    if(ret == -1)
        printf("err. \r\n");

    for(i;i<size;i+=block_size)
    {
        int search_value = *(int*)(buf + i);
        if(search_value == value)
        {
            //搜索到数值
            printf("index:%d address:%lx value:%d\n",i,start + i,search_value);
            count++;
            ret = pwrite64(fd,&wbuf,sizeof(wbuf),start+i);
            printf(" ret = %d . \r\n", ret);
        }
    }
    printf("搜索到：%d个数值\n",count);*/

    return 0;

}
/*
int getAppPid(const char *process_name){
        int id;
        int pid = -1;
        DIR *dir;
        FILE *fp;
        char filename[32];
        char cmdline[256];
        struct dirent *entry;
        if (process_name == NULL)
        {
            return -1;
        }
        dir = opendir("/proc");
        if (dir == NULL)
        {
            return -1;
        }
        while ((entry = readdir(dir)) != NULL)
        {
            id = atoi(entry->d_name);
            if (id != 0)
            {
                sprintf(filename, "/proc/%d/cmdline", id);
                fp = fopen(filename, "r");
                if (fp)
                {
                    fgets(cmdline, sizeof(cmdline), fp);
                    fclose(fp);
                    if (strcmp(process_name, cmdline) == 0)
                    {
                        pid = id;
                        break;
                    }
                }
            }
        }
        closedir(dir);
        return pid;

}*/

extern "C"
JNIEXPORT jint JNICALL
Java_com_liqi_nativelib_NativeLib_ModifyMemory3(JNIEnv *env, jobject thiz, jint javaPid, jlong javaStar,
                                                jlong javaEnd) {
    // TODO: implement ModifyMemory3()
    long start = javaStar;
    long end = javaEnd;
    //long start =;
    //long end =0x00602000;
    int value = 888;//我们要搜索的值
    char filename[256] = {0};
    int pid = javaPid;
    int wbuf = 70; //这个是我们修改后的值
    int fd ;
    long size;
    int count = 0;//计数
    int *buf;
    int block_size = sizeof(int);
    unsigned int i =0;
    int ret;
    //size = 1048576;
    size = end - start;
    //内存分配 分配所需的内存空间，并返回一个指向它的指针 失败的话就返回一个null
    // 参数1 要被分配的元素个数 ， 元素大小
    // int i, n;
    // int *a;
    // printf("要输入的元素个数：");
    // scanf("%d",&n);
    // a = (int*)calloc(n, sizeof(int));
    //buf = (unsigned int*)calloc(1, size);
    buf = (int *)malloc(size);

    //函数原型：  void memset ( void *s , char ch, unsigned n )
    //函数功能：将s为首地址的一片连续的n个字节内存单元都赋值为ch
    memset(buf, 0, size);
    //格式化输出函数  第一个参数是一个指向字符串数组的指针  第二个参数
    //					第二个参数是一个字符串，包含了要被写入字符串的文本
    //    				int sprintf(char *str, const char *format, ...)
    // 将filename这个char类型字节容器用来包装 mem这个文件的字节流
    sprintf(filename,"/proc/%d/mem",pid);
    // open函数原型，获得一个文件的描述符号 将filename 这个文件开启读写模式，返回一个int 类型的描述符号
    fd = open(filename,O_RDWR | O_SYNC);
    // pread64函数原型
   // ret = pread64(fd,buf,size,start);

   // lseek(fd, start, size);
   // read(fd, buf, size);
    ret = pread64(fd,buf,size,start);
    printf("  = 0x%02x. \r\n", *(int*)buf);
    /*if(ret == -1){
        close(fd);
        return -1;
        printf("err. \r\n");
    }*/


    for(i;i<size;i+=block_size)
    {

        int search_value = *(buf + i);

        /*int search_value = 0;
        int j=0;
        char* p = (char*)&search_value;
        for(j=0;j < 4;j++)
        {
            p[j] = buf[i+j];
        }*/


        if(search_value == value)
        {
            //搜索到数值
           // printf("index:%d address:%lx value:%d\n",i,start + i,search_value);
            count++;
            ret = pwrite64(fd,&wbuf,sizeof(wbuf),start+i);
            printf(" ret = %d . \r\n", ret);
        }
        printf("搜索到：%d个数值\n",count);
    }
    printf("搜索到：%d个数值\n",count);
    free(buf);
    close(fd);
    return count;
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_liqi_nativelib_NativeLib_ModifyMemory4(JNIEnv *env, jobject thiz, jint java_pid,
                                                jlong java_addr_statr, jlong java_addr_end,
                                                jint i) {
    // TODO: implement ModifyMemory4()
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_liqi_nativelib_NativeLib_printfI(JNIEnv *env, jobject thiz) {
    // TODO: implement printfI()

    //FILE *fp;
    int id = 14;
    char *s;
    s = strdup("Holberton");
    while(1){
        LOGD("test:%d ，addr:0x%p, s:%p, s=%d \r\n",id,&id,s,s[0]);
        sleep(1);
    }


    return 0;
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_liqi_nativelib_NativeLib_testIntAddr(JNIEnv *env, jobject thiz, jint addr) {
    // TODO: implement testIntAdder()

    LOGD("传递到jni里面 整数的地址是 addr: 0x%p 值是%d ,,\r\n",&addr,addr);
    /*while(1){
        LOGD("传递到jni里面 整数的地址是 addr: 0x%p 值是%d ,,\r\n",&addr,addr);
        sleep(1);

        0x0x7fca235fc4
    }*/
    return 0;
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_liqi_nativelib_NativeLib_test1arry(JNIEnv *env, jobject thiz, jintArray ints) {
    // TODO: implement test1arry()
   /* jint *array = (*env)->GetIntArrayElements(ints, NULL);
    *array = 10;*/
   return 0;
}