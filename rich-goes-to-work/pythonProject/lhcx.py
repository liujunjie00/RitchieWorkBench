#!/usr/bin/python
# -*- coding: UTF-8 -*-
import os
import shutil
import sys
import time
Path = ""
ProjectConfigPath = ""
BuildinfoPath = ""
Defconfig = ""
LkPath = ""
tpSettingPath = ""
zlZzlhcxPath = "/zl/zzlhcx"  # type: str
kernel_meminfopath=""
Rompath=""
Proinfopath =""

Camerapath = Path+"kernel-3.18/drivers/misc/mediatek/imgsensor/src/mt6753"
Lcm800CPath = Path+"vendor/mediatek/proprietary/bootable/bootloader/lk/dev/lcm/LCM_readme_8c.txt"
Lcm101Path = Path+"vendor/mediatek/proprietary/bootable/bootloader/lk/dev/lcm/LCM_readme101new.txt"
Lcm695Path = Path+"vendor/mediatek/proprietary/bootable/bootloader/lk/dev/lcm/LCM_readme_695.txt"
LcmFHDPath = Path+"vendor/mediatek/proprietary/bootable/bootloader/lk/dev/lcm/LCM_readme_fhd.txt"
FalshTypePath = Path+"vendor/mediatek/proprietary/bootable/bootloader/preloader/zl_flash_type"
tpPath = Path+"kernel-3.18/drivers/input/touchscreen/mediatek/gslX68X"

mainCameraTarger = []
subCameraTarger =[]
alllcmTarger ="null"
lcmTarger = "null"
indexoflcm = "null"
tpTarger = "null"
GMSTarger = "null"
VersionIDTarger= "null"
flashTypeTarger = "null"
isopponTarger = "null"
verno = "null"

gitCheckoutFiles = []

#  修改编译时间
def modifyVersionForProject():
    # CUSTOM_SOFTWARE_VERNO = ZLP30V1.0_B_HAOJING_03_53_O_YL9881C_BOE_HZY_V1.0_20211225
    # print time.strftime("%Y-%m-%d %H:%M:%S", time.localtime())
    global verno
    datestr = time.strftime("%Y%m%d", time.localtime())
    print datestr
    verno = "CUSTOM_SOFTWARE_VERNO = "+verno+'_'+datestr
    verno = verno.upper()
    main(ProjectConfigPath,"CUSTOM_SOFTWARE_VERNO =",verno)
    return
# 摄像头函数代码
def cameraSetting():
    mainCamera = ""
    subCamera = ""
    for mcammera in mainCameraTarger:
        if not mcammera == "":
            mainCamera += mcammera+" "
    for scamera in subCameraTarger:
        if not scamera == "":
            subCamera += scamera+" "
    mainCamera = mainCamera.rstrip()
    subCamera = subCamera.rstrip()
    main(Defconfig, "CONFIG_CUSTOM_KERNEL_IMGSENSOR=","CONFIG_CUSTOM_KERNEL_IMGSENSOR="+ '\"'+str(mainCamera)+" " + str(subCamera)+'\"')
    main(ProjectConfigPath, "CUSTOM_HAL_IMGSENSOR = ","CUSTOM_HAL_IMGSENSOR = "+ str(mainCamera)+" " + str(subCamera))
    main(ProjectConfigPath,"CUSTOM_HAL_MAIN_IMGSENSOR = ","CUSTOM_HAL_MAIN_IMGSENSOR = "+ str(mainCamera))
    main(ProjectConfigPath,"CUSTOM_HAL_SUB_IMGSENSOR = ","CUSTOM_HAL_SUB_IMGSENSOR = "+ str(subCamera))
    main(ProjectConfigPath,"CUSTOM_KERNEL_IMGSENSOR = ","CUSTOM_KERNEL_IMGSENSOR = "+str(mainCamera)+" " + str(subCamera))
    main(ProjectConfigPath, "CUSTOM_KERNEL_MAIN_IMGSENSOR = ", "CUSTOM_KERNEL_MAIN_IMGSENSOR = " + str(mainCamera))
    main(ProjectConfigPath, "CUSTOM_KERNEL_SUB_IMGSENSOR = ", "CUSTOM_KERNEL_SUB_IMGSENSOR = " + str(subCamera))
    return
def tpSetting():
    maintpsetting(tpSettingPath,"#endif","static unsigned char gsl_cfg_index",'#include \"'+tpTarger+"\"\n")
    return
def lcmSetting(old_str_onthe_line):
    main(LkPath,"CUSTOM_LK_LCM",'CUSTOM_LK_LCM=\"'+alllcmTarger+"\"")
    main(Defconfig,"CONFIG_CUSTOM_KERNEL_LCM=","CONFIG_CUSTOM_KERNEL_LCM=\""+alllcmTarger+'\"')
    global Proinfopath
    Proinfopath = Path+zlZzlhcxPath +"proinfo.img"
    if os.path.exists(Proinfopath):
        os.system( 'echo -n '+indexoflcm+" >"+Proinfopath)
        print "proinfo.img 将要被设置成"+indexoflcm
    else:
        os.system("touch "+Proinfopath)
        os.system('echo -n '+indexoflcm+" >"+Proinfopath)
        print "正在创建proinfo文件 并且写入值 "+indexoflcm

    return
def writeFileforLcm(string_file_path):
    # 设计成返回值数组的形式 第一个值为屏参的全部文件 第二个参数为使用的屏参,第三个为屏参的数值
    count1 = 0
    count = 0
    count2 = 0
    old_str_onthe_line = []
    global alllcmTarger
    global lcmTarger
    global indexoflcm
    # 上面设计成数组的原因是因为可能存在注释的情况
    # 打印屏参文件给各位大佬看 拿到序列号
    f1 = open(string_file_path, "r")
    for line in f1.readlines():
        sss = line
        if sss.count("\n"):
            sss=sss[0:sss.index("\n")]
            print(sss)
    f1.close()
    indexoflcm = raw_input("请输入你的屏参序列号：")
    # 根据序列号拿到指定的行
    indexString = "("+indexoflcm+')'
    f = open(string_file_path, "r")
    for line in f.readlines():
        if indexString in line:
            # print("第 " + str(count1) + " 行已找到.")
            print("          你选中的屏幕文件是: \n" + line)
            lcmTarger=line
        count1 += 1
    f.close()
    if lcmTarger.count("   "):
        lcmTarger = lcmTarger[lcmTarger.index("   "):len(lcmTarger)].strip()
    if lcmTarger.count("\t"):
        lcmTarger = lcmTarger[lcmTarger.index("\t"):len(lcmTarger)].strip()
    f2 = open(string_file_path, "r")
    allCoutforlcm = 0
    for line in f2.readlines():
        if lcmTarger in line:
           # print("第 " + str(count) + " 行已找到.")
            print("------------------------兼容屏参-----------------------------: \n" + line)
            if allCoutforlcm == 0:
                alllcmTarger = line[0:line.index("\r")]
                allCoutforlcm += 1
                return
        count += 1
    f2.close()

    old_str_onthe_line.append(alllcmTarger)
    old_str_onthe_line.append(lcmTarger)
    old_str_onthe_line.append(indexoflcm)
    return old_str_onthe_line
#  1 就是需要带谷歌
def buildGMS():
    if GMSTarger == 1:
       #  带谷歌的情况下  还需要把输入法复制到另外一个里面去
        main(BuildinfoPath, "persist.sys.timezone", 'echo "persist.sys.timezone=America/New_York"')
        main(BuildinfoPath, "ro.product.browser",'echo "ro.product.browser=https://www.goolge.com"')
        main(BuildinfoPath,"ro.product.locale",'echo "ro.product.locale=en-US"')
        main(ProjectConfigPath, "BUILD_GMS", "BUILD_GMS = yes")
        main(ProjectConfigPath, "MTK_FACTORY_MODE_IN_GB2312 =", "MTK_FACTORY_MODE_IN_GB2312 = no")
    elif GMSTarger == 0:
        main(BuildinfoPath, "persist.sys.timezone", 'echo "persist.sys.timezone=Asia/Shanghai"')
        main(BuildinfoPath, "ro.product.browser", 'echo "ro.product.browser=https://www.baidu.com"')
        main(BuildinfoPath, "ro.product.locale", 'echo "ro.product.locale=zh-CN"')
        main(ProjectConfigPath, "BUILD_GMS", "BUILD_GMS = no")
        main(ProjectConfigPath, "MTK_FACTORY_MODE_IN_GB2312 =", "MTK_FACTORY_MODE_IN_GB2312 = yes")
    return
def flashType():
    # 这里的 ZL_FLASH_TYPE 需要的是哪一行的数据
    main(ProjectConfigPath,"ZL_FLASH_TYPE =","ZL_FLASH_TYPE = "+flashTypeTarger)
    return
def APP_CALL_FORCE_SPEAKER(offoron):
    # 这个是用来没有听筒的默认打开听筒的按键 MTK_TB_APP_CALL_FORCE_SPEAKER_ON = no
    # if isOppon
    # main(ProjectConfigPath,"MTK_TB_APP_CALL_FORCE_SPEAKER_ON =","MTK_TB_APP_CALL_FORCE_SPEAKER_ON = yes")

    return
def quickCharge5405():
    return
# 将文件替换字符串
def main(string_file_path, old_string, new_string):
    # 注意这个file_path是绝对路径
    # 参数2需要被修改的字符串所在的行号  会抹掉字符串
    # 第三个参数会将新的字符串填入老字符串所在行号

    # 开始查找
    count = 0
    old_str_onthe_line = []
    # 上面设计成数组的原因是因为可能存在注释的情况
    f = open(string_file_path, "r")
    for line in f.readlines():
        if old_string in line:
            print("第 " + str(count) + " 行已找到.")
            # print("该行内容: \n" + line)
            old_str_onthe_line.append(count)
        count += 1
    f.close()

    if count == 0:
        print ("需要更改的属性为找到 ，看一下文件属性是不是被注释了")
        return 0

    # 这里写上关于注释的判断逻辑
    lines = open(string_file_path, 'r').readlines()
    for index in range(len(old_str_onthe_line)):

        if ".mk" in string_file_path or ".sh" in string_file_path:
            if not("#" in lines[old_str_onthe_line[index]]):
                lines[old_str_onthe_line[index]] = new_string + '\n'
                # print ("mk---文件中将被替换成---- "+lines[old_str_onthe_line[index]])
                print ("mk---文件中将被替换成---- ")
        if ".h" in string_file_path:
            if not("//" in lines[old_str_onthe_line[index]]):
                lines[old_str_onthe_line[index]] = new_string + '\n'
                # print (".h----文件中将被替换成--- "+lines[old_str_onthe_line[index]])
                print (".h----文件中将被替换成--- ")
        if "config" in string_file_path:
            if not("#" in lines[old_str_onthe_line[index]]):
                lines[old_str_onthe_line[index]] = new_string + '\n'
                # print ("config---文件中将被替换成--- "+lines[old_str_onthe_line[index]])
                print ("config---文件中将被替换成--- ")

    # 所有数据写回文本
    new_file = open(string_file_path, 'w')
    new_file.writelines(lines)
    new_file.flush()
    new_file.close()

    return 1
# 这个是tp专用函数
def maintpsetting(string_file_path, old_string1,old_string2, new_string):
    print "maintpsetting"
    count1 = 0
    count2 = 0
    isaddtp = 0
    f2 = open(string_file_path, "r")
    for line in f2.readlines():
        if old_string2 in line:
            print("该行内容: \n" +str(count2)+':'+ line)
            break
        count2 += 1
    f2.close()

    f = open(string_file_path, "r").readlines()
    nnn = count2
    # print count1
    while count1 == 0:
        # print "开始循环 ="
        nnn = nnn-1
        if old_string1 in f[nnn]:
            count1 = nnn
    print count1

    if count1 == 0:
        print ("需要更改的属性为找到 ，看一下文件属性是不是被注释了")
        return
    if count2 == 0:
        print "失败"
        return
    lines = open(string_file_path, 'r').readlines()
    while not count2 == count1:
        if count1 < count2:
            if isaddtp == 0:
                lines[count1 + 1] = new_string
                isaddtp += 1
                count1 += 1
            else:
                if lines[count1+1].count("#include"):
                    if not lines[count1+1].count("//"):
                        lines[count1+1] = "//"+lines[count1+1]
                count1 += 1
        # 所有数据写回文本
    new_file = open(string_file_path, 'w')
    new_file.writelines(lines)
    new_file.flush()
    new_file.close()
    return
# 是否需要root 权限
def isNeedRoot():

    return
# 内存设置
def ROMAndMemoryService(realRom,showRom,Memory):
    main(BuildinfoPath,'echo "ro.product.realromsize=','echo \"ro.product.realromsize='+str(realRom)+"\"")
    main(BuildinfoPath,'echo "ro.product.showromsize=','echo \"ro.product.showromsize='+str(showRom)+"\"")
    if os.path.exists(Rompath):
        os.system( 'echo -n '+str(showRom)+" >"+Rompath)
    else:
        print "未找到文件"
    if os.path.exists(kernel_meminfopath):
        os.system('echo -n ' + str(Memory) + " >" + kernel_meminfopath)
    else:
        print "内存配置失败，未找到文件"
    return
# 这个函数是用来遍历文件夹下面是子文件夹的,有返回值 返回值是字符串
def Traverse_elements_dao(path_arg1):

    zhuban_cpu = []
    workzhuban_cpu_name = ""
    count = 0
    # 更新recovery 需要添加main.文件中开启debug属性 ，添加recovery.cpp文件

    for filename in os.listdir(path_arg1):
        count = count + 1
        zhuban_cpu.append(filename)
        print (str(count) + "          " + filename)

    # 遍历数
    str1 = raw_input("输入内容 列表没有请输入exit：")
    if str(str1) == "exit":
        return ""

    if int(str1) > len(zhuban_cpu):
        print " 警告！！！！！--------请输入序号对应的编号-------"
        return "请输入序号对应的编号"

    print "你输入的内容是:::::--- ", zhuban_cpu[int(str1) - 1]
    workzhuban_cpu_name = zhuban_cpu[int(str1) - 1]
    # str1 = raw_input("是否需要更改： 0更改填写    1不需要更改 :")
    # if int(str1) != 1:
    #     return "重新输入"

    return workzhuban_cpu_name
# 初始化函数用的 没有很大用处
def initForUser():
    print "欢迎使用刘俊杰项目构建工具" \
          "\n前提：需要在liunx环境上面运行，不能在windows环境上面运行，会出现空格影响shell脚本文件的执行" \
          "\n功能1：只要脚本执行，将进入被动式办公" \
          "\n功能2：半自动帮你填 lk ProjectConfig Buildinfo " \
          "\n话不多说，按下确定键开始吧"
    str1 = raw_input("")
    pathManagerinit()
    copyproject()
    print ""

    return
# 这个是拷贝项目用的
def copyproject():
    print zlZzlhcxPath
    nameForProject = Traverse_elements_dao(zlZzlhcxPath)
    projectNameAndVersion = raw_input("输入你的项目名和版本号用空格分开：")
    projectNameAndVersionList = projectNameAndVersion.split(" ", 1)
    projectName = projectNameAndVersionList[0]
    version = projectNameAndVersionList[1]

    os.system("mkdir "+projectName)
    print "cp -r "+zlZzlhcxPath+'/'+nameForProject+" "+zlZzlhcxPath+'/'+projectName+"/"+version
    os.system("cp -r "+zlZzlhcxPath+'/'+nameForProject+" "+zlZzlhcxPath+'/'+projectName+"/"+version)
    print "拷贝项目成功 "
    return projectNameAndVersionList
# 这个是初始化项目目录用的 很大用处
def pathManagerinit():

    global zlZzlhcxPath
    global Path
    V00VAR = []
    Path = os.popen("pwd").read() # /home/user05/liujunjieWorkBench/mtk6737_8.0_he\n'
    Path = Path[0:Path.index('\n')]
    Path = Path+"/"

    print "------ 正在git status 需要一点时间 "
    zlZzlhcxPath = os.popen("git status").read()
    zlZzlhcxPathList = zlZzlhcxPath.split("\n")

    for i in range(0, len(zlZzlhcxPathList)):
        if zlZzlhcxPathList[i].count("/V0") and zlZzlhcxPathList[i].count("zl"):
            indexofzlpath = zlZzlhcxPathList[i].index("V0")+4
            zlZzlhcxPathList[i] = zlZzlhcxPathList[i][0:indexofzlpath].strip()
            if zlZzlhcxPathList[i].count("modified:"):
                zlZzlhcxPathList[i] = zlZzlhcxPathList[i][zlZzlhcxPathList[i].index("modified:")+9:len(zlZzlhcxPathList[i])].strip()
                # print zlZzlhcxPathList[i]
            if not len(V00VAR) == 0:
                if not V00VAR[(len(V00VAR)-1)] == zlZzlhcxPathList[i]:
                    V00VAR.append(zlZzlhcxPathList[i])
                    #print zlZzlhcxPathList[i]
            else:
                # 初始化 V00VAR
                V00VAR.append(zlZzlhcxPathList[i])
                #print zlZzlhcxPathList[i]
    # 选中项目名称
    V00VARindex = "null"
    for j in range(0,len(V00VAR)):
        print "项目编号 "+str(j)+'-----'+V00VAR[j]
    V00VARindex = raw_input("选中你的项目路径，填写下标就可以了 :")
    zlZzlhcxPath = V00VAR[int(V00VARindex)]+"/"
    print zlZzlhcxPath
    return

def pathManagerinit1():

    global zlZzlhcxPath
    global Path
    boardAndcustomer = ""
    Versionid = ""

    Path = os.popen("pwd").read() # /home/user05/liujunjieWorkBench/mtk6737_8.0_he\n'
    Path = Path[0:Path.index('\n')]
    Path = Path+"/"
    userargs = sys.argv
    if len(userargs) > 1:
        boardAndcustomer = userargs[1]
        boardAndcustomer = os.popen("find zl -name "+boardAndcustomer ).read()
        boardAndcustomer = boardAndcustomer[0:boardAndcustomer.index('\n')]
        #print boardAndcustomer+"edede"
        Versionid = userargs[2]
        print userargs[0],userargs[1],userargs[2]
        zlZzlhcxPath = boardAndcustomer+"/V"+Versionid+"/"
    print zlZzlhcxPath
    return
# 这个是摄像头配置 函数
def cammerakControl():
    print "正在进入后摄像头设置 "
    isaddCamera = "000"
    while not isaddCamera == 'n':
        mainCameraItem = Traverse_elements_dao(Camerapath)
        mainCameraTarger.append(mainCameraItem)
        isaddCamera = raw_input("是否继续添加 y,n:")
        print "你的后摄有"
        for mainCammeraItemI in mainCameraTarger:
            print mainCammeraItemI

    print "正在进入前摄摄像头设置 "
    isaddCamera = "000"
    while not isaddCamera == 'n':
        subCameraItem = Traverse_elements_dao(Camerapath)
        subCameraTarger.append(subCameraItem)
        isaddCamera = raw_input("是否继续添加 y,n:")
        print "你的前摄有"
        for subCammeraItemI in subCameraTarger:
            print subCammeraItemI
    cameraSetting()
    return
# 这个是配置flash用的
def flashtypeControl():
    print "正在配置flash"
    global flashTypeTarger
    flashTypeTarger = Traverse_elements_dao(FalshTypePath)
    flashType()
    return
# 这个是前台配置lcm参数用的
def lcmContral():
    print "正在配置屏幕"
    old_str_onthe_line=[]
    # 这里的返回值就是数组 将第一个参数是所有的lcm 第二个是具体的屏参文件 第三个是序列号
    screenSize = raw_input("正在进入lcm设置：101 8 695 fhd :")
    if is_number(screenSize):
        if int(screenSize) == 101:
            old_str_onthe_line = writeFileforLcm(Lcm101Path)
        elif int(screenSize) == 8:
            old_str_onthe_line = writeFileforLcm(Lcm800CPath)
        elif int(screenSize) == 695:
            old_str_onthe_line = writeFileforLcm(Lcm695Path)
    else:
        if screenSize == "fhd":
            old_str_onthe_line = writeFileforLcm(LcmFHDPath)
        else:
           # print "请选中你的屏幕尺寸"
            return

    lcmSetting(old_str_onthe_line)
    return
# 这个是前台配置tp用的
def tpContral():
    print "正在进入tp配置"
    global tpTarger
    print "第一页，下一页输入exit"
    tpTarger = Traverse_elements_dao(tpPath)
    if tpTarger == "":
        print "第二页，下一页输入exit"
        tpTarger = Traverse_elements_dao(tpPath+"/firmware_863")
        if not tpTarger == "":
            tpTarger = "firmware_863/"+tpTarger
    if tpTarger == "":
        print "第三页，下一页输入exit"
        tpTarger = Traverse_elements_dao(tpPath+"/firmware_hk08")
        if not tpTarger == "":
            tpTarger = "firmware_hk08/"+tpTarger
    if not tpTarger == "":
        tpSetting()
    return
# 这里的内存设置的逻辑需要写的复杂一点 可以把安兔兔什么的做好内存优化
def romAndMemoryContral():
    print "正在进行内存设置"
    MemoryStr1 = raw_input("正在进入内存设置 输入机器的运行内存:")
    RealROMstr1 = raw_input("输入机器的真实内存大小：")
    ShowROMstr1 = raw_input("输入要显示的内存大小：")
    ROMAndMemoryService(RealROMstr1,ShowROMstr1,MemoryStr1)
    return
# 这里是听筒扬声器设置
def APP_CALL_FORCE_SPEAKERContral():
    print "正在进入听筒扬声器设置"
    str1 = raw_input("通话默认扬声器还是听筒 扬声器=1 听筒=2")
    global isoppon
    isoppon = str1
    return
# 这个主体函数主要是寻找相关文件的path路径
def ProjectPathFind():
    userargs = sys.argv
    if len(userargs) > 1:
        pathManagerinit1()
    else:
        pathManagerinit()
    print "----------正在初始化路径---------"
    global ProjectConfigPath
    global BuildinfoPath
    global Defconfig
    global LkPath
    global tpSettingPath
    global kernel_meminfopath
    global Rompath
    global Proinfopath

    ProjectConfigPath = FindResultPathService("ProjectConfig.mk")

    BuildinfoPath = FindResultPathService("buildinfo.sh")

    Defconfig = FindResultPathService("defconfig")

    LkPath = FindResultPathService("lk.mk")

    tpSettingPath = FindResultPathService("gsl_ts_driver.h")

    kernel_meminfopath = FindResultPathService("kernel_meminfo")

    Rompath = FindResultPathService("ROM")
    return
# 谨慎使用find 函数 会大量占用io
def FindResultPathService(FileName):

    resultPath = ""
    FilePath = os.popen("find "+zlZzlhcxPath+' -name ' +FileName).read()
    # print "find "+zlZzlhcxPath+' -name ' +FileName
    # 这里需要考虑到多个文件的切割问题 要切割为一个数组 还有这里是相对路径
    Filelist = FilePath.split("\n")
    Filelist.remove('')
    for i in range(0, len(Filelist)):
        Filelist[i] = Path+"zl"+Filelist[i][2:len(Filelist[i])]
        print i, Filelist[i]
    if len(Filelist) >1 :
        result = raw_input("出现多个重名的文件 ，请选择编号：")
        resultPath = Filelist[int(result)]
    else:
        try:
            resultPath = Filelist[0]
        except:
            resultPath = "未找到 "+FileName
            print resultPath


    return resultPath
# 这个第一个参数为绝对路径，第二个参数为需要复制的项目的绝对路径
def TouchFileAndMakeDirDao(oldFilePath,tocopyFilePathforZl):
    # /home/user05/liujunjieWorkBench/mtk6737_8.0_he/kernel-3.18/drivers/misc/mediatek/imgsensor/src/mt6753/camera_hw/kd_camera_hw.c
    # /home/user05/liujunjieWorkBench/mtk6737_8.0_he/zl/zl809_53w/zl809_53w_weikenuo/V019/other/kernel-3.18/drivers/misc/mediatek/imgsensor/src/mt6753/camera_hw/kd_camera_hw.c
    
    oldFilePathdir = str(oldFilePath)
    oldFilePathdir = oldFilePathdir[0:oldFilePathdir.rindex('/')]
    tocopyFilePathforZldir = tocopyFilePathforZl[0:tocopyFilePathforZl.rindex('/')]
    # print oldFilePathdir
    if not os.path.isdir(tocopyFilePathforZldir):
        os.makedirs(tocopyFilePathforZldir)
        # print "正在创建文件夹 "+tocopyFilePathforZldir
    if os.path.exists(tocopyFilePathforZl):
        print "文件存在："+ tocopyFilePathforZl
        delet = raw_input("是否覆盖 y/n :")
        if delet == "y":
            os.system("rm "+tocopyFilePathforZl)
            print "rm "+tocopyFilePathforZl
            os.system("cp " + oldFilePath + " " + tocopyFilePathforZl)
            gitCheckoutFiles.append(oldFilePath)
        else:
            print "\n"
    else:
        os.system("cp " + oldFilePath+" "+tocopyFilePathforZl)
        print "cp " + oldFilePath+"\n "+tocopyFilePathforZl
        gitCheckoutFiles.append(oldFilePath)
    return gitCheckoutFiles
def TouchFileAndMakeDirContral():
    print "正在检索公版修改的地方"
    # gitCheckoutFiles = []
    gitsize = os.popen("git status").read()
    gitsizeList = gitsize.split("\n")
    for i in range(0, len(gitsizeList)):
        if gitsizeList[i].count("modified:") and gitsizeList[i].count("/") and not gitsizeList[i].count("zl/"):
            indexFormodified = gitsizeList[i].index("modified:") + 10
            gitsizeList[i] = gitsizeList[i][indexFormodified:len(gitsizeList[i])].strip()
            # print Path+gitsizeList[i],Path+zlZzlhcxPath+gitFilesPath[i]
            TouchFileAndMakeDirDao(Path+gitsizeList[i],Path+zlZzlhcxPath+"others/"+gitsizeList[i])

    str1 = raw_input("是否要checkout 复制过去zl目录下面的文件 y/n :")
    if str1 == 'y':
        for checkout in gitCheckoutFiles:
            os.system("git checkout "+checkout)
            print "git checkout "+checkout
            
    return


def git_add_oldfile_eq_newfile():
    print "执行git diff "
    git_add_Files = []
    git_not_add_Files = []
    git_diff_size = os.popen("git diff").read()
    git_diff_List = git_diff_size.split("\n")
    for i in range(0, len(git_diff_List)):
        if i+3<=len(git_diff_List):
            if git_diff_List[i].count("diff --git") and git_diff_List[i+3].count("diff --git"):

                add_file_path_name = git_diff_List[i][git_diff_List[i].index("git a/") + 6:git_diff_List[i].index(" b/")]
                git_add_Files.append(add_file_path_name)

            elif git_diff_List[i].count("diff --git"):
                not_add_file_path_name = git_diff_List[i][git_diff_List[i].index("git a/") + 6:git_diff_List[i].index(" b/")]
                git_not_add_Files.append(not_add_file_path_name)

    print "相同文件但是git diff 显示的文件个数有 "+"\n"+str(len(git_add_Files))
    not_add_str = ""
    for i in range(0, len(git_not_add_Files)):
        not_add_str = not_add_str+git_not_add_Files[i]+"\n"
    print "修改过的文件 git status ："+"\n"+not_add_str

    print "这里可能会有一个未修改的文件，跑到修改的文件里面去，因为他是最后一个判断不了，需要手动判断"
    str1 = raw_input("是否要add 重复的文件夹 y/n :")
    if str1 == 'y':
        for git_add_file_name in git_add_Files:
            os.system("git add " + git_add_file_name)
            print "正在执行 git add "+git_add_file_name
        print "已经add全部添加完毕 ，请手动commit "

    return

# 这个是判断字符串是不是数字
def is_number(s):
    try:
        float(s)
        return True
    except ValueError:
        pass
    try:
        import unicodedata
        unicodedata.numeric(s)
        return True
    except (TypeError, ValueError):
        pass

    return False
# 这个是判断谷歌服务的
def GMSContral():
    global GMSTarger
    str1 = raw_input("是否需要谷歌 y/n：")
    if str1 == 'y':
        GMSTarger = 1
        buildGMS()
    if str1 == 'n':
        GMSTarger = 0
        buildGMS()
    return
# 这个是用修改版本号使用的
def modifyVersionForProjectContral():
    global verno
    print "修改版本号， 例子 ZLP30V1.0_B_HAOJING_03_53_O_YL9881C_BOE_HZY_V1.0_20211225 日期会自动加上去 不需要写日期前面的_ 自动变成大写"
    verno = raw_input("请输入你的版本号：")
    modifyVersionForProject()
    return
# 这个是最后用来展示变量的
def showtarger():
    print "----------------------最后的输出-------------------"
    if len(mainCameraTarger) > 0:
        print "------主摄是："+':'.join(mainCameraTarger)
    else:
        print "------未填写主摄---------------------"
    if len(subCameraTarger) > 0:
        print "------前摄像头是："+':'.join(subCameraTarger)
    else:
        print "------未填写前摄-------------------"
    print "---------tp参数是："+tpTarger
    print "-------flash配置是："+flashTypeTarger
    print "-该机器的屏幕参数是："+lcmTarger +'-------编号是：'+ indexoflcm
    print "--------- 谷歌需求是："+str(GMSTarger)
    print "------------ 版本号是："+verno
    return

def star():
    ProjectPathFind()
    str1 = raw_input("功能控制器，输入你要的功能 tp0 lcm1 gms2 rom3 flash4 camera5 versionId6 zlfileforGitStatus7 数字就可以，用空格隔开:")
    dofunction = str1.split(" ")
    functions = ["tp","lcm","gms","rom","flash","camera","versionId","zlfileforGitStatus"]
    for i in range(0, len(dofunction)):
        if functions[int(dofunction[i])] == "tp":
             tpContral()
        elif functions[int(dofunction[i])] == "lcm":
            lcmContral()
        elif functions[int(dofunction[i])] == "gms":
            GMSContral()
        elif functions[int(dofunction[i])] == "rom":
            romAndMemoryContral()
        elif functions[int(dofunction[i])] == "flash":
            flashtypeControl()
        elif functions[int(dofunction[i])] == "camera":
            cammerakControl()
        elif functions[int(dofunction[i])] == "versionId":
            modifyVersionForProjectContral()
        elif functions[int(dofunction[i])] == "zlfileforGitStatus":
            TouchFileAndMakeDirContral()

    showtarger()
    return

# TouchFileAndMakeDirContral()
git_add_oldfile_eq_newfile()
#star()
# TouchFileAndMakeDirDao("/home/user05/liujunjieWorkBench/mtk6737_8.0_he/kernel-3.18/drivers/misc/mediatek/imgsensor/src/mt6753/camera_hw/kd_camera_hw.c","/home/user05/liujunjieWorkBench/mtk6737_8.0_he/zl/zl809_53w/zl809_53w_weikenuo/V011/others/kernel-3.18/drivers/misc/mediatek/imgsensor/src/mt6753/camera_hw/kd_camera_hw.c")







# 摄像头要优化一下 有问题

# 这个脚本文件的位置还是需要在根目录 需要联合git 和用户输出来确定需要操作的zl目录
# 还需要有一个控制器，来接受脚本的参数，来执行相对应的方法
# 还需要有一个客制化的功能来完成一些基本操作，比如加apk 加上输入法 加壁纸 ，logo
# 屏幕方面还要改一下，一般是10寸啥的需要改分辨率 1920x444 wxga
# 需要修改脚本选择项目的方式 还是采用 lhcx.sh 的方式
# 还需要就是将项目分离开分成 3 个部分 一个是中性文件夹 一个是客户文件夹 一个是需求文件夹
# 需求文件夹建好路径 客户文件夹再建好路径
