import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

public class FastJsonTest {
    public static void main(String[] args) {
        new ArrayList<Set<Map<String, Date>>>();
        HashMap<Object, Object> h = new HashMap<>();
        h.put("aaa",new MyDate(new Date(),1));
        h.put("bbb",new MyDate(new Date(),2));
        HashSet<Object> s = new HashSet<>();
        s.add(h);
        ArrayList<Object> a = new ArrayList<>();
        a.add(s);
        System.out.println(JSON.toJSONString(a, SerializerFeature.PrettyFormat));

        List<Set> asm = JSON.parseArray(JSON.toJSONString(a),Set.class);
        List<Object> asms = JSON.parseArray(JSON.toJSONString(a), new Class[]{HashSet.class, HashMap.class, String.class, Date.class});
        System.out.println(JSON.toJSONString(asm));
        System.out.println(JSON.toJSONString(asms));

        Map<Object, Object> myDate = JSON.parseObject(JSON.toJSONString(h), Map.class);
        ;
        HashMap<String, MyDate> sss = JSON.parseObject(JSON.toJSONString(h), new TypeReference<HashMap<String, MyDate>>() {});
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class MyDate{
        @JSONField(format = "yyyy-MM-dd HH:mm:ss")
        private Date date;
        private Integer num;
    }
}
