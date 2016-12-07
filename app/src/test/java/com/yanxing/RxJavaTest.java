package com.yanxing;

import org.junit.Test;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.functions.Func1;


/**
 * RxJava
 * Created by lishuangxiang on 2016/5/9.
 */
public class RxJavaTest {

    @Test
    public void testRxJava1(){
        //被观察者
        Observable<String> myObservable= Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        subscriber.onNext("Hello,World");
                        subscriber.onCompleted();
                    }
                }
        );
        //观察者
        Subscriber<String> subscriber=new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                System.out.println(s);

            }
        };
        myObservable.subscribe(subscriber);
    }

    @Test
    public void testRxJava2(){
        //testRxJava1简化后的代码
        Observable<String> observable=Observable.just("Hello,World");
        Action1<String> action1=new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        };
        Action2<String,String> action2=new Action2<String,String>() {
            @Override
            public void call(String s,String s2) {
                System.out.println(s);
            }
        };
//        observable.subscribe((Action1<? super String>) action2);
    }

    @Test
    public void testRxJava3(){
        //再简化后的代码
        Observable.just("Hello,World").subscribe(s -> System.out.println(s));
    }

    @Test
    public void testRxJava4(){
        Observable.just("Hello,World")
//                .map(new Func1<String, String>() {
//                    @Override
//                    public String call(String s) {
//                        return s+"yanxing";
//                    }
//                })
                .map(s -> s+"yanxing")
                .subscribe(s -> System.out.println(s));
    }

    @Test
    public void testRxJava5(){
        Observable.just("Hello,World")
                .map(s -> s.hashCode())
                .map(i->Integer.toString(i))
                .subscribe(s1 -> System.out.println(s1));
    }

    @Test
    public void testRxJava6(){
//        query("Hello,World")
//                .subscribe(s->{
//                    Observable.from(s)
//                            .subscribe(s1 -> System.out.println(s1));
//                });
        query("Hello,World")
                .flatMap(list -> Observable.from(list))
        .subscribe(url->System.out.println(url));
    }

    public Observable<List<String>> query(String text){
       return null;
    };
}
