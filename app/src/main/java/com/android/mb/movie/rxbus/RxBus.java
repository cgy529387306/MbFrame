package com.android.mb.movie.rxbus;

import java.util.HashMap;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subscriptions.CompositeSubscription;

/**
 * @Description
 * @Created by cgy on 2017/6/23
 * @version v1.0
 *
 */

public class RxBus {

    private static volatile RxBus mInstance;
    private SerializedSubject<Events<?>,Events<?>> mSubject;
    private HashMap<String,CompositeSubscription> mSubscriptionMap;

    private RxBus(){
        mSubject = new SerializedSubject<Events<?>,Events<?>>(PublishSubject.<Events<?>>create());
    }

    /**
     * 单例模式RxBus
     *
     * @return
     */
    public static RxBus getInstance(){

        if (mInstance == null) {
            synchronized (RxBus.class){
                if (mInstance == null) {
                    mInstance = new RxBus();
                }
            }
        }
        return mInstance;
    }

    public void send(Events<?> o) {
        mSubject.onNext(o);
    }


    public void send(int code, Object content){
        Events<Object> event = new Events<>();
        event.code = code;
        event.content = content;
        send(event);
    }


    /**
     * 接收消息,返回指定的Observable实例
     *
     * @param
     * @return
     */
    public Observable<Events<?>> toObserverable(){
        return mSubject;
    }

    /**
     * 是否已有观察者
     * @return
     */
    public boolean hashObservers(){
        return mSubject.hasObservers();
    }



    /**
     * 保存订阅后的subscription
     * @param o
     * @param subscription
     */
    public void addSubscription(Object o, Subscription subscription){
        if(mSubscriptionMap == null){
            mSubscriptionMap = new HashMap<>();
        }

        //以类名作为key标识
        String key = o.getClass().getName();
        if(mSubscriptionMap.get(key) != null){
            mSubscriptionMap.get(key).add(subscription);
        }else {
            CompositeSubscription compositeSubscription = new CompositeSubscription();
            compositeSubscription.add(subscription);
            mSubscriptionMap.put(key,compositeSubscription);
        }
    }

    public static SubscriberBuilder init(){
        return new SubscriberBuilder();
    }

    /**
     * 取消订阅
     * @param o
     */
    public void unSubscribe(Object o){
        if(mSubscriptionMap == null){
            return;
        }

        String key = o.getClass().getName();
        if(!mSubscriptionMap.containsKey(key)){
            return;
        }

        if(mSubscriptionMap.get(key) != null){
            mSubscriptionMap.get(key).unsubscribe();
        }

        mSubscriptionMap.remove(key);
    }

    public static class SubscriberBuilder{

        private int event;
        private Action1<? super Events<?>> onNext;
        private Action1<Throwable> onError;

        public SubscriberBuilder setEvent(int event){
            this.event = event;
            return this;
        }

        public SubscriberBuilder onNext(Action1<? super Events<?>> action){
            this.onNext = action;
            return this;
        }
        public SubscriberBuilder onError(Action1<Throwable> action){
            this.onError = action;
            return this;
        }


        public void create(Object o){
            Subscription subscription = _create();
            RxBus.getInstance().addSubscription(o,subscription);

        }


        public Subscription _create(){

            return RxBus.getInstance().toObserverable()
                    //过滤 根据code判断返回事件
                    .filter(new Func1<Events<?>, Boolean>() {
                        @Override
                        public Boolean call(Events<?> events) {
                            return events.code == event;
                        }
                    })
                    .subscribe(onNext,onError == null? new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }:onError);
        }
    }
}
