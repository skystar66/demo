package com.liveme.demo.util;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoCallback;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.esotericsoftware.kryo.serializers.CollectionSerializer;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.io.*;
import java.util.*;

/**
 * KryoUtils序列化和反序列化操作
 官方文档：
 中文：https://blog.csdn.net/fanjunjaden/article/details/72823866
 英文：https://github.com/EsotericSoftware/kryo
 */
public class KryoUtils  {
    /* Kryo有三组读写对象的方法
     * 1.如果不知道对象的具体类，且对象可以为null： kryo.writeClassAndObject(output, object); Object object = kryo.readClassAndObject(input);
     * 2.如果类已知且对象可以为null： kryo.writeObjectOrNull(output, someObject); SomeClass someObject = kryo.readObjectOrNull(input, SomeClass.class);
     * 3.如果类已知且对象不能为null:  kryo.writeObject(output, someObject); SomeClass someObject = kryo.readObject(input, SomeClass.class);
     */

    /**
     * （池化Kryo实例）使用ThreadLocal
     */
    private static final ThreadLocal<Kryo> kryos = new ThreadLocal<Kryo>() {
        @Override
        protected Kryo initialValue() {
            Kryo kryo = new Kryo();
            /**
             * 不要轻易改变这里的配置,更改之后，序列化的格式就会发生变化，
             * 上线的同时就必须清除 Redis 里的所有缓存，
             * 否则那些缓存再回来反序列化的时候，就会报错
             */
            //支持对象循环引用（否则会栈溢出）
            kryo.setReferences(true); //默认值就是 true，添加此行的目的是为了提醒维护者，不要改变这个配置
            //不强制要求注册类（注册行为无法保证多个 JVM 内同一个类的注册编号相同；而且业务系统中大量的 Class 也难以一一注册）
            kryo.setRegistrationRequired(false); //默认值就是 false，添加此行的目的是为了提醒维护者，不要改变这个配置
            //Fix the NPE bug when deserializing Collections.
            ((Kryo.DefaultInstantiatorStrategy) kryo.getInstantiatorStrategy()).setFallbackInstantiatorStrategy(new StdInstantiatorStrategy());
            return kryo;
        }
    };
    /**
     * （池化Kryo实例）使用KryoPool
     */
    private static KryoFactory factory = new KryoFactory() {
        public Kryo create () {
            Kryo kryo = new Kryo();
            return kryo;
        }
    };
    private static KryoPool pool = new KryoPool.Builder(factory).softReferences().build();



    /**
     * 使用ThreadLocal创建Kryo
     * 把java对象序列化成byte[];
     * @param obj java对象
     * @return
     */
    public static <T>  byte[] serializeObject(T obj) {
        ByteArrayOutputStream os=null;
        Output output=null;
        if(null != obj){
            Kryo kryo = kryos.get();
            try {
                os = new ByteArrayOutputStream();
                output = new Output(os);
                kryo.writeObject(output, obj);
                close(output);
                return os.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                close(os);

            }
        }
        return null;
    }

    /**
     * 使用ThreadLocal创建Kryo
     * 把byte[]反序列化成指定的java对象
     * @param bytes
     * @param t 指定的java对象
     * @param <T>
     * @return 指定的java对象
     */
    public static <T> T unSerializeObject(byte[] bytes,Class<T> t) {
        ByteArrayInputStream is=null;
        Input input=null;
        if(null != bytes && bytes.length>0 && null!=t){
            try {
                Kryo kryo = kryos.get();
                is = new ByteArrayInputStream(bytes);
                input = new Input(is);
                return kryo.readObject(input,t);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                close(is);
                close(input);
            }
        }
        return null;
    }


    /**
     * 使用ThreadLocal创建Kryo
     * 把List序列化成byte[];
     * @param list java对象
     * @return
     */
    public static <T>  byte[]  serializeList(List<T> list ) {
        ByteArrayOutputStream os=null;
        Output output=null;
        byte[] bytes = null;
        if(null != list && list.size()>0){
            Kryo kryo = kryos.get();
            try {
                os = new ByteArrayOutputStream();
                output = new Output(os);
                kryo.writeObject(output,list);
                close(output);
                bytes = os.toByteArray();
                return bytes;
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                close(os);
            }
        }
        return null;
    }

    /**
     * 使用ThreadLocal创建Kryo
     * 把byte[]反序列化成指定的List<T>
     * @param bytes byte数组
     * @param <T>
     * @return 指定java对象的List
     */
    public static <T> List<T> unSerializeList(byte[] bytes) {
        ByteArrayInputStream is=null;
        Input input=null;
        if(null !=bytes && bytes.length>0){
            try {
                Kryo kryo = kryos.get();
                is = new ByteArrayInputStream(bytes);
                input = new Input(is);
                List<T> list = kryo.readObject(input,ArrayList.class);
                return list;
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                close(is);
                close(input);
            }
        }
        return null;
    }




    /**
     * 使用ThreadLocal创建Kryo
     * 把java对象转序列化存储在文件中;
     * @param obj java对象
     * @return
     */
    public static <T>  boolean serializeFile(T obj,String path) {
        if(null != obj){
            Output output=null;
            try {
                Kryo kryo = kryos.get();
                output = new Output(new FileOutputStream(path));
                kryo.writeObject(output, obj);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                close(output);
            }
        }
        return false;
    }

    /**
     * 使用ThreadLocal创建Kryo
     * 把序列化的文件反序列化成指定的java对象
     * @param path 文件路径
     * @param t 指定的java对象
     * @param <T>
     * @return 指定的java对象
     */
    public static <T> T unSerializeFile(String path,Class<T> t) {
        if(null != path && null !=t ){
            Input input=null;
            try {
                Kryo kryo = kryos.get();
                input = new Input(new FileInputStream(path));
                return kryo.readObject(input,t);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                close(input);
            }
        }
        return null;
    }

    /**
     * 使用KryoPool SoftReferences创建Kryo
     * 把java对象序列化成byte[] ;
     * @param obj java对象
     * @return
     */
    public static <T>  byte[] serializePoolSoftReferences (T obj) {
        if(null!=obj){
            Kryo kryo =pool.borrow();
            ByteArrayOutputStream os=null;
            Output output=null;
            try {
                os = new ByteArrayOutputStream();
                output = new Output(os);
                kryo.writeObject(output, obj);
                close(output);
                byte [] bytes = os.toByteArray();
                return bytes;
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                pool.release(kryo);
                close(os);
            }
        }
        return null;
    }
    /**
     * 使用KryoPool SoftReferences创建Kryo
     * 把byte[]反序列化成指定的java对象
     * @param bytes
     * @return
     */
    public static <T> T unSerializePoolSoftReferences(byte[] bytes,Class<T> t) {
        if(null !=bytes && bytes.length>0 && null!=t){
            Kryo kryo =pool.borrow();
            ByteArrayInputStream is=null;
            Output output=null;
            try {
                is = new ByteArrayInputStream(bytes);
                Input input= new Input(is);
                return kryo.readObject(input, t);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                pool.release(kryo);
                close(is);
                close(output);
            }
        }
        return null;
    }


    /**
     * 使用KryoPool SoftReferences创建Kryo
     * 把java对象序列化成byte[] ;
     * @param obj java对象
     * @return
     */
    public static <T>  byte[] serializePoolCallback (final T obj) {
        if(null != obj){
            try {
                return pool.run(new KryoCallback<byte[]>() {
                    public byte[] execute(Kryo kryo) {
                        ByteArrayOutputStream os = new ByteArrayOutputStream();
                        Output output = new Output(os);
                        kryo.writeObject(output,obj);
                        output.close();
                        try {
                            os.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return os.toByteArray();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 使用KryoPool SoftReferences创建Kryo
     * 把byte[]反序列化成指定的java对象
     * @param bytes
     * @return
     */
    public static <T> T unSerializePoolCallback(final byte[] bytes, final Class<T> t) {
        if(null != bytes && bytes.length>0 && null != t){
            try {
                return pool.run(new KryoCallback<T>() {
                    public T execute(Kryo kryo) {
                        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
                        Input input = new Input(is);
                        T result =kryo.readObject(input,t);
                        input.close();
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return result;
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 关闭io流对象
     *
     * @param closeable
     */
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws FileNotFoundException {
//
//        Set<Point> points = new HashSet<>();
//        Point point = new Point();
//        point.setValue(123.4);
//        point.setTimestamp(System.currentTimeMillis());
//
//        points.add(point);
//
//        byte[] listBean=KryoUtil.writeObjectToByteArray(points);
//
//        Set<Point> list=KryoUtil.readFromByteArray(listBean,HashSet.class);
//
//        System.out.println(list);


//        Test t=new Test();
//        t.setA("a");
//        t.setB("b");
//        t.setC(1);
//        ArrayList<Test> arrayList=new ArrayList();
//        arrayList.add(t);
//        byte[] testBean=serializeObject(t);
//        Test test=unSerializeObject(testBean,Test.class);
//        byte[] listBean=serializeObject(arrayList);
//        ArrayList<Test> list=unSerializeObject(listBean,ArrayList.class);
//
//        ArrayList<String> StringList=new ArrayList();
//        StringList.add("aa");
//        StringList.add("f");
//        StringList.add("afa");
//        StringList.add("ahha");
//        byte[] stringList=serializeList(StringList);
//        List<String> lists=unSerializeList(stringList);
//        List<String> listObject=unSerializeObject(stringList,ArrayList.class);
//
//        byte[] stringList2=serializePoolSoftReferences(StringList);
//        List<String> lists2=unSerializePoolSoftReferences(stringList2,ArrayList.class);
//
//        byte[] stringList3=serializePoolCallback(StringList);
//        List<String> lists3=unSerializePoolCallback(stringList3,ArrayList.class);
//
//
//
//
//       /* ArrayList<Map<String,String>> mapList=new ArrayList();
//        Map<String,String> map1=new HashMap<String, String>();
//        map1.put("1","a");
//        Map<String,String> map2=new HashMap<String, String>();
//        map2.put("1","b");
//        mapList.add(map1);
//        mapList.add(map2);
//        byte[] mapListByte=serializeObject(mapList);
//        List<Map<String,String>> listMaps=unSerializeObject(mapListByte,ArrayList.class);
//        System.out.println("");
//
//        byte[] mapByte=serializeObject(map1);
//        Map<String,String> Maps=unSerializeObject(mapByte,HashMap.class);
//         List<Test> listww = new ArrayList<Test>();
//        Test p ;
//        for(int i=0;i<=10000;i++){
//            p = new Test();
//            p.setA("ww"+i);
//            listww.add(p);
//        }
//        serializeFile(listww,"F://file.txt");
//        List<Test> ddd=unSerializeFile("F://file.txt",ArrayList.class);
//
//        byte[] www=serializeObject(listww);
//        System.out.println("=="+www.length);
//        List<Test> ddd=unSerializeObject(www,ArrayList.class);*/
//        System.out.println("");
    }


}
