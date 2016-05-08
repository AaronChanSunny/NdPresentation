# ND Presentation

## 为什么要阅读源码
presentation-list.xmind
- 知其然，知其所以然。了解底层，更好地服务上层。
- 优秀的代码风格和设计理念。作为编码准则，尽量模仿，缩小差距。
- 了解 Android 设计者的意图。
- 更准确、快速地定位 Bug。

## 异步框架

Android 的 UI 控件并不是线程安全的，也就说只能在 UI 线程也就说主线程更新 UI，如果在非 UI 线程更新 UI，则可能导致异常。另外一方面，Android 又要求不能在 UI 线程执行耗时的任务。然而，我们在开发 App 过程中，经常面对的场景是，处理一段耗时的逻辑，处理完成后将结果呈现给 UI，这就产生了矛盾，因此有了 Android 异步框架。开发者可以在工作线程进行耗时的操作，待耗时操作完成后，通过异步框架将线程切换到 UI 线程，呈现操作结果。

Android 异步框架由 `Handler`, `Looper`, `Message` 三部分组成。其中，`Message` 是一个单链表，负责存储消息；`Handler` 往消息队列 `Message` 发送消息；`Looper` 不断从消息队列里读取消息，如果有新的消息到达，取出消息，并分发给 `Handler` 预先定义好的 Hook 函数去处理。

除了上述三部分，完成线程切换的秘诀在于 `ThreadLocal`。简单的说，`ThreadLocal` 允许我们通过同一个对象在不同线程中存储不同的数据。具体到 Android 异步框架，就是存储每个线程的 `Looper` 对象。

![](screenshots/handler-message-looper.png)

![](screenshots/android-async-uml.png)

### AsyncTask

AsyncTask 是 Google 为了简化 Android 中的异步编程而封装出的一个类。AsyncTask 实际上用到还是 Android 异步框架，只是 Google
将实现细节隐藏了，通过暴露几个关键的方法，让开发者很容易实现自己的异步任务，简化了实现异步任务的复杂性和可控制性。

AsyncTask 最基本的使用方法很简单，创建一个任务类，使这个类继承自 AsyncTask类，重写 AsyncTask#onPreExecute(), AsyncTask#doInBackground(), AsyncTask#onProgressUpdate(), AsyncTask#onPostExecute()，然后调用 AsyncTask#execute() 方法开始异步任务，这样我们就不必定义 Handler，不必发送消息和处理消息，降低了异步编程的复杂度。

AsyncTask#execute() 返回的是 AsyncTask 引用，通过这个引用我们可以在适当时候比如在 Activity 的某个生命周期方法中取消这个任务，提高了异步任务的可控制性。

这里，我们试着解释几个结论：

- AsyncTask#onPreExecute() 运行在调用 AsyncTask#execute() 所在的线程。
- AsyncTask#doInBackground() 运行在工作线程；
- AsyncTask#onProgressUpdate() 运行在 UI 线程；
- AsyncTask#onPostExecute() 运行在主线程；
- 永远不要手动调用上述方法。

后面三个都没问题，但是关于第一个结论，一直以来我一直以为 AsyncTask#onPreExecute() 也是运行在 UI 线程，因为 Android 官方文档给出的解释：

> Runs on the UI thread before doInBackground(Params...).

其实，通过源码可以发现，这个结论是错的。AsyncTask#execute() 最终会调用 AsyncTask#executeOnExecutor(), 让我们重新看看这个方法的具体代码：

```
if (mStatus != Status.PENDING) {
    switch (mStatus) {
        case RUNNING:
            throw new IllegalStateException("Cannot execute task:"
                    + " the task is already running.");
        case FINISHED:
            throw new IllegalStateException("Cannot execute task:"
                    + " the task has already been executed "
                    + "(a task can be executed only once)");
    }
}
mStatus = Status.RUNNING;
onPreExecute();
mWorker.mParams = params;
exec.execute(mFuture);
return this;
```

可以看出，在任务开始之前，只是简单地调用 AsyncTask#onPreExecute() 通知外部，所以 AsyncTask#onPreExecute() 所在的线程和调用 AsyncTask#execute() 是同一个线程，只是我们一般都在 UI 线程启动 AsyncTask，所以一般都认为 AsyncTask#onPreExecute() 运行在主线程。

### HandlerThread

如果要在一个线程中初始化一个 Handler 对象必须有一个前提条件，那就是该线程具有 Looper 循环。如果在一个没有 Looper 循环的线程中获取 Handler 对象，将抛出如下异常：

```
FATAL EXCEPTION: #SimpleThread
Process: me.aaronchan.ndpresentation, PID: 19566
java.lang.RuntimeException: Can't create handler inside thread that has not called Looper.prepare()
    at android.os.Handler.<init>(Handler.java:200)
    at android.os.Handler.<init>(Handler.java:114)
    at me.aaronchan.ndpresentation.HandlerCreationActivity$SimpleThread.run(HandlerCreationActivity.java:49)
```

那么，HandlerThread 和普通的 Thread 有什么区别呢？其实它们之间的区间就在于 HandlerThread 是一个带有消息循环 Looper 的
Thread。换句话说，我们可以在 HandlerThread 中直接创建 Handler。一起看看 HandlerThread 具体是怎么是实现的，关键在 HandlerThread#run() 方法：

```
@Override
public void run() {
    mTid = Process.myTid();
    Looper.prepare();
    synchronized (this) {
        mLooper = Looper.myLooper();
        notifyAll();
    }
    Process.setThreadPriority(mPriority);
    onLooperPrepared();
    Looper.loop();
    mTid = -1;
}
```

在 HandlerThread#run() 方法里，首先使用 Looper.prepare() 初始化消息循环 Looper，通过 onLooperPrepared() 通知外界，Looper 初始化完成，然后就直接开启消息循环 Looper.loop()。所以，之所以我们能够在 HandlerThread 中创建 Handler，是因为 HandlerThread 已经默认为我们创建并开启了一个消息循环。这和 Android 主线程 [ActivityThread](https://github.com/android/platform_frameworks_base/blob/master/core/java/android/app/ActivityThread.java#L5472) 的情况是类似的。

### IntentService 

## 参考

- Android 开发艺术探索
