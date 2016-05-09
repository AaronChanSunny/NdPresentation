# Nd Presentation

## 异步框架

`Android` 的 `UI` 控件并不是线程安全的，也就说只能在 `UI` 线程也就说主线程更新 `UI`，如果在非 `UI` 线程更新 `UI`，则可能导致异常。另外一方面，`Android` 又要求不能在 `UI` 线程执行耗时的任务。然而，我们在开发 `App` 过程中，经常面对的场景是，处理一段耗时的逻辑，处理完成后将结果呈现给 `UI`，这就产生了矛盾，因此有了 `Android` 异步框架。开发者可以在工作线程进行耗时的操作，待耗时操作完成后，通过异步框架将线程切换到 `UI` 线程，呈现操作结果。

`Android` 异步框架由 `Handler`, `Looper`, `Message` 三部分组成。其中，`Message` 是一个单链表数据结构，负责存储消息；`Handler` 往消息队列 `Message` 发送消息；`Looper` 不断从消息队列里读取消息，如果有新的消息到达，取出消息，并分发给 `Handler` 预先定义好的 `Hook` 函数去处理。

除了上述三部分，完成线程切换的秘诀在于 `ThreadLocal`。简单的说，`ThreadLocal` 允许我们通过同一个对象在不同线程中存储不同的数据。具体到 `Android` 异步框架，就是存储每个线程的 `Looper` 对象。

![](screenshots/handler-message-looper.png)

![](screenshots/android-async-uml.png)

### AsyncTask

`AsyncTask` 是 `Google` 为了简化 `Android` 中的异步编程而封装出的一个类。`AsyncTask` 实际上用到还是 `Android` 异步框架，只是 `Google` 将实现细节隐藏了，通过暴露几个关键的方法，让开发者很容易实现自己的异步任务，简化了实现异步任务的复杂性和可控制性。

`AsyncTask` 最基本的使用方法很简单，创建一个任务类，使这个类继承自 `AsyncTask` 类，重写下面四个方法：

- `AsyncTask#onPreExecute()`
- `AsyncTask#doInBackground()`
- `AsyncTask#onProgressUpdate()`
- `AsyncTask#onPostExecute()`
 
然后调用 `AsyncTask#execute()` 方法开始异步任务，这样我们就不必定义 `Handler`，不必发送消息和处理消息，降低了异步编程的复杂度。

`AsyncTask#execute()` 返回的是 `AsyncTask` 引用，通过这个引用我们可以在适当时候比如在 `Activity` 的某个生命周期方法中取消这个任务，提高了异步任务的可控制性。

![](screenshots/asynctask.png)

这里，我们试着解释几个结论：

- `AsyncTask#onPreExecute()` 运行在调用 `AsyncTask#execute()` 所在的线程。
- `AsyncTask#doInBackground()` 运行在工作线程；
- `AsyncTask#onProgressUpdate()` 运行在 `UI` 线程；
- `AsyncTask#onPostExecute()` 运行在主线程；
- 永远不要手动调用上述方法。

后面三个都没问题，但是关于第一个结论，一直以来我一直以为 `AsyncTask#onPreExecute()` 也是运行在 `UI` 线程，因为 `Android` 官方文档给出的解释：

> Runs on the UI thread before doInBackground(Params...).

其实，通过源码可以发现，这个结论是错的。`AsyncTask#execute()` 最终会调用 `AsyncTask#executeOnExecutor()`, 让我们重新看看这个方法的具体代码：

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

可以看出，在任务开始之前，只是简单地调用 `AsyncTask#onPreExecute()` 通知外部，所以 `AsyncTask#onPreExecute()` 所在的线程和调用 `AsyncTask#execute()` 是同一个线程，只是我们一般都在 `UI` 线程启动 `AsyncTask`，所以一般都认为 `AsyncTask#onPreExecute()` 运行在主线程。

### HandlerThread

如果要在一个线程中初始化一个 `Handler` 对象必须有一个前提条件，那就是该线程具有 `Looper` 循环。如果在一个没有 `Looper` 循环的线程中获取 `Handler` 对象，将抛出如下异常：

```
FATAL EXCEPTION: #SimpleThread
Process: me.aaronchan.ndpresentation, PID: 19566
java.lang.RuntimeException: Can't create handler inside thread that has not called Looper.prepare()
    at android.os.Handler.<init>(Handler.java:200)
    at android.os.Handler.<init>(Handler.java:114)
    at me.aaronchan.ndpresentation.HandlerCreationActivity$SimpleThread.run(HandlerCreationActivity.java:49)
```

那么，`HandlerThread` 和普通的 `Thread` 有什么区别呢？其实它们之间的区间就在于 `HandlerThread` 是一个带有消息循环 `Looper` 的 `Thread`。换句话说，我们可以在 `HandlerThread` 中直接创建 `Handler`。一起看看 `HandlerThread` 具体是怎么是实现的，关键在 `HandlerThread#run()` 方法：

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

在 `HandlerThread#run()` 方法里，首先使用 `Looper.prepare()` 初始化消息循环 `Looper`，通过 `onLooperPrepared()` 通知外界，`Looper` 初始化完成，然后就直接开启消息循环 `Looper.loop()`。所以，之所以我们能够在 `HandlerThread` 中创建 `Handler`，是因为 `HandlerThread` 已经默认为我们创建并开启了一个消息循环。这和 `Android` 主线程 [ActivityThread](https://github.com/android/platform_frameworks_base/blob/master/core/java/android/app/ActivityThread.java#L5472) 的情况是类似的。

### IntentService 

`IntentService` 是一个 `Service` 子类，其内部有一个工作线程用来处理异步任务，当异步任务结束后 `IntentService` 会自动停止。通常情况用在应用后台数据下载。

![](screenshots/intentservice.png)

当然，我们也可以直接开一个工作线程完成上述任务，`IntentService` 的优势在哪里呢？在 Andriod 中，进程按照重要程度可以分为以下5个等级：

1. 前台进程（可见可交互）
2. 可见进程（可见不可交互）
3. 服务进程
4. 后台进程
5. 空进程

Android 系统将尽量长时间地保持应用进程，但是当系统资源不足时，Android 就会按照上述优先级，从低到高依次回收进程，释放系统资源以确保高优先级进程的正常运行。
 
这里需要补充一点，一个进程的优先级是由该进程内的所有线程的最高优先级确定的。`IntentService` 归根到底是四大组件之一 `Service`，因此在系统资源优先级上要高于普通的工作线程，因此如果使用 `IntentService` 可以提高整个应用进程的优先级。如果应用中的某些数据比较重要，开发者不希望后台在下载这些数据时线程被回收，可以考虑使用 `IntentService` 来提高后台任务的优先级。

其实 Android 的四大组件都是运行在主线程的，`Service` 同样也是。那么，`IntentService` 的内部是如何工作的呢？它是怎么让 `Service` 具有完成异步任务的功能？其实，`IntentService` 内部非常重要的一个角色就是之前介绍的 `HandlerThread`。首先，我们看看 `IntentService#onCreate()` 方法：

```
@Override
public void onCreate() {
    // TODO: It would be nice to have an option to hold a partial wakelock
    // during processing, and to have a static startService(Context, Intent)
    // method that would launch the service & hand off a wakelock.
    super.onCreate();
    HandlerThread thread = new HandlerThread("IntentService[" + mName + "]");
    thread.start();
    mServiceLooper = thread.getLooper();
    mServiceHandler = new ServiceHandler(mServiceLooper);
}
```

在 `IntentService#onCreate()` 方法里做了3件事情：

- 创建了一个 `HandlerThread` 线程，作为工作线程
- 启动 `HandlerThread` 线程
- 通过 `HandlerThread` 的 `Looper` 创建 `mServiceHandler`

上述步骤结束后，我们就得到了一个 `Handler` 对象 `mServiceHandler`，通过它发送的消息都会在工作线程中进行处理。接下来，看看 `IntentService` 的几个主要生命周期方法都做了什么？启动服务 `onStartCommand()` 方法：

```
@Override
public int onStartCommand(Intent intent, int flags, int startId) {
    onStart(intent, startId);
    return mRedelivery ? START_REDELIVER_INTENT : START_NOT_STICKY;
}
```

这边是直接调用 `onStart()` 方法，跳转到 `onStart()` 方法：

```
@Override
public void onStart(Intent intent, int startId) {
    Message msg = mServiceHandler.obtainMessage();
    msg.arg1 = startId;
    msg.obj = intent;
    mServiceHandler.sendMessage(msg);
}
```

到这一步就很清楚了，当我们开始 `IntentService` 时，我们会将外部传进来的 `Intent` 对象作为 `Message` 的 `obj`，并通过 `mServiceHandler` 发送消息。这里还需要传递一个参数 `startId`，`IntentService` 之所以可以在异步任务结束后自动停止，就是要根据这个参数调用 `Service#stopSelf()`。

当 `mServiceHandler` 从工作线程的消息队列取出消息后，会调用 `IntentService#onHandleIntent()` 抽象方法，异步任务的具体操作逻辑都在这里。

## 为什么要阅读源码

- 知其然，知其所以然。了解底层，更好地服务上层。
- 优秀的代码风格和设计理念。作为编码准则，尽量模仿，缩小差距。
- 了解 `Android` 设计者的意图。
- 更准确、快速地定位 **Bug**。

## 参考

- **Android** 开发艺术探索
