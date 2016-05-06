# ND Presentation

## 为什么要阅读源码

- 知其然，知其所以然。了解底层，更好地服务上层
- 优秀的代码风格和设计理念，作为编码准则，尽量模仿
- 了解 Android 设计者的意图。

具体到工作中来，如果理解了框架层中的运行原理，提高定位 Bug 的效率。

## 异步框架

### 三大件



Android 异步框架由 `Handler`, `Looper`, `Message` 三部分组成。其中，`Message` 是一个单链表，负责存储消息；`Handler` 往消息队列 `Message` 发送消息；`Looper` 不断从消息队列里读取消息，如果有新的消息到达，取出消息，并分发给 `Handler` 预先定义好的 Hook 函数去处理。

除了上述三部分，完成线程切换的秘诀在于 `ThreadLocal`。简单的说，`ThreadLocal` 允许我们通过同一个对象在不同线程中存储不同的数据。具体到 Android 异步框架，就是存储每个线程的 `Looper` 对象。

### HandlerThread

### AsyncTask

### IntentService 

## 参考
