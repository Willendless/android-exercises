# Android Course Exercises

这里记录了我21年春季学期（大四下）在ECNU上移动应用开发课（Android）的期末复习练习。

+ activity
  + manifest注册
+ content provider
  + 通信录: contentResolver
+ service
  + manifest注册
  + onCreate
  + onStartCommand
  + onDestroy
  + 通过binder通信
+ 广播
  + 注册
  + BroadcastReceiver: onReceive
+ 通知
  + NotificationManager: 注册通知、notify
  + pendingIntent
  + NotificationCompat
+ 数据库
  + SQLiteOpenHelper
+ fragment
  + 实例化
  + fragmentManager通过事务添加fragment

## 要点记录

+ [x] Exer1
  + 活动切换
  + Linearlayout; 下拉框spinner、ArrayAdapter; 单选框RadioGroup
  + 生命周期
+ [x] Exer2
  + 强行建立Http连接
  + json对象的parse
+ [x] Exer3
  + 运行时权限获取
  + 通过content Provider通讯录信息多表查询
  + 通过Intent拨打电话
  + ListView简单使用
+ [x] Exer4
  + 地图jar包和环境配置，地图权限获取
  + 位置信息获取: LocationClient
  + 地图view更新: mapView.map.animateMapStatus(LatLng)
+ [x] Exer5
  + (显示/隐式)广播，广播接收者的(静态/动态)注册
  + 通知channel注册、通知跳转: pendingIntent
+ [x] Exer6
  + 左滑菜单: DrawerLayout
  + webview: 强行许可Http连接
+ [x] Exer7
  + service
  + service中子线程周期性向主线程发送ui更新命令，同时同步退出
+ [x] Exer8
  + RecyclerView使用
  + 文件存储
  + AlterDialog简单使用
+ [x] Exer9
  + 数据库curd
  + 权限
  + 通讯录访问
+ [x] Exer10
  + 数据库curd
  + runOnUiThread
  + 生成随机字符串
+ [x] Exer11
  + 数据库curd
  + service和activity通过binder通信，同时子线程同步退出
