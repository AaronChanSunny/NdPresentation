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

- 更轻（页面切换速度）
- 需要适配大屏
- FragmentDialog
- RetainFragment 转屏保存数据
- 

代价，复杂度更高。生命周期复杂；不明所以的异常；原则，根据业务决定是否采用 Fragment。

## 参考

[Don't Store Data in the Application Object](http://www.developerphil.com/dont-store-data-in-the-application-object/)
[Android development: What I wish I had known earlier](http://balpha.de/2013/07/android-development-what-i-wish-i-had-known-earlier/)
[Fragment Transactions & Activity State Loss](http://www.androiddesignpatterns.com/2013/08/fragment-transaction-commit-state-loss.html)
[进程和线程](http://developer.android.com/intl/zh-cn/guide/components/processes-and-threads.html)
