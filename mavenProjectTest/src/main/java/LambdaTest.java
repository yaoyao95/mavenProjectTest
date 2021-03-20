import cn.hutool.core.collection.CollUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class LambdaTest {
    public static void main(String[] args) {
//        List<List<Date>> lz = new ArrayList<>();
//        List<Date> l1 = new ArrayList<>();
//        l1.add(new Date());
//        l1.add(new Date());
//        lz.add(l1);
//        List<Date> l2 = new ArrayList<>();
//        l2.add(new Date());
//        lz.add(l2);
//
//        List<Date> dates = lz.stream()
//                .reduce(CollUtil::unionAll)
//                .orElse(new ArrayList<>(0));
        List<DateObj> lz = new ArrayList<>();
        List<Date> l1 = new ArrayList<>();
        l1.add(new Date());
        l1.add(new Date());
        lz.add(new DateObj(l1));
        List<Date> l2 = new ArrayList<>();
        l2.add(new Date());
        lz.add(new DateObj(l2));

//
//
//        List<Date> dates = lz.stream()
//                .map(DateObj::getDates)
//                .reduce(CollUtil::unionAll)
//                .orElse(new ArrayList<>(0));

        List<Date> dates = Optional.ofNullable(lz).map(
                ((List<DateObj> ll) -> ll.stream()
                        .map(DateObj::getDates)
                        .reduce(CollUtil::unionAll)
                        .orElse(new ArrayList<>(0)))
        ).orElse(new ArrayList<>());

        System.out.println(dates.size());
        System.out.println();

        List<String> s1 = new ArrayList<>();
        List<String> s2 = new ArrayList<>();
        s1.addAll(s2);
        System.out.println(s1.size());
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class DateObj{
        private List<Date> dates;
    }
}
