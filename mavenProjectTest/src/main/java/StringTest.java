import java.nio.charset.StandardCharsets;

public class StringTest {
    public static void main(String[] args) {
        String a = "我们再一次";
        System.out.println(a.getBytes(StandardCharsets.UTF_8).length);



//        java ClassAccess access = ClassAccess.access(TestObject.class);
//        TestObject obj;
//        //Identify the indexes for further use//
//        int newIndex=access.indexOfConstructor(int.class, Double.class, String.class, long.class);
//        int fieldIndex=access.indexOfField("fd");
//        int methodIndex=access.indexOfMethod("func1",String.class);
//        //Now use the index to access object in loop or other part
//        for(int i=0;i<100;i++) {
//            obj=access.newInstanceWithIndex(newIndex,1,2,3,4);
//            access.set(obj,fieldIndex,123);
//            String result=access.invokeWithIndex(null,methodIndex,"x");
//        }
    }

}
