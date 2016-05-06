# NdPresentation

## 拥抱源码

目标，编码规范，实现思路；模块化思想，不必抠细节。

- Android 异步框架

包括 `Handler`, `Looper`, `Message`, `AsyncTask`, `IntentService`

- View 的事件分发机制

## Application 并不可靠

Android 会尽可能长时间的保留应用进程，保证用户下次回到应用有更好的流畅度。Android 进程按照优先级可以分为5个层级：

1. 前台进程（可见可交互）
2. 可见进程（可见不可交互）
3. 服务进程
4. 后台进程
5. 空进程

## Fragment 事务异常

### 为什么选择 Fragment？

- 与 ViewPager 搭配使用
- 更轻（页面切换速度）
- 需要适配大屏
- FragmentDialog
- RetainFragment 转屏保存数据

代价，复杂度更高。生命周期复杂；不明所以的异常；原则，尽量使用 Activity 而非 Fragment。

### 异常信息

```
FATAL EXCEPTION: main
Process: com.example.administrator.myapplication, PID: 16760
java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
    at android.support.v4.app.FragmentManagerImpl.checkStateLoss(FragmentManager.java:1493)
    at android.support.v4.app.FragmentManagerImpl.enqueueAction(FragmentManager.java:1511)
    at android.support.v4.app.BackStackRecord.commitInternal(BackStackRecord.java:638)
    at android.support.v4.app.BackStackRecord.commit(BackStackRecord.java:617)
    at com.example.administrator.myapplication.MainActivity$SimpleTask.onPostExecute(MainActivity.java:103)
    at com.example.administrator.myapplication.MainActivity$SimpleTask.onPostExecute(MainActivity.java:90)
    at android.os.AsyncTask.finish(AsyncTask.java:632)
    at android.os.AsyncTask.access$600(AsyncTask.java:177)
    at android.os.AsyncTask$InternalHandler.handleMessage(AsyncTask.java:645)
    at android.os.Handler.dispatchMessage(Handler.java:102)
    at android.os.Looper.loop(Looper.java:135)
    at android.app.ActivityThread.main(ActivityThread.java:5221)
    at java.lang.reflect.Method.invoke(Native Method)
    at java.lang.reflect.Method.invoke(Method.java:372)
    at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:899)
    at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:694)
```

### 异常原因

当 Fragment 执行事务的时刻在 Activity#onSaveInstanceState() 之后，就会抛出上述异常。这里是时间前后的关系，与是否调用 Activity#onSaveInstanceState() 无关。避免这个异常的方法也很简单，就是确保 Fragment 事务在 onSaveInstanceState() 之前执行完。

对于 onSaveInstanceState() 回调，在什么时候回调呢？

文档关于 onSaveInstanceState() 的说明如下：

> If called, this method will occur before onStop(). There are no guarantees about whether it will occur before or after onPause().

具体的调用时机和 Android 版本有关。

## 转屏测试

关注点：

- 上下文恢复是否正常；
- 应用健壮性（转屏和应用被系统回收的场景比较契合，例如都会进行现场保存和恢复）

## 参考

- [Don't Store Data in the Application Object](http://www.developerphil.com/dont-store-data-in-the-application-object/)
- [Android development: What I wish I had known earlier](http://balpha.de/2013/07/android-development-what-i-wish-i-had-known-earlier/)
- [Fragment Transactions & Activity State Loss](http://www.androiddesignpatterns.com/2013/08/fragment-transaction-commit-state-loss.html)
- [进程和线程](http://developer.android.com/intl/zh-cn/guide/components/processes-and-threads.html)
