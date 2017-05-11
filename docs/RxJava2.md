# Welcome to RxJava2

## Why

- RxJava2 完全遵循 Reactive-Streams 规范进行重写；
- 引入一套新的 Api，解决了 RxJava 中的背压问题；
- 2017.7.1－－不再支持新特性兼容，只修复遗留 BUG；
- 2018.3.31－－不再开发与维护。

> [Backpressure](https://github.com/ReactiveX/RxJava/wiki/Backpressure)

![](../extra/QQ20170511-0@2x.png)

当数据的生产速度大于数据的消费速度，RxJava 就会面临背压问题。内存数据超过 128 个时将会抛出 MissingBackpressureException 异常。

## 区别
### 仓库地址与包名

```
ompile "io.reactivex.rxjava2:rxjava:2.x.y"
```

### Nulls

RxJava 可以将 null 作为一个数据项 next 出去，在 RxJava2 中，以下流都将直接抛 NPT 异常：

```
Observable.just(null);

Single.just(null);

Observable.fromCallable(() -> null)
    .subscribe(System.out::println, Throwable::printStackTrace);

Observable.just(1).map(v -> null)
    .subscribe(System.out::println, Throwable::printStackTrace);
```

### 被观察者 Observable 与 Flowable

为了解决 RxJava 中的背压问题，RxJava2 引入了一套新的被观察者－－ Flowable。

在 RxJava2 中：

- Observable 会将没有消费的数据保存在内存中直到 OutOfMemoryError 而不会抛出 MissBackpressureException；
- Flowable 支持背压，可以在 Flowable.create() 创建时指定背压策略。如：BackpressureStrategy.DROP。

#### 使用场景

##### 什么时候使用 Observable？
- 流的数据不超过 1k 个
- 接受 UI 事件，比如：鼠标移动或点击事件

##### 什么时候使用 Flowable？
- 流的数据超过 10k 个
- 一系列可能阻塞消费的操作（解析文件、网络请求或数据库操作等）
－ 目前是阻塞的将来可能变成非阻塞的数据源

### 观察者新成员

RxJava2 整个架构严格按照 Reactive-Streams 规范设计, 基本的消费者类型都改为了接口。

#### Single

```
interface SingleObserver<T> {
    void onSubscribe(Disposable d);
    void onSuccess(T value);
    void onError(Throwable error);
}
```

Single 只关心调用成功后对数据的处理，订阅之后只能接收到一次数据。如果数据发射成功则调用 onSuccess，发射失败调用 onError。
普通 Observable 可以使用 toSingle 转换：

```
Observable.just(1).toSingle()
```

#### Completable
```
interface CompletableObserver<T> {
    void onSubscribe(Disposable d);
    void onComplete();
    void onError(Throwable error);
}
```

Completable 只关心是否调用成功。同样也可以由普通的 Observable 转换而来：
```
Observable.just(1).toCompletable()
```

#### Maybe
Maybe 可能出现无数据或只有一个数据的情况，所以 onSuccess() 和 onComplete() 只会调用其中一个。可以理解成是 Single 和 Completable 的结合体。适用于可能发射 1 个或者 0 个的数据源。

### 响应式接口

与 Reactive-Streams 中 Flowable extends Publisher<T> 风格一样，其他基本响应类也有类似的基础接口：

```
interface ObservableSource<T> {
    void subscribe(Observer<? super T> observer);
}

interface SingleSource<T> {
    void subscribe(SingleObserver<? super T> observer);
}

interface CompletableSource {
    void subscribe(CompletableObserver observer);
}

interface MaybeSource<T> {
    void subscribe(MaybeObserver<? super T> observer);
}
```

> 可订阅的对象在 RxJava 中只有 Observable 一种，之前我们经常会直接把数据源称作 Observable。
> 而在 RxJava2 中扩充成了 4 种，因此在之后还是把他们统称为数据源为宜。

















