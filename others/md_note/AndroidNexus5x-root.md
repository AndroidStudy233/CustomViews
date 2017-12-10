
#Android -> Nexus 5x ROOT基础教程

> 预备文件: 

> 1. twrp.img (第三方REC) 
> 2. SuperSU.zip	(权限管理包)
> 3. 预备环境: AndroidSDK (配置环境变量)

##一、解锁手机的BootLoader.

1. 开发者模式中解锁OEM引导, 开启USB调试, 然后关机

2. 同时按住电源键+音量下键，进入bootloader界面

3. 连接USB手机到电脑

4. dos窗执行命令:

	fastboot oem unlock

	然后用音量键选为“Yes”，按一下“电源键”确认

5. 解锁成功.


## 二、刷入twrp.img

1. 同时按住电源键+音量下键，进入bootloader界面

2. 连接USB手机到电脑

3. twrp.img所在路径下dos窗口执行命令

	fastboot flash recovery twrp.img

4. 刷入成功后关机, 重新执行步骤1, 然后音量键选择进入Recovery Mode, 这样会进入twrp下.

5. 进入twrp即成功

##三、写入SuperSU.

1. 在第二步的twrp下会询问密码, 不用管直接cancel.

2. 在条目中找到Advanced 点击然后找到 adb sideload 点击然后 swipe(滑), 进入sideload模式

3. 连接USB手机到电脑

4. SuperSU2所在路径dos窗执行命令

	adb sideload SuperSU.zip

5. 执行成功之后Reboot System. 

6. OVER
